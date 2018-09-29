package com.jamesmhare.magicmirror.views.internal.swt.weather;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;
import com.jamesmhare.magicmirror.properties.ApplicationProperties;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Serves as a class to display a weather report by making calls to the Open
 * Weather Map API.
 * 
 * @author James Hare
 */
public class WeatherImpl implements Weather {

	private Label currentConditions;
	private Label currentTemperature;
	private Label sunriseTime;
	private Label sunsetTime;
	private JSONObject weatherData;
	private Display display;
	private ApplicationProperties properties;
	private static final Logger LOGGER = Logger.getLogger(WeatherImpl.class);

	WeatherImpl(Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, WeatherImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, WeatherImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		this.display = display;
		parent.setBackground(ApplicationConstants.BLACK);
		properties = new ApplicationProperties();
		weatherData = retrieveWeatherData();
		currentConditions = createWeatherLabel(parent, display,
				extractStringElementFromInnerJSONArray(weatherData, "weather", "description") + " in "
						+ (String) (weatherData.get("name")),
				"Verdana", 16);
		currentTemperature = createWeatherLabel(parent, display,
				convertKelvinToFahrenheit(extractDoubleElementFromInnerJSONObject(weatherData, "main", "temp")) + "°F",
				"Verdana", 28);
		sunriseTime = createWeatherLabel(parent, display,
				"Sunrise: " + convertDateToString(extractLongElementFromInnerJSONObject(weatherData, "sys", "sunrise")),
				"Verdana", 16);
		sunsetTime = createWeatherLabel(parent, display,
				"Sunset: " + convertDateToString(extractLongElementFromInnerJSONObject(weatherData, "sys", "sunset")),
				"Verdana", 16);
	}

	private JSONObject retrieveWeatherData() {
		JSONObject output = new JSONObject();
		try {
			Client client = Client.create();
			String callToAPI = "http://api.openweathermap.org/data/2.5/weather?zip="
					+ properties.getProperty("UserZipCode") + ",us&APPID="
					+ properties.getProperty("OpenWeatherMapAPIKey");
			WebResource webResource = client.resource(callToAPI);
			ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
			if (response.getStatus() != 200) {
				LOGGER.error("" + "Failed: Weather call HTTP error code " + response.getStatus());
			}
			String result = response.getEntity(String.class);
			JSONParser parser = new JSONParser();
			output = (JSONObject) parser.parse(result);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		return output;
	}

	private Label createWeatherLabel(Composite parent, Display display, String text, String fontType, int fontSize) {
		Preconditions.checkArgument(parent != null, WeatherImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, WeatherImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(text != null, WeatherImplConstants.WEATHER_TEXT_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!text.isEmpty(), WeatherImplConstants.WEATHER_TEXT_EMPTY_ERROR_MESSAGE);
		Preconditions.checkArgument(fontType != null, WeatherImplConstants.FONTTYPE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!fontType.isEmpty(), WeatherImplConstants.FONTTYPE_EMPTY_ERROR_MESSAGE);
		Label weatherLabel = new Label(parent, SWT.NONE);
		Font weatherLabelFont = new Font(display, fontType, fontSize, SWT.NONE);
		weatherLabel.setText(text);
		weatherLabel.setBackground(ApplicationConstants.BLACK);
		weatherLabel.setForeground(ApplicationConstants.WHITE);
		weatherLabel.setFont(weatherLabelFont);
		return weatherLabel;
	}

	private Double extractDoubleElementFromInnerJSONObject(JSONObject jsonObject, String objectKey, String elementKey) {
		JSONObject innerObject = (JSONObject) jsonObject.get(objectKey);
		return new Double((double) innerObject.get(elementKey));
	}

	private Long extractLongElementFromInnerJSONObject(JSONObject jsonObject, String objectKey, String elementKey) {
		JSONObject innerObject = (JSONObject) jsonObject.get(objectKey);
		return new Long((long) innerObject.get(elementKey));
	}

	private String extractStringElementFromInnerJSONArray(JSONObject jsonObject, String arrayKey, String elementKey) {
		JSONArray innerArray = (JSONArray) jsonObject.get(arrayKey);
		JSONObject innerObject = (JSONObject) innerArray.get(0);
		return new String((String) innerObject.get(elementKey));
	}

	private String convertKelvinToFahrenheit(Double kelvinInput) {
		Preconditions.checkArgument(kelvinInput != null, WeatherImplConstants.KELVIN_INPUT_NULL_ERROR_MESSAGE);
		return new String(Integer.toString((int) Math.round((kelvinInput - 273.15) * (9 / 5) + 32)));
	}

	private String convertDateToString(long dateUTC) {
		return new String(new SimpleDateFormat("h:mm a").format(new Date(dateUTC * 1000L)));

	}
}

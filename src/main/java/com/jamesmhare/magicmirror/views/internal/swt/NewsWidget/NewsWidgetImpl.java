package com.jamesmhare.magicmirror.views.internal.swt.NewsWidget;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
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
 * Serves as a class to display news reports by making calls to the News REST
 * API.
 * 
 * @author James Hare
 */
public class NewsWidgetImpl implements NewsWidget {
	private Label[] headlines = new Label[NewsWidgetImplConstants.NUMBER_OF_HEADLINES_TO_DISPLAY];
	private JSONObject newsData;
	private Display display;
	private ApplicationProperties properties;
	private static final Logger LOGGER = Logger.getLogger(NewsWidgetImpl.class);

	NewsWidgetImpl(Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, NewsWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, NewsWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		this.display = display;
		parent.setBackground(ApplicationConstants.BLACK);
		properties = new ApplicationProperties();
		updateNewsWidget(parent);
		startNewsWidgetUpdater(parent);
	}

	private void updateNewsWidget(Composite parent) {
		newsData = retrieveNewsWidgetData();
		for (int i = 0; i < headlines.length; i++) {
			headlines[i] = createNewsWidgetLabel(parent, display,
					extractStringElementFromInnerObjectInInnerJSONArray(newsData, "articles", i, "source", "name")
							+ ": " + extractStringElementFromInnerJSONArray(newsData, "articles", i, "title"),
					"Verdana", 8);
		}
	}

	private JSONObject retrieveNewsWidgetData() {
		JSONObject output = new JSONObject();
		try {
			Client client = Client.create();
			String callToAPI = "https://newsapi.org/v2/top-headlines?country=us&apiKey="
					+ properties.getProperty("NewsAPIKey");
			WebResource webResource = client.resource(callToAPI);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				LOGGER.error(NewsWidgetImplConstants.NEWS_REST_CALL_ERROR_MESSAGE + response.getStatus());
				throw new Exception();
			}
			String result = response.getEntity(String.class);
			JSONParser parser = new JSONParser();
			output = (JSONObject) parser.parse(result);
			LOGGER.info(NewsWidgetImplConstants.LOG_MESSAGE_NEWS_INFO_RETRIEVED);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		return output;
	}

	private void startNewsWidgetUpdater(Composite parent) {
		LOGGER.info(NewsWidgetImplConstants.LOG_MESSAGE_STARTING_NEWS_UPDATE_THREAD);
		new Thread() {
			public void run() throws SWTException {
				while (!display.isDisposed()) {
					try {
						Thread.sleep(1800000); // sleep for 30 minutes.
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								updateNewsWidget(parent);
								LOGGER.info(NewsWidgetImplConstants.LOG_MESSAGE_NEWS_INFO_UPDATED);
							}
						});
					} catch (InterruptedException e) {
						LOGGER.error(NewsWidgetImplConstants.NEWS_UPDATE_THREAD_ERROR_MESSAGE + e);
					}
				}
			}
		}.start();
	}

	private Label createNewsWidgetLabel(Composite parent, Display display, String text, String fontType, int fontSize) {
		Preconditions.checkArgument(parent != null, NewsWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, NewsWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(text != null, NewsWidgetImplConstants.NEWS_TEXT_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!text.isEmpty(), NewsWidgetImplConstants.NEWS_TEXT_EMPTY_ERROR_MESSAGE);
		Preconditions.checkArgument(fontType != null, NewsWidgetImplConstants.FONTTYPE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!fontType.isEmpty(), NewsWidgetImplConstants.FONTTYPE_EMPTY_ERROR_MESSAGE);
		Label weatherLabel = new Label(parent, SWT.NONE);
		Font weatherLabelFont = new Font(display, fontType, fontSize, SWT.NONE);
		weatherLabel.setText(text);
		weatherLabel.setBackground(ApplicationConstants.BLACK);
		weatherLabel.setForeground(ApplicationConstants.WHITE);
		weatherLabel.setFont(weatherLabelFont);
		weatherLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		return weatherLabel;
	}

	private String extractStringElementFromInnerJSONArray(JSONObject jsonObject, String arrayKey, int arrayIndex,
			String elementKey) {
		JSONArray innerArray = (JSONArray) jsonObject.get(arrayKey);
		JSONObject innerObject = (JSONObject) innerArray.get(arrayIndex);
		return new String((String) innerObject.get(elementKey));
	}

	private String extractStringElementFromInnerObjectInInnerJSONArray(JSONObject jsonObject, String arrayKey,
			int arrayIndex, String objectKey, String elementKey) {
		JSONArray innerArray = (JSONArray) jsonObject.get(arrayKey);
		JSONObject innerObject = (JSONObject) innerArray.get(arrayIndex);
		JSONObject targetObject = (JSONObject) innerObject.get(objectKey);
		return new String((String) targetObject.get(elementKey));
	}

	/**
	 * {@inheritDoc}
	 */
	public Label[] getHeadlineLabels() {
		return headlines;
	}

}

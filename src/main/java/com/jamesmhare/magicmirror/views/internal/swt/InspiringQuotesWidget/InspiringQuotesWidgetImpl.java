package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Serves as a class to display the {@link InspiringQuotesWidget} a new
 * inspiring quote every day.
 * 
 * @author James Hare
 */
public class InspiringQuotesWidgetImpl implements InspiringQuotesWidget {
	private Label quoteLabel;
	private Label authorLabel;
	private JSONObject quoteData;
	private Display display;
	private static final Logger LOGGER = Logger.getLogger(InspiringQuotesWidgetImpl.class);

	public InspiringQuotesWidgetImpl(final Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, InspiringQuotesWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, InspiringQuotesWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		parent.setBackground(ApplicationConstants.BLACK);
		this.display = display;
		updateQuote(parent);
		startInspiringQuoteWidgetUpdater(parent);
	}

	private void updateQuote(Composite parent) {
		quoteData = retrieveQuoteData();
		quoteLabel = createLabel(parent, display,
				"\"" + extractQuoteAttributeFromJSON(quoteData, "contents", "quotes", "quote") + "\"", "Verdana", 10);
		authorLabel = createLabel(parent, display,
				" - " + extractQuoteAttributeFromJSON(quoteData, "contents", "quotes", "author"), "helvetica", 10);
	}

	private JSONObject retrieveQuoteData() {
		JSONObject output = new JSONObject();
		try {
			Client client = Client.create();
			String callToAPI = "https://quotes.rest/qod";
			WebResource webResource = client.resource(callToAPI);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				LOGGER.error(InspiringQuotesWidgetImplConstants.QUOTE_OF_THE_DAY_REST_CALL_ERROR_MESSAGE
						+ response.getStatus());
				throw new Exception();
			}
			String result = response.getEntity(String.class);
			JSONParser parser = new JSONParser();
			output = (JSONObject) parser.parse(result);
			LOGGER.info(InspiringQuotesWidgetImplConstants.LOG_MESSAGE_INSPIRING_QUOTE_INFO_RETRIEVED);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		return output;
	}

	private void startInspiringQuoteWidgetUpdater(Composite parent) {
		LOGGER.info(InspiringQuotesWidgetImplConstants.STARTING_INSPIRING_QUOTE_THREAD_LOG_MESSAGE);
		new Thread() {
			public void run() throws SWTException {
				while (!display.isDisposed()) {
					try {
						Thread.sleep(86400000); // sleep for 24 hours.
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								updateQuote(parent);
								LOGGER.info(
										InspiringQuotesWidgetImplConstants.LOG_MESSAGE_INSPIRING_QUOTE_INFO_UPDATED);
							}
						});
					} catch (InterruptedException e) {
						LOGGER.error(
								InspiringQuotesWidgetImplConstants.INSPIRING_QUOTE_UPDATE_THREAD_ERROR_MESSAGE + e);
					}
				}
			}
		}.start();
	}

	private Label createLabel(Composite parent, Display display, String text, String fontType, int fontSize) {
		Preconditions.checkArgument(parent != null, InspiringQuotesWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, InspiringQuotesWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(text != null,
				InspiringQuotesWidgetImplConstants.INSPIRING_QUOTE_TEXT_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!text.isEmpty(),
				InspiringQuotesWidgetImplConstants.INSPIRING_QUOTE_TEXT_EMPTY_ERROR_MESSAGE);
		Preconditions.checkArgument(fontType != null, InspiringQuotesWidgetImplConstants.FONTTYPE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!fontType.isEmpty(),
				InspiringQuotesWidgetImplConstants.FONTTYPE_EMPTY_ERROR_MESSAGE);
		Label label = new Label(parent, SWT.NONE);
		Font labelFont = new Font(display, fontType, fontSize, SWT.NONE);
		label.setText(text);
		label.setBackground(ApplicationConstants.BLACK);
		label.setForeground(ApplicationConstants.WHITE);
		label.setFont(labelFont);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, true, 1, 1));
		return label;
	}

	private String extractQuoteAttributeFromJSON(JSONObject jsonObject, String innerObjectKey, String arrayKey,
			String elementKey) {
		JSONObject innerObject = (JSONObject) jsonObject.get(innerObjectKey);
		JSONArray innerArray = (JSONArray) innerObject.get(arrayKey);
		JSONObject innerArrayObject = (JSONObject) innerArray.get(0);
		return new String((String) innerArrayObject.get(elementKey));
	}

	/**
	 * {@inheritDoc}
	 */
	public Label getQuoteLabel() {
		return quoteLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	public Label getAuthorLabel() {
		return authorLabel;
	}
}

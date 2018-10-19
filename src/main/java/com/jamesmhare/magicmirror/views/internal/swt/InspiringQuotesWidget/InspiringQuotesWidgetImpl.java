package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Serves as a class to display the {@link InspiringQuotesWidget} a new
 * inspiring quote every day.
 * 
 * @author James Hare
 */
public class InspiringQuotesWidgetImpl implements InspiringQuotesWidget {
	private Label quoteLabel;
	private Label authorLabel;
	private Display display;
	private static final Logger LOGGER = Logger.getLogger(InspiringQuotesWidgetImpl.class);

	public InspiringQuotesWidgetImpl(final Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, InspiringQuotesWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, InspiringQuotesWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		parent.setBackground(ApplicationConstants.BLACK);
		this.display = display;
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

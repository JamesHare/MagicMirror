package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the InspiringQuotesWidget object that will
 * show user an inspiring quote every day.
 * 
 * @author James Hare
 */
public interface InspiringQuotesWidget {
	/**
	 * Returns the current quote {@link Label}.
	 * 
	 * @return - The current quote {@link Label}.
	 */
	Label getQuoteLabel();

	/**
	 * Returns the current author {@link Label}.
	 * 
	 * @return - The current author {@link Label}.
	 */
	Label getAuthorLabel();
}

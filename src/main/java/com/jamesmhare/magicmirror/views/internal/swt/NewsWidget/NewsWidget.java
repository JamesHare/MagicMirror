package com.jamesmhare.magicmirror.views.internal.swt.NewsWidget;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the News Widget object that will show users
 * news reports.
 * 
 * @author James Hare
 */
public interface NewsWidget {
	/**
	 * Returns the headlines array of {@link Label}.
	 * 
	 * @return - The headlines array of {@link Label}.
	 */
	Label[] getHeadlineLabels();
}

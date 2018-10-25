package com.jamesmhare.magicmirror.views.internal.swt.NewsWidget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This class serves as a helper method for creating a {@link NewsWidget}
 * object.
 * 
 * @author James Hare
 */
public class NewsWidgetFactory {
	public NewsWidget createNewsWidget(Composite parent, Display display) {
		return new NewsWidgetImpl(parent, display);
	}
}

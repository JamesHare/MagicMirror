package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This class serves as a helper method for creating a new
 * {@link InspiringQuotesWidget}.
 * 
 * @author James Hare
 */
public class InspiringQuotesWidgetFactory {
	public InspiringQuotesWidget createInspiringQuotesWidget(Composite parent, Display display) {
		return new InspiringQuotesWidgetImpl(parent, display);
	}
}

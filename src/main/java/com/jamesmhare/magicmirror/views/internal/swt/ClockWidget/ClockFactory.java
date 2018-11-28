package com.jamesmhare.magicmirror.views.internal.swt.ClockWidget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This class serves as a helper method for creating a {@link Clock}.
 * 
 * @author James Hare
 */
public class ClockFactory {
	public Clock createClock(Composite parent, Display display) {
		return new ClockImpl(parent, display);
	}
}

package com.jamesmhare.magicmirror.views.internal.swt.ClockWidget;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the Clock object that will show date and time
 * information to the user.
 * 
 * @author James Hare
 */
public interface Clock {
	/**
	 * Returns the current running clock {@link Label}.
	 * 
	 * @return - The current running clock {@link Label}.
	 */
	Label getRunningClock();

	/**
	 * Returns the current running date {@link Label}.
	 * 
	 * @return - The current running date {@link Label}.
	 */
	Label getRunningDate();
}

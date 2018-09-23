package com.jamesmhare.magicmirror.views.internal.swt.clock;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the Clock object that will show users date
 * and time information.
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

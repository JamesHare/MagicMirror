package com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the Weather object that will show users
 * weather reports.
 * 
 * @author James Hare
 */
public interface Weather {
	/**
	 * Returns the current conditions {@link Label}.
	 * 
	 * @return - The current conditions {@link Label}.
	 */
	Label getCurrentConditions();

	/**
	 * Returns the current temperature {@link Label}.
	 * 
	 * @return - The current temperature {@link Label}.
	 */
	Label getCurrentTemperature();

	/**
	 * Returns the sunrise time {@link Label}.
	 * 
	 * @return - The sunrise time {@link Label}.
	 */
	Label getSunriseTime();

	/**
	 * Returns the sunset time {@link Label}.
	 * 
	 * @return - The sunset time {@link Label}.
	 */
	Label getSunsetTime();
}

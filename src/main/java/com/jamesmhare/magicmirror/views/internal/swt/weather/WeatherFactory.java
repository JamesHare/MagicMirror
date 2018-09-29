package com.jamesmhare.magicmirror.views.internal.swt.weather;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This class serves as a helper method for creating a {@link Weather} object.
 * 
 * @author James Hare
 */
public class WeatherFactory {
	public Weather createWeather(Composite parent, Display display) {
		return new WeatherImpl(parent, display);
	}
}

package com.jamesmhare.magicmirror.applicationconstants;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

/**
 * This class hold the constants to be used across the entire project.
 * 
 * @author jameshare
 */
public class ApplicationConstants {
	public static final Device device = Display.getCurrent();
	public static final Color WHITE = new Color(device, 255, 255, 255);
	public static final Color BLACK = new Color(device, 0, 0, 0);
}

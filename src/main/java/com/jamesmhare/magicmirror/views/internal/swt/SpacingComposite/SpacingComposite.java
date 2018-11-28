package com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite;

import org.eclipse.swt.widgets.Label;

/**
 * This serves as an interface for the Spacing Composite object that will place
 * blank composites where we want the user to be able to use the mirror aspect
 * of the project.
 * 
 * @author James Hare
 */
public interface SpacingComposite {
	/**
	 * Returns a blank place holder {@link Label}.
	 * 
	 * @return - The place holder {@link Label}.
	 */
	Label getPlaceHolderLabel();
}

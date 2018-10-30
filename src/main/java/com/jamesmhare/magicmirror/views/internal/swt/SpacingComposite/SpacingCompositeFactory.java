package com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This class serves as a helper method for creating a {@link SpacingComposite}
 * object.
 * 
 * @author James Hare
 */
public class SpacingCompositeFactory {
	public SpacingComposite createSpacingComposite(Composite parent, Display display) {
		return new SpacingCompositeImpl(parent, display);
	}
}

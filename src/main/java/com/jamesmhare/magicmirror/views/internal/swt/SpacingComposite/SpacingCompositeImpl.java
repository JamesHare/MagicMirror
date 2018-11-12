package com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Serves as the implementation of the {@link SpacingComposite} to create a
 * blank composite when the user should have full view of the mirror aspect of
 * the project.
 * 
 * @author James Hare
 */
public class SpacingCompositeImpl implements SpacingComposite {
	private Display display;
	private Label blankLabel;

	public SpacingCompositeImpl(Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, SpacingCompositeImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(parent != null, SpacingCompositeImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		display = this.display;
		parent.setBackground(ApplicationConstants.BLACK);
		blankLabel = new Label(parent, SWT.NONE);
		blankLabel.setBackground(ApplicationConstants.BLACK);
		blankLabel.setForeground(ApplicationConstants.BLACK);
	}

	/**
	 * {@inheritDoc}
	 */
	public Label getBlankLabel() {
		return blankLabel;
	}

}

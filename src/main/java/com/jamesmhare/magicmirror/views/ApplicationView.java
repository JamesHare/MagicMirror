package com.jamesmhare.magicmirror.views;

import org.eclipse.swt.widgets.Shell;

/**
 * This is the interface for the {@link ApplicationView} and defines the helper
 * methods for the view.
 * 
 * @author jameshare
 */
public interface ApplicationView {
	/**
	 * Creates the widgets that make up the {@link ApplicationView}.
	 * 
	 * @param shell - The parent {@code Shell}.
	 */
	void createWidgets(final Shell shell);

	/**
	 * Centers a given {@link Shell}.
	 * 
	 * @param shell - The {@code Shell} to be centered.
	 */
	void centerShell(final Shell shell);
}

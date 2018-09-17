package com.jamesmhare.magicmirror.views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the interface for the {@link ApplicationView} and defines the helper
 * methods for the view.
 * 
 * @author James Hare
 */
public interface ApplicationView {
	/**
	 * Creates the widgets that make up the {@link ApplicationView}.
	 * 
	 * @param shell   - The parent {@link Shell}. Cannot be {@link Null}.
	 * @param display - The current {@link Display}. Cannot be {@link Null}.
	 */
	void createWidgets(final Shell shell, final Display display);

	/**
	 * Centers a given {@link Shell}.
	 * 
	 * @param shell - The {@link Shell} to be centered. Cannot be {@link Null}.
	 */
	void centerShell(final Shell shell);
}

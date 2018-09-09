package com.jamesmhare.magicmirror.views;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Serves as the implementation of the {@link ApplicationView} and determines
 * how the application will look.
 * 
 * @author jameshare
 */
public class ApplicationViewImpl implements ApplicationView {

	private static Shell shell;

	/**
	 * Constructor for the parent view that sets the size of the shell, creates the
	 * widgets inside the shell and centers the shell.
	 * 
	 * @param shell - The parent {@code Shell}.
	 */
	public ApplicationViewImpl(final Shell shell) {
		Preconditions.checkArgument(shell != null, ApplicationViewConstants.APPLICATION_VIEW_SHELL_NULL_ERROR_MESSAGE);
		ApplicationViewImpl.shell = shell;
		shell.setText(ApplicationViewConstants.APPLICATION_VIEW_SHELL_TITLE);
		shell.setBackground(ApplicationConstants.BLACK);
		/**
		 * The shell will be 3 columns wide.
		 */
		GridLayout ApplicationViewLayout = new GridLayout(ApplicationViewConstants.APPLICATION_VIEW_NUMBER_OF_COLUMNS,
				true);
		shell.setLayout(ApplicationViewLayout);
		shell.setMinimumSize(shell.getSize());
		centerShell(shell);
		createWidgets(shell);
	}

	/**
	 * Creates the widgets that make up the {@link ApplicationView}.
	 * 
	 * @param shell - The parent {@code Shell}.
	 */
	public void createWidgets(Shell shell) {
		/**
		 * Widgets will be added to this method as they are developed. Each one will be
		 * given a number of columns, used for sizing its child shells, and a boolean
		 * with true indicating that each column has an equal width.
		 */
	}

	/**
	 * Centers a given {@link Shell}.
	 * 
	 * @param shell - The {@code Shell} to be centered.
	 */
	public void centerShell(Shell shell) {
		Rectangle boundsOfDisplay = shell.getDisplay().getPrimaryMonitor().getBounds();
		Point sizeOfShell = shell.getSize();
		int leftShellBound = (boundsOfDisplay.width - sizeOfShell.x) / 2;
		int topShellBound = (boundsOfDisplay.height - sizeOfShell.y) / 2;
		shell.setBounds(leftShellBound, topShellBound, sizeOfShell.x, sizeOfShell.y);
	}

}

package com.jamesmhare.magicmirror.views.internal.swt.ClockWidget;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Serves as a class to display a digital clock with date and time by using
 * {@link SimpleDateFormat}.
 * 
 * @author James Hare
 */
public class ClockImpl implements Clock {

	private Label runningDate;
	private Label runningClock;
	private Display display;
	private static final Logger LOGGER = Logger.getLogger(ClockImpl.class);

	public ClockImpl(final Composite parent, Display display) {
		Preconditions.checkArgument(parent != null, ClockImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, ClockImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		parent.setBackground(ApplicationConstants.BLACK);
		this.display = display;
		runningDate = createClockLabel(parent, display, "EEEE, MMMM dd, yyyy", "Verdana", 14);
		runningClock = createClockLabel(parent, display, "h:mm:ss a", "Arial Narrow", 32);
		startClock();
	}

	private Label createClockLabel(Composite parent, Display display, String clockFormat, String fontType,
			int fontSize) {
		Preconditions.checkArgument(parent != null, ClockImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null, ClockImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(clockFormat != null, ClockImplConstants.CLOCKFORMAT_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!clockFormat.isEmpty(), ClockImplConstants.CLOCKFORMAT_EMPTY_ERROR_MESSAGE);
		Preconditions.checkArgument(fontType != null, ClockImplConstants.FONTTYPE_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(!fontType.isEmpty(), ClockImplConstants.FONTTYPE_EMPTY_ERROR_MESSAGE);
		Label clockLabel = new Label(parent, SWT.NONE);
		Font clockLabelFont = new Font(display, fontType, fontSize, SWT.NONE);
		clockLabel.setText(new SimpleDateFormat(clockFormat).format(new Date()));
		clockLabel.setBackground(ApplicationConstants.BLACK);
		clockLabel.setForeground(ApplicationConstants.WHITE);
		clockLabel.setFont(clockLabelFont);
		clockLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
		return clockLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	public Label getRunningClock() {
		return runningClock;
	}

	/**
	 * {@inheritDoc}
	 */
	public Label getRunningDate() {
		return runningDate;
	}

	private void startClock() {
		LOGGER.info(ClockImplConstants.LOG_MESSAGE_STARTING_CLOCK_THREAD);
		new Thread() {
			public void run() throws SWTException {
				while (!display.isDisposed()) {
					try {
						Thread.sleep(1000);
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								updateClock();
							}
						});
					} catch (InterruptedException e) {
						LOGGER.error(ClockImplConstants.CLOCK_THREAD_ERROR_MESSAGE + e);
					}
				}
			}
		}.start();
	}

	private void updateClock() {
		runningDate.setText(new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date()));
		runningClock.setText(new SimpleDateFormat("h:mm:ss a").format(new Date()));
	}

}

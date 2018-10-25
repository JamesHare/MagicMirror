package com.jamesmhare.magicmirror.views.internal.swt.ClockWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.Clock;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.ClockFactory;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.ClockImplConstants;

/**
 * Test for the {@link ClockFactory} class.
 * 
 * @author James Hare
 *
 */
public class ClockFactoryTest {
	private Display display;
	private Shell shell;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	public Composite mockComposite;

	@Before
	public void setUp() throws Exception {
		shell = getShell();
		display = mock(Display.class);
		mockComposite = new Composite(shell, SWT.NONE);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link ClockFactory} if parent {@link Composite} is {@link Null}.
	 */
	@Test
	public void testClockFactoryNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ClockImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		ClockFactory clockFactory = new ClockFactory();
		clockFactory.createClock(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link ClockFactory} if parent {@link Display} is {@link Null}.
	 */
	@Test
	public void testClockFactoryNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ClockImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		ClockFactory clockFactory = new ClockFactory();
		clockFactory.createClock(shell, null);
	}

	/**
	 * Test to ensure that {@link ClockFactory} works correctly with good input and
	 * that a new {@link Clock} object was created.
	 */
	@Test
	public void testClockFactorySuccess() {
		ClockFactory clockFactory = new ClockFactory();
		Clock testClockObject = clockFactory.createClock(shell, display);
		assertThat(testClockObject, instanceOf(Clock.class));
	}

	private Shell getShell() {
		if (shell != null && shell.isDisposed()) {
			shell = null;
		}
		Display currentDisplay = (this.display != null) ? this.display : Display.getCurrent();
		if (currentDisplay != null && !currentDisplay.isDisposed()) {
			Shell active = currentDisplay.getActiveShell();
			if (active != null) {
				shell = new Shell(active);
			}
		}
		if (shell == null) {
			shell = new Shell(currentDisplay);
		}
		return shell;
	}

}

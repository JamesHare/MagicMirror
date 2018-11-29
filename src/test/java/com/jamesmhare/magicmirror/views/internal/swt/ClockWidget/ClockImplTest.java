package com.jamesmhare.magicmirror.views.internal.swt.ClockWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Test for the {@link ClockImpl} class.
 * 
 * @author James Hare
 * 
 */
public class ClockImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private Clock testClockImpl;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		shell = getShell();
		display = mock(Display.class);
		testClockImpl = new ClockImpl(shell, display);
	}

	@After
	public void teardown() {
		if (shell != null) {
			shell.dispose();
			shell = null;
		}
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if parent
	 * composite is {@link Null}.
	 */
	@Test
	public void testClockNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ClockImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		testClockImpl = new ClockImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if current
	 * display is {@link Null}.
	 */
	@Test
	public void testClockNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ClockImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		testClockImpl = new ClockImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link ClockImpl} constructor works correctly with good
	 * input and that a new {@link Clock} object was created.
	 */
	@Test
	public void testClockConstructorSuccess() {
		testClockImpl = new ClockImpl(shell, display);
		assertThat(testClockImpl, instanceOf(Clock.class));
	}

	/**
	 * Test to ensure that the labels are enabled by default when a new
	 * {@link ClockImpl} is constructed.
	 */
	@Test
	public void testLabelsAreEnabledByDefault() {
		testClockImpl = new ClockImpl(shell, display);
		assertTrue(testClockImpl.getRunningClock().isEnabled());
		assertTrue(testClockImpl.getRunningDate().isEnabled());
	}

	/**
	 * Test to ensure that the colors of the {@link Clock} are set correctly when a
	 * new {@link ClockImpl} is constructed.
	 */
	@Test
	public void testLabelColorsAreSetToCorrectValues() {
		testClockImpl = new ClockImpl(shell, display);
		assertEquals(ApplicationConstants.WHITE, testClockImpl.getRunningClock().getForeground());
		assertEquals(ApplicationConstants.WHITE, testClockImpl.getRunningDate().getForeground());
		assertEquals(ApplicationConstants.BLACK, testClockImpl.getRunningClock().getBackground());
		assertEquals(ApplicationConstants.BLACK, testClockImpl.getRunningDate().getBackground());
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

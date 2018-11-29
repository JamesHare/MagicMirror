package com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget;

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
 * Test for the {@link WeatherImpl} class.
 * 
 * @author James Hare
 * 
 */
public class WeatherImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private Weather testWeatherImpl;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		shell = getShell();
		display = mock(Display.class);
		testWeatherImpl = new WeatherImpl(shell, display);
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
	public void testWeatherNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(WeatherImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		testWeatherImpl = new WeatherImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if current
	 * display is {@link Null}.
	 */
	@Test
	public void testWeatherNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(WeatherImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		testWeatherImpl = new WeatherImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link WeatherImpl} constructor works correctly with good
	 * input and that a new {@link Weather} object was created.
	 */
	@Test
	public void testWeatherConstructorSuccess() {
		testWeatherImpl = new WeatherImpl(shell, display);
		assertThat(testWeatherImpl, instanceOf(Weather.class));
	}

	/**
	 * Test to ensure that the labels are enabled by default when a new
	 * {@link WeatherImpl} is constructed.
	 */
	@Test
	public void testLabelsAreEnabledByDefault() {
		testWeatherImpl = new WeatherImpl(shell, display);
		assertTrue(testWeatherImpl.getCurrentConditions().isEnabled());
		assertTrue(testWeatherImpl.getCurrentTemperature().isEnabled());
		assertTrue(testWeatherImpl.getSunriseTime().isEnabled());
		assertTrue(testWeatherImpl.getSunsetTime().isEnabled());
	}

	/**
	 * Test to ensure that the colors of the {@link Weather} are set correctly when
	 * a new {@link WeatherImpl} is constructed.
	 */
	@Test
	public void testLabelColorsAreSetToCorrectValues() {
		testWeatherImpl = new WeatherImpl(shell, display);
		assertEquals(ApplicationConstants.WHITE, testWeatherImpl.getCurrentConditions().getForeground());
		assertEquals(ApplicationConstants.WHITE, testWeatherImpl.getCurrentTemperature().getForeground());
		assertEquals(ApplicationConstants.WHITE, testWeatherImpl.getSunriseTime().getForeground());
		assertEquals(ApplicationConstants.WHITE, testWeatherImpl.getSunsetTime().getForeground());
		assertEquals(ApplicationConstants.BLACK, testWeatherImpl.getCurrentConditions().getBackground());
		assertEquals(ApplicationConstants.BLACK, testWeatherImpl.getCurrentTemperature().getBackground());
		assertEquals(ApplicationConstants.BLACK, testWeatherImpl.getSunriseTime().getBackground());
		assertEquals(ApplicationConstants.BLACK, testWeatherImpl.getSunsetTime().getBackground());
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

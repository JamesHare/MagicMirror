package com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

/**
 * Test for the {@link WeatherFactory} class.
 * 
 * @author James Hare
 *
 */
public class WeatherFactoryTest {
	private Display display;
	private Shell shell;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	public Composite mockComposite;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		shell = getShell();
		display = mock(Display.class);
		mockComposite = new Composite(shell, SWT.NONE);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link WeatherFactory} if parent {@link Composite} is {@link Null}.
	 */
	@Test
	public void testWeatherFactoryNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(WeatherImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		WeatherFactory weatherFactory = new WeatherFactory();
		weatherFactory.createWeather(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link WeatherFactory} if parent {@link Display} is {@link Null}.
	 */
	@Test
	public void testWeatherFactoryNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(WeatherImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		WeatherFactory weatherFactory = new WeatherFactory();
		weatherFactory.createWeather(shell, null);
	}

	/**
	 * Test to ensure that {@link WeatherFactory} works correctly with good input
	 * and that a new {@link Weather} object was created.
	 */
	@Test
	public void testWeatherFactorySuccess() {
		WeatherFactory weatherFactory = new WeatherFactory();
		Weather testWeather = weatherFactory.createWeather(shell, display);
		assertThat(testWeather, instanceOf(Weather.class));
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

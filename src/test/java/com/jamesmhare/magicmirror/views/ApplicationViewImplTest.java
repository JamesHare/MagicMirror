package com.jamesmhare.magicmirror.views;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.Clock;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.ClockFactory;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.ClockImpl;
import com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget.InspiringQuotesWidget;
import com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget.InspiringQuotesWidgetFactory;
import com.jamesmhare.magicmirror.views.internal.swt.NewsWidget.NewsWidget;
import com.jamesmhare.magicmirror.views.internal.swt.NewsWidget.NewsWidgetFactory;
import com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget.Weather;
import com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget.WeatherFactory;
import com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget.WeatherImpl;

/**
 * Test class for {@link ApplicationViewImpl}.
 * 
 * @author James Hare
 */
public class ApplicationViewImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private ApplicationViewImpl testApplicationViewImpl;
	private Clock mockClock;
	private Weather mockWeather;
	private InspiringQuotesWidget mockInspiringQuotesWidget;
	private NewsWidget mockNewsWidget;
	private ClockFactory mockClockFactory;
	private WeatherFactory mockWeatherFactory;
	private InspiringQuotesWidgetFactory mockInspiringQuotesWidgetFactory;
	private NewsWidgetFactory mockNewsWidgetFactory;

	/**
	 * Runs before the tests the open a new Shell.
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		shell = getShell();
		display = mock(Display.class);
		createMockClock();
		createMockWeather();
		createMockInspiringQuotesWidget();
		createMockNewsWidget();
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * parent {@link Shell} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewShellIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_SHELL_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * current {@link Display} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewDisplayIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_DISPLAY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link ClockFactory} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewClockFactoryIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_CLOCK_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, null, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link WeatherFactory} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewWeatherFactoryIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_WEATHER_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, null,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link InspiringQuotesWidgetFactory} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewInspiringQuotesWidgetFactoryIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(
				ApplicationViewConstants.APPLICATION_VIEW_INSPIRING_QUOTES_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory, null,
				mockNewsWidgetFactory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link NewsWidgetFactory} is {@link Null}.
	 */
	@Test
	public void testErrorWhenApplicationViewNewsWidgetFactoryIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_NEWS_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, null);
	}

	/**
	 * Test to ensure that the minimum size is set with constructor.
	 */
	@Test
	public void testShellMinimumSizeIsSet() {
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory);
		assertEquals(shell.getSize(), shell.getMinimumSize());
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

	private void createMockClock() {
		mockClock = mock(ClockImpl.class);
		mockClockFactory = mock(ClockFactory.class);
		when(mockClockFactory.createClock(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockClock);
	}

	private void createMockWeather() {
		mockWeather = mock(WeatherImpl.class);
		mockWeatherFactory = mock(WeatherFactory.class);
		when(mockWeatherFactory.createWeather(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockWeather);
	}

	private void createMockInspiringQuotesWidget() {
		mockInspiringQuotesWidget = mock(InspiringQuotesWidget.class);
		mockInspiringQuotesWidgetFactory = mock(InspiringQuotesWidgetFactory.class);
		when(mockInspiringQuotesWidgetFactory.createInspiringQuotesWidget(Mockito.any(Composite.class),
				Mockito.any(Display.class))).thenReturn(mockInspiringQuotesWidget);
	}

	private void createMockNewsWidget() {
		mockNewsWidget = mock(NewsWidget.class);
		mockNewsWidgetFactory = mock(NewsWidgetFactory.class);
		when(mockNewsWidgetFactory.createNewsWidget(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockNewsWidget);
	}
}

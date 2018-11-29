package com.jamesmhare.magicmirror.views;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.BasicConfigurator;
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
import com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite.SpacingComposite;
import com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite.SpacingCompositeFactory;
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
	private SpacingComposite mockSpaceA2;
	private SpacingComposite mockSpaceB3;
	private SpacingComposite mockSpaceC1;
	private SpacingComposite mockSpaceC3;
	private ClockFactory mockClockFactory;
	private WeatherFactory mockWeatherFactory;
	private InspiringQuotesWidgetFactory mockInspiringQuotesWidgetFactory;
	private NewsWidgetFactory mockNewsWidgetFactory;
	private SpacingCompositeFactory mockSpaceA2Factory;
	private SpacingCompositeFactory mockSpaceB3Factory;
	private SpacingCompositeFactory mockSpaceC1Factory;
	private SpacingCompositeFactory mockSpaceC3Factory;

	/**
	 * Runs before the tests the open a new Shell.
	 */
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		MockitoAnnotations.initMocks(this);
		shell = getShell();
		display = mock(Display.class);
		createMockClock();
		createMockWeather();
		createMockInspiringQuotesWidget();
		createMockNewsWidget();
		createMockA2SpacingComposite();
		createMockB3SpacingComposite();
		createMockC1SpacingComposite();
		createMockC3SpacingComposite();
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
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory,
				mockSpaceC1Factory, mockSpaceC3Factory);
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
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory,
				mockSpaceC1Factory, mockSpaceC3Factory);
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
				mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory, mockSpaceC1Factory, mockSpaceC3Factory);
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
				mockInspiringQuotesWidgetFactory, null, mockSpaceA2Factory, mockSpaceB3Factory, mockSpaceC1Factory,
				mockSpaceC3Factory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link SpacingCompositeFactory} at cell A2 is {@link Null}.
	 */
	@Test
	public void testErrorWhenSpacingCompositeFactoryAtA2IsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, null, mockSpaceB3Factory, mockSpaceC1Factory,
				mockSpaceC3Factory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link SpacingCompositeFactory} at cell B3 is {@link Null}.
	 */
	@Test
	public void testErrorWhenSpacingCompositeFactoryAtB3IsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, null, mockSpaceC1Factory,
				mockSpaceC3Factory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link SpacingCompositeFactory} at cell C1 is {@link Null}.
	 */
	@Test
	public void testErrorWhenSpacingCompositeFactoryAtC1IsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory, null,
				mockSpaceC3Factory);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown when the
	 * {@link SpacingCompositeFactory} at cell C3 is {@link Null}.
	 */
	@Test
	public void testErrorWhenSpacingCompositeFactoryAtC3IsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory,
				mockSpaceC1Factory, null);
	}

	/**
	 * Test to ensure that the minimum size is set with constructor.
	 */
	@Test
	public void testShellMinimumSizeIsSet() {
		testApplicationViewImpl = new ApplicationViewImpl(shell, display, mockClockFactory, mockWeatherFactory,
				mockInspiringQuotesWidgetFactory, mockNewsWidgetFactory, mockSpaceA2Factory, mockSpaceB3Factory,
				mockSpaceC1Factory, mockSpaceC3Factory);
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

	private void createMockA2SpacingComposite() {
		mockSpaceA2 = mock(SpacingComposite.class);
		mockSpaceA2Factory = mock(SpacingCompositeFactory.class);
		when(mockSpaceA2Factory.createSpacingComposite(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockSpaceA2);
	}

	private void createMockB3SpacingComposite() {
		mockSpaceB3 = mock(SpacingComposite.class);
		mockSpaceB3Factory = mock(SpacingCompositeFactory.class);
		when(mockSpaceB3Factory.createSpacingComposite(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockSpaceB3);
	}

	private void createMockC1SpacingComposite() {
		mockSpaceC1 = mock(SpacingComposite.class);
		mockSpaceC1Factory = mock(SpacingCompositeFactory.class);
		when(mockSpaceC1Factory.createSpacingComposite(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockSpaceC1);
	}

	private void createMockC3SpacingComposite() {
		mockSpaceC3 = mock(SpacingComposite.class);
		mockSpaceC3Factory = mock(SpacingCompositeFactory.class);
		when(mockSpaceC3Factory.createSpacingComposite(Mockito.any(Composite.class), Mockito.any(Display.class)))
				.thenReturn(mockSpaceC3);
	}
}

package com.jamesmhare.magicmirror.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Preconditions;
import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.Clock;
import com.jamesmhare.magicmirror.views.internal.swt.ClockWidget.ClockFactory;
import com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget.InspiringQuotesWidget;
import com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget.InspiringQuotesWidgetFactory;
import com.jamesmhare.magicmirror.views.internal.swt.NewsWidget.NewsWidget;
import com.jamesmhare.magicmirror.views.internal.swt.NewsWidget.NewsWidgetFactory;
import com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget.Weather;
import com.jamesmhare.magicmirror.views.internal.swt.WeatherWidget.WeatherFactory;

/**
 * Serves as the implementation of the {@link ApplicationView} and determines
 * how the application will look.
 * 
 * @author James Hare
 */
public class ApplicationViewImpl implements ApplicationView {

	private static Shell shell;
	private static Display display;
	private Clock clock;
	private Weather weather;
	private InspiringQuotesWidget inspiringQuotesWidget;
	private NewsWidget newsWidget;
	private final ClockFactory clockFactory;
	private final WeatherFactory weatherFactory;
	private final InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory;
	private final NewsWidgetFactory newsWidgetFactory;

	/**
	 * Constructor for the parent view which handles the centering of the parent
	 * {@link Shell} and ordering of the child Widgets.
	 * 
	 * @param shell   - The active parent {@link Shell}.
	 * @param display - The current {@link Display}.
	 */
	public ApplicationViewImpl(final Shell shell, Display display) {
		this(shell, display, new ClockFactory(), new WeatherFactory(), new InspiringQuotesWidgetFactory(),
				new NewsWidgetFactory());
	}

	/**
	 * Constructor for the parent view that sets the size of the shell, creates the
	 * widgets inside the shell and centers the shell.
	 * 
	 * @param shell                        - The parent {@link Shell}.
	 * @param display                      - The current {@link Display}.
	 * @param clockFactory                 - The factory for creating the
	 *                                     {@link Clock}. Cannot be {@link Null}.
	 * @param weatherFactory               - The factory for creating the
	 *                                     {@link Weather}. Cannot be {@link Null}.
	 * @param inspiringQuotesWidgetFactory - The factory for creating the
	 *                                     {@link InspiringQuotesWidget}. Cannot be
	 *                                     {@link Null}.
	 * @param newsWidgetFactory            - The factory for creating the
	 *                                     {@link NewsWidget}. Cannot be
	 *                                     {@link Null}.
	 */
	public ApplicationViewImpl(final Shell shell, Display display, ClockFactory clockFactory,
			WeatherFactory weatherFactory, InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory,
			NewsWidgetFactory newsWidgetFactory) {
		Preconditions.checkArgument(shell != null, ApplicationViewConstants.APPLICATION_VIEW_SHELL_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null,
				ApplicationViewConstants.APPLICATION_VIEW_DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(clockFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_CLOCK_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(weatherFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_WEATHER_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(inspiringQuotesWidgetFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_INSPIRING_QUOTES_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(weatherFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_NEWS_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		ApplicationViewImpl.shell = shell;
		ApplicationViewImpl.display = display;
		this.clockFactory = clockFactory;
		this.weatherFactory = weatherFactory;
		this.inspiringQuotesWidgetFactory = inspiringQuotesWidgetFactory;
		this.newsWidgetFactory = newsWidgetFactory;
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
		createWidgets(shell, display);
	}

	/**
	 * {@inheritDoc}
	 */
	public void createWidgets(Shell shell, Display display) {
		Preconditions.checkArgument(shell != null, ApplicationViewConstants.CREATE_WIDGETS_SHELL_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null,
				ApplicationViewConstants.CREATE_WIDGETS_DISPLAY_NULL_ERROR_MESSAGE);
		/**
		 * Widgets will be added to this method as they are developed. Each one will be
		 * given a number of columns, used for sizing its child shells, and a boolean
		 * with true indicating that each column has an equal width.
		 */
		Composite clockComposite = new Composite(shell, SWT.NONE);
		clockComposite.setLayout(new GridLayout(1, false));
		clock = clockFactory.createClock(clockComposite, display);
		Composite weatherComposite = new Composite(shell, SWT.NONE);
		weatherComposite.setLayout(new GridLayout(1, false));
		weather = weatherFactory.createWeather(weatherComposite, display);
		Composite inspiringQuotesComposite = new Composite(shell, SWT.NONE);
		inspiringQuotesComposite.setLayout(new GridLayout(1, false));
		inspiringQuotesWidget = inspiringQuotesWidgetFactory.createInspiringQuotesWidget(inspiringQuotesComposite,
				display);
		Composite newsWidgetComposite = new Composite(shell, SWT.NONE);
		newsWidgetComposite.setLayout(new GridLayout(1, false));
		newsWidget = newsWidgetFactory.createNewsWidget(newsWidgetComposite, display);
	}

	/**
	 * {@inheritDoc}
	 */
	public void centerShell(Shell shell) {
		Preconditions.checkArgument(shell != null, ApplicationViewConstants.CENTER_SHELL_SHELL_NULL_ERROR_MESSAGE);
		Rectangle boundsOfDisplay = shell.getDisplay().getPrimaryMonitor().getBounds();
		Point sizeOfShell = shell.getSize();
		int leftShellBound = (boundsOfDisplay.width - sizeOfShell.x) / 2;
		int topShellBound = (boundsOfDisplay.height - sizeOfShell.y) / 2;
		shell.setBounds(leftShellBound, topShellBound, sizeOfShell.x, sizeOfShell.y);
	}

}

package com.jamesmhare.magicmirror.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
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
import com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite.SpacingComposite;
import com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite.SpacingCompositeFactory;
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
	private SpacingComposite spaceA2;
	private SpacingComposite spaceB3;
	private SpacingComposite spaceC1;
	private SpacingComposite spaceC3;
	private final ClockFactory clockFactory;
	private final WeatherFactory weatherFactory;
	private final InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory;
	private final NewsWidgetFactory newsWidgetFactory;
	private final SpacingCompositeFactory spaceA2Factory;
	private final SpacingCompositeFactory spaceB3Factory;
	private final SpacingCompositeFactory spaceC1Factory;
	private final SpacingCompositeFactory spaceC3Factory;

	/**
	 * Constructor for the parent view which handles the centering of the parent
	 * {@link Shell} and ordering of the child Widgets.
	 * 
	 * @param shell   - The active parent {@link Shell}.
	 * @param display - The current {@link Display}.
	 */
	public ApplicationViewImpl(final Shell shell, Display display) {
		this(shell, display, new ClockFactory(), new WeatherFactory(), new InspiringQuotesWidgetFactory(),
				new NewsWidgetFactory(), new SpacingCompositeFactory(), new SpacingCompositeFactory(),
				new SpacingCompositeFactory(), new SpacingCompositeFactory());
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
	 * @param spaceA2Factory               - The factory for creating the
	 *                                     {@link SpaceComposite} placeholder in
	 *                                     cell A2.
	 * @param spaceB3Factory               - The factory for creating the
	 *                                     {@link SpaceComposite} placeholder in
	 *                                     cell B3.
	 * @param spaceC1Factory               - The factory for creating the
	 *                                     {@link SpaceComposite} placeholder in
	 *                                     cell C1.
	 * @param spaceC3Factory               - The factory for creating the
	 *                                     {@link SpaceComposite} placeholder in
	 *                                     cell C3.
	 */
	public ApplicationViewImpl(final Shell shell, Display display, ClockFactory clockFactory,
			WeatherFactory weatherFactory, InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory,
			NewsWidgetFactory newsWidgetFactory, SpacingCompositeFactory spaceA2Factory,
			SpacingCompositeFactory spaceB3Factory, SpacingCompositeFactory spaceC1Factory,
			SpacingCompositeFactory spaceC3Factory) {
		Preconditions.checkArgument(shell != null, ApplicationViewConstants.APPLICATION_VIEW_SHELL_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(display != null,
				ApplicationViewConstants.APPLICATION_VIEW_DISPLAY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(clockFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_CLOCK_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(weatherFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_WEATHER_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(inspiringQuotesWidgetFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_INSPIRING_QUOTES_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(newsWidgetFactory != null,
				ApplicationViewConstants.APPLICATION_VIEW_NEWS_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(spaceA2Factory != null,
				ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(spaceB3Factory != null,
				ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(spaceC1Factory != null,
				ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);
		Preconditions.checkArgument(spaceC3Factory != null,
				ApplicationViewConstants.APPLICATION_VIEW_SPACING_WIDGET_FACTORY_NULL_ERROR_MESSAGE);

		ApplicationViewImpl.shell = shell;
		ApplicationViewImpl.display = display;
		this.clockFactory = clockFactory;
		this.weatherFactory = weatherFactory;
		this.inspiringQuotesWidgetFactory = inspiringQuotesWidgetFactory;
		this.newsWidgetFactory = newsWidgetFactory;
		this.spaceA2Factory = spaceA2Factory;
		this.spaceB3Factory = spaceB3Factory;
		this.spaceC1Factory = spaceC1Factory;
		this.spaceC3Factory = spaceC3Factory;
		shell.setText(ApplicationViewConstants.APPLICATION_VIEW_SHELL_TITLE);
		shell.setBackground(ApplicationConstants.BLACK);
		/**
		 * The shell will be 3 columns wide.
		 */
		GridLayout ApplicationViewLayout = new GridLayout(3, true);
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
		GridData clockGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		clockComposite.setLayoutData(clockGridData);
		clock = clockFactory.createClock(clockComposite, display);

		Composite spaceA2Composite = new Composite(shell, SWT.NONE);
		spaceA2Composite.setLayout(new GridLayout(1, false));
		GridData spacingGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		spaceA2Composite.setLayoutData(spacingGridData);
		spaceA2 = spaceA2Factory.createSpacingComposite(spaceA2Composite, display);

		Composite weatherComposite = new Composite(shell, SWT.NONE);
		weatherComposite.setLayout(new GridLayout(2, false));
		GridData weatherGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		weatherComposite.setLayoutData(weatherGridData);
		weather = weatherFactory.createWeather(weatherComposite, display);

		Composite newsWidgetComposite = new Composite(shell, SWT.NONE);
		newsWidgetComposite.setLayout(new GridLayout(1, false));
		GridData newsWidgetGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		newsWidgetComposite.setLayoutData(newsWidgetGridData);
		newsWidget = newsWidgetFactory.createNewsWidget(newsWidgetComposite, display);

		Composite spaceB3Composite = new Composite(shell, SWT.NONE);
		spaceB3Composite.setLayout(new GridLayout(1, false));
		GridData bottomSpacingGridData = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
		spaceB3Composite.setLayoutData(bottomSpacingGridData);
		spaceB3 = spaceB3Factory.createSpacingComposite(spaceB3Composite, display);

		Composite spaceC1Composite = new Composite(shell, SWT.NONE);
		spaceC1Composite.setLayout(new GridLayout(1, false));
		spaceC1Composite.setLayoutData(bottomSpacingGridData);
		spaceC1 = spaceC1Factory.createSpacingComposite(spaceC1Composite, display);

		Composite inspiringQuotesComposite = new Composite(shell, SWT.NONE);
		inspiringQuotesComposite.setLayout(new GridLayout(1, false));
		GridData inspiringQuotesGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		spaceA2Composite.setLayoutData(inspiringQuotesGridData);
		inspiringQuotesWidget = inspiringQuotesWidgetFactory.createInspiringQuotesWidget(inspiringQuotesComposite,
				display);

		Composite spaceC3Composite = new Composite(shell, SWT.NONE);
		spaceC3Composite.setLayout(new GridLayout(1, false));
		spaceC3Composite.setLayoutData(bottomSpacingGridData);
		spaceC3 = spaceC3Factory.createSpacingComposite(spaceC3Composite, display);

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

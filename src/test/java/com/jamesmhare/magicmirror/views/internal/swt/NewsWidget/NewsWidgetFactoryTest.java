package com.jamesmhare.magicmirror.views.internal.swt.NewsWidget;

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
 * Test for the {@link NewsWidgetFactory} class.
 * 
 * @author James Hare
 *
 */
public class NewsWidgetFactoryTest {
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
	 * {@link NewsWidgetFactory} if parent {@link Composite} is {@link Null}.
	 */
	@Test
	public void testNewsWidgetFactoryNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(NewsWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		NewsWidgetFactory newsWidgetFactory = new NewsWidgetFactory();
		newsWidgetFactory.createNewsWidget(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link NewsWidgetFactory} if parent {@link Display} is {@link Null}.
	 */
	@Test
	public void testNewsWidgetFactoryNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(NewsWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		NewsWidgetFactory newsWidgetFactory = new NewsWidgetFactory();
		newsWidgetFactory.createNewsWidget(shell, null);
	}

	/**
	 * Test to ensure that {@link NewsWidgetFactory} works correctly with good input
	 * and that a new {@link NewsWidget} object was created.
	 */
	@Test
	public void testNewsWidgetFactorySuccess() {
		NewsWidgetFactory newsWidgetFactory = new NewsWidgetFactory();
		NewsWidget testNewsWidget = newsWidgetFactory.createNewsWidget(shell, display);
		assertThat(testNewsWidget, instanceOf(NewsWidget.class));
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

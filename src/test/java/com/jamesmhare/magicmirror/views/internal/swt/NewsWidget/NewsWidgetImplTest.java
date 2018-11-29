package com.jamesmhare.magicmirror.views.internal.swt.NewsWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Test for the {@link NewsWidgetImpl} class.
 * 
 * @author James Hare
 * 
 */
public class NewsWidgetImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private NewsWidget testNewsWidgetImpl;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		shell = getShell();
		display = mock(Display.class);
		testNewsWidgetImpl = new NewsWidgetImpl(shell, display);
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
	public void testNewsWidgetNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(NewsWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		testNewsWidgetImpl = new NewsWidgetImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if current
	 * display is {@link Null}.
	 */
	@Test
	public void testNewsWidgetNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(NewsWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		testNewsWidgetImpl = new NewsWidgetImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link NewsWidgetImpl} constructor works correctly with
	 * good input and that a new {@link NewsWidget} object was created.
	 */
	@Test
	public void testNewsWidgetConstructorSuccess() {
		testNewsWidgetImpl = new NewsWidgetImpl(shell, display);
		assertThat(testNewsWidgetImpl, instanceOf(NewsWidget.class));
	}

	/**
	 * Test to ensure that the labels are enabled by default when a new
	 * {@link NewsWidgetImpl} is constructed.
	 */
	@Test
	public void testHeadlineLabelsAreEnabledByDefault() {
		testNewsWidgetImpl = new NewsWidgetImpl(shell, display);
		Label[] testHeadlines = testNewsWidgetImpl.getHeadlineLabels();
		for (Label testHeadline : testHeadlines) {
			assertTrue(testHeadline.isEnabled());
		}
		assertTrue(testNewsWidgetImpl.getNewsWidgetTitleLabel().isEnabled());
	}

	/**
	 * Test to ensure that the colors of the {@link NewsWidget} are set correctly
	 * when a new {@link NewsWidgetImpl} is constructed.
	 */
	@Test
	public void testLabelColorsAreSetToCorrectValues() {
		testNewsWidgetImpl = new NewsWidgetImpl(shell, display);
		Label[] testHeadlines = testNewsWidgetImpl.getHeadlineLabels();
		for (Label testHeadline : testHeadlines) {
			assertEquals(ApplicationConstants.WHITE, testHeadline.getForeground());
			assertEquals(ApplicationConstants.BLACK, testHeadline.getBackground());
		}
		assertEquals(ApplicationConstants.WHITE, testNewsWidgetImpl.getNewsWidgetTitleLabel().getForeground());
		assertEquals(ApplicationConstants.BLACK, testNewsWidgetImpl.getNewsWidgetTitleLabel().getBackground());
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

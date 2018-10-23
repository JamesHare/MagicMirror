package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jamesmhare.magicmirror.applicationconstants.ApplicationConstants;

/**
 * Test for the {@link InspiringQuotesWidgetImpl} class.
 * 
 * @author James Hare
 * 
 */
public class InspiringQuotesWidgetImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private InspiringQuotesWidget testInspiringQuotesWidgetImpl;

	@Before
	public void setUp() throws Exception {
		shell = getShell();
		display = mock(Display.class);
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(shell, display);
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
	public void testInspiringQuotesWidgetNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(InspiringQuotesWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if current
	 * display is {@link Null}.
	 */
	@Test
	public void testInspiringQuotesWidgetNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(InspiringQuotesWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link InspiringQuotesWidgetImpl} constructor works
	 * correctly with good input and that a new {@link InspiringQuotesWidget} object
	 * was created.
	 */
	@Test
	public void testInspiringQuotesWidgetConstructorSuccess() {
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(shell, display);
		assertThat(testInspiringQuotesWidgetImpl, instanceOf(InspiringQuotesWidget.class));
	}

	/**
	 * Test to ensure that the labels are enabled by default when a new
	 * {@link InspiringQuotesWidgetImpl} is constructed.
	 */
	@Test
	public void testLabelsAreEnabledByDefault() {
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(shell, display);
		assertTrue(testInspiringQuotesWidgetImpl.getQuoteLabel().isEnabled());
		assertTrue(testInspiringQuotesWidgetImpl.getAuthorLabel().isEnabled());
	}

	/**
	 * Test to ensure that the colors of the {@link InspiringQuotesWidget} are set
	 * correctly when a new {@link InspiringQuotesWidgetImpl} is constructed.
	 */
	@Test
	public void testLabelColorsAreSetToCorrectValues() {
		testInspiringQuotesWidgetImpl = new InspiringQuotesWidgetImpl(shell, display);
		assertEquals(ApplicationConstants.WHITE, testInspiringQuotesWidgetImpl.getQuoteLabel().getForeground());
		assertEquals(ApplicationConstants.WHITE, testInspiringQuotesWidgetImpl.getAuthorLabel().getForeground());
		assertEquals(ApplicationConstants.BLACK, testInspiringQuotesWidgetImpl.getQuoteLabel().getBackground());
		assertEquals(ApplicationConstants.BLACK, testInspiringQuotesWidgetImpl.getAuthorLabel().getBackground());
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

package com.jamesmhare.magicmirror.views.internal.swt.InspiringQuotesWidget;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

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
 * Test for the {@link InspiringQuotesWidgetFactory} class.
 * 
 * @author James Hare
 *
 */
public class InspiringQuotesWidgetFactoryTest {
	private Display display;
	private Shell shell;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	public Composite mockComposite;

	@Before
	public void setUp() throws Exception {
		shell = getShell();
		display = mock(Display.class);
		mockComposite = new Composite(shell, SWT.NONE);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link InspiringQuotesWidgetFactory} if parent {@link Composite} is
	 * {@link Null}.
	 */
	@Test
	public void testInspiringQuotesWidgetFactoryNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(InspiringQuotesWidgetImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory = new InspiringQuotesWidgetFactory();
		inspiringQuotesWidgetFactory.createInspiringQuotesWidget(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link InspiringQuotesWidgetFactory} if parent {@link Display} is
	 * {@link Null}.
	 */
	@Test
	public void testInspiringQuotesWidgetFactoryNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(InspiringQuotesWidgetImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory = new InspiringQuotesWidgetFactory();
		inspiringQuotesWidgetFactory.createInspiringQuotesWidget(shell, null);
	}

	/**
	 * Test to ensure that {@link InspiringQuotesWidgetFactory} works correctly with
	 * good input and that a new {@link InspiringQuotesWidget} object was created.
	 */
	@Test
	public void testInspiringQuotesWidgetFactorySuccess() {
		InspiringQuotesWidgetFactory inspiringQuotesWidgetFactory = new InspiringQuotesWidgetFactory();
		InspiringQuotesWidget testInspiringQuotesWidget = inspiringQuotesWidgetFactory
				.createInspiringQuotesWidget(shell, display);
		assertThat(testInspiringQuotesWidget, instanceOf(InspiringQuotesWidget.class));
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

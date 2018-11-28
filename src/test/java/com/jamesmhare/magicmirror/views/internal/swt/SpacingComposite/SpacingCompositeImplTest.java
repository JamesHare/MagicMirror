package com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite;

import static org.hamcrest.CoreMatchers.instanceOf;
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

/**
 * Test for the {@link SpacingCompositeImpl} class.
 * 
 * @author James Hare
 * 
 */
public class SpacingCompositeImplTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private Shell shell;
	private Display display;
	private SpacingComposite testSpacingCompositeImpl;

	@Before
	public void setUp() throws Exception {
		shell = getShell();
		display = mock(Display.class);
		testSpacingCompositeImpl = new SpacingCompositeImpl(shell, display);
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
	public void testSpacingCompositeNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(SpacingCompositeImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		testSpacingCompositeImpl = new SpacingCompositeImpl(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown if current
	 * display is {@link Null}.
	 */
	@Test
	public void testSpacingCompositeNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(SpacingCompositeImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		testSpacingCompositeImpl = new SpacingCompositeImpl(shell, null);
	}

	/**
	 * Test to ensure that {@link SpacingCompositeImpl} constructor works correctly
	 * with good input and that a new {@link SpacingComposite} object was created.
	 */
	@Test
	public void testSpacingCompositeConstructorSuccess() {
		testSpacingCompositeImpl = new SpacingCompositeImpl(shell, display);
		assertThat(testSpacingCompositeImpl, instanceOf(SpacingComposite.class));
	}

	/**
	 * Test to ensure that the labels are enabled by default when a new
	 * {@link SpacingCompositeImpl} is constructed.
	 */
	@Test
	public void testLabelsAreEnabledByDefault() {
		testSpacingCompositeImpl = new SpacingCompositeImpl(shell, display);
		assertTrue(testSpacingCompositeImpl.getPlaceHolderLabel().isEnabled());
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

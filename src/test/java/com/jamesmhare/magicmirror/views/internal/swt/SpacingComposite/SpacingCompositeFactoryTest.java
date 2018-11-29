package com.jamesmhare.magicmirror.views.internal.swt.SpacingComposite;

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
 * Test for the {@link SpacingCompositeFactory} class.
 * 
 * @author James Hare
 *
 */
public class SpacingCompositeFactoryTest {
	private Shell shell;
	private Display display;

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
	 * {@link SpacingCompositeFactory} if parent {@link Composite} is {@link Null}.
	 */
	@Test
	public void testSpacingCompositeFactoryNullParentCompositeThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(SpacingCompositeImplConstants.COMPOSITE_NULL_ERROR_MESSAGE);
		SpacingCompositeFactory spacingCompositeFactory = new SpacingCompositeFactory();
		spacingCompositeFactory.createSpacingComposite(null, display);
	}

	/**
	 * Test to ensure that {@link IllegalArgumentException} is thrown from
	 * {@link SpacingCompositeFactory} if parent {@link Display} is {@link Null}.
	 */
	@Test
	public void testSpacingCompositeFactoryNullDisplayThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(SpacingCompositeImplConstants.DISPLAY_NULL_ERROR_MESSAGE);
		SpacingCompositeFactory spacingCompositeFactory = new SpacingCompositeFactory();
		spacingCompositeFactory.createSpacingComposite(shell, null);
	}

	/**
	 * Test to ensure that {@link SpacingCompositeFactory} works correctly with good
	 * input and that a new {@link SpacingComposite} object was created.
	 */
	@Test
	public void testSpacingCompositeFactorySuccess() {
		SpacingCompositeFactory spacingCompositeFactory = new SpacingCompositeFactory();
		SpacingComposite testSpacingComposite = spacingCompositeFactory.createSpacingComposite(shell, display);
		assertThat(testSpacingComposite, instanceOf(SpacingComposite.class));
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

/**
 * @author Stanko 16.06.2014.
 * EditPasteAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class EditPasteAction extends GDAction {
	EditPasteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(SMALL_ICON, loadIcon("editpaste.png"));
		putValue(NAME, "Paste");
		putValue(SHORT_DESCRIPTION, "Paste");
		setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		((DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame())).paste();
	}
}

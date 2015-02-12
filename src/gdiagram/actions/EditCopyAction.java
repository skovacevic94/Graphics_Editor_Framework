/**
 * @author Stanko 16.06.2014.
 * EditCopyAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.DiagramElementsSelection;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class EditCopyAction extends GDAction {

	EditCopyAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(SMALL_ICON, loadIcon("editcopy.png"));
		putValue(NAME, "Copy");
		putValue(SHORT_DESCRIPTION, "Copy");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(!(((DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame())).getDiagram().getSelModel().getSelectionListSize()==0)){
			MainFrame.getInstance().setPasteCounter(0);
			DiagramElementsSelection contents = new DiagramElementsSelection (((DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame())).getDiagram().getSelModel().getSelectionList());
			MainFrame.getInstance().getClipboard().setContents (contents, MainFrame.getInstance());
		
			MainFrame.getInstance().getActionManager().getPasteAction().setEnabled(true);
		}
		
	}
}

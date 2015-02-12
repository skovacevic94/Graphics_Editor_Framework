/**
 * @author Stanko 16.06.2014.
 * EditCutAction.java
 */
package gdiagram.actions;

import gdiagram.command.DeleteCommand;
import gdiagram.core.MainFrame;
import gdiagram.model.DiagramElementsSelection;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class EditCutAction extends GDAction {

	EditCutAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(SMALL_ICON, loadIcon("editcut.png"));
		putValue(NAME, "Cut");
		putValue(SHORT_DESCRIPTION, "Cut");
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(!(((DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame())).getDiagram().getSelModel().getSelectionListSize()==0)){
			DiagramElementsSelection contents = new DiagramElementsSelection ((ArrayList<DiagramElement>)((DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame())).getDiagram().getSelModel().getSelectionList().clone());
			MainFrame.getInstance().getClipboard().setContents(contents, MainFrame.getInstance());
			
			DiagramView view= (DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame();
    		view.getCommandManager().addCommand(new DeleteCommand(view));	
    		
    		MainFrame.getInstance().getActionManager().getPasteAction().setEnabled(true);
		}
	}

}

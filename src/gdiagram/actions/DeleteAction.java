/**
 * @author Stanko 28.04.2014.
 * DeleteAction.java
 */
package gdiagram.actions;

import gdiagram.actions.strategy.DeleteActionStrategy;
import gdiagram.actions.strategy.DeleteDiagramStrategy;
import gdiagram.actions.strategy.DeleteElementStrategy;
import gdiagram.actions.strategy.DeleteProjectStrategy;
import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.model.Workspace;
import gdiagram.model.elements.DiagramElement;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class DeleteAction extends GDAction{
	
	private DeleteActionStrategy deleteStrategy;
	
	public DeleteAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_DELETE, 0));
		putValue(SMALL_ICON, loadIcon("delete.png"));
		putValue(NAME, "Delete");
		putValue(SHORT_DESCRIPTION, "Delete project or diagram");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		TreePath tPath = MainFrame.getInstance().getWorkspaceTree().getSelectionPath(); 
		Object selected = tPath.getLastPathComponent();
		String typeMsg = "";
		
		if(MainFrame.getInstance().getWorkspaceTree().getSelectionCount() > 1)
		{
			typeMsg = "selected elements?";
			deleteStrategy = new DeleteElementStrategy();
		}
		else if(selected instanceof Workspace)
			return;
		else if(selected instanceof Project) {
			typeMsg = "project: " + (Project)selected + "?";
			deleteStrategy = new DeleteProjectStrategy();
		}
		else if(selected instanceof Diagram) {
			typeMsg = "diagram: " + (Diagram)selected + "?";
			deleteStrategy = new DeleteDiagramStrategy();
		}
		else if(selected instanceof DiagramElement) {
			typeMsg = "element: " + (DiagramElement)selected + "?";
			deleteStrategy = new DeleteElementStrategy();
		}
		
		int	answer = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
		            "Are you sure you want to delete " + typeMsg, "Delete " + typeMsg.substring(0, 7), 
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (answer == JOptionPane.YES_OPTION){
		    	deleteStrategy.execute(selected);
		    }
	}

}

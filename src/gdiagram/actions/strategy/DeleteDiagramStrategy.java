/**
 * @author Stanko 28.04.2014.
 * DeleteDiagramStrategy.java
 */
package gdiagram.actions.strategy;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.view.DiagramView;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
public class DeleteDiagramStrategy implements DeleteActionStrategy{

	@Override
	public void execute(Object selected) {
		// TODO Auto-generated method stub
		Diagram diagram = (Diagram)selected;
		Project project = (Project)diagram.getParent();
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		for(JInternalFrame frame : frames) 
			if(((DiagramView)frame).getDiagram().equals(diagram)) 
				frame.dispose();
		TreePath tp = new TreePath(diagram.getPath());
		MainFrame.getInstance().getWorkspaceTree().removeSelectionPath(tp);
		project.remove(diagram);
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}

}

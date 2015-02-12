/**
 * @author Stanko 28.04.2014.
 * DeleteProjectStrategy.java
 */
package gdiagram.actions.strategy;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.model.Workspace;
import gdiagram.view.DiagramView;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
public class DeleteProjectStrategy implements DeleteActionStrategy{

	@Override
	public void execute(Object selected) {
		Project project = (Project)selected;
		for(int i=0; i<project.getChildCount(); i++) {
			Diagram diagram = (Diagram)project.getChildAt(i);
			JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
			for(JInternalFrame frame : frames) 
				if(((DiagramView)frame).getDiagram().equals(diagram)) 
					frame.dispose();
		}
		Workspace workspace = ((Workspace)(MainFrame.getInstance().getWorkspaceModel().getRoot()));
		
		TreePath tp = new TreePath(project.getPath());
		MainFrame.getInstance().getWorkspaceTree().removeSelectionPath(tp);
		
		workspace.remove(project);

		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}

}

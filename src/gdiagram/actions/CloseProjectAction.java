/**
 * @author Stanko 27.04.2014.
 * CloseProjectAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class CloseProjectAction extends GDAction{

	public CloseProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F4, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("clProj.png"));
		putValue(NAME, "Close project");
		putValue(SHORT_DESCRIPTION, "Close project");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object selected = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		if(!(selected instanceof Project) && !(selected instanceof Diagram))
			return;
		
		Project project;
		if(selected instanceof Diagram) {
			project = ((Project)((Diagram) selected).getParent());
		}
		else
			project = (Project)selected;
		
		for(int i=0; i<project.getChildCount(); i++) {
			Diagram diagram = (Diagram)project.getChildAt(i);
			JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
			for(JInternalFrame frame : frames) {
				if(((DiagramView)frame).getDiagram().equals(diagram))
					frame.dispose();
			}
			MainFrame.getInstance().getWorkspaceTree().collapsePath(new TreePath(project.getPath()));
		}
	}
}

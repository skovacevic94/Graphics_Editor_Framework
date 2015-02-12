/**
 * @author Stanko 27.04.2014.
 * CloseDiagramAction.java
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
public class CloseDiagramAction extends GDAction{

	public CloseDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F4, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("clDiag.png"));
		putValue(NAME, "Close diagram");
		putValue(SHORT_DESCRIPTION, "Close diagram");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object selected = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		boolean collapseProject = true;
		
		if(!(selected instanceof Diagram))
			return;
		
		Diagram diagram = (Diagram)selected;
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		for(JInternalFrame frame : frames) {
			DiagramView view = (DiagramView)frame;
			if(view.getDiagram().equals(diagram))
				view.dispose();
			if(view.isVisible())
				collapseProject = false;
		}
		
		if(!collapseProject) return;
		Project project = (Project)diagram.getParent();
		MainFrame.getInstance().getWorkspaceTree().setSelectionPath(new TreePath(project.getPath()));
		MainFrame.getInstance().getWorkspaceTree().collapsePath(new TreePath(project.getPath()));
	}

}

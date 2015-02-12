/**
 * @author Stanko 27.04.2014.
 * CloseAllAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Project;
import gdiagram.model.Workspace;

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
public class CloseAllAction extends GDAction{

	public CloseAllAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F4, ActionEvent.SHIFT_MASK));
		putValue(SMALL_ICON, loadIcon("clAll.png"));
		putValue(NAME, "Close all");
		putValue(SHORT_DESCRIPTION, "Close all diagrams");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Workspace root = (Workspace) MainFrame.getInstance().getWorkspaceModel().getRoot();
		MainFrame.getInstance().getWorkspaceTree().setSelectionPath(new TreePath(root.getPath()));
		for(int i=0; i<root.getChildCount(); i++) {
			Project project = (Project)root.getChildAt(i);
			for(int j=0; j<project.getChildCount(); j++) {
				JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
				for(JInternalFrame frame : frames)
					frame.dispose();
			}
			MainFrame.getInstance().getWorkspaceTree().collapsePath(new TreePath(project.getPath()));
		}
	}
}

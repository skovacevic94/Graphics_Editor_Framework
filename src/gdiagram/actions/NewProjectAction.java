/**
 * @author Stanko 27.04.2014.
 * NewProjectAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class NewProjectAction extends GDAction{
	
	public NewProjectAction() {
		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
		        KeyEvent.VK_J, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("nproject.png"));
		putValue(NAME, "New project");
		putValue(SHORT_DESCRIPTION, "New project");	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Project project = new Project("");
		MainFrame.getInstance().getWorkspaceTree().addProject(project);
		Diagram diagram = new Diagram("");
		project.add(diagram);
		DiagramView dView = new DiagramView();
		dView.setDiagram(diagram);
		MainFrame.getInstance().getDesktopPane().add(dView);
		try {
			dView.setSelected(true);
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

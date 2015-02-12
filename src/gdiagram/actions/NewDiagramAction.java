/**
 * @author Stanko 27.04.2014.
 * NewDiagramAction.java
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
public class NewDiagramAction extends GDAction{
	
	public NewDiagramAction() {
		putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("ndiagram.png"));
		putValue(NAME, "New diagram");
		putValue(SHORT_DESCRIPTION, "New diagram");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object selected = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if(selected==null)
			return;
		else if(selected instanceof Project || selected instanceof Diagram) {
			Diagram diagram = new Diagram("New Diagram");
			if(selected instanceof Diagram) {
				Project project = ((Project)((Diagram) selected).getParent());
				project.add(diagram);
			}
			else
				((Project)selected).add(diagram);
			
			DiagramView view = new DiagramView();
			view.setDiagram(diagram);
			MainFrame.getInstance().getDesktopPane().add(view);
			
			try {
				view.setSelected(true);
			} catch(PropertyVetoException exc) {
				exc.printStackTrace();
			}
		}
	}

}

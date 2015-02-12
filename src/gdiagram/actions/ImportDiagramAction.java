/**
 * @author Stanko 27.05.2014.
 * OpenDiagramAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.ElementFolder;
import gdiagram.model.Project;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class ImportDiagramAction extends GDAction{

	public ImportDiagramAction() {
		putValue(SMALL_ICON, loadIcon("importD.png"));
		putValue(NAME, "Import diagram");
		putValue(SHORT_DESCRIPTION, "Import diagram");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new DiagramFileFilter());
		
		if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				Diagram diagram = (Diagram) is.readObject();
				is.close();
				
				String dName = diagram.getDiagramName();
				
				Object tn = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
				
				Project pm = null;
				
				if (tn instanceof Project) {
					pm = (Project)tn;
				} else if (tn instanceof Diagram) {
					pm = (Project)((Diagram)tn).getParent();
				} else if (tn instanceof ElementFolder) {
					pm = (Project)((Diagram)((ElementFolder)tn).getParent()).getParent();
				} else if (tn instanceof DiagramElement){
					pm = (Project)((Diagram)((ElementFolder)((DiagramElement)tn).getParent()).getParent()).getParent();
				} else {
					return;
				}
				
				pm.add(diagram);
				
				while(!pm.isUnique(dName)) 
					dName = JOptionPane.showInputDialog(MainFrame.getInstance(), "Enter a new name as current one is already taken:", "Rename", JOptionPane.DEFAULT_OPTION);
				diagram.setDiagramName(dName);
				
				pm.setChanged(true);
				DiagramView dv = new DiagramView();
				dv.setDiagram(diagram);
				MainFrame.getInstance().getDesktopPane().add(dv);
				
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			return;
		}
	}

}

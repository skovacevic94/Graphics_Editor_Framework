/**
 * @author Stanko 27.04.2014.
 * WorkspaceTree.java
 */
package gdiagram.gui;

import gdiagram.controller.WorkspaceController;
import gdiagram.controller.WorkspaceMouseLisener;
import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.model.WorkspaceModel;
import gdiagram.view.WorkspaceTreeCellRenderer;
import gdiagram.view.WorkspaceTreeEditor;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class WorkspaceTree extends JTree{
	public WorkspaceTree() {
		addMouseListener(new WorkspaceMouseLisener());
		addTreeSelectionListener(new WorkspaceController());
		setCellEditor(new WorkspaceTreeEditor(this, new DefaultTreeCellRenderer()));
		setCellRenderer(new WorkspaceTreeCellRenderer());
		setEditable(true);
	}
	
	public void addProject(Project project) {
		((WorkspaceModel)getModel()).addProject(project);
	}
	
	public void valueChanged(TreeSelectionEvent arg0) {
		TreePath path = arg0.getPath();
		for(int i=0; i<path.getPathCount(); i++){
			if(path.getPathComponent(i) instanceof Diagram){
				Diagram d=(Diagram)path.getPathComponent(i);
				
				JInternalFrame[] jif= MainFrame.getInstance().getDesktopPane().getAllFrames();
				for (int j=0;j<jif.length;j++){
					
					if (jif[j].getName().equals(d.getDiagramName())){
						try {
							
							jif[j].setSelected(true);
							
						} catch (PropertyVetoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					
				}
				
				break;
			}
		}
		
	}
	public Project getCurrentProject() {
		TreePath path = getSelectionPath();
		for(int i=0; i<path.getPathCount(); i++){
			if(path.getPathComponent(i) instanceof Project){
				return (Project)path.getPathComponent(i);
			}
		}
		return null;
	}
}

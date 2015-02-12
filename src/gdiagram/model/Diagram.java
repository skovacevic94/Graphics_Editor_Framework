/**
 * @author Stanko 27.04.2014.
 * Diagram.java
 */
package gdiagram.model;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.io.File;
import java.io.Serializable;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class Diagram extends DefaultMutableTreeNode implements Serializable{

	private String 	diagramName;
	private DiagramModel model;
	
	private File diagramFile;
	
	private transient boolean changed; 
	
	private DiagramSelectionModel selModel;
	
	public Diagram(String diagramName) {
		this.diagramName = diagramName;
		model = new DiagramModel(this);
		diagramFile = null;
	}
	
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ElementFolder getFolder(String folderName) {
		for(int i=0; i<getChildCount(); i++) {
			if(((ElementFolder)getChildAt(i)).getFolderName().equals(folderName))
				return (ElementFolder)getChildAt(i);
		}
		return null;
	}
	
	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * @return the diagramName
	 */
	public String getDiagramName() {
		return diagramName;
	}

	/**
	 * @param diagramName the diagramName to set
	 */
	public void setDiagramName(String diagramName) {
		Project project = (Project) getParent();
		if(project.isUnique(diagramName))
			this.diagramName = diagramName;
		else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Name is already taken. Please select another name.");
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		for(JInternalFrame frame : frames){
			if(((DiagramView)frame).getDiagram().equals(this)) {
				frame.setTitle(this.diagramName);
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return diagramName;
	}

	/**
	 * @return the model
	 */
	public DiagramModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(DiagramModel model) {
		this.model = model;
	}

	/**
	 * @return the selModel
	 */
	public DiagramSelectionModel getSelModel() {
		if(selModel == null)
			selModel = new DiagramSelectionModel();
		return selModel;
	}

	/**
	 * @param selModel the selModel to set
	 */
	public void setSelModel(DiagramSelectionModel selModel) {
		this.selModel = selModel;
	}

	/**
	 * @return the diagramFile
	 */
	public File getDiagramFile() {
		return diagramFile;
	}

	/**
	 * @param diagramFile the diagramFile to set
	 */
	public void setDiagramFile(File diagramFile) {
		this.diagramFile = diagramFile;
	}
}

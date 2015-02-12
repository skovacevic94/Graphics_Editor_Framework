package gdiagram.model;

import gdiagram.core.MainFrame;
import gdiagram.events.UpdateEvent;
import gdiagram.events.UpdateListener;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

@SuppressWarnings("serial")
public class Project extends DefaultMutableTreeNode implements UpdateListener, Serializable{

	private String 	projectName;
	private String  orgProjectName;
	private transient boolean changed; 
	private File projectFolder;

	private ArrayList<File> diagramFiles = new ArrayList<File>();
	
	public Project(String projectName) {
		this.projectName = projectName;
		this.orgProjectName = projectName;
		this.changed = false;
		this.projectFolder = null;
	}
	
	public boolean isUnique(String name) {
		for(Object child : children) 
			if(((Diagram)child).getDiagramName().equals(name))
				return false;
		return true;
	}
	
	public void addDiagramFile(File f) {
		diagramFiles.add(f);
	}

	/* (non-Javadoc)
	 * @see gdiagram.events.UpdateListener#updatePerformed(gdiagram.events.UpdateEvent)
	 */
	@Override
	public void updatePerformed(UpdateEvent e) {
		// TODO Auto-generated method stub
		setChanged(true);
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		// TODO Auto-generated method stub
		super.add(newChild);
		((Diagram)newChild).getModel().addUpdateListener(this);
		for(int i=1; i<=getChildCount(); i++) {
			Project project = ((Project)newChild.getParent());
			if(isUnique(project.getOrgProjectName() + " - Diagram " + i)) {
				((Diagram)newChild).setDiagramName(project.getOrgProjectName() + " - Diagram " + i);
				break;
			}
		}
		
		setChanged(true);
		
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}
	
	
	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		if (this.changed!=changed){
		     this.changed=changed;
		     if(changed) 
		    	 projectName = orgProjectName + " [*]";
		     else {
		    	 projectName = orgProjectName;
		     }
		     SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
		}
	}

	/**
	 * @return the projectFile
	 */
	public File getProjectFolder() {
		return projectFolder;
	}

	/**
	 * @param projectFile the projectFile to set
	 */
	public void setProjectFolder(File projectFile) {
		this.projectFolder = projectFile;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		Workspace root = (Workspace) MainFrame.getInstance().getWorkspaceModel().getRoot();
		if(root.isUnique(projectName)) {
			this.projectName = projectName;
			this.orgProjectName = projectName;
		}
		else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Name is already taken. Please select another name.");
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}
	
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return projectName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the orgProjectName
	 */
	public String getOrgProjectName() {
		return orgProjectName;
	}

	/**
	 * @return the diagramFiles
	 */
	public ArrayList<File> getDiagramFiles() {
		return diagramFiles;
	}
}

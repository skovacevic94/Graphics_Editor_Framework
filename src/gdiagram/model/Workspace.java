/**
 * @author Stanko 27.04.2014.
 * Workspace.java
 */
package gdiagram.model;

import java.io.File;
import java.util.ArrayList;

import gdiagram.core.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class Workspace extends DefaultMutableTreeNode{
	
	private ArrayList<File> projectFiles = new ArrayList<File>();
	
	public boolean isUnique(String name) {
		if(children.size() == 0)
			return true;
		
		for(Object child : children) {
			if(((Project)child).getOrgProjectName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public void addProjectFile(File f) {
		projectFiles.add(f);
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		// TODO Auto-generated method stub
		super.add(newChild);
		
		String projName = ((Project)newChild).getOrgProjectName();
		for(int i=1; i<=getChildCount(); i++) {
			if(isUnique("Project " + i)) {
				((Project)newChild).setProjectName("Project " + i);
				break;
			}
		}
		
		System.out.println("Project name:" + projName);
		System.out.println(projName.length() == 0);
		System.out.println(!isUnique(projName));
		if(projName.length() > 0 && isUnique(projName))
			((Project)newChild).setProjectName(projName);
		
		if(((Project)newChild).getProjectFolder() != null)
			projectFiles.add(((Project)newChild).getProjectFolder());
		
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}
	
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Workspace";
	}

	/**
	 * @return the projectFiles
	 */
	public ArrayList<File> getProjectFiles() {
		return projectFiles;
	}
}

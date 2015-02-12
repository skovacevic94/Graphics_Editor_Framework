/**
 * @author Stanko 27.04.2014.
 * WorkspaceModel.java
 */
package gdiagram.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class WorkspaceModel extends DefaultTreeModel{
	public WorkspaceModel() {
		super(new Workspace());
	}
	
	public WorkspaceModel(Workspace w) {
		super(w);
	}
	
	public void addProject(Project project) {
		((Workspace)getRoot()).add((MutableTreeNode)project);
	}
}

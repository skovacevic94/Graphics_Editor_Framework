/**
 * @author Stanko 27.04.2014.
 * WorkspaceTreeEditor.java
 */
package gdiagram.view;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.model.Workspace;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author Stanko
 *
 */
public class WorkspaceTreeEditor extends DefaultTreeCellEditor{

	private Object 		item = null;
	private JTextField 	editField = null;
	
	public WorkspaceTreeEditor(JTree tree, DefaultTreeCellRenderer tcRenderer) {
		super(tree, tcRenderer);
	}
	
	public Component getTreeCellEditorComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row)
	{
		item = value;
		
		editField = new JTextField(value.toString());
		editField.addActionListener(this);
		
		return editField;
	}
	
	public boolean isCellEditable(EventObject event) {
		if(event instanceof MouseEvent) {
			if(MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Workspace)
				return false;
			if(((MouseEvent)event).getClickCount()==3)
				return true;
			return false;
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent event) {
		if(item instanceof Project)
			((Project)item).setProjectName(event.getActionCommand());
		else
			((Diagram)item).setDiagramName(event.getActionCommand());
	}
}

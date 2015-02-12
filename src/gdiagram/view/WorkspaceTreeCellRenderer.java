/**
 * @author Stanko 27.04.2014.
 * WorkspaceTreeCellRenderer.java
 */
package gdiagram.view;

import gdiagram.model.Diagram;
import gdiagram.model.Project;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class WorkspaceTreeCellRenderer extends DefaultTreeCellRenderer{
	public WorkspaceTreeCellRenderer() {
		
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// TODO Auto-generated method stub
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		//LATER WE WILL HAVE ELEMENTS TO CHECK TOO
		if(!(value instanceof Diagram) && !(value instanceof Project)) 
			return this;
		String	imgName = "";
				
		if(value instanceof Diagram) 
			imgName = "diagIcon.png";
		else if(value instanceof Project)
			imgName = "projIcon.png";
		URL imageURL = getClass().getResource("/gdiagram/res/" + imgName);
		Icon icon = null;
		if(imageURL != null) 
			icon = new ImageIcon(imageURL);
		setIcon(icon);
		return this;
	}
}

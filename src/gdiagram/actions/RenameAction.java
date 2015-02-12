/**
 * @author Stanko 29.04.2014.
 * RenameAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.Project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class RenameAction extends GDAction{

	public RenameAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("rename.png"));
		putValue(NAME, "Rename");
		putValue(SHORT_DESCRIPTION, "Rename project or diagram");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String newName = JOptionPane.showInputDialog(MainFrame.getInstance(), "Enter a new name:", "Rename", JOptionPane.DEFAULT_OPTION);
		if(newName == null)
			return;
		
		Object selected = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if(selected instanceof Project) 
			((Project)selected).setProjectName(newName);
		else if(selected instanceof Diagram) 
			((Diagram)selected).setDiagramName(newName);
	}
}

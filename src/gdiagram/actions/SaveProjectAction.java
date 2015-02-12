package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SaveProjectAction extends GDAction{
	

	public SaveProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("filesave.png"));
		putValue(NAME, "Save project");
		putValue(SHORT_DESCRIPTION, "Save project");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new ProjectFileFilter());
		Project project=MainFrame.getInstance().getWorkspaceTree().getCurrentProject();
		
		if (!project.isChanged()){
			return;
		}
		
		if (project.getProjectFolder() == null) {
			if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
				project.setProjectFolder(new File(jfc.getSelectedFile().toString() + ".gpf"));
			} else {
				return;
			}
		}
		
		try {
			ObjectOutputStream is = new ObjectOutputStream(new FileOutputStream(project.getProjectFolder()));
			is.writeObject(project);
			project.setChanged(false);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}

package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Project;
import gdiagram.model.Workspace;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class OpenProjectAction extends GDAction {

	public OpenProjectAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("fileopen.png"));
		putValue(NAME, "Open project");
		putValue(SHORT_DESCRIPTION, "Open project");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new ProjectFileFilter());
		
		if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				Project project = (Project) is.readObject();
				is.close();
				
				((Workspace)MainFrame.getInstance().getWorkspaceTree().getModel().getRoot()).add(project);
				((Workspace)MainFrame.getInstance().getWorkspaceTree().getModel().getRoot()).addProjectFile(project.getProjectFolder());
								
				project.setChanged(false);
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
	}
}

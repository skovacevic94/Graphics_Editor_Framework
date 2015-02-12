/**
 * @author Stanko 28.04.2014.
 * ExitAction.java
 */
package gdiagram.actions;

import gdiagram.controller.ExitLineListener;
import gdiagram.core.MainFrame;
import gdiagram.model.Project;
import gdiagram.model.Workspace;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class ExitAction extends GDAction{
	
	public ExitAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("exit.png"));
		putValue(NAME, "Exit");
		putValue(SHORT_DESCRIPTION, "Exit application");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean shouldSave = false;
		ArrayList<Project> projectsToSave = new ArrayList<Project>();
		Workspace workspace = (Workspace) MainFrame.getInstance().getWorkspaceModel().getRoot();
		for(int i=0; i<workspace.getChildCount(); i++) {
			Project project = (Project) workspace.getChildAt(i);
			if(project.isChanged()) {
				shouldSave = true;
				projectsToSave.add(project);
			}
		}
		
		int answer = 0;
		if(shouldSave) {
			answer = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
		            "You have unsaved work\nWould you like to save it?", "Exit best diagram editor", 
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(answer == JOptionPane.YES_OPTION) {
				ArrayList<Object> params = new ArrayList<Object>();
				for(int k=0; k<projectsToSave.size(); k++)
					params.add(new JCheckBox(projectsToSave.get(k).getOrgProjectName()));
				int saveAnswer = JOptionPane.showConfirmDialog(MainFrame.getInstance(), params.toArray(), "Select projects to save", JOptionPane.OK_CANCEL_OPTION);
				if(saveAnswer == JOptionPane.CANCEL_OPTION)
					playExit();
				else if(saveAnswer == JOptionPane.OK_OPTION) {
					for(int k=0; k<params.size(); k++) {
						JCheckBox cBox = (JCheckBox) params.get(k);
						if(cBox.isSelected()) {
							for(Project p : projectsToSave) {
								if(p.getOrgProjectName().equals(cBox.getText())) {
									TreePath tp = new TreePath(p.getPath());
									MainFrame.getInstance().getWorkspaceTree().setSelectionPath(tp);
									MainFrame.getInstance().getActionManager().getSaveProjectAction().actionPerformed(null);
								}
							}
						}
					}
					System.exit(0);
				}
			}
			else if(answer == JOptionPane.NO_OPTION)
				playExit();
		}
		else {
			answer = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
	            "Are you sure you want to quit? ", "Exit best diagram editor", 
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(answer == JOptionPane.YES_OPTION) {
				playExit();
			}
		}
	}

	private void playExit() {
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(
			          MainFrame.class.getResourceAsStream("/gdiagram/res/exitS.wav"));
			clip.open(inputStream);
			clip.addLineListener(new ExitLineListener());
			clip.start();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}

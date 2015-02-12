/**
 * @author Stanko 29.04.2014.
 * HelpAboutAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class HelpAboutAction extends GDAction {

	public HelpAboutAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		putValue(SMALL_ICON, loadIcon("null"));
		putValue(NAME, "About");
		putValue(SHORT_DESCRIPTION, "About this software");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JDialog about = MainFrame.getInstance().getAboutDialog();
		about.setLocationRelativeTo(MainFrame.getInstance());
		about.setVisible(true);
	}
}

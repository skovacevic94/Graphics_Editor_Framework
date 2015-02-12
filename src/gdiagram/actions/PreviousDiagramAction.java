/**
 * @author Stanko 27.04.2014.
 * PreviousDiagramAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class PreviousDiagramAction extends GDAction{

	public PreviousDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("previous.png"));
		putValue(NAME, "Previus diagram");
		putValue(SHORT_DESCRIPTION, "Previus diagram");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainFrame.getInstance().getDesktopPane().selectFrame(true);
	}

}

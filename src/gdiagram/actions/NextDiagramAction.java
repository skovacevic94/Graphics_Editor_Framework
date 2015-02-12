/**
 * @author Stanko 27.04.2014.
 * NextDiagramAction.java
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
public class NextDiagramAction extends GDAction{

	public NextDiagramAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_RIGHT, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("next.png"));
		putValue(NAME, "Next diagram");
		putValue(SHORT_DESCRIPTION, "Next diagram");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MainFrame.getInstance().getDesktopPane().selectFrame(false);
	}

}

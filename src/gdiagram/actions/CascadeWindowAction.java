/**
 * @author Stanko 27.04.2014.
 * CascadeWindowAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class CascadeWindowAction extends GDAction{
	
	public CascadeWindowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("cascade.png"));
		putValue(NAME, "Cascade");
		putValue(SHORT_DESCRIPTION, "Cascade windows");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int xOffset = 30;
		int yOffset = 30;
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		int i = frames.length;
		for(JInternalFrame frame : frames){
			if(frame.isVisible()){
				frame.setBounds(i  * xOffset, i * yOffset, 400, 400);
				i--;
			}
		}
	}
}

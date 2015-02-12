/**
 * @author Stanko 27.04.2014.
 * TileVWindowAction.java
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
public class TileHWindowAction extends GDAction{

	public TileHWindowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_V, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("tileh.png"));
		putValue(NAME, "Tile Horizontally");
		putValue(SHORT_DESCRIPTION, "Tile Horizontally");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		int deskHeight = MainFrame.getInstance().getDesktopPane().getHeight();
		int deskWidth  = MainFrame.getInstance().getDesktopPane().getWidth();
		int framesCnt = frames.length;
		
		int interFrameWidth = deskWidth/framesCnt;
		int iter = 0;
		for(JInternalFrame frame : frames) {
			frame.setLocation(iter*interFrameWidth, 0);
			frame.setSize(interFrameWidth , deskHeight);
			iter++;
		}
	}
	
}

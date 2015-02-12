/**
 * @author Stanko 27.04.2014.
 * TileHWindowAction.java
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
public class TileVWindowAction extends GDAction{

	public TileVWindowAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_H, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("tilev.png"));
		putValue(NAME, "Tile Vertically");
		putValue(SHORT_DESCRIPTION, "Tile Vertically");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
		int deskHeight = MainFrame.getInstance().getDesktopPane().getHeight();
		int deskWidth  = MainFrame.getInstance().getDesktopPane().getWidth();
		int framesCnt = frames.length;
		
		int interFrameHeight = deskHeight/framesCnt;
		int iter = 0;
		for(JInternalFrame frame : frames) {
			frame.setLocation(0, iter*interFrameHeight);
			frame.setSize(deskWidth , interFrameHeight);
			iter++;
		}
	}
	
}

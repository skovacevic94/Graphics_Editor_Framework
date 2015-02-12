/**
 * @author Stanko 14.06.2014.
 * ZoomOutAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class ZoomOutAction extends GDAction {

	public ZoomOutAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("zoomOut.png"));
		putValue(NAME, "Zoom out");
		putValue(SHORT_DESCRIPTION, "Zoom out");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DiagramView dView = (DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame());
		dView.zoomOut();
	}

}

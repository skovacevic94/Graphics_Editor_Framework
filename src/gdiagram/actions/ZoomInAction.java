/**
 * @author Stanko 14.06.2014.
 * ZoomInAction.java
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
public class ZoomInAction extends GDAction {

	public ZoomInAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("zoomIn.png"));
		putValue(NAME, "Zoom in");
		putValue(SHORT_DESCRIPTION, "Zoom in");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DiagramView dView = (DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame());
		dView.zoomIn();
	}

}

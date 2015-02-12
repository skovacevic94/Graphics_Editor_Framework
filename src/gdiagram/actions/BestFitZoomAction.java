/**
 * @author Stanko 14.06.2014.
 * BestFitZoomAction.java
 */
package gdiagram.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gdiagram.actions.GDAction;
import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class BestFitZoomAction extends GDAction {

	public BestFitZoomAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("bestFit.png"));
		putValue(NAME, "Best fit zoom");
		putValue(SHORT_DESCRIPTION, "Best fit zoom");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DiagramView dView = (DiagramView)(MainFrame.getInstance().getDesktopPane().getSelectedFrame());
		dView.bestFitZoom();
	}

}

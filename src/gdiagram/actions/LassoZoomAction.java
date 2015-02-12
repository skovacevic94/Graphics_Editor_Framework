/**
 * @author Stanko 14.06.2014.
 * LassoZoomAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class LassoZoomAction extends GDAction{

	public LassoZoomAction() {
		putValue(SMALL_ICON, loadIcon("lassoZoom.png"));
		putValue(NAME, "Lasso zoom");
		putValue(SHORT_DESCRIPTION, "Lasso zoom");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		((DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame()).getStateManager().setLassoZoomState();
	}

}

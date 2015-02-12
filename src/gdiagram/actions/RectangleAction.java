/**
 * @author Stanko 27.05.2014.
 * RectangleAction.java
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
public class RectangleAction extends GDAction{

	public RectangleAction() {
		putValue(SMALL_ICON, loadIcon("rectangle.png"));
		putValue(SHORT_DESCRIPTION, "Rectangle");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		((DiagramView) MainFrame.getInstance().getDesktopPane()
				.getSelectedFrame()).startRectangleState();
	}

}

/**
 * @author Stanko 27.05.2014.
 * CircleAction.java
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
public class CircleAction extends GDAction{

	public CircleAction() {
		putValue(SMALL_ICON, loadIcon("circle.png"));
		putValue(SHORT_DESCRIPTION, "Circle");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		((DiagramView) MainFrame.getInstance().getDesktopPane()
				.getSelectedFrame()).startCircleState();
	}

}

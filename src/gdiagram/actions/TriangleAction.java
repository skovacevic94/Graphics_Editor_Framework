package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class TriangleAction extends GDAction {

	public TriangleAction() {
		putValue(SMALL_ICON, loadIcon("triangle.png"));
		putValue(SHORT_DESCRIPTION, "Triangle");
	}

	public void actionPerformed(ActionEvent arg0) {
		((DiagramView) MainFrame.getInstance().getDesktopPane()
				.getSelectedFrame()).startTrigState();
	}

}

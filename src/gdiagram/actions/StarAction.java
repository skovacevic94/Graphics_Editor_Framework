package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class StarAction extends GDAction {

	public StarAction() {
		putValue(SMALL_ICON, loadIcon("star.png"));
		putValue(SHORT_DESCRIPTION, "Star");
	}

	public void actionPerformed(ActionEvent arg0) {
		((DiagramView) MainFrame.getInstance().getDesktopPane()
				.getSelectedFrame()).startStarState();
	}

}

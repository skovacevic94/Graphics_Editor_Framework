package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SelectAction extends GDAction {

	public SelectAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("hand.png"));
		// putValue(NAME, "Select mode");
		putValue(SHORT_DESCRIPTION, "Select mode");
	}

	public void actionPerformed(ActionEvent arg0) {
		((DiagramView) MainFrame.getInstance().getDesktopPane()
				.getSelectedFrame()).startSelectState();
	}

}

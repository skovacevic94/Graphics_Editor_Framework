package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class LinkAction extends GDAction {

	public LinkAction() {

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("link.png"));
		putValue(SHORT_DESCRIPTION, "Link");
	}

	public void actionPerformed(ActionEvent arg0) {
		((DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame())
				.startLinkState();
	}

}

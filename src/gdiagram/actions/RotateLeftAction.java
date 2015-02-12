/**
 * @author Stanko 15.06.2014.
 * RotateLeftAction.java
 */
package gdiagram.actions;

import gdiagram.command.RotateCommand;
import gdiagram.core.MainFrame;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class RotateLeftAction extends GDAction {

	public RotateLeftAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("rotateL.png"));
		putValue(NAME, "Rotate left");
		putValue(SHORT_DESCRIPTION, "Rotate left");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DiagramView med = (DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame();
		if(med != null) {
			if(med.getDiagram().getSelModel().getSelectionListSize() == 1 &&
					(med.getDiagram().getSelModel().getElementFromSelectionListAt(0) instanceof DiagramDevice))
				med.getCommandManager().addCommand(new RotateCommand(med, RotateCommand.LEFT));
		}
	}

}

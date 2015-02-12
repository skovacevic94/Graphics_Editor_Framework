/**
 * @author Stanko 26.05.2014.
 * SelectAll.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class SelectAllAction extends GDAction {

	public SelectAllAction() {
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("null"));
		putValue(NAME, "Select All");
		putValue(SHORT_DESCRIPTION, "Select All Elements on Diagram");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DiagramView dView = null;
		Diagram diagram = null;

		dView = (DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame();
		if (dView == null)
			return;
		diagram = dView.getDiagram();
		MainFrame.getInstance().getWorkspaceTree().clearSelection();

		/*
		 * Ovdje sam mogao i
		 * diagram.addToSElectionList(diagram.getModel().getDiagramElements())
		 * O(n) je u oba slucaja, a ova metoda mi ima implementiranu
		 * funkcionalnost za tacke 13 i 14
		 */
		for (DiagramElement element : diagram.getModel().getDiagramElements())
			diagram.getSelModel().addToSelectionList(element);
	}
}

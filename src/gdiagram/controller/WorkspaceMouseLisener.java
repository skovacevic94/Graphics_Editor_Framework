/**
 * @author Stanko 28.04.2014.
 * PopupListener.java
 */
package gdiagram.controller;

import gdiagram.core.MainFrame;
import gdiagram.gui.popup.PopupDiagramStrategy;
import gdiagram.gui.popup.PopupElementStrategy;
import gdiagram.gui.popup.PopupMenu;
import gdiagram.gui.popup.PopupProjectStrategy;
import gdiagram.model.Diagram;
import gdiagram.model.Project;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 * 
 */
public class WorkspaceMouseLisener extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isRightMouseButton(e)) {
			int row = MainFrame.getInstance().getWorkspaceTree()
					.getRowForLocation(e.getX(), e.getY());
			if (row > -1) {
				TreePath tPath = MainFrame.getInstance().getWorkspaceTree()
						.getPathForLocation(e.getX(), e.getY());
				Object selected = tPath.getLastPathComponent();

				PopupMenu popupMenu = new PopupMenu();
				if (selected instanceof Project)
					popupMenu.setInitializer(new PopupProjectStrategy());
				else if (selected instanceof Diagram)
					popupMenu.setInitializer(new PopupDiagramStrategy());
				else if(selected instanceof DiagramElement)
					popupMenu.setInitializer(new PopupElementStrategy((DiagramElement)selected));
				else
					return;
				
				popupMenu.init();
				MainFrame.getInstance().getWorkspaceTree().setSelectionPath(tPath);
				popupMenu.show(MainFrame.getInstance().getWorkspaceTree(),
						e.getX(), e.getY());
			}
		} else if(e.getClickCount() == 2) {
			int row = MainFrame.getInstance().getWorkspaceTree()
					.getRowForLocation(e.getX(), e.getY());
			if (row > -1) {
				Object selected = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
				if(selected instanceof Diagram) {
					Diagram diagram = (Diagram)selected;
					JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
					for(JInternalFrame frame : frames)
						if(((DiagramView)frame).getDiagram().equals(diagram))
							return;
					try {
						DiagramView dView = new DiagramView();
						dView.setDiagram(diagram);
						MainFrame.getInstance().getDesktopPane().add(dView);
						dView.setSelected(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

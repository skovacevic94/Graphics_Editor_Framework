/**
 * @author Stanko 28.04.2014.
 * DiagramListener.java
 */
package gdiagram.controller;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.view.DiagramView;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
public class DiagramListener extends InternalFrameAdapter{
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		DiagramView dView = (DiagramView)e.getInternalFrame();
		Diagram diagram = dView.getDiagram();
		MainFrame.getInstance().getWorkspaceTree().setSelectionPath(new TreePath(diagram.getPath()));
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		DiagramView.decFrameCount();
	}	
}

/**
 * @author Stanko 27.05.2014.
 * PopupAddElementStrategy.java
 */
package gdiagram.gui.popup;

import gdiagram.core.MainFrame;
import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.RectangleElement;
import gdiagram.model.elements.StarElement;
import gdiagram.model.elements.TriangleElement;
import gdiagram.view.DiagramView;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;

/**
 * @author Stanko
 *
 */
public class PopupAddElementStrategy implements PopupStrategy{

	private Point2D point;
	
	public PopupAddElementStrategy(Point2D point) {
		this.point = point;
	}
	
	@SuppressWarnings("serial")
	private class AddStarAction extends AbstractAction {
		public AddStarAction() {
			putValue(NAME, "Add Star");
			putValue(SHORT_DESCRIPTION, "Add Star");
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DiagramView med = (DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame();
			
			if (med.getDiagram().getModel().getElementAtPosition((Point)point)==-1){
				 DiagramDevice device = StarElement.createDefault(point,med.getDiagram().getModel().getElementsCount(), med.getDiagram());
				 med.getDiagram().getModel().addDiagramElement(device);
			 }
		}
		
	}
	
	@SuppressWarnings("serial")
	private class AddTriangleAction extends AbstractAction {
		public AddTriangleAction() {
			putValue(NAME, "Add Triangle");
			putValue(SHORT_DESCRIPTION, "Add Triangle");
		}
	
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DiagramView med = (DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame();
			
			if (med.getDiagram().getModel().getElementAtPosition((Point)point)==-1){
				 DiagramDevice device = TriangleElement.createDefault(point,med.getDiagram().getModel().getElementsCount(), med.getDiagram());
				 med.getDiagram().getModel().addDiagramElement(device);
			 }
		}
		
	}
	
	@SuppressWarnings("serial")
	private class AddRectangleAction extends AbstractAction {
		public AddRectangleAction() {
			putValue(NAME, "Add Rectangle");
			putValue(SHORT_DESCRIPTION, "Add Rectangle");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			DiagramView med = (DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame();
			
			if (med.getDiagram().getModel().getElementAtPosition((Point)point)==-1){
				 DiagramDevice device = RectangleElement.createDefault(point,med.getDiagram().getModel().getElementsCount(), med.getDiagram());
				 med.getDiagram().getModel().addDiagramElement(device);
			 }
		}
	}
	
	@SuppressWarnings("serial")
	private class AddCircleAction extends AbstractAction {
		public AddCircleAction() {
			putValue(NAME, "Add Circle");
			putValue(SHORT_DESCRIPTION, "Add Circle");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			DiagramView med = (DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame();
			
			if (med.getDiagram().getModel().getElementAtPosition((Point)point)==-1){
				 DiagramDevice device = CircleElement.createDefault(point,med.getDiagram().getModel().getElementsCount(), med.getDiagram());
				 med.getDiagram().getModel().addDiagramElement(device);
			 }
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.gui.popup.PopupStrategy#execute(gdiagram.gui.popup.PopupMenu)
	 */
	@Override
	public void execute(PopupMenu popupMenu) {
		// TODO Auto-generated method stub

		popupMenu.add(new AddStarAction());
		popupMenu.add(new AddTriangleAction());
		popupMenu.add(new AddRectangleAction());
		popupMenu.add(new AddCircleAction());
	}

}

/**
 * 
 */
package gdiagram.state;

import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class LassoState extends State {

	Rectangle2D rect = new Rectangle2D.Double();

	private DiagramView med;

	public LassoState(DiagramView md) {
		med = md;
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.state.State#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!e.isControlDown()) {
			med.getDiagram().getSelModel().removeAllFromSelectionList();
			MainFrame.getInstance().getWorkspaceTree().clearSelection();
		}
	}

	public void mouseDragged(MouseEvent e) {
		Point2D mousePos = e.getPoint();
		med.transformToUserSpace(mousePos);
		
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (!e.isControlDown()) {
				med.getDiagram().getSelModel().removeAllFromSelectionList();
				MainFrame.getInstance().getWorkspaceTree().clearSelection();
			}

			double width = mousePos.getX() - med.getLastPosition().getX();
			double height = mousePos.getY() - med.getLastPosition().getY();
			if ((width < 0) && (height < 0)) {
				rect.setRect(mousePos.getX(), mousePos.getY(), Math.abs(width),
						Math.abs(height));
			} else if ((width < 0) && (height >= 0)) {
				rect.setRect(mousePos.getX(), med.getLastPosition().getY(),
						Math.abs(width), Math.abs(height));
			} else if ((width > 0) && (height < 0)) {
				rect.setRect(med.getLastPosition().getX(), mousePos.getY(),
						Math.abs(width), Math.abs(height));
			} else {
				rect.setRect(med.getLastPosition().getX(), med
						.getLastPosition().getY(), Math.abs(width), Math
						.abs(height));
			}
			med.setSelectionRectangle(rect);

			med.getDiagram()
					.getSelModel()
					.selectElements(rect,
							med.getDiagram().getModel().getDiagramElements());

			med.repaint();
		}

	}

	public void mouseReleased(MouseEvent e) {
		med.setSelectionRectangle(new Rectangle2D.Double(0, 0, 0, 0));
		med.repaint();
		med.startSelectState();
	}

}

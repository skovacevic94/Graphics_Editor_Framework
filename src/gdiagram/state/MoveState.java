/**
 * @author Stanko 14.06.2014.
 * MoveState.java
 */
package gdiagram.state;

import gdiagram.command.MoveDeviceCommand;
import gdiagram.command.MoveLinkCommand;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.view.AutoScrollThread;
import gdiagram.view.DiagramView;
import gdiagram.view.DiagramView.Direction;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class MoveState extends State {
	private DiagramView med;
	private Point2D breakPoint = null;
	private double x = 0;
	private double y = 0;

	public MoveState(DiagramView med) {
		this.med = med;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		AutoScrollThread thread = med.getThread();
		
		Point2D lastPosition = e.getPoint();
		med.transformToUserSpace(lastPosition);
		int elementInMotion = med.getDiagram().getModel()
				.getElementAtPosition((Point) lastPosition);
		DiagramElement handleElement = null;
		if(elementInMotion != -1)
			handleElement = med.getDiagram().getModel()
					.getElementAt(elementInMotion);
		
		if (!thread.isStarted())
			thread.start();
		if (e.getPoint().getX() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
			thread.setMousePos(e.getPoint());
			thread.setHandleElement(handleElement);
		} else if (e.getPoint().getY() >= med.getFramework().getSize()
				.getHeight() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
			thread.setMousePos(e.getPoint());
			thread.setHandleElement(handleElement);
		} else if (e.getPoint().getX() >= med.getFramework().getSize()
				.getWidth() - 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Right);
			thread.setScroll(true);
			thread.setMousePos(e.getPoint());
			thread.setHandleElement(handleElement);
		} else if (e.getPoint().getY() <= 10) {
			thread.setScroll(false);
			thread.setDirection(Direction.Down);
			thread.setScroll(true);
			thread.setMousePos(e.getPoint());
			thread.setHandleElement(handleElement);
		} else {
			thread.setScroll(false);
		}

		med.getFramework().setCursor(
				Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		
		double deltaX = lastPosition.getX() - med.getLastPosition().getX();
		double deltaY = lastPosition.getY() - med.getLastPosition().getY();

		if (med.getDiagram().getSelModel().getSelectionListSize() == 1) {
			if (med.getDiagram().getSelModel().getElementFromSelectionListAt(0) instanceof LinkElement) {
				if (breakPoint == null) {
					LinkElement link = (LinkElement) med.getDiagram()
							.getSelModel().getElementFromSelectionListAt(0);
					Iterator<Point2D> bpIt = link.getPointsIterator();
					while (bpIt.hasNext()) {
						Point2D currBp = bpIt.next();
						if (Point2D.distance(lastPosition.getX(),
								lastPosition.getY(), currBp.getX(),
								currBp.getY()) <= 7) {
							breakPoint = currBp;
							break;
						}
					}
				} else {
					breakPoint.setLocation(breakPoint.getX() + deltaX,
							breakPoint.getY() + deltaY);
				}
			}
		}

		if (breakPoint == null) {
			Iterator<DiagramElement> it = med.getDiagram().getSelModel()
					.getSelectionListIterator();
			while (it.hasNext()) {
				DiagramElement element = it.next();
				if (element instanceof DiagramDevice) {
					DiagramDevice device = (DiagramDevice) element;
					Point2D newPosition = (Point2D) device.getPosition()
							.clone();
					newPosition.setLocation(newPosition.getX() + deltaX,
							newPosition.getY() + deltaY);
					device.setPosition(newPosition);
				} else if (element instanceof LinkElement) {
					LinkElement link = (LinkElement) element;
					Iterator<Point2D> linkIt = link.getPointsIterator();
					while (linkIt.hasNext()) {
						Point2D bpPos = linkIt.next();
						bpPos.setLocation(bpPos.getX() + deltaX, bpPos.getY()
								+ deltaY);
					}
				}
			}
		}
		x = x + deltaX;
		y = y + deltaY;
		med.setLastPosition(lastPosition);
		med.updatePerformed(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		AutoScrollThread thread = med.getThread();
		thread.setScroll(false);

		if (breakPoint == null)
			med.getCommandManager().addCommand(
					new MoveDeviceCommand(med.getDiagram().getModel(), med
							.getDiagram().getSelModel(), x, y));
		else
			med.getCommandManager().addCommand(
					new MoveLinkCommand(med, breakPoint, x, y));
		breakPoint = null;
		med.getFramework().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		x = 0;
		y = 0;
		Point2D lastPosition = e.getPoint();
		med.transformToUserSpace(lastPosition);
		med.setLastPosition(lastPosition);
		med.startSelectState();
	}
}

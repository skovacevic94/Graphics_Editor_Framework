/**
 * @author Stanko 15.06.2014.
 * MoveDeviceCommand.java
 */
package gdiagram.command;

import gdiagram.model.DiagramModel;
import gdiagram.model.DiagramSelectionModel;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Stanko
 * 
 */
public class MoveDeviceCommand extends AbstractCommand {

	DiagramModel model;

	ArrayList<DiagramElement> movedElements = new ArrayList<DiagramElement>();

	DiagramSelectionModel tempSelectionModel = new DiagramSelectionModel();

	boolean firstAction;

	double deltaX;
	double deltaY;

	public MoveDeviceCommand(DiagramModel model, DiagramSelectionModel gsm,
			double x, double y) {
		this.model = model;
		for (int i = 0; i < gsm.getSelectionListSize(); i++) {
			DiagramElement element = gsm.getElementFromSelectionListAt(i);
			if (element instanceof DiagramDevice
					|| element instanceof LinkElement) {
				movedElements.add(gsm.getElementFromSelectionListAt(i));
			}
		}

		this.tempSelectionModel = gsm;
		firstAction = true;
		deltaX = x;
		deltaY = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if (firstAction) {
			firstAction = false;
		} else {
			tempSelectionModel
					.addToSelectionList((ArrayList<DiagramElement>) movedElements
							.clone());
			Iterator<DiagramElement> it = movedElements.iterator();
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
					Iterator<Point2D> it1 = link.getPointsIterator();
					while (it1.hasNext()) {
						Point2D tren = it1.next();
						tren.setLocation(tren.getX() + deltaX, tren.getY()
								+ deltaY);
					}
				}
			}
			tempSelectionModel.removeAllFromSelectionList();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		tempSelectionModel
				.addToSelectionList((ArrayList<DiagramElement>) movedElements
						.clone());
		Iterator<DiagramElement> it = movedElements.iterator();
		while (it.hasNext()) {
			DiagramElement element = it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				Point2D newPosition = (Point2D) device.getPosition().clone();
				newPosition.setLocation(newPosition.getX() - deltaX,
						newPosition.getY() - deltaY);
				device.setPosition(newPosition);

			} else if (element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				Iterator<Point2D> it1 = link.getPointsIterator();
				while (it1.hasNext()) {
					Point2D tren = it1.next();
					tren.setLocation(tren.getX() - deltaX, tren.getY() - deltaY);
				}
			}
		}
		tempSelectionModel.removeAllFromSelectionList();
	}
}

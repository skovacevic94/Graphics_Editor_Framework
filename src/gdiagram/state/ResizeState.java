/**
 * @author Stanko 15.06.2014.
 * ResizeState.java
 */
package gdiagram.state;

import gdiagram.command.ResizeElementsCommand;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;
import gdiagram.view.DiagramView.Handle;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class ResizeState extends State {

	Handle handleInMotion = null;
	private DiagramView med;
	private double deltaS;
	private DiagramDevice tempDevice = null;
	
	private ArrayList<Point2D> oldCommPos;
	private ArrayList<Point2D> newCommPos;
	private ArrayList<Double> oldCommScale;
	private ArrayList<Double> newCommScale;

	public ResizeState(DiagramView md) {
		med = md;
		oldCommPos = new ArrayList<Point2D>();
		newCommPos = new ArrayList<Point2D>();
		oldCommScale = new ArrayList<Double>();
		newCommScale = new ArrayList<Double>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Point2D position = e.getPoint();
		med.transformToUserSpace(position);
		if (handleInMotion == null) {
			handleInMotion = med.getDeviceAndHandleForPoint(position);
			tempDevice = med.getDeviceFromHandle(position);
		}
		if (handleInMotion != null) {

			DiagramDevice device = tempDevice;
			int handle = handleInMotion.ordinal();

			if (device == null)
				return;

			switch (handle) {
			case 0: {
				double deltaX = position.getX()
						- (device.getPosition().getX() + device.getSize()
								.getWidth());
				double deltaY = position.getY()
						- (device.getPosition().getY() + device.getSize()
								.getHeight());
				double newWidth = device.getSize().getWidth() + deltaX;
				double newHeight = device.getSize().getHeight() + deltaY;
				double scaleX = newWidth / device.getInitSize().getWidth();
				double scaleY = newHeight / device.getInitSize().getHeight();

				double newScale = 1;
				if (scaleX < scaleY)
					newScale = scaleX;
				else
					newScale = scaleY;

				if (newScale < 0.2)
					newScale = 0.2;
				else if (newScale > 3)
					newScale = 3;

				deltaS = newScale - device.getScale();

				device.setScale(newScale);
			}
				break;
			case 1: {
				double deltaX = position.getX() - (device.getPosition().getX());
				double deltaY = position.getY()
						- (device.getPosition().getY() + device.getSize()
								.getHeight());
				double newWidth = device.getSize().getWidth() - deltaX;
				double newHeight = device.getSize().getHeight() + deltaY;
				double scaleX = newWidth / device.getInitSize().getWidth();
				double scaleY = newHeight / device.getInitSize().getHeight();

				double oldWidth = device.getSize().getWidth();

				double newScale = 1;
				if (scaleX < scaleY)
					newScale = scaleX;
				else
					newScale = scaleY;

				if (newScale < 0.2)
					newScale = 0.2;
				else if (newScale > 3)
					newScale = 3;

				deltaS = newScale - device.getScale();

				device.setScale(newScale);

				Point2D newDevPos = device.getPosition();
				newDevPos.setLocation(newDevPos.getX()
						+ (oldWidth - device.getSize().getWidth()),
						newDevPos.getY());
				device.setPosition(newDevPos);
			}
				break;
			case 2: {
				double deltaX = position.getX()
						- (device.getPosition().getX() + device.getSize()
								.getWidth());
				double deltaY = position.getY() - (device.getPosition().getY());
				double newWidth = device.getSize().getWidth() + deltaX;
				double newHeight = device.getSize().getHeight() - deltaY;
				double scaleX = newWidth / device.getInitSize().getWidth();
				double scaleY = newHeight / device.getInitSize().getHeight();

				double oldHeight = device.getSize().getHeight();

				double newScale = 1;
				if (scaleX < scaleY)
					newScale = scaleX;
				else
					newScale = scaleY;

				if (newScale < 0.2)
					newScale = 0.2;
				else if (newScale > 3)
					newScale = 3;

				deltaS = newScale - device.getScale();

				device.setScale(newScale);

				Point2D newDevPos = device.getPosition();
				newDevPos.setLocation(newDevPos.getX(), newDevPos.getY()
						+ (oldHeight - device.getSize().getHeight()));
				device.setPosition(newDevPos);
			}
				break;
			case 3: {
				double deltaX = position.getX() - (device.getPosition().getX());
				double deltaY = position.getY() - (device.getPosition().getY());
				double newWidth = device.getSize().getWidth() - deltaX;
				double newHeight = device.getSize().getHeight() - deltaY;
				double scaleX = newWidth / device.getInitSize().getWidth();
				double scaleY = newHeight / device.getInitSize().getHeight();

				double oldWidth = device.getSize().getWidth();
				double oldHeight = device.getSize().getHeight();

				double newScale = 1;
				if (scaleX < scaleY)
					newScale = scaleX;
				else
					newScale = scaleY;

				if (newScale < 0.2)
					newScale = 0.2;
				else if (newScale > 3)
					newScale = 3;

				deltaS = newScale - device.getScale();

				device.setScale(newScale);

				Point2D newDevPos = device.getPosition();
				newDevPos.setLocation(newDevPos.getX()
						+ (oldWidth - device.getSize().getWidth()),
						newDevPos.getY()
								+ (oldHeight - device.getSize().getHeight()));
				device.setPosition(newDevPos);
			}
				break;
			}
			med.updatePerformed(null);

			Iterator<DiagramElement> it = med.getDiagram().getSelModel()
					.getSelectionListIterator();
			while (it.hasNext()) {

				DiagramElement element = it.next();
				if (element instanceof DiagramDevice) {
					device = (DiagramDevice) element;
					if (device.equals(tempDevice))
						continue;
					switch (handle) {
					case 0: {
						double newScale = device.getScale() + deltaS;
						if(newScale < 0.2)
							newScale = 0.2;
						if(newScale > 3) 
							newScale = 3;

						device.setScale(newScale);
					}
						break;
					case 1: {
						double newScale = device.getScale() + deltaS;
						if(newScale < 0.2)
							newScale = 0.2;
						if(newScale > 3) 
							newScale = 3;
						
						double tempWidth = device.getSize().getWidth();
						device.setScale(newScale);

						Point2D newDevPos = device.getPosition();
						newDevPos.setLocation(newDevPos.getX()
								+ (tempWidth - device.getSize().getWidth()),
								newDevPos.getY());
						device.setPosition(newDevPos);
					}
						break;
					case 2: {
						double newScale = device.getScale() + deltaS;
						if(newScale < 0.2)
							newScale = 0.2;
						if(newScale > 3) 
							newScale = 3;

						double tempHeight = device.getSize().getHeight();

						device.setScale(newScale);

						Point2D newDevPos = device.getPosition();
						newDevPos.setLocation(newDevPos.getX(),
								newDevPos.getY()
										+ (tempHeight - device.getSize()
												.getHeight()));
						device.setPosition(newDevPos);
					}
						break;
					case 3: {
						double newScale = device.getScale() + deltaS;
						if(newScale < 0.2)
							newScale = 0.2;
						if(newScale > 3) 
							newScale = 3;
						
						double tempHeight = device.getSize().getHeight();
						double tempWidth = device.getSize().getWidth();

						device.setScale(newScale);

						Point2D newDevPos = device.getPosition();
						newDevPos.setLocation(newDevPos.getX()
								+ (tempWidth - device.getSize().getWidth()),
								newDevPos.getY()
										+ (tempHeight - device.getSize()
												.getHeight()));
						device.setPosition(newDevPos);
					}
						break;
					}
				}
				med.getDiagram().getModel().fireUpdatePerformed();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Iterator<DiagramElement> it = med.getDiagram().getSelModel().getSelectionListIterator();
		while(it.hasNext()) {
			DiagramElement element = it.next();
			if(element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice)element;
				newCommPos.add(device.getPosition());
				newCommScale.add(device.getScale());
			}
		}
		
		med.getCommandManager().addCommand(new ResizeElementsCommand(med, oldCommPos, newCommPos, oldCommScale, newCommScale));
		handleInMotion = null;
		
		newCommPos.clear();
		newCommScale.clear();
		oldCommPos.clear();
		oldCommScale.clear();
		
		med.startSelectState();
	}

	/**
	 * @return the oldCommPos
	 */
	public ArrayList<Point2D> getOldCommPos() {
		return oldCommPos;
	}

	/**
	 * @return the oldCommScale
	 */
	public ArrayList<Double> getOldCommScale() {
		return oldCommScale;
	}
}

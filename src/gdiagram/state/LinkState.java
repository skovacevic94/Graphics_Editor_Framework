package gdiagram.state;

import gdiagram.command.AddLinkCommand;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.InputOutputElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.view.AutoScrollThread;
import gdiagram.view.DiagramView;
import gdiagram.view.DiagramView.Direction;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class LinkState extends State {
	private DiagramView med;
	private LinkElement link;

	DiagramElement startElement = null;

	public LinkState(DiagramView md) {
		med = md;
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		med.transformToUserSpace(position);
		if (e.getButton() == MouseEvent.BUTTON1) {
			
			if (link == null) {
				int devicePos = med.getDiagram().getModel()
						.getElementAtPosition(position);
				if (devicePos != -1) {
					startElement = med.getDiagram().getModel()
							.getElementAt(devicePos);
					DiagramDevice startDevice = (DiagramDevice) startElement;
					InputOutputElement output = startDevice.getFirstOutput();
					if (output == null) {
						startElement = null;
						link = null;
						return;
					}

					output.setUsed(true);
					link = (LinkElement) LinkElement.createDefault(startDevice,
							output, med.getDiagram().getModel()
									.getElementsCount());
					med.getDiagram().getModel()
							.addDiagramElement((LinkElement) link);
				}
			} else {
				int devicePos = med.getDiagram().getModel()
						.getElementAtPosition(position);
				if (devicePos != -1) {
					DiagramElement endElement = med.getDiagram().getModel()
							.getElementAt(devicePos);
					DiagramDevice endDevice = (DiagramDevice) endElement;
					InputOutputElement input = endDevice.getFirstInput();

					if (endElement.equals(startElement) || input == null) {
						med.getDiagram().getModel().removeElement(link);
						startElement = null;
						((LinkElement) link).getOutput().setUsed(false);
						link = null;
						return;
					}

					input.setUsed(true);
					link.setEndDevice(endDevice);
					ArrayList<Point2D> pts = (ArrayList<Point2D>)link.getPoints();
					pts.remove(pts.size() - 1);
					link.setInput(input);
					link.setPainter(link);
					med.getCommandManager().addCommand(new AddLinkCommand((LinkElement)link, med.getDiagram()));
					med.getDiagram().getModel().fireUpdatePerformed();
					startElement = null;
					link = null;
				} 
			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (link != null) {
				med.getDiagram().getModel().removeElement(link);
				startElement = null;
				((LinkElement) link).getOutput().setUsed(false);
				link = null;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		Point position = e.getPoint();
		med.transformToUserSpace(position);
		if (link == null)
			return;

		if (e.getButton() == MouseEvent.BUTTON1) {
			int devicePos = med.getDiagram().getModel()
					.getElementAtPosition(position);
			if (devicePos != -1) {
				DiagramElement endElement = med.getDiagram().getModel()
						.getElementAt(devicePos);
				DiagramDevice endDevice = (DiagramDevice) endElement;
				InputOutputElement input = endDevice.getFirstInput();

				if (endElement.equals(startElement) || input == null) {
					med.getDiagram().getModel().removeElement(link);
					startElement = null;
					((LinkElement) link).getOutput().setUsed(false);
					link = null;
					return;
				}

				((LinkElement) link).setEndDevice(endDevice);
				((LinkElement) link).setInput(input);
				((LinkElement) link).setPainter(link);

				ArrayList<Point2D> pts = (ArrayList<Point2D>)link.getPoints();
				pts.remove(pts.size() - 1);
				((LinkElement) link).getInput().setUsed(true);
				((LinkElement) link).getOutput().setUsed(true);

				med.getCommandManager().addCommand(new AddLinkCommand((LinkElement)link, med.getDiagram()));
				
				med.getDiagram().getModel().fireUpdatePerformed();
				link = null;
			} else {
				((LinkElement) link).addPoint(position);

			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		AutoScrollThread thread = med.getThread();
		
		if(!thread.isStarted())
			thread.start();
		if(e.getPoint().getX()<=10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
		}
		else if(e.getPoint().getY()>=med.getFramework().getSize().getHeight()-10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
		}
		else if(e.getPoint().getX()>=med.getFramework().getSize().getWidth()-10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Right);
			thread.setScroll(true);
		}
		else if(e.getPoint().getY()<=10){			
			thread.setScroll(false);			
			thread.setDirection(Direction.Down);
			thread.setScroll(true);
		}
		else{
			thread.setScroll(false);
		}
		
		Point position = e.getPoint();
		med.transformToUserSpace(position);
		if (link != null) {

			link.getLastPoint().setLocation(position);
			link.setPainter(link);
			med.getDiagram().getModel().fireUpdatePerformed();
		}
	}

	public void mouseMoved(MouseEvent e) {
		AutoScrollThread thread = med.getThread();
		
		if(!thread.isStarted())
			thread.start();
		if(e.getPoint().getX()<=10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Left);
			thread.setScroll(true);
		}
		else if(e.getPoint().getY()>=med.getFramework().getSize().getHeight()-10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Up);
			thread.setScroll(true);
		}
		else if(e.getPoint().getX()>=med.getFramework().getSize().getWidth()-10){
			thread.setScroll(false);			
			thread.setDirection(Direction.Right);
			thread.setScroll(true);
		}
		else if(e.getPoint().getY()<=10){			
			thread.setScroll(false);			
			thread.setDirection(Direction.Down);
			thread.setScroll(true);
		}
		else{
			thread.setScroll(false);
		}
		
		Point position = e.getPoint();
		med.transformToUserSpace(position);
		
		if (link != null) {
			link.getLastPoint().setLocation(position);
			link.setPainter(link);
			med.getDiagram().getModel().fireUpdatePerformed();
		}
	}
}

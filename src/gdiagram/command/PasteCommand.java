/**
 * @author Stanko 16.06.2014.
 * PasteCommand.java
 */
package gdiagram.command;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.DiagramElementsSelection;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Stanko
 *
 */
public class PasteCommand extends AbstractCommand {
	int trans;
	private Diagram diagram;
	ArrayList<DiagramElement> elements = null;

	public PasteCommand(Diagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public void doCommand() {
	
		elements = new ArrayList<>();
		trans = MainFrame.getInstance().getPasteCounter();
		Transferable clipboardContent = MainFrame.getInstance().getClipboard().getContents(MainFrame.getInstance());
		if (clipboardContent != null && clipboardContent.isDataFlavorSupported(DiagramElementsSelection.elementFlavor)) {
			try {
				@SuppressWarnings("unchecked")
				ArrayList<DiagramElement> tempElements = (ArrayList<DiagramElement>) clipboardContent.getTransferData(DiagramElementsSelection.elementFlavor);
				
				for (int i = 0; i < tempElements.size(); i++) {
					if (tempElements.get(i) instanceof DiagramDevice) {
							DiagramDevice device=(DiagramDevice) tempElements.get(i).clone();
							Point2D newLocation=(Point2D) device.getPosition().clone();
							newLocation.setLocation(device.getPosition().getX()+40 * trans,device.getPosition().getY()+40*trans);
							device.setPosition(newLocation);
							device.setName(((DiagramDevice) tempElements.get(i)).getName() + "(" + trans + ")");

							diagram.getModel().addDiagramElement(device);
							elements.add(device);
					}
				}
				
				for (int i = 0; i < tempElements.size(); i++) {
					if (tempElements.get(i) instanceof LinkElement) {
						LinkElement link = (LinkElement) tempElements.get(i).clone();
						elements.add(link);
						Point p = new Point();
						
						p.setLocation(link.getStartDevice().getPosition().getX() +40 * trans+10, link.getStartDevice().getPosition().getY()+40 * trans+10);
						int el = diagram.getModel().getElementAtPosition(p);
						DiagramElement startElement= diagram.getModel().getElementAt(el);
						
						p.setLocation(link.getEndDevice().getPosition().getX() +40 * trans+10, link.getEndDevice().getPosition().getY()+40 * trans+10);
						el = diagram.getModel().getElementAtPosition(p);
						DiagramElement endElement= diagram.getModel().getElementAt(el);
						
						link.addPoint(new Point(0, 0));
						link.setStartDevice((DiagramDevice)startElement);
						link.setEndDevice((DiagramDevice)endElement);
						link.setInput(((DiagramDevice)endElement).getInputAt(link.getInM()));
						((DiagramDevice)endElement).getInputAt(link.getInM()).setUsed(true);
						link.setOutput(((DiagramDevice)startElement).getOutputAt(link.getOutM()));
						((DiagramDevice)startElement).getOutputAt(link.getOutM()).setUsed(true);
						
						for(Point2D point : link.getPoints()){
							point.setLocation(point.getX()+40*trans, point.getY()+40*trans);
						}
						link.setName(((LinkElement) tempElements.get(i)).getName() + "(" + trans + ")");
						diagram.getModel().addDiagramElement(link);
						elements.add(link);
						
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void undoCommand() {
		for (DiagramElement element : elements) {
			diagram.getModel().removeElement(element);
			diagram.getSelModel().removeFromSelectionList(element);
		}
	}
}

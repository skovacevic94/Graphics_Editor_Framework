/**
 * @author Stanko 16.06.2014.
 * ResizeElementsCommand.java
 */
package gdiagram.command;

import gdiagram.core.MainFrame;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Stanko
 *
 */
public class ResizeElementsCommand extends AbstractCommand {

	private ArrayList<Point2D> oldElemPos;
	private ArrayList<Point2D> newElemPos;
	private ArrayList<Double> oldElemScale;
	private ArrayList<Double> newElemScale;
	
	private ArrayList<DiagramElement> affectedElements;
	
	private DiagramView med;
	
	private boolean firstTime;
	
	@SuppressWarnings("unchecked")
	public ResizeElementsCommand(DiagramView med,  ArrayList<Point2D> oldEPos,
				ArrayList<Point2D> newEPos, ArrayList<Double> oldEScale, ArrayList<Double> newEScale) {
		this.med = med;
		affectedElements = (ArrayList<DiagramElement>) med.getDiagram().getSelModel().getSelectionList().clone();
		oldElemPos = (ArrayList<Point2D>) oldEPos.clone();
		newElemPos = (ArrayList<Point2D>) newEPos.clone();
		oldElemScale = (ArrayList<Double>) oldEScale.clone();
		newElemScale = (ArrayList<Double>) newEScale.clone();
		
		firstTime = true;
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if(firstTime) {
			firstTime = false;
			return;
		}
		med.getDiagram().getSelModel().addToSelectionList((ArrayList<DiagramElement>)affectedElements.clone());
		for(int i=0; i<newElemPos.size(); i++) {
			DiagramElement element = med.getDiagram().getSelModel().getElementFromSelectionListAt(i);
			if(element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice)element;
				device.setPosition(newElemPos.get(i));
				device.setScale(newElemScale.get(i));
			}
		}
		med.getDiagram().getSelModel().removeAllFromSelectionList();
		MainFrame.getInstance().getWorkspaceTree().clearSelection();
	}

	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		med.getDiagram().getSelModel().addToSelectionList((ArrayList<DiagramElement>)affectedElements.clone());
		for(int i=0; i<oldElemPos.size(); i++) {
			DiagramElement element = med.getDiagram().getSelModel().getElementFromSelectionListAt(i);
			if(element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice)element;
				device.setPosition(oldElemPos.get(i));
				device.setScale(oldElemScale.get(i));
			}
		}
		med.getDiagram().getSelModel().removeAllFromSelectionList();
		MainFrame.getInstance().getWorkspaceTree().clearSelection();
	}

}

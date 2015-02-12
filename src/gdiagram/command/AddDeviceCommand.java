/**
 * @author Stanko 15.06.2014.
 * AddDeviceCommand.java
 */
package gdiagram.command;

import gdiagram.core.MainFrame;
import gdiagram.model.DiagramModel;
import gdiagram.model.DiagramSelectionModel;
import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.RectangleElement;
import gdiagram.model.elements.StarElement;
import gdiagram.model.elements.TriangleElement;
import gdiagram.view.DiagramView;

import java.awt.geom.Point2D;

/**
 * @author Stanko
 * 
 */
public class AddDeviceCommand extends AbstractCommand {
	DiagramModel model;
	Point2D lastPosition;
	DiagramElement device = null;
	DiagramSelectionModel selectionModel;
	int deviceType;

	public AddDeviceCommand(DiagramModel model,
			DiagramSelectionModel selectionModel, Point2D lastPosition,
			int deviceType) {
		this.model = model;
		this.lastPosition = lastPosition;
		this.selectionModel = selectionModel;
		this.deviceType = deviceType;

	}

	@Override
	public void doCommand() {
		if (device == null)
			if (deviceType == DiagramView.CIRCLE) {
				device = CircleElement.createDefault(lastPosition,
						model.getElementsCount(), model.getOwner());
			} else if (deviceType == DiagramView.RECTANGLE) {
				device = RectangleElement.createDefault(lastPosition,
						model.getElementsCount(), model.getOwner());
			} else if (deviceType == DiagramView.STAR) {
				device = StarElement.createDefault(lastPosition, model.getElementsCount(), model.getOwner());
			} else if (deviceType == DiagramView.TRIANGLE) {
				device = TriangleElement.createDefault(lastPosition, model.getElementsCount(), model.getOwner());
			}

		selectionModel.removeAllFromSelectionList();
		MainFrame.getInstance().getWorkspaceTree().clearSelection();
		
		model.addDiagramElement(device);
		selectionModel.addToSelectionList(device);

	}

	@Override
	public void undoCommand() {
		selectionModel.removeAllFromSelectionList();
		model.removeElement(device);
	}

}

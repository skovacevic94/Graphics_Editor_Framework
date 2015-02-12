/**
 * @author Stanko 15.06.2014.
 * RotateCommand.java
 */
package gdiagram.command;

import gdiagram.model.elements.DiagramDevice;
import gdiagram.view.DiagramView;

/**
 * @author Stanko
 * 
 */
public class RotateCommand extends AbstractCommand {

	private DiagramView med;
	private DiagramDevice deviceToRotate;
	private int dir;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public RotateCommand(DiagramView med, int dir) {
		this.med = med;
		this.dir = dir;

		deviceToRotate = (DiagramDevice) med.getDiagram().getSelModel()
				.getElementFromSelectionListAt(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if (dir == RotateCommand.LEFT) {
			deviceToRotate.setRotation(deviceToRotate.getRotation() - Math.PI/2);
		} else {
			deviceToRotate.setRotation((deviceToRotate.getRotation() + Math.PI/2) );
		}
		med.getDiagram().getModel().fireUpdatePerformed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		if (dir == RotateCommand.LEFT) {
			deviceToRotate.setRotation(deviceToRotate.getRotation() + Math.PI/2);
		} else {
			deviceToRotate.setRotation(deviceToRotate.getRotation() - Math.PI/2);
		}
		med.getDiagram().getModel().fireUpdatePerformed();
	}

}

/**
 * @author Stanko 15.06.2014.
 * MoveLinkCommand.java
 */
package gdiagram.command;

import gdiagram.view.DiagramView;

import java.awt.geom.Point2D;

/**
 * @author Stanko
 *
 */
public class MoveLinkCommand extends AbstractCommand {
	private DiagramView med;
	private Point2D breakPoint;
	private boolean firstTime;
	
	private double deltaX;
	private double deltaY;
	
	public MoveLinkCommand(DiagramView med, Point2D breakPoint, double deltaX, double deltaY) {
		this.med = med;
		this.breakPoint = breakPoint;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.firstTime = true;
	}
	
	
	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if(firstTime) 
			firstTime = false;
		else {
			Point2D newPosition = (Point2D) breakPoint.clone();
			newPosition.setLocation(breakPoint.getX() + deltaX,
					breakPoint.getY() + deltaY);
			breakPoint.setLocation(newPosition);
			med.getDiagram().getModel().fireUpdatePerformed();
		}
	}

	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		Point2D newPosition = (Point2D) breakPoint.clone();
		newPosition.setLocation(breakPoint.getX() - deltaX,
				breakPoint.getY() - deltaY);
		breakPoint.setLocation(newPosition);
		med.getDiagram().getModel().fireUpdatePerformed();
	}

}

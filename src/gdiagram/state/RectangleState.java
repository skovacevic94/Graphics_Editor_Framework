package gdiagram.state;

import gdiagram.command.AddDeviceCommand;
import gdiagram.view.AutoScrollThread;
import gdiagram.view.DiagramView;
import gdiagram.view.DiagramView.Direction;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class RectangleState extends State {

	private static final long serialVersionUID = 242706857331800112L;
	
	private DiagramView med; 
	
	public RectangleState(DiagramView md) {
      	med = md;
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.state.State#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
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
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		med.transformToUserSpace(position);
		if (e.getButton()==MouseEvent.BUTTON1){
			if (med.getDiagram().getModel().getElementAtPosition(position)==-1){
				 med.getCommandManager().addCommand(new AddDeviceCommand(med.getDiagram().getModel(), med.getDiagram().getSelModel(), position, DiagramView.RECTANGLE));
			 }		
		}
	}
}

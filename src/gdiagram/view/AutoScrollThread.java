/**
 * @author Stanko 15.06.2014.
 * AutoScrollThread.java
 */
package gdiagram.view;

import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView.Direction;

import java.awt.Point;

/**
 * @author Stanko
 *
 */
public class AutoScrollThread extends Thread{
	boolean scroll=false;
	DiagramView view;
	Direction direction;
	Point mousePos;
	DiagramElement handleElement;
	boolean started=false;
	

	public AutoScrollThread(DiagramView view) {
		super();		
		this.view=view;
	}

	@Override
	public void run() {
		started=true;		
		while(true){
			if(scroll){
				try{
					sleep(50);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				view.autoScroll(direction);
			}
			if(!started)
				break;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void setMousePos(Point mousePos) {
		this.mousePos = mousePos;
	}
	
	public Point getMousePos() {
		return mousePos;
	}
	
	public void setHandleElement(DiagramElement handleElement) {
		this.handleElement = handleElement;
	}
	
	public DiagramElement getHandleElement() {
		return handleElement;
	}
	
}

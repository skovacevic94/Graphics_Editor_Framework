package gdiagram.state;

import gdiagram.view.DiagramView;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StateManager implements Serializable {
	private State currentState;
	
	LinkState linkState;
	StarState starState; 
	TriangleState trigState;
	CircleState circleState;
	RectangleState rectState;
	SelectState selectState;
	LassoState lassoState;
	LassoZoomState lassoZoomState;
	MoveState moveState;
	ResizeState resizeState;
	
	public StateManager(DiagramView med)
	{
		linkState = new LinkState(med); 
		starState = new StarState(med);
		trigState=new TriangleState(med);
		circleState = new CircleState(med);
		rectState = new RectangleState(med);
		selectState=new SelectState(med);
		lassoState=new LassoState(med);
		lassoZoomState = new LassoZoomState(med);
     	moveState = new MoveState(med);
     	resizeState = new ResizeState(med);
		
		currentState = selectState;
	}
	public void setLinkState(){ currentState = linkState; }
	public void setSelectState(){ currentState = selectState; }
	public void setLassoState(){ currentState = lassoState; }
	public void setStarState() { currentState = starState; }
	public void setTrigState() { currentState = trigState; }
	public void setCircleState() { currentState = circleState; }
	public void setRectState() { currentState = rectState; }
	public void setLassoZoomState() {currentState = lassoZoomState; }
	public void setMoveState() { currentState = moveState; }
	public void setResizeState() { currentState = resizeState; }
	
	public State getCurrentState() {
		return currentState;
	}
}

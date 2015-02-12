package gdiagram.view.painters;

import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Iterator;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class LinkPainter extends ElementPainter{

	public LinkPainter(DiagramElement device) {
		super(device);

	
	}
	
	public void paint(Graphics2D g, DiagramElement element){
		g.setPaint(element.getStrokeColor());
		g.setStroke(element.getStroke());

		LinkElement link = (LinkElement) element;
		Point2D last =(Point2D) link.getOutput().getPosition().clone();
		
		
		last.setLocation(last.getX()+5,last.getY());
		 
		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D current = (Point2D) it.next();
			g.drawLine((int)last.getX(), (int)last.getY(), (int)current.getX(), (int)current.getY());
			last=current;
			g.drawRect((int)last.getX()-2, (int)last.getY()-2, 4, 4);
			
			
		}
		 if (link.getInput()!=null)
			 g.drawLine((int)last.getX(), (int)last.getY(), (int)link.getInput().getPosition().getX()-5,(int)link.getInput().getPosition().getY());
	}

	@Override
	public boolean isElementAt(Point pos) {
		LinkElement link = (LinkElement)element;
		if (link.getInput()==null)
			return false;
		
		Rectangle rect = new Rectangle(0,0,0,0);
		
		Point2D sbp = link.getOutput().getPosition();
		Point2D dbp = link.getInput().getPosition();
		
		rect.setRect(sbp.getX()-2, sbp.getY()-2, 4, 4);
		if (rect.contains(pos)) {
			return true;
		}
		rect.setRect(dbp.getX()-2, dbp.getY()-2, 4, 4);
		if (rect.contains(pos)) {
			return true;
		}

		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D bp = (Point2D) it.next();
			rect.setRect(bp.getX()-2, bp.getY()-2, 4, 4);
			if (rect.contains(pos)) {
				return true;
			}
		}

		return false;
	}	
	
	public static Rectangle findRectangle(LinkElement link){
		double minX=0,minY=0,maxX=0,maxY=0;
		minX=link.getOutput().getPosition().getX();
		minY=link.getOutput().getPosition().getY(); 
		maxX=link.getOutput().getPosition().getX()+5; 
		maxY=link.getOutput().getPosition().getY()+5;		
		Iterator<Point2D> it = link.getPointsIterator();
		while(it.hasNext()){
			Point2D  point  =  it.next();
			if(point.getX()<minX)
				minX = point.getX();
			if(point.getX()>maxX)
				maxX = point.getX();
			if(point.getY()<minY)
				minY = point.getY();
			if(point.getY()>maxY)
				maxY = point.getY();
		}
		Point2D  point  =  link.getInput().getPosition();
		if(point.getX()<minX)
			minX = point.getX();
		if(point.getX()>maxX)
			maxX = point.getX();
		if(point.getY()<minY)
			minY = point.getY();
		if(point.getY()>maxY)
			maxY = point.getY();

		
		Rectangle rect=new Rectangle((int)minX-5,(int)minY-5,(int)(maxX-minX+10),(int)(maxY-minY+10));
		return rect;
	}	
	

}

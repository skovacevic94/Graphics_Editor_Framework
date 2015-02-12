package gdiagram.model.elements;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.view.painters.CirclePainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;


@SuppressWarnings("serial")
public class CircleElement extends DiagramDevice {
	
	public CircleElement(Point2D position, Dimension size, Stroke stroke, Paint paint,Color strokeColor, Diagram owner) {
		super(position, size, stroke, paint,strokeColor,MainFrame.getInstance().getPallete().getNumIO(),1, owner);
		
		elementPainter = new CirclePainter(this);
	}
	
	public CircleElement(CircleElement circle){
		super(circle);
		setName(circle.getName()+"-");
	}

	public static DiagramDevice createDefault(Point2D pos, int elemNo, Diagram owner){
		Point2D position = (Point2D) pos.clone();
		
        Paint fill = Color.WHITE;
	    CircleElement or=new CircleElement(position, 
	    		                   new Dimension(50,50),
	    		                   new BasicStroke((float)(2), BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL ),
	    		                   fill,
	    		                   Color.BLACK, owner);
        or.setName("Circle " + elemNo);
		return or;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new CircleElement(this);
	}
}

package gdiagram.model.elements;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.view.painters.TrianglePainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class TriangleElement extends DiagramDevice {

	public TriangleElement(Point2D position, Dimension size, Stroke stroke, Paint paint, Color strokeColor, Diagram owner) {
		super(position, size, stroke, paint,strokeColor,MainFrame.getInstance().getPallete().getNumIO(),1, owner);
		elementPainter = new TrianglePainter(this);
	}
	
	public TriangleElement(TriangleElement triangle){
		super(triangle);
		setName("copy");
	}

	
	public static DiagramDevice createDefault(Point2D pos, int elemNo, Diagram owner){
		Point2D position = (Point2D) pos.clone();
		
        Paint fill = Color.WHITE;
        TriangleElement triangleElement=new TriangleElement(position, 
	    		                       new Dimension(60,50),
	    		                      new BasicStroke((float)(2), BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL ),
             	                      fill,
	    		                      Color.BLACK, owner);
        triangleElement.setName("Triangle " + elemNo);
		return triangleElement;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new TriangleElement(this);
	}
}

package gdiagram.model.elements;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.view.painters.RectPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;


@SuppressWarnings("serial")
public class RectangleElement extends DiagramDevice {

	public RectangleElement(Point2D position, Dimension size, Stroke stroke, Paint paint, Color strokeColor, Diagram owner) {
		super(position, size, stroke, paint,strokeColor,MainFrame.getInstance().getPallete().getNumIO(),1, owner);
		elementPainter = new RectPainter(this);
	}

	public RectangleElement(RectangleElement rectangle){
		super(rectangle);
		setName("copy");
	}
	
	public static DiagramDevice createDefault(Point2D pos, int elemNo, Diagram owner){
		Point2D position = (Point2D) pos.clone();
		
        Paint fill = Color.WHITE;
        RectangleElement rectangleElement = new RectangleElement(position, 
	    		                       new Dimension(80,40),
	    		                      new BasicStroke((float)(2), BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL ),
             	                      fill,
	    		                      Color.BLACK, owner);
        rectangleElement.setName("Rectangle" + elemNo);
		return rectangleElement;
	}

	/* (non-Javadoc)
	 * @see gdiagram.model.elements.DiagramElement#clone()
	 */
	@Override
	public DiagramElement clone() {
		// TODO Auto-generated method stub
		return new RectangleElement(this);
	}
}

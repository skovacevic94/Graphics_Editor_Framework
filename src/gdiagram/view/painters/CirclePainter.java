package gdiagram.view.painters;

import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramElement;

import java.awt.geom.Ellipse2D;

public class CirclePainter extends DevicePainter {
	
	private static final long serialVersionUID = -2824460556126643023L;

	public CirclePainter(DiagramElement device) {
		super(device);
		CircleElement circle = (CircleElement) device;
		shape = new Ellipse2D.Double(0, 0, circle.getSize().getHeight(), circle.getSize().getHeight());
	}
	
}

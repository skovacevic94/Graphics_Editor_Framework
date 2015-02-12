package gdiagram.view.painters;

import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.RectangleElement;

import java.awt.geom.GeneralPath;

public class RectPainter extends DevicePainter {

	private static final long serialVersionUID = -9177196909226170222L;

	public RectPainter(DiagramElement device) {
		super(device);
		RectangleElement rectangle = (RectangleElement) device;

		shape = new GeneralPath();
		((GeneralPath)shape).moveTo(0,0);
		
		((GeneralPath)shape).lineTo(rectangle.getSize().width,0);
		
		((GeneralPath)shape).lineTo(rectangle.getSize().width,rectangle.getSize().height);
		
		((GeneralPath)shape).lineTo(0,rectangle.getSize().height);
		
		((GeneralPath)shape).closePath();	
	}
}

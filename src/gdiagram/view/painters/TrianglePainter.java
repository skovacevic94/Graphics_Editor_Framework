package gdiagram.view.painters;

import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.TriangleElement;

import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class TrianglePainter extends DevicePainter{

	public TrianglePainter(DiagramElement device) {
		super(device);
		TriangleElement triangle = (TriangleElement) device;

		shape=new GeneralPath();
		((GeneralPath)shape).moveTo(0,triangle.getSize().getHeight());
		
		((GeneralPath)shape).lineTo(triangle.getSize().getWidth()/2.0,0);
		
		((GeneralPath)shape).lineTo(triangle.getSize().getWidth(),triangle.getSize().getHeight());
		
		((GeneralPath)shape).closePath();

		
	}
	

	
}

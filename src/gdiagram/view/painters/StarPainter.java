package gdiagram.view.painters;

import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.StarElement;

import java.awt.geom.GeneralPath;

/**
 * Konkretan painter je zadužen za definisanje Shape objekta koji predstavlja dati element
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public class StarPainter extends DevicePainter{

	public StarPainter(DiagramElement device) {
		super(device);
		StarElement star = (StarElement) device;

		double x = 0;
		double y = 0;

		double w = star.getSize().getWidth();
		double h = star.getSize().getHeight();

		shape = new GeneralPath();
		((GeneralPath) shape).moveTo(x + (w/2), y);
		((GeneralPath) shape).lineTo(x + w, y + h);
		((GeneralPath) shape).lineTo(x, y + (h/3));
		((GeneralPath) shape).lineTo(x + w, y + (h/3));
		((GeneralPath) shape).lineTo(x, y + h);
		((GeneralPath) shape).closePath();
	}
	

	
}

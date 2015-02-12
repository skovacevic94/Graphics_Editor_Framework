package gdiagram.model.elements;

import gdiagram.view.painters.InputOutputPainter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class InputOutputElement extends DiagramElement {

	public static final int INPUT = 0;
	public static final int OUTPUT = 1;

	protected Point2D position;
	protected DiagramDevice device;

	private boolean used;
	
	protected int ioNo;
	protected int type;

	public InputOutputElement(Point2D position, Dimension size, Stroke stroke,
			Paint paint, Color strokeColor, DiagramDevice device, int ioNo,
			int type) {
		super(stroke, paint, strokeColor);

		this.device = device;
		this.ioNo = ioNo;
		this.type = type;
		this.position = position;
		used = false;
		elementPainter = new InputOutputPainter(this);
	}
	
	public InputOutputElement(InputOutputElement element, DiagramDevice device) {
		super(element);
		this.device = device;
		this.ioNo = element.ioNo;
		this.type = element.type;
		this.position = element.position;
		//elementPainter = new InputOutputPainter(this);
	}

	public static DiagramElement createDefault(Point2D pos, Stroke stroke,
			Paint paint, DiagramDevice device, int ioNo, int type) {
		Point2D position = (Point2D) pos.clone();
		position.setLocation(position.getX(), position.getY());
		InputOutputElement io = new InputOutputElement(position, new Dimension(
				40, 25), stroke, paint, Color.BLUE, device, ioNo, type);
		io.setName("IO" + ioNo);
		return io;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public Point2D getPosition() {
		AffineTransform trans = new AffineTransform();
		trans.translate(device.getPosition().getX(), device.getPosition()
				.getY());
		trans.rotate(device.getRotation(), device.getSize().getWidth() / 2,
				device.getSize().getHeight() / 2);
		trans.scale(device.getScale(), device.getScale());
		return trans.transform(position, null);
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	public int getType() {
		return type;
	}
	
	public DiagramDevice getDevice() {
		return device;
	}
	
	public void setDevice(DiagramDevice device) {
		this.device = device;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new InputOutputElement(this, device);
	}
}

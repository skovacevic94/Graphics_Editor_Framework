package gdiagram.model.elements;

import gdiagram.view.painters.LinkPainter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class LinkElement extends DiagramElement {

	protected DiagramDevice startDevice;
	protected InputOutputElement output;

	protected DiagramDevice endDevice;
	protected InputOutputElement input;
	
	protected int inM;
	protected int outM;

	protected ArrayList<Point2D> points = new ArrayList<Point2D>();

	public LinkElement(DiagramDevice startDevice, InputOutputElement input,
			Stroke stroke, Paint paint, Color strokeColor) {
		super(stroke, paint, strokeColor);

		this.startDevice = startDevice;
		this.output = input;
		addPoint(new Point2D.Double(output.getPosition().getX(), output
				.getPosition().getY()));
		elementPainter = new LinkPainter(this);

	}
	
	public LinkElement(LinkElement link) {
		super(link);
		//this.points=(ArrayList<Point2D>) link.points.clone();
		this.points = new ArrayList<>();
		for (int i = 0; i < link.points.size(); i++) {
			this.points.add((Point2D) link.points.get(i).clone());
		}
		this.startDevice = (DiagramDevice) link.startDevice.clone();
		this.endDevice = (DiagramDevice) link.endDevice.clone();
		this.inM = link.endDevice.inM((InputOutputElement) link.input);
		this.outM = link.startDevice.outM((InputOutputElement)link.output);

		this.output = (InputOutputElement) link.output.clone();
		this.input = (InputOutputElement) link.input.clone();
		//elementPainter = new LinkPainter(this);
	}

	public static DiagramElement createDefault(DiagramDevice startDevice,
			InputOutputElement input, int elemNo) {
		Paint fill = Color.WHITE;
		LinkElement or = new LinkElement(startDevice, input, new BasicStroke(
				(float) (2), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL),
				fill, Color.BLUE);
		or.setName("LINK " + elemNo);
		return or;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return startDevice.getOwner().getFolder("Links");
	}

	public void addPoint(Point2D p) {
		points.add(p);

	}

	public Iterator<Point2D> getPointsIterator() {
		return points.iterator();
	}

	public void setPainter(DiagramElement link) {
		elementPainter = new LinkPainter(link);

	}

	public InputOutputElement getInput() {
		return input;
	}

	public DiagramDevice getStartDevice() {
		return startDevice;
	}

	public void setEndDevice(DiagramDevice endDevice) {
		this.endDevice = endDevice;
	}

	public void setOutput(InputOutputElement output) {
		this.output = output;
	}

	public void setInput(InputOutputElement input) {
		this.input = input;
	}

	public InputOutputElement getOutput() {
		return output;
	}

	public ArrayList<Point2D> getPoints() {
		return points;
	}
	
	public Point2D getLastPoint() {
		return points.get(points.size() - 1);
	}

	/**
	 * @return the endDevice
	 */
	public DiagramDevice getEndDevice() {
		return endDevice;
	}

	/**
	 * @param startElement
	 */
	public void setStartDevice(DiagramDevice startElement) {
		// TODO Auto-generated method stub
		this.startDevice = startElement;
		points.remove(points.size()-1);
	}

	/**
	 * @return the inM
	 */
	public int getInM() {
		return inM;
	}

	/**
	 * @return the outM
	 */
	public int getOutM() {
		return outM;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new LinkElement(this);
	}
	
}

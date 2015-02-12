package gdiagram.model.elements;

import gdiagram.model.Diagram;
import gdiagram.model.ElementFolder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Igor Z.
 *
 */
@SuppressWarnings("serial")
public abstract class DiagramDevice extends DiagramElement {

	protected Dimension size;
	protected boolean fliped;
	protected Point2D position;
	
	protected double scale;
	protected double rotation;
	
	private Diagram owner;
	
	protected int inputNo;
	protected int outputNo;
	
	protected ArrayList<InputOutputElement> inputs=new ArrayList<InputOutputElement>();
	protected ArrayList<InputOutputElement> outputs=new ArrayList<InputOutputElement>();
	
	public DiagramDevice(Point2D position, Dimension size, Stroke stroke, Paint paint,Color  strokeColor,int inputNo,int outputNo, Diagram owner){
		super(stroke, paint,strokeColor);
		this.size = size;
		//ovo omogućava translaciju tačaka tako da se element kreira u centru
		//System.out.println("Init device on:(" + position.getX() + "," + position.getY() + ")");
		position.setLocation(position.getX()-size.getWidth()/2,position.getY()-size.getHeight()/2);
		this.position = position;
		this.inputNo=inputNo;
		this.outputNo=outputNo;
		this.strokeColor=strokeColor;
		this.scale = 1;
		this.rotation = 0;
		this.fliped = false;
		
		this.owner = owner;
		
		//kreiranje ulaza
		
		for (int i=0;i<inputNo;i++){

			Point2D ioPos = new Point2D.Double(0, 
					                           (getSize().getHeight()/(getInputNo() +1))*(i+1)) ;
			addInput((InputOutputElement) InputOutputElement.createDefault(ioPos,stroke,paint,this,i+1,InputOutputElement.INPUT));
		  
      	}
		
		//kreiranje izlaza
		for (int i=0;i<outputNo;i++){
			Point2D ioPos = new Point2D.Double(size.width,
					                           (getSize().getHeight()/(getOutputNo() +1))*(i+1)) ;
			addOutput((InputOutputElement) InputOutputElement.createDefault(ioPos,stroke,paint,this,i+1,InputOutputElement.OUTPUT));
		  
      	}		
		
	}

	public DiagramDevice(DiagramDevice device) {
		super(device);
		this.size = device.size;
		this.position = (Point2D) device.position.clone();
		
		this.owner = device.owner;

		this.inputNo = device.inputNo;
		this.outputNo = device.outputNo;
		
		this.scale = device.scale;
		this.rotation = device.rotation;
		
		for (int i = 0; i < device.inputs.size(); i++)
			this.inputs.add(new InputOutputElement(device.inputs.get(i), this));
		for (int i = 0; i < device.outputs.size(); i++)
			this.outputs.add(new InputOutputElement(device.outputs.get(i), this));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getParent()
	 */
	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		ElementFolder folder = null;
		if(this instanceof CircleElement)
			return owner.getFolder("Circles");
		else if(this instanceof RectangleElement)
			return owner.getFolder("Rectangles");
		else if(this instanceof StarElement) 
			return owner.getFolder("Stars");
		else if(this instanceof TriangleElement)
			return owner.getFolder("Triangles");
		return folder;
	}
	
	public Diagram getOwner() {
		return this.owner;
	}
	
	
    //metode za rad sa kolekcijom ulaza
	public void addInput(InputOutputElement p){
		inputs.add(p);
	}
	
	public void removeInput(InputOutputElement p){
		inputs.remove(p);
	}
	
	public InputOutputElement getInputAt(int i){
		return inputs.get(i);
	}
	
	public InputOutputElement getFirstInput() {
		for(InputOutputElement in : inputs) 
			if(!in.isUsed())
				return in;
		return null;
	}
	
	public int getInputCount(){
		return inputs.size();
	}
	
	public Iterator<InputOutputElement> getInputIterator(){
		return inputs.iterator();
	}
	//------------------------------------------------------
	
	
	
    //metode za rad sa kolekcijom izlaza
	public void addOutput(InputOutputElement p){
		outputs.add(p);
	}
	
	public void removeOutput(InputOutputElement p){
		outputs.remove(p);
	}
	
	public InputOutputElement getOutputAt(int i){
		return outputs.get(i);
	}
	
	public InputOutputElement getFirstOutput() {
		for(InputOutputElement out : outputs)
			if(!out.isUsed())
				return out;
		return null;
	}
	
	public int getIOutputCount(){
		return outputs.size();
	}
	
	public Iterator<InputOutputElement> getOutputIterator(){
		return outputs.iterator();
	}
	//------------------------------------------------------
	
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getRotation() {
		return rotation;
	}
	
	public boolean isFliped() {
		return fliped;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
		if(Math.abs(rotation) >= 2 * Math.PI){
			if(this.rotation >= 2 * Math.PI)
				this.rotation -= 2 * Math.PI;
			if(this.rotation <= 2 * Math.PI)
				this.rotation += 2 * Math.PI;
		}
		if(fliped) fliped = false;
		else fliped = true;
			
	}
	
	public Dimension getInitSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public Dimension getSize() {
		Dimension tempSize = new Dimension();
		
		double width = size.getWidth() * getScale();
		double height = size.getHeight() * getScale();		
		tempSize.setSize(width, height);
		return tempSize;
	}
	
	public int getInputNo() {
		return inputNo;
	}

	public ArrayList<InputOutputElement> getInputs() {
		return inputs;
	}

	public int getOutputNo() {
		return outputNo;
	}
	
	public int inM(InputOutputElement e){
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i).equals(e))
				return i;
		}
		return -1;
	}
	
	public int outM(InputOutputElement e){
		for (int i = 0; i < outputs.size(); i++) {
			if (outputs.get(i).equals(e))
				return i;
		}
		return -1;
	}

	/**
	 * @return
	 */
	public ArrayList<InputOutputElement> getOutputs() {
		// TODO Auto-generated method stub
		return outputs;
	}
}

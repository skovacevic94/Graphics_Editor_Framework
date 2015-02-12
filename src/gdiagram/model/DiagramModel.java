package gdiagram.model;

import gdiagram.core.MainFrame;
import gdiagram.events.UpdateEvent;
import gdiagram.events.UpdateListener;
import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.model.elements.RectangleElement;
import gdiagram.model.elements.StarElement;
import gdiagram.model.elements.TriangleElement;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

@SuppressWarnings("serial")
public class DiagramModel implements Serializable {

	private String name;
	
	Diagram owner;
	
	protected ArrayList<DiagramElement> diagramElements = new ArrayList<DiagramElement>();

	transient EventListenerList listenerList = new EventListenerList();
	UpdateEvent updateEvent = null;

	public DiagramModel(Diagram owner) {
		this.owner = owner;
	}
	
	public Diagram getOwner() {
		return owner;
	}
	
	private Object readResolve() {
		listenerList = new EventListenerList();
		return this;
	}

	public ArrayList<DiagramElement> getElements() {
		return diagramElements;
	}
	
	/**
	 * @param point
	 * @return index of element at point
	 */
	public int getElementAtPosition(Point point) {
		for (int i = getElementsCount() - 1; i >= 0; i--) {
			DiagramElement element = getElementAt(i);
			if(element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice)element;
				Rectangle2D rect = new Rectangle2D.Double(device.getPosition().getX(), device.getPosition().getY(), 
						device.getSize().getWidth(), device.getSize().getHeight());
				if(rect.contains(point))
					return i;
			}
			else if (element.getPainter().isElementAt(point)) {
				return i;
			}
		}
		return -1;
	}

	public void removeElement(DiagramElement element) {
		diagramElements.remove(element);
		ElementFolder parent = (ElementFolder)element.getParent();
		if(parent.getChildCount() == 0) {
			Diagram diagParent = (Diagram)parent.getParent();
			diagParent.remove(parent);
		}
		
		fireUpdatePerformed();
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}

	public int getElementsCount() {
		return diagramElements.size();
	}

	public DiagramElement getElementAt(int i) {
		return diagramElements.get(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public Iterator<DiagramElement> getElementsIterator() {
		return diagramElements.iterator();
	}

	public void addDiagramElement(DiagramElement element) {
		diagramElements.add(element);
		
		if(element instanceof CircleElement) {
			if(owner.getFolder("Circles") == null)
				owner.add(new ElementFolder("Circles", owner));
		}
		else if(element instanceof RectangleElement) {
			if(owner.getFolder("Rectangles") == null)
				owner.add(new ElementFolder("Rectangles", owner));
		}
		else if(element instanceof LinkElement) {
			if(owner.getFolder("Links") == null)
				owner.add(new ElementFolder("Links", owner));
		} else if(element instanceof StarElement) {
			if(owner.getFolder("Stars") == null)
				owner.add(new ElementFolder("Stars", owner));
		} else if(element instanceof TriangleElement) {
			if(owner.getFolder("Triangles") == null)
				owner.add(new ElementFolder("Triangles", owner));
		}
			
		fireUpdatePerformed();
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}

	public void addUpdateListener(UpdateListener l) {
		listenerList.add(UpdateListener.class, l);
	}

	public void removeUpdateListener(UpdateListener l) {
		listenerList.remove(UpdateListener.class, l);
	}

	public ArrayList<DiagramElement> getDiagramElements() {
		return diagramElements;
	}


	public void fireUpdatePerformed() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UpdateListener.class) {
				if (updateEvent == null)
					updateEvent = new UpdateEvent(this);
				((UpdateListener) listeners[i + 1])
						.updatePerformed(updateEvent);
			}
		}
	}

}

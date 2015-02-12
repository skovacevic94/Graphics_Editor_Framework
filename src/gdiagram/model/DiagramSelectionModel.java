package gdiagram.model;

import gdiagram.core.MainFrame;
import gdiagram.events.UpdateEvent;
import gdiagram.events.UpdateListener;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultSingleSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class DiagramSelectionModel extends DefaultSingleSelectionModel {

	/**
	 * Lista sa elementima koji su selektovani.
	 */
	private ArrayList<DiagramElement> selectionList = new ArrayList<DiagramElement>();

	transient EventListenerList listenerList = new EventListenerList();
	transient UpdateEvent updateEvent = null;

	/**
	 * Metoda dodaje element u listu selekcije.
	 * 
	 */
	public void addToSelectionList(DiagramElement element) {
		selectionList.add(element);
		fireUpdatePerformed();
		
		ArrayList<TreePath> tPaths = new ArrayList<TreePath>();
		for(DiagramElement elem : selectionList) {
			TreePath tp = new TreePath(elem.getPath());
			tPaths.add(tp);
		}
		
		for(TreePath tp : tPaths) 
			MainFrame.getInstance().getWorkspaceTree().addSelectionPath(tp);
	}

	public void selectOnFrame(DiagramElement element) {
		selectionList.add(element);
		fireUpdatePerformed();
	}
	
	/**
	 * Metoda dodaje listu elemenata u selekcionu listu.
	 */
	public void addToSelectionList(ArrayList<DiagramElement> list) {
		selectionList.addAll(list);
		fireUpdatePerformed();
	}

	/**
	 * Vraca broj elemenata u selekcionoj listi.
	 */
	public int getSelectionListSize() {
		return selectionList.size();
	}

	/**
	 * Vraca element iz selekcione liste koji se nalazi na indeksu koji se
	 * navodi kao argument.
	 */
	public DiagramElement getElementFromSelectionListAt(int index) {
		return (DiagramElement) selectionList.get(index);
	}

	/**
	 * Vraca indeks zadatog elementa u selekcionoj listi.
	 * 
	 * @param element
	 *            - element za koji se trazi indeks u listi
	 * @return - indeks elementa u listi ili -1
	 */
	public int getIndexByObject(DiagramElement element) {
		return selectionList.indexOf(element);
	}

	/**
	 * Uklanja element iz selekcione liste na datom indeksu.
	 * 
	 * @param index
	 *            - indeks elementa koji se uklanja iz selekcione liste.
	 */
	public void removeFromSelectionList(DiagramElement element) {
		selectionList.remove(element);
		fireUpdatePerformed();
	}

	/**
	 * Uklanja sve elemente iz selekcione liste.
	 */
	public void removeAllFromSelectionList() {
		selectionList.clear();
		fireUpdatePerformed();
	}

	/**
	 * Vraca objekat selekcione liste.
	 * 
	 * @return - objekat selekcione liste
	 */
	public ArrayList<DiagramElement> getSelectionList() {
		return selectionList;
	}

	public Iterator<DiagramElement> getSelectionListIterator() {
		return selectionList.iterator();
	}

	public boolean isElementSelected(DiagramElement element) {
		return selectionList.contains(element);

	}

	/**
	 * Metoda selektuje sve elemente koji se seku ili su obuhvaceni
	 * pravougaonikom
	 * 
	 * @param rec
	 *            - selekcioni pravougaonik
	 * @param elements
	 *            - niz elemenata iz modela
	 * 
	 */
	public void selectElements(Rectangle2D rec,
			ArrayList<DiagramElement> elements) {
		Iterator<DiagramElement> it = elements.iterator();
		while (it.hasNext()) {
			DiagramElement element = it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				if (rec.contains(device.getPosition().getX(), device
						.getPosition().getY(), device.getSize().getWidth(),
						device.getSize().getHeight())) {
					if (!isElementSelected(device))
						addToSelectionList(device);
				}
			} else {
				LinkElement link = (LinkElement) element;
				if(isElementSelected(link)) 
					return;
				
				int counter = 0;
				for(Point2D point : link.getPoints()) {
					if(rec.contains(point)) {
						counter++;
					}
				}
				if(counter < link.getPoints().size())
					return;
				
				if(rec.contains(link.getInput().getPosition()) && rec.contains(link.getOutput().getPosition()))
					addToSelectionList(link);
				
			}
		}

	}

	public void addUpdateListener(UpdateListener l) {
		if(this.listenerList == null) 
			listenerList = new EventListenerList();
		listenerList.add(UpdateListener.class, l);
	}

	public void removeUpdateListener(UpdateListener l) {
		listenerList.remove(UpdateListener.class, l);
	}

	/**
	 * Javljamo svim listenerima da se događaj desio
	 */
	public void fireUpdatePerformed() {
		Object[] listeners = null;
		try {
			listeners = listenerList.getListenerList();
		} catch(NullPointerException e) {}
		if(listeners == null) return;
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

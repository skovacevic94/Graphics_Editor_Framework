/**
 * @author Stanko 16.06.2014.
 * DiagramElementsSelection.java
 */
package gdiagram.model;

import gdiagram.model.elements.DiagramElement;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Stanko
 *
 */
public class DiagramElementsSelection implements Transferable, ClipboardOwner{
	static public DataFlavor elementFlavor;
	private DataFlavor[] supportedFlavors = { elementFlavor };
	public ArrayList<DiagramElement> diagramElements = new ArrayList<DiagramElement>();

	@SuppressWarnings("unchecked")
	public DiagramElementsSelection(ArrayList<DiagramElement> elements) {
		diagramElements = (ArrayList<DiagramElement>) elements.clone();
		try {
			elementFlavor = new DataFlavor(
					Class.forName("java.util.ArrayList"), "Elements");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {

		if (flavor.equals(elementFlavor))
			return (diagramElements);
		else
			throw new UnsupportedFlavorException(elementFlavor);
	}

	public DataFlavor[] getTransferDataFlavors() {
		return (supportedFlavors);
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.equals(elementFlavor));
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}
}

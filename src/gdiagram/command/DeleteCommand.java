/**
 * @author Stanko 15.06.2014.
 * DeleteCommand.java
 */
package gdiagram.command;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.view.DiagramView;

import java.util.ArrayList;

import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
public class DeleteCommand extends AbstractCommand {

	private ArrayList<DiagramElement> deletedElements;
	private DiagramView med;
	
	@SuppressWarnings("unchecked")
	public DeleteCommand(DiagramView med) {
		deletedElements = (ArrayList<DiagramElement>) med.getDiagram().getSelModel().getSelectionList().clone();
		Diagram diagram = med.getDiagram();
		for(int i=0; i<diagram.getModel().getElementsCount(); i++) {
			if(diagram.getModel().getElementAt(i) instanceof LinkElement) {
				LinkElement link = (LinkElement) med.getDiagram().getModel().getElementAt(i);
				DiagramDevice startDev = link.getStartDevice();
				DiagramDevice endDev = link.getEndDevice();
				if(diagram.getSelModel().isElementSelected(startDev) 
						|| diagram.getSelModel().isElementSelected(endDev))
					if(!diagram.getSelModel().isElementSelected(link))
					deletedElements.add(link);
			}
		}
		
		
		this.med = med;
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		Diagram diagram = med.getDiagram();
		
		diagram.getSelModel().removeAllFromSelectionList();
		TreePath tp = new TreePath(diagram.getPath());
		MainFrame.getInstance().getWorkspaceTree().setSelectionPath(tp);
		
		for(DiagramElement element : deletedElements) {
			
			if (element instanceof LinkElement)
				unsetLink((LinkElement) element);
			diagram.getModel().removeElement(element);
			MainFrame.getInstance().repaint();
		}
		diagram.getSelModel().removeAllFromSelectionList();
	}

	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		for(DiagramElement element : deletedElements) {
			if(element instanceof LinkElement)
				setLink((LinkElement)element);
			med.getDiagram().getModel().addDiagramElement(element);
		}
	}

	private void unsetLink(LinkElement link) {
		link.getInput().setUsed(false);
		link.getOutput().setUsed(false);
	}
	
	private void setLink(LinkElement link) {
		link.getInput().setUsed(true);
		link.getOutput().setUsed(true);
	}
	
}

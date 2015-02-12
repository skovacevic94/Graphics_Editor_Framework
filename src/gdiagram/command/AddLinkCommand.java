/**
 * @author Stanko 15.06.2014.
 * AddLinkCommand.java
 */
package gdiagram.command;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.elements.LinkElement;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 *
 */
public class AddLinkCommand extends AbstractCommand{

	private Diagram owner;
	private LinkElement link;
	private boolean firstTime;
	
	public AddLinkCommand(LinkElement link, Diagram owner) {
		this.owner = owner;
		this.link = link;
		firstTime = true;
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#doCommand()
	 */
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if(firstTime) {
			firstTime = false;
			link.setName("Link " + owner.getModel().getElementsCount());
			
			owner.getSelModel().removeAllFromSelectionList();
			MainFrame.getInstance().getWorkspaceTree().clearSelection();
			owner.getSelModel().addToSelectionList(link);
			SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
			
			return;
		}
		
		owner.getModel().addDiagramElement(link);
		
		link.getInput().setUsed(true);
		link.getOutput().setUsed(true);
		
		owner.getSelModel().removeAllFromSelectionList();
		MainFrame.getInstance().getWorkspaceTree().clearSelection();
		owner.getSelModel().addToSelectionList(link);
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
		owner.getModel().fireUpdatePerformed();
	}

	/* (non-Javadoc)
	 * @see gdiagram.command.AbstractCommand#undoCommand()
	 */
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		link.getInput().setUsed(false);
		link.getOutput().setUsed(false);
		owner.getSelModel().removeAllFromSelectionList();
		
		TreePath tp = new TreePath(owner.getPath());
		MainFrame.getInstance().getWorkspaceTree().setSelectionPath(tp);
		owner.getModel().removeElement(link);
		owner.getModel().fireUpdatePerformed();
	}

}

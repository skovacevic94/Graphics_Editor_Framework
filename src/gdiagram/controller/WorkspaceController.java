/**
 * @author Stanko 27.04.2014.
 * WorkspaceController.java
 */
package gdiagram.controller;

import gdiagram.core.MainFrame;
import gdiagram.gui.Pallete;
import gdiagram.model.Diagram;
import gdiagram.model.DiagramSelectionModel;
import gdiagram.model.Project;
import gdiagram.model.Workspace;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.state.CircleState;
import gdiagram.state.LinkState;
import gdiagram.state.RectangleState;
import gdiagram.state.SelectState;
import gdiagram.state.StarState;
import gdiagram.state.StateManager;
import gdiagram.state.TriangleState;
import gdiagram.view.DiagramView;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 * 
 */
public class WorkspaceController implements TreeSelectionListener {

	private static Diagram lastSelectedDiagram = null;
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		TreePath tPath = e.getPath();
		if( (e.getPath().getLastPathComponent() instanceof Project) || 
			(e.getPath().getLastPathComponent() instanceof Workspace))
			MainFrame.getInstance().getActionManager().getSearchAction().setEnabled(false);
		else
			MainFrame.getInstance().getActionManager().getSearchAction().setEnabled(true);
		
		DiagramView med = (DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame();
		if((e.getPath().getLastPathComponent() instanceof DiagramDevice) &&
				med.getDiagram().getSelModel().getSelectionListSize() == 1) {
			MainFrame.getInstance().getActionManager().getRotateLeftAction().setEnabled(true);
			MainFrame.getInstance().getActionManager().getRotateRightAction().setEnabled(true);
		} else {
			MainFrame.getInstance().getActionManager().getRotateLeftAction().setEnabled(false);
			MainFrame.getInstance().getActionManager().getRotateRightAction().setEnabled(false);
		}
	
		Clipboard mainClipboard = MainFrame.getInstance().getClipboard();
		Transferable transferable = mainClipboard.getContents(MainFrame.getInstance());
		if(transferable == null)
			MainFrame.getInstance().getActionManager().getPasteAction().setEnabled(false);
		else
			MainFrame.getInstance().getActionManager().getPasteAction().setEnabled(true);
		
		boolean failCombo = false;
		ArrayList<DiagramElement> selected = new ArrayList<DiagramElement>();
		if(med != null && med.getDiagram() != null && med.getDiagram().getSelModel() != null)
			selected = med.getDiagram().getSelModel().getSelectionList();
		for(DiagramElement element : selected) {
			if(element instanceof LinkElement) {
				LinkElement link = (LinkElement) element;
				DiagramDevice startDev = link.getStartDevice();
				DiagramDevice endDev = link.getEndDevice();
				if(!med.getDiagram().getSelModel().isElementSelected(startDev) ||
						!med.getDiagram().getSelModel().isElementSelected(endDev)) {
					failCombo = true;		
					break;
				}
			}
		}
		
		if(failCombo) {
			MainFrame.getInstance().getActionManager().getCutAction().setEnabled(false);
			MainFrame.getInstance().getActionManager().getCopyAction().setEnabled(false);
		} else {
			MainFrame.getInstance().getActionManager().getCutAction().setEnabled(true);
			MainFrame.getInstance().getActionManager().getCopyAction().setEnabled(true);
		}
		
		for (int i = 0; i < tPath.getPathCount(); i++) {
			if (tPath.getPathComponent(i) instanceof Diagram) {
				Diagram diagram = (Diagram) tPath.getPathComponent(i);
				if(lastSelectedDiagram != null && !lastSelectedDiagram.equals(diagram)) 
					lastSelectedDiagram.getSelModel().removeAllFromSelectionList();
				
				lastSelectedDiagram = diagram;
				
				JInternalFrame[] frames = MainFrame.getInstance()
						.getDesktopPane().getAllFrames();
				for (JInternalFrame frame : frames) {
					if (((DiagramView) frame).getDiagram().equals(diagram)
							&& frame.isVisible())
						try {
							frame.setSelected(true);

							StateManager stateManager = ((DiagramView) frame)
									.getStateManager();
							Pallete pallete = MainFrame.getInstance()
									.getPallete();
							if (stateManager.getCurrentState() instanceof SelectState) {
								pallete.getGroup().setSelected(
										pallete.getSelBtn().getModel(), true);
							}
							else if (stateManager.getCurrentState() instanceof StarState) {
								pallete.getGroup().setSelected(
										pallete.getStarBtn().getModel(), true);
							}
							else if(stateManager.getCurrentState() instanceof CircleState) {
								pallete.getGroup().setSelected(
										pallete.getCircleBtn().getModel(), true);
							}
							else if(stateManager.getCurrentState() instanceof RectangleState) {
								pallete.getGroup().setSelected(
										pallete.getRectBtn().getModel(), true);
							}
							else if (stateManager.getCurrentState() instanceof TriangleState) {
								pallete.getGroup().setSelected(
										pallete.getTriBtn().getModel(), true);
							}
							else if (stateManager.getCurrentState() instanceof LinkState) {
								pallete.getGroup().setSelected(
										pallete.getLinkBtn().getModel(), true);
							}
							break;
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		}
		
		TreePath[] tPaths = MainFrame.getInstance().getWorkspaceTree().getSelectionPaths();
		if(tPaths == null)
			return;
		
		if(lastSelectedDiagram != null)
			lastSelectedDiagram.getSelModel().removeAllFromSelectionList();;
			
		for (int i = 0; i < tPaths.length; i++) {
			for (int j = 0; j < tPaths[i].getPathCount(); j++) {
				if (tPaths[i].getPathComponent(j) instanceof DiagramElement) {
					DiagramView dView = (DiagramView) MainFrame.getInstance().getDesktopPane().getSelectedFrame();
					Diagram diagram = dView.getDiagram();
					
					DiagramSelectionModel selModel = diagram.getSelModel();
					
					selModel.selectOnFrame((DiagramElement) tPaths[i]
							.getPathComponent(j));
				}
			}
		}
	}
	
	public static Diagram getLastSelDiagram() {
		return lastSelectedDiagram;
	}
}

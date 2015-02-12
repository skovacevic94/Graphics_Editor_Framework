/**
 * @author Stanko 20.05.2014.
 * StatusBar.java
 */
package gdiagram.gui;

import gdiagram.controller.WorkspaceController;
import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.DiagramSelectionModel;
import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.model.elements.RectangleElement;
import gdiagram.model.elements.StarElement;
import gdiagram.model.elements.TriangleElement;
import gdiagram.state.CircleState;
import gdiagram.state.LinkState;
import gdiagram.state.RectangleState;
import gdiagram.state.SelectState;
import gdiagram.state.StarState;
import gdiagram.state.StateManager;
import gdiagram.state.TriangleState;
import gdiagram.view.DiagramView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class StatusBar extends JPanel {
	
	private StatusPane status=new StatusPane("State");
	private StatusPane elementType=new StatusPane("Element type");
	private StatusPane elementName=new StatusPane("Element name");
	private StatusPane position=new StatusPane("Position");
	private StatusPane dimension=new StatusPane("Dimension");
	
	Thread refresher;

	public StatusBar(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(status);
		add(elementType);
		add(elementName);
		add(position);
		add(dimension);
		
		refresher = new Thread(new Refresher());
		refresher.start();
	}
	
	private class Refresher implements Runnable {

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				Diagram d = WorkspaceController.getLastSelDiagram();
				if(d == null)
					continue;
				
				DiagramSelectionModel dSel = d.getSelModel();
				DiagramView med = null;
				
				JInternalFrame[] frames = MainFrame.getInstance().getDesktopPane().getAllFrames();
				for(JInternalFrame frame : frames) {
					if(((DiagramView)frame).getDiagram().equals(d)) {
						med = (DiagramView)frame;
					}
				}
				
				if(med == null) {
					continue;
				}
				
				StateManager stateManager = med.getStateManager();
				if(stateManager.getCurrentState() instanceof SelectState)
					MainFrame.getInstance().getStatusBar().setStatus("Select");
				else if(stateManager.getCurrentState() instanceof StarState)
					MainFrame.getInstance().getStatusBar().setStatus("Star");
				else if(stateManager.getCurrentState() instanceof TriangleState)
					MainFrame.getInstance().getStatusBar().setStatus("Triangle");
				else if(stateManager.getCurrentState() instanceof LinkState)
					MainFrame.getInstance().getStatusBar().setStatus("Link");
				else if(stateManager.getCurrentState() instanceof CircleState)
					MainFrame.getInstance().getStatusBar().setStatus("Circle");
				else if(stateManager.getCurrentState() instanceof RectangleState)
					MainFrame.getInstance().getStatusBar().setStatus("Rectangle");
				
				if(dSel.getSelectionListSize() == 0 || dSel.getSelectionListSize() > 1) {
					MainFrame.getInstance().getStatusBar().setElementType("");
					MainFrame.getInstance().getStatusBar().setElementName("");
					MainFrame.getInstance().getStatusBar().setPosition("");
					MainFrame.getInstance().getStatusBar().setDimension("");
				
					continue;
				}
				
				DiagramElement selected = dSel.getElementFromSelectionListAt(0);
				if(selected instanceof StarElement)
					MainFrame.getInstance().getStatusBar().setElementType("Star");
				else if(selected instanceof TriangleElement)
					MainFrame.getInstance().getStatusBar().setElementType("Triangle");
				else if(selected instanceof CircleElement)
					MainFrame.getInstance().getStatusBar().setElementType("Circle");
				else if(selected instanceof RectangleElement)
					MainFrame.getInstance().getStatusBar().setElementType("Rectangle");
				else 
					MainFrame.getInstance().getStatusBar().setElementType("Link");
					
				MainFrame.getInstance().getStatusBar().setElementName(selected.getName());
				
				if(selected instanceof LinkElement) {
					MainFrame.getInstance().getStatusBar().setPosition("");
					MainFrame.getInstance().getStatusBar().setDimension("");
					continue;
				}
				
				MainFrame.getInstance().getStatusBar().setPosition("(" + ((DiagramDevice)selected).getPosition().getX() 
						+ "," + ((DiagramDevice)selected).getPosition().getY() + ")");
				MainFrame.getInstance().getStatusBar().setDimension(((DiagramDevice)selected).getSize().getWidth() 
						+ " X " + ((DiagramDevice)selected).getSize().getHeight());
			}
		}
		
	}
	
	private class StatusPane extends JLabel{
		public StatusPane (String text){
			setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			setBackground(Color.GRAY);
			setPreferredSize(new Dimension(170,20));
			setHorizontalAlignment(CENTER);
			setText(text);
		}
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status.setText(status);
	}

	/**
	 * @param elementType the elementType to set
	 */
	public void setElementType(String elementType) {
		this.elementType.setText(elementType);
	}

	/**
	 * @param elementName the elementName to set
	 */
	public void setElementName(String elementName) {
		this.elementName.setText(elementName);
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position.setText(position);
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(String dimension) {
		this.dimension.setText(dimension);
	}
}

/**
 * @author Stanko 27.04.2014.
 * Toolbar.java
 */
package gdiagram.gui;

import gdiagram.core.MainFrame;

import javax.swing.JToolBar;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class ToolBar extends JToolBar{
	public ToolBar() {
		super();
		add(MainFrame.getInstance().getActionManager().getNewProjectAction());
		add(MainFrame.getInstance().getActionManager().getNewDiagramAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getCloseDiagramAction());
		add(MainFrame.getInstance().getActionManager().getCloseAllDiagramAction());
		add(MainFrame.getInstance().getActionManager().getCloseProjectAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getCutAction());
		add(MainFrame.getInstance().getActionManager().getCopyAction());
		add(MainFrame.getInstance().getActionManager().getPasteAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getUndoAction());
		add(MainFrame.getInstance().getActionManager().getRedoAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getRotateLeftAction());
		add(MainFrame.getInstance().getActionManager().getRotateRightAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getDiagramPreviusAction());
		add(MainFrame.getInstance().getActionManager().getDiagramNextAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getZoomInAction());
		add(MainFrame.getInstance().getActionManager().getZoomOutAction());
		add(MainFrame.getInstance().getActionManager().getBfZoomAction());
		add(MainFrame.getInstance().getActionManager().getLassoZoomAction());
		
		addSeparator();
		
		add(MainFrame.getInstance().getActionManager().getHelpAboutAction());
		
		setFloatable(false);
	}
}

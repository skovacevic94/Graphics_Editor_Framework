package gdiagram.gui;

import gdiagram.core.MainFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{
	public MenuBar() {
		JMenu fileMenu = new JMenu("File");
		{
			fileMenu.add(MainFrame.getInstance().getActionManager().getNewProjectAction());
			fileMenu.add(MainFrame.getInstance().getActionManager().getNewDiagramAction());
			
			fileMenu.addSeparator();
			
			fileMenu.add(MainFrame.getInstance().getActionManager().getOpenProjectAction());
			fileMenu.add(MainFrame.getInstance().getActionManager().getSaveProjectAction());
			
			fileMenu.addSeparator();
			
			fileMenu.add(MainFrame.getInstance().getActionManager().getImportDiagramAction());
			fileMenu.add(MainFrame.getInstance().getActionManager().getExportDiagramAction());
			
			fileMenu.addSeparator();
				
			fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());
		}
		add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		{
			editMenu.add(MainFrame.getInstance().getActionManager().getSearchAction());
		
			editMenu.addSeparator();
			
			editMenu.add(MainFrame.getInstance().getActionManager().getCutAction());
			editMenu.add(MainFrame.getInstance().getActionManager().getCopyAction());
			editMenu.add(MainFrame.getInstance().getActionManager().getPasteAction());
			
			editMenu.addSeparator();
			
			editMenu.add(MainFrame.getInstance().getActionManager().getUndoAction());
			editMenu.add(MainFrame.getInstance().getActionManager().getRedoAction());
			
			editMenu.addSeparator();
			
			editMenu.add(MainFrame.getInstance().getActionManager().getRotateLeftAction());
			editMenu.add(MainFrame.getInstance().getActionManager().getRotateRightAction());
			
			editMenu.addSeparator();
			
			editMenu.add(MainFrame.getInstance().getActionManager().getSelectAllAction());
			editMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
		}
		add(editMenu);
		
		JMenu windowMenu = new JMenu("Window");
		{
			windowMenu.add(MainFrame.getInstance().getActionManager().getZoomInAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getZoomOutAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getBfZoomAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getLassoZoomAction());
			
			windowMenu.addSeparator();
			
			windowMenu.add(MainFrame.getInstance().getActionManager().getCascadeDiagramAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getTileHorizontallyDiagramAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getTileVerticallyDiagramAction());
			
			windowMenu.addSeparator();
			
			windowMenu.add(MainFrame.getInstance().getActionManager().getDiagramNextAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getDiagramPreviusAction());
			
			windowMenu.addSeparator();

			windowMenu.add(MainFrame.getInstance().getActionManager().getCloseDiagramAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getCloseProjectAction());
			windowMenu.add(MainFrame.getInstance().getActionManager().getCloseAllDiagramAction());
		}
		add(windowMenu);
		
		JMenu helpMenu = new JMenu("Help");
		{
			helpMenu.add(MainFrame.getInstance().getActionManager().getHelpAboutAction());
		}
		add(helpMenu);
	}
}

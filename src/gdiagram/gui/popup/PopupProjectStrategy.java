/**
 * @author Stanko 28.04.2014.
 * PopupProjectStrategy.java
 */
package gdiagram.gui.popup;

import gdiagram.core.MainFrame;

/**
 * @author Stanko
 *
 */
public class PopupProjectStrategy implements PopupStrategy{

	@Override
	public void execute(PopupMenu popupMenu) {
		// TODO Auto-generated method stub
		popupMenu.add(MainFrame.getInstance().getActionManager().getNewDiagramAction());
		
		popupMenu.addSeparator();

		popupMenu.add(MainFrame.getInstance().getActionManager().getRenameAction());
		popupMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
	
		popupMenu.addSeparator();
		
		popupMenu.add(MainFrame.getInstance().getActionManager().getCloseProjectAction());
	}

}

/**
 * @author Stanko 28.04.2014.
 * DiagramPopupStrategy.java
 */
package gdiagram.gui.popup;

import gdiagram.core.MainFrame;

/**
 * @author Stanko
 *
 */
public class PopupDiagramStrategy implements PopupStrategy{

	@Override
	public void execute(PopupMenu popupMenu) {
		// TODO Auto-generated method stub
		popupMenu.add(MainFrame.getInstance().getActionManager().getSearchAction());
		
		popupMenu.addSeparator();
		
		popupMenu.add(MainFrame.getInstance().getActionManager().getRenameAction());
		popupMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
		
		popupMenu.addSeparator();
		
		popupMenu.add(MainFrame.getInstance().getActionManager().getCloseDiagramAction());
	}

}

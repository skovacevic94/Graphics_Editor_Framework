/**
 * @author Stanko 27.05.2014.
 * PopupElementStrategy.java
 */
package gdiagram.gui.popup;

import gdiagram.actions.PropertiesAction;
import gdiagram.core.MainFrame;
import gdiagram.model.elements.DiagramElement;

/**
 * @author Stanko
 *
 */
public class PopupElementStrategy implements PopupStrategy{

	private PropertiesAction action;
	
	public PopupElementStrategy(DiagramElement element) {
		action = new PropertiesAction(element);
	}
	
	/* (non-Javadoc)
	 * @see gdiagram.gui.popup.PopupStrategy#execute(gdiagram.gui.popup.PopupMenu)
	 */
	@Override
	public void execute(PopupMenu popupMenu) {
		// TODO Auto-generated method stub
		popupMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
		
		popupMenu.addSeparator();
		
		popupMenu.add(action);
	}

}

/**
 * @author Stanko 28.04.2014.
 * PopupMenu.java
 */
package gdiagram.gui.popup;

import javax.swing.JPopupMenu;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu{
	
	private PopupStrategy initializer;
	
	public PopupMenu() {
	}
	
	public void init() {
		initializer.execute(this);
	}
	
	/**
	 * @param initializer the initializer to set
	 */
	public void setInitializer(PopupStrategy initializer) {
		this.initializer = initializer;
	}
}

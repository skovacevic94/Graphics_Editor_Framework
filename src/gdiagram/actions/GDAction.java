/**
 * @author Stanko 27.04.2014.
 * GDAction.java
 */
package gdiagram.actions;

import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public abstract class GDAction extends AbstractAction{
	public Icon loadIcon(String fileName) {
		URL imageURL = getClass().getResource("/gdiagram/res/" + fileName);
		Icon icon = null;
		
		if(imageURL != null)
			icon = new ImageIcon(imageURL);
		return icon;
	}
}

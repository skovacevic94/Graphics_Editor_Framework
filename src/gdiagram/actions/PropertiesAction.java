/**
 * @author Stanko 27.05.2014.
 * PropertiesAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.gui.Properties;
import gdiagram.model.elements.DiagramElement;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class PropertiesAction extends GDAction{

	private DiagramElement element;
	
	public PropertiesAction(DiagramElement element) {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F4, ActionEvent.SHIFT_MASK));
		putValue(SMALL_ICON, loadIcon("null"));
		putValue(NAME, "Properties");
		putValue(SHORT_DESCRIPTION, "Properties");
		
		this.element = element;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Properties prop = new Properties(element);
		prop.setVisible(true);
		MainFrame.getInstance().repaint();
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}

}

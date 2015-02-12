/**
 * @author Stanko 26.05.2014.
 * SearchAction.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class SearchAction extends GDAction{

	public SearchAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("srch.png"));
		putValue(NAME, "Search");
		putValue(SHORT_DESCRIPTION, "Search diagram");
		setEnabled(false);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String inputName = JOptionPane.showInputDialog(MainFrame.getInstance(), "Name of the element:", "Search", JOptionPane.DEFAULT_OPTION);
		String regex = "";
		
		if(inputName == null)
			return;
		
		int starIndex = -1;
		while((starIndex = inputName.indexOf("*")) != -1) {
			regex += inputName.substring(0, starIndex);
			if(regex.length() < 2 || !regex.substring(regex.length()-2, regex.length()).equals(".*"))
				regex += ".*";
			
			inputName = inputName.substring(starIndex+1);
		}
		if(starIndex == -1 || starIndex < inputName.length()-1)
			regex += inputName;
		
		Diagram diagram = ((DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame()).getDiagram();
	
		MainFrame.getInstance().getWorkspaceTree().clearSelection();
		diagram.getSelModel().removeAllFromSelectionList();

		Pattern p = Pattern.compile(regex);

		for (DiagramElement element : diagram.getModel().getDiagramElements()) {
			if (p.matcher(element.getName()).matches()) {
				diagram.getSelModel().addToSelectionList(element);
		    }
		}
		MainFrame.getInstance().repaint();
	}
}

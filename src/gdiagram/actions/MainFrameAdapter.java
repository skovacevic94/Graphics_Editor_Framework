/**
 * @author Stanko 29.04.2014.
 * MainFrameAdapter.java
 */
package gdiagram.actions;

import gdiagram.core.MainFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Stanko
 *
 */
public class MainFrameAdapter extends WindowAdapter{

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		MainFrame.getInstance().getActionManager().getExitAction().actionPerformed(null);
	}
}

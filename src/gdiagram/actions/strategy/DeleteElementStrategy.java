/**
 * @author Stanko 20.05.2014.
 * DeleteElementStrategy.java
 */
package gdiagram.actions.strategy;

import gdiagram.command.DeleteCommand;
import gdiagram.core.MainFrame;
import gdiagram.view.DiagramView;

/**
 * @author Stanko
 * 
 */
public class DeleteElementStrategy implements DeleteActionStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gdiagram.actions.strategy.DeleteActionStrategy#execute(java.lang.Object)
	 */
	@Override
	public void execute(Object selected) {
		// TODO Auto-generated method stub
		DiagramView med = (DiagramView) MainFrame.getInstance()
				.getDesktopPane().getSelectedFrame();
		med.getCommandManager().addCommand(new DeleteCommand(med));
	}

}

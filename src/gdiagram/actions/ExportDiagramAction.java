package gdiagram.actions;

import gdiagram.core.MainFrame;
import gdiagram.model.Diagram;
import gdiagram.view.DiagramView;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class ExportDiagramAction extends GDAction{
	

	public ExportDiagramAction() {		
		putValue(SMALL_ICON, loadIcon("exportD.png"));
		putValue(NAME, "Export diagram");
		putValue(SHORT_DESCRIPTION, "Export selected diagram");
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new DiagramFileFilter());
		File exportFile;
		
		System.out.println("Comm:" + arg0.getActionCommand());
		System.out.println("Source:" + arg0.getSource());
		if((DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame() == null)
			return;
		
		Diagram	diagram = ((DiagramView)MainFrame.getInstance().getDesktopPane().getSelectedFrame()).getDiagram();
			
		if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION)
				exportFile = new File(jfc.getSelectedFile().toString() + ".gdf");
		else
			return;
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(exportFile));
			os.writeObject(diagram);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

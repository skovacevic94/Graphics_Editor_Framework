/**
 * @author Stanko 27.05.2014.
 * WorkspaceFileFilter.java
 */
package gdiagram.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Stanko
 *
 */
public class WorkspaceFileFilter extends FileFilter{

	@Override
	public String getDescription() {
		return "GrafEditor Workspace Files (*.gpf)";
	}

	@Override
	public boolean accept(File f) {
		return (f.isDirectory() || 
                f.getName().toLowerCase().endsWith(".gpw"));
	}
	
}

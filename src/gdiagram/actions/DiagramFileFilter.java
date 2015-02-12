/**
 * @author Stanko 27.05.2014.
 * DiagramFileFilter.java
 */
package gdiagram.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Stanko
 *
 */
public class DiagramFileFilter extends FileFilter{
	
	@Override
	public String getDescription() {
		return "GrafEditor Diagram Files (*.gdf)";
	}

	@Override
	public boolean accept(File f) {
		return (f.isDirectory() || 
                f.getName().toLowerCase().endsWith(".gdf"));
	}

}

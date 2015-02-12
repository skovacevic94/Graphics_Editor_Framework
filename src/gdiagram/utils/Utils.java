/**
 * @author Stanko 26.05.2014.
 * Utils.java
 */
package gdiagram.utils;

import java.util.ArrayList;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Stanko
 * 
 */
public class Utils {
	public static TreePath getPath(TreeNode treeNode) {
		ArrayList<Object> nodes = new ArrayList<Object>();
		if (treeNode != null) {
			nodes.add(treeNode);
			treeNode = treeNode.getParent();
			while (treeNode != null) {
				nodes.add(0, treeNode);
				treeNode = treeNode.getParent();
			}
		}

		return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
	}

}

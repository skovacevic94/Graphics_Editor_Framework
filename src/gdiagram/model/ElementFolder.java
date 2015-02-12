/**
 * @author Stanko 25.05.2014.
 * ElementFolder.java
 */
package gdiagram.model;

import gdiagram.model.elements.CircleElement;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.model.elements.RectangleElement;
import gdiagram.model.elements.StarElement;
import gdiagram.model.elements.TriangleElement;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class ElementFolder extends DefaultMutableTreeNode {

	private String folderName;
	private Diagram owner;

	public ElementFolder(String folderName, Diagram owner) {
		this.folderName = folderName;
		this.owner = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#getChildCount()
	 */
	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		int childCount = 0;

		ArrayList<DiagramElement> elements = owner.getModel()
				.getDiagramElements();
		for (DiagramElement element : elements) {
			switch (folderName) {
			case "Circles": {
				if(element instanceof CircleElement)
					childCount++;
			}
				break;
			case "Rectangles": {
				if(element instanceof RectangleElement)
					childCount++;
			}
				break;
			case "Links": {
				if (element instanceof LinkElement)
					childCount++;
			}
				break;
			case "Stars": {
				if (element instanceof StarElement)
					childCount++;
			}
				break;
			case "Triangles": {
				if (element instanceof TriangleElement)
					childCount++;
			}
				break;
			}
		}
		return childCount;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		// TODO Auto-generated method stub
		ArrayList<DiagramElement> elements = owner.getModel().getDiagramElements();
		int childCount = 0;
		for(DiagramElement element : elements) {
			if(folderName.equals("Circles")) {
				if(element instanceof CircleElement) {
					if(childCount == index)
						return element;
					else
						childCount++;
				}
			}
			else if(folderName.equals("Rectangles")) {
				if(element instanceof RectangleElement) {
					if(childCount == index) 
						return element;
					else
						childCount++;
				}
			}
			else if(folderName.equals("Links")) {
				if(element instanceof LinkElement) {
					if(childCount == index) 
						return element;
					else
						childCount++;
				}
			} else if(folderName.equals("Stars")) {
				if(element instanceof StarElement) {
					if(childCount == index) 
						return element;
					else
						childCount++;
				}
			} else if(folderName.equals("Triangles")) {
				if(element instanceof TriangleElement) {
					if(childCount == index) 
						return element;
					else
						childCount++;
				}
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getIndex(TreeNode aChild) {
		// TODO Auto-generated method stub
		for(int index = 0; index<getChildCount(); index++) {
			if(getChildAt(index).equals(aChild))
				return index;
		}
		
		return -1;
	}

	public String getFolderName() {
		return this.folderName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return folderName;
	}
}

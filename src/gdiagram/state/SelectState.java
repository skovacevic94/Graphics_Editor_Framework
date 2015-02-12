package gdiagram.state;

import gdiagram.core.MainFrame;
import gdiagram.gui.Properties;
import gdiagram.gui.popup.PopupAddElementStrategy;
import gdiagram.gui.popup.PopupElementStrategy;
import gdiagram.gui.popup.PopupMenu;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.view.DiagramView;
import gdiagram.view.DiagramView.Handle;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class SelectState extends State {
	private DiagramView med;

	private boolean selected = false;
	private int elementID = -1;
	private int elementInMotion = -1;
	private Handle handleInMotion = null;

	private int mouseButton = 0;

	public SelectState(DiagramView md) {
		med = md;
	}

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();

		Point position = e.getPoint();
		med.transformToUserSpace(position);

		if (e.getButton() == MouseEvent.BUTTON1) {
			handleInMotion = med.getDeviceAndHandleForPoint(position);
			if (handleInMotion == null) {
				elementInMotion = med.getDiagram().getModel()
						.getElementAtPosition(position);
				if (elementInMotion != -1) {
					selected = true;
					if (e.getClickCount() == 2) {
						DiagramElement element = med.getDiagram().getModel()
								.getElementAt(elementInMotion);
						Properties prop = new Properties(element);
						prop.setVisible(true);
					}
					DiagramElement element = med.getDiagram().getModel()
							.getElementAt(elementInMotion);
					if (med.getDiagram().getSelModel()
							.isElementSelected(element)) {
						elementID = elementInMotion;
					} else {
						if (!e.isControlDown()) {
							med.getDiagram().getSelModel()
									.removeAllFromSelectionList();
							MainFrame.getInstance().getWorkspaceTree()
									.clearSelection();
						}
						med.getDiagram().getSelModel()
								.addToSelectionList(element);
					}
				} else {
					selected = false;
					elementID = -1;
					med.getDiagram().getSelModel().removeAllFromSelectionList();
					MainFrame.getInstance().getWorkspaceTree().clearSelection();
					SwingUtilities.updateComponentTreeUI(MainFrame
							.getInstance().getWorkspaceTree());
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Point position = e.getPoint();
		selected = false;

		if (e.getButton() == MouseEvent.BUTTON3) {
			handleInMotion = med.getDeviceAndHandleForPoint(position);
			if (handleInMotion == null) {
				if (!e.isControlDown()) {
					PopupMenu popupMenu = new PopupMenu();
					elementInMotion = med.getDiagram().getModel()
							.getElementAtPosition(position);
					if (elementInMotion != -1) {
						DiagramElement element = med.getDiagram().getModel()
								.getElementAt(elementInMotion);

						if (med.getDiagram().getSelModel()
								.isElementSelected(element)) {
							med.getDiagram().getSelModel()
									.removeFromSelectionList(element);
							MainFrame
									.getInstance()
									.getWorkspaceTree()
									.removeSelectionPath(
											new TreePath(element.getPath()));
						} else {
							med.getDiagram().getSelModel()
									.addToSelectionList(element);
						}
						popupMenu.setInitializer(new PopupElementStrategy(
								element));
					} else {
						popupMenu.setInitializer(new PopupAddElementStrategy(e
								.getPoint()));
					}
					popupMenu.init();
					popupMenu.show(MainFrame.getInstance().getDesktopPane()
							.getSelectedFrame(), (int) position.getX(),
							(int) position.getY());
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			elementInMotion = med.getDiagram().getModel()
					.getElementAtPosition(position);
			if (elementInMotion != -1) {
				if (elementInMotion == elementID) {
					DiagramElement element = med.getDiagram().getModel()
							.getElementAt(elementInMotion);
					if (med.getDiagram().getSelModel()
							.isElementSelected(element)) {
						if (!e.isControlDown()) {
							med.getDiagram().getSelModel()
									.removeAllFromSelectionList();
							med.getDiagram().getSelModel()
									.addToSelectionList(element);
						} else {
							med.getDiagram().getSelModel()
									.removeAllFromSelectionList();
							MainFrame.getInstance().getWorkspaceTree().clearSelection();
						}
					} else {
						if (!e.isControlDown())
							med.getDiagram().getSelModel()
									.removeAllFromSelectionList();
						med.getDiagram().getSelModel()
								.addToSelectionList(element);
					}
					elementID = -1;
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Point2D position = e.getPoint();
		med.transformToUserSpace(position);
		med.setMouseCursor(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gdiagram.state.State#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (mouseButton == MouseEvent.BUTTON1) {
			Point position = e.getPoint();
			med.transformToUserSpace(position);
			handleInMotion = med.getDeviceAndHandleForPoint(position);
			if (handleInMotion != null) { 
				med.startResizeState();
				ResizeState resize = (ResizeState) med.getStateManager().getCurrentState();
				for(int i = 0 ; i < med.getDiagram().getSelModel().getSelectionListSize(); i++){
					if(med.getDiagram().getSelModel().getElementFromSelectionListAt(i) instanceof DiagramDevice){
						resize.getOldCommPos().add((Point2D) (((DiagramDevice) med.getDiagram().getSelModel().getElementFromSelectionListAt(i)).getPosition()).clone());
						resize.getOldCommScale().add(((DiagramDevice) med.getDiagram().getSelModel().getElementFromSelectionListAt(i)).getScale());
					}
				}
			} else {
				elementInMotion = med.getDiagram().getModel()
						.getElementAtPosition(position);
				if (selected) {
					if (!e.isControlDown())
						med.startMoveState();
					return;
				} else
					med.startLassoState();
			}
		}
	}
}

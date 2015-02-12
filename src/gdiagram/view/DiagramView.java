/**
 * @author Stanko 27.04.2014.
 * DiagramView.java
 */
package gdiagram.view;

import gdiagram.command.CommandManager;
import gdiagram.command.PasteCommand;
import gdiagram.controller.DiagramListener;
import gdiagram.core.MainFrame;
import gdiagram.events.UpdateEvent;
import gdiagram.events.UpdateListener;
import gdiagram.model.Diagram;
import gdiagram.model.elements.DiagramDevice;
import gdiagram.model.elements.DiagramElement;
import gdiagram.model.elements.LinkElement;
import gdiagram.state.StateManager;
import gdiagram.view.painters.ElementPainter;

import java.awt.Adjustable;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class DiagramView extends JInternalFrame implements UpdateListener,
		AdjustmentListener {

	double translateX = 0.0;
	double translateY = 0.0;
	double scaling = 1;

	final static double deltaXY = 10;
	final static double deltaS = 1.2;

	private int hScrollValue = 140;
	private int vScrollValue = 140;

	private AffineTransform transformation = new AffineTransform();

	static int openFrameCount = 0;

	static final int xOffset = 30, yOffset = 30;

	private Diagram diagram;

	private JPanel framework;
	private JScrollBar sbVertical;
	private JScrollBar sbHorizontal;

	private StateManager stateManager = new StateManager(this);

	private Point2D lastPosition = null;
	private Rectangle2D selectionRectangle = new Rectangle2D.Double();

	private CommandManager commandManager;

	private AutoScrollThread thread;

	public static final int CIRCLE = 0;
	public static final int RECTANGLE = 1;
	public static final int STAR = 2;
	public static final int TRIANGLE = 3;

	public DiagramView() {
		super("", true, true, true, true);
		++openFrameCount;

		commandManager = new CommandManager();
		stateManager = new StateManager(this);
		lastPosition = null;
		selectionRectangle = new Rectangle2D.Double();

		addInternalFrameListener(new DiagramListener());

		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		setIconifiable(true);
		setClosable(true);
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		framework = new Framework();
		framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
		framework.setBackground(Color.WHITE);
		add(framework, BorderLayout.CENTER);

		sbHorizontal = new JScrollBar(JScrollBar.HORIZONTAL, 140, 20, 0, 300);
		sbHorizontal.addAdjustmentListener(this);
		sbVertical = new JScrollBar(JScrollBar.VERTICAL, 140, 20, 0, 300);
		sbVertical.addAdjustmentListener(this);

		add(sbHorizontal, BorderLayout.SOUTH);
		add(sbVertical, BorderLayout.EAST);

		DiagramController c = new DiagramController();
		framework.addMouseListener(c);
		framework.addMouseMotionListener(c);
		framework.addMouseWheelListener(c);

		thread = new AutoScrollThread(this);
	}

	public enum Direction {
		Up, Down, Left, Right
	}

	public void paste() {
		getCommandManager().addCommand(new PasteCommand(getDiagram()));
	}

	public void autoScroll(Direction direction) {
		switch (direction) {
		case Left: {
			int valueScroll = sbHorizontal.getValue();
			if (valueScroll >= sbHorizontal.getMinimum()) {
				sbHorizontal.setValue(sbHorizontal.getValue() - 2);
				valueScroll = sbHorizontal.getValue();
				hScrollValue = valueScroll;
			}
			break;
		}
		case Up: {
			int valueScroll = sbVertical.getValue();
			if (valueScroll <= sbVertical.getMaximum()) {
				sbVertical.setValue(sbVertical.getValue() + 2);
				valueScroll = sbVertical.getValue();
				vScrollValue = valueScroll;
			}
			break;
		}
		case Right: {
			int valueScroll = sbHorizontal.getValue();
			if (valueScroll <= sbHorizontal.getMaximum()) {
				sbHorizontal.setValue(sbHorizontal.getValue() + 2);
				valueScroll = sbHorizontal.getValue();
				hScrollValue = valueScroll;
			}
			break;
		}
		case Down: {
			int valueScroll = sbVertical.getValue();
			if (valueScroll <= sbVertical.getMaximum()) {
				sbVertical.setValue(sbVertical.getValue() - 2);
				valueScroll = sbVertical.getValue();
				vScrollValue = valueScroll;
			}
			break;

		}

		}
		repaint();
	}

	public void transformToUserSpace(Point2D devSpace) {
		try {
			transformation.inverseTransform(devSpace, devSpace);
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setupTransformation() {
		transformation.setToIdentity();
		transformation.scale(scaling, scaling);
		transformation.translate(translateX, translateY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event
	 * .AdjustmentEvent)
	 */
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		if (e.getAdjustable().getOrientation() == Adjustable.HORIZONTAL) {
			if (hScrollValue < e.getValue())
				translateX += (double) ((e.getValue() - hScrollValue) * (-deltaXY))
						/ transformation.getScaleX();

			else
				translateX += (double) ((e.getValue() - hScrollValue) * (-deltaXY))
						/ transformation.getScaleX();

			hScrollValue = e.getValue();

		} else {
			if (vScrollValue < e.getValue())
				translateY += (double) ((e.getValue() - vScrollValue) * (-deltaXY))
						/ transformation.getScaleX();
			else
				translateY += (double) ((e.getValue() - vScrollValue) * (-deltaXY))
						/ transformation.getScaleX();

			vScrollValue = e.getValue();
		}
		setupTransformation();
		repaint();
	}

	public void zoomIn() {
		double newScaling = scaling;

		newScaling *= deltaS;

		if (newScaling < 0.2)
			newScaling = 0.2;
		if (newScaling > 5)
			newScaling = 5;

		Point2D oldPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(oldPosition);

		scaling = newScaling;
		setupTransformation();

		Point2D newPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(newPosition);

		translateX += newPosition.getX() - oldPosition.getX();
		translateY += newPosition.getY() - oldPosition.getY();

		sbVertical.setVisibleAmount((int) (20 / scaling));
		sbHorizontal.setVisibleAmount((int) (20 / scaling));

		setupTransformation();
	}

	public void zoomOut() {
		double newScaling = scaling;

		newScaling /= deltaS;

		if (newScaling < 0.2)
			newScaling = 0.2;
		if (newScaling > 5)
			newScaling = 5;

		Point2D oldPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(oldPosition);

		scaling = newScaling;
		setupTransformation();

		Point2D newPosition = new Point2D.Double(getWidth() / 2,
				getHeight() / 2);
		transformToUserSpace(newPosition);

		translateX += newPosition.getX() - oldPosition.getX();
		translateY += newPosition.getY() - oldPosition.getY();

		sbVertical.setVisibleAmount((int) (20 / scaling));
		sbHorizontal.setVisibleAmount((int) (20 / scaling));

		setupTransformation();
	}

	public void bestFitZoom() {
		double minX = 0, minY = 0, maxX = 0, maxY = 0;
		Iterator<DiagramElement> it = diagram.getModel().getElementsIterator();
		if (it.hasNext()) {
			DiagramElement element = (DiagramElement) it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				minX = device.getPosition().getX();
				minY = device.getPosition().getY();
				maxX = device.getPosition().getX()
						+ device.getSize().getWidth();
				maxY = device.getPosition().getY()
						+ device.getSize().getHeight();
				while (it.hasNext()) {
					element = (DiagramElement) it.next();
					if (element instanceof DiagramDevice) {
						device = (DiagramDevice) element;
						if (device.getPosition().getX() < minX)
							minX = device.getPosition().getX();
						if (device.getPosition().getX()
								+ device.getSize().getWidth() > maxX)
							maxX = device.getPosition().getX()
									+ device.getSize().getWidth();
						if (device.getPosition().getY() < minY)
							minY = device.getPosition().getY();
						if (device.getPosition().getY()
								+ device.getSize().getHeight() > maxY)
							maxY = device.getPosition().getY()
									+ device.getSize().getHeight();
					}
				}
			}
		}
		regionZoom(minX, maxX, minY, maxY);
	}

	public void regionZoom(double minX, double maxX, double minY, double maxY) {
		double minXX, maxXX, minYY, maxYY;
		maxXX = transformLineToUserSpaceX(maxX);
		minXX = transformLineToUserSpaceX(minX);
		maxYY = transformLineToUserSpaceY(maxY);
		minYY = transformLineToUserSpaceY(minY);

		double regionWidth = maxXX - minXX;
		double regionHeight = maxYY - minYY;
		int deviceWidth = this.getWidth();
		int deviceHeight = this.getHeight();
		double newScaling = transformation.getScaleX();
		if (regionWidth != 0 && regionHeight != 0) {
			double scaleX = (deviceWidth / regionWidth);
			double scaleY = (deviceHeight / regionHeight);

			if (scaleX < scaleY) {
				newScaling *= scaleX;
			} else {
				newScaling *= scaleY;
			}
			if (newScaling < 0.2)
				newScaling = 0.2;
			if (newScaling > 5)
				newScaling = 5;
			transformation.setToScale(newScaling, newScaling);
		}

		maxXX = transformLineToUserSpaceX(maxX);
		minXX = transformLineToUserSpaceX(minX);
		maxYY = transformLineToUserSpaceY(maxY);
		minYY = transformLineToUserSpaceY(minY);
		regionWidth = maxXX - minXX;
		regionHeight = maxYY - minYY;
		transformation.translate(
				(-minXX + (deviceWidth - regionWidth) / 2)
						/ transformation.getScaleX(),
				(-minYY + (deviceHeight - regionHeight) / 2)
						/ transformation.getScaleY());
		repaint();
	}

	public double transformLineToUserSpaceX(double deviceSpace) {
		return deviceSpace * transformation.getScaleX()
				+ transformation.getTranslateX();
	}

	public double transformLineToUserSpaceY(double deviceSpace) {
		return deviceSpace * transformation.getScaleY()
				+ transformation.getTranslateY();
	}

	public DiagramDevice getDeviceFromHandle(Point2D point) {
		DiagramElement element;
		DiagramDevice device;

		Iterator<DiagramElement> it = diagram.getSelModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			element = it.next();
			if (element instanceof DiagramDevice) {
				device = (DiagramDevice) element;
				if (getHandleForPoint(element, point) != null)
					return device;
			}
		}
		return null;
	}

	public void startStarState() {
		diagram.getSelModel().removeAllFromSelectionList();
		stateManager.setStarState();
		MainFrame.getInstance().getPallete().showNumIO();
	}

	public void startMoveState() {
		stateManager.setMoveState();
	}

	public void startResizeState() {
		stateManager.setResizeState();
	}

	public void startTrigState() {
		diagram.getSelModel().removeAllFromSelectionList();
		stateManager.setTrigState();
		MainFrame.getInstance().getPallete().showNumIO();
	}

	public void startCircleState() {
		diagram.getSelModel().removeAllFromSelectionList();
		stateManager.setCircleState();
		MainFrame.getInstance().getPallete().showNumIO();
	}

	public void startRectangleState() {
		diagram.getSelModel().removeAllFromSelectionList();
		stateManager.setRectState();
		MainFrame.getInstance().getPallete().showNumIO();
	}

	public void startSelectState() {
		stateManager.setSelectState();
		MainFrame.getInstance().getPallete().hideNumIO();
	}

	public void startLinkState() {
		diagram.getSelModel().removeAllFromSelectionList();
		stateManager.setLinkState();
		MainFrame.getInstance().getPallete().hideNumIO();
	}

	public void startLassoState() {
		stateManager.setLassoState();
	}

	public static void decFrameCount() {
		--openFrameCount;
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	public enum Handle {
		SouthEast, SouthWest, NorthEast, NorthWest
	}

	static final int handleSize = 7;

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
		this.setName(diagram.getDiagramName());
		this.diagram.getModel().addUpdateListener(this);
		this.diagram.getSelModel().addUpdateListener(this);
		setTitle(diagram.getDiagramName());
	}

	public Diagram getDiagram() {
		return diagram;
	}

	private class DiagramController extends MouseAdapter implements
			MouseMotionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			thread.scroll = false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			lastPosition = e.getPoint();
			transformToUserSpace(lastPosition);
			getStateManager().getCurrentState().mousePressed(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			getStateManager().getCurrentState().mouseReleased(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			getStateManager().getCurrentState().mouseDragged(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method
			// stubgetStateManager().getCurrentState().mouseMoved(e);
			getStateManager().getCurrentState().mouseMoved(e);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mouseWheelMoved(java.awt.event.
		 * MouseWheelEvent)
		 */
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if ((e.getModifiers() & MouseWheelEvent.CTRL_MASK) != 0) {
				double newScaling = scaling;
				if (e.getWheelRotation() > 0)
					newScaling *= (double) e.getWheelRotation() * deltaS;
				else
					newScaling /= -(double) e.getWheelRotation() * deltaS;
				if (newScaling < 0.2)
					newScaling = 0.2;
				if (newScaling > 5)
					newScaling = 5;

				Point2D oldPosition = e.getPoint();
				transformToUserSpace(oldPosition);

				scaling = newScaling;
				setupTransformation();

				Point2D newPosition = e.getPoint();
				transformToUserSpace(newPosition);

				translateX += newPosition.getX() - oldPosition.getX();
				translateY += newPosition.getY() - oldPosition.getY();

				sbVertical.setVisibleAmount((int) (20 / scaling));
				sbHorizontal.setVisibleAmount((int) (20 / scaling));

				setupTransformation();

			} else if ((e.getModifiers() & MouseWheelEvent.SHIFT_MASK) != 0) {
				translateX += (double) e.getWheelRotation() * deltaXY / scaling;
			} else {
				translateY += (double) e.getWheelRotation() * deltaXY / scaling;
			}

			setupTransformation();
			repaint();
		}

	}

	public void updatePerformed(UpdateEvent e) {
		repaint();
	}

	public double sizeToUserSpace(double deviceSize) {
		return deviceSize / transformation.getScaleX();
	}

	private class Framework extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			setBackground(Color.LIGHT_GRAY);
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.8f));

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2.transform(transformation);

			Iterator<DiagramElement> it = diagram.getModel()
					.getElementsIterator();
			while (it.hasNext()) {
				DiagramElement d = (DiagramElement) it.next();
				ElementPainter paint = (ElementPainter) d.getPainter();
				paint.paint(g2, d);
			}

			paintSelectionHandles(g2);

			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke((float) 1, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_BEVEL, 1f, new float[] { (float) 3,
							(float) 6 }, 0));
			g2.draw(selectionRectangle);
		}

	}

	private void paintSelectionHandles(Graphics2D g2) {
		Iterator<DiagramElement> it = diagram.getSelModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			DiagramElement element = it.next();
			if (element instanceof DiagramDevice) {
				DiagramDevice device = (DiagramDevice) element;
				// Iscrtavanje pravougaonika sa isprekidanom linijom

				if (device.getRotation() == 0
						|| device.getRotation() == Math.PI
						|| device.getRotation() == -Math.PI) {
					g2.setStroke(new BasicStroke((float) sizeToUserSpace(1),
							BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1f,
							new float[] { (float) sizeToUserSpace(3),
									(float) sizeToUserSpace(6) }, 0));
					g2.setPaint(Color.BLACK);
					g2.drawRect((int) device.getPosition().getX(), (int) device
							.getPosition().getY(), (int) device.getSize()
							.getWidth(), (int) device.getSize().getHeight());

				} else {
					g2.setStroke(new BasicStroke((float) sizeToUserSpace(1),
							BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1f,
							new float[] { (float) sizeToUserSpace(3),
									(float) sizeToUserSpace(6) }, 0));
					g2.setPaint(Color.BLACK);
					double razlika = (device.getSize().getWidth() - device
							.getSize().getHeight()) / 2;
					g2.drawRect((int) (device.getPosition().getX() + razlika),
							(int) (device.getPosition().getY() - razlika),
							(int) device.getSize().getHeight(), (int) device
									.getSize().getWidth());
				}
				for (Handle e : Handle.values()) {
					paintSelectionHandle(
							g2,
							getHandlePoint(device.getPosition(),
									device.getSize(), e, device));
				}

			} else {
				// isrtavanje handlova za link
				LinkElement link = (LinkElement) element;

				Point2D bp = null;
				bp = link.getOutput().getPosition();
				g2.setPaint(Color.BLACK);
				g2.setStroke(new BasicStroke((float) 2, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL));

				g2.drawRect((int) bp.getX() - handleSize / 2, (int) bp.getY()
						- handleSize / 2, handleSize, handleSize);

				Iterator<Point2D> itp = link.getPointsIterator();
				while (itp.hasNext()) {
					bp = (Point2D) itp.next();
					g2.drawRect((int) bp.getX() - handleSize / 2,
							(int) bp.getY() - handleSize / 2, handleSize,
							handleSize);

				}
				bp = link.getInput().getPosition();
				g2.drawRect((int) bp.getX() - handleSize / 2, (int) bp.getY()
						- handleSize / 2, handleSize, handleSize);
			}

		}
	}

	private void paintSelectionHandle(Graphics2D g2, Point2D position) {
		double size = handleSize;
		g2.fill(new Rectangle2D.Double(position.getX() - size / 2, position
				.getY() - size / 2, size, size));
	}

	public void setMouseCursor(Point2D point) {
		Handle handle = getDeviceAndHandleForPoint(point);

		if (handle != null) {
			Cursor cursor = null;

			switch (handle) {
			case SouthEast:
				cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
				break;
			case NorthWest:
				cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
				break;
			case SouthWest:
				cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
				break;
			case NorthEast:
				cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
				break;
			}
			framework.setCursor(cursor);
		} else
			framework.setCursor(Cursor.getDefaultCursor());
	}

	public Handle getDeviceAndHandleForPoint(Point2D point) {
		DiagramElement element;

		Iterator<DiagramElement> it = diagram.getSelModel()
				.getSelectionListIterator();
		while (it.hasNext()) {
			element = it.next();
			if (getHandleForPoint(element, point) == null)
				continue;
			return getHandleForPoint(element, point);
		}
		return null;
	}

	private Point2D getHandlePoint(Point2D topLeft, Dimension2D size,
			Handle handlePosition, DiagramDevice device) {
		double x = 0, y = 0;
		double razlika = (device.getSize().getWidth() - device.getSize()
				.getHeight()) / 2;
		if (device.getRotation() == 0 || device.getRotation() == Math.PI
				|| device.getRotation() == -Math.PI) {
			// Ako su gornji hendlovi
			if (handlePosition == Handle.NorthWest
					|| handlePosition == Handle.NorthEast) {
				y = topLeft.getY();
			}
			// Ako su donji
			if (handlePosition == Handle.SouthWest
					|| handlePosition == Handle.SouthEast) {
				y = topLeft.getY() + size.getHeight();
			}
			// Određivanje x koordinate
			// Ako su levi
			if (handlePosition == Handle.NorthWest
					|| handlePosition == Handle.SouthWest) {
				x = topLeft.getX();
			}
			// ako su desni
			if (handlePosition == Handle.NorthEast
					|| handlePosition == Handle.SouthEast) {
				x = topLeft.getX() + size.getWidth();
			}
		} else {
			if (handlePosition == Handle.NorthWest
					|| handlePosition == Handle.NorthEast) {
				y = topLeft.getY() - razlika;
			}
			// Ako su donji
			if (handlePosition == Handle.SouthWest
					|| handlePosition == Handle.SouthEast) {
				y = topLeft.getY() + size.getWidth() - razlika;
			}
			// Ako su levi
			if (handlePosition == Handle.NorthWest
					|| handlePosition == Handle.SouthWest) {
				x = topLeft.getX() + razlika;
			}
			// ako su desni
			if (handlePosition == Handle.NorthEast
					|| handlePosition == Handle.SouthEast) {
				x = topLeft.getX() + size.getHeight() + razlika;
			}
		}
		return new Point2D.Double(x, y);
	}

	public Handle getHandleForPoint(DiagramElement element, Point2D point) {
		for (Handle h : Handle.values()) {
			if (isPointInHandle(element, point, h)) {
				return h;
			}
		}
		return null;
	}

	private boolean isPointInHandle(DiagramElement element, Point2D point,
			Handle handle) {
		if (element instanceof DiagramDevice) {
			DiagramDevice device = (DiagramDevice) element;
			Point2D handleCenter = getHandlePoint(device.getPosition(),
					device.getSize(), handle, device);
			return ((Math.abs(point.getX() - handleCenter.getX()) <= (double) handleSize) && (Math
					.abs(point.getY() - handleCenter.getY()) <= (double) handleSize));
		} else
			return false;
	}

	public Point2D getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(Point2D lastPosition) {
		this.lastPosition = lastPosition;
	}

	public Rectangle2D getSelectionRectangle() {
		return selectionRectangle;
	}

	public void setSelectionRectangle(Rectangle2D selectionRectangle) {
		this.selectionRectangle = selectionRectangle;
	}

	/**
	 * @return the framework
	 */
	public JPanel getFramework() {
		return framework;
	}

	/**
	 * @return the commandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * @param commandManager
	 *            the commandManager to set
	 */
	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	/**
	 * @return the thread
	 */
	public AutoScrollThread getThread() {
		return thread;
	}
}

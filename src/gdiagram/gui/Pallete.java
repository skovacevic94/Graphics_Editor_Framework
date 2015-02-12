/**
 * @author Stanko 20.05.2014.
 * Pallete.java
 */
package gdiagram.gui;

import gdiagram.core.MainFrame;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class Pallete extends JToolBar{
	
	private ButtonGroup group;
	private JToggleButton selBtn;
	private JToggleButton circleBtn;
	private JToggleButton rectBtn;
	private JToggleButton starBtn;
	private JToggleButton triBtn;
	private JToggleButton linkBtn;
	private JRadioButton num3;
	private JRadioButton num4;
	private JRadioButton num8;
	
	private JLabel inputNumLbl;
	
	public Pallete() {
		super(JToolBar.VERTICAL);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		group = new ButtonGroup();
		
		selBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getSelectAction());
		group.add(selBtn);
		group.setSelected(selBtn.getModel(), true);
		add(selBtn);

		addSeparator();
		
		circleBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getCircleAction());
		group.add(circleBtn);
		add(circleBtn);
		
		addSeparator();
		
		rectBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getRectAction());
		group.add(rectBtn);
		add(rectBtn);

		addSeparator();
		
		starBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getStarAction());
		group.add(starBtn);
		add(starBtn);
		
		addSeparator();
		
		triBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getTriangleAction());
		group.add(triBtn);
		add(triBtn);
		
		addSeparator();
		
		linkBtn = new JToggleButton(MainFrame.getInstance().getActionManager().getLinkAction());
		group.add(linkBtn);
		add(linkBtn);
		
		addSeparator();
		
		ButtonGroup numBtnGroup = new ButtonGroup();
		
		inputNumLbl = new JLabel("Inputs #");
		inputNumLbl.setVisible(false);
		add(inputNumLbl);
		
		num3 = new JRadioButton("3");
		numBtnGroup.add(num3);
		add(num3);
		num3.setSelected(true);
		num3.setVisible(false);
		
		num4 = new JRadioButton("4");
		numBtnGroup.add(num4);
		add(num4);
		num4.setVisible(false);
		
		num8 = new JRadioButton("8");
		numBtnGroup.add(num8);
		add(num8);
		num8.setVisible(false);
	}

	public int getNumIO() {
		
		if (num3.isSelected())
			return 3;
		if (num4.isSelected())
			return 4;
		if (num8.isSelected())
			return 8;
		
		return 0;
	}
	
	public void showNumIO() {
		inputNumLbl.setVisible(true);
		num3.setVisible(true);
		num4.setVisible(true);
		num8.setVisible(true);
	}
	
	public void hideNumIO() {
		inputNumLbl.setVisible(false);
		num3.setVisible(false);
		num4.setVisible(false);
		num8.setVisible(false);
	}
	
	/**
	 * @return the group
	 */
	public ButtonGroup getGroup() {
		return group;
	}

	/**
	 * @return the selBtn
	 */
	public JToggleButton getSelBtn() {
		return selBtn;
	}

	/**
	 * @return the starBtn
	 */
	public JToggleButton getStarBtn() {
		return starBtn;
	}

	/**
	 * @return the triBtn
	 */
	public JToggleButton getTriBtn() {
		return triBtn;
	}

	/**
	 * @return the linkBtn
	 */
	public JToggleButton getLinkBtn() {
		return linkBtn;
	}

	/**
	 * @return the circleBtn
	 */
	public JToggleButton getCircleBtn() {
		return circleBtn;
	}

	/**
	 * @return the rectBtn
	 */
	public JToggleButton getRectBtn() {
		return rectBtn;
	}
}

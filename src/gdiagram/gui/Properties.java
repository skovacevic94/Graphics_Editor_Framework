/**
 * @author Stanko 20.05.2014.
 * Properties.java
 */
package gdiagram.gui;

import gdiagram.core.MainFrame;
import gdiagram.model.elements.DiagramElement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class Properties extends JDialog{
	
	private DiagramElement element;
	
	private JTextField nameText;
	private JTextArea descText;
	private JLabel nameLabel;
	private JLabel descLabel;
	
	JColorChooser colorChooser;
	
	public Properties(DiagramElement el) {
		super(MainFrame.getInstance(), "Properties: " + el.getName() , true);
		
		this.element = el;
		
		JPanel upper = new JPanel();
		upper.setLayout(new GridLayout(2, 2));
		
		nameLabel = new JLabel("Name:");
		nameText = new JTextField(element.getName());
		descLabel = new JLabel("Description:");
		descText = new JTextArea(element.getDescription());
		
		upper.add(nameLabel);
		upper.add(nameText);
		upper.add(descLabel);
		upper.add(descText);
		
		add(upper, BorderLayout.NORTH);
		
		colorChooser = new JColorChooser((Color)element.getPaint());
		add(colorChooser, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		JButton okBtn 		= new JButton("Ok");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				element.setName(nameText.getText());
				element.setDescription(descText.getText());
				Paint p = colorChooser.getColor();
				element.setPaint(p);
				dispose();
				
				MainFrame.getInstance().repaint();
				SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
			}
		});
		btnPanel.add(okBtn);
		
		JButton cancelBtn 	= new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		btnPanel.add(cancelBtn);
		
		add(btnPanel, BorderLayout.SOUTH);
		
		pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}

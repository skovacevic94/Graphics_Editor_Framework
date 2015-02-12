/**
 * @author Stanko 29.04.2014.
 * AboutDialog.java
 */
package gdiagram.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Stanko
 * 
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	public AboutDialog(JFrame parent) {
		super(parent, "About", true);
		this.setLocationRelativeTo(parent);
		
		setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS ));

		JLabel imageLbl = new JLabel();
		imageLbl.setBorder(BorderFactory.createEtchedBorder());
		URL imageURL = getClass().getResource("/gdiagram/res/autImage.jpg");
		Icon icon = new ImageIcon(imageURL);
		imageLbl.setIcon(icon);
		
		north.add(Box.createHorizontalGlue());
		north.add(imageLbl);
		north.add(Box.createHorizontalGlue());
		
		getContentPane().add(north, BorderLayout.NORTH);
		
		JPanel dataPane = new JPanel();
		dataPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		b.add(new JLabel("Author:---Stanko Kovacevic"));
		b.add(new JLabel("Email:-----stanko_kovacevic@outlook.com"));
		b.add(Box.createGlue());
		dataPane.add(b);

		getContentPane().add(dataPane, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel();
		JButton ok = new JButton("Ok");
		p2.add(ok);
		getContentPane().add(p2, BorderLayout.SOUTH);

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});

		pack();
	}

}

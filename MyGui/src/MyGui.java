import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyGui
{

	JFrame frame;

	JLabel label;

	public static void main(String[] args)
	{
		MyGui gui = new MyGui();
		gui.go();
	}

	public void go()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton labelButton = new JButton("Change Label");
		labelButton.addActionListener(new LabelListener());

		JButton colorButton = new JButton("Change Circle");
		colorButton.addActionListener(new ColorListener());

		label = new JLabel("I'm a label");
		MyDrawPanel drawPanel = new MyDrawPanel();

		frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		frame.getContentPane().add(BorderLayout.EAST, labelButton);
		frame.getContentPane().add(BorderLayout.WEST, label);

		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	
	
	
	class LabelListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			label.setText("Ouch");
		}

	}

	class ColorListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			frame.repaint();
		}

	}

}

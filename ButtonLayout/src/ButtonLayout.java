import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ButtonLayout
{

	public static void main(String[] args)
	{
		ButtonLayout btl = new ButtonLayout();
		btl.go();
	}

	public void go()
	{
		JFrame frame = new JFrame("Disposição dos botões");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton north = new JButton("North");
		JButton south = new JButton("South");
		JButton west = new JButton("West");
		JButton east = new JButton("East");
		JButton center = new JButton("Center");

		frame.getContentPane().add(BorderLayout.NORTH, north);
		frame.getContentPane().add(BorderLayout.SOUTH, south);
		frame.getContentPane().add(BorderLayout.WEST, west);
		frame.getContentPane().add(BorderLayout.EAST, east);
		frame.getContentPane().add(BorderLayout.CENTER, center);

		frame.setSize(300, 300);
		frame.setVisible(true);

	}
}

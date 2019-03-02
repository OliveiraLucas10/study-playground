import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainButtons
{

	public static void main(String[] args)
	{
		MainButtons mb = new MainButtons();
		mb.go();
	}

	public void go()
	{
		JFrame frame = new JFrame("Novo teste com botões");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JButton b1 = new JButton("schock me");
		JButton b2 = new JButton("bliss");
		JButton b3 = new JButton("huh?");

		panel.add(b1);
		panel.add(b2);
		panel.add(b3);

		frame.getContentPane().add(BorderLayout.EAST, panel);
		frame.setSize(250, 200);
		frame.setVisible(true);
	}
}

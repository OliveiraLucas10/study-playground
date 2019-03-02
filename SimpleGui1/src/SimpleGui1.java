import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SimpleGui1 implements ActionListener
{

	JFrame frame;

	JButton button;

	public static void main(String[] args)
	{
		SimpleGui1 gui = new SimpleGui1();
		gui.go();
	}

	public void go()
	{
		frame = new JFrame();
		button = new JButton("Click me");

		button.addActionListener(this);

		MyDrawPanel myDraw = new MyDrawPanel();

		// frame.getContentPane().add(button);
		frame.getContentPane().add(myDraw);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
		myDraw.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		button.setText("I've been clicked");
		frame.repaint();
	}

}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleChatClientA
{

	JTextField outgoing;

	PrintWriter writer;

	Socket sock;

	public static void main(String[] args)
	{
		new SimpleChatClientA().go();
	}

	public void go()
	{
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
		JPanel panel = new JPanel();
		outgoing = new JTextField(20);
		JButton sedButton = new JButton("Send");
		sedButton.addActionListener(new SendButtonListener());
		panel.add(outgoing);
		panel.add(sedButton);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		setUpNetworking();
		frame.setSize(400, 500);
		frame.setVisible(true);
	}

	private void setUpNetworking()
	{
		try
		{
			sock = new Socket("127.0.0.1", 5000);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		}
		catch (IOException e)
		{
			// TODO: handle exception
		}
	}

	public class SendButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				writer.println(outgoing.getText());
				writer.flush();
			}
			catch (Exception e2)
			{
				// TODO: handle exception
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}

	}
}

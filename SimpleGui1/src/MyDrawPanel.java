import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyDrawPanel extends JPanel
{

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Image image = new ImageIcon("cat.jpge").getImage();
		
		g.drawImage(image, 3, 4, this);
	}
}

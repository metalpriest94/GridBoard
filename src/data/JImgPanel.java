package data;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JImgPanel extends JPanel {
	private Image image;
	
	public JImgPanel(String path)
	{
		super();
		try
		{
			this.image = ImageIO.read(new File(path));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D)g;
		g2d.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
	}
}

package data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapTile {
	private String id;
	private String name;
	private int background;
	private Image image;
	private boolean accessable;
	private boolean canCarryItem;
	
	public MapTile(String[] data)
	{
		this.setID(data[0]);
		this.setName(data[1]);
		this.setAccessable(Boolean.parseBoolean(data[2]));
		this.setCanCarryItem(Boolean.parseBoolean(data[3]));
		this.setBackground((Integer.parseInt(data[4]) * 256 * 256) + (Integer.parseInt(data[5]) * 256) + Integer.parseInt(data[6]));
		
		try
		{
			this.setImage(ImageIO.read(new File("." + File.separator + "resources" + File.separator + "images" + File.separator + "tiles" + File.separator + data[7])));
		}
		catch (IOException ex)
		{
			
			ex.printStackTrace();
		}
	}
	
	
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final int getBackground() {
		return background;
	}
	public final void setBackground(int background) {
		this.background = background;
	}
	public final Image getImage() {
		return image;
	}
	public final void setImage(Image image) {
		this.image = image;
	}
	public final boolean isAccessable() {
		return accessable;
	}
	public final void setAccessable(boolean accessable) {
		this.accessable = accessable;
	}
	public final boolean canCarryItem() {
		return canCarryItem;
	}
	public final void setCanCarryItem(boolean canCarryItem) {
		this.canCarryItem = canCarryItem;
	}	
}

package assets;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {

	private String id,name;
	private int purpose;
	private boolean isConstructable; 
	private Image image;
	
	public Asset(String[] data) {
		this.id = data[0];
		this.name = data[1];
		this.purpose = translatePurposeToInt(data[2]);
		this.isConstructable = Boolean.parseBoolean(data[3]);
		
		try
		{
			this.setImage(ImageIO.read(Asset.class.getResource("." + File.separator + data[4])));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPurpose() {
		return purpose;
	}

	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}

	public boolean isConstructable() {
		return isConstructable;
	}

	public void setConstructable(boolean isConstructable) {
		this.isConstructable = isConstructable;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public int translatePurposeToInt(String p)
	{
		int i = 0;
		if(p.equals("living"))
			i = 1;
		
		return i;
	}

}

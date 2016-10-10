package assets;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Asset {

	private String id,name;
	private int purpose, buildResource1, buildResource2, buildResource3, amountBuildResource1, amountBuildResource2, amountBuildResource3;
	private boolean isConstructable;
	private Image image;
	
	
	public Asset(String[] data) {
		this.id = data[0];
		this.name = data[1];
		this.purpose = translatePurposeToInt(data[2]);
		this.isConstructable = Boolean.parseBoolean(data[3]);
		this.buildResource1 = translateAssetToInt(data[4]);
		this.buildResource2 = translateAssetToInt(data[5]);
		this.buildResource3 = translateAssetToInt(data[6]);
		this.amountBuildResource1 = Integer.parseInt(data[7]);
		this.amountBuildResource2 = Integer.parseInt(data[8]);
		this.amountBuildResource3 = Integer.parseInt(data[9]);
		
		try
		{
			this.setImage(ImageIO.read(Asset.class.getResource("." + File.separator + data[10])));
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
	
	
	public int getBuildResource1() {
		return buildResource1;
	}

	public void setBuildResource1(int buildResource1) {
		this.buildResource1 = buildResource1;
	}

	public int getBuildResource2() {
		return buildResource2;
	}

	public void setBuildResource2(int buildResource2) {
		this.buildResource2 = buildResource2;
	}

	public int getBuildResource3() {
		return buildResource3;
	}

	public void setBuildResource3(int buildResource3) {
		this.buildResource3 = buildResource3;
	}

	public int getAmountBuildResource1() {
		return amountBuildResource1;
	}

	public void setAmountBuildResource1(int amountBuildResource1) {
		this.amountBuildResource1 = amountBuildResource1;
	}

	public int getAmountBuildResource2() {
		return amountBuildResource2;
	}

	public void setAmountBuildResource2(int amountBuildResource2) {
		this.amountBuildResource2 = amountBuildResource2;
	}

	public int getAmountBuildResource3() {
		return amountBuildResource3;
	}

	public void setAmountBuildResource3(int amountBuildResource3) {
		this.amountBuildResource3 = amountBuildResource3;
	}

	public int translatePurposeToInt(String purpose)
	{
		int i = 0;
		if(purpose.equals("living"))
			i = 1;
		return i;
	}
	public int translateAssetToInt(String asset)
	{
		int i = 0;
		BufferedReader assetRead = null;
		try
		{
			if (!asset.equals(""))
			{
				assetRead = new BufferedReader(new FileReader("src" + File.separator +  "assets" + File.separator +asset + ".ast"));
				i = Integer.parseInt(assetRead.readLine().split("/")[0]);
			}
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Error reading " + asset + ".ast");
		}
		finally
		{
			try 
			{
				if (assetRead != null)
					assetRead.close();
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Error reading " + asset + ".ast");
			}
		}
		return i;
	}
}

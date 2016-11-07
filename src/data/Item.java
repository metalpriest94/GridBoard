package data;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Item {

	private String id,name;
	private boolean isConstructable, hasEmployee;
	private int purpose, buildResource1, buildResource2, buildResource3, amountBuildResource1, amountBuildResource2, amountBuildResource3, capacity, usedCapacity ,employee;
	private int producingTime, producingResource1, producingResource2, amountProducingResource1, amountProducingResource2, yieldProduct1, yieldProduct2, amountYieldProduct1, amountYieldProduct2;
	private int store1, store2, store3, storeSideProduct;
	
	private Image image;
	
	
	public Item(String[] data) {
		//Saved in maps or savegames
		this.id = data[0];
		this.name = data[1];
		this.purpose = translatePurposeToInt(data[2]);
		this.isConstructable = Boolean.parseBoolean(data[3]);
		/*	Needs a new method 'translateInhabToInt' 
		this.employee = translateInhabToInt(data[4]);
		*/
		this.hasEmployee = Boolean.parseBoolean(data[5]);
		this.capacity = Integer.parseInt(data[6]);			//to be used for non-item related amounts of goods, like the max amount of inhabs in a house
		this.usedCapacity = Integer.parseInt(data[7]);
		
		this.store1 = Integer.parseInt(data[8]);			//to be used for the already delivered amount of buildResource1 and the present amount of producingResource1
		this.store2 = Integer.parseInt(data[9]);			//to be used for the already delivered amount of buildResource2 and the present amount of producingResource2
		this.store3 = Integer.parseInt(data[10]);			//to be used for the already delivered amount of buildResource3 and the present amount of yieldProduct1
		this.storeSideProduct = Integer.parseInt(data[11]);	//to be used for the present amount of yieldProduct2
		
		//Not saved in maps or savegames
		this.buildResource1 = translateItemToInt(data[12]);
		this.buildResource2 = translateItemToInt(data[13]);
		this.buildResource3 = translateItemToInt(data[14]);
		this.amountBuildResource1 = Integer.parseInt(data[15]);
		this.amountBuildResource2 = Integer.parseInt(data[16]);
		this.amountBuildResource3 = Integer.parseInt(data[17]);
		this.producingTime = Integer.parseInt(data[18]);
		this.producingResource1 =translateItemToInt(data[19]);
		this.producingResource2 =translateItemToInt(data[20]);
		this.amountProducingResource1 = Integer.parseInt(data[21]);
		this.amountProducingResource2 = Integer.parseInt(data[22]);
		this.yieldProduct1 =translateItemToInt(data[23]);
		this.yieldProduct2 =translateItemToInt(data[24]);
		this.amountYieldProduct1 = Integer.parseInt(data[25]);
		this.amountYieldProduct2 = Integer.parseInt(data[26]);
		
		
		
		try
		{
			this.setImage(ImageIO.read(new File("."+ File.separator + "resources" + File.separator + "images" + File.separator + "items" +  File.separator + data[27])));
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
	
	public boolean hasEmployee() {
		return hasEmployee;
	}

	public void setHasEmployee(boolean hasEmployee) {
		this.hasEmployee = hasEmployee;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}

	public int getStore1() {
		return store1;
	}

	public void setStore1(int store1) {
		this.store1 = store1;
	}

	public int getStore2() {
		return store2;
	}

	public void setStore2(int store2) {
		this.store2 = store2;
	}

	public int getStore3() {
		return store3;
	}

	public void setStore3(int store3) {
		this.store3 = store3;
	}

	public int getStoreSideProduct() {
		return storeSideProduct;
	}

	public void setStoreSideProduct(int storeSideProduct) {
		this.storeSideProduct = storeSideProduct;
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
	
	public int getProducingTime() {
		return producingTime;
	}

	public void setProducingTime(int producingTime) {
		this.producingTime = producingTime;
	}

	public int getProducingResource1() {
		return producingResource1;
	}

	public void setProducingResource1(int producingResource1) {
		this.producingResource1 = producingResource1;
	}

	public int getProducingResource2() {
		return producingResource2;
	}

	public void setProducingResource2(int producingResource2) {
		this.producingResource2 = producingResource2;
	}

	public int getAmountProducingResource1() {
		return amountProducingResource1;
	}

	public void setAmountProducingResource1(int amountProducingResource1) {
		this.amountProducingResource1 = amountProducingResource1;
	}

	public int getAmountProducingResource2() {
		return amountProducingResource2;
	}

	public void setAmountProducingResource2(int amountProducingResource2) {
		this.amountProducingResource2 = amountProducingResource2;
	}

	public int getYieldProduct1() {
		return yieldProduct1;
	}

	public void setYieldProduct1(int yieldProduct1) {
		this.yieldProduct1 = yieldProduct1;
	}

	public int getYieldProduct2() {
		return yieldProduct2;
	}

	public void setYieldProduct2(int yieldProduct2) {
		this.yieldProduct2 = yieldProduct2;
	}

	public int getAmountYieldProduct1() {
		return amountYieldProduct1;
	}

	public void setAmountYieldProduct1(int amountYieldProduct1) {
		this.amountYieldProduct1 = amountYieldProduct1;
	}

	public int getAmountYieldProduct2() {
		return amountYieldProduct2;
	}

	public void setAmountYieldProduct2(int amountYieldProduct2) {
		this.amountYieldProduct2 = amountYieldProduct2;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int translatePurposeToInt(String purpose)
	{
		int i = 0;
		if(purpose.equals("home"))
			i = 1;
		return i;
	}
	public int translateItemToInt(String item)
	{
		int i = 0;
		BufferedReader itemRead = null;
		try
		{
			if (!(item.equals("") || item.equals("0")))
			{
				itemRead = new BufferedReader(new FileReader("src" + File.separator +  "items" + File.separator +item + ".itm"));
				i = Integer.parseInt(itemRead.readLine().split("/")[0]);
			}
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Error reading " + item + ".itm");
		}
		finally
		{
			try 
			{
				if (itemRead != null)
					itemRead.close();
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Error reading " + item + ".itm");
			}
		}
		return i;
	}
}

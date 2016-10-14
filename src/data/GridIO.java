package data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GridIO{
	private JGridPanel panelMap;
	
	private ArrayList<MapTile> allTiles;
	private ArrayList<Item> allItems;
	
	//Variables required by load() and readMap(isLastLine)
	String[] lastSplitData, nextSplitData;
	String nextLineContent;
	int lastY, nextY;
	
	public GridIO(JGridPanel jgridpanel, ArrayList<MapTile> listmaptiles, ArrayList<Item> listitems)
	{
		panelMap = jgridpanel;
		allTiles = listmaptiles;
		allItems = listitems;
	}
	
	public void save(String fileName)
	{
		BufferedWriter fileWrite = null;
		String data, collectData, lastData;
		collectData = "";
		try
		{
			fileWrite = new BufferedWriter(new FileWriter("src" + File.separator +  "maps" + File.separator + fileName +".map"));
			fileWrite.write(panelMap.getTilesX() + "/" + panelMap.getTilesY());
			fileWrite.newLine();
			for (int i = 0; i < panelMap.getMapping().length; i++)
			{

				for (int j= 0; j < panelMap.getMapping()[0].length; j++)
				{
					lastData = collectData;
					collectData = "";
					for (int k= 0; k < panelMap.getMapping()[0][0].length; k++)
					{
						data = "/" + panelMap.getMapping()[i][j][k];
						collectData = collectData.concat(data);
					}
					
					if(j == 0 || !(collectData.equals(lastData)))
					{
						fileWrite.write(i + "/" +  j + collectData);
						fileWrite.newLine();
					}
					else;
				}
			}
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Error writing" + fileWrite.toString());
		}
		finally
		{
			if (fileWrite != null)
			{
				try
				{
					fileWrite.close();
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Error writing" + fileWrite.toString());
				}
			}
		}
	}
	
	public void load(String fileName)
	{
		BufferedReader fileRead = null;
		try
		{
			fileRead = new BufferedReader(new FileReader("src" + File.separator + "maps" + File.separator + fileName + ".map"));
			lastSplitData = fileRead.readLine().split("/");
			newMap(Integer.parseInt(lastSplitData[0]), Integer.parseInt(lastSplitData[1]));
			
			lastSplitData = fileRead.readLine().split("/");
			lastY = Integer.parseInt(lastSplitData[1]);
			while ((nextLineContent = fileRead.readLine())  != null)
			{
				readMap(false); //is not the last line of map-File
			}
			readMap(true); //is the last line of map-File
			
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Warning: File " + fileName +".map is damaged or does not exist" );
		}
		finally
		{
			if(fileRead != null)
			{
				try
				{
					fileRead.close();
				}
				catch(IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Warning: File " + fileName +".map is damaged or does not exist" );
				}
			}
		}
	}
	
	public void readMap(boolean isLastLine)
	{
		if (isLastLine)
		{
			String[] substitudeForFinalLine = {String.valueOf(panelMap.getTilesY() + 1),"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
			nextSplitData = substitudeForFinalLine;
		}
		else
			nextSplitData = nextLineContent.split("/");
		nextY = Integer.parseInt(nextSplitData[1]);
		for(int y = lastY; (y < nextY || Integer.parseInt(nextSplitData[1]) == 0) ; y++)
		{
			for(int prop = 0; prop < panelMap.getProperties(); prop++)
			{
				panelMap.applyProperty(Integer.parseInt(lastSplitData[0]), y, prop, Integer.parseInt(lastSplitData[prop + 2]));
				for(MapTile each:allTiles)
				{
					if(lastSplitData[3].equals(each.getID()))
					{
						panelMap.applyTileImage(Integer.parseInt(lastSplitData[0]), y, each.getImage());
						panelMap.applyProperty(Integer.parseInt(lastSplitData[0]), y, prop, Integer.parseInt(lastSplitData[prop + 2]));
					}
				}
				for(Item each:allItems)
				{
					
					if(lastSplitData[6].equals(each.getID()))
					{
						panelMap.applyItemImage(Integer.parseInt(lastSplitData[0]), y, each.getImage());
					}
				}
			}
			if(Integer.parseInt(nextSplitData[1]) == 0 && y == panelMap.getTilesY() - 1)
				break;
		}
		lastSplitData = nextSplitData;
		lastY = nextY;
	}
	
	/*
	public void saveSerial(String fileName)
	{
		ObjectOutputStream output = null;
		try
		{
			output = new ObjectOutputStream(new FileOutputStream("src" + File.separator +  "maps" + File.separator + fileName +".smap"));
			output.writeObject(panelMap);
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
		if (output != null)
			{
				try
				{
					output.close();
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Error writing" + output.toString());
				}
			}
		}
	}
	
	
	public void loadSerial(String fileName)
	{
		ObjectInputStream input = null;
		try
		{
			input = new ObjectInputStream(new FileInputStream("src" + File.separator +  "maps" + File.separator + fileName +".smap"));
			panelMap = (JGridPanel) input.readObject();
			for(int x = 0; x > panelMap.getMapping().length; x++)
			{
				for(int y = 0; y > panelMap.getMapping()[0].length; y++)
				{
					if(panelMap.getMapping()[x][y][1] != 0)
					{
						for(MapTile each:allTiles)
						{
							if(panelMap.getMapping()[x][y][1] == Integer.parseInt(each.getID()))
							{
								panelMap.applyTileImage(x, y, each.getImage());
							}
						}
					}
				}
			}	
			repaint();
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
		if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Error reading" + input.toString());
				}
			}
		}
	}
	*/
	
	public void resize(int x, int y)
	{
		panelMap.setTilesX(x);
		panelMap.setTilesY(y);
		panelMap.prepareMapping(panelMap.getTilesX(), panelMap.getTilesY(), 16);
	}
	
	public void newMap(int x, int y)
	{
		resize(1,1);
		for(int i = 0; i < panelMap.getProperties(); i++)
		{
			panelMap.applyProperty(0, 0, i, 0);
			panelMap.applyTileImage(0, 0, null);
			panelMap.applyItemImage(0, 0, null);
		}
		resize(x,y);
	}
	
	
}



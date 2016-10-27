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
	private final String mapDataSeparator = "---";
	
	//Variables required by load() and readMap(isLastLine)
	private String[] lastSplitData, nextSplitData;
	private String nextLineContent;
	private int lastY, nextY;

	private ArrayList<Item> constructableItems;
	
	public GridIO(JGridPanel jgridpanel, ArrayList<MapTile> listmaptiles, ArrayList<Item> listitems)
	{
		panelMap = jgridpanel;
		allTiles = listmaptiles;
		allItems = listitems;
	}
	
	public void save(String fileName, boolean isSav)
	{
		BufferedWriter fileWrite = null;
		String data, collectData, lastData;
		collectData = "";
		try
		{
			if (isSav)
				fileWrite = new BufferedWriter(new FileWriter("src" + File.separator +  "savegames" + File.separator + fileName +".sav"));
			else
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
			fileWrite.write(mapDataSeparator);
			fileWrite.newLine();
			fileWrite.write(panelMap.getVisibleCornerX() + "/" + panelMap.getVisibleCornerY());
			fileWrite.newLine();
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
	
	public void load(String fileName, boolean isSav)
	{
		BufferedReader fileRead = null;
		try
		{
			if(isSav)
				fileRead = new BufferedReader(new FileReader("src" + File.separator + "savegames" + File.separator + fileName + ".sav"));
			else
				fileRead = new BufferedReader(new FileReader("src" + File.separator + "maps" + File.separator + fileName + ".map"));
			lastSplitData = fileRead.readLine().split("/");
			newMap(Integer.parseInt(lastSplitData[0]), Integer.parseInt(lastSplitData[1]));
			
			lastSplitData = fileRead.readLine().split("/");
			lastY = Integer.parseInt(lastSplitData[1]);
			if (isSav)
			{
				while (!(nextLineContent = fileRead.readLine()).equals(mapDataSeparator))
				{
					readMap(false); //is not the last line of map-File
				}
				readMap(true); //is the last line of map-File
				nextLineContent = fileRead.readLine(); //Line after mapDataSeparator ("---")
				nextSplitData = nextLineContent.split("/");
				panelMap.setVisibleCornerX(Integer.parseInt(nextSplitData[0]));
				panelMap.setVisibleCornerY(Integer.parseInt(nextSplitData[1]));
				panelMap.repaint();
			}
			else
			{
				while ((nextLineContent = fileRead.readLine()) != null)
				{
					readMap(false); //is not the last line of map-File
				}
				readMap(true); //is the last line of map-File
			}
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
	
	public ArrayList<MapTile> createTileList()
	{
		String[] findMPT, files, fileContent;
		BufferedReader fileRead;
		String line;
		int i, lengthOfFile = 8;
		allTiles = new ArrayList<MapTile>();
		File folderRead = new File("src" + File.separator +  "tiles"); 	
		{
			
			if(folderRead.exists() && folderRead.isDirectory())
			{
				files = folderRead.list();
				for (String name: files)
				{
					findMPT = name.split("\\.");
					if(findMPT[findMPT.length - 1].equals("mpt"))
					{
						fileRead = null;
						fileContent = null;
						try
						{
							fileRead = new BufferedReader(new FileReader(new File("src" + File.separator +  "tiles" + File.separator + name)));
							try
							{
								fileContent = new String[lengthOfFile];
								i = 0;
								while((line = fileRead.readLine()) != null)
								{
									fileContent[i] = line.split("/")[0];
									i++;
									
								}
								allTiles.add(new MapTile(fileContent));
							}
							catch (IOException ex)
							{
								JOptionPane.showMessageDialog(null, "Error in" + name);
							}
							finally
							{
								try
								{
									if(fileRead != null)
										fileRead.close();
								}
								catch (IOException ex)
								{
									JOptionPane.showMessageDialog(null, "Error in" + name);
								}
							}
							

						}
						catch (FileNotFoundException ex)
						{
							JOptionPane.showMessageDialog(null, "Error in" + name);
						}
					}
				}
			}
		}
		return allTiles;
	}
	
	public ArrayList<Item> createItemList(boolean onlyConstructables)
	{
		String[] findITM, files, fileContent;
		BufferedReader fileRead;
		String line;
		int i, lengthOfFile = 28;
		
		allItems = new ArrayList<Item>();
		constructableItems = new ArrayList<Item>();
		
		File folderRead = new File("src" + File.separator +  "items"); 
			
		{
			
			if(folderRead.exists() && folderRead.isDirectory())
			{
				files = folderRead.list();
				for (String name: files)
				{
					findITM = name.split("\\.");
					if(findITM[findITM.length - 1].equals("itm"))
					{
						fileRead = null;
						fileContent = null;
						try
						{
							fileRead = new BufferedReader(new FileReader(new File("src" + File.separator +  "items" + File.separator + name)));
							try
							{
								fileContent = new String[lengthOfFile];
								i = 0;
								while((line = fileRead.readLine()) != null)
								{
									fileContent[i] = line.split("/")[0];
									i++;
								}
								allItems.add(new Item(fileContent));
								if(fileContent[3].equals(String.valueOf(true)))
								{
									constructableItems.add(new Item(fileContent));
								}
							}
							catch (IOException ex)
							{
								JOptionPane.showMessageDialog(null, "Error in" + name);
							}
							finally
							{
								try
								{
									if(fileRead != null)
										fileRead.close();
								}
								catch (IOException ex)
								{
									JOptionPane.showMessageDialog(null, "Error in" + name);
								}
							}
						}
						catch (FileNotFoundException ex)
						{
							JOptionPane.showMessageDialog(null, "Error in" + name);
						}
					}
				}
			}
		}
		if(onlyConstructables)
			return constructableItems;
		else
			return allItems;
	}
}



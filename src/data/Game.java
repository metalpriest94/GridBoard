package data;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import assets.Asset;
import net.miginfocom.swing.MigLayout;
import tiles.MapTile;

import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class Game extends JFrame {
	private GridIO gioGame;
	private ArrayList<MapTile> allTiles;
	private ArrayList<Asset> allAssets;
	
	private int zoomLevel = 3;
	
	// Variables defining the appearance of the MiniMap
	// ((Mapsize / Detail) * Scale) should be as close to 256 as possible. If the map is not square-shaped, use the long side for Mapsize.
	private int miniMapDetail = 1; //Adds every n-th tile to the MiniMap --- the smaller, the better
	private int miniMapScale = 1; //The smaller the map, the higher this should be
	
	private JPanel contentPane;
	private JGridPanel panelGame;
	private JGridPanel panelMiniMap;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
					frame.setExtendedState(MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game() {
		AbstractAction moveUp = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				moveUp();
			}
		};
		
		AbstractAction moveDown = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				moveDown();
			}
		};
		
		AbstractAction moveLeft = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				moveLeft();
			}
		};
		
		AbstractAction moveRight = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				moveRight();
			}
		};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[663.00,grow][grow]", "[grow]"));
		
		panelGame = new JGridPanel(4,4,16);
		panelGame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0)
					zoomIn();
				else
					zoomOut();
			}
		});
		panelGame.setTileSize(52);
		panelGame.setBackground(Color.DARK_GRAY);
		contentPane.add(panelGame, "cell 0 0,grow");
		panelGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int startX = panelGame.getCornerDragX() / panelGame.getTileSize() + panelGame.getVisibleCornerX() -1;
				int startY = panelGame.getCornerDragY() / panelGame.getTileSize() + panelGame.getVisibleCornerY() -1;
				
					if(panelGame.isDragged())
					{
						for(int x = startX; x < startX + panelGame.getDraggedTilesX(); x++)
						{
							for(int y = startY; y < startY + panelGame.getDraggedTilesY(); y++)
							{

							}
						}
						panelGame.setDragged(false);
					}
					else
					{

					}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				panelGame.setStartDragX(panelGame.getPosX());
				panelGame.setStartDragY(panelGame.getPosY()); 
			}
		});
		panelGame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				panelGame.setDragged(false);
				panelGame.setPosX(e.getX() / panelGame.getTileSize() * panelGame.getTileSize() );
				panelGame.setPosY(e.getY() / panelGame.getTileSize() * panelGame.getTileSize() );
				if(e.getX() <= 10)
					moveLeft();
				else if(e.getX() >= panelGame.getWidth() - 10 )
					moveRight();
				
				if(e.getY() <= 10)
					moveUp();
				else if(e.getY() >= panelGame.getHeight() - 10 )
					moveDown();
				repaint();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				panelGame.setDragged(true);
				panelGame.setPosX(e.getX() / panelGame.getTileSize() * panelGame.getTileSize() );
				panelGame.setPosY(e.getY() / panelGame.getTileSize() * panelGame.getTileSize() );
				repaint();
			}
		});
		panelGame.setShowGrid(false);
		
		panelMiniMap = new JGridPanel(panelGame.getTilesX()/4, panelGame.getTilesY()/4, 1);
		panelMiniMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int targetX = e.getX() - (panelGame.getWidth() / 2) / panelGame.getTileSize();
				int targetY = e.getY() - (panelGame.getHeight() / 2) / panelGame.getTileSize();
				
				if(targetX <= 0)
					targetX = 1;
				else if (targetX > panelGame.getTilesX() - (panelGame.getWidth() / 2) / panelGame.getTileSize() * 2)
					targetX = panelGame.getTilesX() - (panelGame.getWidth() / panelGame.getTileSize()) +1;
				
				if(targetY <= 0)
					targetY = 1;
				else if (targetY > panelGame.getTilesY() - (panelGame.getHeight() / 2) / panelGame.getTileSize() * 2)
					targetY = panelGame.getTilesY() - (panelGame.getHeight() / panelGame.getTileSize()) +1;
		
				panelGame.setVisibleCornerX(targetX);
				panelGame.setVisibleCornerY(targetY);
				updateMiniMap();
				repaint();
			}
		});
		
		InputMap input = panelGame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = panelGame.getActionMap();
	
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		action.put("up", moveUp);
		action.put("down", moveDown);
		action.put("left", moveLeft);
		action.put("right", moveRight);
		
		callMap("test5");
		designMiniMap(miniMapDetail, miniMapScale);
	}
	
	public void callMap(String name)
	{
		createTileList();
		createAssetList();
		gioGame = new GridIO(panelGame, allTiles, allAssets);
		
		gioGame.load(name);
	}
	
	public void designMiniMap(int detail, int scale)
	{

		GridIO gioMiniMap = new GridIO(panelMiniMap, allTiles, allAssets);
		gioMiniMap.newMap(panelGame.getTilesX()/detail, panelGame.getTilesY()/detail);
		for (int x = 0; x < panelGame.getTilesX(); x += detail)
		{
			for (int y = 0; y < panelGame.getTilesY(); y += detail)
			{
				panelMiniMap.applyProperty(x /detail , y /detail , 0, panelGame.getMapping()[x][y][0]);
			}
		}
		panelMiniMap.setTileSize(scale);
		panelMiniMap.setShowGrid(false);
		panelMiniMap.setDragged(true); // Simulation of Mousedrag to apply the visible area as square on the MiniMap.
		contentPane.add(panelMiniMap, "cell 1 0,grow");
	}
	
	public void updateMiniMap()
	{
		panelMiniMap.setStartDragX(panelGame.getVisibleCornerX()-1);
		panelMiniMap.setStartDragY(panelGame.getVisibleCornerY()-1);
		panelMiniMap.setPosX(panelGame.getVisibleCornerX() + panelGame.getWidth()  / panelGame.getTileSize() -1);
		panelMiniMap.setPosY(panelGame.getVisibleCornerY() + panelGame.getHeight() / panelGame.getTileSize() -1);
	}
	
	public void createTileList()
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
	}
	
	public void createAssetList()
	{
		String[] findAST, files, fileContent;
		BufferedReader fileRead;
		String line;
		int i, lengthOfFile = 5;
		
		allAssets = new ArrayList<Asset>();
		
		File folderRead = new File("src" + File.separator +  "assets"); 
			
		{
			
			if(folderRead.exists() && folderRead.isDirectory())
			{
				files = folderRead.list();
				for (String name: files)
				{
					findAST = name.split("\\.");
					if(findAST[findAST.length - 1].equals("ast"))
					{
						fileRead = null;
						fileContent = null;
						try
						{
							fileRead = new BufferedReader(new FileReader(new File("src" + File.separator +  "assets" + File.separator + name)));
							try
							{
								fileContent = new String[lengthOfFile];
								i = 0;
								while((line = fileRead.readLine()) != null)
								{
									fileContent[i] = line.split("/")[0];
									i++;
								}
								allAssets.add(new Asset(fileContent));
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
	}
	
	public void moveUp()
	{
		if(panelGame.getVisibleCornerY() > 1)
		{
			panelGame.setVisibleCornerY(panelGame.getVisibleCornerY() - 1);
			updateMiniMap();
			repaint();
		}
	}
	
	public void moveDown()
	{
		if(panelGame.getVisibleCornerY() < panelGame.getTilesY() - (panelGame.getHeight() / panelGame.getTileSize() - 1))
		{
			panelGame.setVisibleCornerY(panelGame.getVisibleCornerY() + 1);
			updateMiniMap();
			repaint();
		}
	}
	
	public void moveLeft()
	{
		if(panelGame.getVisibleCornerX() > 1)
		{
			panelGame.setVisibleCornerX(panelGame.getVisibleCornerX() - 1);
			updateMiniMap();
			repaint();
		}
	}
	
	public void moveRight()
	{
		if(panelGame.getVisibleCornerX() < panelGame.getTilesX() - (panelGame.getWidth() / panelGame.getTileSize() - 1))
		{
			panelGame.setVisibleCornerX(panelGame.getVisibleCornerX() + 1);
			updateMiniMap();
			repaint();
		}
	}
	
	public void zoomIn()
	{
		int lastCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
		int lastCenterY = lastCenterX * 5 / 6;
		
		if (zoomLevel < 8)
		{
			changeZoom(+1);
			int newCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
			int newCenterY = newCenterX * 5 / 6;
			
			int offsetX = lastCenterX - newCenterX;
			int offsetY = lastCenterY - newCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				moveRight();
			}
			for(int i =0; i < offsetY; i++)
			{
				moveDown();
			}
			
			
		}
		repaint();
	}
	
	public void zoomOut()
	{
		int lastCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
		int lastCenterY = lastCenterX * 5 / 6;
		
		if (zoomLevel > 1)
		{
			changeZoom(-1);
			int newCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
			int newCenterY = newCenterX * 5 / 6;
			
			int offsetX = newCenterX - lastCenterX;
			int offsetY = newCenterY - lastCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				moveLeft();
			}
			for(int i =0; i < offsetY; i++)
			{
				moveUp();
			}
		}
		repaint();
	}
	public void changeZoom(int value)
	{
		zoomLevel += value;
		panelGame.setTileSize(18 * (zoomLevel - 1) + 16);
	}
}

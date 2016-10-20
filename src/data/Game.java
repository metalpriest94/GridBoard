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

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Dialog.ModalExclusionType;

public class Game extends JFrame {
	private GridIO gioGame;
	private GridScroller gsGame;
	private MiniMapUpdater mmuGame;
	private Thread gridScroll;
	private Thread miniMapUpdate;
	
	private ArrayList<MapTile> allTiles;
	private ArrayList<Item> allItems;
	private ArrayList<Item> constructableItems;
	
	
	private int zoomLevel = 3;
	private final int baseTileSize = 16;
	
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
		setUndecorated(true);
		AbstractAction moveUp = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyUp(true);
			}
		};
		
		AbstractAction moveDown = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyDown(true);
			}
		};
		
		AbstractAction moveLeft = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyLeft(true);
			}
		};
		
		AbstractAction moveRight = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyRight(true);
			}
		};
		
		AbstractAction moveUpRelease = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyUp(false);
			}
		};
		
		AbstractAction moveDownRelease = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyDown(false);
			}
		};
		
		AbstractAction moveLeftRelease = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyLeft(false);
			}
		};
		
		AbstractAction moveRightRelease = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				gsGame.setKeyRight(false);
			}
		};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1096, 621);
		contentPane = new JPanel();
		contentPane.setAlignmentY(0.0f);
		contentPane.setAlignmentX(0.0f);
		contentPane.setBorder(null);
		contentPane.setBackground(new Color(153, 204, 204));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[398.00,grow][256:n:256,grow]", "[256:n:256,grow][361.00,grow]"));
		
		panelGame = new JGridPanel(4,4,16);
		FlowLayout flowLayout_1 = (FlowLayout) panelGame.getLayout();
		panelGame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0)
					zoomIn();
				else
					zoomOut();
			}
		});
		panelGame.setTileSize(52);
		panelGame.setBackground(new Color(153, 204, 204));
		contentPane.add(panelGame, "cell 0 0 1 2,grow");
		panelGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
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

			@Override
			public void mouseExited(MouseEvent e) {
				gsGame.setInComponent(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				gsGame.setInComponent(true);
			}
		});
		panelGame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				panelGame.setDragged(false);
				gsGame.moved(e.getX(), e.getY());
				panelGame.setPosX(e.getX() / panelGame.getTileSize() * panelGame.getTileSize() );
				panelGame.setPosY(e.getY() / panelGame.getTileSize() * panelGame.getTileSize() );
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
		panelMiniMap.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panelMiniMap.getLayout();
		flowLayout.setAlignOnBaseline(true);
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
				repaint();
			}
		});
		
		InputMap input = panelGame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = panelGame.getActionMap();
	
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightR");
		
		action.put("up", moveUp);
		action.put("down", moveDown);
		action.put("left", moveLeft);
		action.put("right", moveRight);	
		action.put("upR", moveUpRelease);
		action.put("downR", moveDownRelease);
		action.put("leftR", moveLeftRelease);
		action.put("rightR", moveRightRelease);	
		
		
		callMap("test5");
		designMiniMap(miniMapDetail, miniMapScale);
		
		gsGame = new GridScroller(panelGame);
		gridScroll = new Thread(gsGame);
		gridScroll.start();
		
		mmuGame = new MiniMapUpdater(panelMiniMap, panelGame);
		miniMapUpdate = new Thread(mmuGame);
		miniMapUpdate.start();
		
		
	}
	
	public void callMap(String name)
	{

		gioGame = new GridIO(panelGame, allTiles, allItems);
		allTiles = gioGame.createTileList();
		allItems = gioGame.createItemList(false);
		constructableItems = gioGame.createItemList(true);
		
		gioGame.load(name);
	}
	
	public void designMiniMap(int detail, int scale)
	{

		GridIO gioMiniMap = new GridIO(panelMiniMap, allTiles, allItems);
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
	
	
	
	public void zoomIn()
	{
		int lastCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
		int lastCenterY = lastCenterX * panelGame.getHeight() / panelGame.getWidth();
		
		if (zoomLevel < 8)
		{
			changeZoom(+1);
			int newCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
			int newCenterY = newCenterX * panelGame.getHeight() / panelGame.getWidth();
			
			int offsetX = lastCenterX - newCenterX;
			int offsetY = lastCenterY - newCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				gsGame.moveRight();
			}
			for(int i =0; i < offsetY; i++)
			{
				gsGame.moveDown();
			}
			
			
		}
		repaint();
	}
	
	public void zoomOut()
	{
		int lastCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
		int lastCenterY = lastCenterX * panelGame.getHeight() / panelGame.getWidth();
		
		if (zoomLevel > 1)
		{
			changeZoom(-1);
			int newCenterX = panelGame.getWidth() / panelGame.getTileSize() / 2;
			int newCenterY = newCenterX * panelGame.getHeight() / panelGame.getWidth();
			
			int offsetX = newCenterX - lastCenterX;
			int offsetY = newCenterY - lastCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				gsGame.moveLeft();
			}
			for(int i =0; i < offsetY; i++)
			{
				gsGame.moveUp();
			}
		}
		repaint();
	}
	public void changeZoom(int value)
	{
		zoomLevel += value;
		panelGame.setTileSize(zoomLevel * baseTileSize);
	}
}

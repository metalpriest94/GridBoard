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
import java.util.Random;

import javax.imageio.ImageIO;
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
import java.awt.Image;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Game extends JFrame {
	private GridIO gioGame;
	private GridScroller gsGame;
	private MiniMapUpdater mmuGame;
	private Clockwork cwGame;
	private Thread gridScroll;
	private Thread miniMapUpdate;
	private Thread clockwork;
	
	private ArrayList<MapTile> allTiles;
	private ArrayList<Item> allItems;
	private ArrayList<Item> constructableItems;
	
	// Variables defining the appearance of the MiniMap
	// ((Mapsize / Detail) * Scale) should be as close to 256 as possible. If the map is not square-shaped, use the long side for Mapsize.
	private int miniMapDetail = 1; //Adds every n-th tile to the MiniMap --- the smaller, the better
	private int miniMapScale = 1; //The smaller the map, the higher this should be
	
	private JPanel contentPane;
	private JGridPanel panelGame;
	private JGridPanel panelMiniMap;
	private JPanel panelTools;
	private JButton btnQuit;
	private JButton btnResume;
	private JLabel lblExit1;
	
	private boolean askExit = false;
	private JPanel panelQuit;
	private JButton btnSaveQuit;
	private JToggleButton tglbtnBuild;
	private JToggleButton tglbtnInfo;
	private JToggleButton tglbtnStats;
	private JPanel panelToolSelection;
	private JPanel panelBuild;
	private JPanel panelInfo;
	private JPanel panelStats;
	private JPanel panelStorage;
	private JLabel lblStats;
	private JLabel lblInfo;
	private JLabel lblBuild;
	private JLabel lblStorage;
	
	private CardLayout toolsCard;
	private CardLayout topBarCard;
	private final String cardBuild 	 = "cardBuild" ;
	private final String cardInfo	 = "cardInfo";
	private final String cardStats 	 = "cardStats";
	private final String cardStorage = "cardStorage";
	private final String cardResources = "cardResources";
	private final String cardTime = "cardTime";
	private final String cardIndicators = "cardIndicators";
	
	private String activeCard = cardBuild; 
	private JLabel lblPosition;
	private JLabel lblTilename;
	private JLabel lblItemname;
	private JScrollPane scrollPane;
	private JGridPanel panelSelectItem;
	private JPanel panelResources;
	private JPanel panelTopBar;
	private JImgPanel panelPicWood;
	private JLabel lblStoreWood;
	
	private int storeWood;
	private int storeStone;
	private int storeSteel;
	private int storeGlass;
	private int storeGold;
	
	private int storeWater;
	private int storeVegetables;
	
	private int inhabs;
	private int capacity;
	
	private double happiness;
	
	private final Font overview = new Font("Tahoma", Font.PLAIN, 24);
	private JImgPanel panelPicStone;
	private JImgPanel panelPicSteel;
	private JImgPanel panelPicGlass;
	private JLabel lblStoreStone;
	private JLabel lblStoreSteel;
	private JLabel lblStoreGlass;
	private JPanel panelSpacer1;
	private JPanel panelSpacer2;
	private JPanel panelSpacer3;
	private JPanel panelSpacer4;
	private JImgPanel panelPicGold;
	private JLabel lblGold;
	private JLabel lblHours;
	private JLabel lblClockSeparator;
	private JLabel lblMinutes;
	private JButton btnSpeed;
	
	private final int maxSpeed = 4;
	private JLabel lblDay;
	private JLabel lblInhabs;
	private JLabel lblHousingSpace;
	private JPanel panelTime;
	private JPanel panelIndicators;
	private JButton btnBackInd;
	private JButton btnNextInd;
	private JButton btnBackTime;
	private JButton btnNextTime;
	private JButton btnBackRes;
	private JButton btnNextRes;
	private JLabel lblHappiness;
	private JImgPanel panelPicHappiness;
	private JScrollPane scrollPaneStorage;
	private JPanel panelStorageItems;
	private JPanel panelStorageSpacer;
	private JImgPanel panelStorageWood;
	private JLabel lblStorageWood;
	private JImgPanel panelStorageStone;
	private JLabel lblStorageStone;
	private JImgPanel panelStorageSteel;
	private JLabel lblStorageSteel;
	private JImgPanel panelStorageGlass;
	private JLabel lblStorageGlass;
	private JImgPanel panelStorageWater;
	private JLabel lblStorageWater;
	private JImgPanel panelStorageVegetables;
	private JLabel lblStorageVegetables;
	private JImgPanel panelStorageGold;
	private JLabel lblStorageGold;
	
	
	public GridIO getGioGame() {
		return gioGame;
	}


	public GridScroller getGsGame() {
		return gsGame;
	}


	public MiniMapUpdater getMmuGame() {
		return mmuGame;
	}

	public Clockwork getCwGame() {
		return cwGame;
	}
	
	public ArrayList<Integer> getStorage()
	{
		ArrayList<Integer> storage = new ArrayList<Integer>();
		storage.add(storeWood);
		storage.add(storeStone);
		storage.add(storeSteel);
		storage.add(storeGlass);
		storage.add(storeWater);
		storage.add(storeVegetables);
		storage.add(storeGold);
		return storage;
	}

	/**
	 * Launch the application.
	 */
	public static void main(boolean isNewGame, String file, JFrame caller) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game(isNewGame, file, caller);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game(boolean isNewGame, String file, JFrame caller) {
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
		
		AbstractAction plus = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				speedUp();
			}
		};
		
		AbstractAction minus = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				speedDown();
			}
		};
		
		AbstractAction escape = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (askExit)
					hideExitMenu();
				else
					showExitMenu();
			}
		};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1096, 621);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				gsGame.setInComponent(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				gsGame.setInComponent(true);
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				gsGame.moved(e.getX(), e.getY());
			}
		});
		contentPane.setAlignmentY(0.0f);
		contentPane.setAlignmentX(0.0f);
		contentPane.setBorder(null);
		contentPane.setBackground(new Color(153, 204, 204));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[398.00,grow][::256]", "[][256:n:256,grow][][361.00,grow]"));
		
		panelGame = new JGridPanel(4,4,16);
		FlowLayout flowLayout_1 = (FlowLayout) panelGame.getLayout();
		panelGame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0)
					gsGame.zoomIn(panelGame.getPosX(), panelGame.getPosY());
				else
					gsGame.zoomOut(panelGame.getPosX(), panelGame.getPosY());
			}
		});
		
		panelTopBar = new JPanel();
		panelTopBar.setBackground(new Color(102, 153, 153));
		contentPane.add(panelTopBar, "cell 0 0,grow");
		panelTopBar.setLayout(new CardLayout(0, 0));
		topBarCard = (CardLayout)panelTopBar.getLayout(); 
		
		panelResources = new JPanel();
		panelResources.setBackground(new Color(102, 153, 153));
		panelResources.setLayout(new MigLayout("", "[40px:n:40px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][grow]", "[grow,center]"));
		panelTopBar.add(panelResources, cardResources);
		
		panelTime = new JPanel();
		panelTime.setBackground(new Color(102, 153, 153));
		panelTopBar.add(panelTime, cardTime);
		panelTime.setLayout(new MigLayout("", "[40px:n:40px][48px:n:196px][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][grow]", "[grow,center]"));
		
		panelIndicators = new JPanel();
		panelIndicators.setBackground(new Color(102, 153, 153));
		panelIndicators.setLayout(new MigLayout("", "[40px:n:40px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][grow]", "[grow,center]"));
		panelTopBar.add(panelIndicators, cardIndicators);
		
		btnBackInd = new JButton("<");
		btnBackInd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardTime);
			}
		});
		panelIndicators.add(btnBackInd, "cell 0 0,alignx left,growy");
		
		btnNextInd = new JButton(">");
		btnNextInd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardResources);
			}
		});
		
		panelPicHappiness = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "happiness.png");
		panelPicHappiness.setToolTipText("HAPPINESS");
		panelPicHappiness.setBackground(new Color(102, 153, 153));
		panelIndicators.add(panelPicHappiness, "cell 1 0,grow");
		
		lblHappiness = new JLabel("70");
		lblHappiness.setFont(overview);
		panelIndicators.add(lblHappiness, "cell 2 0");
		panelIndicators.add(btnNextInd, "cell 15 0,alignx right,growy");
		
		btnBackTime = new JButton("<");
		btnBackTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardResources);
			}
		});
		panelTime.add(btnBackTime, "cell 0 0,alignx left,growy");
		
		btnNextTime = new JButton(">");
		btnNextTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardIndicators);
			}
		});
		panelTime.add(btnNextTime, "cell 6 0,alignx right,growy");
		
		lblDay = new JLabel("Day 1");
		lblDay.setFont(overview);
		lblDay.setBackground(new Color(102, 153, 153));
		panelTime.add(lblDay, "cell 1 0,alignx center");
		
		lblHours = new JLabel("00");
		lblHours.setFont(overview);
		panelTime.add(lblHours, "cell 2 0");
		
		lblClockSeparator = new JLabel(":");
		lblClockSeparator.setFont(overview);
		panelTime.add(lblClockSeparator, "cell 3 0");
		
		lblMinutes = new JLabel("00");
		lblMinutes.setFont(overview);
		panelTime.add(lblMinutes, "cell 4 0");
		
		btnSpeed = new JButton("1x");
		btnSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cwGame.getSpeed() >= maxSpeed)
				{
					for(int i = 0; i < maxSpeed -1 ;i++)
						speedDown();
				}
				else
					speedUp();
			}
		});

		btnSpeed.setMargin(new Insets(2, 2, 2, 2));
		btnSpeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTime.add(btnSpeed, "cell 5 0,grow");
		
		btnBackRes = new JButton("<");
		btnBackRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardIndicators);
			}
		});
		panelResources.add(btnBackRes, "cell 0 0,alignx left,growy");
		
		panelPicWood = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.jpg");
		panelPicWood.setToolTipText("WOOD");
		panelPicWood.setBackground(new Color(102, 153, 153));
		panelResources.add(panelPicWood, "cell 1 0,grow");
		
		lblStoreWood = new JLabel("0");
		lblStoreWood.setFont(overview);
		panelResources.add(lblStoreWood, "cell 2 0,alignx center");
		
		panelSpacer1 = new JPanel();
		panelSpacer1.setBackground(new Color(102, 153, 153));
		panelResources.add(panelSpacer1, "cell 3 0,grow");
		
		panelPicStone = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.jpg");
		panelPicStone.setToolTipText("STONE");
		panelPicStone.setBackground(new Color(102, 153, 153));
		panelResources.add(panelPicStone, "cell 4 0,grow");
		
		lblStoreStone = new JLabel("0");
		lblStoreStone.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreStone, "cell 5 0,alignx center");
		
		panelSpacer2 = new JPanel();
		panelSpacer2.setBackground(new Color(102, 153, 153));
		panelResources.add(panelSpacer2, "cell 6 0,grow");
		
		panelPicSteel = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.jpg");
		panelPicSteel.setToolTipText("STEEL");
		panelPicSteel.setBackground(new Color(102, 153, 153));
		panelResources.add(panelPicSteel, "cell 7 0,grow");
		
		lblStoreSteel = new JLabel("0");
		lblStoreSteel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreSteel, "cell 8 0,alignx center");
		
		panelSpacer3 = new JPanel();
		panelSpacer3.setBackground(new Color(102, 153, 153));
		panelResources.add(panelSpacer3, "cell 9 0,grow");
		
		panelPicGlass = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.jpg");
		panelPicGlass.setToolTipText("GLASS");
		panelPicGlass.setBackground(new Color(102, 153, 153));
		panelResources.add(panelPicGlass, "cell 10 0,grow");
		
		lblStoreGlass = new JLabel("0");
		lblStoreGlass.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreGlass, "cell 11 0,alignx center");
		
		panelSpacer4 = new JPanel();
		panelSpacer4.setBackground(new Color(102, 153, 153));
		panelResources.add(panelSpacer4, "cell 12 0,grow");
		
		panelPicGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.jpg");
		panelPicGold.setToolTipText("GOLD");
		panelPicGold.setBackground(new Color(102, 153, 153));
		panelResources.add(panelPicGold, "cell 13 0,grow");
		
		lblGold = new JLabel("10");
		lblGold.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblGold, "cell 14 0,alignx center");
		
		btnNextRes = new JButton(">");
		btnNextRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardTime);
			}
		});
		panelResources.add(btnNextRes, "cell 15 0,alignx right,growy");
		
		panelGame.setTileSize(52);
		panelGame.setBackground(new Color(153, 204, 204));
		contentPane.add(panelGame, "cell 0 1 1 3,grow");
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
				
				if(activeCard.equals(cardBuild))
				{
					constructBuilding(panelSelectItem.getMapping()[panelSelectItem.getCurrentX()][panelSelectItem.getCurrentY()][1]);
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
				String tile = null, item = null;
				panelGame.setDragged(false);
				panelGame.setPosX(e.getX() / panelGame.getTileSize() * panelGame.getTileSize() );
				panelGame.setPosY(e.getY() / panelGame.getTileSize() * panelGame.getTileSize() );
				lblPosition.setText("- X: "+ panelGame.getCurrentX() +" | Y: "+ panelGame.getCurrentY());
				for (MapTile each:allTiles)
				{
					if(Integer.parseInt(each.getID()) == panelGame.getMapping()[panelGame.getCurrentX()][panelGame.getCurrentY()][1])
					{
						tile = each.getName().substring(4);
						break;
					}
		
				}
				for (Item each:allItems)
				{
					if(Integer.parseInt(each.getID()) == panelGame.getMapping()[panelGame.getCurrentX()][panelGame.getCurrentY()][4])
					{
						item = each.getName();
						break;
					}
		
				}
				lblTilename.setText(tile);
				lblItemname.setText(item);
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				panelGame.setDragged(true);
				panelGame.setPosX(e.getX() / panelGame.getTileSize() * panelGame.getTileSize() );
				panelGame.setPosY(e.getY() / panelGame.getTileSize() * panelGame.getTileSize() );
				lblPosition.setText("- X: "+ panelGame.getCurrentX() +" | Y: "+ panelGame.getCurrentY());
				repaint();
			}
		});
		panelGame.setShowGrid(false);
		
		panelMiniMap = new JGridPanel(panelGame.getTilesX()/4, panelGame.getTilesY()/4, 1);
		panelMiniMap.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mmuGame.clickMiniMap(e);
			}
		});
		panelMiniMap.setBackground(new Color(102, 153, 153));
		FlowLayout flowLayout = (FlowLayout) panelMiniMap.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panelMiniMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mmuGame.clickMiniMap(e);
			}
		});
		
		panelToolSelection = new JPanel();
		panelToolSelection.setBackground(new Color(102, 153, 153));
		contentPane.add(panelToolSelection, "cell 1 2,grow");
		panelToolSelection.setLayout(new MigLayout("", "[grow][grow][grow]", "[][]"));
		
		tglbtnBuild = new JToggleButton("Build");
		panelToolSelection.add(tglbtnBuild, "cell 0 0,growx");
		tglbtnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolsCard.show(panelTools, cardBuild);
				tglbtnBuild.setSelected(true);
				tglbtnInfo.setSelected(false);
				tglbtnStats.setSelected(false);
				activeCard = cardBuild;

			}
		});
		tglbtnBuild.setSelected(true);
		
		tglbtnInfo = new JToggleButton("Info");
		panelToolSelection.add(tglbtnInfo, "cell 1 0,growx");
		tglbtnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolsCard.show(panelTools, cardInfo);
				tglbtnBuild.setSelected(false);
				tglbtnInfo.setSelected(true);
				tglbtnStats.setSelected(false);
				activeCard = cardInfo;
			}
		});
		
		tglbtnStats = new JToggleButton("Stats");
		panelToolSelection.add(tglbtnStats, "cell 2 0,growx");
		tglbtnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolsCard.show(panelTools, cardStats);
				tglbtnBuild.setSelected(false);
				tglbtnInfo.setSelected(false);
				tglbtnStats.setSelected(true);
				activeCard = cardStats;
			}
		});
		
		panelTools = new JPanel();
		panelTools.setBorder(null);
		panelTools.setBackground(new Color(102, 153, 153));
		contentPane.add(panelTools, "cell 1 3,grow");
		panelTools.setLayout(new CardLayout(0, 0));
		toolsCard = ((CardLayout)panelTools.getLayout());
		
		panelBuild = new JPanel();
		panelBuild.setBackground(new Color(102, 153, 153));
		panelTools.add(panelBuild, cardBuild);
		panelBuild.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		lblBuild = new JLabel("Build");
		panelBuild.add(lblBuild, "cell 0 0");
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelBuild.add(scrollPane, "cell 0 1,grow");
		
		panelSelectItem = new JGridPanel(3,6,4);
		panelSelectItem.setTileSize(64);
		panelSelectItem.setPreferredSize(new Dimension(192, (panelSelectItem.getTilesY() * panelSelectItem.getTileSize())));
		panelSelectItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panelSelectItem.setDragged(false);
				panelSelectItem.setPosX(e.getX() / panelSelectItem.getTileSize() * panelSelectItem.getTileSize() );
				panelSelectItem.setPosY(e.getY() / panelSelectItem.getTileSize() * panelSelectItem.getTileSize() ); 
				panelSelectItem.repaint();
			}
		});
		
		
		panelSelectItem.setBackground(new Color(102, 153, 153));
		
		scrollPane.setViewportView(panelSelectItem);
		scrollPane.getVerticalScrollBar().setUnitIncrement(panelSelectItem.getTileSize());
				
		panelInfo = new JPanel();
		panelInfo.setBackground(new Color(102, 153, 153));
		panelTools.add(panelInfo, cardInfo);
		panelInfo.setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		lblInfo = new JLabel("Info");
		panelInfo.add(lblInfo, "cell 0 0");
		
		lblPosition = new JLabel("- X: 1 | Y: 1");
		panelInfo.add(lblPosition, "cell 1 0,growx");
		
		lblTilename = new JLabel("TileName");
		panelInfo.add(lblTilename, "cell 0 1 2 1");
		
		lblItemname = new JLabel("ItemName");
		panelInfo.add(lblItemname, "cell 0 2 2 1");
		
		panelStats = new JPanel();
		panelStats.setBackground(new Color(102, 153, 153));
		panelTools.add(panelStats, cardStats);
		panelStats.setLayout(new MigLayout("", "[]", "[][][]"));
		
		lblStats = new JLabel("Stats");
		panelStats.add(lblStats, "cell 0 0");
		
		lblInhabs = new JLabel("Inhabitants: 0");
		panelStats.add(lblInhabs, "cell 0 1");
		
		lblHousingSpace = new JLabel("Housing Space: 0");
		panelStats.add(lblHousingSpace, "cell 0 2");
		
		panelStorage = new JPanel();
		panelStorage.setBackground(new Color(102, 153, 153));
		panelTools.add(panelStorage, cardStorage);
		panelStorage.setLayout(new MigLayout("", "[grow,fill]", "[][grow]"));
		
		lblStorageStone = new JLabel("Storage");
		panelStorage.add(lblStorageStone, "cell 0 0");
		
		scrollPaneStorage = new JScrollPane();
		scrollPaneStorage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelStorage.add(scrollPaneStorage, "cell 0 1,grow");
		
		panelStorageItems = new JPanel();
		panelStorageItems.setBackground(new Color(102, 153, 153));
		scrollPaneStorage.setViewportView(panelStorageItems);
		panelStorageItems.setLayout(new MigLayout("", "[32px:n:32px][40px:n:40px,right][grow][32px:n:32px][40px:n:40px,right]", "[32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow]"));
		
		panelStorageWood = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.jpg");
		panelStorageItems.add(panelStorageWood, "cell 0 0,grow");
		
		lblStorageWood = new JLabel("0");
		panelStorageItems.add(lblStorageWood, "cell 1 0");
		
		panelStorageSpacer = new JPanel();
		panelStorageSpacer.setBackground(new Color(102, 153, 153));
		panelStorageItems.add(panelStorageSpacer, "cell 2 0 1 2,grow");
		
		panelStorageStone = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.jpg");
		panelStorageItems.add(panelStorageStone, "cell 3 0,grow");
		
		lblStorageStone = new JLabel("0");
		panelStorageItems.add(lblStorageStone, "cell 4 0");
		
		panelStorageSteel = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.jpg");
		panelStorageItems.add(panelStorageSteel, "cell 0 1,grow");
		
		lblStorageSteel = new JLabel("0");
		panelStorageItems.add(lblStorageSteel, "cell 1 1");
		
		panelStorageGlass = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.jpg");
		panelStorageItems.add(panelStorageGlass, "cell 3 1,grow");
		
		lblStorageGlass = new JLabel("0");
		panelStorageItems.add(lblStorageGlass, "cell 4 1");
		
		panelStorageWater = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "water.jpg");
		panelStorageItems.add(panelStorageWater, "cell 0 2,grow");
		
		lblStorageWater = new JLabel("0");
		panelStorageItems.add(lblStorageWater, "cell 1 2");
		
		panelStorageVegetables = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "water.jpg");
		panelStorageItems.add(panelStorageVegetables, "cell 3 2,grow");
		
		lblStorageVegetables = new JLabel("0");
		panelStorageItems.add(lblStorageVegetables, "cell 4 2");
		
		panelStorageGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.jpg");
		panelStorageItems.add(panelStorageGold, "cell 0 3,grow");
		
		lblStorageGold = new JLabel("0");
		panelStorageItems.add(lblStorageGold, "cell 1 3");
		
		panelQuit = new JPanel();
		panelQuit.setBackground(new Color(102, 153, 153));
		contentPane.add(panelQuit, "cell 1 0,grow");
		panelQuit.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][]"));
		
		lblExit1 = new JLabel("Return to Main Menu?");
		panelQuit.add(lblExit1, "cell 0 0 3 1,alignx center");
		
		btnResume = new JButton("Resume");
		panelQuit.add(btnResume, "cell 0 1,growx");
		
		btnSaveQuit = new JButton("Save & Quit");
		btnSaveQuit.setVisible(false);
		btnSaveQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (gsGame.getZoomLevel() > gsGame.baseZoomLevel)
					gsGame.zoomOut(panelGame.getWidth() / 2, panelGame.getHeight() / 2);
				while (gsGame.getZoomLevel() < gsGame.baseZoomLevel)
					gsGame.zoomIn(panelGame.getWidth() / 2, panelGame.getHeight() / 2);
				gioGame.save("test", true);
				dispose();
				MainMenu.main(null);
			}
		});
		panelQuit.add(btnSaveQuit, "cell 1 1,growx");
		
		btnQuit = new JButton("Quit");
		panelQuit.add(btnQuit, "cell 2 1,growx");
		btnQuit.setVisible(false);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenu.main(null);
			}
		});
		btnResume.setVisible(false);
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideExitMenu();
			}
		});
		lblExit1.setVisible(false);	
		
		
		
		InputMap input = panelGame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = panelGame.getActionMap();
	
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "plus");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "minus");
		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightR");
		
		
		action.put("up", moveUp);
		action.put("down", moveDown);
		action.put("left", moveLeft);
		action.put("right", moveRight);
		action.put("plus", plus);
		action.put("minus", minus);
		action.put("escape", escape);
		
		action.put("upR", moveUpRelease);
		action.put("downR", moveDownRelease);
		action.put("leftR", moveLeftRelease);
		action.put("rightR", moveRightRelease);	
		
		panelGame.setDoubleBuffered(true);
		readSettings();
		
		cwGame = new Clockwork(lblDay,lblHours, lblMinutes, this);
		if(isNewGame)
			callMap(file, false);
		else
			callMap(file, true);
		designMiniMap(miniMapDetail, miniMapScale);
		
		gsGame = new GridScroller(panelGame, contentPane);
		gridScroll = new Thread(gsGame);
		gridScroll.start();
		
		mmuGame = new MiniMapUpdater(panelMiniMap, panelGame);
		miniMapUpdate = new Thread(mmuGame);
		miniMapUpdate.start();
		
		setUpBuildCard();
		if (isNewGame)
			setUpStorage();
		setUpLiving();
		happiness = 70;
		
		caller.dispose();
		
		clockwork = new Thread(cwGame);
		clockwork.start();
	}
	
	public void callMap(String name, boolean isNewGame)
	{

		gioGame = new GridIO(panelGame, allTiles, allItems, this);
		allTiles = gioGame.createTileList();
		allItems = gioGame.createItemList(false);
		constructableItems = gioGame.createItemList(true);
		
		gioGame.load(name, isNewGame);
	}
	
	public void designMiniMap(int detail, int scale)
	{
		GridIO gioMiniMap = new GridIO(panelMiniMap, allTiles, allItems, this);
		gioMiniMap.newMap(panelGame.getTilesX()/detail, panelGame.getTilesY()/detail);
		if (panelGame.getTilesX() >= panelGame.getTilesY())
		{
			if (panelGame.getTilesX() % panelMiniMap.getTilesX() == 0)
				detail = (panelGame.getTilesX() / panelMiniMap.getTilesX());
			else
				detail = (panelGame.getTilesX() / panelMiniMap.getTilesX()) + 1;
		}
		else
		{
			if (panelGame.getTilesY() % panelMiniMap.getTilesY() == 0)
				detail = (panelGame.getTilesY() / panelMiniMap.getTilesY());
			else
				detail = (panelGame.getTilesY() / panelMiniMap.getTilesY()) + 1;
		}
			
		
		for (int x = 0; x < panelGame.getTilesX(); x += detail)
		{
			for (int y = 0; y < panelGame.getTilesY(); y += detail)
			{
				panelMiniMap.applyProperty(x /detail , y /detail , 0, panelGame.getMapping()[x][y][0]);
			}
		}
		
		panelMiniMap.setShowGrid(false);
		panelMiniMap.setDragged(true); // Simulation of Mousedrag to apply the visible area as square on the MiniMap.
		contentPane.add(panelMiniMap, "cell 1 1,grow");
		scaleMiniMap();
	}
	public void scaleMiniMap()
	{
		int scale;
		final int size = 256;
		if (panelGame.getTilesX() > size || panelGame.getTilesY() > size)
			scale = 1;
		else
		{
			if (panelGame.getTilesX() >= panelGame.getTilesY())
				scale = size / panelGame.getTilesX();
			else
				scale = size / panelGame.getTilesY();
		}
		panelMiniMap.setTileSize(scale);
	}
	
	public void showExitMenu()
	{
		lblExit1.setVisible(true);
		btnResume.setVisible(true);
		btnSaveQuit.setVisible(true);
		btnQuit.setVisible(true);
		askExit = true;
	}
	public void hideExitMenu()
	{
		lblExit1.setVisible(false);
		btnResume.setVisible(false);
		btnSaveQuit.setVisible(false);
		btnQuit.setVisible(false);
		askExit = false;
	}
	
	public void setUpBuildCard()
	{
		int column = 0;
		int row = 0;
		ArrayList<String> config = readBuildConfig();
		for(String line: config)
		{
			if (!(line.trim().equals("-new") || line.trim().equals("--")))
			{
				for(Item each: constructableItems)
				{
					if(each.getName().equals(line))
					{
						panelSelectItem.applyItemImage(column, row, each.getImage());
						panelSelectItem.applyProperty(column, row, 1, Integer.parseInt(each.getID()));
					}
				}
				row++;
			}
			else if (line.trim().equals("-new"))
			{
				row = 0;
				column++;
			}
		}
		for(int x = 0; x < panelSelectItem.getMapping().length; x++)
		{
			for(int y = 0; y < panelSelectItem.getMapping()[0].length; y++)
			{
				panelSelectItem.applyProperty(x, y, 0, (102 *256*256 + 155 *256 + 155));
			}
		}
	}
	
	public ArrayList<String> readBuildConfig()
	{
		BufferedReader readConfig = null;
		ArrayList<String> allLines = new ArrayList<String>();
		String line;
		
		try
		{
			readConfig = new BufferedReader(new FileReader(new File("src" + File.separator + "config" + File.separator + "build.config")));
			while ((line=readConfig.readLine()) != null)
			{
				allLines.add(line);
			}
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "build.config is corrupted or does not exist.");
		}
		finally
		{
			try
			{
				if (readConfig != null)
					readConfig.close();
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(null, "build.config is corrupted or does not exist.");
			}
		}
		return allLines;
		
	}
	
	public void constructBuilding(int id)
	{
		boolean canConstructHere = false, enoughRes1 = false, enoughRes2 = false, enoughRes3 = false;
		for(MapTile each: allTiles)
		{
			if(Integer.parseInt(each.getID()) == panelGame.getMapping()[panelGame.getCurrentX()][panelGame.getCurrentY()][1] && each.canCarryItem())
				canConstructHere = true;
		}
		
		for(Item each: constructableItems)
		{
			if (Integer.parseInt(each.getID()) == id && panelGame.getMapping()[panelGame.getCurrentX()][panelGame.getCurrentY()][4] == 0 && canConstructHere)
			{
				if (each.getBuildResource1() == 102 && storeWood >= each.getAmountBuildResource1())
					enoughRes1 = true;
				else if (each.getBuildResource2() == 102 && storeWood >= each.getAmountBuildResource2())	
					enoughRes2 = true;
				else if (each.getBuildResource3() == 102 && storeWood >= each.getAmountBuildResource3()) 
					enoughRes3 = true;
				
				if (each.getBuildResource1() == 103 && storeStone >= each.getAmountBuildResource1())
					enoughRes1 = true;
				else if (each.getBuildResource2() == 103 && storeStone >= each.getAmountBuildResource2())	
					enoughRes2 = true;
				else if (each.getBuildResource3() == 103 && storeStone >= each.getAmountBuildResource3()) 
					enoughRes3 = true;
				
				if (each.getBuildResource1() == 104 && storeSteel >= each.getAmountBuildResource1())
					enoughRes1 = true;
				else if (each.getBuildResource2() == 104 && storeSteel >= each.getAmountBuildResource2())	
					enoughRes2 = true;
				else if (each.getBuildResource3() == 104 && storeSteel >= each.getAmountBuildResource3()) 
					enoughRes3 = true;
				
				if (each.getBuildResource1() == 105 && storeGlass >= each.getAmountBuildResource1())
					enoughRes1 = true;
				else if (each.getBuildResource2() == 105 && storeGlass >= each.getAmountBuildResource2())	
					enoughRes2 = true;
				else if (each.getBuildResource3() == 105 && storeGlass >= each.getAmountBuildResource3()) 
					enoughRes3 = true;
				
				if (each.getBuildResource1() == 0) 
					enoughRes1 = true;
				if (each.getBuildResource2() == 0)
					enoughRes2 = true;
				if (each.getBuildResource3() == 0) 
					enoughRes3 = true;
				if (enoughRes1 && enoughRes2 && enoughRes3)
				{
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 4, Integer.parseInt(each.getID()));
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 5, each.getPurpose());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 6, Utilities.boolToInt(each.isConstructable()));
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 7, each.getEmployee());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 8, Utilities.boolToInt(each.hasEmployee()));
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 9, each.getCapacity());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 10, each.getUsedCapacity());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 11, each.getStore1());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 12, each.getStore2());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 13, each.getStore3());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 14, each.getStoreSideProduct());
					panelGame.applyItemImage(panelGame.getCurrentX(), panelGame.getCurrentY(), each.getImage());
					
					if (each.getPurpose() == 1)
					{
						capacity += each.getCapacity();
						lblHousingSpace.setText("Housing Space: "+ capacity);
					}
					
					
					if (each.getBuildResource1() == 102)
						storeWood -= each.getAmountBuildResource1();
					else if (each.getBuildResource2() == 102)	
						storeWood -= each.getAmountBuildResource2();
					else if (each.getBuildResource3() == 102) 	
						storeWood -= each.getAmountBuildResource3();
					
					if (each.getBuildResource1() == 103)
						storeStone -= each.getAmountBuildResource1();
					else if (each.getBuildResource2() == 103)	
						storeStone -= each.getAmountBuildResource2();
					else if (each.getBuildResource3() == 103) 	
						storeStone -= each.getAmountBuildResource3();
					
					if (each.getBuildResource1() == 104)
						storeSteel -= each.getAmountBuildResource1();
					else if (each.getBuildResource2() == 104)	
						storeSteel -= each.getAmountBuildResource2();
					else if (each.getBuildResource3() == 104) 	
						storeSteel -= each.getAmountBuildResource3();
					
					if (each.getBuildResource1() == 105)
						storeGlass -= each.getAmountBuildResource1();
					else if (each.getBuildResource2() == 105)	
						storeGlass -= each.getAmountBuildResource2();
					else if (each.getBuildResource3() == 105) 	
						storeGlass -= each.getAmountBuildResource3();
	
					lblStoreWood.setText(String.valueOf(storeWood));
					lblStoreStone.setText(String.valueOf(storeStone));
					lblStoreSteel.setText(String.valueOf(storeSteel));
					lblStoreGlass.setText(String.valueOf(storeGlass));
				}
			}
		}
		panelGame.repaint();
	}	
	
	public void setUpStorage(int[] storage)
	{
		storeWood = storage[0];
		storeStone = storage[1];
		storeSteel = storage[2];
		storeGlass = storage[3];
		storeWater = storage[4];
		storeVegetables = storage[5];
		storeGold = storage[6];
		
		displayStorage();
	}
	
	public void setUpStorage()
	{
		storeWood = 30;
		storeStone = 10;
		storeSteel = 10;
		storeGlass = 10;
		storeGold = 10;
		storeWater = 20;
		storeVegetables = 20;
		
		displayStorage();
	}
	
	public void displayStorage()
	{
		lblStorageWood.setText(String.valueOf(storeWood));
		lblStorageStone.setText(String.valueOf(storeStone));
		lblStorageSteel.setText(String.valueOf(storeSteel));
		lblStorageGlass.setText(String.valueOf(storeGlass));
		lblStorageGold.setText(String.valueOf(storeGold));
		lblStorageWater.setText(String.valueOf(storeWater));
		lblStorageVegetables.setText(String.valueOf(storeVegetables));
		
		lblStoreWood.setText(String.valueOf(storeWood));
		lblStoreStone.setText(String.valueOf(storeStone));
		lblStoreSteel.setText(String.valueOf(storeSteel));
		lblStoreGlass.setText(String.valueOf(storeGlass));
		lblGold.setText(String.valueOf(storeGold));
		
	}
	public void setUpLiving()
	{
		inhabs = 0;
		capacity = 0;
		for (int x = 0; x < panelGame.getTilesX(); x++)
		{
			for (int y= 0; y < panelGame.getTilesY(); y++)
			{
				capacity += panelGame.getMapping()[x][y][9]; 
				inhabs += panelGame.getMapping()[x][y][10]; 
				lblInhabs.setText("Inhabitants: "+ inhabs);
				lblHousingSpace.setText("Housing Space: "+ capacity);
			}
		}
	}
	
	
	public void newTick()
	{
		Random r = new Random();
		final int percentageBaseNewInhab = 4;
		final int ticksPerDayInGame = cwGame.getTICKSPERSEC() * (60/cwGame.getINTERVAL()) * 24;
		final int timeToConsume = 1700; //equals 17:00
		final int timeToCheckLiving = 600;
		{
			int randomNewInhab = r.nextInt();
			if (randomNewInhab < 0)
				randomNewInhab *= -1;
			int limit = 10000;			//determines likeliness of an inhab being born
			if (inhabs >= capacity)
				limit *= 10;
			int required = limit - (percentageBaseNewInhab * inhabs/2);
			if (randomNewInhab%limit >= required)
			{
				inhabs++;
				lblInhabs.setText("Inhabitants: "+ inhabs);
			}
		}
		{
			if (cwGame.getContinousTime() % ticksPerDayInGame == ticksPerDayInGame * timeToConsume / 2400)
				consumeGoods();
			if (cwGame.getContinousTime() % ticksPerDayInGame == ticksPerDayInGame * timeToCheckLiving / 2400)
				checkHousingSpace();
		}
	}
	
	public void consumeGoods()
	{				
		if (storeWater / (inhabs * 2) >= 1)
		{
			happiness = happiness + 2;
			storeWater -= inhabs * 2;
		}
		else
		{
			happiness = happiness + 2 - (5 * (1 - (storeWater / (inhabs * 2.0))));
			storeWater = 0;
		}
		
		if (storeVegetables / (inhabs * 2) >= 1)
		{
			happiness = happiness + 2;
			storeVegetables -= inhabs * 2;
		}
		else
		{
			happiness = happiness + 2 - (5 * (1 - (storeVegetables / (inhabs * 2.0))));
			storeVegetables = 0;
		}

		if (happiness > 100)
			happiness = 100;
		else if (happiness < 0)
			happiness = 0;
		Double happy = new Double(happiness);
		lblHappiness.setText(String.valueOf(happy.intValue()));
		lblStorageWater.setText(String.valueOf(storeWater));
		lblStorageVegetables.setText(String.valueOf(storeVegetables));
	}
	
	public void checkHousingSpace()
	{
		if(inhabs > capacity)
		{
			happiness = happiness + 2 - (20 * (1 - (storeVegetables / (inhabs * 2.0))));
		}
		else
		{
			happiness = happiness + 2;
		}
		Double happy = new Double(happiness);
		lblHappiness.setText(String.valueOf(happy.intValue()));
	}
	
	
	public void speedUp()
	{
		synchronized (cwGame) 
		{
			if (cwGame.getSpeed() < maxSpeed) 
				cwGame.setSpeed(cwGame.getSpeed() + 1);
		}
		btnSpeed.setText(cwGame.getSpeed() + "x");
	}
	
	public void speedDown()
	{
		synchronized (cwGame) 
		{
			if (cwGame.getSpeed() > 1) 	
				cwGame.setSpeed(cwGame.getSpeed() - 1);
		}
		btnSpeed.setText(cwGame.getSpeed() + "x");
	}
	
	public void readSettings()
	{
		BufferedReader configRead = null;
		String data = null;
		try
		{
			{//VideoSettings
				configRead = new BufferedReader(new FileReader("src" + File.separator + "config" + File.separator + "settings.config"));
				data = configRead.readLine();
				if (Boolean.parseBoolean(data))
					setExtendedState(MAXIMIZED_BOTH);
				else
					setExtendedState(NORMAL);
			}
			
			{//AudioSettings
				data = configRead.readLine();
				if (Boolean.parseBoolean(data))
				{

				}
				else
				{

				}
			}
			
			{//GameplaySettings
				
			}
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Critical Exception: Cannot read settings!");
		}
		finally
		{
			try
			{
				if (configRead != null)
				{
					configRead.close();
				}
			}
			catch(IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Critical Exception: Cannot read settings!");
			}
		}	
	}
}


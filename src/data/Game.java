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
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTextField;

public class Game extends JFrame {
	private GridIO gioGame;
	private GridScroller gsGame;
	private MiniMapUpdater mmuGame;
	private Clockwork cwGame;
	private PanelAnimator paGame;
	private Thread gridScroll;
	private Thread miniMapUpdate;
	private Thread clockwork;
	private Thread animator;
	
	private final Color basicBackground = new Color(255, 219, 153);
	private final Color darkBackground = new Color(229, 194, 137);
	
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
	private CardLayout mainCard;
	private final String cardGame 	 = "cardGame" ;
	private final String cardResearch= "cardResearch";
	private final String cardTrade	 = "cardTrade";
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
	
	private int techLevel;
	
	private int storeWater;
	private int storeVegetables;
	private int storeClothes;
	private int storeShoes;
	
	private int storeMilk;
	private int storeMeat;
	private int storeMedicine;
	private int storeCoal;
	private int storeHoney;
	private int storeOrnaments;
	
	private int storeBeer;
	private int storeFruits;
	private int storeHorses;
	private int storeBread;
	private int storeTobacco;
	private int storePerfume;
	private int storeCheese;
	private int storeCocoa;
	
	private int storeIcewine;
	private int storeCake;
	private int storeDeer;
	private int storeChocolates;
	private int storePistols;
	private int storeLiquor;
	private int storeBooks;
	private int storeJewelry;
	private int storeSalmon;
	private int storeCoffee;
	
	private int storeWood;
	private int storeStone;
	private int storeSteel;
	private int storeGlass;
	private int storeSand;
	private int storeWool;
	private int storeLeather;
	private int storeMarshweed;
	private int storeGrain;
	private int storeHops;
	private int storeCocoabeans;
	private int storeRawChocolate;
	private int storeIceflowers;
	private int storeBarrels;
	private int storePaper;
	private int storeGold;
	private int storeIronOre;
	private int storeGoldOre;
	
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
	private JPanel panelMain;
	private JPanel panelResearch;
	
	private String saveFile;
	private JPanel panelTrade;
	private JLabel lblBuy;
	private JLabel lblSell;
	private JLabel lblNation;
	private JLabel lblIncome;
	private JImgPanel panelImgWest;
	private JImgPanel panelImgEast;
	private JImgPanel panelImgSouth;
	private JImgPanel panelImgNorth;
	private JLabel lblTrading;
	private JLabel lblTotal;
	private JLabel lblTotalIncome;
	
	
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources" + File.separator + "images" + File.separator + "game" + File.separator + "icon.png"));
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

		AbstractAction keyE = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				topBarCard.show(panelTopBar, cardIndicators);
			}
		};
		
		AbstractAction keyR = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				topBarCard.show(panelTopBar, cardResources);
			}
		};
		
		AbstractAction keyT = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				topBarCard.show(panelTopBar, cardTime);
			}
		};
		
		AbstractAction keyY = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				toggleBuild();
			}
		};
		
		AbstractAction keyX = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				toggleInfo();
			}
		};
		
		AbstractAction keyC = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				toggleStats();
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
		saveFile = file;
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
		contentPane.setBackground(basicBackground);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[398.00,grow][::256]", "[][256:n:256,grow][][361.00,grow]"));
		
		panelMain = new JPanel();
		contentPane.add(panelMain, "cell 0 1 1 3,grow");
		panelMain.setLayout(new CardLayout(0, 0));
		mainCard = ((CardLayout)panelMain.getLayout());
		
		panelGame = new JGridPanel(4,4,16);
		panelMain.add(panelGame, cardGame);
		FlowLayout flowLayout_1 = (FlowLayout) panelGame.getLayout();
		panelGame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0)
					gsGame.zoomIn(panelGame.getPosX(), panelGame.getPosY());
				else
					gsGame.zoomOut(panelGame.getPosX(), panelGame.getPosY());
			}
		});
		
		panelGame.setTileSize(48);
		panelGame.setBackground(basicBackground);
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
			}
		});
		panelGame.setShowGrid(false);
		
		panelResearch = new JPanel();
		panelResearch.setBackground(basicBackground);
		panelMain.add(panelResearch, cardResearch);
		panelResearch.setLayout(new MigLayout("", "[]", "[]"));
		
		panelStorage = new JPanel();
		panelStorage.setBackground(basicBackground);
		panelMain.add(panelStorage, cardStorage);
		panelStorage.setLayout(new MigLayout("", "[grow,fill]", "[][grow]"));
		
		lblStorageStone = new JLabel("Storage");
		panelStorage.add(lblStorageStone, "cell 0 0");
		
		scrollPaneStorage = new JScrollPane();
		scrollPaneStorage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelStorage.add(scrollPaneStorage, "cell 0 1,grow");
		
		panelStorageItems = new JPanel();
		panelStorageItems.setBackground(basicBackground);
		scrollPaneStorage.setViewportView(panelStorageItems);
		panelStorageItems.setLayout(new MigLayout("", "[32px:n:32px][40px:n:40px,right][grow][32px:n:32px][40px:n:40px,right]", "[32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow]"));
		
		panelStorageWood = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.png");
		panelStorageWood.setBackground(darkBackground);
		panelStorageItems.add(panelStorageWood, "cell 0 0,grow");
		
		lblStorageWood = new JLabel("0");
		panelStorageItems.add(lblStorageWood, "cell 1 0");
		
		panelStorageSpacer = new JPanel();
		panelStorageSpacer.setBackground(basicBackground);
		panelStorageItems.add(panelStorageSpacer, "cell 2 0 1 2,grow");
		
		panelStorageStone = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.png");
		panelStorageStone.setBackground(darkBackground);
		panelStorageItems.add(panelStorageStone, "cell 3 0,grow");
		
		lblStorageStone = new JLabel("0");
		panelStorageItems.add(lblStorageStone, "cell 4 0");
		
		panelStorageSteel = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.png");
		panelStorageSteel.setBackground(darkBackground);
		panelStorageItems.add(panelStorageSteel, "cell 0 1,grow");
		
		lblStorageSteel = new JLabel("0");
		panelStorageItems.add(lblStorageSteel, "cell 1 1");
		
		panelStorageGlass = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.png");
		panelStorageGlass.setBackground(darkBackground);
		panelStorageItems.add(panelStorageGlass, "cell 3 1,grow");
		
		lblStorageGlass = new JLabel("0");
		panelStorageItems.add(lblStorageGlass, "cell 4 1");
		
		panelStorageWater = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "water.png");
		panelStorageWater.setBackground(darkBackground);
		panelStorageItems.add(panelStorageWater, "cell 0 2,grow");
		
		lblStorageWater = new JLabel("0");
		panelStorageItems.add(lblStorageWater, "cell 1 2");
		
		panelStorageVegetables = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "vegetables.png");
		panelStorageVegetables.setBackground(darkBackground);
		panelStorageItems.add(panelStorageVegetables, "cell 3 2,grow");
		
		lblStorageVegetables = new JLabel("0");
		panelStorageItems.add(lblStorageVegetables, "cell 4 2");
		
		panelStorageGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.png");
		panelStorageGold.setBackground(darkBackground);
		panelStorageItems.add(panelStorageGold, "cell 0 3,grow");
		
		lblStorageGold = new JLabel("0");
		panelStorageItems.add(lblStorageGold, "cell 1 3");
		
		panelTrade = new JPanel();
		panelTrade.setBackground(basicBackground);
		panelMain.add(panelTrade, cardTrade);
		panelTrade.setLayout(new MigLayout("", "[32px:n:32px,grow][][][][]", "[][][32px:n:32px,grow][32px:n:32px][32px:n:32px][32px:n:32px][][]"));
		
		lblTrading = new JLabel("Trading");
		lblTrading.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTrade.add(lblTrading, "cell 0 0");
		
		lblNation = new JLabel("Nation");
		lblNation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblNation, "cell 0 1 2 1");
		
		lblBuy = new JLabel("Buy");
		lblBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblBuy, "flowx,cell 2 1");
		
		lblSell = new JLabel("Sell");
		lblSell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblSell, "cell 3 1");
		
		lblIncome = new JLabel("Income");
		lblIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncome, "cell 4 1");
		
		panelImgWest = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagWest.png");
		panelTrade.add(panelImgWest, "cell 0 2,grow");
		
		panelImgEast = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagEast.png");
		panelTrade.add(panelImgEast, "cell 0 3,grow");
		
		panelImgSouth = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagSouth.png");
		panelTrade.add(panelImgSouth, "cell 0 4,grow");
		
		panelImgNorth = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagNorth.png");
		panelTrade.add(panelImgNorth, "cell 0 5,grow");
		
		lblTotal = new JLabel("Total");
		panelTrade.add(lblTotal, "cell 4 6");
		
		lblTotalIncome = new JLabel("");
		panelTrade.add(lblTotalIncome, "cell 4 7");
		
		panelTopBar = new JPanel();
		panelTopBar.setBackground(darkBackground);
		contentPane.add(panelTopBar, "cell 0 0,grow");
		panelTopBar.setLayout(new CardLayout(0, 0));
		topBarCard = (CardLayout)panelTopBar.getLayout(); 
		
		panelResources = new JPanel();
		panelResources.setBackground(darkBackground);
		panelResources.setLayout(new MigLayout("", "[40px:n:40px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px,leading][48px:n:48px,right][12px:n:12px][48px:n:48px][48px:n:48px,right][grow]", "[grow,center]"));
		panelTopBar.add(panelResources, cardResources);
		
		panelTime = new JPanel();
		panelTime.setBackground(darkBackground);
		panelTopBar.add(panelTime, cardTime);
		panelTime.setLayout(new MigLayout("", "[40px:n:40px][48px:n:196px][48px:n:48px,right][12px:n:12px,center][48px:n:48px][48px:n:48px,right][grow]", "[grow,center]"));
		
		panelIndicators = new JPanel();
		panelIndicators.setBackground(darkBackground);
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
		
		panelPicHappiness = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "happiness3.png");
		panelPicHappiness.setToolTipText("HAPPINESS");
		panelPicHappiness.setBackground(darkBackground);
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
		lblDay.setBackground(darkBackground);
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
		
		panelPicWood = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.png");
		panelPicWood.setToolTipText("WOOD");
		panelPicWood.setBackground(darkBackground);
		panelResources.add(panelPicWood, "cell 1 0,grow");
		
		lblStoreWood = new JLabel("0");
		lblStoreWood.setFont(overview);
		panelResources.add(lblStoreWood, "cell 2 0,alignx center");
		
		panelSpacer1 = new JPanel();
		panelSpacer1.setBackground(darkBackground);
		panelResources.add(panelSpacer1, "cell 3 0,grow");
		
		panelPicStone = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.png");
		panelPicStone.setToolTipText("STONE");
		panelPicStone.setBackground(darkBackground);
		panelResources.add(panelPicStone, "cell 4 0,grow");
		
		lblStoreStone = new JLabel("0");
		lblStoreStone.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreStone, "cell 5 0,alignx center");
		
		panelSpacer2 = new JPanel();
		panelSpacer2.setBackground(darkBackground);
		panelResources.add(panelSpacer2, "cell 6 0,grow");
		
		panelPicSteel = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.png");
		panelPicSteel.setToolTipText("STEEL");
		panelPicSteel.setBackground(darkBackground);
		panelResources.add(panelPicSteel, "cell 7 0,grow");
		
		lblStoreSteel = new JLabel("0");
		lblStoreSteel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreSteel, "cell 8 0,alignx center");
		
		panelSpacer3 = new JPanel();
		panelSpacer3.setBackground(darkBackground);
		panelResources.add(panelSpacer3, "cell 9 0,grow");
		
		panelPicGlass = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.png");
		panelPicGlass.setToolTipText("GLASS");
		panelPicGlass.setBackground(darkBackground);
		panelResources.add(panelPicGlass, "cell 10 0,grow");
		
		lblStoreGlass = new JLabel("0");
		lblStoreGlass.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreGlass, "cell 11 0,alignx center");
		
		panelSpacer4 = new JPanel();
		panelSpacer4.setBackground(darkBackground);
		panelResources.add(panelSpacer4, "cell 12 0,grow");
		
		panelPicGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.png");
		panelPicGold.setToolTipText("GOLD");
		panelPicGold.setBackground(darkBackground);
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
		
		panelMiniMap = new JGridPanel(panelGame.getTilesX()/4, panelGame.getTilesY()/4, 1);
		panelMiniMap.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mmuGame.clickMiniMap(e);
			}
		});
		panelMiniMap.setBackground(darkBackground);
		FlowLayout flowLayout = (FlowLayout) panelMiniMap.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panelMiniMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mmuGame.clickMiniMap(e);
			}
		});
		
		panelToolSelection = new JPanel();
		panelToolSelection.setBackground(darkBackground);
		contentPane.add(panelToolSelection, "cell 1 2,grow");
		panelToolSelection.setLayout(new MigLayout("", "[grow][grow][grow]", "[][]"));
		
		tglbtnBuild = new JToggleButton("Build");
		panelToolSelection.add(tglbtnBuild, "cell 0 0,growx");
		tglbtnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleBuild();
			}
		});
		tglbtnBuild.setSelected(true);
		
		tglbtnInfo = new JToggleButton("Info");
		panelToolSelection.add(tglbtnInfo, "cell 1 0,growx");
		tglbtnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleInfo();
			}
		});
		
		tglbtnStats = new JToggleButton("Stats");
		panelToolSelection.add(tglbtnStats, "cell 2 0,growx");
		tglbtnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleStats();
			}
		});
		
		panelTools = new JPanel();
		panelTools.setBorder(null);
		panelTools.setBackground(darkBackground);
		contentPane.add(panelTools, "cell 1 3,grow");
		panelTools.setLayout(new CardLayout(0, 0));
		toolsCard = ((CardLayout)panelTools.getLayout());
		
		panelBuild = new JPanel();
		panelBuild.setBackground(darkBackground);
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
		
		
		panelSelectItem.setBackground(darkBackground);
		
		scrollPane.setViewportView(panelSelectItem);
		scrollPane.getVerticalScrollBar().setUnitIncrement(panelSelectItem.getTileSize());
				
		panelInfo = new JPanel();
		panelInfo.setBackground(darkBackground);
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
		panelStats.setBackground(darkBackground);
		panelTools.add(panelStats, cardStats);
		panelStats.setLayout(new MigLayout("", "[]", "[][][]"));
		
		lblStats = new JLabel("Stats");
		panelStats.add(lblStats, "cell 0 0");
		
		lblInhabs = new JLabel("Inhabitants: 0");
		panelStats.add(lblInhabs, "cell 0 1");
		
		lblHousingSpace = new JLabel("Housing Space: 0");
		panelStats.add(lblHousingSpace, "cell 0 2");
		
		
		
		panelQuit = new JPanel();
		panelQuit.setBackground(darkBackground);
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
				gioGame.save(saveFile, true);
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
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "globals");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "resources");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0), "time");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0), "build");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "info");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "stats");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "plus");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "minus");
		
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "upR_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "downR_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftR_alt");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightR");
		input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightR_alt");
		
		
		action.put("up", moveUp);
		action.put("up_alt", moveUp);
		action.put("down", moveDown);
		action.put("down_alt", moveDown);
		action.put("left", moveLeft);
		action.put("left_alt", moveLeft);
		action.put("right", moveRight);
		action.put("right_alt", moveRight);
		action.put("globals", keyE);
		action.put("resources", keyR);
		action.put("time", keyT);
		action.put("build", keyY);
		action.put("info", keyX);
		action.put("stats", keyC);
		action.put("plus", plus);
		action.put("minus", minus);
		action.put("escape", escape);
		
		action.put("upR", moveUpRelease);
		action.put("upR_alt", moveUpRelease);
		action.put("downR", moveDownRelease);
		action.put("downR_alt", moveDownRelease);
		action.put("leftR", moveLeftRelease);
		action.put("leftR_alt", moveLeftRelease);
		action.put("rightR", moveRightRelease);	
		action.put("rightR_alt", moveRightRelease);	
		readSettings();
		
		panelGame.setDoubleBuffered(true);
		
		gsGame = new GridScroller(panelGame, contentPane);
		mmuGame = new MiniMapUpdater(panelMiniMap, panelGame);
		paGame = new PanelAnimator(panelGame, 25);
		
		cwGame = new Clockwork(lblDay,lblHours, lblMinutes, this);
		if(isNewGame)
			callMap(file, false);
		else
			callMap(file, true);
		designMiniMap(miniMapDetail, miniMapScale);
		gridScroll = new Thread(gsGame);
		gridScroll.start();
		miniMapUpdate = new Thread(mmuGame);
		miniMapUpdate.start();
		animator = new Thread(paGame);
		animator.start();
		
		setUpBuildCard();
		if (isNewGame)
			setUpStorage();
		setUpLiving();
		happiness = 70;
		techLevel = 1;
		
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
				panelSelectItem.applyProperty(x, y, 0, (229 *256*256 + 194 *256 + 137));
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
		storeClothes = 0;
		storeCoal = 0;
		
		
		storeMilk = 0;
		storeMeat = 0;
		storeMedicine = 0;
		storeShoes = 0;
		storeHoney = 0;
		storeOrnaments = 0;
		
		storeBeer = 0;
		storeFruits = 0;
		storeHorses = 0;
		storeBread = 0;
		storeTobacco = 0;
		storePerfume = 0;
		storeCheese = 0;
		storeCocoa = 0;
		
		storeIcewine = 0;
		storeCake = 0;
		storeDeer = 0;
		storeChocolates = 0;
		storePistols = 0;
		storeLiquor = 0;
		storeBooks = 0;
		storeJewelry = 0;
		storeSalmon = 0;
		storeCoffee = 0;
		
		storeSand = 0;
		storeWool = 0;
		storeLeather = 0;
		storeMarshweed = 0;
		storeGrain = 0;
		storeHops = 0;
		storeCocoabeans = 0;
		storeRawChocolate = 0;
		storeIceflowers = 0;
		storeBarrels = 0;
		storePaper = 0;
		storeIronOre = 0;
		storeGoldOre = 0;
		
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
		if (techLevel >= 1)
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
			
			if (storeClothes / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeClothes -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeClothes / (inhabs * 2.0))));
				storeClothes = 0;
			}
			
			if (storeCoal / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeCoal -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeCoal / (inhabs * 2.0))));
				storeCoal = 0;
			}
			

		}
		
		if (techLevel >= 2)
		{	
			if (storeMilk / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeMilk -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeMilk / (inhabs * 2.0))));
				storeMilk = 0;
			}
			
			if (storeMeat / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeMeat -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeMeat / (inhabs * 2.0))));
				storeMeat = 0;
			}
			
			if (storeMedicine / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeMedicine -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeMedicine / (inhabs * 2.0))));
				storeMedicine = 0;
			}
			
			if (storeShoes / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeShoes -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeShoes / (inhabs * 2.0))));
				storeShoes = 0;
			}
			
			if (storeHoney / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeHoney -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeHoney / (inhabs * 2.0))));
				storeHoney = 0;
			}
			
			if (storeOrnaments / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeOrnaments -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeOrnaments / (inhabs * 2.0))));
				storeOrnaments = 0;
			}
		}
		
		if (techLevel >= 3)
		{
			if (storeBeer / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeBeer -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeBeer / (inhabs * 2.0))));
				storeBeer = 0;
			}
			
			if (storeFruits / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeFruits -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeFruits / (inhabs * 2.0))));
				storeFruits = 0;
			}
			
			if (storeHorses / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeHorses -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeHorses / (inhabs * 2.0))));
				storeHorses = 0;
			}
			
			if (storeBread / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeBread -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeBread / (inhabs * 2.0))));
				storeBread = 0;
			}
			
			if (storeTobacco / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeTobacco -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeTobacco / (inhabs * 2.0))));
				storeTobacco = 0;
			}
			
			if (storePerfume / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storePerfume -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storePerfume / (inhabs * 2.0))));
				storePerfume = 0;
			}
			
			if (storeCheese / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeCheese -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeCheese / (inhabs * 2.0))));
				storeCheese = 0;
			}
			
			if (storeCocoa / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeCocoa -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeCocoa / (inhabs * 2.0))));
				storeCocoa = 0;
			}
		}
		
		if (techLevel >= 4)
		{
			if (storeIcewine / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeIcewine -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeIcewine / (inhabs * 2.0))));
				storeIcewine = 0;
			}
			
			if (storeCake / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeCake -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeCake / (inhabs * 2.0))));
				storeCake = 0;
			}
			
			if (storeDeer / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeDeer -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeDeer / (inhabs * 2.0))));
				storeDeer = 0;
			}
			
			if (storeChocolates / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeChocolates -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeChocolates / (inhabs * 2.0))));
				storeChocolates = 0;
			}
			
			if (storePistols / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storePistols -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storePistols / (inhabs * 2.0))));
				storePistols = 0;
			}
			
			if (storeLiquor / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeLiquor -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeLiquor / (inhabs * 2.0))));
				storeLiquor = 0;
			}
			
			if (storeBooks / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeBooks -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeBooks / (inhabs * 2.0))));
				storeBooks = 0;
			}
			
			if (storeJewelry / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeJewelry -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeJewelry / (inhabs * 2.0))));
				storeJewelry = 0;
			}
			
			if (storeSalmon / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeSalmon -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeSalmon / (inhabs * 2.0))));
				storeSalmon = 0;
			}
			
			if (storeCoffee / (inhabs * 2) >= 1)
			{
				happiness = happiness + 2;
				storeCoffee -= inhabs * 2;
			}
			else
			{
				happiness = happiness + 2 - (5 * (1 - (storeCoffee / (inhabs * 2.0))));
				storeCoffee = 0;
			}
		}

		if (happiness > 100)
			happiness = 100;
		else if (happiness < 0)
			happiness = 0;
		
		lblStorageWater.setText(String.valueOf(storeWater));
		lblStorageVegetables.setText(String.valueOf(storeVegetables));
		showHappiness(happiness);
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
		showHappiness(happiness);
	}
	
	public void showHappiness(double happiness)
	{
		int level;
		Double happy = new Double(happiness);
		lblHappiness.setText(String.valueOf(happy.intValue()));
		if (happiness >= 90)
			level = 5;
		else if(happiness >= 70)
			level = 4;
		else if(happiness >= 50)
			level = 3;
		else if(happiness >= 25)
			level = 2;
		else
			level = 1;
		panelPicHappiness.setImage("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "happiness" + level +".png");
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
	
	public void toggleBuild()
	{
		toolsCard.show(panelTools, cardBuild);
		tglbtnBuild.setSelected(true);
		tglbtnInfo.setSelected(false);
		tglbtnStats.setSelected(false);
		activeCard = cardBuild;
	}
	
	public void toggleInfo()
	{
		toolsCard.show(panelTools, cardInfo);
		tglbtnBuild.setSelected(false);
		tglbtnInfo.setSelected(true);
		tglbtnStats.setSelected(false);
		activeCard = cardInfo;
	}
	
	public void toggleStats()
	{
		toolsCard.show(panelTools, cardStats);
		tglbtnBuild.setSelected(false);
		tglbtnInfo.setSelected(false);
		tglbtnStats.setSelected(true);
		activeCard = cardStats;
	}
	
}
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
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class Game extends JFrame {
	private GridIO gioGame;
	private GridScroller gsGame;
	private MiniMapUpdater mmuGame;
	private Clockwork cwGame;
	private PanelAnimator paGame;
	private StorageMonitor smGame;
	private Thread gridScroll;
	private Thread miniMapUpdate;
	private Thread clockwork;
	private Thread animator;
	private Thread storageMonitor;
	
	private final Color basicBackground = new Color(255, 219, 153);
	private final Color darkBackground = new Color(229, 194, 137);
	
	private final Color researchLocked = new Color(140, 0, 0);
	private final Color researchOk = new Color(255, 210, 0);
	private final Color researchDone = new Color(0, 140, 0);
	
	private ArrayList<MapTile> allTiles;
	private ArrayList<Item> allItems;
	private ArrayList<Item> constructableItems;	
	private ArrayList<Integer>productCosts1;
	private ArrayList<Integer>productCosts2;
	private ArrayList<Integer>productAmounts1;
	private ArrayList<Integer>productAmounts2;
	
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
	private int storeChocolate;
	private int storeIceflowers;
	private int storeBarrels;
	private int storePaper;
	private int storeGold;
	private int storeIronOre;
	private int storeGoldOre;
	private int storeCows;
	
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
	private JLabel lblStoreGold;
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
	private JPanel panelStorageSpacer1;
	private JImgPanel panelStorageWood;
	private JLabel lblStorageWood;
	private JImgPanel panelStorageStone;
	private JLabel lblStorageStone;
	private JImgPanel panelStorageSteel;
	private JLabel lblStorageSteel;
	private JImgPanel panelStorageGlass;
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
	private JLabel lblNation;
	private JLabel lblIncome;
	private JImgPanel panelImgWest;
	private JImgPanel panelImgEast;
	private JImgPanel panelImgSouth;
	private JImgPanel panelImgNorth;
	private JLabel lblTrading;
	private JLabel lblTotal;
	private JLabel lblTotalIncome;
	private JLabel lblBuyW;
	private JLabel lblBuyE;
	private JLabel lblBuyS;
	private JLabel lblBuyN;
	private JLabel lblSellW;
	private JLabel lblSellE;
	private JLabel lblSellS;
	private JLabel lblSellN;
	private JImgPanel panelImgWestBuy1;
	private JImgPanel panelImgEastBuy1;
	private JImgPanel panelImgSouthBuy1;
	private JImgPanel panelImgNorthBuy1;
	private JImgPanel panelImgWestSell1;
	private JImgPanel panelImgEastSell1;
	private JImgPanel panelImgSouthSell1;
	private JImgPanel panelImgNorthSell1;

	private JLabel lblIncomeWBuy;
	private JLabel lblIncomeWSell;
	private JLabel lblIncomeEBuy;
	private JLabel lblIncomeESell;
	private JLabel lblIncomeSBuy;
	private JLabel lblIncomeSSell;
	private JLabel lblIncomeNBuy;
	private JLabel lblIncomeNSell;
	private JImgPanel panelImgTotalGold;
	private JTextField textFieldWBuy1;
	private JTextField textFieldWSell1;
	private JTextField textFieldEBuy1;
	private JTextField textFieldESell1;
	private JTextField textFieldSBuy1;
	private JTextField textFieldSSell1;
	private JTextField textFieldNBuy1;
	private JTextField textFieldNSell1;
	private JImgPanel panelImgWestBuy2;
	private JImgPanel panelImgWestBuy3;
	private JImgPanel panelImgWestBuy4;
	private JImgPanel panelImgWestBuy5;
	private JTextField textFieldWBuy2;
	private JTextField textFieldWBuy3;
	private JTextField textFieldWBuy4;
	private JTextField textFieldWBuy5;
	private JImgPanel panelImgWestSell2;
	private JImgPanel panelImgWestSell3;
	private JImgPanel panelImgWestSell4;
	private JImgPanel panelImgWestSell5;
	private JImgPanel panelImgEastBuy2;
	private JImgPanel panelImgEastBuy3;
	private JImgPanel panelImgEastBuy4;
	private JImgPanel panelImgEastBuy5;
	private JImgPanel panelImgEastSell2;
	private JImgPanel panelImgEastSell3;
	private JImgPanel panelImgEastSell14;
	private JImgPanel panelImgEastSell5;
	private JImgPanel panelImgSouthBuy2;
	private JImgPanel panelImgSouthBuy3;
	private JImgPanel panelImgSouthBuy4;
	private JImgPanel panelImgSouthBuy5;
	private JImgPanel panelImgSouthSell2;
	private JImgPanel panelImgSouthSell3;
	private JImgPanel panelImgSouthSell4;
	private JImgPanel panelImgSouthSell5;
	private JImgPanel panelImgNorthBuy2;
	private JImgPanel panelImgNorthBuy3;
	private JImgPanel panelImgNorthBuy4;
	private JImgPanel panelImgNorthBuy5;
	private JImgPanel panelImgNorthSell2;
	private JImgPanel panelImgNorthSell3;
	private JImgPanel panelImgNorthSell4;
	private JImgPanel panelImgNorthSell5;
	private JTextField textFieldWSell2;
	private JTextField textFieldWSell3;
	private JTextField textFieldWSell4;
	private JTextField textFieldWSell5;
	private JTextField textFieldEBuy2;
	private JTextField textFieldEBuy3;
	private JTextField textFieldEBuy4;
	private JTextField textFieldEBuy5;
	private JTextField textFieldESell2;
	private JTextField textFieldESell3;
	private JTextField textFieldESell4;
	private JTextField textFieldESell5;
	private JTextField textFieldSBuy2;
	private JTextField textFieldSBuy3;
	private JTextField textFieldSBuy4;
	private JTextField textFieldSBuy5;
	private JTextField textFieldSSell2;
	private JTextField textFieldSSell3;
	private JTextField textFieldSSell4;
	private JTextField textFieldSSell5;
	private JTextField textFieldNBuy2;
	private JTextField textFieldNBuy3;
	private JTextField textFieldNBuy4;
	private JTextField textFieldNBuy5;
	private JTextField textFieldNSell2;
	private JTextField textFieldNSell3;
	private JTextField textFieldNSell4;
	private JTextField textFieldNSell5;
	private JLabel lblNationW;
	private JLabel lblNationE;
	private JLabel lblNationS;
	private JLabel lblNationN;
	
	private ArrayList<Integer> amountBuy = new ArrayList<Integer>();
	private ArrayList<Integer> amountSell = new ArrayList<Integer>();
	
	private int[] tradeData = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private ArrayList<Integer> tradeValues = new ArrayList<Integer>();
	private int westIncome;
	private int westPayment;
	private int eastIncome;
	private int eastPayment;
	private int southIncome;
	private int southPayment;
	private int northIncome;
	private int northPayment;
	private int income;
	private JButton btnConfirm;
	private JButton btnCalculate;
	private JButton btnStopPurchases;
	private JButton btnStopSales;
	private JLabel lblInvalidValuesFound;
	private JButton btnBackTrading;
	private JPanel panelStorageSpacer2;
	private JPanel panelStorageSpacer3;
	private JImgPanel panelStorageMilk;
	private JImgPanel panelStorageBeer;
	private JLabel lblStorageMilk;
	private JLabel lblStorageBeer;
	private JPanel panelStorageSpacer4;
	private JImgPanel panelStorageIcewine;
	private JLabel lblStorageIcewine;
	private JImgPanel panelStorageClothes;
	private JImgPanel panelStorageCoal;
	private JImgPanel panelStorageMeat;
	private JImgPanel panelStorageMedicine;
	private JImgPanel panelStorageShoes;
	private JImgPanel panelStorageHoney;
	private JImgPanel panelStorageOrnaments;
	private JImgPanel panelStorageMarshweed;
	private JImgPanel panelStorageFruits;
	private JImgPanel panelStorageHorses;
	private JImgPanel panelStorageBread;
	private JImgPanel panelStorageTobacco;
	private JImgPanel panelStoragePerfume;
	private JImgPanel panelStorageCocoa;
	private JImgPanel panelStorageCheese;
	private JImgPanel panelStorageCake;
	private JImgPanel panelStorageDeer;
	private JImgPanel panelStorageChocolates;
	private JImgPanel panelStoragePistols;
	private JImgPanel panelStorageLiquor;
	private JImgPanel panelStorageJewelry;
	private JImgPanel panelStorageCoffee;
	private JImgPanel panelStorageSalmon;
	private JImgPanel panelStorageBooks;
	private JImgPanel panelStorageWool;
	private JImgPanel panelStorageLeather;
	private JImgPanel panelStorageGrain;
	private JImgPanel panelStorageIronOre;
	private JImgPanel panelStorageChocolate;
	private JImgPanel panelStorageHops;
	private JImgPanel panelStorageCocoabeans;
	private JImgPanel panelStorageIceflowers;
	private JImgPanel panelStorageBarrels;
	private JImgPanel panelStorageGoldOre;
	private JImgPanel panelStoragePaper;
	private JLabel lblStorageGlass;
	private JLabel lblStorageClothes;
	private JLabel lblStorageCoal;
	private JLabel lblStorageWool;
	private JLabel lblStorageMeat;
	private JLabel lblStorageMedicine;
	private JLabel lblStorageShoes;
	private JLabel lblStorageHoney;
	private JLabel lblStorageOrnaments;
	private JLabel lblStorageLeather;
	private JLabel lblStorageMarshweed;
	private JLabel lblStorageFruits;
	private JLabel lblStorageHorses;
	private JLabel lblStorageBread;
	private JLabel lblStorageTobacco;
	private JLabel lblStoragePerfume;
	private JLabel lblStorageCheese;
	private JLabel lblStorageCocoa;
	private JLabel lblStorageGrain;
	private JLabel lblStorageHops;
	private JLabel lblStorageCocoabeans;
	private JLabel lblStorageRawChocolate;
	private JLabel lblStorageIronOre;
	private JLabel lblStorageCake;
	private JLabel lblStorageDeer;
	private JLabel lblStorageChocolates;
	private JLabel lblStoragePistols;
	private JLabel lblStorageLiquor;
	private JLabel lblStorageBooks;
	private JLabel lblStorageJewelry;
	private JLabel lblStorageSalmon;
	private JLabel lblStorageCoffee;
	private JLabel lblStorageIceflowers;
	private JLabel lblStorageBarrels;
	private JLabel lblStoragePaper;
	private JLabel lblStorageGoldOre;
	private JLabel lblBasic;
	private JLabel lblTechI;
	private JLabel lblTechIi;
	private JLabel lblTechIii;
	private JLabel lblTechIv;
	private JImgPanel panelStorageSand;
	private JLabel lblStorageSand;
	private JButton btnBackStorage;
	private JImgPanel panelStorageCows;
	private JLabel lblStorageStore;
	private JPanel panelResearchWeaving;
	private JPanel panelResearchCattleBreeding;
	private JPanel panelResearchBeeBreeding;
	private JPanel panelResearchEnhancedAgricultlure;
	private JPanel panelResearchExoticAgriculture;
	private JPanel panelResearchChocolate;
	private JPanel panelResearchEnhancedBaking;
	private JPanel panelResearchConfectionery;
	private JPanel panelResearchTrade1;
	private JPanel panelResearchButchery;
	private JPanel panelResearchTannery;
	private JPanel panelResearchCoalBurning;
	private JPanel panelResearchNatureStudies;
	private JPanel panelResearchTrade2;
	private JPanel panelResearchTrade3;
	private JPanel panelResearchTrade4;
	private JPanel panelResearchTradeExotic;
	private JPanel panelResearchTrade5;
	private JPanel panelResearchRevegetation;
	private JPanel panelResearchFlorist;
	private JPanel panelResearchAnatomy;
	private JPanel panelResearchHealth1;
	private JPanel panelResearchMining;
	private JPanel panelResearchIronProcessing;
	private JPanel panelResearchSandProcessing;
	private JPanel panelResearchBricklaying;
	private JPanel panelResearchHealth2;
	private JPanel panelResearchSafety;
	private JPanel panelResearchEnhancedCattleBreeding;
	private JPanel panelResearchHunting;
	private JPanel panelResearchBaking;
	private JPanel panelResearchDairy;
	private JImgPanel panelResearchFermentation;
	private JImgPanel panelResearchEnhancedFermentation;
	private JImgPanel panelResearchWine;
	private JImgPanel panelResearchLiquor;
	private JImgPanel panelResearchDeeperMines;
	private JImgPanel panelResearchGoldProcessing;
	private JImgPanel panelResearchAdvancedSmithing;
	private JImgPanel panelResearchFlintlockGuns;
	private JImgPanel panelResearchPaper;
	private JImgPanel panelResearchPrinting;
	private JImgPanel panelResearchGlassProduction;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblResearch;
	
	
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
		storage.add(storeSand);
		storage.add(storeWool);
		storage.add(storeLeather);
		storage.add(storeMarshweed);
		storage.add(storeGrain);
		storage.add(storeHops);
		storage.add(storeCocoabeans);
		storage.add(storeChocolate);
		storage.add(storeIceflowers);
		storage.add(storeBarrels);
		storage.add(storePaper);
		storage.add(storeGold);
		storage.add(storeIronOre);
		storage.add(storeGoldOre);
		
		storage.add(storeWater);
		storage.add(storeVegetables);
		storage.add(storeClothes);
		storage.add(storeCoal);
		
		storage.add(storeMilk);
		storage.add(storeMeat);
		storage.add(storeMedicine);
		storage.add(storeShoes);
		storage.add(storeHoney);
		storage.add(storeOrnaments);
		
		storage.add(storeBeer);
		storage.add(storeFruits);
		storage.add(storeHorses);
		storage.add(storeBread);
		storage.add(storeTobacco);
		storage.add(storePerfume);
		storage.add(storeCheese);
		storage.add(storeCocoa);
		
		storage.add(storeIcewine);
		storage.add(storeCake);
		storage.add(storeDeer);
		storage.add(storeChocolates);
		storage.add(storePistols);
		storage.add(storeLiquor);
		storage.add(storeBooks);
		storage.add(storeJewelry);
		storage.add(storeSalmon);
		storage.add(storeCoffee);
		
		return storage;
	}
	
	public ArrayList<Integer> getTrades()
	{
		ArrayList<Integer> trades = new ArrayList<Integer>();
		trades.add(Integer.parseInt(textFieldWBuy1.getText()));
		trades.add(Integer.parseInt(textFieldWBuy2.getText()));
		trades.add(Integer.parseInt(textFieldWBuy3.getText()));
		trades.add(Integer.parseInt(textFieldWBuy4.getText()));
		trades.add(Integer.parseInt(textFieldWBuy5.getText()));
		
		trades.add(Integer.parseInt(textFieldWSell1.getText()));
		trades.add(Integer.parseInt(textFieldWSell2.getText()));
		trades.add(Integer.parseInt(textFieldWSell3.getText()));
		trades.add(Integer.parseInt(textFieldWSell4.getText()));
		trades.add(Integer.parseInt(textFieldWSell5.getText()));
		
		
		trades.add(Integer.parseInt(textFieldEBuy1.getText()));
		trades.add(Integer.parseInt(textFieldEBuy2.getText()));
		trades.add(Integer.parseInt(textFieldEBuy3.getText()));
		trades.add(Integer.parseInt(textFieldEBuy4.getText()));
		trades.add(Integer.parseInt(textFieldEBuy5.getText()));
		
		trades.add(Integer.parseInt(textFieldESell1.getText()));
		trades.add(Integer.parseInt(textFieldESell2.getText()));
		trades.add(Integer.parseInt(textFieldESell3.getText()));
		trades.add(Integer.parseInt(textFieldESell4.getText()));
		trades.add(Integer.parseInt(textFieldESell5.getText()));
		
		
		trades.add(Integer.parseInt(textFieldSBuy1.getText()));
		trades.add(Integer.parseInt(textFieldSBuy2.getText()));
		trades.add(Integer.parseInt(textFieldSBuy3.getText()));
		trades.add(Integer.parseInt(textFieldSBuy4.getText()));
		trades.add(Integer.parseInt(textFieldSBuy5.getText()));
		
		trades.add(Integer.parseInt(textFieldSSell1.getText()));
		trades.add(Integer.parseInt(textFieldSSell2.getText()));
		trades.add(Integer.parseInt(textFieldSSell3.getText()));
		trades.add(Integer.parseInt(textFieldSSell4.getText()));
		trades.add(Integer.parseInt(textFieldSSell5.getText()));
		
		
		trades.add(Integer.parseInt(textFieldNBuy1.getText()));
		trades.add(Integer.parseInt(textFieldNBuy2.getText()));
		trades.add(Integer.parseInt(textFieldNBuy3.getText()));
		trades.add(Integer.parseInt(textFieldNBuy4.getText()));
		trades.add(Integer.parseInt(textFieldNBuy5.getText()));
		
		trades.add(Integer.parseInt(textFieldNSell1.getText()));
		trades.add(Integer.parseInt(textFieldNSell2.getText()));
		trades.add(Integer.parseInt(textFieldNSell3.getText()));
		trades.add(Integer.parseInt(textFieldNSell4.getText()));
		trades.add(Integer.parseInt(textFieldNSell5.getText()));
		return trades;
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
		setBounds(100, 100, 1024, 768);
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
		contentPane.setLayout(new MigLayout("", "[398.00,grow][256px:n:256px]", "[][256:n:256,grow][][361.00,grow]"));
		
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
		panelGame.setDoubleBuffered(true);
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
		panelResearch.setLayout(new MigLayout("", "[grow][8px:n:8px][grow][grow][8px:n:8px][grow][grow][grow][8px:n:8px][grow][grow][grow]", "[][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		setUpResearchScreen();
		
		lblResearch = new JLabel("Research");
		lblResearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelResearch.add(lblResearch, "cell 0 0 4 1");
		
		
		panelResearchWeaving = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "clothes.png");
		panelResearchWeaving.setBackground(researchLocked);
		panelResearch.add(panelResearchWeaving, "cell 0 1,grow");
		/*
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panelResearch.add(panel, "cell 1 1 1 11,grow");
		
		panelResearchCattleBreeding = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cows.png");
		panelResearchCattleBreeding.setBackground(researchLocked);
		panelResearch.add(panelResearchCattleBreeding, "cell 2 1,grow");
		
		panelResearchBeeBreeding = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "honey.png");
		panelResearchBeeBreeding.setBackground(researchLocked);
		panelResearch.add(panelResearchBeeBreeding, "cell 3 1,grow");
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panelResearch.add(panel_1, "cell 4 1 1 11,grow");
		
		panelResearchEnhancedAgricultlure = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "fruits.png");
		panelResearchEnhancedAgricultlure.setBackground(researchLocked);
		panelResearch.add(panelResearchEnhancedAgricultlure, "cell 5 1,grow");
		
		panelResearchExoticAgriculture = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "tobacco.png");
		panelResearchExoticAgriculture.setBackground(researchLocked);
		panelResearch.add(panelResearchExoticAgriculture, "cell 6 1,grow");
		
		panelResearchChocolate = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cocoabeans.png");
		panelResearchChocolate.setBackground(researchLocked);
		panelResearch.add(panelResearchChocolate, "cell 7 1,grow");
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panelResearch.add(panel_2, "cell 8 1 1 11,grow");
		
		panelResearchEnhancedBaking = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cake.png");
		panelResearchEnhancedBaking.setBackground(researchLocked);
		panelResearch.add(panelResearchEnhancedBaking, "cell 9 1,grow");
		
		panelResearchConfectionery = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "chocolates.png");
		panelResearchConfectionery.setBackground(researchLocked);
		panelResearch.add(panelResearchConfectionery, "cell 10 1,grow");
		
		panelResearchTannery = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "leather.png");
		panelResearchTannery.setBackground(researchLocked);
		panelResearch.add(panelResearchTannery, "cell 3 2,grow");
		
		panelResearchBaking = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "bread.png");
		panelResearchBaking.setBackground(researchLocked);
		panelResearch.add(panelResearchBaking, "cell 6 2,grow");
		
		panelResearchDairy = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cheese.png");
		panelResearchDairy.setBackground(researchLocked);
		panelResearch.add(panelResearchDairy, "cell 7 2,grow");
		
		panelResearchFermentation = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "beer.png");
		panelResearchFermentation.setBackground(researchLocked);
		panelResearch.add(panelResearchFermentation, "cell 6 3,grow");
		
		panelResearchEnhancedFermentation = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "barrels.png");
		panelResearchEnhancedFermentation.setBackground(researchLocked);
		panelResearch.add(panelResearchEnhancedFermentation, "cell 9 3,grow");
		
		panelResearchWine = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "icewine.png");
		panelResearchWine.setBackground(researchLocked);
		panelResearch.add(panelResearchWine, "cell 10 3,grow");
		
		panelResearchLiquor = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "liquor.png");
		panelResearchLiquor.setBackground(researchLocked);
		panelResearch.add(panelResearchLiquor, "cell 11 3,grow");
		
		panelResearchButchery = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "meat.png");
		panelResearchButchery.setBackground(researchLocked);
		panelResearch.add(panelResearchButchery, "cell 3 4,grow");
		
		panelResearchEnhancedCattleBreeding = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "horses.png");
		panelResearchEnhancedCattleBreeding.setBackground(researchLocked);
		panelResearch.add(panelResearchEnhancedCattleBreeding, "cell 5 4,grow");
		
		panelResearchHunting = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "deer.png");
		panelResearchHunting.setBackground(researchLocked);
		panelResearch.add(panelResearchHunting, "cell 9 4,grow");
		
		panelResearchCoalBurning = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "coal.png");
		panelResearchCoalBurning.setBackground(researchLocked);
		panelResearch.add(panelResearchCoalBurning, "cell 0 5,grow");
		
		panelResearchMining = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "ironore.png");
		panelResearchMining.setBackground(researchLocked);
		panelResearch.add(panelResearchMining, "cell 5 5,grow");
		
		panelResearchIronProcessing = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.png");
		panelResearchIronProcessing.setBackground(researchLocked);
		panelResearch.add(panelResearchIronProcessing, "cell 6 5,grow");
		
		panelResearchDeeperMines = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "goldore.png");
		panelResearchDeeperMines.setBackground(researchLocked);
		panelResearch.add(panelResearchDeeperMines, "cell 9 5,grow");
		
		panelResearchGoldProcessing = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.png");
		panelResearchGoldProcessing.setBackground(researchLocked);
		panelResearch.add(panelResearchGoldProcessing, "cell 10 5,grow");
		
		panelResearchAdvancedSmithing = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "jewelry.png");
		panelResearchAdvancedSmithing.setBackground(researchLocked);
		panelResearch.add(panelResearchAdvancedSmithing, "cell 11 5,grow");
		
		panelResearchRevegetation = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.png");
		panelResearchRevegetation.setBackground(researchLocked);
		panelResearch.add(panelResearchRevegetation, "cell 3 6,grow");
		
		panelResearchSandProcessing = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "sand.png");
		panelResearchSandProcessing.setBackground(researchLocked);
		panelResearch.add(panelResearchSandProcessing, "cell 6 6,grow");
		
		panelResearchBricklaying = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.png");
		panelResearchBricklaying.setBackground(researchLocked);
		panelResearch.add(panelResearchBricklaying, "cell 7 6,grow");
		
		panelResearchFlintlockGuns = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "pistols.png");
		panelResearchFlintlockGuns.setBackground(researchLocked);
		panelResearch.add(panelResearchFlintlockGuns, "cell 9 6,grow");
		
		panelResearchNatureStudies = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "marshweed.png");
		panelResearchNatureStudies.setBackground(researchLocked);
		panelResearch.add(panelResearchNatureStudies, "cell 2 7,grow");
		
		panelResearchFlorist = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "ornaments.png");
		panelResearchFlorist.setBackground(researchLocked);
		panelResearch.add(panelResearchFlorist, "cell 3 7,grow");
		
		panelResearchPaper = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "paper.png");
		panelResearchPaper.setBackground(researchLocked);
		panelResearch.add(panelResearchPaper, "cell 9 7,grow");
		
		panelResearchPrinting = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "books.png");
		panelResearchPrinting.setBackground(researchLocked);
		panelResearch.add(panelResearchPrinting, "cell 10 7,grow");
		
		panelResearchAnatomy = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "medicine.png");
		panelResearchAnatomy.setBackground(researchLocked);
		panelResearch.add(panelResearchAnatomy, "cell 3 8,grow");
		
		panelResearchGlassProduction = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.png");
		panelResearchGlassProduction.setBackground(researchLocked);
		panelResearch.add(panelResearchGlassProduction, "cell 9 8,grow");
		
		panelResearchHealth1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "health.png");
		panelResearchHealth1.setBackground(researchLocked);
		panelResearch.add(panelResearchHealth1, "cell 5 9,grow");
		
		panelResearchHealth2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "health.png");
		panelResearchHealth2.setBackground(researchLocked);
		panelResearch.add(panelResearchHealth2, "cell 11 9,grow");
		
		panelResearchSafety = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "health.png");
		panelResearchSafety.setBackground(researchLocked);
		panelResearch.add(panelResearchSafety, "cell 11 10,grow");
		
		panelResearchTrade1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "trade.png");
		panelResearchTrade1.setBackground(researchLocked);
		panelResearch.add(panelResearchTrade1, "cell 0 11,grow");

		panelResearchTrade2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "trade.png");
		panelResearchTrade2.setBackground(researchLocked);
		panelResearch.add(panelResearchTrade2, "cell 2 11,grow");
		
		panelResearchTrade3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "trade.png");
		panelResearchTrade3.setBackground(researchLocked);
		panelResearch.add(panelResearchTrade3, "cell 5 11,grow");
		
		panelResearchTrade4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "trade.png");
		panelResearchTrade4.setBackground(researchLocked);
		panelResearch.add(panelResearchTrade4, "cell 6 11,grow");

		panelResearchTradeExotic = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "tradeGold.png");
		panelResearchTradeExotic.setBackground(researchLocked);
		panelResearch.add(panelResearchTradeExotic, "cell 9 11,grow");
		
		panelResearchTrade5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "trade.png");
		panelResearchTrade5.setBackground(researchLocked);
		panelResearch.add(panelResearchTrade5, "cell 11 11,grow");
		*/
		
		
		panelStorage = new JPanel();
		panelStorage.setBackground(basicBackground);
		panelMain.add(panelStorage, cardStorage);
		panelStorage.setLayout(new MigLayout("", "[grow,fill]", "[grow]"));
		
		scrollPaneStorage = new JScrollPane();
		scrollPaneStorage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelStorage.add(scrollPaneStorage, "cell 0 0,grow");
		
		panelStorageItems = new JPanel();
		panelStorageItems.setBackground(basicBackground);
		scrollPaneStorage.setViewportView(panelStorageItems);
		panelStorageItems.setLayout(new MigLayout("", "[32px:n:32px,grow][40px:n:40px,right][grow][32px:n:32px,grow][40px:n:40px,right][grow][32px:n:32px,grow][40px:n:40px,right][grow][32px:n:32px,grow][40px:n:40px,right][grow][32px:n:32px,grow][40px:n:40px,right]", "[][][][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px,grow]"));
		
		lblStorageStone = new JLabel("Storage");
		panelStorageItems.add(lblStorageStone, "cell 0 0");
		lblStorageStone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnBackStorage = new JButton("Back");
		btnBackStorage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainCard.show(panelMain, cardGame);
			}
		});
		panelStorageItems.add(btnBackStorage, "cell 12 1 2 1,growx");
		
		lblBasic = new JLabel("Basic");
		lblBasic.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStorageItems.add(lblBasic, "cell 0 2 2 1");
		
		lblTechI = new JLabel("Tech I");
		lblTechI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStorageItems.add(lblTechI, "cell 3 2 2 1");
		
		lblTechIi = new JLabel("Tech II");
		lblTechIi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStorageItems.add(lblTechIi, "cell 6 2 2 1");
		
		lblTechIii = new JLabel("Tech III");
		lblTechIii.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStorageItems.add(lblTechIii, "cell 9 2 2 1");
		
		lblTechIv = new JLabel("Tech IV");
		lblTechIv.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStorageItems.add(lblTechIv, "cell 12 2 2 1");
		
		panelStorageWood = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.png");
		panelStorageWood.setBackground(darkBackground);
		panelStorageItems.add(panelStorageWood, "cell 0 3,grow");
		
		lblStorageWood = new JLabel("0");
		panelStorageItems.add(lblStorageWood, "cell 1 3");
		
		panelStorageSpacer1 = new JPanel();
		panelStorageSpacer1.setBackground(basicBackground);
		panelStorageItems.add(panelStorageSpacer1, "cell 2 3 1 5,grow");
		
		panelStorageWater = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "water.png");
		panelStorageWater.setBackground(darkBackground);
		panelStorageItems.add(panelStorageWater, "cell 3 3,grow");
		
		lblStorageWater = new JLabel("0");
		panelStorageItems.add(lblStorageWater, "cell 4 3");
		
		panelStorageSpacer2 = new JPanel();
		panelStorageSpacer2.setBackground(new Color(255, 219, 153));
		panelStorageItems.add(panelStorageSpacer2, "cell 5 3 1 5,grow");
		
		panelStorageMilk = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"milk.png");
		panelStorageMilk.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageMilk, "cell 6 3,grow");
		
		lblStorageMilk = new JLabel("0");
		panelStorageItems.add(lblStorageMilk, "cell 7 3");
		
		panelStorageSpacer3 = new JPanel();
		panelStorageSpacer3.setBackground(new Color(255, 219, 153));
		panelStorageItems.add(panelStorageSpacer3, "cell 8 3 1 8,grow");
		
		panelStorageBeer = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"beer.png");
		panelStorageBeer.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageBeer, "cell 9 3,grow");
		
		lblStorageBeer = new JLabel("0");
		panelStorageItems.add(lblStorageBeer, "cell 10 3");
		
		panelStorageSpacer4 = new JPanel();
		panelStorageSpacer4.setBackground(new Color(255, 219, 153));
		panelStorageItems.add(panelStorageSpacer4, "cell 11 3 1 14,grow");
		
		panelStorageIcewine = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"icewine.png");
		panelStorageIcewine.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageIcewine, "cell 12 3,grow");
		
		lblStorageIcewine = new JLabel("0");
		panelStorageItems.add(lblStorageIcewine, "cell 13 3");
		
		panelStorageStone = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.png");
		panelStorageStone.setBackground(darkBackground);
		panelStorageItems.add(panelStorageStone, "cell 0 4,grow");
		
		lblStorageStone = new JLabel("0");
		panelStorageItems.add(lblStorageStone, "cell 1 4");
		
		panelStorageVegetables = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "vegetables.png");
		panelStorageVegetables.setBackground(darkBackground);
		panelStorageItems.add(panelStorageVegetables, "cell 3 4,grow");
		
		lblStorageVegetables = new JLabel("0");
		panelStorageItems.add(lblStorageVegetables, "cell 4 4");
		
		panelStorageMeat = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"meat.png");
		panelStorageMeat.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageMeat, "cell 6 4,grow");
		
		lblStorageMeat = new JLabel("0");
		panelStorageItems.add(lblStorageMeat, "cell 7 4");
		
		panelStorageFruits = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"fruits.png");
		panelStorageFruits.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageFruits, "cell 9 4,grow");
		
		lblStorageFruits = new JLabel("0");
		panelStorageItems.add(lblStorageFruits, "cell 10 4");
		
		panelStorageCake = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"cake.png");
		panelStorageCake.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCake, "cell 12 4,grow");
		
		lblStorageCake = new JLabel("0");
		panelStorageItems.add(lblStorageCake, "cell 13 4");
		
		panelStorageSteel = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "steel.png");
		panelStorageSteel.setBackground(darkBackground);
		panelStorageItems.add(panelStorageSteel, "cell 0 5,grow");
		
		lblStorageSteel = new JLabel("0");
		panelStorageItems.add(lblStorageSteel, "cell 1 5");
		
		panelStorageClothes = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"clothes.png");
		panelStorageClothes.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageClothes, "cell 3 5,grow");
		
		lblStorageClothes = new JLabel("0");
		panelStorageItems.add(lblStorageClothes, "cell 4 5");
		
		panelStorageMedicine = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"medicine.png");
		panelStorageMedicine.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageMedicine, "cell 6 5,grow");
		
		lblStorageMedicine = new JLabel("0");
		panelStorageItems.add(lblStorageMedicine, "cell 7 5");
		
		panelStorageHorses = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"horses.png");
		panelStorageHorses.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageHorses, "cell 9 5,grow");
		
		lblStorageHorses = new JLabel("0");
		panelStorageItems.add(lblStorageHorses, "cell 10 5");
		
		panelStorageDeer = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"deer.png");
		panelStorageDeer.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageDeer, "cell 12 5,grow");
		
		lblStorageDeer = new JLabel("0");
		panelStorageItems.add(lblStorageDeer, "cell 13 5");
		
		panelStorageGlass = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "glass.png");
		panelStorageGlass.setBackground(darkBackground);
		panelStorageItems.add(panelStorageGlass, "cell 0 6,grow");
		
		lblStorageGlass = new JLabel("0");
		panelStorageItems.add(lblStorageGlass, "cell 1 6");
		
		panelStorageCoal = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"coal.png");
		panelStorageCoal.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCoal, "cell 3 6,grow");
		
		lblStorageCoal = new JLabel("0");
		panelStorageItems.add(lblStorageCoal, "cell 4 6");
		
		panelStorageShoes = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"shoes.png");
		panelStorageShoes.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageShoes, "cell 6 6,grow");
		
		lblStorageShoes = new JLabel("0");
		panelStorageItems.add(lblStorageShoes, "cell 7 6");
		
		panelStorageBread = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"bread.png");
		panelStorageBread.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageBread, "cell 9 6,grow");
		
		lblStorageBread = new JLabel("0");
		panelStorageItems.add(lblStorageBread, "cell 10 6");
		
		panelStorageChocolates = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"chocolates.png");
		panelStorageChocolates.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageChocolates, "cell 12 6,grow");
		
		lblStorageChocolates = new JLabel("0");
		panelStorageItems.add(lblStorageChocolates, "cell 13 6");
		
		panelStorageGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.png");
		panelStorageGold.setBackground(darkBackground);
		panelStorageItems.add(panelStorageGold, "cell 0 7,grow");
		
		lblStorageGold = new JLabel("0");
		panelStorageItems.add(lblStorageGold, "cell 1 7");
		
		panelStorageWool = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"wool.png");
		panelStorageWool.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageWool, "cell 3 7,grow");
		
		lblStorageWool = new JLabel("0");
		panelStorageItems.add(lblStorageWool, "cell 4 7");
		
		panelStorageHoney = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"honey.png");
		panelStorageHoney.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageHoney, "cell 6 7,grow");
		
		lblStorageHoney = new JLabel("0");
		panelStorageItems.add(lblStorageHoney, "cell 7 7");
		
		panelStorageTobacco = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"tobacco.png");
		panelStorageTobacco.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageTobacco, "cell 9 7,grow");
		
		lblStorageTobacco = new JLabel("0");
		panelStorageItems.add(lblStorageTobacco, "cell 10 7");
		
		panelStoragePistols = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"pistols.png");
		panelStoragePistols.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStoragePistols, "cell 12 7,grow");
		
		lblStoragePistols = new JLabel("0");
		panelStorageItems.add(lblStoragePistols, "cell 13 7");
		
		panelStorageOrnaments = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"ornaments.png");
		panelStorageOrnaments.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageOrnaments, "cell 6 8,grow");
		
		lblStorageOrnaments = new JLabel("0");
		panelStorageItems.add(lblStorageOrnaments, "cell 7 8");
		
		panelStoragePerfume = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"perfume.png");
		panelStoragePerfume.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStoragePerfume, "cell 9 8,grow");
		
		lblStoragePerfume = new JLabel("0");
		panelStorageItems.add(lblStoragePerfume, "cell 10 8");
		
		panelStorageLiquor = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"liquor.png");
		panelStorageLiquor.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageLiquor, "cell 12 8,grow");
		
		lblStorageLiquor = new JLabel("0");
		panelStorageItems.add(lblStorageLiquor, "cell 13 8");
		
		panelStorageCows = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"cows.png");
		panelStorageCows.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCows, "cell 6 9,grow");
		
		lblStorageStore = new JLabel("0");
		panelStorageItems.add(lblStorageStore, "cell 7 9");
		
		panelStorageCheese = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"cheese.png");
		panelStorageCheese.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCheese, "cell 9 9,grow");
		
		lblStorageCheese = new JLabel("0");
		panelStorageItems.add(lblStorageCheese, "cell 10 9");
		
		panelStorageBooks = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"books.png");
		panelStorageBooks.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageBooks, "cell 12 9,grow");
		
		lblStorageBooks = new JLabel("0");
		panelStorageItems.add(lblStorageBooks, "cell 13 9");
		
		panelStorageLeather = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"leather.png");
		panelStorageLeather.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageLeather, "cell 6 10,grow");
		
		lblStorageLeather = new JLabel("0");
		panelStorageItems.add(lblStorageLeather, "cell 7 10");
		
		panelStorageCocoa = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"cocoa.png");
		panelStorageCocoa.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCocoa, "cell 9 10,grow");
		
		lblStorageCocoa = new JLabel("0");
		panelStorageItems.add(lblStorageCocoa, "cell 10 10");
		
		panelStorageJewelry = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"jewelry.png");
		panelStorageJewelry.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageJewelry, "cell 12 10,grow");
		
		lblStorageJewelry = new JLabel("0");
		panelStorageItems.add(lblStorageJewelry, "cell 13 10");
		
		panelStorageMarshweed = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"marshweed.png");
		panelStorageMarshweed.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageMarshweed, "cell 6 11,grow");
		
		lblStorageMarshweed = new JLabel("0");
		panelStorageItems.add(lblStorageMarshweed, "cell 7 11");
		
		panelStorageSand = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"sand.png");
		panelStorageSand.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageSand, "cell 9 11,grow");
		
		lblStorageSand = new JLabel("0");
		panelStorageItems.add(lblStorageSand, "cell 10 11");
		
		panelStorageSalmon = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"salmon.png");
		panelStorageSalmon.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageSalmon, "cell 12 11,grow");
		
		lblStorageSalmon = new JLabel("0");
		panelStorageItems.add(lblStorageSalmon, "cell 13 11");
		
		panelStorageGrain = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"grain.png");
		panelStorageGrain.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageGrain, "cell 9 12,grow");
		
		lblStorageGrain = new JLabel("0");
		panelStorageItems.add(lblStorageGrain, "cell 10 12");
		
		panelStorageCoffee = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"coffee.png");
		panelStorageCoffee.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCoffee, "cell 12 12,grow");
		
		lblStorageCoffee = new JLabel("0");
		panelStorageItems.add(lblStorageCoffee, "cell 13 12");
		
		panelStorageHops = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"hops.png");
		panelStorageHops.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageHops, "cell 9 13,grow");
		
		lblStorageHops = new JLabel("0");
		panelStorageItems.add(lblStorageHops, "cell 10 13");
		
		panelStorageIceflowers = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"iceflowers.png");
		panelStorageIceflowers.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageIceflowers, "cell 12 13,grow");
		
		lblStorageIceflowers = new JLabel("0");
		panelStorageItems.add(lblStorageIceflowers, "cell 13 13");
		
		panelStorageCocoabeans = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"cocoabeans.png");
		panelStorageCocoabeans.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageCocoabeans, "cell 9 14,grow");
		
		lblStorageCocoabeans = new JLabel("0");
		panelStorageItems.add(lblStorageCocoabeans, "cell 10 14");
		
		panelStorageBarrels = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"barrels.png");
		panelStorageBarrels.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageBarrels, "cell 12 14,grow");
		
		lblStorageBarrels = new JLabel("0");
		panelStorageItems.add(lblStorageBarrels, "cell 13 14");
		
		panelStorageChocolate = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"chocolate.png");
		panelStorageChocolate.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageChocolate, "cell 9 15,grow");
		
		lblStorageRawChocolate = new JLabel("0");
		panelStorageItems.add(lblStorageRawChocolate, "cell 10 15");
		
		panelStoragePaper = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"paper.png");
		panelStoragePaper.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStoragePaper, "cell 12 15,grow");
		
		lblStoragePaper = new JLabel("0");
		panelStorageItems.add(lblStoragePaper, "cell 13 15");
		
		panelStorageIronOre = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"ironore.png");
		panelStorageIronOre.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageIronOre, "cell 9 16,grow");
		
		lblStorageIronOre = new JLabel("0");
		panelStorageItems.add(lblStorageIronOre, "cell 10 16");
		
		panelStorageGoldOre = new JImgPanel("resources"+ File.separator +"images"+ File.separator +"items"+ File.separator +"goldore.png");
		panelStorageGoldOre.setBackground(new Color(229, 194, 137));
		panelStorageItems.add(panelStorageGoldOre, "cell 12 16,grow");
		
		lblStorageGoldOre = new JLabel("0");
		panelStorageItems.add(lblStorageGoldOre, "cell 13 16");
		
		
		
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
		
		lblStoreGold = new JLabel("0");
		lblStoreGold.setFont(new Font("Tahoma", Font.PLAIN, 24));
		panelResources.add(lblStoreGold, "cell 14 0,alignx center");
		
		btnNextRes = new JButton(">");
		btnNextRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topBarCard.show(panelTopBar, cardTime);
				mainCard.show(panelMain, cardResearch);
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
		
		panelSelectItem = new JGridPanel(3,24,4);
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
		
		scrollPane.setRowHeaderView(panelSelectItem);
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
		
		InputMap input = panelMain.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = panelMain.getActionMap();
		
	
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
		smGame = new StorageMonitor(this);
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
		storageMonitor = new Thread(smGame);
		storageMonitor.start();
		
		setUpBuildCard();
		if (isNewGame)
			setUpStorage();
		setUpLiving();
		setUpTrade();
		happiness = 70;
		techLevel = 1;
		
		caller.dispose();
		
		clockwork = new Thread(cwGame);
		clockwork.start();
	}
	
	public void callMap(String name, boolean isNewGame)
	{

		gioGame = new GridIO(panelGame, allTiles, allItems, this);
		gioGame.createItemList();
		allTiles = gioGame.createTileList();
		allItems = gioGame.getAllItems();
		constructableItems = gioGame.getConstructableItems();
		productCosts1 = gioGame.getProductionCosts1();
		productCosts2 = gioGame.getProductionCosts2();
		productAmounts1 = gioGame.getProductionAmounts1();
		productAmounts2 = gioGame.getProductionAmounts2();
		
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
	public ArrayList<Integer> readTradeConfig()
	{
		BufferedReader readConfig = null;
		ArrayList<Integer> allLines = new ArrayList<Integer>();
		String line;
		
		try
		{
			readConfig = new BufferedReader(new FileReader(new File("src" + File.separator + "config" + File.separator + "trade.config")));
			while ((line=readConfig.readLine()) != null)
			{
				for(String each:line.split("/"))
				{
					allLines.add(Integer.parseInt(each));
				}
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
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 11, each.getProducingResource1());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 12, each.getProducingResource2());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 13, each.getYieldProduct1());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 14, each.getYieldProduct2());
					panelGame.applyProperty(panelGame.getCurrentX(), panelGame.getCurrentY(), 15, each.getProducingTime());
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
		storeSand = storage[4];
		storeWool = storage[5];
		storeLeather = storage[6];
		storeMarshweed = storage[7];
		storeGrain = storage[8];
		storeHops = storage[9];
		storeCocoabeans = storage[10];
		storeChocolate = storage[11];
		storeIceflowers = storage[12];
		storeBarrels = storage[13];
		storePaper = storage[14];
		storeGold = storage[15];
		storeIronOre = storage[16];
		storeGoldOre = storage[17];
		
		storeWater = storage[18];
		storeVegetables = storage[19];
		storeClothes = storage[20];
		storeCoal = storage[21];
		
		storeMilk = storage[22];
		storeMeat = storage[23];
		storeMedicine = storage[24];
		storeShoes = storage[25];
		storeHoney = storage[26];
		storeOrnaments = storage[27];
		
		storeBeer = storage[28];
		storeFruits = storage[29];
		storeHorses = storage[30];
		storeBread = storage[31];
		storeTobacco = storage[32];
		storePerfume = storage[33];
		storeCheese = storage[34];
		storeCocoa = storage[35];
		
		storeIcewine = storage[36];
		storeCake = storage[37];
		storeDeer = storage[38];
		storeChocolates = storage[39];
		storePistols = storage[40];
		storeLiquor = storage[41];
		storeBooks = storage[42];
		storeJewelry = storage[43];
		storeSalmon = storage[44];
		storeCoffee = storage[45];
		
		displayStorage();
	}
	
	public void setUpStorage()
	{
		storeWood = 30;
		storeStone = 10;
		storeSteel = 10;
		storeGlass = 10;
		storeSand = 0;
		storeWool = 0;
		storeLeather = 0;
		storeMarshweed = 0;
		storeGrain = 0;
		storeHops = 0;
		storeCocoabeans = 0;
		storeChocolate = 0;
		storeIceflowers = 0;
		storeBarrels = 0;
		storePaper = 0;
		storeGold = 10;
		storeIronOre = 0;
		storeGoldOre = 0;
		
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
		lblStoreGold.setText(String.valueOf(storeGold));
		
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
	
	public void setUpTrade()
	{
		for (int i = 0; i < 20; i++)
		{
			amountBuy.add(0);
			amountSell.add(0);
		}
		tradeValues = readTradeConfig();
		westIncome = 0;
		westPayment = 0;
		eastIncome = 0;
		eastPayment = 0;
		southIncome = 0;
		southPayment = 0;
		northIncome = 0;
		northPayment = 0;
		
		panelTrade = new JPanel();
		panelTrade.setBackground(basicBackground);
		panelMain.add(panelTrade, cardTrade);
		panelTrade.setLayout(new MigLayout("", "[32px:n:32px][32px:n:32px,grow][32px:n:32px][32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px][32px:n:32px,grow][32px:n:32px,grow][32px:n:32px][64px:n:64px][32px:n:32px]", "[][][][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][32px:n:32px][][32px:n:32px]"));
		
		lblTrading = new JLabel("Trading");
		lblTrading.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelTrade.add(lblTrading, "cell 0 0");
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateTrades();
				
				amountBuy.clear();
				amountBuy.add(Integer.valueOf(textFieldWBuy1.getText()));
				amountBuy.add(Integer.valueOf(textFieldWBuy2.getText()));
				amountBuy.add(Integer.valueOf(textFieldWBuy3.getText()));
				amountBuy.add(Integer.valueOf(textFieldWBuy4.getText()));
				amountBuy.add(Integer.valueOf(textFieldWBuy5.getText()));
				
				amountBuy.add(Integer.valueOf(textFieldEBuy1.getText()));
				amountBuy.add(Integer.valueOf(textFieldEBuy2.getText()));
				amountBuy.add(Integer.valueOf(textFieldEBuy3.getText()));
				amountBuy.add(Integer.valueOf(textFieldEBuy4.getText()));
				amountBuy.add(Integer.valueOf(textFieldEBuy5.getText()));
				
				amountBuy.add(Integer.valueOf(textFieldSBuy1.getText()));
				amountBuy.add(Integer.valueOf(textFieldSBuy2.getText()));
				amountBuy.add(Integer.valueOf(textFieldSBuy3.getText()));
				amountBuy.add(Integer.valueOf(textFieldSBuy4.getText()));
				amountBuy.add(Integer.valueOf(textFieldSBuy5.getText()));
				
				amountBuy.add(Integer.valueOf(textFieldNBuy1.getText()));
				amountBuy.add(Integer.valueOf(textFieldNBuy2.getText()));
				amountBuy.add(Integer.valueOf(textFieldNBuy3.getText()));
				amountBuy.add(Integer.valueOf(textFieldNBuy4.getText()));
				amountBuy.add(Integer.valueOf(textFieldNBuy5.getText()));
				
				amountSell.clear();
				amountSell.add(Integer.valueOf(textFieldWSell1.getText()));
				amountSell.add(Integer.valueOf(textFieldWSell2.getText()));
				amountSell.add(Integer.valueOf(textFieldWSell3.getText()));
				amountSell.add(Integer.valueOf(textFieldWSell4.getText()));
				amountSell.add(Integer.valueOf(textFieldWSell5.getText()));
				
				amountSell.add(Integer.valueOf(textFieldESell1.getText()));
				amountSell.add(Integer.valueOf(textFieldESell2.getText()));
				amountSell.add(Integer.valueOf(textFieldESell3.getText()));
				amountSell.add(Integer.valueOf(textFieldESell4.getText()));
				amountSell.add(Integer.valueOf(textFieldESell5.getText()));
				
				amountSell.add(Integer.valueOf(textFieldSSell1.getText()));
				amountSell.add(Integer.valueOf(textFieldSSell2.getText()));
				amountSell.add(Integer.valueOf(textFieldSSell3.getText()));
				amountSell.add(Integer.valueOf(textFieldSSell4.getText()));
				amountSell.add(Integer.valueOf(textFieldSSell5.getText()));
				
				amountSell.add(Integer.valueOf(textFieldNSell1.getText()));
				amountSell.add(Integer.valueOf(textFieldNSell2.getText()));
				amountSell.add(Integer.valueOf(textFieldNSell3.getText()));
				amountSell.add(Integer.valueOf(textFieldNSell4.getText()));
				amountSell.add(Integer.valueOf(textFieldNSell5.getText()));
				
				
			}
		});
		panelTrade.add(btnConfirm, "cell 0 1 4 1,growx");
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateTrades();
			}
		});
		panelTrade.add(btnCalculate, "cell 4 1 4 1,growx");
		
		btnStopPurchases = new JButton("Stop purchases");
		btnStopPurchases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amountSell.clear();
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				amountSell.add(0);
				textFieldWSell1.setText("0");
				textFieldWSell2.setText("0");
				textFieldWSell3.setText("0");
				textFieldWSell4.setText("0");
				textFieldWSell5.setText("0");
				textFieldESell1.setText("0");
				textFieldESell2.setText("0");
				textFieldESell3.setText("0");
				textFieldESell4.setText("0");
				textFieldESell5.setText("0");
				textFieldSSell1.setText("0");
				textFieldSSell2.setText("0");
				textFieldSSell3.setText("0");
				textFieldSSell4.setText("0");
				textFieldSSell5.setText("0");
				textFieldNSell1.setText("0");
				textFieldNSell2.setText("0");
				textFieldNSell3.setText("0");
				textFieldNSell4.setText("0");
				textFieldNSell5.setText("0");
				westPayment = 0;
				eastPayment = 0;
				southPayment = 0;
				northPayment = 0;
				calculateTrades();
				
			}
		});
		panelTrade.add(btnStopPurchases, "cell 8 1 4 1,growx");
		
		btnStopSales = new JButton("Stop sales");
		btnStopSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amountBuy.clear();
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				amountBuy.add(0);
				textFieldWBuy1.setText("0");
				textFieldWBuy2.setText("0");
				textFieldWBuy3.setText("0");
				textFieldWBuy4.setText("0");
				textFieldWBuy5.setText("0");
				textFieldEBuy1.setText("0");
				textFieldEBuy2.setText("0");
				textFieldEBuy3.setText("0");
				textFieldEBuy4.setText("0");
				textFieldEBuy5.setText("0");
				textFieldSBuy1.setText("0");
				textFieldSBuy2.setText("0");
				textFieldSBuy3.setText("0");
				textFieldSBuy4.setText("0");
				textFieldSBuy5.setText("0");
				textFieldNBuy1.setText("0");
				textFieldNBuy2.setText("0");
				textFieldNBuy3.setText("0");
				textFieldNBuy4.setText("0");
				textFieldNBuy5.setText("0");
				westIncome = 0;
				eastIncome = 0;
				southIncome = 0;
				northIncome = 0;
				calculateTrades();
			}
		});
		panelTrade.add(btnStopSales, "cell 12 1 4 1,growx");
		
		btnBackTrading = new JButton("Back");
		btnBackTrading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainCard.show(panelMain, cardGame);
				textFieldWSell1.setText(String.valueOf(amountSell.get(0)));
				textFieldWSell2.setText(String.valueOf(amountSell.get(1)));
				textFieldWSell3.setText(String.valueOf(amountSell.get(2)));
				textFieldWSell4.setText(String.valueOf(amountSell.get(3)));
				textFieldWSell5.setText(String.valueOf(amountSell.get(4)));
				textFieldESell1.setText(String.valueOf(amountSell.get(5)));
				textFieldESell2.setText(String.valueOf(amountSell.get(6)));
				textFieldESell3.setText(String.valueOf(amountSell.get(7)));
				textFieldESell4.setText(String.valueOf(amountSell.get(8)));
				textFieldESell5.setText(String.valueOf(amountSell.get(9)));
				textFieldSSell1.setText(String.valueOf(amountSell.get(10)));
				textFieldSSell2.setText(String.valueOf(amountSell.get(11)));
				textFieldSSell3.setText(String.valueOf(amountSell.get(12)));
				textFieldSSell4.setText(String.valueOf(amountSell.get(13)));
				textFieldSSell5.setText(String.valueOf(amountSell.get(14)));
				textFieldNSell1.setText(String.valueOf(amountSell.get(15)));
				textFieldNSell2.setText(String.valueOf(amountSell.get(16)));
				textFieldNSell3.setText(String.valueOf(amountSell.get(17)));
				textFieldNSell4.setText(String.valueOf(amountSell.get(18)));
				textFieldNSell5.setText(String.valueOf(amountSell.get(19)));
				
				textFieldWBuy1.setText(String.valueOf(amountBuy.get(0)));
				textFieldWBuy2.setText(String.valueOf(amountBuy.get(1)));
				textFieldWBuy3.setText(String.valueOf(amountBuy.get(2)));
				textFieldWBuy4.setText(String.valueOf(amountBuy.get(3)));
				textFieldWBuy5.setText(String.valueOf(amountBuy.get(4)));
				textFieldEBuy1.setText(String.valueOf(amountBuy.get(5)));
				textFieldEBuy2.setText(String.valueOf(amountBuy.get(6)));
				textFieldEBuy3.setText(String.valueOf(amountBuy.get(7)));
				textFieldEBuy4.setText(String.valueOf(amountBuy.get(8)));
				textFieldEBuy5.setText(String.valueOf(amountBuy.get(9)));
				textFieldSBuy1.setText(String.valueOf(amountBuy.get(10)));
				textFieldSBuy2.setText(String.valueOf(amountBuy.get(11)));
				textFieldSBuy3.setText(String.valueOf(amountBuy.get(12)));
				textFieldSBuy4.setText(String.valueOf(amountBuy.get(13)));
				textFieldSBuy5.setText(String.valueOf(amountBuy.get(14)));
				textFieldNBuy1.setText(String.valueOf(amountBuy.get(15)));
				textFieldNBuy2.setText(String.valueOf(amountBuy.get(16)));
				textFieldNBuy3.setText(String.valueOf(amountBuy.get(17)));
				textFieldNBuy4.setText(String.valueOf(amountBuy.get(18)));
				textFieldNBuy5.setText(String.valueOf(amountBuy.get(19)));
			}
		});
		panelTrade.add(btnBackTrading, "cell 16 1,growx");
		
		lblNation = new JLabel("Nation");
		lblNation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblNation, "cell 0 2");
		
		lblIncome = new JLabel("Income");
		lblIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncome, "cell 16 2,alignx right");
		
		panelImgWest = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagWest.png");
		panelImgWest.setToolTipText("ARBORIA (WEST)");
		panelImgWest.setBackground(darkBackground);
		panelTrade.add(panelImgWest, "cell 0 3,grow");
		
		lblNationW = new JLabel("ARBORIA");
		panelTrade.add(lblNationW, "cell 1 3 2 1");
		
		lblBuyW = new JLabel("Buys");
		lblBuyW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblBuyW, "flowx,cell 0 4");
		
		panelImgWestBuy1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "shoes.png");
		panelImgWestBuy1.setToolTipText("SHOES");
		panelImgWestBuy1.setBackground(darkBackground);
		panelTrade.add(panelImgWestBuy1, "cell 1 4,grow");
		
		textFieldWBuy1 = new JTextField();
		textFieldWBuy1.setText("0");
		panelTrade.add(textFieldWBuy1, "cell 2 4,growx");
		textFieldWBuy1.setColumns(10);
		
		panelImgWestBuy2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "medicine.png");
		panelImgWestBuy2.setToolTipText("MEDICINE");
		panelImgWestBuy2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestBuy2, "cell 4 4,grow");
		
		textFieldWBuy2 = new JTextField();
		textFieldWBuy2.setText("0");
		textFieldWBuy2.setColumns(10);
		panelTrade.add(textFieldWBuy2, "cell 5 4,growx");
		
		panelImgWestBuy3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "fruits.png");
		panelImgWestBuy3.setToolTipText("FRUITS");
		panelImgWestBuy3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestBuy3, "cell 7 4,grow");
		
		textFieldWBuy3 = new JTextField();
		textFieldWBuy3.setText("0");
		textFieldWBuy3.setColumns(10);
		panelTrade.add(textFieldWBuy3, "cell 8 4,growx");
		
		panelImgWestBuy4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "books.png");
		panelImgWestBuy4.setToolTipText("BOOKS");
		panelImgWestBuy4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestBuy4, "cell 10 4,grow");
		
		textFieldWBuy4 = new JTextField();
		textFieldWBuy4.setText("0");
		textFieldWBuy4.setColumns(10);
		panelTrade.add(textFieldWBuy4, "cell 11 4,growx");
		
		panelImgWestBuy5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "iceflowers.png");
		panelImgWestBuy5.setToolTipText("ICEFLOWERS");
		panelImgWestBuy5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestBuy5, "cell 13 4,grow");
		
		textFieldWBuy5 = new JTextField();
		textFieldWBuy5.setText("0");
		textFieldWBuy5.setColumns(10);
		panelTrade.add(textFieldWBuy5, "cell 14 4,growx");
		
		lblIncomeWBuy = new JLabel("+0");
		lblIncomeWBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeWBuy, "cell 16 4,alignx right");
		
		lblSellW = new JLabel("Sells");
		lblSellW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblSellW, "cell 0 5");
		
		panelImgWestSell1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "vegetables.png");
		panelImgWestSell1.setToolTipText("VEGETABLES");
		panelImgWestSell1.setBackground(darkBackground);
		panelTrade.add(panelImgWestSell1, "cell 1 5,grow");
		
		textFieldWSell1 = new JTextField();
		textFieldWSell1.setText("0");
		textFieldWSell1.setColumns(10);
		panelTrade.add(textFieldWSell1, "cell 2 5,growx");
		
		panelImgWestSell2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wood.png");
		panelImgWestSell2.setToolTipText("WOOD");
		panelImgWestSell2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestSell2, "cell 4 5,grow");
		
		textFieldWSell2 = new JTextField();
		textFieldWSell2.setText("0");
		textFieldWSell2.setColumns(10);
		panelTrade.add(textFieldWSell2, "cell 5 5,growx");
		
		panelImgWestSell3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "milk.png");
		panelImgWestSell3.setToolTipText("MILK");
		panelImgWestSell3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestSell3, "cell 7 5,grow");
		
		textFieldWSell3 = new JTextField();
		textFieldWSell3.setText("0");
		textFieldWSell3.setColumns(10);
		panelTrade.add(textFieldWSell3, "cell 8 5,growx");
		
		panelImgWestSell4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "honey.png");
		panelImgWestSell4.setToolTipText("HONEY");
		panelImgWestSell4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestSell4, "cell 10 5,grow");
		
		textFieldWSell4 = new JTextField();
		textFieldWSell4.setText("0");
		textFieldWSell4.setColumns(10);
		panelTrade.add(textFieldWSell4, "cell 11 5,growx");
		
		panelImgWestSell5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "chocolates.png");
		panelImgWestSell5.setToolTipText("CHOCOLATES");
		panelImgWestSell5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgWestSell5, "cell 13 5,grow");
		
		textFieldWSell5 = new JTextField();
		textFieldWSell5.setText("0");
		textFieldWSell5.setColumns(10);
		panelTrade.add(textFieldWSell5, "cell 14 5,growx");
		
		lblIncomeWSell = new JLabel("-0");
		lblIncomeWSell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeWSell, "cell 16 5,alignx right");
		
		panelImgEast = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagEast.png");
		panelImgEast.setToolTipText("HUA (EAST)");
		panelImgEast.setBackground(darkBackground);
		panelTrade.add(panelImgEast, "cell 0 7,grow");
		
		lblNationE = new JLabel("HUA");
		panelTrade.add(lblNationE, "cell 1 7 2 1");
		
		lblBuyE = new JLabel("Buys");
		lblBuyE.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblBuyE, "cell 0 8");
		
		panelImgEastBuy1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "leather.png");
		panelImgEastBuy1.setToolTipText("LEATHER");
		panelImgEastBuy1.setBackground(darkBackground);
		panelTrade.add(panelImgEastBuy1, "cell 1 8,grow");
		
		textFieldEBuy1 = new JTextField();
		textFieldEBuy1.setText("0");
		textFieldEBuy1.setColumns(10);
		panelTrade.add(textFieldEBuy1, "cell 2 8,growx");
		
		panelImgEastBuy2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "ornaments.png");
		panelImgEastBuy2.setToolTipText("ORNAMETNS");
		panelImgEastBuy2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastBuy2, "cell 4 8,grow");
		
		textFieldEBuy2 = new JTextField();
		textFieldEBuy2.setText("0");
		textFieldEBuy2.setColumns(10);
		panelTrade.add(textFieldEBuy2, "cell 5 8,growx");
		
		panelImgEastBuy3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cocoabeans.png");
		panelImgEastBuy3.setToolTipText("COCOABEANS");
		panelImgEastBuy3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastBuy3, "cell 7 8,grow");
		
		textFieldEBuy3 = new JTextField();
		textFieldEBuy3.setText("0");
		textFieldEBuy3.setColumns(10);
		panelTrade.add(textFieldEBuy3, "cell 8 8,growx");
		
		panelImgEastBuy4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "barrels.png");
		panelImgEastBuy4.setToolTipText("BARRELS");
		panelImgEastBuy4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastBuy4, "cell 10 8,grow");
		
		textFieldEBuy4 = new JTextField();
		textFieldEBuy4.setText("0");
		textFieldEBuy4.setColumns(10);
		panelTrade.add(textFieldEBuy4, "cell 11 8,growx");
		
		panelImgEastBuy5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "pistols.png");
		panelImgEastBuy5.setToolTipText("PISTOLS");
		panelImgEastBuy5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastBuy5, "cell 13 8,grow");
		
		textFieldEBuy5 = new JTextField();
		textFieldEBuy5.setText("0");
		textFieldEBuy5.setColumns(10);
		panelTrade.add(textFieldEBuy5, "cell 14 8,growx");
		
		lblIncomeEBuy = new JLabel("+0");
		lblIncomeEBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeEBuy, "cell 16 8,alignx right");
		
		lblSellE = new JLabel("Sells");
		lblSellE.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblSellE, "cell 0 9");
		
		panelImgEastSell1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "stone.png");
		panelImgEastSell1.setToolTipText("STONE");
		panelImgEastSell1.setBackground(darkBackground);
		panelTrade.add(panelImgEastSell1, "cell 1 9,grow");
		
		textFieldESell1 = new JTextField();
		textFieldESell1.setText("0");
		textFieldESell1.setColumns(10);
		panelTrade.add(textFieldESell1, "cell 2 9,growx");
		
		panelImgEastSell2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "grain.png");
		panelImgEastSell2.setToolTipText("GRAIN");
		panelImgEastSell2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastSell2, "cell 4 9,grow");
		
		textFieldESell2 = new JTextField();
		textFieldESell2.setText("0");
		textFieldESell2.setColumns(10);
		panelTrade.add(textFieldESell2, "cell 5 9,growx");
		
		panelImgEastSell3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "tobacco.png");
		panelImgEastSell3.setToolTipText("TOBACCO");
		panelImgEastSell3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastSell3, "cell 7 9,grow");
		
		textFieldESell3 = new JTextField();
		textFieldESell3.setText("0");
		textFieldESell3.setColumns(10);
		panelTrade.add(textFieldESell3, "cell 8 9,growx");
		
		panelImgEastSell14 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "horses.png");
		panelImgEastSell14.setToolTipText("HORSES");
		panelImgEastSell14.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastSell14, "cell 10 9,grow");
		
		textFieldESell4 = new JTextField();
		textFieldESell4.setText("0");
		textFieldESell4.setColumns(10);
		panelTrade.add(textFieldESell4, "cell 11 9,growx");
		
		panelImgEastSell5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cake.png");
		panelImgEastSell5.setToolTipText("CAKE");
		panelImgEastSell5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgEastSell5, "cell 13 9,grow");
		
		textFieldESell5 = new JTextField();
		textFieldESell5.setText("0");
		textFieldESell5.setColumns(10);
		panelTrade.add(textFieldESell5, "cell 14 9,growx");
		
		lblIncomeESell = new JLabel("-0");
		lblIncomeESell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeESell, "cell 16 9,alignx right");
		
		panelImgSouth = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagSouth.png");
		panelImgSouth.setToolTipText("EEGIS (SOUTH)");
		panelImgSouth.setBackground(darkBackground);
		panelTrade.add(panelImgSouth, "cell 0 11,grow");
		
		lblNationS = new JLabel("EEGIS");
		panelTrade.add(lblNationS, "cell 1 11 2 1");
		
		lblBuyS = new JLabel("Buys");
		lblBuyS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblBuyS, "cell 0 12");
		
		panelImgSouthBuy1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "water.png");
		panelImgSouthBuy1.setToolTipText("WATER");
		panelImgSouthBuy1.setBackground(darkBackground);
		panelTrade.add(panelImgSouthBuy1, "cell 1 12,grow");
		
		textFieldSBuy1 = new JTextField();
		textFieldSBuy1.setText("0");
		textFieldSBuy1.setColumns(10);
		panelTrade.add(textFieldSBuy1, "cell 2 12,growx");
		
		panelImgSouthBuy2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "paper.png");
		panelImgSouthBuy2.setToolTipText("PAPER");
		panelImgSouthBuy2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthBuy2, "cell 4 12,grow");
		
		textFieldSBuy2 = new JTextField();
		textFieldSBuy2.setText("0");
		textFieldSBuy2.setColumns(10);
		panelTrade.add(textFieldSBuy2, "cell 5 12,growx");
		
		panelImgSouthBuy3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cheese.png");
		panelImgSouthBuy3.setToolTipText("CHEESE");
		panelImgSouthBuy3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthBuy3, "cell 7 12,grow");
		
		textFieldSBuy3 = new JTextField();
		textFieldSBuy3.setText("0");
		textFieldSBuy3.setColumns(10);
		panelTrade.add(textFieldSBuy3, "cell 8 12,growx");
		
		panelImgSouthBuy4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "beer.png");
		panelImgSouthBuy4.setToolTipText("BEER");
		panelImgSouthBuy4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthBuy4, "cell 10 12,grow");
		
		textFieldSBuy4 = new JTextField();
		textFieldSBuy4.setText("0");
		textFieldSBuy4.setColumns(10);
		panelTrade.add(textFieldSBuy4, "cell 11 12,growx");
		
		panelImgSouthBuy5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "bread.png");
		panelImgSouthBuy5.setToolTipText("BREAD");
		panelImgSouthBuy5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthBuy5, "cell 13 12,grow");
		
		textFieldSBuy5 = new JTextField();
		textFieldSBuy5.setText("0");
		textFieldSBuy5.setColumns(10);
		panelTrade.add(textFieldSBuy5, "cell 14 12,growx");
		
		lblIncomeSBuy = new JLabel("+0");
		lblIncomeSBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeSBuy, "cell 16 12,alignx right");
		
		lblSellS = new JLabel("Sells");
		lblSellS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblSellS, "cell 0 13");
		
		panelImgSouthSell1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "sand.png");
		panelImgSouthSell1.setToolTipText("SAND");
		panelImgSouthSell1.setBackground(darkBackground);
		panelTrade.add(panelImgSouthSell1, "cell 1 13,grow");
		
		textFieldSSell1 = new JTextField();
		textFieldSSell1.setText("0");
		textFieldSSell1.setColumns(10);
		panelTrade.add(textFieldSSell1, "cell 2 13,growx");
		
		panelImgSouthSell2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "perfume.png");
		panelImgSouthSell2.setToolTipText("PERFUME");
		panelImgSouthSell2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthSell2, "cell 4 13,grow");
		
		textFieldSSell2 = new JTextField();
		textFieldSSell2.setText("0");
		textFieldSSell2.setColumns(10);
		panelTrade.add(textFieldSSell2, "cell 5 13,growx");
		
		panelImgSouthSell3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "goldOre.png");
		panelImgSouthSell3.setToolTipText("GOLD ORE");
		panelImgSouthSell3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthSell3, "cell 7 13,grow");
		
		textFieldSSell3 = new JTextField();
		textFieldSSell3.setText("0");
		textFieldSSell3.setColumns(10);
		panelTrade.add(textFieldSSell3, "cell 8 13,growx");
		
		panelImgSouthSell4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "cocoa.png");
		panelImgSouthSell4.setToolTipText("COCOA");
		panelImgSouthSell4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthSell4, "cell 10 13,grow");
		
		textFieldSSell4 = new JTextField();
		textFieldSSell4.setText("0");
		textFieldSSell4.setColumns(10);
		panelTrade.add(textFieldSSell4, "cell 11 13,growx");
		
		panelImgSouthSell5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "coffee.png");
		panelImgSouthSell5.setToolTipText("COFFEE");
		panelImgSouthSell5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgSouthSell5, "cell 13 13,grow");
		
		textFieldSSell5 = new JTextField();
		textFieldSSell5.setText("0");
		textFieldSSell5.setColumns(10);
		panelTrade.add(textFieldSSell5, "cell 14 13,growx");
		
		lblIncomeSSell = new JLabel("-0");
		lblIncomeSSell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeSSell, "cell 16 13,alignx right");
		
		panelImgNorth = new JImgPanel("resources" + File.separator + "images" + File.separator + "decoration" + File.separator + "flagNorth.png");
		panelImgNorth.setToolTipText("GOREHEIM (NORTH)");
		panelImgNorth.setBackground(darkBackground);
		panelTrade.add(panelImgNorth, "cell 0 15,grow");
		
		lblNationN = new JLabel("GOREHEIM");
		panelTrade.add(lblNationN, "cell 1 15 2 1");
		
		lblBuyN = new JLabel("Buys");
		lblBuyN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblBuyN, "cell 0 16");
		
		panelImgNorthBuy1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "wool.png");
		panelImgNorthBuy1.setToolTipText("WOOL");
		panelImgNorthBuy1.setBackground(darkBackground);
		panelTrade.add(panelImgNorthBuy1, "cell 1 16,grow");
		
		textFieldNBuy1 = new JTextField();
		textFieldNBuy1.setText("0");
		textFieldNBuy1.setColumns(10);
		panelTrade.add(textFieldNBuy1, "cell 2 16,growx");
		
		panelImgNorthBuy2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "clothes.png");
		panelImgNorthBuy2.setToolTipText("CLOTHES");
		panelImgNorthBuy2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthBuy2, "cell 4 16,grow");
		
		textFieldNBuy2 = new JTextField();
		textFieldNBuy2.setText("0");
		textFieldNBuy2.setColumns(10);
		panelTrade.add(textFieldNBuy2, "cell 5 16,growx");
		
		panelImgNorthBuy3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "marshweed.png");
		panelImgNorthBuy3.setToolTipText("MARSHWEED");
		panelImgNorthBuy3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthBuy3, "cell 7 16,grow");
		
		textFieldNBuy3 = new JTextField();
		textFieldNBuy3.setText("0");
		textFieldNBuy3.setColumns(10);
		panelTrade.add(textFieldNBuy3, "cell 8 16,growx");
		
		panelImgNorthBuy4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "jewelry.png");
		panelImgNorthBuy4.setToolTipText("JEWELRY");
		panelImgNorthBuy4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthBuy4, "cell 10 16,grow");
		
		textFieldNBuy4 = new JTextField();
		textFieldNBuy4.setText("0");
		textFieldNBuy4.setColumns(10);
		panelTrade.add(textFieldNBuy4, "cell 11 16,growx");
		
		panelImgNorthBuy5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "liquor.png");
		panelImgNorthBuy5.setToolTipText("LIQUOR");
		panelImgNorthBuy5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthBuy5, "cell 13 16,grow");
		
		textFieldNBuy5 = new JTextField();
		textFieldNBuy5.setText("0");
		textFieldNBuy5.setColumns(10);
		panelTrade.add(textFieldNBuy5, "cell 14 16,growx");
		
		lblIncomeNBuy = new JLabel("+0");
		lblIncomeNBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeNBuy, "cell 16 16,alignx right");
		
		lblSellN = new JLabel("Sells");
		lblSellN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblSellN, "cell 0 17");
		
		panelImgNorthSell1 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "coal.png");
		panelImgNorthSell1.setToolTipText("COAL");
		panelImgNorthSell1.setBackground(darkBackground);
		panelTrade.add(panelImgNorthSell1, "cell 1 17,grow");
		
		textFieldNSell1 = new JTextField();
		textFieldNSell1.setText("0");
		textFieldNSell1.setColumns(10);
		panelTrade.add(textFieldNSell1, "cell 2 17,growx");
		
		panelImgNorthSell2 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "ironOre.png");
		panelImgNorthSell2.setToolTipText("IRON ORE");
		panelImgNorthSell2.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthSell2, "cell 4 17,grow");
		
		textFieldNSell2 = new JTextField();
		textFieldNSell2.setText("0");
		textFieldNSell2.setColumns(10);
		panelTrade.add(textFieldNSell2, "cell 5 17,growx");
		
		panelImgNorthSell3 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "icewine.png");
		panelImgNorthSell3.setToolTipText("ICEWINE");
		panelImgNorthSell3.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthSell3, "cell 7 17,grow");
		
		textFieldNSell3 = new JTextField();
		textFieldNSell3.setText("0");
		textFieldNSell3.setColumns(10);
		panelTrade.add(textFieldNSell3, "cell 8 17,growx");
		
		panelImgNorthSell4 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "hops.png");
		panelImgNorthSell4.setToolTipText("HOPS");
		panelImgNorthSell4.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthSell4, "cell 10 17,grow");
		
		textFieldNSell4 = new JTextField();
		textFieldNSell4.setText("0");
		textFieldNSell4.setColumns(10);
		panelTrade.add(textFieldNSell4, "cell 11 17,growx");
		
		panelImgNorthSell5 = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "salmon.png");
		panelImgNorthSell5.setToolTipText("SALMON");
		panelImgNorthSell5.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgNorthSell5, "cell 13 17,grow");
		
		textFieldNSell5 = new JTextField();
		textFieldNSell5.setText("0");
		textFieldNSell5.setColumns(10);
		panelTrade.add(textFieldNSell5, "cell 14 17,growx");
		
		lblIncomeNSell = new JLabel("-0");
		lblIncomeNSell.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblIncomeNSell, "cell 16 17,alignx right");
		
		lblTotal = new JLabel("Total");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblTotal, "cell 16 18,alignx right");
		
		lblInvalidValuesFound = new JLabel("Invalid value(s) found, please correct these!");
		lblInvalidValuesFound.setVisible(false);
		lblInvalidValuesFound.setForeground(Color.RED);
		lblInvalidValuesFound.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblInvalidValuesFound, "cell 0 19 15 1");
		
		lblTotalIncome = new JLabel("+0");
		lblTotalIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTrade.add(lblTotalIncome, "cell 16 19,alignx right");
		
		panelImgTotalGold = new JImgPanel("resources" + File.separator + "images" + File.separator + "items" + File.separator + "gold.png");
		panelImgTotalGold.setToolTipText("GOLD");
		panelImgTotalGold.setBackground(new Color(229, 194, 137));
		panelTrade.add(panelImgTotalGold, "cell 17 19,grow");
		
		setUpTradeData(tradeData);
	}
	
	public void setUpResearchScreen()
	{
		BufferedReader read = null;
		String lastLine = null, shortLine=null;
		try
		{
			read = new BufferedReader(new FileReader("src" + File.separator + "config" + File.separator + "research.config"));
			read.readLine();
			while((lastLine=read.readLine()) != null && !(lastLine.equals("---connectors")) )
			{
				shortLine = lastLine.split("/")[0].trim();
				System.out.println(shortLine);
			}
			
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Error in research.config!");
		}
		finally
		{
			try 
			{
				if(read!=null)
				{
					read.close();
				}					
			} 
			catch (IOException ex) 
			{
				JOptionPane.showMessageDialog(null, "Error in research.config!");
			}
		}
		
				

	}
	public void receiveTradeData(int[] trades)
	{
		tradeData = trades;
	}
	public void setUpTradeData(int[] trades)
	{
		textFieldWSell1.setText(String.valueOf(trades[0]));
		textFieldWSell2.setText(String.valueOf(trades[1]));
		textFieldWSell3.setText(String.valueOf(trades[2]));
		textFieldWSell4.setText(String.valueOf(trades[3]));
		textFieldWSell5.setText(String.valueOf(trades[4]));
		textFieldESell1.setText(String.valueOf(trades[10]));
		textFieldESell2.setText(String.valueOf(trades[11]));
		textFieldESell3.setText(String.valueOf(trades[12]));
		textFieldESell4.setText(String.valueOf(trades[13]));
		textFieldESell5.setText(String.valueOf(trades[14]));
		textFieldSSell1.setText(String.valueOf(trades[20]));
		textFieldSSell2.setText(String.valueOf(trades[21]));
		textFieldSSell3.setText(String.valueOf(trades[22]));
		textFieldSSell4.setText(String.valueOf(trades[23]));
		textFieldSSell5.setText(String.valueOf(trades[24]));
		textFieldNSell1.setText(String.valueOf(trades[30]));
		textFieldNSell2.setText(String.valueOf(trades[31]));
		textFieldNSell3.setText(String.valueOf(trades[32]));
		textFieldNSell4.setText(String.valueOf(trades[33]));
		textFieldNSell5.setText(String.valueOf(trades[34]));
		
		textFieldWBuy1.setText(String.valueOf(trades[5]));
		textFieldWBuy2.setText(String.valueOf(trades[6]));
		textFieldWBuy3.setText(String.valueOf(trades[7]));
		textFieldWBuy4.setText(String.valueOf(trades[8]));
		textFieldWBuy5.setText(String.valueOf(trades[9]));
		textFieldEBuy1.setText(String.valueOf(trades[15]));
		textFieldEBuy2.setText(String.valueOf(trades[16]));
		textFieldEBuy3.setText(String.valueOf(trades[17]));
		textFieldEBuy4.setText(String.valueOf(trades[18]));
		textFieldEBuy5.setText(String.valueOf(trades[19]));
		textFieldSBuy1.setText(String.valueOf(trades[25]));
		textFieldSBuy2.setText(String.valueOf(trades[26]));
		textFieldSBuy3.setText(String.valueOf(trades[27]));
		textFieldSBuy4.setText(String.valueOf(trades[28]));
		textFieldSBuy5.setText(String.valueOf(trades[29]));
		textFieldNBuy1.setText(String.valueOf(trades[35]));
		textFieldNBuy2.setText(String.valueOf(trades[36]));
		textFieldNBuy3.setText(String.valueOf(trades[37]));
		textFieldNBuy4.setText(String.valueOf(trades[38]));
		textFieldNBuy5.setText(String.valueOf(trades[39]));
		
		amountBuy.clear();
		amountBuy.add(trades[0]);
		amountBuy.add(trades[1]);
		amountBuy.add(trades[2]);
		amountBuy.add(trades[3]);
		amountBuy.add(trades[4]);
		amountBuy.add(trades[10]);
		amountBuy.add(trades[11]);
		amountBuy.add(trades[12]);
		amountBuy.add(trades[13]);
		amountBuy.add(trades[14]);
		amountBuy.add(trades[20]);
		amountBuy.add(trades[21]);
		amountBuy.add(trades[22]);
		amountBuy.add(trades[23]);
		amountBuy.add(trades[24]);
		amountBuy.add(trades[30]);
		amountBuy.add(trades[31]);
		amountBuy.add(trades[32]);
		amountBuy.add(trades[33]);
		amountBuy.add(trades[34]);
		
		amountSell.clear();
		amountSell.add(trades[5]);
		amountSell.add(trades[6]);
		amountSell.add(trades[7]);
		amountSell.add(trades[8]);
		amountSell.add(trades[9]);
		amountSell.add(trades[15]);
		amountSell.add(trades[16]);
		amountSell.add(trades[17]);
		amountSell.add(trades[18]);
		amountSell.add(trades[19]);
		amountSell.add(trades[25]);
		amountSell.add(trades[26]);
		amountSell.add(trades[27]);
		amountSell.add(trades[28]);
		amountSell.add(trades[29]);
		amountSell.add(trades[35]);
		amountSell.add(trades[36]);
		amountSell.add(trades[37]);
		amountSell.add(trades[38]);
		amountSell.add(trades[39]);
		
		calculateTrades();
	}
	public void calculateTrades()
	{
		try
		{
			lblInvalidValuesFound.setVisible(false);
			westIncome = (Integer.parseInt(textFieldWBuy1.getText()) *tradeValues.get(0) +
					Integer.parseInt(textFieldWBuy2.getText()) *tradeValues.get(1) + 
					Integer.parseInt(textFieldWBuy3.getText()) *tradeValues.get(2) + 
					Integer.parseInt(textFieldWBuy4.getText()) *tradeValues.get(3) +
					Integer.parseInt(textFieldWBuy5.getText()) *tradeValues.get(4));
			
			westPayment = (Integer.parseInt(textFieldWSell1.getText()) *tradeValues.get(5) +
					Integer.parseInt(textFieldWSell2.getText()) *tradeValues.get(6) + 
					Integer.parseInt(textFieldWSell3.getText()) *tradeValues.get(7) + 
					Integer.parseInt(textFieldWSell4.getText()) *tradeValues.get(8) +
					Integer.parseInt(textFieldWSell5.getText()) *tradeValues.get(9));
			
			eastIncome = (Integer.parseInt(textFieldEBuy1.getText()) *tradeValues.get(10) +
					Integer.parseInt(textFieldEBuy2.getText()) *tradeValues.get(11) + 
					Integer.parseInt(textFieldEBuy3.getText()) *tradeValues.get(12) + 
					Integer.parseInt(textFieldEBuy4.getText()) *tradeValues.get(13) +
					Integer.parseInt(textFieldEBuy5.getText()) *tradeValues.get(14));
			
			eastPayment = (Integer.parseInt(textFieldESell1.getText()) *tradeValues.get(15) +
					Integer.parseInt(textFieldESell2.getText()) *tradeValues.get(16) + 
					Integer.parseInt(textFieldESell3.getText()) *tradeValues.get(17) + 
					Integer.parseInt(textFieldESell4.getText()) *tradeValues.get(18) +
					Integer.parseInt(textFieldESell5.getText()) *tradeValues.get(19));
			
			southIncome = (Integer.parseInt(textFieldSBuy1.getText()) *tradeValues.get(20) +
					Integer.parseInt(textFieldSBuy2.getText()) *tradeValues.get(21) + 
					Integer.parseInt(textFieldSBuy3.getText()) *tradeValues.get(22) + 
					Integer.parseInt(textFieldSBuy4.getText()) *tradeValues.get(23) +
					Integer.parseInt(textFieldSBuy5.getText()) *tradeValues.get(24));
			
			southPayment = (Integer.parseInt(textFieldSSell1.getText()) *tradeValues.get(25) +
					Integer.parseInt(textFieldSSell2.getText()) *tradeValues.get(26) + 
					Integer.parseInt(textFieldSSell3.getText()) *tradeValues.get(27) + 
					Integer.parseInt(textFieldSSell4.getText()) *tradeValues.get(28) +
					Integer.parseInt(textFieldSSell5.getText()) *tradeValues.get(29));
			
			northIncome = (Integer.parseInt(textFieldNBuy1.getText()) *tradeValues.get(30) +
					Integer.parseInt(textFieldNBuy2.getText()) *tradeValues.get(31) + 
					Integer.parseInt(textFieldNBuy3.getText()) *tradeValues.get(32) + 
					Integer.parseInt(textFieldNBuy4.getText()) *tradeValues.get(33) +
					Integer.parseInt(textFieldNBuy5.getText()) *tradeValues.get(34));
			
			northPayment = (Integer.parseInt(textFieldNSell1.getText()) *tradeValues.get(35) +
					Integer.parseInt(textFieldNSell2.getText()) *tradeValues.get(36) + 
					Integer.parseInt(textFieldNSell3.getText()) *tradeValues.get(37) + 
					Integer.parseInt(textFieldNSell4.getText()) *tradeValues.get(38) +
					Integer.parseInt(textFieldNSell5.getText()) *tradeValues.get(39));
		}
		catch(NumberFormatException ex)
		{
			lblInvalidValuesFound.setVisible(true);
		}
		
		lblIncomeWBuy.setText(("+" + String.valueOf(westIncome)));
		lblIncomeWSell.setText(("-" + String.valueOf(westPayment)));
		lblIncomeEBuy.setText(("+" + String.valueOf(eastIncome)));
		lblIncomeESell.setText(("-" + String.valueOf(eastPayment)));
		lblIncomeSBuy.setText(("+" + String.valueOf(southIncome)));
		lblIncomeSSell.setText(("-" + String.valueOf(southPayment)));
		lblIncomeNBuy.setText(("+" + String.valueOf(northIncome)));
		lblIncomeNSell.setText(("-" + String.valueOf(northPayment)));
		
		income = (westIncome + eastIncome + southIncome + northIncome) - (westPayment + eastPayment + southPayment + northPayment);
		
		if (income < 0)
			lblTotalIncome.setText(String.valueOf(income));
		else
			lblTotalIncome.setText("+" + income);
	}
	public void newTick()
	{
		Random r = new Random();
		final int percentageBaseNewInhab = 4;
		final int ticksPerDayInGame = cwGame.getTICKSPERSEC() * (60/cwGame.getINTERVAL()) * 24;
		final int timeToConsume = 1700; //equals 17:00
		final int timeToCheckLiving = 600;
		final int timeToTrade = 800;
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
			produceGoods();
			if (cwGame.getContinousTime() % ticksPerDayInGame == ticksPerDayInGame * timeToConsume / 2400)
				consumeGoods();
			if (cwGame.getContinousTime() % ticksPerDayInGame == ticksPerDayInGame * timeToCheckLiving / 2400)
				checkHousingSpace();
			if (cwGame.getContinousTime() % ticksPerDayInGame == ticksPerDayInGame * timeToTrade / 2400)
				executeTrades();
		}
	}
	public void produceGoods()
	{
		for (int x = 0; x < panelGame.getTilesX(); x++)
		{
			for (int y= 0; y < panelGame.getTilesY(); y++)
			{
				Boolean resource1Available = false;
				Boolean resource2Available = false;
				Boolean resourcesAvailable = false;
				if(panelGame.getMapping()[x][y][4] >= 200 && panelGame.getMapping()[x][y][4] < 300)
				{
					if (panelGame.getMapping()[x][y][9] == 0)
					{
						if (panelGame.getMapping()[x][y][11] == 0)
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 101 && storeWater >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 102 && storeWood >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 103 && storeStone >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 104 && storeSteel >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 105 && storeGlass >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 106 && storeVegetables >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 107 && storeClothes >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 108 && storeCoal >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 109 && storeMilk >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 110 && storeMeat >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 111 && storeMedicine >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 112 && storeShoes >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 113 && storeHoney >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 114 && storeOrnaments >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 115 && storeBeer >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 116 && storeFruits >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 117 && storeHorses >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 118 && storeBread >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 119 && storeTobacco >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 120 && storePerfume >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 121 && storeCheese >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 122 && storeCocoa >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 123 && storeIcewine >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 124 && storeCake >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 125 && storeDeer >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 126 && storeChocolates >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 127 && storePistols >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 128 && storeLiquor >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 129 && storeBooks >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 130 && storeJewelry >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 131 && storeSalmon >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 132 && storeCoffee >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 133 && storeSand >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 134 && storeWool >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 135 && storeLeather >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 136 && storeMarshweed >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 137 && storeGrain >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 138 && storeHops >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 139 && storeCocoabeans >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 140 && storeChocolate >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 141 && storeIceflowers >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 142 && storeBarrels >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 143 && storePaper >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 144 && storeIronOre >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;							
						}
						else if (panelGame.getMapping()[x][y][11] == 145 && storeGoldOre >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;							
						}
						else if (panelGame.getMapping()[x][y][11] == 146 && storeCows >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						else if (panelGame.getMapping()[x][y][11] == 147 && storeGold >= productCosts1.get(panelGame.getMapping()[x][y][11] - 101))
						{
							resource1Available = true;
						}
						
						
						if (panelGame.getMapping()[x][y][12] == 0)
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 101 && storeWater >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 102 && storeWood >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 103 && storeStone >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 104 && storeSteel >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 105 && storeGlass >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 106 && storeVegetables >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 107 && storeClothes >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 108 && storeCoal >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 109 && storeMilk >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 110 && storeMeat >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 111 && storeMedicine >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 112 && storeShoes >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 113 && storeHoney >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 114 && storeOrnaments >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 115 && storeBeer >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 116 && storeFruits >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 117 && storeHorses >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 118 && storeBread >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 119 && storeTobacco >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 120 && storePerfume >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 121 && storeCheese >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 122 && storeCocoa >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 123 && storeIcewine >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 124 && storeCake >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 125 && storeDeer >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 126 && storeChocolates >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 127 && storePistols >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 128 && storeLiquor >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 129 && storeBooks >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 130 && storeJewelry >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 131 && storeSalmon >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 132 && storeCoffee >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 133 && storeSand >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 134 && storeWool >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 135 && storeLeather >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 136 && storeMarshweed >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 137 && storeGrain >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 138 && storeHops >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 139 && storeCocoabeans >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 140 && storeChocolate >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 141 && storeIceflowers >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 142 && storeBarrels >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 143 && storePaper >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 144 && storeIronOre >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;							
						}
						else if (panelGame.getMapping()[x][y][12] == 145 && storeGoldOre >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;							
						}
						else if (panelGame.getMapping()[x][y][12] == 146 && storeCows >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						else if (panelGame.getMapping()[x][y][12] == 147 && storeGold >= productCosts2.get(panelGame.getMapping()[x][y][12] - 101))
						{
							resource2Available = true;
						}
						
						if (resource1Available && resource2Available)
						{
							resourcesAvailable = true;
							panelGame.getMapping()[x][y][9] = 1;
						}
						
						if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 101)
						{
							storeWater -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 102)
						{
							storeWood -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);						
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 103)
						{
							storeStone -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 104)
						{
							storeSteel -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 105)
						{
							storeGlass -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 106)
						{
							storeVegetables -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 107)
						{
							storeClothes -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 108)
						{
							storeCoal -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 109)
						{
							storeMilk -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 110)
						{
							storeMeat -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 111)
						{
							storeMedicine -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 112)
						{
							storeShoes -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 113)
						{
							storeHoney -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 114)
						{
							storeOrnaments -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 115)
						{
							storeBeer -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 116)
						{
							storeFruits -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 117)
						{
							storeHorses -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 118)
						{
							storeBread -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 119)
						{
							storeTobacco -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 120)
						{
							storePerfume -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 121)
						{
							storeCheese -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 122)
						{
							storeCocoa -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 123)
						{
							storeIcewine -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 124)
						{
							storeCake -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 125)
						{
							storeDeer -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 126)
						{
							storeChocolates -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 127)
						{
							storePistols -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 128)
						{
							storeLiquor -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 129)
						{
							storeBooks -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 130)
						{
							storeJewelry -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 131)
						{
							storeSalmon -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 132)
						{
							storeCoffee -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 133)
						{
							storeSand -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 134)
						{
							storeWool -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 135)
						{
							storeLeather -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 136)
						{
							storeMarshweed -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 137)
						{
							storeGrain -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 138)
						{
							storeHops -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 139)
						{
							storeCocoabeans -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 140)
						{
							storeChocolate -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 141)
						{
							storeIceflowers -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 142)
						{
							storeBarrels -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 143)
						{
							storePaper -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 144)
						{
							storeIronOre -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 145)
						{
							storeGoldOre -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 146)
						{
							storeCows -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][11] == 147)
						{
							storeGold -= productCosts1.get(panelGame.getMapping()[x][y][11] - 101);							
						}
						
						
						if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 101)
						{
							storeWater -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 102)
						{
							storeWood -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);						
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 103)
						{
							storeStone -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 104)
						{
							storeSteel -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 105)
						{
							storeGlass -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 106)
						{
							storeVegetables -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 107)
						{
							storeClothes -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 108)
						{
							storeCoal -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 109)
						{
							storeMilk -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 110)
						{
							storeMeat -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 111)
						{
							storeMedicine -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 112)
						{
							storeShoes -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 113)
						{
							storeHoney -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 114)
						{
							storeOrnaments -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 115)
						{
							storeBeer -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 116)
						{
							storeFruits -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 117)
						{
							storeHorses -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 118)
						{
							storeBread -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 119)
						{
							storeTobacco -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 120)
						{
							storePerfume -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 121)
						{
							storeCheese -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 122)
						{
							storeCocoa -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 123)
						{
							storeIcewine -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 124)
						{
							storeCake -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 125)
						{
							storeDeer -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 126)
						{
							storeChocolates -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 127)
						{
							storePistols -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 128)
						{
							storeLiquor -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 129)
						{
							storeBooks -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 130)
						{
							storeJewelry -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 131)
						{
							storeSalmon -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 132)
						{
							storeCoffee -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 133)
						{
							storeSand -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 134)
						{
							storeWool -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 135)
						{
							storeLeather -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 136)
						{
							storeMarshweed -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 137)
						{
							storeGrain -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 138)
						{
							storeHops -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 139)
						{
							storeCocoabeans -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 140)
						{
							storeChocolate -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 141)
						{
							storeIceflowers -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 142)
						{
							storeBarrels -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 143)
						{
							storePaper -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 144)
						{
							storeIronOre -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 145)
						{
							storeGoldOre -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 146)
						{
							storeCows -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
						else if (resourcesAvailable && panelGame.getMapping()[x][y][12] == 147)
						{
							storeGold -= productCosts2.get(panelGame.getMapping()[x][y][12] - 101);							
						}
					}
					
					else if (panelGame.getMapping()[x][y][9] >= 1)
					{
						panelGame.applyProperty(x, y, 9, (panelGame.getMapping()[x][y][9] + 1));
						if(panelGame.getMapping()[x][y][9] >= panelGame.getMapping()[x][y][15])
						{
							panelGame.applyProperty(x, y, 9, 0);
							if (panelGame.getMapping()[x][y][13] == 101)
							{
								storeWater += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 102)
							{
								storeWood += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 103)
							{
								storeStone += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 104)
							{
								storeSteel += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 105)
							{
								storeGlass += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 106)
							{
								storeVegetables += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 107)
							{
								storeClothes += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 108)
							{
								storeCoal += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 109)
							{
								storeMilk += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 110)
							{
								storeMeat += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 111)
							{
								storeMedicine += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 112)
							{
								storeShoes += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 113)
							{
								storeHoney += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 114)
							{
								storeOrnaments += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 115)
							{
								storeBeer += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 116)
							{
								storeFruits += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 117)
							{
								storeHorses += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 118)
							{
								storeBread += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 119)
							{
								storeTobacco += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 120)
							{
								storePerfume += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 121)
							{
								storeCheese += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 122)
							{
								storeCocoa += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 123)
							{
								storeIcewine += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 124)
							{
								storeCake += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 125)
							{
								storeDeer += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 126)
							{
								storeChocolates += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 127)
							{
								storePistols += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 128)
							{
								storeLiquor += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 129)
							{
								storeBooks += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 130)
							{
								storeJewelry += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 131)
							{
								storeSalmon += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 132)
							{
								storeCoffee += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 133)
							{
								storeSand += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 134)
							{
								storeWool += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 135)
							{
								storeLeather += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 136)
							{
								storeMarshweed += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 137)
							{
								storeGrain += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 138)
							{
								storeHops += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 139)
							{
								storeCocoabeans += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 140)
							{
								storeChocolate += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 141)
							{
								storeIceflowers += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 142)
							{
								storeBarrels += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 143)
							{
								storePaper += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 144)
							{
								storeIronOre += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 145)
							{
								storeGoldOre += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 146)
							{
								storeCows += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							else if (panelGame.getMapping()[x][y][13] == 147)
							{
								storeGold += productAmounts1.get(panelGame.getMapping()[x][y][13] - 101);
							}
							
							if (panelGame.getMapping()[x][y][14] == 101)
							{
								storeWater += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 102)
							{
								storeWood += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 103)
							{
								storeStone += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 104)
							{
								storeSteel += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 105)
							{
								storeGlass += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 106)
							{
								storeVegetables += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 107)
							{
								storeClothes += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 108)
							{
								storeCoal += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 109)
							{
								storeMilk += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 110)
							{
								storeMeat += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 111)
							{
								storeMedicine += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 112)
							{
								storeShoes += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 113)
							{
								storeHoney += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 114)
							{
								storeOrnaments += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 115)
							{
								storeBeer += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 116)
							{
								storeFruits += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 117)
							{
								storeHorses += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 118)
							{
								storeBread += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 119)
							{
								storeTobacco += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 120)
							{
								storePerfume += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 121)
							{
								storeCheese += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 122)
							{
								storeCocoa += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 123)
							{
								storeIcewine += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 124)
							{
								storeCake += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 125)
							{
								storeDeer += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 126)
							{
								storeChocolates += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 127)
							{
								storePistols += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 128)
							{
								storeLiquor += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 129)
							{
								storeBooks += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 130)
							{
								storeJewelry += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 131)
							{
								storeSalmon += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 132)
							{
								storeCoffee += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 133)
							{
								storeSand += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 134)
							{
								storeWool += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 135)
							{
								storeLeather += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 136)
							{
								storeMarshweed += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 137)
							{
								storeGrain += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 138)
							{
								storeHops += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 139)
							{
								storeCocoabeans += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 140)
							{
								storeChocolate += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 141)
							{
								storeIceflowers += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 142)
							{
								storeBarrels += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 143)
							{
								storePaper += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 144)
							{
								storeIronOre += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 145)
							{
								storeGoldOre += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 146)
							{
								storeCows += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
							else if (panelGame.getMapping()[x][y][14] == 147)
							{
								storeGold += productAmounts2.get(panelGame.getMapping()[x][y][14] - 101);
							}
						}
					}
				}
			}
		}
		displayStorage();
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
			happiness = happiness + 2 - (20 * (1 - (capacity / (inhabs * 2.0))));
			if(happiness > ((double)capacity / (double)inhabs))
				happiness = 100 - (100 - ((double)capacity / (double)inhabs) /2);
		}
		else
		{
			happiness = happiness + 2;
		}
		showHappiness(happiness);
	}
	
	public void executeTrades()
	{
		if(storeShoes < Integer.parseInt(textFieldWBuy1.getText()))
		{
			storeGold += storeShoes * tradeValues.get(0);
			storeShoes =0;
		}
		else
		{
			storeShoes-=Integer.parseInt(textFieldWBuy1.getText());
			storeGold += Integer.parseInt(textFieldWBuy1.getText()) * tradeValues.get(0);
		}
		
		if(storeMedicine < Integer.parseInt(textFieldWBuy2.getText()))
		{
			storeGold += storeMedicine * tradeValues.get(1);
			storeMedicine =0;
		}
		else
		{
			storeMedicine-=Integer.parseInt(textFieldWBuy2.getText());
			storeGold += Integer.parseInt(textFieldWBuy2.getText()) * tradeValues.get(1);
		}
		
		if(storeFruits < Integer.parseInt(textFieldWBuy3.getText()))
		{
			storeGold += storeFruits * tradeValues.get(2);
			storeFruits =0;
		}
		else
		{
			storeFruits-=Integer.parseInt(textFieldWBuy3.getText());
			storeGold += Integer.parseInt(textFieldWBuy3.getText()) * tradeValues.get(2);
		}
		
		if(storeBooks < Integer.parseInt(textFieldWBuy4.getText()))
		{
			storeGold += storeBooks * tradeValues.get(3);
			storeBooks =0;
		}
		else
		{
			storeBooks-=Integer.parseInt(textFieldWBuy4.getText());
			storeGold += Integer.parseInt(textFieldWBuy4.getText()) * tradeValues.get(3);
		}
		
		if(storeIceflowers < Integer.parseInt(textFieldWBuy5.getText()))
		{
			storeGold += storeIceflowers * tradeValues.get(4);
			storeIceflowers =0;
		}
		else
		{
			storeIceflowers-=Integer.parseInt(textFieldWBuy5.getText());
			storeGold += Integer.parseInt(textFieldWBuy5.getText()) * tradeValues.get(4);
		}
		
		
		if(storeLeather < Integer.parseInt(textFieldEBuy1.getText()))
		{
			storeGold += storeLeather * tradeValues.get(0);
			storeLeather =0;
		}
		else
		{
			storeLeather-=Integer.parseInt(textFieldEBuy1.getText());
			storeGold += Integer.parseInt(textFieldEBuy1.getText()) * tradeValues.get(10);
		}
		
		if(storeOrnaments < Integer.parseInt(textFieldEBuy2.getText()))
		{
			storeGold += storeOrnaments * tradeValues.get(1);
			storeOrnaments =0;
		}
		else
		{
			storeOrnaments-=Integer.parseInt(textFieldEBuy2.getText());
			storeGold += Integer.parseInt(textFieldEBuy2.getText()) * tradeValues.get(11);
		}
		
		if(storeCocoabeans < Integer.parseInt(textFieldEBuy3.getText()))
		{
			storeGold += storeCocoabeans * tradeValues.get(2);
			storeCocoabeans =0;
		}
		else
		{
			storeCocoabeans-=Integer.parseInt(textFieldEBuy3.getText());
			storeGold += Integer.parseInt(textFieldEBuy3.getText()) * tradeValues.get(12);
		}
		
		if(storeBarrels < Integer.parseInt(textFieldEBuy4.getText()))
		{
			storeGold += storeBarrels * tradeValues.get(3);
			storeBarrels =0;
		}
		else
		{
			storeBarrels-=Integer.parseInt(textFieldEBuy4.getText());
			storeGold += Integer.parseInt(textFieldEBuy4.getText()) * tradeValues.get(13);
		}
		
		if(storePistols < Integer.parseInt(textFieldEBuy5.getText()))
		{
			storeGold += storePistols * tradeValues.get(4);
			storePistols =0;
		}
		else
		{
			storePistols-=Integer.parseInt(textFieldEBuy5.getText());
			storeGold += Integer.parseInt(textFieldEBuy5.getText()) * tradeValues.get(14);
		}
		
		
		if(storeWater < Integer.parseInt(textFieldSBuy1.getText()))
		{
			storeGold += storeWater * tradeValues.get(0);
			storeWater =0;
		}
		else
		{
			storeWater-=Integer.parseInt(textFieldSBuy1.getText());
			storeGold += Integer.parseInt(textFieldSBuy1.getText()) * tradeValues.get(20);
		}
		
		if(storePaper < Integer.parseInt(textFieldSBuy2.getText()))
		{
			storeGold += storePaper * tradeValues.get(1);
			storePaper =0;
		}
		else
		{
			storePaper-=Integer.parseInt(textFieldSBuy2.getText());
			storeGold += Integer.parseInt(textFieldSBuy2.getText()) * tradeValues.get(21);
		}
		
		if(storeCheese < Integer.parseInt(textFieldSBuy3.getText()))
		{
			storeGold += storeCheese * tradeValues.get(2);
			storeCheese =0;
		}
		else
		{
			storeCheese-=Integer.parseInt(textFieldSBuy3.getText());
			storeGold += Integer.parseInt(textFieldSBuy3.getText()) * tradeValues.get(22);
		}
		
		if(storeBeer < Integer.parseInt(textFieldSBuy4.getText()))
		{
			storeGold += storeBeer * tradeValues.get(3);
			storeBeer =0;
		}
		else
		{
			storeBeer-=Integer.parseInt(textFieldSBuy4.getText());
			storeGold += Integer.parseInt(textFieldSBuy4.getText()) * tradeValues.get(23);
		}
		
		if(storeBread < Integer.parseInt(textFieldSBuy5.getText()))
		{
			storeGold += storeBread * tradeValues.get(4);
			storeBread =0;
		}
		else
		{
			storeBread-=Integer.parseInt(textFieldSBuy5.getText());
			storeGold += Integer.parseInt(textFieldSBuy5.getText()) * tradeValues.get(24);
		}
		
		if(storeWool < Integer.parseInt(textFieldWBuy1.getText()))
		{
			storeWool += storeWool * tradeValues.get(0);
			storeShoes =0;
		}
		else
		{
			storeWool-=Integer.parseInt(textFieldWBuy1.getText());
			storeGold += Integer.parseInt(textFieldWBuy1.getText()) * tradeValues.get(30);
		}
		
		if(storeClothes < Integer.parseInt(textFieldWBuy2.getText()))
		{
			storeGold += storeClothes * tradeValues.get(1);
			storeClothes =0;
		}
		else
		{
			storeClothes-=Integer.parseInt(textFieldWBuy2.getText());
			storeGold += Integer.parseInt(textFieldWBuy2.getText()) * tradeValues.get(31);
		}
		
		if(storeMarshweed < Integer.parseInt(textFieldWBuy3.getText()))
		{
			storeGold += storeMarshweed * tradeValues.get(2);
			storeMarshweed =0;
		}
		else
		{
			storeMarshweed-=Integer.parseInt(textFieldWBuy3.getText());
			storeGold += Integer.parseInt(textFieldWBuy3.getText()) * tradeValues.get(32);
		}
		
		if(storeJewelry < Integer.parseInt(textFieldWBuy4.getText()))
		{
			storeGold += storeJewelry * tradeValues.get(3);
			storeJewelry =0;
		}
		else
		{
			storeJewelry-=Integer.parseInt(textFieldWBuy4.getText());
			storeGold += Integer.parseInt(textFieldWBuy4.getText()) * tradeValues.get(33);
		}
		
		if(storeLiquor < Integer.parseInt(textFieldWBuy5.getText()))
		{
			storeGold += storeLiquor * tradeValues.get(4);
			storeLiquor =0;
		}
		else
		{
			storeLiquor-=Integer.parseInt(textFieldWBuy5.getText());
			storeGold += Integer.parseInt(textFieldWBuy5.getText()) * tradeValues.get(34);
		}
			
		
		
		if(storeGold < Integer.parseInt(textFieldWSell1.getText()) *tradeValues.get(5))
		{
			storeVegetables += storeGold / tradeValues.get(5);
			storeGold = storeGold % tradeValues.get(5); 
		}
		else
		{
			storeVegetables += Integer.parseInt(textFieldWSell1.getText());
			storeGold -= Integer.parseInt(textFieldWSell1.getText()) *tradeValues.get(5);
		}
		
		if(storeGold < Integer.parseInt(textFieldWSell2.getText()) *tradeValues.get(6))
		{
			storeWood += storeGold / tradeValues.get(6);
			storeGold = storeGold % tradeValues.get(6); 
		}
		else
		{
			storeWood += Integer.parseInt(textFieldWSell2.getText());
			storeGold -= Integer.parseInt(textFieldWSell2.getText()) *tradeValues.get(6);
		}
		
		if(storeGold < Integer.parseInt(textFieldWSell3.getText()) *tradeValues.get(7))
		{
			storeMilk += storeGold / tradeValues.get(7);
			storeGold = storeGold % tradeValues.get(7); 
		}
		else
		{
			storeMilk += Integer.parseInt(textFieldWSell3.getText());
			storeGold -= Integer.parseInt(textFieldWSell3.getText()) *tradeValues.get(7);
		}
		
		if(storeGold < Integer.parseInt(textFieldWSell4.getText()) *tradeValues.get(8))
		{
			storeHoney += storeGold / tradeValues.get(8);
			storeGold = storeGold % tradeValues.get(8); 
		}
		else
		{
			storeHoney += Integer.parseInt(textFieldWSell4.getText());
			storeGold -= Integer.parseInt(textFieldWSell4.getText()) *tradeValues.get(8);
		}
		
		if(storeGold < Integer.parseInt(textFieldWSell5.getText()) *tradeValues.get(9))
		{
			storeChocolates += storeGold / tradeValues.get(9);
			storeGold = storeGold % tradeValues.get(9); 
		}
		else
		{
			storeChocolates += Integer.parseInt(textFieldWSell5.getText());
			storeGold -= Integer.parseInt(textFieldWSell5.getText()) *tradeValues.get(9);
		}
		
		
		if(storeGold < Integer.parseInt(textFieldESell1.getText()) *tradeValues.get(15))
		{
			storeStone += storeGold / tradeValues.get(15);
			storeGold = storeGold % tradeValues.get(15); 
		}
		else
		{
			storeStone += Integer.parseInt(textFieldESell1.getText());
			storeGold -= Integer.parseInt(textFieldESell1.getText()) *tradeValues.get(15);
		}
		
		if(storeGold < Integer.parseInt(textFieldESell2.getText()) *tradeValues.get(16))
		{
			storeGrain += storeGold / tradeValues.get(16);
			storeGold = storeGold % tradeValues.get(16); 
		}
		else
		{
			storeGrain += Integer.parseInt(textFieldESell2.getText());
			storeGold -= Integer.parseInt(textFieldESell2.getText()) *tradeValues.get(16);
		}
		
		if(storeGold < Integer.parseInt(textFieldESell3.getText()) *tradeValues.get(17))
		{
			storeTobacco += storeGold / tradeValues.get(17);
			storeGold = storeGold % tradeValues.get(17); 
		}
		else
		{
			storeTobacco += Integer.parseInt(textFieldESell3.getText());
			storeGold -= Integer.parseInt(textFieldESell3.getText()) *tradeValues.get(17);
		}
		
		if(storeGold < Integer.parseInt(textFieldESell4.getText()) *tradeValues.get(18))
		{
			storeHorses += storeGold / tradeValues.get(18);
			storeGold = storeGold % tradeValues.get(18); 
		}
		else
		{
			storeHorses += Integer.parseInt(textFieldESell4.getText());
			storeGold -= Integer.parseInt(textFieldESell4.getText()) *tradeValues.get(18);
		}
		
		if(storeGold < Integer.parseInt(textFieldESell5.getText()) *tradeValues.get(19))
		{
			storeCake += storeGold / tradeValues.get(19);
			storeGold = storeGold % tradeValues.get(19); 
		}
		else
		{
			storeCake += Integer.parseInt(textFieldESell5.getText());
			storeGold -= Integer.parseInt(textFieldESell5.getText()) *tradeValues.get(19);
		}
		
		
		if(storeGold < Integer.parseInt(textFieldSSell1.getText()) *tradeValues.get(25))
		{
			storeSand += storeGold / tradeValues.get(25);
			storeGold = storeGold % tradeValues.get(25); 
		}
		else
		{
			storeSand += Integer.parseInt(textFieldSSell1.getText());
			storeGold -= Integer.parseInt(textFieldSSell1.getText()) *tradeValues.get(25);
		}
		
		if(storeGold < Integer.parseInt(textFieldSSell2.getText()) *tradeValues.get(26))
		{
			storePerfume += storeGold / tradeValues.get(26);
			storeGold = storeGold % tradeValues.get(26); 
		}
		else
		{
			storePerfume += Integer.parseInt(textFieldSSell2.getText());
			storeGold -= Integer.parseInt(textFieldSSell2.getText()) *tradeValues.get(26);
		}
		
		if(storeGold < Integer.parseInt(textFieldSSell3.getText()) *tradeValues.get(27))
		{
			storeGoldOre += storeGold / tradeValues.get(27);
			storeGold = storeGold % tradeValues.get(27); 
		}
		else
		{
			storeGoldOre += Integer.parseInt(textFieldSSell3.getText());
			storeGold -= Integer.parseInt(textFieldSSell3.getText()) *tradeValues.get(27);
		}
		
		if(storeGold < Integer.parseInt(textFieldSSell4.getText()) *tradeValues.get(28))
		{
			storeCocoa += storeGold / tradeValues.get(28);
			storeGold = storeGold % tradeValues.get(28); 
		}
		else
		{
			storeCocoa += Integer.parseInt(textFieldSSell4.getText());
			storeGold -= Integer.parseInt(textFieldSSell4.getText()) *tradeValues.get(28);
		}
		
		if(storeGold < Integer.parseInt(textFieldSSell5.getText()) *tradeValues.get(29))
		{
			storeCoffee += storeGold / tradeValues.get(29);
			storeGold = storeGold % tradeValues.get(29); 
		}
		else
		{
			storeCoffee += Integer.parseInt(textFieldSSell5.getText());
			storeGold -= Integer.parseInt(textFieldSSell5.getText()) *tradeValues.get(29);
		}
		
		
		if(storeGold < Integer.parseInt(textFieldNSell1.getText()) *tradeValues.get(35))
		{
			storeCoal += storeGold / tradeValues.get(35);
			storeGold = storeGold % tradeValues.get(35); 
		}
		else
		{
			storeCoal += Integer.parseInt(textFieldNSell1.getText());
			storeGold -= Integer.parseInt(textFieldNSell1.getText()) *tradeValues.get(35);
		}
		
		if(storeGold < Integer.parseInt(textFieldNSell2.getText()) *tradeValues.get(36))
		{
			storeIronOre += storeGold / tradeValues.get(36);
			storeGold = storeGold % tradeValues.get(36); 
		}
		else
		{
			storeIronOre += Integer.parseInt(textFieldNSell2.getText());
			storeGold -= Integer.parseInt(textFieldNSell2.getText()) *tradeValues.get(36);
		}
		
		if(storeGold < Integer.parseInt(textFieldNSell3.getText()) *tradeValues.get(37))
		{
			storeIcewine += storeGold / tradeValues.get(37);
			storeGold = storeGold % tradeValues.get(37); 
		}
		else
		{
			storeIcewine += Integer.parseInt(textFieldNSell3.getText());
			storeGold -= Integer.parseInt(textFieldNSell3.getText()) *tradeValues.get(37);
		}
		
		if(storeGold < Integer.parseInt(textFieldNSell4.getText()) *tradeValues.get(38))
		{
			storeHops += storeGold / tradeValues.get(38);
			storeGold = storeGold % tradeValues.get(38); 
		}
		else
		{
			storeHops += Integer.parseInt(textFieldNSell4.getText());
			storeGold -= Integer.parseInt(textFieldNSell4.getText()) *tradeValues.get(38);
		}
		
		if(storeGold < Integer.parseInt(textFieldNSell5.getText()) *tradeValues.get(39))
		{
			storeSalmon += storeGold / tradeValues.get(39);
			storeGold = storeGold % tradeValues.get(39); 
		}
		else
		{
			storeSalmon += Integer.parseInt(textFieldNSell5.getText());
			storeGold -= Integer.parseInt(textFieldNSell5.getText()) *tradeValues.get(39);
		}
	}
	
	public void updateStorage(ArrayList<Integer> data)
	{
		lblStorageWood.setText(String.valueOf(data.get(0)));
		lblStorageStone.setText(String.valueOf(data.get(1)));
		lblStorageSteel.setText(String.valueOf(data.get(2)));
		lblStorageGlass.setText(String.valueOf(data.get(3)));
		lblStorageSand.setText(String.valueOf(data.get(4)));
		lblStorageWool.setText(String.valueOf(data.get(5)));
		lblStorageLeather.setText(String.valueOf(data.get(6)));
		lblStorageMarshweed.setText(String.valueOf(data.get(7)));
		lblStorageGrain.setText(String.valueOf(data.get(8)));
		lblStorageHops.setText(String.valueOf(data.get(9)));
		lblStorageCocoabeans.setText(String.valueOf(data.get(10)));
		lblStorageRawChocolate.setText(String.valueOf(data.get(11)));
		lblStorageIceflowers.setText(String.valueOf(data.get(12)));
		lblStorageBarrels.setText(String.valueOf(data.get(13)));
		lblStoragePaper.setText(String.valueOf(data.get(14)));
		lblStorageGold.setText(String.valueOf(data.get(15)));
		lblStorageIronOre.setText(String.valueOf(data.get(16)));
		lblStorageGoldOre.setText(String.valueOf(data.get(17)));
		
		lblStorageWater.setText(String.valueOf(data.get(18)));
		lblStorageVegetables.setText(String.valueOf(data.get(19)));
		lblStorageClothes.setText(String.valueOf(data.get(20)));
		lblStorageCoal.setText(String.valueOf(data.get(21)));
		
		lblStorageMilk.setText(String.valueOf(data.get(22)));
		lblStorageMeat.setText(String.valueOf(data.get(23)));
		lblStorageMedicine.setText(String.valueOf(data.get(24)));
		lblStorageShoes.setText(String.valueOf(data.get(25)));
		lblStorageHoney.setText(String.valueOf(data.get(26)));
		lblStorageOrnaments.setText(String.valueOf(data.get(27)));
		
		lblStorageBeer.setText(String.valueOf(data.get(28)));
		lblStorageFruits.setText(String.valueOf(data.get(29)));
		lblStorageHorses.setText(String.valueOf(data.get(30)));
		lblStorageBread.setText(String.valueOf(data.get(31)));
		lblStorageTobacco.setText(String.valueOf(data.get(32)));
		lblStoragePerfume.setText(String.valueOf(data.get(33)));
		lblStorageCheese.setText(String.valueOf(data.get(34)));
		lblStorageCocoa.setText(String.valueOf(data.get(35)));
		
		lblStorageIcewine.setText(String.valueOf(data.get(36)));
		lblStorageCake.setText(String.valueOf(data.get(37)));
		lblStorageDeer.setText(String.valueOf(data.get(38)));
		lblStorageChocolates.setText(String.valueOf(data.get(39)));
		lblStoragePistols.setText(String.valueOf(data.get(40)));
		lblStorageLiquor.setText(String.valueOf(data.get(41)));
		lblStorageBooks.setText(String.valueOf(data.get(42)));
		lblStorageJewelry.setText(String.valueOf(data.get(43)));
		lblStorageSalmon.setText(String.valueOf(data.get(44)));
		lblStorageCoffee.setText(String.valueOf(data.get(45)));
		
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
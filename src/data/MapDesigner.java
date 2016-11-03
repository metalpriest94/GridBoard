package data;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;

import data.GridIO;
import data.Utilities;

import javax.swing.event.ChangeEvent;

public class MapDesigner extends JFrame {

	private JPanel contentPane;
	private JGridPanel panelMap;
	private JButton btnUp;
	private JButton btnRight;
	private JButton btnDown;
	private JButton btnLeft;
	private JPanel panelTools;
	private JLabel lblMaptiles;
	private JLabel lblItems;
	private JLabel lblGlobalInformation;
	private JLabel lblSettings;
	private JLabel lblTools;
	private JLabel lblWidth;
	private JLabel lblHeight;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JButton btnResize;
	private JLabel lblTileSize;
	private JTextField txtTileSize;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JList<String> listItems;
	private JList<String> listTiles;
	private JRadioButton rdbtnPlaceMaptile;
	private JRadioButton rdbtnPlaceItem;
	private JRadioButton rdbtnRemoveItem;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private DefaultListModel<String> modelMapTiles = new DefaultListModel<String>();
	private ArrayList<MapTile> allTiles;
	private DefaultListModel<String> modelItems = new DefaultListModel<String>();
	private ArrayList<Item> allItems;
	private JLabel lblCurrentPos;
	private JRadioButton rdbtnConfigureItem;
	private JTextField txtFileName;
	private JLabel lblFileName;
	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnNew;
	
	//Variables required by load() and readMap(isLastLine)
	String[] lastSplitData, nextSplitData;
	String nextLineContent;
	int lastY, nextY;
	private JCheckBox chckbxShowGrid;
	private JTextField txtJumpX;
	private JLabel lblJumpX;
	private JLabel lblJumpY;
	private JTextField txtJumpY;
	private JButton btnJump;
	private GridIO gio; 
	private JLabel lblItemModifier1;
	private JTextField txtItemModifier1;
	private JButton btnApplyAsStandard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//JOptionPane.showMessageDialog(null, "Attention!" + '\n' + "The MapDesigner does not provide an optimized user interface and " + '\n' + "therefore may behave unexpected and cause damage to savegames and crucial game files." + '\n' + '\n' + "By using MapDesigner you agree that you are solely responsible for any damage done to your " + '\n' + "files as well your system by this software.");
					MapDesigner frame = new MapDesigner();
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
	public MapDesigner() {
		setTitle("MapDesigner");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 623);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][][]", "[][grow][]"));
		
		panelMap = new JGridPanel(4,4,16);
		panelMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int startX = panelMap.getCornerDragX() / panelMap.getTileSize() + panelMap.getVisibleCornerX() -1;
				int startY = panelMap.getCornerDragY() / panelMap.getTileSize() + panelMap.getVisibleCornerY() -1;
				
					if(panelMap.isDragged())
					{
						for(int x = startX; x < startX + panelMap.getDraggedTilesX(); x++)
						{
							for(int y = startY; y < startY + panelMap.getDraggedTilesY(); y++)
							{
								if(rdbtnPlaceMaptile.isSelected())
									assignTile(x, y);
								else if(rdbtnPlaceItem.isSelected() && (panelMap.getMapping()[x][y][2] == 1))
									assignItem(x, y, true);
								else if(rdbtnRemoveItem.isSelected())
									assignItem(x, y, false);
							}
						}
						panelMap.setDragged(false);
					}
					else
					{
						if(rdbtnPlaceMaptile.isSelected())
							assignTile(panelMap.getCurrentX(), panelMap.getCurrentY());
						else if(rdbtnPlaceItem.isSelected() && (panelMap.getMapping()[panelMap.getCurrentX()][panelMap.getCurrentY()][2] == 1))
							assignItem(panelMap.getCurrentX(), panelMap.getCurrentY(), true);
						else if(rdbtnRemoveItem.isSelected())
							assignItem(panelMap.getCurrentX(), panelMap.getCurrentY(), false);
					}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				panelMap.setStartDragX(panelMap.getPosX());
				panelMap.setStartDragY(panelMap.getPosY()); 
			}
		});
		panelMap.setBackground(new Color(255, 255, 255));
		panelMap.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				panelMap.setDragged(false);
				panelMap.setPosX(e.getX() / panelMap.getTileSize() * panelMap.getTileSize() );
				panelMap.setPosY(e.getY() / panelMap.getTileSize() * panelMap.getTileSize() );
				lblCurrentPos.setText("X: " + String.valueOf(panelMap.getCurrentX() + 1 + " | Y: " + String.valueOf(panelMap.getCurrentY() + 1)));
				repaint();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				panelMap.setDragged(true);
				panelMap.setPosX(e.getX() / panelMap.getTileSize() * panelMap.getTileSize() );
				panelMap.setPosY(e.getY() / panelMap.getTileSize() * panelMap.getTileSize() );
				lblCurrentPos.setText("X: " + String.valueOf(panelMap.getCurrentX() + 1 + " | Y: " + String.valueOf(panelMap.getCurrentY() + 1)));
				repaint();
			}
		});
		panelMap.setShowGrid(true);
		
		btnUp = new JButton("^");
		btnUp.setBackground(UIManager.getColor("Button.background"));
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMap.setVisibleCornerY(panelMap.getVisibleCornerY() - 1);
				btnDown.setEnabled(true);
				if(panelMap.getVisibleCornerY() == 1)
					btnUp.setEnabled(false);
				repaint();
			}
		});
		contentPane.add(btnUp, "cell 1 0,growx");
		
		btnLeft = new JButton("<");
		btnLeft.setBackground(UIManager.getColor("Button.background"));
		btnLeft.setEnabled(false);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMap.setVisibleCornerX(panelMap.getVisibleCornerX() - 1);
				btnRight.setEnabled(true);
				if(panelMap.getVisibleCornerX() == 1)
					btnLeft.setEnabled(false);
				repaint();
			}
		});
		
		lblCurrentPos = new JLabel("X: 1 | Y: 1");
		contentPane.add(lblCurrentPos, "cell 3 0");
		contentPane.add(btnLeft, "cell 0 1,growy");
		contentPane.add(panelMap, "cell 1 1,grow");
		
		btnRight = new JButton(">");
		btnRight.setBackground(UIManager.getColor("Button.background"));
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMap.setVisibleCornerX(panelMap.getVisibleCornerX() + 1);
				btnLeft.setEnabled(true);
				if(panelMap.getVisibleCornerX() >= panelMap.getTilesX())
					btnRight.setEnabled(false);
				repaint();
			}
		});
		contentPane.add(btnRight, "cell 2 1,growy");
		
		btnDown = new JButton("v");
		btnDown.setBackground(UIManager.getColor("Button.background"));
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMap.setVisibleCornerY(panelMap.getVisibleCornerY() + 1);
				btnUp.setEnabled(true);
				if(panelMap.getVisibleCornerY() == panelMap.getTilesY())
					btnDown.setEnabled(false);
				repaint();
			}
		});
		
		panelTools = new JPanel();
		panelTools.setBackground(new Color(153, 204, 204));
		contentPane.add(panelTools, "cell 3 1,grow");
		panelTools.setLayout(new MigLayout("", "[][85.00][-14.00][54.00,grow][][119.00][][grow]", "[][][][][20.00][20.00][][][][][][]"));
		
		lblGlobalInformation = new JLabel("Global Information");
		lblGlobalInformation.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelTools.add(lblGlobalInformation, "cell 0 0 2 1");
		
		lblTools = new JLabel("Tools");
		lblTools.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelTools.add(lblTools, "cell 2 0 2 1");
		
		lblMaptiles = new JLabel("MapTiles");
		lblMaptiles.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelTools.add(lblMaptiles, "cell 4 0 2 1");
		
		lblSettings = new JLabel("Editor Settings");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelTools.add(lblSettings, "cell 6 0 2 1");
		
		lblFileName = new JLabel("File");
		panelTools.add(lblFileName, "cell 0 1,alignx trailing");
		
		txtFileName = new JTextField();
		panelTools.add(txtFileName, "cell 1 1,growx");
		txtFileName.setColumns(10);
		
		rdbtnPlaceMaptile = new JRadioButton("Place MapTile");
		rdbtnPlaceMaptile.setSelected(true);
		rdbtnPlaceMaptile.setBackground(new Color(153, 204, 204));
		buttonGroup.add(rdbtnPlaceMaptile);
		panelTools.add(rdbtnPlaceMaptile, "cell 2 1 2 1");
		
		scrollPane_1 = new JScrollPane();
		panelTools.add(scrollPane_1, "cell 4 1 2 4,grow");
		
		listTiles = new JList<String>();
		listTiles.setVisibleRowCount(6);
		listTiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTiles.setModel(modelMapTiles);
		scrollPane_1.setViewportView(listTiles);
		
		lblTileSize = new JLabel("Tile Size");
		panelTools.add(lblTileSize, "cell 6 1,alignx trailing");
		
		txtTileSize = new JTextField();
		txtTileSize.setText("100");
		txtTileSize.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				try
				{
					panelMap.setTileSize(Integer.parseInt(txtTileSize.getText()));
				}
				catch(NumberFormatException ex)
				{
					panelMap.setTileSize(1);
				}
				repaint();
			}
		});
		panelTools.add(txtTileSize, "cell 7 1");
		txtTileSize.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gio.save(txtFileName.getText(), false);
			}
		});
		panelTools.add(btnSave, "cell 1 2,growx");
		
		rdbtnPlaceItem = new JRadioButton("Place Item");
		rdbtnPlaceItem.setBackground(new Color(153, 204, 204));
		buttonGroup.add(rdbtnPlaceItem);
		panelTools.add(rdbtnPlaceItem, "cell 2 2 2 1");
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gio.load(txtFileName.getText(),false);
			}
		});
		
		chckbxShowGrid = new JCheckBox("Show Grid");
		chckbxShowGrid.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelMap.setShowGrid(chckbxShowGrid.isSelected());
				repaint();
			}
		});
		chckbxShowGrid.setBackground(new Color(153, 204, 204));
		chckbxShowGrid.setSelected(true);
		panelTools.add(chckbxShowGrid, "cell 7 2");
		panelTools.add(btnLoad, "cell 1 3,growx");
		
		rdbtnRemoveItem = new JRadioButton("Remove Item");
		rdbtnRemoveItem.setBackground(new Color(153, 204, 204));
		buttonGroup.add(rdbtnRemoveItem);
		panelTools.add(rdbtnRemoveItem, "cell 2 3 2 1");
		
		btnApplyAsStandard = new JButton("Apply as Standard");
		btnApplyAsStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedWriter writeConfig = null;
				try
				{
					writeConfig = new BufferedWriter(new FileWriter(new File("src" + File.separator + "config" + File.separator + "editor.config")));
					writeConfig.write(txtTileSize.getText());
					writeConfig.newLine();
					writeConfig.write(String.valueOf(chckbxShowGrid.isSelected()));
				}
				catch(IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Error writing config-File");
				}
				finally
				{
					try
					{
						if (writeConfig != null)
							writeConfig.close();
					}
					catch (IOException ex) 
					{
						JOptionPane.showMessageDialog(null, "Error writing config-File");
					}
				}
			}
		});
		panelTools.add(btnApplyAsStandard, "cell 6 3 2 1,growx");
		
		lblWidth = new JLabel("Width");
		panelTools.add(lblWidth, "cell 0 4,alignx left");
		
		txtWidth = new JTextField();
		txtWidth.setText("4");
		panelTools.add(txtWidth, "cell 1 4");
		txtWidth.setColumns(10);
		
		rdbtnConfigureItem = new JRadioButton("Configure Item");
		rdbtnConfigureItem.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (rdbtnConfigureItem.isSelected())
				{
					lblItemModifier1.setVisible(true);
					lblItemModifier1.setText("Info");
					txtItemModifier1.setVisible(true);
				}
				else
				{
					lblItemModifier1.setVisible(false);
					txtItemModifier1.setVisible(false);
				}
			}
		});
		buttonGroup.add(rdbtnConfigureItem);
		rdbtnConfigureItem.setBackground(new Color(153, 204, 204));
		panelTools.add(rdbtnConfigureItem, "cell 2 4 2 1");
		
		lblHeight = new JLabel("Height");
		panelTools.add(lblHeight, "cell 0 5,alignx left");
		
		txtHeight = new JTextField();
		txtHeight.setText("4");
		txtHeight.setColumns(10);
		panelTools.add(txtHeight, "cell 1 5");
		
		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gio.newMap(Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
			}
		});
		panelTools.add(btnNew, "cell 1 6,growx");
		
		txtJumpX = new JTextField();
		txtJumpX.setText("1");
		panelTools.add(txtJumpX, "cell 3 6,growx");
		txtJumpX.setColumns(10);
		
		btnResize = new JButton("Resize");
		btnResize.setBackground(UIManager.getColor("Button.background"));
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gio.resize(Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
				repaint();
			}
		});
		
		lblItems = new JLabel("Items");
		lblItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelTools.add(lblItems, "cell 4 6 2 1,aligny bottom");
		panelTools.add(btnResize, "cell 1 7,growx");
		
		lblJumpX = new JLabel("X");
		panelTools.add(lblJumpX, "cell 2 6");
		
		lblJumpY = new JLabel("Y");
		panelTools.add(lblJumpY, "cell 2 7,alignx trailing");
		
		txtJumpY = new JTextField();
		txtJumpY.setText("1");
		panelTools.add(txtJumpY, "cell 3 7,growx");
		txtJumpY.setColumns(10);
		
		scrollPane = new JScrollPane();
		panelTools.add(scrollPane, "cell 4 7 2 4,grow");
		
		listItems = new JList<String>();
		listItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listItems.setModel(modelItems);
		scrollPane.setViewportView(listItems);
		
		btnJump = new JButton("Jump");
		btnJump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					panelMap.setVisibleCornerX(Integer.parseInt(txtJumpX.getText()));
					panelMap.setVisibleCornerY(Integer.parseInt(txtJumpY.getText()));
					if (panelMap.getVisibleCornerX() > 1)
						btnLeft.setEnabled(true);
					else
						btnLeft.setEnabled(false);
					
					if (panelMap.getVisibleCornerX() < panelMap.getTilesX())
						btnRight.setEnabled(true);
					else
						btnRight.setEnabled(false);
					
					
					if (panelMap.getVisibleCornerY() > 1)
						btnUp.setEnabled(true);
					else 
						btnUp.setEnabled(false);
					
					if (panelMap.getVisibleCornerY() < panelMap.getTilesY())
						btnDown.setEnabled(true);
					else 
						btnDown.setEnabled(false);
					repaint();					
				}
				catch(NumberFormatException ex)
				{
					panelMap.setVisibleCornerX(1);
					panelMap.setVisibleCornerY(1);
					txtJumpX.setText("1");
					txtJumpY.setText("1");
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					panelMap.setVisibleCornerX(1);
					panelMap.setVisibleCornerY(1);
					txtJumpX.setText("1");
					txtJumpY.setText("1");
				}
				
			}
		});
		panelTools.add(btnJump, "cell 3 8,growx");
		
		lblItemModifier1 = new JLabel("Info");
		panelTools.add(lblItemModifier1, "cell 4 11");
		
		txtItemModifier1 = new JTextField();
		panelTools.add(txtItemModifier1, "cell 5 11,growx");
		txtItemModifier1.setColumns(10);
		contentPane.add(btnDown, "cell 1 2,growx");
		
		lblItemModifier1.setVisible(false);
		txtItemModifier1.setVisible(false);
		
		readConfig();
		gio = new GridIO(panelMap, allTiles, allItems);
		allTiles = gio.createTileList();
		allItems = gio.createItemList(false);
		for(MapTile each: allTiles)
		{
			modelMapTiles.addElement(each.getName());
		}
		
		for(Item each: allItems)
		{
			modelItems.addElement(each.getName());
		}
		
		
	}
	
	public void readConfig()
	{
		BufferedReader readConfig = null;
		ArrayList<String> allLines = new ArrayList<String>();
		String line;
		
		try
		{
			readConfig = new BufferedReader(new FileReader(new File("src" + File.separator + "config" + File.separator + "editor.config")));
			while ((line=readConfig.readLine()) != null)
			{
				allLines.add(line);
			}
			txtTileSize.setText(allLines.get(0));
			chckbxShowGrid.setSelected(Boolean.parseBoolean(allLines.get(1)));
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "editor.config is corrupted or does not exist.");
		}
		finally
		{
			try
			{
				if (readConfig != null)
					readConfig.close();
				System.out.println("config");
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(null, "editor.config is corrupted or does not exist.");
			}
		}
		
	}
	
	
	public void assignTile(int x, int y)
	{
		for(MapTile each:allTiles)
		{
			try
			{
				if (listTiles.getSelectedValue().equals(each.getName()))
				{
					panelMap.applyProperty(x, y, 0, each.getBackground());
					panelMap.applyProperty(x, y, 1, Integer.parseInt(each.getID()));
					panelMap.applyProperty(x, y, 2, Utilities.boolToInt(each.isAccessable()));
					panelMap.applyProperty(x, y, 3, Utilities.boolToInt(each.canCarryItem()));
					panelMap.applyTileImage(x, y, each.getImage());
				}
			}
			catch (NullPointerException ex)
			{
				panelMap.applyProperty(x, y, 0, 0);
			}
		}
	}
	
	public void assignItem(int x, int y, boolean place)
	{
		if(place)
		{
			for(Item each:allItems)
			{
				try
				{
					if (listItems.getSelectedValue().equals(each.getName()))
					{
						panelMap.applyProperty(x, y, 4, Integer.parseInt(each.getID()));
						panelMap.applyProperty(x, y, 5, each.getPurpose());
						panelMap.applyProperty(x, y, 6, Utilities.boolToInt(each.isConstructable()));
						panelMap.applyProperty(x, y, 7, each.getEmployee());
						panelMap.applyProperty(x, y, 8, Utilities.boolToInt(each.hasEmployee()));
						panelMap.applyProperty(x, y, 9, each.getCapacity());
						panelMap.applyProperty(x, y, 10, each.getUsedCapacity());
						panelMap.applyProperty(x, y, 11, each.getStore1());
						panelMap.applyProperty(x, y, 12, each.getStore2());
						panelMap.applyProperty(x, y, 13, each.getStore3());
						panelMap.applyProperty(x, y, 14, each.getStoreSideProduct());
						panelMap.applyItemImage(x, y, each.getImage());
					}
				}
				catch (NullPointerException ex)
				{
					panelMap.applyProperty(x, y, 4, 0);
				}
			}
		}
		else
		{
			try
			{
				for (int i = 4; i <= 15; i++)
				{
					panelMap.applyProperty(x, y, i, 0);
				}
				panelMap.applyItemImage(x, y, null);

			}
			catch (NullPointerException ex)
			{
				panelMap.applyProperty(x, y, 4, 0);
			}
		}
			
	}
}

package data;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	
	private final Font menuFont = new Font("Tahoma", Font.BOLD, 30);
	private DefaultListModel<String> modelSavs = new DefaultListModel<String>();
	private DefaultListModel<String> modelMaps = new DefaultListModel<String>();
	
	private CardLayout menuCards;
	private final String cardMain = "main";
	private final String cardNew = "new";
	private final String cardLoad = "load";
	private final String cardLoadingScreen = "loading";
	private JList<String> listSavs;
	private JList<String> listMaps;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		menuCards = ((CardLayout)contentPane.getLayout());
		
		JPanel panelMain =  new JPanel();
		panelMain.setBackground(new Color(153, 204, 204));
		contentPane.add(panelMain, cardMain);
		panelMain.setLayout(new MigLayout("", "[117px,grow]", "[45px,grow][96px:n:96px][96px:n:96px][96px:n:96px][96px:n:96px]"));
		
		JLabel lblLoad = new JLabel("Space for upcoming logo");
		panelMain.add(lblLoad, "cell 0 0,alignx center,aligny top");
		
		JButton btnNewGame = new JButton("New Game");
		panelMain.add(btnNewGame, "cell 0 1,grow");
		btnNewGame.setFont(menuFont);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardNew);
			}
		});
		
		JButton btnLoadGame = new JButton("Load Game");
		panelMain.add(btnLoadGame, "cell 0 2,grow");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardLoad);
			}
		});
		btnLoadGame.setFont(menuFont);
		
		JButton btnOptions = new JButton("Options (coming soon)");
		panelMain.add(btnOptions, "cell 0 3,grow");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOptions.setFont(menuFont);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(menuFont);
		panelMain.add(btnExit, "cell 0 4,grow");

		
		JPanel panelLoad = new JPanel();
		panelLoad.setBackground(new Color(153, 204, 204));
		contentPane.add(panelLoad, cardLoad);
		panelLoad.setLayout(new MigLayout("", "[][117px,grow][]", "[96px:n:96px][45px,grow]"));
		
		JButton btnBackLoad = new JButton("<--");
		btnBackLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardMain);
			}
			
		});
		btnBackLoad.setFont(menuFont);
		panelLoad.add(btnBackLoad, "cell 0 0,grow");
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setEnabled(false);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardLoadingScreen);
				load();
			}
		});
		btnLoad.setFont(menuFont);
		panelLoad.add(btnLoad, "cell 1 0,grow");
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.setFont(menuFont);
		panelLoad.add(btnDelete, "cell 2 0,grow");
		
		JScrollPane scrollPaneLoad = new JScrollPane();
		panelLoad.add(scrollPaneLoad, "cell 0 1 3 1,grow");
		
		listSavs = new JList<String>();
		listSavs.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnLoad.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});
		listSavs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSavs.setFont(menuFont);
		scrollPaneLoad.setViewportView(listSavs);
		listSavs.setModel(modelSavs);
		
		JPanel panelLoadingScreen = new JPanel();
		panelLoadingScreen.setBackground(new Color(0, 0, 0));
		contentPane.add(panelLoadingScreen, cardLoadingScreen);
		panelLoadingScreen.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JLabel lblLoadingScreen = new JLabel("Loading. Please wait...");
		lblLoadingScreen.setFont(menuFont);
		lblLoadingScreen.setForeground(new Color(255, 255, 255));
		panelLoadingScreen.add(lblLoadingScreen, "cell 0 0,alignx center,aligny center");
		
		JPanel panelNew = new JPanel();
		panelNew.setBackground(new Color(153, 204, 204));
		contentPane.add(panelNew, cardNew);
		panelNew.setLayout(new MigLayout("", "[][grow,fill]", "[96px:n:96px][grow]"));
		
		JButton btnBackNew = new JButton("<--");
		btnBackNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardMain);
			}
		});
		btnBackNew.setFont(menuFont);
		panelNew.add(btnBackNew, "cell 0 0,grow");
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardLoadingScreen);
				newGame();
			}
		});
		btnStart.setFont(menuFont);
		panelNew.add(btnStart, "cell 1 0,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		panelNew.add(scrollPane, "cell 0 1 2 1,grow");
		
		listMaps = new JList();
		listMaps.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listMaps.setFont(menuFont);
		scrollPane.setViewportView(listMaps);
		listMaps.setModel(modelMaps);
		
		findSavs();
		findMaps();
	}
	
	public void findSavs()
	{
		String[] files;
		File folderRead = new File("src" + File.separator +  "savegames"); 	
		{
			
			if(folderRead.exists() && folderRead.isDirectory())
			{
				files = folderRead.list();
				
				for (String name: files)
				{
					if(name.split("\\.")[1].equals("sav"))
						modelSavs.addElement(name.split("\\.")[0]);
				}
			}
		}
	}
	
	public void findMaps()
	{
		String[] files;
		File folderRead = new File("src" + File.separator +  "maps"); 	
		{
			
			if(folderRead.exists() && folderRead.isDirectory())
			{
				files = folderRead.list();
				
				for (String name: files)
				{
					if(name.split("\\.")[1].equals("map"))
						modelMaps.addElement(name.split("\\.")[0]);
				}
			}
		}
	}
	
	public void newGame()
	{
		Game.main(true, listMaps.getSelectedValue(), this);
	}
	
	public void load()
	{
		Game.main(false, listSavs.getSelectedValue().toString(), this);
	}
}

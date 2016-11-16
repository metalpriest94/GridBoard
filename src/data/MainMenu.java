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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	
	private final String basicFont = "Tahoma";
	private final Font menuFont = new Font(basicFont, Font.BOLD, 30);
	private final Font subFont = new Font(basicFont, Font.BOLD, 24);
	private final Font optionFont = new Font(basicFont, Font.PLAIN, 24);;
	private final Color basicBackground = new Color(153, 204, 204);
	
	private DefaultListModel<String> modelSavs = new DefaultListModel<String>();
	private DefaultListModel<String> modelMaps = new DefaultListModel<String>();
	
	private CardLayout menuCards;
	private final String cardMain = "main";
	private final String cardNew = "new";
	private final String cardLoad = "load";
	private final String cardLoadingScreen = "loading";
	private final String cardSettings = "settings";
	private JList<String> listSavs;
	private JList<String> listMaps;
	private final ButtonGroup radioButtonsWindowMode = new ButtonGroup();
	private final ButtonGroup radioButtonsMusic = new ButtonGroup();
	private JRadioButton rdbtnFullscreen;
	private JRadioButton rdbtnWindow;
	private JRadioButton rdbtnMusicOn;
	private JRadioButton rdbtnMusicOff;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		menuCards = ((CardLayout)contentPane.getLayout());
		
		JPanel panelMain =  new JPanel();
		panelMain.setBackground(basicBackground);
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
		
		JButton btnSettings = new JButton("Settings");
		panelMain.add(btnSettings, "cell 0 3,grow");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardSettings);
			}
		});
		btnSettings.setFont(menuFont);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(menuFont);
		panelMain.add(btnExit, "cell 0 4,grow");

		
		JPanel panelLoad = new JPanel();
		panelLoad.setBackground(basicBackground);
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
		panelNew.setBackground(basicBackground);
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
		btnStart.setEnabled(false);
		panelNew.add(btnStart, "cell 1 0,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		panelNew.add(scrollPane, "cell 0 1 2 1,grow");
		
		listMaps = new JList();
		listMaps.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnStart.setEnabled(true);
			}
		});
		listMaps.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listMaps.setFont(menuFont);
		scrollPane.setViewportView(listMaps);
		listMaps.setModel(modelMaps);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBackground(basicBackground);
		contentPane.add(panelSettings, cardSettings);
		panelSettings.setLayout(new MigLayout("", "[][24px:n:24px,fill][120px:n:120px][120px:n:120px,grow][120px:n:120px][120px:n:120px][120px:n:120px][grow]", "[96px:n:96px][][][][][]"));
		
		JButton btnBackSettings = new JButton("<--");
		btnBackSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuCards.show(contentPane, cardMain);
				readSettings();
			}
		});
		btnBackSettings.setFont(menuFont);
		panelSettings.add(btnBackSettings, "cell 0 0,growy");
		
		JButton btnApplySettings = new JButton("Apply Settings");
		btnApplySettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applySettings();
			}
		});
		btnApplySettings.setFont(menuFont);
		panelSettings.add(btnApplySettings, "cell 1 0 7 1,growy");
		
		JLabel lblVideo = new JLabel("Video");
		lblVideo.setFont(menuFont);
		panelSettings.add(lblVideo, "cell 0 1 3 1");
		
		JLabel lblAudio = new JLabel("Audio");
		lblAudio.setFont(menuFont);
		panelSettings.add(lblAudio, "cell 3 1 2 1");
		
		JLabel lblGameplay = new JLabel("Gameplay");
		lblGameplay.setFont(menuFont);
		panelSettings.add(lblGameplay, "cell 5 1 2 1");
		
		JLabel lblWindowMode = new JLabel("Window Mode");
		lblWindowMode.setFont(subFont);
		panelSettings.add(lblWindowMode, "cell 0 2 3 1");
		
		JLabel lblMusic = new JLabel("Music");
		lblMusic.setFont(subFont);
		panelSettings.add(lblMusic, "cell 3 2 2 1");
		
		rdbtnFullscreen = new JRadioButton("Fullscreen");
		rdbtnFullscreen.setSelected(true);
		rdbtnFullscreen.setBackground(basicBackground);
		rdbtnFullscreen.setFont(optionFont);
		radioButtonsWindowMode.add(rdbtnFullscreen);
		panelSettings.add(rdbtnFullscreen, "cell 0 3 3 1");
		
		rdbtnMusicOn = new JRadioButton("On");
		rdbtnMusicOn.setSelected(true);
		rdbtnMusicOn.setBackground(basicBackground);
		radioButtonsMusic.add(rdbtnMusicOn);
		rdbtnMusicOn.setFont(optionFont);
		panelSettings.add(rdbtnMusicOn, "cell 3 3 2 1");
		
		rdbtnWindow = new JRadioButton("Window");
		rdbtnWindow.setBackground(basicBackground);
		rdbtnWindow.setFont(optionFont);
		radioButtonsWindowMode.add(rdbtnWindow);
		panelSettings.add(rdbtnWindow, "cell 0 4 3 1");
		
		rdbtnMusicOff = new JRadioButton("Off");
		rdbtnMusicOff.setBackground(basicBackground);
		radioButtonsMusic.add(rdbtnMusicOff);
		rdbtnMusicOff.setFont(optionFont);
		panelSettings.add(rdbtnMusicOff, "cell 3 4 2 1");
		
		JLabel lblMoreComingSoon = new JLabel("more coming soon...");
		panelSettings.add(lblMoreComingSoon, "cell 0 5 3 1");
		
		readSettings();
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
	
	public void applySettings()
	{
		BufferedWriter configWrite = null;
		try
		{
			configWrite = new BufferedWriter(new FileWriter("src" + File.separator + "config" + File.separator + "settings.config"));
			{//VideoSettings
				if (rdbtnFullscreen.isSelected())
					setExtendedState(MAXIMIZED_BOTH);
				else
					setExtendedState(NORMAL);
				configWrite.write(String.valueOf(rdbtnFullscreen.isSelected()));
				configWrite.newLine();
			}
			
			{//AudioSettings
				configWrite.write(String.valueOf(rdbtnMusicOn.isSelected()));
			}
			
			{//GameplaySettings
				
			}
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null, "Critical Exception: Cannot save settings!");
		}
		finally
		{
			try
			{
				if (configWrite != null)
				{
					configWrite.close();
				}
			}
			catch(IOException ex)
			{
				JOptionPane.showMessageDialog(null, "Critical Exception: Cannot save settings!");
			}
		}	
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
				{
					setExtendedState(MAXIMIZED_BOTH);
					rdbtnFullscreen.setSelected(true);
				}
				else
				{
					setExtendedState(NORMAL);
					rdbtnWindow.setSelected(true);
				}
			}
			
			{//AudioSettings
				data = configRead.readLine();
				if (Boolean.parseBoolean(data))
				{
					rdbtnMusicOn.setSelected(true);
				}
				else
				{
					rdbtnMusicOff.setSelected(true);
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

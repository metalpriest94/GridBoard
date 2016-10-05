package data;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseMotionAdapter;
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
import java.awt.Toolkit;

public class CrazyColours extends JFrame {

	private JPanel contentPane;
	private JGridPanel panel;
	private JTextField txtStart;
	private JTextField txtMod1;
	private JTextField txtMod2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrazyColours frame = new CrazyColours();
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
	public CrazyColours() {
		setTitle("Crazy Colours");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrazyColours.class.getResource("/grid/colours.jpg")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][][][]", "[][][grow]"));
		
		panel = new JGridPanel(256,256,1);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(panel.getCurrentX() +" " + panel.getCurrentY());
			}
		});
		//panel.setTilesX(32);
		//panel.setTilesY(18);
		panel.setBackground(new Color(255, 255, 255));
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				panel.setPosX(e.getX() / panel.getTileSize() * panel.getTileSize());
				panel.setPosY(e.getY() / panel.getTileSize() * panel.getTileSize());
				repaint();
			}
		});
		
		JButton btnRendern = new JButton("Rendern");
		btnRendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setColors(Integer.parseInt(txtStart.getText()), Integer.parseInt(txtMod1.getText()), Integer.parseInt(txtMod2.getText()));
			}
		});
		contentPane.add(btnRendern, "cell 0 0 1 2,grow");
		
		JLabel lblStart = new JLabel("Startwert");
		contentPane.add(lblStart, "cell 1 0");
		
		JLabel lblModifier1 = new JLabel("Modifikator 1");
		contentPane.add(lblModifier1, "cell 2 0");
		
		JLabel lblModifier2 = new JLabel("Modifikator 2");
		contentPane.add(lblModifier2, "cell 3 0");
		
		txtStart = new JTextField();
		txtStart.setText("0");
		contentPane.add(txtStart, "cell 1 1");
		txtStart.setColumns(10);
		
		txtMod1 = new JTextField();
		txtMod1.setText("0");
		contentPane.add(txtMod1, "cell 2 1");
		txtMod1.setColumns(10);
		
		txtMod2 = new JTextField();
		txtMod2.setText("0");
		contentPane.add(txtMod2, "cell 3 1");
		txtMod2.setColumns(10);
		contentPane.add(panel, "cell 0 2 4 1,grow");
		
		txtStart.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				setColors(Integer.parseInt(txtStart.getText()), Integer.parseInt(txtMod1.getText()), Integer.parseInt(txtMod2.getText()));
			}
		});
		txtMod1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				setColors(Integer.parseInt(txtStart.getText()), Integer.parseInt(txtMod1.getText()), Integer.parseInt(txtMod2.getText()));
			}
		});
		txtMod2.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				setColors(Integer.parseInt(txtStart.getText()), Integer.parseInt(txtMod1.getText()), Integer.parseInt(txtMod2.getText()));
			}
		});
		
		panel.setTileSize(1);
	}
	public void setColors(int start, int mod1, int mod2)
	{
		int[][] farben = new int[panel.getTilesX()][panel.getTilesY()];
		int c = start;
		for(int x = 0; x < farben.length; x++)
		{
			for(int y = 0; y < farben[0].length; y++)
			{
				farben[x][y] = c;
				c += mod1;
			}
			c += mod2;
		}
		panel.applyProperty(farben, 0);
	}
}

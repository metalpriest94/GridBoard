package data;

import javax.swing.JOptionPane;

public class PanelAnimator implements Runnable {
	private JGridPanel panel;
	private int refresh;
	
	public PanelAnimator(JGridPanel panel, int refreshRate) {
		this.panel = panel;
		this.refresh = refreshRate;
	}
	@Override
	public void run() {
		while(true)
		{
			synchronized(panel)
			{
				panel.repaint();
			}
			try
			{
				Thread.sleep(1000 / refresh);
			}
			catch(InterruptedException ex)
			{
				JOptionPane.showMessageDialog(null, "Critical RuntimeException occured.");
			}
		}
	}

}

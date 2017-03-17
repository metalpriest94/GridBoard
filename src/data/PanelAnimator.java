package data;

import javax.swing.JOptionPane;

public class PanelAnimator implements Runnable {
	private JGridPanel panel;
	private int refresh;
	
	private boolean askedToSuspend = false;
	
	public PanelAnimator(JGridPanel panel, int refreshRate) {
		this.panel = panel;
		this.refresh = refreshRate;
	}
	
	public void askToSuspend()
	{
		askedToSuspend = true;
	}
	
	@Override
	public void run() {
		while(!askedToSuspend)
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
				break;
			}
		}
	}

}

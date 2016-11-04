package data;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Clockwork implements Runnable {
	private final int HOUR_DURATION = 30; //in seconds
	private final int INTERVAL = 15;
	private JLabel labelH, labelM;
	private int hours, minutes;
	private StringBuilder strHours, strMinutes;
	
	
	public Clockwork(JLabel hours, JLabel minutes)
	{
		this.labelH = hours;
		this.labelM = minutes;
	}
	
	public void setClock(int hours, int minutes)
	{
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public void forward(int minutes)
	{
		this.minutes += minutes;
		while (this.minutes >= 60)
		{
			this.minutes -= 60;
			this.hours += 1;
		}
		while(this.hours >= 24)
			this.hours -= 24;
	}
	
	public void displayTime()
	{
		strHours = new StringBuilder(String.valueOf(this.hours));
		strMinutes = new StringBuilder(String.valueOf(this.minutes));
		if (strHours.length() == 1)
			strHours.insert(0, 0);
		if (strMinutes.length() == 1)
			strMinutes.insert(0, 0);
		labelH.setText(strHours.toString());
		labelM.setText(strMinutes.toString());
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			try
			{
				Thread.sleep(100 * HOUR_DURATION / (60 / INTERVAL));
			}
			catch(InterruptedException ex)
			{
				JOptionPane.showMessageDialog(null, "Critical RuntimeException occured.");
			}
			synchronized (this) 
			{
				forward(INTERVAL);
				displayTime();
			}
		}
	}
}

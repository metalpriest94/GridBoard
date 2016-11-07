package data;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Clockwork implements Runnable {
	private final int HOUR_DURATION = 30; //in seconds
	private final int INTERVAL = 15;
	private final int ticksPerSec = 5;
	private int speed = 1;
	private JLabel labelD, labelH, labelM;
	private int days = 1, hours, minutes;
	private StringBuilder strDays, strHours, strMinutes;
	private int continousTime = 0;
	private Game base;
	
	
	public Clockwork(JLabel days, JLabel hours, JLabel minutes, Game caller)
	{
		this.labelD = days;
		this.labelH = hours;
		this.labelM = minutes;
		this.base = caller;
	}
	
	public int getContinousTime() {
		return continousTime;
	}

	public void setContinousTime(int continousTime) {
		this.continousTime = continousTime;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setClock(int days, int hours, int minutes)
	{
		this.days = days;
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
		{
			this.hours -= 24;
			this.days += 1;
		}
	}
	
	public void displayTime()
	{
		strDays = new StringBuilder("D. " + String.valueOf(this.days));
		strHours = new StringBuilder(String.valueOf(this.hours));
		strMinutes = new StringBuilder(String.valueOf(this.minutes));
		if (strHours.length() == 1)
			strHours.insert(0, 0);
		if (strMinutes.length() == 1)
			strMinutes.insert(0, 0);
		labelD.setText(strDays.toString());
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
				for (int i = 0; i < ticksPerSec; i++)
				{
					Thread.sleep(((1000/ speed) / ticksPerSec) * HOUR_DURATION / (60 / INTERVAL));
					synchronized (this) 
					{
						forward(INTERVAL / ticksPerSec);
						continousTime++;
					}
					base.newTick();
				}
			}
			catch(InterruptedException ex)
			{
				break;
			}
			displayTime();
		}
	}
}

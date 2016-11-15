package data;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Clockwork implements Runnable {
	private final int HOUR_DURATION = 30; //in seconds
	private final int INTERVAL = 15;
	private final int TICKS_PER_SEC = 5;
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
		return this.continousTime;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getTICKSPERSEC() {
		return TICKS_PER_SEC;
	}

	public int getINTERVAL() {
		return INTERVAL;
	}

	public void setClock(int continousTime)
	{
		this.continousTime = continousTime;		
		this.days = continousTime    / (TICKS_PER_SEC * (60/INTERVAL) * 24) + 1;
		this.hours = (continousTime   % (TICKS_PER_SEC * (60/INTERVAL) * 24)) / (TICKS_PER_SEC * (60/INTERVAL));
		this.minutes = continousTime % (TICKS_PER_SEC * (60/INTERVAL)) / TICKS_PER_SEC * INTERVAL;
		displayTime();
	}
	
	public int[] getClock()
	{
		int[] time = new int[]{this.days, this.hours, this. minutes};
		return time;
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
		strDays = new StringBuilder("Day " + String.valueOf(this.days));
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
				for (int i = 0; i < TICKS_PER_SEC; i++)
				{
					Thread.sleep(((1000/ speed) / TICKS_PER_SEC) * HOUR_DURATION / (60 / INTERVAL));
					synchronized (this) 
					{
						forward(INTERVAL / TICKS_PER_SEC);
						continousTime++;
					}
					base.newTick();
				}
				displayTime();
			}
			catch(InterruptedException ex)
			{
				break;
			}
			
		}
	}
}

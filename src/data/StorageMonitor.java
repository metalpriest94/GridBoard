package data;

import java.util.ArrayList;

public class StorageMonitor implements Runnable {
	private Game game; 
	private ArrayList<Integer> storage;
	
	private boolean askedToSuspend = false;
	
	public StorageMonitor(Game game)
	{
		this.game = game;
	}

	public void askToSuspend()
	{
		askedToSuspend = true;
	}
	
	@Override
	public void run() {
		while(!askedToSuspend)
		{
			try
			{
				synchronized(this)
				{
					storage = game.getStorage();
					game.updateStorage(storage);
				}
				Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
				break;
			}
		}

	}

}

package data;

import java.util.ArrayList;

public class StorageMonitor implements Runnable {
	private Game game; 
	private ArrayList<Integer> storage;
	
	public StorageMonitor(Game game)
	{
		this.game = game;
	}

	@Override
	public void run() {
		while(true)
		{
			synchronized(this)
			{
				storage = game.getStorage();
				game.updateStorage(storage);
			}
		}

	}

}

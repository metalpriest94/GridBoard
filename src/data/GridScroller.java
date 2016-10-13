package data;

public class GridScroller implements Runnable {
	private JGridPanel affected; 
	private int positionX;
	private int positionY;
	
	
	public GridScroller(JGridPanel jgridpanel) {
		affected = jgridpanel;
	}
	
	public void moved(int x, int y)
	{
		synchronized (this) 
		{
			positionX = x;
			positionY = y;
		}
		
	}
	
	public void moveUp()
	{
		if(affected.getVisibleCornerY() > 1)
		{
			affected.setVisibleCornerY(affected.getVisibleCornerY() - 1);
		}
	}
	
	public void moveDown()
	{
		if(affected.getVisibleCornerY() < affected.getTilesY() - (affected.getHeight() / affected.getTileSize() - 1))
		{
			affected.setVisibleCornerY(affected.getVisibleCornerY() + 1);
		}
	}
	
	public void moveLeft()
	{
		if(affected.getVisibleCornerX() > 1)
		{
			affected.setVisibleCornerX(affected.getVisibleCornerX() - 1);
		}
	}
	
	public void moveRight()
	{
		if(affected.getVisibleCornerX() < affected.getTilesX() - (affected.getWidth() / affected.getTileSize() - 1))
		{
			affected.setVisibleCornerX(affected.getVisibleCornerX() + 1);

		}
	}
	
	@Override
	public void run() {
		while(true)
		{
			synchronized (this) 
			{
				if(positionX <= 10 && positionX != 0)
					moveLeft();
				else if(positionX >= affected.getWidth() - 11 && positionX != affected.getWidth()-1)
					moveRight();
				
				if(positionY <= 10 && positionY != 0)
					moveUp();
				else if(positionY >= affected.getHeight() - 11 && positionY != affected.getHeight()-1)
					moveDown();
				
				affected.repaint();
			}
			try
			{
				Thread.sleep(40);
			}
			catch(InterruptedException ex)
			{
				
			}
		}

	}

}

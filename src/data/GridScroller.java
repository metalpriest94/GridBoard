package data;

public class GridScroller implements Runnable {
	private JGridPanel affected; 
	private int positionX, positionY;
	
	private final int edgeSize = 20;
	
	
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
				if(positionX <= edgeSize && positionX != 0)
					moveLeft();
				else if(positionX >= affected.getWidth() - (edgeSize + 1) && positionX != affected.getWidth()-1)
					moveRight();
				
				if(positionY <= edgeSize && positionY != 0)
					moveUp();
				else if(positionY >= affected.getHeight() - (edgeSize + 1) && positionY != affected.getHeight()-1)
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

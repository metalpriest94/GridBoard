package data;

public class GridScroller implements Runnable {
	private JGridPanel affected; 
	private int positionX, positionY;
	private final int edgeSize = 20;
	private final int refresh = 12;
	private boolean isInComponent = true;
	
	private int zoomLevel = 3;
	private final int baseTileSize = 16;
	
	
	
	private boolean keyUp, keyDown, keyLeft, keyRight;
	
	
	
	public GridScroller(JGridPanel jgridpanel) {
		affected = jgridpanel;
	}
	
	
	public void setKeyUp(boolean keyUp) 
	{
		this.keyUp = keyUp;
	}

	public void setKeyDown(boolean keyDown) 
	{
		this.keyDown = keyDown;
	}

	public void setKeyLeft(boolean keyLeft) 
	{
		this.keyLeft = keyLeft;
	}

	public void setKeyRight(boolean keyRight) 
	{
		this.keyRight = keyRight;
	}

	public void setInComponent(boolean isInComponent) {
		this.isInComponent = isInComponent;
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
	
	public void zoomIn()
	{
		int lastCenterX = affected.getPosX() / affected.getTileSize();
		int lastCenterY = affected.getPosY() / affected.getTileSize();
		
		if (zoomLevel < 8)
		{
			changeZoom(+1);
			int newCenterX = affected.getPosX() / affected.getTileSize();
			int newCenterY = affected.getPosY() / affected.getTileSize();
			
			int offsetX = lastCenterX - newCenterX;
			int offsetY = lastCenterY - newCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				moveRight();
			}
			for(int i =0; i < offsetY; i++)
			{
				moveDown();
			}			
		}
		affected.repaint();
	}
	
	public void zoomOut()
	{
		int lastCenterX = affected.getPosX() / affected.getTileSize();
		int lastCenterY = affected.getPosY() / affected.getTileSize();
		
		if (zoomLevel > 1)
		{
			changeZoom(-1);
			int newCenterX = affected.getPosX() / affected.getTileSize();
			int newCenterY = affected.getPosY() / affected.getTileSize();
			
			int offsetX = newCenterX - lastCenterX;
			int offsetY = newCenterY - lastCenterY;
			
			for(int i =0; i < offsetX; i++)
			{
				moveLeft();
			}
			for(int i =0; i < offsetY; i++)
			{
				moveUp();
			}
			while (affected.getVisibleCornerX() > (affected.getTilesX() - affected.getWidth() / affected.getTileSize()) +1)
			{
				moveLeft();
			}
			while (affected.getVisibleCornerY() > (affected.getTilesY() - affected.getHeight() / affected.getTileSize()) +1)
			{
				moveUp();
			}
		}
		affected.repaint();
	}
	public void changeZoom(int value)
	{
		zoomLevel += value;
		affected.setTileSize(zoomLevel * baseTileSize);
	}
	
	@Override
	public void run() {
		while(true)
		{
			synchronized (this) 
			{
				if((positionX <= edgeSize && isInComponent ) || keyLeft)
					moveLeft();
				else if((positionX >= affected.getWidth() - (edgeSize + 1) && isInComponent) || keyRight)
					moveRight();
				
				if((positionY <= edgeSize && isInComponent) || keyUp)
					moveUp();
				else if((positionY >= affected.getHeight() - (edgeSize + 1) && isInComponent) || keyDown)
					moveDown();
				
				affected.repaint();
			}
			try
			{
				Thread.sleep(1000 / refresh);
			}
			catch(InterruptedException ex)
			{
				
			}
		}

	}

}

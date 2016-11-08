package data;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class GridScroller implements Runnable {
	private JComponent frame;
	private JGridPanel affected; 
	private int positionX, positionY;
	private final int edgeSize = 5;
	private final int refresh = 25;
	private boolean isInComponent = false;
	
	final int baseZoomLevel = 3;
	final int maxZoomLevel = 8;
	private int zoomLevel = baseZoomLevel;
	private final int baseTileSize = 16;
	
	private boolean keyUp, keyDown, keyLeft, keyRight;
	private boolean awaitZoomIn, awaitZoomOut;
	
	
	
	public GridScroller(JGridPanel jgridpanel, JComponent cursorBase) {
		affected = jgridpanel;
		frame = cursorBase;
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

	public void setAwaitZoomIn(boolean awaitZoomIn) {
		this.awaitZoomIn = awaitZoomIn;
	}


	public void setAwaitZoomOut(boolean awaitZoomOut) {
		this.awaitZoomOut = awaitZoomOut;
	}


	public void setInComponent(boolean isInComponent) {
		this.isInComponent = isInComponent;
	}

	public int getZoomLevel() {
		return zoomLevel;
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
			affected.setCurrentY(affected.getCurrentY() -1);
		}
	}
	
	public void moveDown()
	{
		if(affected.getVisibleCornerY() < affected.getTilesY() - (affected.getHeight() / affected.getTileSize() - 1))
		{
			affected.setVisibleCornerY(affected.getVisibleCornerY() + 1);
			affected.setCurrentY(affected.getCurrentY() +1);
		}
	}
	
	public void moveLeft()
	{
		if(affected.getVisibleCornerX() > 1)
		{
			affected.setVisibleCornerX(affected.getVisibleCornerX() - 1);
			affected.setCurrentX(affected.getCurrentX() -1);
		}
	}
	
	public void moveRight()
	{
		if(affected.getVisibleCornerX() < affected.getTilesX() - (affected.getWidth() / affected.getTileSize() - 1))
		{
			affected.setVisibleCornerX(affected.getVisibleCornerX() + 1);
			affected.setCurrentX(affected.getCurrentX() +1);

		}
	}
	
	public void zoomIn(int focusX, int focusY)
	{
		int lastCenterX = focusX / affected.getTileSize();
		int lastCenterY = focusY / affected.getTileSize();
		
		if (zoomLevel < maxZoomLevel)
		{
			changeZoom(+1);
			int newCenterX = focusX / affected.getTileSize();
			int newCenterY = focusY / affected.getTileSize();
			
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
		awaitZoomIn = false;
	}
	
	public void zoomOut(int focusX, int focusY)
	{
		int lastCenterX = focusX / affected.getTileSize();
		int lastCenterY = focusY / affected.getTileSize();
		
		if (zoomLevel > 1)
		{
			changeZoom(-1);
			int newCenterX = focusX / affected.getTileSize();
			int newCenterY = focusY / affected.getTileSize();
			
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
			while (affected.getVisibleCornerX() > (affected.getTilesX() - affected.getWidth() / affected.getTileSize()) +1 && affected.getWidth() < affected.getTilesX() * affected.getTileSize())
			{
				moveLeft();
			}
			while (affected.getVisibleCornerY() > (affected.getTilesY() - affected.getHeight() / affected.getTileSize()) +1 && affected.getHeight() < affected.getTilesY() * affected.getTileSize())
			{
				moveUp();
			}
		}
		awaitZoomOut = false;
	}
	public void changeZoom(int value)
	{
		zoomLevel += value;
		affected.setTileSize(zoomLevel * baseTileSize + baseTileSize);
	}
	
	@Override
	public void run() {
		while(true)
		{
			synchronized (this) 
			{
				if((positionX <= edgeSize && isInComponent ) || keyLeft)
					moveLeft();
				else if((positionX >= frame.getWidth() - (edgeSize + 1) && isInComponent) || keyRight)
					moveRight();
				
				if((positionY <= edgeSize && isInComponent) || keyUp)
					moveUp();
				else if((positionY >= frame.getHeight() - (edgeSize + 1) && isInComponent) || keyDown)
					moveDown();
				
				if(awaitZoomIn)
					zoomIn(affected.getPosX(), affected.getPosY());
				if(awaitZoomOut)
					zoomOut(affected.getPosX(), affected.getPosY());
				
				affected.repaint();
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

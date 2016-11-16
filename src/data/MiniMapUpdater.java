package data;

import java.awt.event.MouseEvent;

public class MiniMapUpdater implements Runnable {
	private JGridPanel affected, info;
	private int itemColor = (255*256*256) + (191*256) + (31);
	public MiniMapUpdater(JGridPanel miniMap, JGridPanel wholeMap)
	{
		affected = miniMap;
		info = wholeMap;
		affected.setDragged(true);
		
	}
	public void run() {
		while(true)
		{
			synchronized (this) 
			{
				updateMiniMap();				
				affected.repaint();
				info.repaint();
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
	
	public void updateMiniMap()
	{
		int upperLeftX, upperLeftY, lowerRightX, lowerRightY;
		upperLeftX= (info.getVisibleCornerX()-1) * affected.getTileSize();
		upperLeftY= (info.getVisibleCornerY()-1) * affected.getTileSize();
		affected.setStartDragX(upperLeftX);
		affected.setStartDragY(upperLeftY);
		
		lowerRightX = (info.getVisibleCornerX() + info.getWidth()  / info.getTileSize() -1) * affected.getTileSize();
		lowerRightY = (info.getVisibleCornerY() + info.getHeight() / info.getTileSize() -1) * affected.getTileSize();
		if (lowerRightX > affected.getWidth())
			lowerRightX = affected.getWidth();
		if (lowerRightY > affected.getHeight())
			lowerRightY = affected.getHeight();
		affected.setPosX(lowerRightX);
		affected.setPosY(lowerRightY);
		
		for(int x=0; x < info.getTilesX();x++)
		{
			for(int y=0; y < info.getTilesY();y++)
			{
				if(info.getMapping()[x][y][4] !=0)
				{
					affected.getMapping()[x][y][0] = itemColor;
				}
			}
		}
	}
	
	public void clickMiniMap(MouseEvent e)
	{
		int targetX = e.getX() /affected.getTileSize() - (info.getWidth() / 2) / info.getTileSize();
		int targetY = e.getY() /affected.getTileSize()- (info.getHeight() / 2) / info.getTileSize();
		
		if(targetX <= 0)
			targetX = 1;
		else if (targetX > info.getTilesX() - (info.getWidth() / 2) / info.getTileSize() * 2)
			targetX = info.getTilesX() - (info.getWidth() / info.getTileSize()) +1;
		
		if(targetY <= 0)
			targetY = 1;
		else if (targetY > info.getTilesY() - (info.getHeight() / 2) / info.getTileSize() * 2)
			targetY = info.getTilesY() - (info.getHeight() / info.getTileSize()) +1;

		info.setVisibleCornerX(targetX);
		info.setVisibleCornerY(targetY);
	}
	public void changeItemColor(int r, int g, int b)
	{
		itemColor = (r*256*256) + (g*256) + (b);
	}
}

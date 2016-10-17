package data;

public class MiniMapUpdater implements Runnable {
	private JGridPanel affected, info;
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
		affected.setStartDragX(info.getVisibleCornerX()-1);
		affected.setStartDragY(info.getVisibleCornerY()-1);
		affected.setPosX(info.getVisibleCornerX() + info.getWidth()  / info.getTileSize() -1);
		affected.setPosY(info.getVisibleCornerY() + info.getHeight() / info.getTileSize() -1);
	}
}

package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.JPanel;

public class JGridPanel extends JPanel implements Serializable{
	// Variablendeklaration  -------------------------------------------------------------------------------------------------
	private int posX;				// Variable zum Erfassen der X-Ordinate der Mausposition auf dem Panel
	private int posY;				// ------------------------- Y-Ordinate ------------------------------
	
	private int tilesX;				// Variable zum Erfassen der Spaltenanzahl auf dem Panel
	private int tilesY;				// ------------------------- Reihenanzaal  -------------
	
	private int visibleCornerX = 1;		// Variable zum Bestimmen des sichtbaren Bereiches, legt die Spalte am linken Rand fest
	private int visibleCornerY = 1;		// -----------------------------------------------, legt die Reihe  am oberen Rand fest
	
	private int tileSize = 100;			// Variable zum Erfassen der Groesse jeder Kachel
	
	private int currentX = 1; 		// Variable zum Erfassen der X-Ordinate einer aufgerufenen Kachel
	private int currentY = 1;		// ------------------------- Y-Ordinate -------------------------
	
	private int properties = 16;
	private transient Image[][] imageTile;
	private transient Image[][] imageAsset;
	private transient Image[][] imageTileCache;
	private transient Image[][] imageAssetCache;
	
	private int[][][] mapping; 		// Dreidimensionales Array für X-Ordinate, Y-Ordinate und Eigenschaften jeder Kachel
	private int[][][] mappingCache;
	private boolean isInitialMapping = true;
	private boolean showGrid = false;
	
	private boolean isDragged = false;
	private int startDragX;
	private int startDragY;
	
	private int cornerDragX;
	private int cornerDragY;
	private int draggedWidth;
	private int draggedHeight;
	private int draggedTilesX;
	private int draggedTilesY;
	//private assets;
	
	
	
	
	// Konstruktoren ---------------------------------------------------------------------------------------------------------
	public JGridPanel(int sizeX, int sizeY, int properties) {
		startUp(sizeX, sizeY, properties);
		
	}

	public JGridPanel(LayoutManager arg0, int sizeX, int sizeY, int properties) {
		super(arg0);
		startUp(sizeX, sizeY, properties);
	}

	public JGridPanel(boolean arg0, int sizeX, int sizeY, int properties) {
		super(arg0);
		startUp(sizeX, sizeY, properties);
	}

	public JGridPanel(LayoutManager arg0, boolean arg1, int sizeX, int sizeY, int properties) {
		super(arg0, arg1);
		startUp(sizeX, sizeY, properties);
	}
	
	// Additional Method called on Construction 
	private void startUp(int sizeX, int sizeY, int properties)
	{
		tilesX = sizeX;
		tilesY = sizeY;
		isInitialMapping = true;
		prepareMapping(sizeX, sizeY, properties);
		
	}
	
	// Setter + Getter -------------------------------------------------------------------------------------------------------
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getTilesX() {
		return tilesX;
	}

	public void setTilesX(int tilesX) {
		this.tilesX = tilesX;
	}

	public int getTilesY() {
		return tilesY;
	}

	public void setTilesY(int tilesY) {
		this.tilesY = tilesY;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	
	public int getVisibleCornerX() {
		return visibleCornerX;
	}

	public void setVisibleCornerX(int visibleCornerX) {
		this.visibleCornerX = visibleCornerX;
	}

	public int getVisibleCornerY() {
		return visibleCornerY;
	}

	public void setVisibleCornerY(int visibleCornerY) {
		this.visibleCornerY = visibleCornerY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public int getProperties() {
		return properties;
	}

	public void setProperties(int properties) {
		this.properties = properties;
	}

	public boolean showGrid() {
		return showGrid;
	}

	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}
	
	public void setImageTile(int x, int y, Image img)
	{
		this.imageTile[x][y] = img;
	}
	
	public void setImageAsset(int x, int y, Image img)
	{
		this.imageAsset[x][y] = img;
	}
	
	public int[][][] getMapping() {
		return mapping;
	}
	
	public void setMapping(int[][][] mapping) {
		this.mapping = mapping;
	}
	
	public boolean isDragged() {
		return isDragged;
	}

	public void setDragged(boolean isDragged) {
		this.isDragged = isDragged;
	}
	
	public void setStartDragX(int startDragX) {
		this.startDragX = startDragX;
	}

	public void setStartDragY(int startDragY) {
		this.startDragY = startDragY;
	}	
	
	public int getCornerDragX() {
		return cornerDragX;
	}

	public int getCornerDragY() {
		return cornerDragY;
	}

	public int getDraggedTilesX() {
		return draggedTilesX;
	}

	public int getDraggedTilesY() {
		return draggedTilesY;
	}

	//Weitere Methoden -------------------------------------------------------------------------------------------------------
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		locateTile();
		Graphics2D g2d= (Graphics2D)g;
		for(int i = visibleCornerX - 1; ((i  - visibleCornerX) < (getWidth() / tileSize) && i < tilesX ); i++)
		{
			for(int j = visibleCornerY -1 ; ((j - visibleCornerY) < getHeight() / tileSize && j < tilesY) ; j++)
			{
				g2d.setColor(new Color(mapping[i][j][0]));
				g2d.fill(new Rectangle((i - (visibleCornerX - 1)) * tileSize , (j  - (visibleCornerY - 1)) * tileSize, tileSize, tileSize));
				if(imageTile[i][j] != null)
				{
					g2d.drawImage(imageTile[i][j], (i - (visibleCornerX - 1)) * tileSize, (j  - (visibleCornerY - 1)) * tileSize, tileSize, tileSize, this);
				}
			}

		}
		
		for(int i = visibleCornerX - 1; ((i  - visibleCornerX) < (getWidth() / tileSize) && i < tilesX ); i++)
		{
			for(int j = visibleCornerY -1 ; ((j - visibleCornerY) < getHeight() / tileSize && j < tilesY); j++)
			{
				if(imageAsset[i][j] != null)
				{
					g2d.drawImage(imageAsset[i][j], (i - (visibleCornerX - 1)) * tileSize, (j  - (visibleCornerY - 1)) * tileSize, tileSize, tileSize, this);
				}
				if(showGrid)
				{
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.draw(new Rectangle((i - (visibleCornerX - 1)) * tileSize , (j  - (visibleCornerY - 1)) * tileSize, tileSize, tileSize));
				}
			}
		}
		
		g2d.setColor(Color.RED);
		if(isDragged)
		{
			cornerDragX = startDragX;
			cornerDragY = startDragY;
			draggedWidth = posX - cornerDragX + tileSize;
			draggedHeight = posY - cornerDragY + tileSize;
			if (draggedWidth <= 0)
			{
				cornerDragX -= -(draggedWidth - tileSize);
				draggedWidth = startDragX - cornerDragX + tileSize;
			}
			if (draggedHeight <= 0)
			{
				cornerDragY -= -(draggedHeight - tileSize);
				draggedHeight = startDragY - cornerDragY + tileSize;
			}
			draggedTilesX = draggedWidth / tileSize;
			draggedTilesY = draggedHeight / tileSize;
			g2d.draw(new Rectangle(cornerDragX, cornerDragY, draggedWidth, draggedHeight));
		}
		else
		{
			g2d.draw(new Rectangle(posX,posY,tileSize,tileSize));
		}
	
		
	}
	public void locateTile()		// Methode zum Ermitteln der Koordinaten einer Kachel, verhindert Verlassen der generierten Kacheln
	{
		try
		{
			if(posX > (tilesX - (visibleCornerX - 1) - 1)  * tileSize)
				posX = (tilesX - (visibleCornerX - 1) - 1) * tileSize;
			if(posY > (tilesY - (visibleCornerY - 1) - 1) * tileSize)
				posY = (tilesY - (visibleCornerY - 1) - 1) * tileSize;
			currentX = posX / tileSize + (visibleCornerX - 1);
			currentY = posY / tileSize + (visibleCornerY - 1);
		}
		catch(ArithmeticException ex)
		{
			tileSize = 1;
		}
	}
	
	public void prepareMapping(int sizeX, int sizeY, int properties)	
	{
		if (!isInitialMapping)
		{
			mappingCache = mapping;
			imageTileCache = imageTile;
			imageAssetCache = imageAsset;
		}
		mapping = new int[sizeX][sizeY][properties];
		imageTile = new Image[sizeX][sizeY];
		imageAsset = new Image[sizeX][sizeY];
		for(int x = 0; x < sizeX; x++)
		{
			for(int y = 0; y < sizeY; y++)
			{
				
				for(int z = 0; z < properties; z++)
				{
					if(isInitialMapping)
					{
						applyProperty(x, y, z, 0);		
					}
					else
					{
						try
						{
							applyProperty(x, y, z, mappingCache[x][y][z]);
							applyTileImage(x, y, imageTileCache[x][y]);
							applyAssetImage(x, y, imageAssetCache[x][y]);
						}
						catch (ArrayIndexOutOfBoundsException ex)
						{
							applyProperty(x, y, z, 0);
						}
					}
				}
			}
		}
		isInitialMapping = false;
	}
	
	public void applyProperty(int x, int y ,int property, int value)
	{
		mapping[x][y][property] = value;
		repaint();
	}
	
	public void applyProperty(int[][] map, int property)
	{
		for(int x = 0; x < map.length; x++)
		{
			for(int y = 0; y < map[0].length; y++)
			{
				mapping[x][y][property] = map[x][y];
			}
		}
		repaint();
	}


	public void applyTileImage(int x, int y ,Image img)
	{
		imageTile[x][y] = img;
		repaint();
	}
	
	public void applyAssetImage(int x, int y ,Image img)
	{
		imageAsset[x][y] = img;
		repaint();
	}

}

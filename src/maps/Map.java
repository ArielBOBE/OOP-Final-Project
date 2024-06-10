package maps;

import java.util.ArrayList;

public class Map {
	private String name;
	private int worldCol;
	private int worldRow;
	private int worldWidth;
	private int worldHeight;
	private int startX;
	private int startY;
	private int screenX;
	private int screenY;
	public int[][] mapTileNum1; // etc.
	public int[][] mapTileNum2; // grass
	public int[][] mapTileNum3; // ledges
			
	// getter methods
	public String getName() {
		return this.name;
	}
	
	public int getWorldCol() {
		return this.worldCol;
	}
	
	public int getWorldRow() {
		return this.worldRow;
	}
	
	public int getWorldWidth() {
		return this.worldWidth;
	}
	
	public int getWorldHeight() {
		return this.worldHeight;
	}
	
	public int getStartX() {
		return this.startX;
	}
	
	public int getStartY() {
		return this.startY;
	}
	
	// setter methods
	public void setName(String name) {
		this.name = name;
	}
	
	public void setWorldCol(int worldCol) {
		this.worldCol = worldCol;
	}
	
	public void setWorldRow(int worldRow) {
		this.worldRow = worldRow;
	}
	
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}
	
	public void setStartX(int StartX) {
		this.startX = StartX;
	}
	
	public void setStartY(int StartY) {
		this.startY = StartY;
	}
	
}

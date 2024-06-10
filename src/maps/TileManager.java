package maps;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class TileManager {
	
	GamePanel gp;
	public Tile[] allTiles;
	
	public Map[] maps;
	private int mapNum = 0;
	private int[] currMap;
	
	// the position of the tile to be drawn
	private int currentY;
	private int currentX;
	
	// the total col and row of the current map
	private int maxCol;
	private int maxRow;
	
	// the start position of the player in the world
	private int startCol;
	private int startRow;
	
	// the start position of the player in the screen
	private int screenCol;
	private int screenRow;
	
	// the movement speed of the player; translated into the movement of the map
	private int walkSpeed;
	KeyHandler KeyH;
	
	public TileManager(GamePanel gp, KeyHandler KeyH) {
		this.gp = gp;
		this.KeyH = KeyH;
		this.allTiles = new Tile[42];
		this.maps = new Map[1];
		initMaps();
		initRoute1CSV();
		initTiles();
		
		// get the (relative) X and Y coordinate of the current MAP {temporarily [0]}
		// the start coordinates of where the first tile is drawn!
		this.currentX = this.maps[0].getStartX();
		this.currentY = this.maps[0].getStartY() * -1;
		
		// get the information of worldCol and worldRow of the current MAP
		this.maxCol = this.maps[0].getWorldCol();
		this.maxRow = this.maps[0].getWorldRow();
		
		// setting the start positions (WORLD) 13,38
		this.startCol = 13;
		this.startRow = 37;
		
		// setting the start positions (ROW) 13,16
		this.screenCol = 13;
		this.screenRow = 15;
	}
	
	public int getWorldWidth() {
		return gp.getTileSize()*this.maxCol;
	}
	
	public int getWorldHeight() {
		return gp.getTileSize()*this.maxRow;
	}
	
	// getter method for world row and col
	public int getWorldCol() {
		return this.maxCol;
	}
	
	public int getWorldRow() {
		return this.maxRow;
	}
	
	// getter methods to get start coords (WORLD)
	public int getStartCol() {
		return this.startCol;
	}
	
	public int getStartRow() {
		return this.startRow;
	}
	
	// getter methods to get start coords (SCREEN)
	public int getScreenCol() {
		return this.screenCol;
	}
	
	public int getScreenRow() {
		return this.screenRow;
	}
	
	public void initTiles() {
		switch (this.mapNum) {
		case 0:
			initRoute1Tiles();
			break;
		default:
			break;
		}
	}
	
	public void initMaps() {
		this.maps[0] = new Map();
		this.maps[0].setWorldCol(25);
		this.maps[0].setWorldRow(40);
		this.maps[0].setWorldWidth(this.maps[0].getWorldCol() * gp.getTileSize()); // 1250
		this.maps[0].setWorldHeight(this.maps[0].getWorldRow() * gp.getTileSize()); // 2000
		this.maps[0].setName("Route 01");
		this.maps[0].setStartX(0);
		this.maps[0].setStartY(this.maps[0].getWorldHeight() - gp.getScreenHeight()); // 1100
		this.maps[0].mapTileNum1 = new int[40][25];
		this.maps[0].mapTileNum2 = new int[40][25];
		this.maps[0].mapTileNum3 = new int[40][25];
		
	} 
	
	// route 1 methods
	public void initRoute1Tiles() {
		for (int i = 0; i < this.allTiles.length; i ++) {
			this.allTiles[i] = new Tile();
		}
		
		try {
			// fence tiles
			this.allTiles[0].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/00_315.png"));
			this.allTiles[1].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/01_314.png"));
			this.allTiles[2].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/02_316.png"));
			for (int i = 0; i < 3; i++) {
				this.allTiles[i].collision = true;
			}
			
			// flower tiles
			this.allTiles[3].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/03_63.png"));
			
			// floor grass tiles
			this.allTiles[4].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/04_62.png"));
			this.allTiles[5].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/05_35.png"));
			this.allTiles[6].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/06_6.png"));
			this.allTiles[7].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/07_34.png"));
			
			// ledge tiles
			this.allTiles[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles_ledge/08_230.png"));
			this.allTiles[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles_ledge/09_231.png"));
			this.allTiles[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles_ledge/10_232.png"));
			for (int i = 8; i < 11; i++) {
				this.allTiles[i].collision = true;
			}
			
			// road tiles
			this.allTiles[11].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/11_4.png"));
			this.allTiles[12].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/12_58.png"));
			this.allTiles[13].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/13_57.png"));
			this.allTiles[14].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/14_1.png"));
			this.allTiles[15].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/15_29.png"));
			this.allTiles[16].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/16_31.png"));
			this.allTiles[17].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/17_2.png"));
			this.allTiles[18].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/18_30.png"));
			this.allTiles[19].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/19_32.png"));
			this.allTiles[20].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/20_3.png"));
			this.allTiles[21].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/21_60.png"));
			this.allTiles[22].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/22_59.png"));
			this.allTiles[41].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/41_0.png"));
			
			// sign tiles
			this.allTiles[23].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/23_106.png"));
			this.allTiles[23].collision = true;
			
			// tall grass tiles
			this.allTiles[26].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/26_7.png"));
			
			// tree tiles
			this.allTiles[29].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/29_546.png"));
			this.allTiles[30].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/30_547.png"));
			this.allTiles[31].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/31_518.png"));
			this.allTiles[32].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/32_519.png"));
			this.allTiles[33].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/33_490.png"));
			this.allTiles[34].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/34_491.png"));
			this.allTiles[35].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/35_548.png"));
			this.allTiles[36].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/36_549.png"));
			this.allTiles[37].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/37_520.png"));
			this.allTiles[38].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/38_521.png"));
			this.allTiles[39].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/39_492.png"));
			this.allTiles[40].image = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/40_493.png"));
			
			for (int i = 29; i < 41; i++) {
				this.allTiles[i].collision = true;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initRoute1CSV() {
		try {
			
			// loading CSV file for non collision tiles and mostly the bottom layer
			InputStream is = getClass().getResourceAsStream("/maps_route01/route1_Bottom.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for (int i = 0; i < maps[0].mapTileNum1.length; i++) {
				
				// reading CSV file by line
				String line = br.readLine();
				String numbers[] = line.split(",");
				for (int j = 0; j < maps[0].mapTileNum1[0].length; j++) {
					
					// getting String number in [i][j] as Integer
					int num = Integer.parseInt(numbers[j]);
					
					// storing number in [i][j] into mapTileNum
					this.maps[0].mapTileNum1[i][j] = num; 
				}
			}
			
			// loading CSV file for tall grass
			InputStream is2 = getClass().getResourceAsStream("/maps_route01/route1_tallGrass.csv");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
			
			for (int i = 0; i < maps[0].mapTileNum2.length; i++) {
				
				// reading CSV file by line
				String line = br2.readLine();
				String numbers[] = line.split(",");
				for (int j = 0; j < maps[0].mapTileNum2[0].length; j++) {
					
					// getting String number in [i][j] as Integer
					int num = Integer.parseInt(numbers[j]);
					
					// storing number in [i][j] into mapTileNum
					this.maps[0].mapTileNum2[i][j] = num; 
				}
			}
			
			// loading CSV file for tall grass
			InputStream is3 = getClass().getResourceAsStream("/maps_route01/route1_Ledges.csv");
			BufferedReader br3 = new BufferedReader(new InputStreamReader(is3));
			
			for (int i = 0; i < maps[0].mapTileNum3.length; i++) {
				
				// reading CSV file by line
				String line = br3.readLine();
				String numbers[] = line.split(",");
				for (int j = 0; j < maps[0].mapTileNum3[0].length; j++) {
					
					// getting String number in [i][j] as Integer
					int num = Integer.parseInt(numbers[j]);
					
					// storing number in [i][j] into mapTileNum
					this.maps[0].mapTileNum3[i][j] = num; 
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mapSelect(int mapNum) {
		
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g2, int pWorldX, int pWorldY, int pScreenX, int pScreenY) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < this.maxCol && worldRow < this.maxRow) {
			
			int tileNum = this.maps[0].mapTileNum1[worldRow][worldCol];
			int tileNum2 = this.maps[0].mapTileNum2[worldRow][worldCol];
			int tileNum3 = this.maps[0].mapTileNum3[worldRow][worldCol];
			
			int worldX = worldCol * gp.getTileSize();
			int worldY = worldRow * gp.getTileSize();
			int screenX = worldX - pWorldX + pScreenX;
			int screenY = worldY - pWorldY + pScreenY;
			
//			if (worldX + gp.getTileSize() > pWorldX - pScreenX - gp.getTileSize()
//					&& worldX - gp.getTileSize() < pWorldX + pScreenX + 20
//					&& worldY + gp.getTileSize() > pWorldY - pScreenY - 20 
//					&& worldY - gp.getTileSize()< pWorldY + pScreenY + 20) {
				g2.drawImage(allTiles[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
				if (tileNum2 != -1) {
					g2.drawImage(allTiles[tileNum2].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
				}
				if (tileNum3 != -1) {
					g2.drawImage(allTiles[tileNum3].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
				}
//				g2.drawRect(screenX, screenY, gp.getTileSize(), gp.getTileSize());
		
			
			worldCol++;
			if (worldCol == this.maxCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
}

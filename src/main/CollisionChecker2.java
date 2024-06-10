package main;
import entities.Player;

public class CollisionChecker2 {
	private GamePanel gp;
	private KeyHandler KeyH;
	
	public CollisionChecker2(GamePanel gp, KeyHandler KeyH) {
		this.gp = gp;
		this.KeyH = KeyH;
	}
	
	// gets the center x coordinate of the player's hit box
	public int getX(Player player) {
		int hitBoxCenterX = player.getStaticX() + player.getRect().x + (int)player.getRect().getWidth()/2;
		
		return hitBoxCenterX;
	}
	
	// gets the column (in terms of tiles)  that the hitbox's center x is
	public int getCol(Player player) {
		int hitBoxCenterX = player.getStaticX() + player.getRect().x + (int)player.getRect().getWidth()/2;
		int col = hitBoxCenterX/gp.getTileSize();
		
		return col;
	}
	
	// gets the row (in terms of tiles)  that the hitbox's center y is
	public int getRow(Player player) {
		int hitBoxCenterY = player.getStaticY() + player.getHeight() - (int)player.getRect().getHeight()/2;
		int row = hitBoxCenterY/gp.getTileSize();
		
		return row;
	}
	
	// method to check if player is currently in grass
	public boolean currentGrass(Player player) {
		int hitBoxCenterX = player.getStaticX() + player.getRect().x + (int)player.getRect().getWidth()/2;
		int hitBoxCenterY = player.getStaticY() + player.getHeight() - (int)player.getRect().getHeight()/2;
		
		int col = hitBoxCenterX/gp.getTileSize();
		int row = hitBoxCenterY/gp.getTileSize();
		int tileNum;
		
		tileNum = gp.tM.maps[0].mapTileNum2[row][col];
		
		// tileNume 26 is tall grass tile
		if (tileNum == 26) {
			return true;
		}
 		
		return false;
	}
	
	// method to check whether player is going to be moving into grass
	public boolean checkGrass(Player player) {
		int hitBoxCenterX = player.getStaticX() + player.getRect().x + (int)player.getRect().getWidth()/2;
		int hitBoxCenterY = player.getStaticY() + player.getHeight() - (int)player.getRect().getHeight()/2;
		
		int col = hitBoxCenterX/gp.getTileSize();
		int row = hitBoxCenterY/gp.getTileSize();

		int nextCol;
		int nextRow;
		int tileNum2;
		
		if (player.getDirection() == "Up") {
			nextCol = col;
			nextRow = row - 1;
			
			tileNum2 = gp.tM.maps[0].mapTileNum2[nextRow][nextCol];
			
			if(tileNum2 == 26) {
				return true;
			}
		}
		else if (player.getDirection() == "Down") {
			nextCol = col;
			nextRow = row + 1;
			
			tileNum2 = gp.tM.maps[0].mapTileNum2[nextRow][nextCol];
			
			if(tileNum2 == 26) {
				return true;
			}
		}
		else if (player.getDirection() == "Left") {
			nextCol = col - 1;
			nextRow = row;
			
			tileNum2 = gp.tM.maps[0].mapTileNum2[nextRow][nextCol];
			
			if(tileNum2 == 26) {
				return true;
			}
		}
		else if (player.getDirection() == "Right") {
			nextCol = col + 1;
			nextRow = row;
			
			tileNum2 = gp.tM.maps[0].mapTileNum2[nextRow][nextCol];
			
			if(tileNum2 == 26) {
				return true;
			}
		}
		
		return false;
	}
	
	// method to check if tile that player is moving towards is collidible
	public boolean checkTile(Player player) {
		int hitBoxCenterX = player.getStaticX() + player.getRect().x + (int)player.getRect().getWidth()/2;
		int hitBoxCenterY = player.getStaticY() + player.getHeight() - (int)player.getRect().getHeight()/2;
		
		int col = hitBoxCenterX/gp.getTileSize();
		int row = hitBoxCenterY/gp.getTileSize();
		
		int nextCol;
		int nextRow;
		int tileNum, tileNum3;
		
		if (player.getDirection() == "Up") {
			nextCol = col;
			nextRow = row - 1;
			
			tileNum = gp.tM.maps[0].mapTileNum1[nextRow][nextCol];
			tileNum3 = gp.tM.maps[0].mapTileNum3[nextRow][nextCol];
			
			if (gp.tM.allTiles[tileNum].collision == true) {
				return true;
			}
			
			if (tileNum3 != -1) {
				if (gp.tM.allTiles[tileNum3].collision == true) {
					return true;
				}
			}

		}
		else if (player.getDirection() == "Down") {
			nextCol = col;
			nextRow = row + 1;
			
			tileNum = gp.tM.maps[0].mapTileNum1[nextRow][nextCol];
			
			if (gp.tM.allTiles[tileNum].collision == true) {
				return true;
			}
		}
		else if (player.getDirection() == "Left") {
			nextCol = col - 1;
			nextRow = row;
			
			tileNum = gp.tM.maps[0].mapTileNum1[nextRow][nextCol];
			tileNum3 = gp.tM.maps[0].mapTileNum3[nextRow][nextCol];
			
			if (gp.tM.allTiles[tileNum].collision == true) {
				return true;
			}
			
			if (tileNum3 != -1) {
				if (gp.tM.allTiles[tileNum3].collision == true) {
					return true;
				}
			}

		}
		else if (player.getDirection() == "Right") {
			nextCol = col + 1;
			nextRow = row;
			
			tileNum = gp.tM.maps[0].mapTileNum1[nextRow][nextCol];
			tileNum3 = gp.tM.maps[0].mapTileNum3[nextRow][nextCol];
			
			if (gp.tM.allTiles[tileNum].collision == true) {
				return true;
			}
			
			if (tileNum3 != -1) {
				if (gp.tM.allTiles[tileNum3].collision == true) {
					return true;
				}
			}
		}
		
		return false;
	}
}

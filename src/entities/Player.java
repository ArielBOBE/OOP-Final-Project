package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import main.BattleHandler;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Character{
	
	private GamePanel gp;
	private BattleHandler bH;
	private KeyHandler KeyH;
	private AllMoves allMoves = new AllMoves();
	private AllSprites allSprites = new AllSprites();
	private MyPKMN[] myPokemons = new MyPKMN[6];
	private BufferedImage currentSprite;
	private BufferedImage rightR0, rightR1, rightR2, leftR0, leftR1, leftR2, upR0, upR1, upR2, downR0, downR1, downR2;
	private int screenX;
	private int screenY;
	
	private boolean keyPressed = false;
	
	// the number of frames the movement key is pessed down for
	private int pressFrames = 25;
	
	// position of player after done moving (x, y)
	private int staticY;
	private int staticX;
	
	// checks if after moving has already moved one tile exactly
	private boolean moveDone = true;
	
	// true if p is pressed, false if escape is pressed
	private boolean switchScreen = false;
	
	// checks the number of frames that has elapsed after moving
	// rests to 0 after 15 frames to allow for input again
	int moveCounter = 0;	
	
	// a counter for how many frames the sound effect has played for 
	int soundCounter = 0;
	
	public Player(GamePanel gp, KeyHandler KeyH, 
			int startCol, int startRow, int screenCol, int screenRow) {
		this.gp = gp;
		this.KeyH = KeyH;
		this.spriteCounter = 0;
		this.direction = "Up";
		loadDimensions();
		loadSprites();
		initPosition(startCol, startRow, screenCol, screenRow);
		createPokemons();
		setStarterMoves();
		this.solidArea = new Rectangle(0, this.height, gp.getTileSize(), gp.getTileSize());
		this.collisionOn = false;
	}
	
	public void killPokemon(int i) {
		this.myPokemons[i] = null;
	}
	
	// initializes starter pokemons in array
	public void createPokemons() {
		myPokemons[0] = new MyPKMN(this.gp, this.allSprites,
				"Charmander", "Fire", 5, "Modest");
		this.myPokemons[0].setSelected(true);
		myPokemons[1] = new MyPKMN(this.gp, this.allSprites,
				"Bulbasaur", "Grass", 5, "Adamant");
		this.myPokemons[1].setSelected(false);
	}
	
	// method to check if pokemon switch screen is opened/closed
	public void openSwitchScreen() {
		if (this.KeyH.pPressed()) {
			this.switchScreen = true;
		}
		if (this.KeyH.escapePressed()) {
			this.switchScreen = false;
		}
	}
	
	// updates contents of pokemon switch screen
	public void updateSwitchScreen() {
		openSwitchScreen();
		this.gp.pokeMenu.update(null);
	}
	
	// draws pokemon switch screen only if it is opened (p pressed)
	public void drawSwitchScreen(Graphics2D g2) {
		if (this.switchScreen) {
			this.gp.pokeMenu.draw(g2);
		}
	}
	
	public MyPKMN getPokemon(int i) {
		return this.myPokemons[i];
	}
	
	public void setStarterMoves() {
		this.myPokemons[0].setMoves(0, this.allMoves.getMove(0));
		this.myPokemons[0].setMoves(1, this.allMoves.getMove(1));
		this.myPokemons[0].setMoves(2, this.allMoves.getMove(2));
		this.myPokemons[0].setHpIV(15);
		this.myPokemons[0].setAtkIV(15);
		this.myPokemons[0].setSpAtkIV(15);
		this.myPokemons[0].setDefIV(15);
		this.myPokemons[0].setSpDefIV(15);
		this.myPokemons[0].setSpeedIV(15);
		
		this.myPokemons[1].setMoves(0, this.allMoves.getMove(1));
		this.myPokemons[1].setMoves(1, this.allMoves.getMove(6));
		this.myPokemons[1].setMoves(2, this.allMoves.getMove(7));
		this.myPokemons[1].setHpIV(15);
		this.myPokemons[1].setAtkIV(15);
		this.myPokemons[1].setSpAtkIV(15);
		this.myPokemons[1].setDefIV(15);
		this.myPokemons[1].setSpDefIV(15);
		this.myPokemons[1].setSpeedIV(15);
	}
	
	// method to print out attributes that have previously caused errors
	public void errorCheck() {
//		System.out.println("KeyU, collision: "+"("+this.KeyH.upPressed()+","+this.collisionOn+")");
//		System.out.println("KeyL, collision: "+"("+this.KeyH.leftPressed()+","+this.collisionOn+")");
//		System.out.println("World x,y: ("+this.worldX+","+this.worldY+")");
//		System.out.println("Screen x,y: ("+this.screenX+","+this.screenY+")");
//		System.out.println("PressFrames: "+this.pressFrames);
//		System.out.println("KeyPressed?: "+this.keyPressed);
//		System.out.println("MoveCounter: "+this.moveCounter);
//		System.out.println("moveDone?: "+this.moveDone);
//		System.out.println("PosY%Tile: "+this.worldY%gp.getTileSize());
//		System.out.println("PosX%Tile: "+this.worldX%gp.getTileSize());
//		System.out.println("Direction: "+this.direction);
//		System.out.println("CollisioN?: "+this.collisionOn);
//		System.out.println("WorldX, WorldY:"+this.worldX+","+this.worldY);
//		System.out.println("StaticX, StaticY:"+this.staticX+","+this.staticY);
//		
//		if (this.worldX == this.staticX) {
//			System.out.println("EQUAL");
//		}
	}
	
	// true if any key is pressed
	public boolean getKeyPressed() {
		return this.keyPressed;
	}
	
	// returns the position of player after they come to a stop
	public int getStaticX() {
		return this.staticX;
	}
	
	public int getStaticY() {
		return this.staticY;
	}
	
	public int getMovementSpeed() {
		return (int) this.movementSpeed;
	}

	public String getDirection() {
		return this.direction;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Rectangle getRect() {
		return this.solidArea;
	}
	
	public int getWorldX() {
		return this.worldX;
	}
	
	public int getWorldY() {
		return this.worldY;
	}
	
	public int getScreenY() {
		return this.screenY;
	}
	
	public int getScreenX() {
		return this.screenX;
	}
	
	public MyPKMN[] getPokemons() {
		return this.myPokemons;
	}
	
	public void loadDimensions() {
		this.height = gp.getTileSize()*3/2;
		this.width = gp.getTileSize();
	}
	
	public void resetMove() {
		this.moveCounter = 0;
	}
	
	// sets initial position of player
	public void initPosition(int startCol, int startRow, int screenCol, int screenRow) {
		this.worldX = gp.getTileSize() * startCol; 
		this.worldY = gp.getTileSize() * startRow - (this.height - gp.getTileSize());
		this.staticX = this.worldX;
		this.staticY = this.worldY;
		this.screenX = gp.getTileSize() * screenCol;
		this.screenY = gp.getTileSize() * screenRow;
		this.movementSpeed = 4 * gp.getTileSize()/gp.getFPS();

	}
	
	// plays thud sound effect moving into a collidable tile
	public void walkCollided() {		
		if (this.collisionOn && this.KeyH.KeyPress()) {
			switch(this.soundCounter) {
			case 0:
				this.gp.playSE(1);
				break;
			case 34:
				this.gp.playSE(1);
				this.soundCounter = 0;
				break;
			}
		}
		
		if (this.collisionOn) {
			this.soundCounter++;
		}
		if (this.collisionOn && !this.KeyH.KeyPress()) {
			this.soundCounter = 0;
		}
	
	}
	
	public void loadSprites() {
		try {
			// down sprite
			down0 = ImageIO.read(getClass().getResourceAsStream("/character_player/down_0.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/character_player/down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/character_player/down_2.png"));
			
			// up sprite
			up0 = ImageIO.read(getClass().getResourceAsStream("/character_player/up_0.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/character_player/up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/character_player/up_2.png"));
			
			// left sprite
			left0 = ImageIO.read(getClass().getResourceAsStream("/character_player/left_0.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/character_player/left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/character_player/left_2.png"));
			
			// right sprite
			right0 = ImageIO.read(getClass().getResourceAsStream("/character_player/right_0.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/character_player/right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/character_player/right_2.png"));
			
			// RUNNING SPRITES
			
			// down sprite
			downR0 = ImageIO.read(getClass().getResourceAsStream("/character_player/downRun_0.png"));
			downR1 = ImageIO.read(getClass().getResourceAsStream("/character_player/downRun_1.png"));
			downR2 = ImageIO.read(getClass().getResourceAsStream("/character_player/downRun_2.png"));
			
			// up sprite
			upR0 = ImageIO.read(getClass().getResourceAsStream("/character_player/upRun_0.png"));
			upR1 = ImageIO.read(getClass().getResourceAsStream("/character_player/upRun_1.png"));
			upR2 = ImageIO.read(getClass().getResourceAsStream("/character_player/upRun_2.png"));
			
			// left sprite
			leftR0 = ImageIO.read(getClass().getResourceAsStream("/character_player/leftRun_0.png"));
			leftR1 = ImageIO.read(getClass().getResourceAsStream("/character_player/leftRun_1.png"));
			leftR2 = ImageIO.read(getClass().getResourceAsStream("/character_player/leftRun_2.png"));
			
			// right sprite
			rightR0 = ImageIO.read(getClass().getResourceAsStream("/character_player/rightRun_0.png"));
			rightR1 = ImageIO.read(getClass().getResourceAsStream("/character_player/rightRun_1.png"));
			rightR2 = ImageIO.read(getClass().getResourceAsStream("/character_player/rightRun_2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(int worldWidth, int worldHeight) {
	
		// movement
		// if view is NOT at border of the map, the camera follows the player; centered at screen
		if (this.worldX > 650 && this.worldX < 800) {	
			this.screenX = 650;
		}
		// if the world position of the player is less than the screen position, the camera stops and player moves uncentered
		else if (this.worldX < this.screenX) {
			this.screenX = this.worldX;
		}
		else if (this.worldX >= worldWidth - this.width) {
			this.screenX = gp.getScreenWidth() - this.width;
		}
		// if view is NOT at border of the map, the camera follows the player; centered at screen
		if (this.worldY < worldHeight - gp.getScreenHeight()/2 && this.worldY > gp.getScreenHeight()/2) {
			this.screenY = gp.getScreenHeight()/2;
		}
		// if the world position of the player is less than the screen position, the camera stops and player moves uncentered
		else if (this.worldY < this.screenY) {
			this.screenY = this.worldY;
		}
		else if (this.worldY >= worldHeight - this.height) {
			this.screenY =  gp.getScreenHeight() - this.height;
		}
		
		
		// 
		if (KeyH.upPressed() && !this.keyPressed) {
			this.direction = "Up";
			this.keyPressed = true;
			this.pressFrames++;
			if(this.moveCounter == 0 && !this.moveDone) {
				this.moveDone = true;
			}

		}
		else if (KeyH.downPressed() && !this.keyPressed) {
			this.direction = "Down";
			this.keyPressed = true;
			this.pressFrames++;
			if(this.moveCounter == 0 && !this.moveDone) {
				this.moveDone = true;
			}
		}
		
		
		else if (KeyH.leftPressed() && !this.keyPressed) {
			this.direction = "Left";
			this.keyPressed = true;
			this.pressFrames++;
			if(this.moveCounter == 0 && !this.moveDone) {
				this.moveDone = true;
			}

		}
		else if (KeyH.rightPressed() && !this.keyPressed) {
			this.direction = "Right";
			this.keyPressed = true;
			this.pressFrames++;
			if(this.moveCounter == 0 && !this.moveDone) {
				this.moveDone = true;
			}
		}
		
		this.collisionOn = false;
		this.collisionOn = gp.cChecker.checkTile(this);
		
		
		if (!KeyH.KeyPress()) {
			this.pressFrames = 0;
		}
		if (!moveDone) {
			this.keyPressed = false;
		}
		if (this.collisionOn) {
			this.keyPressed = false;
		}
		this.errorCheck();
		if (this.collisionOn == false && this.keyPressed && pressFrames < 40) {
	
			switch(this.direction) {
			case "Up":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldY > 0) {
					this.worldY -= this.movementSpeed;
				}
				if (this.worldY <= worldHeight && this.worldY > worldHeight - gp.getScreenHeight()/2) {
					this.screenY -= this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY + 1;
					}
				} 
				else if (this.worldY > 0 && this.worldY <= gp.getScreenHeight()/2){
					this.screenY -= this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY + 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldY%gp.getTileSize() < 25) {
						this.worldY = this.worldY + 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Left":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldX > 0) {
					this.worldX -= this.movementSpeed;
				}
				
				if (this.worldX <= 650 && this.worldX > 0) {
					this.screenX -= this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX + 1;
					}
				} else if (this.worldX > 800 && this.worldX <= 1200) {
					this.screenX -= this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX + 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldX%gp.getTileSize() <= 49) {
						this.worldX = this.worldX + 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Right":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldX < 1200) {
					this.worldX += this.movementSpeed;	
				} 
				
				if (this.worldX <= 650 && this.worldX > 0) {
					this.screenX += this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX - 1;
					}
				} else if (this.worldX > 800 && this.worldX < 1200) {
					this.screenX += this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX - 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldX%gp.getTileSize() > 0) {
						this.worldX = this.worldX - 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Down":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldY < worldHeight - this.height) {
					this.worldY += this.movementSpeed;
				}
				
				if (this.worldY >= 0 && this.worldY < gp.getScreenHeight()/2) {
					this.screenY += this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY - 1;
					}
				}
				else if (this.worldY < worldHeight - this.height 
						&& this.worldY > worldHeight - gp.getScreenHeight()/2) {
					this.screenY += this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY - 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldY%gp.getTileSize() > 25) {
						this.worldY = this.worldY - 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			}
		}
		else if (this.collisionOn == false && this.keyPressed && pressFrames > 40) {
			switch(this.direction) {
			case "Up":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldY > 0) {
					this.worldY -= this.movementSpeed;
				}
				if (this.worldY <= worldHeight && this.worldY > worldHeight - gp.getScreenHeight()/2) {
					this.screenY -= this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY + 1;
					}
				} 
				else if (this.worldY > 0 && this.worldY <= gp.getScreenHeight()/2){
					this.screenY -= this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY + 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldY%gp.getTileSize() < 25) {
						this.worldY = this.worldY + 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Left":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldX > 0) {
					this.worldX -= this.movementSpeed;
				}
				
				if (this.worldX <= 650 && this.worldX > 0) {
					this.screenX -= this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX + 1;
					}
				} else if (this.worldX > 800 && this.worldX <= 1200) {
					this.screenX -= this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX + 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldX%gp.getTileSize() <= 49) {
						this.worldX = this.worldX + 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Right":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldX < 1200) {
					this.worldX += this.movementSpeed;	
				} 
				
				if (this.worldX <= 650 && this.worldX > 0) {
					this.screenX += this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX - 1;
					}
				} else if (this.worldX > 800 && this.worldX < 1200) {
					this.screenX += this.movementSpeed;
					if (this.moveCounter == 17) {
						this.screenX = this.screenX - 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldX%gp.getTileSize() > 0) {
						this.worldX = this.worldX - 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			case "Down":
				if (!moveDone) {
					this.keyPressed = false;
					break;
				}
				this.moveCounter++;
				if (this.worldY < worldHeight - this.height) {
					this.worldY += this.movementSpeed;
				}
				
				if (this.worldY >= 0 && this.worldY < gp.getScreenHeight()/2) {
					this.screenY += this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY - 1;
					}
				}
				else if (this.worldY < worldHeight - this.height 
						&& this.worldY > worldHeight - gp.getScreenHeight()/2) {
					this.screenY += this.movementSpeed;
					if(this.moveCounter == 17) {
						this.screenY = this.screenY - 1;
					}
				}
				if (this.moveCounter == 17) {
					this.moveCounter = 0;
					this.moveDone = false;
					if (this.worldY%gp.getTileSize() > 25) {
						this.worldY = this.worldY - 1;
					}
					this.staticX = this.worldX;
					this.staticY = this.worldY;
				}
				break;
			}
		}
		updateSwitchScreen();
		this.walkCollided();
	}
	
	public void draw(Graphics2D g2) {
		// switch case for sprite
		
		if (this.keyPressed || this.collisionOn == true && this.KeyH.KeyPress()) {
			if (this.direction == "Up") {
				if (spriteCounter == 0 || spriteCounter == 31) {
					this.currentSprite = up0;
				}
				else if (spriteCounter == 15 || spriteCounter == 45) {
					this.currentSprite = up1;
				}
				else if (spriteCounter == 30 || this.spriteCounter == 60) {
					this.currentSprite = up2;
					if (spriteCounter == 60) {
						this.spriteCounter = 0;
					}
				}
			}
			else if (this.direction == "Left") {
				if (spriteCounter == 0 || spriteCounter == 31) {
					this.currentSprite = left0;
				}
				else if (spriteCounter == 15 || spriteCounter == 45) {
					this.currentSprite = left1;
				}
				else if (spriteCounter == 30 || spriteCounter == 60) {
					this.currentSprite = left2;
					if (spriteCounter == 60) {
						this.spriteCounter = 0;
					}
					
				}
			}
			else if (this.direction == "Down") {
				if (spriteCounter == 0 || spriteCounter == 31) {
					this.currentSprite = down0;
				}
				else if (spriteCounter == 15 || spriteCounter == 45) {
					this.currentSprite = down1;
				}
				else if (spriteCounter == 30 || spriteCounter == 60) {
					this.currentSprite = down2;
					if (this.spriteCounter == 60) {
						this.spriteCounter = 0;
					}
				}
			}
			else if (this.direction == "Right") {
				if (spriteCounter == 0 || spriteCounter == 31) {
					this.currentSprite = right0;
				}
				else if (spriteCounter == 15 || spriteCounter == 45) {
					this.currentSprite = right1;
				}
				else if (spriteCounter == 30 || spriteCounter == 60) {
					this.currentSprite = right2;
					if (this.spriteCounter == 60) {
						this.spriteCounter = 0;
					}
				}
			}
			
			this.spriteCounter++;
		}
		else if (!KeyH.KeyPress()) {
			if (this.direction == "Up") {
				this.currentSprite = up1;
			}
			else if (this.direction == "Left") {
				this.currentSprite = left1;
			}
			else if (this.direction == "Down") {
				this.currentSprite = down1;
			}
			else if (this.direction == "Right") {
				this.currentSprite = right1;
			}
			this.spriteCounter = 0;
		}
		
		g2.drawImage(this.currentSprite, this.screenX, this.screenY, this.width, this.height, null);
		drawSwitchScreen(g2);
//		g2.drawRect(screenX, screenY, this.width, this.height);
	}
}

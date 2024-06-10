package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class MyPKMN extends Pokemon implements Trainable{
	
	private int prevLevel;
	
	private String[] learnSet;
	
	// exp cap at level 1
	private int baseExpCap = 50;
	
	// current exp cap based on level
	private int currentExpCap;
	
	// exp progress, if equal to current cap, level up
	private int expProgress = 0;
	
	private int width = 156;
	private int height = 150;
	private int screenX = 310;
	private int screenY = 610 - this.height;
	private int spriteCounter = 0;
	private int frameCounter = 0;
	private BufferedImage currentSprite;
	private boolean selected;
	private boolean isAlive = true;
	
	public MyPKMN(GamePanel gp, AllSprites allSprites, String name, String type, int level
			, String nature) {
		super(gp, allSprites, name, type, level, nature);
		
		this.prevLevel = this.getLevel();
		this.updateExpCap();
	}
	
	public boolean getIsAlive() {
		return this.isAlive;
	}
	
	public void setSelected(boolean state) {
		this.selected = state;
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public void updateExpCap() {
		this.currentExpCap = this.baseExpCap + this.getLevel() * 10;
	}
	
	public void setLearnSet() throws IOException {
		if (getName().equals("Charmander")) {
			InputStream is = getClass().getResourceAsStream("/pokemonLearnSheets/Charmander.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			this.learnSet = line.split(",");
		}
		else if (getName().equals("Bulbasaur")) {
			InputStream is = getClass().getResourceAsStream("/pokemonLearnSheets/Bulbasaur.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			this.learnSet = line.split(",");
		}
		else if (getName().equals("Squirtle")) {
			InputStream is = getClass().getResourceAsStream("/pokemonLearnSheets/Squirtle.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			this.learnSet = line.split(",");
		}
	}
	
	// draw UI and update after level up
	public String[] getLearnable() {
		// subtract previous level from current level = D // or maybe just move at current level
		// see the moves that can be learned within D indexes after previous level in the learnSheet
		String[] learnable = {"-1", "-1"};
		
		int startIndex = this.prevLevel - 1;
		int stopIndex = this.getLevel() - 1;
		int index = 0;
		for (int i = startIndex; i < stopIndex; i++) {
			if(!this.learnSet[i].equals("-1")) {
				learnable[index] = this.learnSet[i];
				index++;
			}
		}
		
		return learnable;
	}
	
	public void replaceMove(Moves move) {
		for (int i = 0; i < this.getMoves().length; i++) {
			if (this.getMoves()[i] == null) {
				this.getMoves()[i] = move;
				return;
			}
		}
	}
	
	// called when super.isFainted == true;
	@Override
	public void gainEXP(int enemyLvl, int evYield, String evStat) {
		this.expProgress = this.expProgress + ((enemyLvl + 10) * 2);
		// record the previous level so that you know what moves to be learned
		this.prevLevel = this.getLevel();
		levelUp();
		gainEV(evYield, evStat);
	}

	@Override
	public void gainEV(int evYield, String evStat) {
		// TODO Auto-generated method stub
		switch(evStat) {
		case "hp":
			this.setHpEV(evYield);
			break;
		case "atk":
			this.setAtkEV(evYield);
			break;
		case "spAtk":
			this.setSpAtkEV(evYield);
			break;
		case "def":
			this.setDefEV(evYield);
			break;
		case "spDef":
			this.setSpDefEV(evYield);
			break;
		case "speed":
			this.setSpeedEV(evYield);
			break;
		}
	}

	@Override
	public void levelUp() {
		if (this.expProgress < this.currentExpCap) {
			return;
		}
		if (this.expProgress >= this.currentExpCap) {
			this.expProgress = this.expProgress - this.currentExpCap;
			this.gainLevel();
			super.defaultHp();
			updateExpCap();
			levelUp();
		}
	}
	
	public int getExp() {
		return this.expProgress;
	}
	
	public int getExpCap() {
		return this.currentExpCap;
	}
	
	public void update() {
		if (this.getName().equals("Charmander")) {
			if (this.frameCounter == 9) {
			this.spriteCounter++;
			this.frameCounter = 0;
			}
			
			if (this.spriteCounter >= 9) {
				this.spriteCounter = 0;
			}
		}
		else if (this.getName().equals("Bulbasaur")) {
			if (this.frameCounter == 12) {
				this.spriteCounter++;
				this.frameCounter = 0;
			}
				
			if (this.spriteCounter >= 12) {
				this.spriteCounter = 0;
			}
		}

		this.currentSprite = this.getBackSprites()[this.spriteCounter];
		this.frameCounter++;
	}
	
	public void draw(Graphics2D g2) {
		switch(this.getName()) {
		case "Charmander":
			g2.drawImage(this.currentSprite, this.screenX, this.screenY, this.width, this.height, null);
			break;
		case "Bulbasaur":
			g2.drawImage(this.currentSprite, this.screenX + 40, this.screenY + 40, 150, 112, null);
			break;
		}
		
	}

}

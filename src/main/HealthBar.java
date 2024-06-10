package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class HealthBar {
	GamePanel gp;
	private FontMetrics metrics;

	// font large is for pokemon name 
	private Font fontLarge;
	// font medium is for level
	private Font fontMedium;
	// font small is for hit point number 
	private Font fontSmall;
	
	// font metrics
	private FontMetrics fmL, fmM, fmS;
	
	// font sizes
	private int fontSizeLarge = 15;
	private int fontSizeMedium = 13;
	private int fontSizeSmall = 10;
	
	// positions of elements to be drawn
	private int screenX, screenY;
	private int width,height, height2;
	private int hpHeight;
	private int nameX, nameY;
	private int levelX, levelY;
	private int hpNumX, hpNumY;
	private boolean enemyBar;
	
	// name of pokemon
	private String name;
	// level of pokemon
	private int level;
	// current hp vs max hp of the pokemon
	private int currentHp;
	private int maxHp;
	// ratio of current hp to max hp to determine health color
	private double ratio;
	// length of the hp bar depending on current health
	private int length;
	
	// Colors
	// blue and red
	private Color hpColor1;
	private Color hpColor2;
	private Color hpColor3;
	// black translucent and solid grey
	private Color hpBoxColor1;
	private Color hpBoxColor2;
	// white
	private Color textColor;
	
	public HealthBar(GamePanel gp, int x, int y, int currentHp, int maxHp, String name, int level, boolean enemy) {
		this.gp = gp;
		this.currentHp = currentHp;
		this.maxHp = maxHp;
		this.name = name;
		this.level = level;
		this.enemyBar = enemy;
		setScreenXY(x, y);
		setFont();
		defaultDimensions();
		loadColors();
	}
	
	public void defaultDimensions() {
		this.width = 270;
		this.height = 50;
		this.height2 = 20;
		this.hpHeight = 5;
		this.nameX = this.screenX + 13;
		this.nameY = this.screenY + 30;
		this.levelX = this.screenX + this.width - 40;
		this.levelY = this.nameY;
		this.hpNumX = this.screenX + 125;
		this.hpNumY = this.screenY + this.height + this.height2 - 5;
	}
	
	public void setScreenXY(int x, int y) {
		this.screenX = x;
		this.screenY = y;
	}
	
	public void setFont(){
		this.fontLarge = new Font("Bahnschrift SemiLight", Font.BOLD, this.fontSizeLarge);
		this.fontMedium = new Font("Bahnschrift SemiLight", Font.PLAIN, this.fontSizeMedium);
		this.fontSmall = new Font("Bahnschrift SemiLight", Font.PLAIN, this.fontSizeSmall);
		
		this.fmL = this.gp.getFontMetrics(this.fontLarge);
		this.fmM = this.gp.getFontMetrics(this.fontMedium);
		this.fmS = this.gp.getFontMetrics(this.fontSmall);
	}
	
	public void loadColors() {
		this.hpBoxColor1 = new Color(28, 43, 41);
		this.hpBoxColor2 = new Color(62, 65, 82);
		this.hpColor1 = new Color(3, 158, 255);
		this.hpColor2 = new Color(255, 29, 0);
		this.hpColor3 = new Color(242, 12, 0);
		this.textColor = new Color(255, 255, 255);
	}
	
	public void drawName(Graphics2D g2) {
		g2.setFont(this.fontLarge);
		g2.setComposite(AlphaComposite.SrcOver.derive(0.9f));
		g2.setColor(this.textColor);
		g2.drawString(this.name, this.nameX, this.nameY);
	}
	
	public void drawLevel(Graphics2D g2) {
		g2.setFont(this.fontMedium);
		g2.setComposite(AlphaComposite.SrcOver.derive(0.9f));
		g2.setColor(this.textColor);
		g2.drawString("Lvl "+this.level, this.levelX, this.levelY);
	}
	
	public void updateHPBar(int currentHp, int maxHp, String name, int level) {
		this.currentHp = currentHp;
		this.maxHp = maxHp;
		this.level = level;
		this.name = name;
		this.ratio = (double)this.currentHp/this.maxHp;
		this.length = this.width * this.currentHp/this.maxHp;
		if (this.length < 0) {
			this.length = 0;
		}
	}
	
	public void drawHPBar(Graphics2D g2) {
		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
		
		if (this.ratio < 0.5 && this.ratio > 0.2) {
			g2.setColor(this.hpColor3);
		}
		else if (this.ratio <= 0.2) {
			g2.setColor(this.hpColor2);
		}
		else if (this.ratio >= 0.5){
			g2.setColor(this.hpColor1);
		}
		g2.fillRect(this.screenX, this.screenY, this.length, this.hpHeight);
		// drawing the empty health bar
		g2.setColor(Color.black);
		g2.fillRect(this.screenX + this.length, this.screenY, this.width - this.length, this.hpHeight);
	}
	
	public void drawHPBox2(Graphics2D g2) {
		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
		g2.setColor(this.hpBoxColor2);
		g2.fillRect(this.screenX, this.screenY + this.height, this.width, this.height2);
	}
	
	public void drawHPBox(Graphics2D g2) {
		// setting translucent
		g2.setComposite(AlphaComposite.SrcOver.derive(0.95f));
		g2.setColor(this.hpBoxColor1);
		g2.fillRect(this.screenX, this.screenY, this.width, this.height);
	}
	
	public void drawHPNum(Graphics2D g2) {
		g2.setFont(this.fontSmall);
		g2.setComposite(AlphaComposite.SrcOver.derive(0.9f));
		g2.setColor(this.textColor);
		g2.drawString(this.currentHp+"/"+this.maxHp, this.hpNumX, this.hpNumY);
	}
	
	public void reduceHP() {
		
	}
	
	public void update(int currentHp, int maxHp, String name, int level) {
		updateHPBar(currentHp, maxHp, name, level);
	}
	
	public void draw(Graphics2D g2) {
		drawHPBox(g2);
		drawHPBar(g2);
		drawName(g2);
		drawLevel(g2);
		if(!this.enemyBar) {
			drawHPBox2(g2);
			drawHPNum(g2);
		}
	}
}

package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Button {
	// attributes for button
	private int width;
	private int height;
	private int posX;
	private int posY;
	private boolean hovered = false;
	
	// attributes for text within button
	private String text = "";
	private Font font;
	private int fontSize = 15;
	private int textX;
	private int textY;
	
	GamePanel gp;
	MouseHandler mouseH;
	// color when not hover
	private Color color1;
	// color when hover
	private Color color2;
	private Color textColor;
	
	public Button(GamePanel gp, MouseHandler mouseH) {
		this.gp = gp;
		this.mouseH = mouseH;
	}
	
	public Button(GamePanel gp, MouseHandler mouseH, int width, int height, Color color1, Color color2) {
		this.gp = gp;
		this.mouseH = mouseH;
		this.width = width;
		this.height = height;
		setColor(color1, color2);
	}
	
	public Button(GamePanel gp, MouseHandler mouseH, int width, int height, int posX, int posY) {
		this.gp = gp;
		this.mouseH = mouseH;
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
	}
	
	public Button(GamePanel gp, MouseHandler mouseH, int width, int height, int posX, int posY, 
			Color color1, Color color2) {
		this.gp = gp;
		this.mouseH = mouseH;
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		setColor(color1, color2);
	}
	
	public void setText(String text) {
		this.text = text;
		this.textX = this.posX + 20;
		this.textY = this.posY + this.height/2 + this.fontSize/4;
		this.font = new Font("Bahnschrift SemiLight", Font.PLAIN, this.fontSize);
	}
	
	public void setTextPosX(int x) {
		this.textX = x;
	}
	
	public void setColor() {
		this.color1 = new Color(28, 43, 41);
		this.color2 = new Color(3, 158, 255);
		this.textColor = new Color(255, 255, 255);
	}
	
	public void setColor(Color color1, Color color2) {
		this.color1 = color1;
		this.color2 = color2;
		this.textColor = new Color(255, 255, 255);
	}
	
	public Color getColor1() {
		return this.color1;
	}
	
	public Color getColor2() {
		return this.color2;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public void setPosX(int x) {
		this.posX = x;
	}
	
	public void setPosY(int y) {
		this.posY = y;
	}
	
	public boolean clicked() {
		// bugged
		if (this.hovered && mouseH.getClicked()) {
			this.mouseH.resetClicked();
			return true;
		}

		return false;
	}
	
	// goes hand in hand with getClicked method, will make it so you can tell if button is hovered on
	public void update() {
		int boundsRX = this.posX + this.width;
		int boundsLX = this.posX;
		int boundsUY = this.posY;
		int boundsLY = this.posY + this.height;
		
		// update color of the button when hovered on
		if ((mouseH.getMouseX() > boundsLX && mouseH.getMouseX() < boundsRX)
				&& (mouseH.getMouseY() > boundsUY && mouseH.getMouseY() < boundsLY)) {
			this.hovered = true;
		}
		else if((mouseH.getMouseX() < boundsLX || mouseH.getMouseX() > boundsRX
				|| (mouseH.getMouseY() < boundsUY || mouseH.getMouseY() > boundsLY))) {
			this.hovered = false;
		}
		
		if (this.getText().equals("")) {
			this.hovered = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		// setting translucent

		if(this.hovered) {
			// setting solid and color2 if hovered on
			g2.setComposite(AlphaComposite.SrcOver.derive(1f));
			g2.setColor(color2);
		}
		else {
			// if not hovered color1
			g2.setColor(color1);
		}
		g2.fillRect(this.posX, this.posY, this.width, this.height);
		drawText(g2);
	}
	
	public void drawText(Graphics2D g2) {	
		if (this.text != null) {
			g2.setFont(this.font);
			g2.setColor(this.textColor);
			g2.drawString(this.text, this.textX, this.textY);
		}
	}

	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}
	
}

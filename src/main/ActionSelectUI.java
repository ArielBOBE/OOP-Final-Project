package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class ActionSelectUI {
	GamePanel gp;
	MouseHandler mouseH;
	
	// array of buttons
	Button[] options;
	
	// dimensions of each button
	private int bttnWidth = 200;
	private int bttnHeight = 50;
	
	// the X position of the very first button
	private int startX;
		
	// the Y position of the very first button
	private int startY;
	
	// the vertical margin between buttons
	private int marginY; 
	
	// color of button when not hovered and when hovered
	private Color hoverColor = new Color(3, 158, 255); // blue
	private Color noHoverColor = new Color(28, 43, 41); // black-ish
	
	// the selected action
	public String action = "";
	
	public ActionSelectUI(GamePanel gp, MouseHandler mouseH) {
		this.gp = gp;
		this.mouseH = mouseH;
		
		// starting coordinates is (900, 500)
		this.startX = this.gp.getScreenWidth() - this.bttnWidth;
		this.startY = 500;
		
		// margin is 10 pixels
		this.marginY = this.bttnHeight + 10;
		
		// initializing buttons array
		this.options = new Button[4];
		
		// creating buttons as elements of the options array 
		createButtons();
		
		// adding text to each button in the options array
		addText();
	}
	
	public void createButtons() {
		for (int i = 0; i < this.options.length; i++) {
			this.options[i] = new Button(this.gp, this.mouseH, this.bttnWidth, this.bttnHeight,
										this.startX, this.startY + this.marginY * i, 
										this.noHoverColor, this.hoverColor);
		}
	}
	
	public void addText() {
		this.options[0].setText("F i g h t");
		this.options[1].setText("P o k e m o n");
		this.options[2].setText("P o k e b a l l");
		this.options[3].setText("R u n");
	}
	
	public void drawOptions(Graphics2D g2) {
		for (int i = 0; i < this.options.length; i++) {
			this.options[i].draw(g2);
		}
	}
	
	public String getChoice() {
		return this.action;
	}
	
	public void resetChoice() {
		this.action = "";
	}
	
	public void update() {
		for (int i = 0; i < this.options.length; i++) {
			this.options[i].update();
			if (this.options[i].clicked()) {
				this.action = this.options[i].getText();
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		drawOptions(g2);
	}
}

package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import entities.Moves;

public class MovesUI {
	GamePanel gp;
	
	Button[] moveButtons = new Button[4];
	
	// index of moves and moveOptions correspond to one another
	Moves[] moves = new Moves[4];
	
	Moves selectedMove;
	
	HashMap<String, Color> colorMapping;
	
	MouseHandler mouseH;
	
	private int bttnWidth = 250;
	private int bttnHeight = 50;
	
	// the x position of the very first move
	private int startX;
	
	// the y position of the very first move
	private int startY;
	
	// the vertical margin between buttons
	private int marginY = bttnHeight + 10;
	
	// color of button when hovered
	private Color hoverColor = new Color(181, 180, 154);
	
	// color of button if no move
	private Color grayedOut = new Color(89, 89, 89);
	
	public MovesUI(GamePanel gp, MouseHandler mouseH, Moves[] myMoves) {
		this.gp = gp;
		this.mouseH = mouseH;
		this.moves = myMoves;
		
		this.startX = 815;
		this.startY = 500;
		
		createColorMapping();
	
		// creating buttons to be clicked
		createOptions();
		
		// adding text (move name) to button
		addText();
		
		// setting color of buttons according to move type
		setColor();
	}
	
	// called once
	public void createColorMapping() {
		this.colorMapping = new HashMap<>();
		this.colorMapping.put("Normal", new Color(168, 167, 122));
		this.colorMapping.put("Fire", new Color(238, 129, 48));
		this.colorMapping.put("Water", new Color(99, 144, 240));
		this.colorMapping.put("Electric", new Color(247, 208, 44));
		this.colorMapping.put("Grass", new Color(122, 199, 76));
		this.colorMapping.put("Ice", new Color(150, 217, 214));
		this.colorMapping.put("Fighting", new Color(194, 46, 40));
		this.colorMapping.put("Poison", new Color(163, 62, 161));
		this.colorMapping.put("Ground", new Color(226, 191, 101));
		this.colorMapping.put("Flying", new Color(169, 143, 243));
		this.colorMapping.put("Psychic", new Color(249, 85, 135));
		this.colorMapping.put("Bug", new Color(166, 185, 26));
		this.colorMapping.put("Rock", new Color(182, 161, 54));
		this.colorMapping.put("Ghost", new Color(115, 87, 151));
		this.colorMapping.put("Dragon", new Color(111, 53, 252));
		this.colorMapping.put("Dark", new Color(112, 87, 70));
	}
	
	// creating buttons
	public void createOptions() {
		for (int i = 0; i < moveButtons.length; i++) {
			this.moveButtons[i] = new Button(this.gp, this.mouseH, this.bttnWidth, this.bttnHeight,
					this.startX, this.startY + this.marginY * i);
		}
	}
	
	// adds text to button 
	public void addText() {
		for (int i = 0; i < moveButtons.length; i++) {
			if (moves[i] != null) {
				this.moveButtons[i].setText(moves[i].getName());
			}
		}
	}
	
	// sets color of button based on the type of the move assigned to that button
	public void setColor() {
		for (int i = 0; i < moveButtons.length; i++) {
			if (moves[i] != null) {
				this.moveButtons[i].setColor(this.colorMapping.get(moves[i].getType()), this.hoverColor);
			}
			else {
				this.moveButtons[i].setColor(this.grayedOut, this.hoverColor);
			}
		}
	}
	
	// returns move that has been picked
	public Moves moveChoice() {
		// iterates through buttons array and if clicked, returns move that corresponds
		
		return selectedMove;
	}
	
	public void resetChoice() {
		this.selectedMove = null;
	}
	
	public void updateMoves(Moves[] myMoves) {
		this.moves = myMoves;
		this.setColor();
		this.addText();
	}
		
	
	public void update(Moves[] myMoves) {
		updateMoves(myMoves);
		for (int i = 0; i < moveButtons.length; i++) {
			this.moveButtons[i].update();
			if (this.moveButtons[i].clicked()) {
				this.selectedMove = this.moves[i];
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		for (int i = 0; i < moveButtons.length; i++) {
			moveButtons[i].draw(g2);
		}
	}
	
	
}

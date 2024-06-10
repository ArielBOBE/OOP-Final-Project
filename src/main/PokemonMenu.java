package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import entities.AllSprites;
import entities.MyPKMN;
import entities.Pokemon;


// initialize object of this class in gamePanel since it is accessible from both map and battle

public class PokemonMenu {
	
	private GamePanel gp;
	private MouseHandler mouseH;
	
	private AllSprites allSprites = new AllSprites();
	
	private Rectangle container;
	private Rectangle border;
	
	private MyPKMN selectedPokemon;
	
	// will need to override text position in order to make room for pokemon icons
	private MyPKMN[] myPokemons;
	private Button[] pokemonsButtons = new Button[6];
	
	private Color containerColor = new Color(28, 43, 41);
	private Color borderColor = new Color(255, 255, 255);
	
	// grey?/ or maybe a darker color that pops >> same color is background but 100% opacity
	private Color bttnColorCurrent = new Color(3, 158, 255); // the same white that is used when selecting move
	private Color bttnColorHover = new Color(3, 158, 255); // same as bttnColorCurrent
	private Color bttnColorNoHover = new Color(181, 180, 154); // Dark color idk
	private Color textColor = new Color(255, 255, 255); // white
	
	// position of the container and its border
	private int containerX;
	private int containerY;
	
	// dimensions of the container
	private int containerWidth;
	private int containerHeight;
	
	// curvature of rounded corners
	private int curveW = 30;
	private int curveH = 30;

	// initial positions of the first button
	private int bttnStartX;
	private int bttnStartY; 
	private int bttnMarginY;
	
	// dimensions of the buttons
	private int bttnWidth;
	private int bttnHeight;
	
	private BufferedImage slot1Image = this.allSprites.getCharmanderSpritesF()[0];
	private BufferedImage slot2Image;
	private BufferedImage slot3Image;
	private BufferedImage slot4Image;
	private BufferedImage slot5Image;
	private BufferedImage slot6Image;
	
	// the position of the very first icon
	private int iconStartX;
	private int iconStartY; 
	private int iconMarginY; // vertical margin between icons draw (same as bttnMarginY)
	
	// icon dimensions
	private int iconWidth;
	private int iconHeight;
	
	// boolean for switching
	private boolean switching = false;
	
	// if pokemon[0] == charmander, image = charmander[0]
	
	public PokemonMenu(GamePanel gp, MouseHandler mouseH) {
		this.gp = gp;
		this.mouseH = mouseH;
		this.myPokemons = this.gp.player.getPokemons();
		defaultSelection();
		
		// setting container dimensions
		this.containerWidth = 430;
		this.containerHeight = 535;
		
		// setting container position
		this.containerX = 340;
		this.containerY = 155;
		
		// setting button dimensions
		this.bttnWidth = 350;
		this.bttnHeight = 60;
		this.bttnMarginY = this.bttnHeight + 10;
		
		// setting button positions
		this.bttnStartX = this.containerX + 40;
		this.bttnStartY = this.containerY + 20;
		
		// setting icon dimensions
		this.iconWidth = 70;
		this.iconHeight = 50;
		this.iconMarginY = this.bttnMarginY;
		
		// setting icon positions
		this.iconStartX = this.bttnStartX + 5;
		this.iconStartY = this.bttnStartY + this.bttnHeight - this.iconHeight - 5;
		
		// creating buttons
		createButtons();
		// setting colors for button
		setButtonColor();
	}
		
	public void drawIcons(Graphics2D g2) {
		if (this.myPokemons[0] != null) {
			g2.drawImage(slot1Image, this.iconStartX, this.iconStartY, this.iconWidth, this.iconHeight, null);
		}
		if (this.myPokemons[1] != null) {
			if (this.myPokemons[1].getName().equals("Bulbasaur")) {
				this.slot2Image = this.allSprites.getBulbasaurSpritesF()[0];
				g2.drawImage(slot2Image, this.iconStartX, this.iconStartY + this.iconMarginY*1, this.iconWidth, this.iconHeight, null);
			}
		}
	}
	
	public void createButtons() {
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				this.pokemonsButtons[i] = new Button(this.gp, this.mouseH, 
						this.bttnWidth, this.bttnHeight,
						this.bttnStartX, this.bttnStartY + this.bttnMarginY*i);
				
				this.pokemonsButtons[i].setText("Lvl "+this.myPokemons[i].getLevel()+": "+this.myPokemons[i].getName());
				this.pokemonsButtons[i].setTextPosX(this.bttnStartX + 80);
			}
		}
	}
	
	public void setButtonColor() {
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				if (this.myPokemons[i].getSelected()) {
					this.pokemonsButtons[i].setColor(this.bttnColorCurrent, this.bttnColorHover);
				}
				else {
					this.pokemonsButtons[i].setColor(this.bttnColorNoHover, this.bttnColorHover);
				}
			}
		}
	}
	
	public void drawPkmnButtons(Graphics2D g2) {
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				this.pokemonsButtons[i].draw(g2);
			}
		}
	}
	
	public void drawContainer(Graphics2D g2) {
		// set the color of the container
		g2.setColor(this.containerColor);
		// set the opacity of the container, 90%
		g2.setComposite(AlphaComposite.SrcOver.derive(0.9f));
		// draw the rectangle
		g2.fillRoundRect(this.containerX, this.containerY, this.containerWidth, this.containerHeight, 
				this.curveW, this.curveH);
		
		// set the color of the border
		g2.setColor(this.borderColor);
		// set the opacity of the border, 100%
		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
		// set the STROKE of the border, very strokeful
		g2.setStroke(new BasicStroke(3f));
		// draw the border
		g2.drawRoundRect(this.containerX, this.containerY, this.containerWidth, this.containerHeight, 
				this.curveW, this.curveH);
	}
	
	public boolean isSwitching() {
		return this.switching;
	}
	
	public void defaultSelection() {
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				if (this.myPokemons[i].getSelected()) {
					this.selectedPokemon = this.myPokemons[i];
					return;
				}
			}
		}
	}
	
	// accepts alive pokemon as argument
	public void updateSelection(MyPKMN myPokemon) {
		if (myPokemon != null) {
			this.selectedPokemon = myPokemon;
		}
	}
	
	public void updateSelection() {
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				if (this.pokemonsButtons[i].clicked() && !this.myPokemons[i].getSelected()) {
					this.myPokemons[i].setSelected(true);
					this.gp.player.getPokemons()[i].setSelected(true);
					this.selectedPokemon = this.myPokemons[i];
					this.switching = true;
				}
				if (this.myPokemons[i].getSelected() && this.selectedPokemon != this.myPokemons[i]) {
					this.myPokemons[i].setSelected(false);
					this.gp.player.getPokemons()[i].setSelected(false);
				}
			}
		}
		if (!this.selectedPokemon.getSelected()) {
			System.out.println("BOB");
			for (int i = 0; i < this.myPokemons.length; i++) {
				if (this.myPokemons[i] != null) {
					this.myPokemons[i].setSelected(true);
					this.gp.player.getPokemons()[i].setSelected(true);
					this.selectedPokemon = this.myPokemons[i];
					this.switching = true;
				}
			}
		}
	}
	
	public MyPKMN getSelected() {
		this.switching = false;
		return this.selectedPokemon;
	}
	
	public void update(MyPKMN myPokemon) {
		updateSelection();
		updateSelection(myPokemon);
		setButtonColor();
		for (int i = 0; i < this.myPokemons.length; i++) {
			if (this.myPokemons[i] != null) {
				this.pokemonsButtons[i].update();
			}
		}
	}
	
	public void draw(Graphics2D g2) { 
		drawContainer(g2);
		drawPkmnButtons(g2);
		drawIcons(g2);
	}
}

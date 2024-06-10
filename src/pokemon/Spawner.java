package pokemon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Player;
import main.GamePanel;
import main.KeyHandler;

public class Spawner {
	
	private GamePanel gp;
	
	// array containing possible encounters in grass
	private String[] pokemons = {"Bulbasaur"};
	
	// array of grass images
	private BufferedImage[] walkGrass = new BufferedImage[3];
	// grass image
	private BufferedImage grass0;
	private KeyHandler KeyH;
	private Random rand;
	
	// encounter is a random integer from 1 - 8 which determines number of steps taken before encounter
	int encounterIndex;
	
	// is true when stepping into grass from non-grass
	boolean newGrass = true;
	
	// steps taken in new grass
	int steps = 0;
	
	// true if encountered pokemon
	boolean fight;
	
	
	// coordinates in which grass will be drawn at
	private int screenX;
	private int screenY;
	
	public Spawner(GamePanel gp, KeyHandler KeyH) {
		this.gp = gp;
		this.KeyH = KeyH;
		this.rand = new Random();
		loadTiles();
	}
	
	public void loadTiles() {
		try {
			this.walkGrass[0] = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/28_14.png"));
			this.walkGrass[1] = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/25_15.png"));
			this.walkGrass[2] = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/24_16.png"));
			this.grass0 = ImageIO.read(getClass().getResourceAsStream("/maps_bottom/27_12.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// checks whether the player is moving towards tall grass
	public boolean toGrass(Player player) {
		return gp.cChecker.checkGrass(player);
	}
	
	// checks whether the player is currently in grass
	public boolean inGrass(Player player) {
		return gp.cChecker.currentGrass(player);
	}
	
	// method to see if player has encounter a pokemon
	public boolean encounter(Player player) {
		if (player.getWorldX() == player.getStaticX() 
				&& player.getWorldY() == player.getStaticY() && player.getKeyPressed() && this.inGrass(player)) {
			this.steps++;
		}
		
		// if the number of steps = encounter index, true (pokemon encountered)
		if (this.steps == this.encounterIndex) {
			this.steps = 0;
			return true;
		}
		
		return false;
	}
	
	// picks pokemon randomly from the array of possible pokemons
	public String pokemonInGrass() {
		return pokemons[rand.nextInt(0, 1)];

	}
	
	
	public void update(Player player) {
		// getting world position of the grass
		int worldX = gp.cChecker.getCol(player) * gp.getTileSize();
		int worldY = gp.cChecker.getRow(player) * gp.getTileSize();
		int screenX = worldX - player.getWorldX() + player.getScreenX();
		int screenY = worldY - player.getWorldY() + player.getScreenY();
				
		if (inGrass(player) && player.getWorldX() == player.getStaticX() 
				&& player.getWorldY() == player.getStaticY()) {
			this.screenX = screenX;
			this.screenY = screenY;
		}
		
		// updating encounter index, if in grass and that grass is new, sets a new encounter index
		if (inGrass(player) && this.newGrass) {
			this.newGrass = false;
			this.encounterIndex = rand.nextInt(1, 9);
		}
		
		// if not in new grass, this.fight is true if player has encountered pokemon
		if (!this.newGrass) {
			this.fight = encounter(player);
		}
		
		// if pokemon encounter, new grass is reset into true and goes to the battle screen
		if (this.fight) {
			this.newGrass = true;
			gp.setInfight(fight);
		}
	
	}
	
	public void draw(Graphics2D g2, Player player) {
		if (inGrass(player) && player.getWorldX() == player.getStaticX() && player.getWorldY() == player.getStaticY()) {
			g2.drawImage(grass0, this.screenX, this.screenY, gp.getTileSize(), gp.getTileSize(), null);
		}
		
	}
}

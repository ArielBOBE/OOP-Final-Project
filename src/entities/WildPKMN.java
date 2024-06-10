package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

import main.GamePanel;

public class WildPKMN extends Pokemon{
	private String evStat;
	private int evYield;
	private Random rand = new Random();
	private AllMoves allMoves = new AllMoves();
	private String[] natures = {"Modest", "Timid", "Jolly", "Adamant"};
	private int frameCounter = 0;
	private int spriteCounter = 0;
	private int width = 120;
	private int height = 120;
	private int screenX = 680;
	private int screenY = 450 - height;
	private TypeChecker tCheck = new TypeChecker();
	
	private BufferedImage currentSprite;
	
	public WildPKMN(GamePanel gp, AllSprites allSprites, String name, int myPokemonLvl) {
		super(gp, allSprites, name);
		setLevel(myPokemonLvl);
		try {
			setMoves();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIVsRandom();
		setNatureRandom();
		setBaseStats();
		super.calculateStats();
		super.defaultHp();
		super.setType();
	}
	
	public void setLevel(int myPokemonLvl) {
		int level = rand.nextInt(myPokemonLvl - 3, myPokemonLvl + 1);
		super.setLevel(level);
	}
	
	public void setNatureRandom() {
		this.setNature(this.natures[rand.nextInt(0, 4)]);
	}
	
	public void setIVsRandom() {
		setHpIV(rand.nextInt(0, 32));
		setAtkIV(rand.nextInt(0, 32));
		setSpAtkIV(rand.nextInt(0, 32));
		setDefIV(rand.nextInt(0, 32));
		setSpDefIV(rand.nextInt(0, 32));
		setSpeedIV(rand.nextInt(0, 32));
	}
	
	public void readMoves(BufferedReader bR) throws IOException {
		// reading CSV file by line
		String line = bR.readLine();
		String learnSheet[] = line.split(",");
		System.out.println("LearnSheet: "+Arrays.toString(learnSheet));
		int counter = 0;
		int level = getLevel();
		for (int i = level - 1; i >= 0; i--) {
			Moves move;
			while(learnSheet[i].equals("-1")) {
				i--;
				System.out.println(learnSheet[i]);
			}
			for (int j = 0; j < allMoves.getSize(); j ++) {
				// checking if the move in the learn sheet is available in the game
				if(learnSheet[i].equals(allMoves.getMove(j).getName())) {
					move = allMoves.getMove(j);
					super.setMoves(counter, move);
					break;
				}
			}
			if (counter == 4) {
				break;
			}
			counter++;
		}
		for (int k = 0; k < this.getMoves().length; k++) {
			if(this.getMoves()[k] == null) {
				this.getMoves()[k] = allMoves.getMove(1);
				break;
			}
		}
	}
	
	// accepts myPokemon's type as argument
	public Moves pickMove(String type) {
		Moves selectedMove = null;
		String effective; 
		int randint;
		int[] normalEffect = new int[4];
		boolean hasSE = true;
		boolean hasNE = false;
		int notEffectiveIndex = -1;
		int counter = 0;
		int number = 1;
		for (int i = 0; i < this.getMoves().length; i++) {
			if (this.getMoves()[i] == null) {
				number++;
			}
			if (this.getMoves()[i] != null) {
				number++;
				effective = tCheck.checkEffect(this.getMoves()[i].getType(), type);
				
				if (effective.equals("Super Effective")) {
					selectedMove = this.getMoves()[i];
					break;
				}
				
				if (effective.equals("Neutral")) {
					normalEffect[counter] = i;
					counter++;
				}
				
				if (effective.equals("Not Effective")) {
					notEffectiveIndex = i;
				}
			}
	}
		
		if (selectedMove == null) {
			randint = rand.nextInt(0, counter+1);
			selectedMove = this.getMoves()[normalEffect[randint]];
		}
	
		return selectedMove;
	}
	
	public void setMoves() throws IOException {
		InputStream sqrt = getClass().getResourceAsStream("/pokemonLearnSheets/Squirtle.csv");
		InputStream blb = getClass().getResourceAsStream("/pokemonLearnSheets/Bulbasaur.csv");
		InputStream chmndr = getClass().getResourceAsStream("/pokemonLearnSheets/Charmander.csv");
		
		if(getName() == "Squirtle") {
			BufferedReader br = new BufferedReader(new InputStreamReader(sqrt));
			
			// reading CSV file by line
			readMoves(br);
		}
		
		else if(getName() == "Charmander") {
			BufferedReader br = new BufferedReader(new InputStreamReader(chmndr));
			
			// reading CSV file by line
			readMoves(br);
		}
		
		else if(getName() == "Bulbasaur") {
			this.evStat = "spAtk";
			BufferedReader br = new BufferedReader(new InputStreamReader(blb));
			
			// reading CSV file by line
			readMoves(br);
		}
	}
	
	public int getEvYield() {
		return this.evYield;
	}
	
	public String getEvStat() {
		return this.evStat;
	}
	
	public void update() {
		if (this.frameCounter == 9) {
			this.spriteCounter++;
			this.frameCounter = 0;
		}
		
		if (this.spriteCounter >= 10) {
			this.spriteCounter = 0;
		}
		
		this.currentSprite = this.getFrontSprites()[this.spriteCounter];
		this.frameCounter++;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(this.currentSprite, this.screenX, this.screenY, this.width, this.height, null);
	}
	// method to set methods

}

package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.Scanner;

import entities.AllMoves;
import entities.AllSprites;
import entities.Moves;
import entities.MyPKMN;
import entities.WildPKMN;
import pokemon.Spawner;

public class BattleHandler {
	GamePanel gp;
	BufferedImage battleBackground;
	private int bgHeight;
	private int bgWidth;
	private int startX;
	private int startY;
	
	// button objects 
	Button bttnFight;
	Button bttnPKMN;
	Button bttnRun;
	
	// battle ui
	MovesUI battleUI;
	ActionSelectUI asUI;
	PokemonMenu pokeMenu;
	
	// Hp bar objects
	HealthBar myHP;
	HealthBar enemyHP;
	
	private int bttnWidth = 200;
	private int bttnHeight = 50;
	private int bttnMargins = bttnHeight + 10; // 10 pixels
	
	// all pokemon sprites
	private AllMoves allMoves = new AllMoves();
	private AllSprites allSprites = new AllSprites();
	
	// wild pokemon object;
	private WildPKMN wildPokemon = null;
	private MyPKMN myPokemon = null;
	private Spawner spawner;
	
	// battle back end 
	private Scanner sc = new Scanner(System.in);
	
	// states during battle
	// if choosingAction, draw and update the action ui
	private boolean choosingAction = true;
	
	// if choosingAttack, draw and update the attack ui
	private boolean choosingAttack = false;
	
	// if choosingPokemon, draw and update the pokeMenu
	private boolean choosingPokemon = false;
	
	// if this state is true, allow for pokemons to do their animations
	private boolean animationTurn = false;
	
	// action flags //
	
	// true if run is selected
	private boolean running = false;

	// true if speed of my pokemon > wild
	private boolean attacking = false;
	
	// true if has switched pokemon
	private boolean switching = false;
	
	// true if wild pokemon still alive or player has not run
	private boolean inBattle = true;
	
	// true if player pokemon has done attack/move
	private boolean pHasMoved = false;
	private boolean cpuHasMoved = false;
	
	// selected move
	private Moves mySelectedMove;
	private Moves cpuSelectedMove;
	
	// sprite counter flicker
	private int spriteNum = 0; // 0 is drawn, 1 is not drawn,, to give flickering effect
	private int frameCounter = 0;
	private int frameCounterUniversal = 0; // goes up to 120, then reset and stops
	
	public BattleHandler(GamePanel gp, Spawner spawner) {
		this.gp = gp;
		this.spawner = spawner;
		spawnPokemon();
		createMoveUI();
		createActionUI();
		createPokeMenu();
		createHPElements();
		setDimensions();
		loadImages();
	}
	
	public AllSprites getAllSprites() {
		return this.allSprites;
	}
	
	public void spawnPokemon() {
		// if my pokemon is null, sets the first pokemon that is alive in inventory as the selected one
		if (this.myPokemon == null) {
			for (int i = 0; i < this.gp.player.getPokemons().length; i++) {
				if (this.gp.player.getPokemons()[i] != null) {
					if (this.gp.player.getPokemons()[i].getSelected()) {
						this.myPokemon = this.gp.player.getPokemon(i);
					}
				}
			}
		}
		// if wild pokemon is null, create new wild pokemon object with randomized stats
		if (this.wildPokemon == null) {
			this.wildPokemon = new WildPKMN(this.gp, allSprites, this.spawner.pokemonInGrass(), this.myPokemon.getLevel());
		}
	}
	
	public void createMoveUI() {
		this.battleUI = new MovesUI(this.gp, this.gp.mouseH, this.myPokemon.getMoves());
	}
	
	public void createActionUI() {
		this.asUI = new ActionSelectUI(this.gp, this.gp.mouseH);
	}
	
	public void createPokeMenu() {
		this.pokeMenu = this.gp.pokeMenu;
	}
	
	public void createHPElements() {
		this.myHP = new HealthBar(this.gp, 40, 400, 
				this.myPokemon.getCurrentHP(), this.myPokemon.getHp(), 
				this.myPokemon.getName(), this.myPokemon.getLevel(), false);
		
		this.enemyHP = new HealthBar(this.gp, 800, 270,
				this.wildPokemon.getCurrentHP(), this.wildPokemon.getHp(),
				this.wildPokemon.getName(), this.wildPokemon.getLevel(), true);
	}
 	
	public void setDimensions() {
		this.bgHeight = gp.getScreenHeight() * 3/4;
		this.bgWidth = gp.getScreenWidth();
		this.startX = 0;
		this.startY = 100;
	}
	
	public void loadImages() {
		try {
			this.battleBackground = ImageIO.read(getClass().getResourceAsStream("/BattleUI/battlebgmaybe.jpg"));
//			this.battleBackground = ImageIO.read(getClass().getResourceAsStream("/BattleUI/grassBG.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// call after asUI.update
	public void playerAction() {
		switch(asUI.getChoice()) {
		case "F i g h t":
			this.choosingAction = false;
			this.choosingAttack = true;
			break;
		case "R u n":
			this.choosingAction = false;
			this.running = true;
			break;
		case "P o k e m o n":
			this.choosingAction = false;
			this.choosingPokemon = true;
			break;
		case "P o k e b a l l":
			System.out.println("Choice: "+asUI.getChoice());
			break;
		}
	}
	
	// call after battleUI.update
	public void selectMove() {
		if (battleUI.moveChoice() != null) {
			this.mySelectedMove = battleUI.moveChoice();
			this.choosingAttack = false;
			this.attacking = true;
			System.out.println("Selected is my: "+this.mySelectedMove.getName());
			this.cpuSelectedMove = this.wildPokemon.pickMove(this.myPokemon.getType());
			System.out.println("Selected is wild: "+this.cpuSelectedMove.getName());
			this.animationTurn = true;
		}
	}
	
	public void switchPokemon() {
		if (this.choosingPokemon && this.pokeMenu.isSwitching()) {
			this.myPokemon = this.pokeMenu.getSelected();
			this.choosingPokemon = false;
			this.switching = true;
			this.animationTurn = true;
			this.cpuSelectedMove = this.wildPokemon.pickMove(this.myPokemon.getType());
		}
	}
	
	// create method to convert wild to mine if caught
	public void convertPKMN() {
		
	}
	
	public void resetFlags() {
		this.choosingAction = true;
		this.choosingAttack = false;
		this.running = false;
		this.attacking = false;
		this.mySelectedMove = null;
		this.cpuSelectedMove = null;
		this.asUI.resetChoice();
		this.battleUI.resetChoice();
		this.pHasMoved = false;
		this.cpuHasMoved = false;
		this.animationTurn = false;
		this.choosingPokemon = false;
		this.switching = false;
	}
	
	public void calculateMyDamage() {
		if (this.mySelectedMove.getAtk()) {
			int damage = this.myPokemon.calculateDMG(this.wildPokemon.getType(), this.wildPokemon
					, this.mySelectedMove);
				System.out.println("Damage: "+damage);
				System.out.println("Charmander uses "+this.mySelectedMove.getName());
				this.wildPokemon.takeDamage(damage, this.mySelectedMove.getLifeSteal(), this.mySelectedMove);
		}
	}
	
	public void calculateCpuDamage() {
		if (this.cpuSelectedMove.getAtk()) { 
			int damage = this.wildPokemon.calculateDMG(this.myPokemon.getType(), this.myPokemon
				, this.cpuSelectedMove);
			
			System.out.println("Bulbasaur uses "+this.cpuSelectedMove.getName());
			System.out.println("Deals damage: "+damage);
			this.myPokemon.takeDamage(damage, this.cpuSelectedMove.getLifeSteal(), this.cpuSelectedMove);
			}
	}
	
	public void playSfx(Moves move) {
		if (move != null) {
			if (this.frameCounterUniversal <= 1 && move.getStatus() 
				&& !move.getBuff()) {
			this.gp.playSE(3);
			}
			else if (this.frameCounterUniversal <= 1){
				this.gp.playSE(5);
			}
		}
		
//		if (this.sfxTally == 3) {
//			this.sfxTally = 0;
//			this.gp.stopSE();
//		}
	}
	
	public void myStatusMove() {
		if (this.mySelectedMove.getStatus()) {
			if (this.mySelectedMove.getBuff()) {
				this.myPokemon.applyBuff(this.mySelectedMove);
			}
			else if (!this.mySelectedMove.getBuff()) {
				this.wildPokemon.applyDebuff(this.mySelectedMove);
			}
		}
	}
	
	public void cpuStatusMove() {
		if (this.cpuSelectedMove.getStatus()) {
			if (this.cpuSelectedMove.getBuff()) {
				this.wildPokemon.applyBuff(this.cpuSelectedMove);
			}
			else if (!this.cpuSelectedMove.getBuff()) {
				this.myPokemon.applyDebuff(this.cpuSelectedMove);
			}
		}
	}
	
	public void checkMeFainted() {
//		if (this.myPokemon.getCurrentHP() <= 0) {
//			for (int i = 0; i < this.gp.player.getPokemons().length; i++) {
//				if (this.gp.player.getPokemons()[i] != null 
//						&& this.gp.player.getPokemons()[i].getCurrentHP() >= 0) {
//					this.myPokemon = this.gp.player.getPokemons()[i];
//					this.pokeMenu.updateSelection(this.myPokemon);
//				}
//			}
//		}
	}
	
	public boolean getInBattle() {
		return this.inBattle;
	}
	
	public void update() {
		spawnPokemon();
		
		if (this.gp.keyH.escapePressed()) {
			resetFlags();
		}
		
		if (choosingAction) {
			this.asUI.update();
			playerAction();
		}
		
		else if (choosingAttack) {
			this.battleUI.update(this.myPokemon.getMoves());
			selectMove();
		}
		
		else if (choosingPokemon) {
			this.pokeMenu.update(null);
			switchPokemon();
		}
		
		else if (running) {
			this.inBattle = false;
			this.wildPokemon = null;
			this.myPokemon = null;
			return;
		}
		
		else if ((attacking || switching) && this.cpuHasMoved && this.pHasMoved) {
			if (this.myPokemon.getSpeed() >= this.wildPokemon.getSpeed() && attacking) {
				this.calculateMyDamage();
				this.myStatusMove();
				if (this.wildPokemon.getCurrentHP() <= 0) {
					this.inBattle = false;
					this.gp.player.getPokemon(0).gainEXP(this.wildPokemon.getLevel(), this.wildPokemon.getEvYield(), this.wildPokemon.getEvStat());
					this.wildPokemon = null;
					this.myPokemon = null;
					return;
				}
				this.calculateCpuDamage();
				this.cpuStatusMove();
			}
			else if (this.switching) {
				this.calculateCpuDamage();
				this.cpuStatusMove();
			}
			else if (this.myPokemon.getSpeed() < this.wildPokemon.getSpeed() && attacking){
				this.calculateCpuDamage();
				this.cpuStatusMove();
				if (this.myPokemon.getCurrentHP() <= 0) {
					for (int i = 0; i < this.gp.player.getPokemons().length; i++) {
						if (this.gp.player.getPokemons()[i] != null) {
							if (this.gp.player.getPokemons()[i].equals(this.myPokemon)) {
								System.out.println("Yuhu");
								this.gp.player.killPokemon(i);
								this.inBattle = false;
								this.wildPokemon = null;
								this.myPokemon = null;
								for (int j = 0; j < this.gp.player.getPokemons().length; j++) {
									if (this.gp.player.getPokemons()[j] != null) {
										this.gp.pokeMenu.update(this.gp.player.getPokemons()[j]);
										break;
									}
								}
								return;
							}
						}
					}
				}
				this.calculateMyDamage();
				this.myStatusMove();
			}
			resetFlags();
		}
		
		
		
		// else if (catch pokemon?)
		// 100% success rate when low hp, 50% otherwise 
		// if fail call cpu moves, reset flags
		// if succeed copy to myPokemon object and add to inventory
		// capturing a pokemon is a wildPokemon method
		
		
		
		// if (asUI.fight, bring up fight menu?)
		// if (asUI.run, escape from battle?)
		
		// calculating damages
		// calculate damage dealt here, but do not update health bar until animations finish playing
		// for now, no animations, just time lag as placeholder for animations
		
		// if boolean flag from draw method "Faggot500" is triggered, then player will update, but not enemy
		// e.g., player has just finished attacking, enemy health bar decreases
		//		then enemy starts attacking, my health bar decreases >>> so doesn't decrease simultaneously 
		
		// if statement, if attacking turn?
		this.myPokemon.update();
		this.wildPokemon.update();
			
		// temporary
		if (this.myPokemon.getSpeed() >= this.wildPokemon.getSpeed()) {
			if (this.pHasMoved) {
				this.enemyHP.update(this.wildPokemon.getCurrentHP(), this.wildPokemon.getHp(), 
					this.wildPokemon.getName(), this.wildPokemon.getLevel());
			}
			if (this.cpuHasMoved) {
				this.myHP.update(this.myPokemon.getCurrentHP(), this.myPokemon.getHp(), 
						this.myPokemon.getName(), this.myPokemon.getLevel());
			}
		}
		
		if (!this.animationTurn) {
			this.myHP.update(this.myPokemon.getCurrentHP(), this.myPokemon.getHp(), 
				this.myPokemon.getName(), this.myPokemon.getLevel());
		
			this.enemyHP.update(this.wildPokemon.getCurrentHP(), this.wildPokemon.getHp(), 
					this.wildPokemon.getName(), this.wildPokemon.getLevel());
		}
		
//		checkMeFainted();
		
		if (this.myPokemon.getCurrentHP() <= 0) {
			for (int i = 0; i < this.gp.player.getPokemons().length; i++) {
				if (this.gp.player.getPokemons()[i] != null) {
					if (this.gp.player.getPokemons()[i].equals(this.myPokemon)) {
						System.out.println("Yuhu");
						this.gp.player.killPokemon(i);
						this.inBattle = false;
						this.wildPokemon = null;
						this.myPokemon = null;
						for (int j = 0; j < this.gp.player.getPokemons().length; j++) {
							if (this.gp.player.getPokemons()[j] != null) {
								this.gp.pokeMenu.update(this.gp.player.getPokemons()[j]);
								break;
							}
						}
						return;
					}
				}
			}
		}
		
		if (this.wildPokemon.getCurrentHP() <= 0) {
			this.inBattle = false;
			this.gp.player.getPokemon(0).gainEXP(this.wildPokemon.getLevel(), this.wildPokemon.getEvYield(), this.wildPokemon.getEvStat());
			this.wildPokemon = null;
			this.myPokemon = null;
			return;
		}
		else {
			this.inBattle = true;
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(this.battleBackground, this.startX, this.startY, this.bgWidth, this.bgHeight, null);
		if (choosingAction) {
			this.asUI.draw(g2);
		}
		if (choosingAttack) {
			this.battleUI.draw(g2);
		}

		this.myHP.draw(g2);
		this.enemyHP.draw(g2);
		
		// if (!attackAnimations)
		if (!this.animationTurn) {
			this.myPokemon.draw(g2);
			this.wildPokemon.draw(g2);
		}
		else if (this.animationTurn) {
			
			if (this.frameCounter == 7) {
				this.spriteNum = 1;
			}
			
			if (this.frameCounter == 14) {
				this.spriteNum = 0;
				this.frameCounter = 0;
			}
			
			this.frameCounter++;
			this.frameCounterUniversal++;
			
			
			if (this.myPokemon.getSpeed() >= this.wildPokemon.getSpeed()) {
				if (!this.pHasMoved) {
					if (this.mySelectedMove != null) {
						playSfx(this.mySelectedMove);
					}
					this.myPokemon.draw(g2); // outside the switch case because always present
					switch(this.spriteNum) {
					case 0:
						this.wildPokemon.draw(g2);
						break;
					case 1:
						break;
					}
				}
				if (this.pHasMoved) {
					playSfx(this.cpuSelectedMove);
					this.wildPokemon.draw(g2); // outside the switch case because always present
					switch(this.spriteNum) {
					case 0:
						this.myPokemon.draw(g2);
						break;
					case 1:
						break;
					}
				}
				if (this.frameCounterUniversal >= 90 && !this.pHasMoved) {
					this.pHasMoved = true;
					this.frameCounter = 0;
					this.frameCounterUniversal = 0;
				}
				else if (this.frameCounterUniversal >= 90 && this.pHasMoved) {
					this.cpuHasMoved = true;
					this.frameCounter = 0;
					this.frameCounterUniversal = 0;
				}
			}
			else {
				if (!this.cpuHasMoved) {
					playSfx(this.cpuSelectedMove);
					this.wildPokemon.draw(g2);
					switch(this.spriteNum) {
					case 0:
						this.myPokemon.draw(g2);
						break;
					case 1:
						break;
					}
				}
				if (this.cpuHasMoved) {
					playSfx(this.mySelectedMove);
					this.myPokemon.draw(g2);
					switch(this.spriteNum) {
					case 0:
						this.wildPokemon.draw(g2);
						break;
					case 1:
						break;
					}
				}
				if (this.frameCounterUniversal >= 90 && !this.cpuHasMoved) {
					this.cpuHasMoved = true;
					this.frameCounter = 0;
					this.frameCounterUniversal = 0;
				}
				else if (this.frameCounterUniversal >= 90 && this.cpuHasMoved) {
					this.pHasMoved = true;
					this.frameCounter = 0;
					this.frameCounterUniversal = 0;
				}
			}
		}
		if (choosingPokemon) {
			this.pokeMenu.draw(g2);
		}
		
	}
}

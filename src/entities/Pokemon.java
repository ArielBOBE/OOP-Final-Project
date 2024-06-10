package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;

public class Pokemon {
	private GamePanel gp;
	private TypeChecker tCheck;
	private Random rand = new Random();
	
	private BufferedImage[] backSprites, frontSprites;
	
	private String nickName;
	private String name;
	private String type;
	private int level;
	
	// base stats inherent to pokemon of that species
	private int baseAtk, baseSpAtk, baseDef, baseSpDef, baseSpeed, baseHp;
	
	// stats in battle
	private int currentHP;
	private int currentAtk;
	private int currentSpAtk;
	private int currentDef;
	private int currentSpDef;
	private int currentSpeed;
	private String nature;
	
	private int atkStat, spAtkStat, defStat, spDefStat, speedStat, hpStat;
	private int atkIV, spAtkIV, defIV, spDefIV, speedIV, hpIV;
	private int atkEV, spAtkEV, defEV, spDefEV, speedEV, hpEV;
	private int totalEV;
	
	// stat modifiers in battle
	private double atkMod = 1, spAtkMod = 1, defMod = 1, spDefMod = 1, speedMod = 1, accMod = 1;
	private int atkStage = 0, spAtkStage = 0, defStage = 0, spDefStage = 0, speedStage = 0, accStage = 0;
	private Moves[] moveSet;
	private String statusCond;
	private boolean attackMove;
	private boolean fainted;

	
	public Pokemon(GamePanel gp, AllSprites allSprites, 
			String name, String type, int level, String nature) {
		this.gp = gp;
		this.name = name;
		this.type = type;
		this.level = level;
		this.nature = nature;
		this.moveSet = new Moves[4];
		setBaseStats();
		calculateStats();
		defaultHp();
		setSprites(allSprites);
		this.tCheck = new TypeChecker();
	}
	
	public Pokemon(GamePanel gp, AllSprites allSprites, 
			String name) {
		this.gp = gp;
		this.name = name;
		this.moveSet = new Moves[4];
		setSprites(allSprites);
		this.tCheck = new TypeChecker();
	}
	
	public void setSprites(AllSprites allSprites) {
		switch(name) {
		case "Charmander":
			this.backSprites = allSprites.getCharmanderSpritesB();
			this.frontSprites = allSprites.getCharmanderSpritesB();
			break;
		case "Bulbasaur":
			this.backSprites = allSprites.getBulbasaurSpritesB();
			this.frontSprites = allSprites.getBulbasaurSpritesF();
			break;
		}
	}
	
	public void setBattleStats() {
		this.currentHP = this.hpStat;
		this.currentAtk = this.atkStat;
		this.currentSpAtk = this.spAtkStat;
		this.currentDef = this.defStat;
		this.currentSpDef = this.spDefStat;
		this.currentSpeed = this.speedStat;
	}
	
	public void getMoveSet() {
		for (int i = 0; i < this.moveSet.length; i++) {
			
			if (this.moveSet[i] != null) {
				System.out.println(i+1+": "+this.moveSet[i].getName());
			}
			else {
				System.out.println(i+1+": "+null);
			}
		}
	}
	
	public Moves[] getMoves() {
		return this.moveSet;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setMoves(int i, Moves move) {
		this.moveSet[i] = move;
	}
	
	public BufferedImage[] getBackSprites() {
		return this.backSprites;
	}
	
	public BufferedImage[] getFrontSprites() {
		return this.frontSprites;
	}
	
	public void printStats() {
		System.out.println("hp: "+this.hpStat);
		System.out.println("atk: "+this.atkStat);
		System.out.println("spAtk: "+this.spAtkStat);
		System.out.println("def: "+this.defStat);
		System.out.println("spDef: "+this.spDefStat);
		System.out.println("speed: "+this.speedStat);
		System.out.println("CURRENT HP IS: "+this.currentHP);
	}
	
	public void printCurrentStats() {
		System.out.println("hp: "+this.currentHP);
		System.out.println("atk: "+this.currentAtk);
		System.out.println("spAtk: "+this.currentSpAtk);
		System.out.println("def: "+this.currentDef);
		System.out.println("spDef: "+this.currentSpDef);
		System.out.println("speed: "+this.currentSpeed);
	}
	
	public void printIVs() {
		System.out.println("HP: "+this.hpIV);
		System.out.println("ATK: "+this.atkIV);
		System.out.println("SPATK: "+this.spAtkIV);
		System.out.println("DEF: "+this.defIV);
		System.out.println("SPDEF: "+this.spDefIV);
		System.out.println("SPEED: "+this.speedIV);
	}
	
	public void setType() {
		switch(this.name) {
		case"Bulbasaur":
			this.type = "Grass";
			break;
		case"Charmander":
			this.type = "Fire";
			break;
		}
	}
	
	public void setBaseStats() {
		switch(this.name) {
		case"Charmander":
			this.baseHp = 39;
			this.baseAtk = 52;
			this.baseSpAtk = 60;
			this.baseDef = 43;
			this.baseSpDef = 50;
			this.baseSpeed = 65;
			break;
		case"Bulbasaur":
			this.baseHp = 45;
			this.baseAtk = 49;
			this.baseSpAtk = 65;
			this.baseDef = 49;
			this.baseSpDef = 65;
			this.baseSpeed = 45;
			break;
		case"Squirtle":
			this.baseHp = 44;
			this.baseAtk = 48;
			this.baseSpAtk = 50;
			this.baseDef = 65;
			this.baseSpDef = 64;
			this.baseSpeed = 43;
			break;
		}
	}
	
	public void printBaseStats() {
		System.out.println("HP: "+this.baseHp);
		System.out.println("ATK: "+this.baseAtk);
		System.out.println("SpAtk: "+this.baseSpAtk);
		System.out.println("Def: "+this.baseDef);
		System.out.println("SpDef: "+this.baseSpDef);
		System.out.println("Speed: "+this.baseSpeed);
		
	}
	
	public void setLevel(int i) {
		this.level = i;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getNature() {
		return this.nature;
	}
	
	public void setNature(String nature) {
		this.nature = nature;
	}
	
	public void calculateStats() {
		this.hpStat = (((2 * this.baseHp + this.hpIV + this.hpEV/4)*this.level)/100) + this.level + 10;
		this.defStat = (((2 * this.baseDef + this.defIV + this.defEV/4)*this.level)/100) + 5;
		this.spDefStat = (((2 * this.baseSpDef + this.spDefIV + this.spDefEV/4)*this.level)/100) + 5;
		switch(this.nature) {
		case "Adamant":
			this.atkStat = (int) (((((2 * this.baseAtk + this.atkIV + this.atkEV/4)*this.level)/100) + 5)*1.1);
			this.spAtkStat = (int) (((((2 * this.baseSpAtk + this.spAtkIV + this.spAtkEV/4)*this.level)/100) + 5)*0.9);
			this.speedStat = (int) (((((2 * this.baseSpeed + this.speedIV + this.speedEV/4)*this.level)/100) + 5));
			break;
		case "Modest":
			this.atkStat = (int) (((((2 * this.baseAtk + this.atkIV + this.atkEV/4)*this.level)/100) + 5)*0.9);
			this.spAtkStat = (int) (((((2 * this.baseSpAtk + this.spAtkIV + this.spAtkEV/4)*this.level)/100) + 5)*1.1);
			this.speedStat = (int) (((((2 * this.baseSpeed + this.speedIV + this.speedEV/4)*this.level)/100) + 5));
			break;
		case "Timid":
			this.atkStat = (int) (((((2 * this.baseAtk + this.atkIV + this.atkEV/4)*this.level)/100) + 5)*0.9);
			this.spAtkStat = (int) (((((2 * this.baseSpAtk + this.spAtkIV + this.spAtkEV/4)*this.level)/100) + 5));
			this.speedStat = (int) (((((2 * this.baseSpeed + this.speedIV + this.speedEV/4)*this.level)/100) + 5)*1.1);
			break;
		case "Jolly":
			this.atkStat = (int) (((((2 * this.baseAtk + this.atkIV + this.atkEV/4)*this.level)/100) + 5));
			this.spAtkStat = (int) (((((2 * this.baseSpAtk + this.spAtkIV + this.spAtkEV/4)*this.level)/100) + 5)*0.9);
			this.speedStat = (int) (((((2 * this.baseSpeed + this.speedIV + this.speedEV/4)*this.level)/100) + 5)*1.1);
			break;
		}
	}
	
	public boolean isFainted() {
		return this.fainted;
	}
	
	public void defaultHp() {
		this.currentHP = this.hpStat;
		this.currentAtk = this.atkStat;
		this.currentSpAtk = this.spAtkStat;
		this.currentDef = this.defStat;
		this.currentSpDef = this.spDefStat;
		this.currentSpeed = this.speedStat;
	}
	
	// getter methods for stats
	public int getHp() {
		return this.hpStat;
	}
	
	public int getCurrentHP() {
		return this.currentHP;
	}
	
	public int getCurrentDef() {
		return this.currentDef;
	}
	
	public int getCurrentSpDef() {
		return this.currentSpDef;
	}
	
	public int getAtk() {
		return this.atkStat;
	}
	
	public int getSpAtk() {
		return this.spAtkStat;
	}
	
	public int getDef() {
		return this.defStat;
	}
	
	public int getSpDef() {
		return this.spDefStat;
	}
	
	public int getSpeed() {
		return this.speedStat;
	}
	
	// setter methods for stat EVs
	public void setHpEV(int evYield) {
		this.hpEV = this.hpEV + evYield;
	}
	
	public void setAtkEV(int evYield) {
		this.atkEV = this.atkEV + evYield;
	}
	
	public void setSpAtkEV(int evYield) {
		this.spAtkEV = this.spAtkEV + evYield;
	}
	
	public void setDefEV(int evYield) {
		this.defEV = this.defEV + evYield;
	}
	
	public void setSpDefEV(int evYield) {
		this.spDefEV = this.spDefEV + evYield;
	}
	
	public void setSpeedEV(int evYield) {
		this.speedEV = this.speedEV + evYield;
	}
	
	// methods for setting IVs
	public void setHpIV(int i) {
		this.hpIV = i;
	}
	
	public void setAtkIV(int i) {
		this.atkIV = i;
	}
	
	public void setSpAtkIV(int i) {
		this.spAtkIV = i;
	}
	
	public void setDefIV(int i) {
		this.defIV = i;
	}

	public void setSpDefIV(int i) {
		this.spDefIV = i;
	}
	
	public void setSpeedIV(int i) {
		this.speedIV = i;
	}
	
	
	// level setter for level up
	public void gainLevel() {
		this.level = this.level + 1;
		this.calculateStats();
	}
	
	public int calculateDMG(String enemyType, Pokemon enemy, Moves move) {
		String effective = tCheck.checkEffect(move.getType(), enemyType);
		double typeMultiplier = 1;
		int effectiveAttackStat = 1;
		int effectiveDefenseStat = 1;
		
		switch(effective) {
		case "Neutral":
			typeMultiplier = 1;
			break;
		case "No Effect":
			typeMultiplier = 0;
			break;
		case "Super Effective":
			typeMultiplier = 2;
			break;
		case "Not Effective":
			typeMultiplier = 0.5;
			break;
		case "NONE":
			typeMultiplier = 0;
			break;
		}
		
		if (move.getDamageType() == "Physical") {
			effectiveAttackStat = this.currentAtk;
			effectiveDefenseStat = enemy.getCurrentDef();
		}
		else if (move.getDamageType() == "Special") {
			effectiveAttackStat = this.currentSpAtk;
			effectiveDefenseStat = enemy.getCurrentSpDef();
		}
		
		if (move.getPower() == 0) {
			return 0;
		}
		
		double damage = (((((double)((2 * this.level)/5) +2) * move.getPower() * (double)((double)(effectiveAttackStat/effectiveDefenseStat)/50) + 2))
				* move.getSTAB(this.type) * typeMultiplier * (double)rand.nextInt(85, 101)/100);
		int numRoll = rand.nextInt(1, 101);
		if (numRoll <= move.getAccuracy() * this.accMod) {
			return (int) (damage+1);
		}
		return 0;
	}
	
	public String applyStatus(Moves move, String enemyStatus) {
		if (move.getStatusEffect() != "None" && enemyStatus == "None") {
			int rng = rand.nextInt(1, 101);
			if (rng <= move.getStatusProc()) {
				return move.getStatusEffect();
			}
		}
		return "None";
	}
	
	public String getType() {
		return this.type;
	}
	
	// takes apply status as input
	public void setStatusCond(String status) {
		this.statusCond = status;
	}
	
	public void applyBuff(Moves effectMove) {
		switch(effectMove.getStatusTarget()) {
		case "atk":
			this.atkStage += effectMove.getMultiplier();
			break;
		case "spAtk":
			this.spAtkStage += effectMove.getMultiplier();
			break;
		case "def":
			this.defStage += effectMove.getMultiplier();
			break;
		case "spDef":
			this.spDefStage += effectMove.getMultiplier();
			break;
		case "speed":
			this.speedStage += effectMove.getMultiplier();
			break;
		case "acc":
			this.accStage += effectMove.getMultiplier();
			break;
		}
		
		switch(effectMove.getStatusTarget()) {
		case "atk":
			if (this.atkStage > 0) {
				this.atkMod = 1 + this.atkStage * 0.5;
			}
			else {
				this.atkMod = (double) 1/(1 + Math.abs(this.atkStage) * 0.5);
			}
			this.currentAtk = (int) (this.atkStat * this.atkMod);
			break;
		case "spAtk":
			if (this.spAtkStage > 0) {
				this.spAtkMod = 1 + this.spAtkStage * 0.5;
			}
			else {
				this.spAtkMod = (double) 1/(1 + Math.abs(this.spAtkStage) * 0.5);
			}
			this.currentSpAtk = (int) (this.atkStat * this.spAtkMod);
			break;
		case "def":
			if (this.defStage > 0) {
				this.defMod = 1 + this.defStage * 0.5;
			}
			else {
				this.defMod = (double) 1/(1 + Math.abs(this.defStage) * 0.5);
			}
			this.currentDef = (int) (this.defStat * this.defMod);
			break;
		case "spDef":
			if (this.spDefStage > 0) {
				this.spDefMod = 1 + this.spDefStage * 0.5;
			}
			else {
				this.spDefMod = (double) 1/(1 + Math.abs(this.spDefStage) * 0.5);
			}
			this.currentSpDef = (int) (this.spDefStat * this.spDefMod);
			break;
		case "speed":
			if (this.speedStage > 0) {
				this.speedMod = 1 + this.speedStage * 0.5;
			}
			else {
				this.speedMod = (double) 1/(1 + Math.abs(this.speedStage) * 0.5);
			}
			this.currentSpeed = (int) (this.speedStat * this.speedMod);
			break;
		case "acc":
			if (this.accStage > 0) {
				this.accMod = 1 + this.accStage * 0.5;
			}
			else {
				this.accMod = (double) 1/(1 + Math.abs(this.accStage) * 0.5);
			}
			break;
		}
	}
	
	public void applyDebuff(Moves effectMove) {
		switch(effectMove.getStatusTarget()) {
		case "atk":
			this.atkStage -= effectMove.getMultiplier();
			break;
		case "spAtk":
			this.spAtkStage -= effectMove.getMultiplier();
			break;
		case "def":
			this.defStage -= effectMove.getMultiplier();
			break;
		case "spDef":
			this.spDefStage -= effectMove.getMultiplier();
			break;
		case "speed":
			this.speedStage -= effectMove.getMultiplier();
			break;
		}
		
		switch(effectMove.getStatusTarget()) {
		case "atk":
			if (this.atkStage > 0) {
				this.atkMod = 1 + this.atkStage * 0.5;
			}
			else {
				this.atkMod = (double) 1/(1 + Math.abs(this.atkStage) * 0.5);
			}
			this.currentAtk = (int) (this.atkStat * this.atkMod);
			break;
		case "spAtk":
			if (this.spAtkStage > 0) {
				this.spAtkMod = 1 + this.spAtkStage * 0.5;
			}
			else {
				this.spAtkMod = (double) 1/(1 + Math.abs(this.spAtkStage) * 0.5);
			}
			this.currentSpAtk = (int) (this.atkStat * this.spAtkMod);
			break;
		case "def":
			if (this.defStage > 0) {
				this.defMod = 1 + this.defStage * 0.5;
			}
			else {
				this.defMod = (double) 1/(1 + Math.abs(this.defStage) * 0.5);
			}
			this.currentDef = (int) (this.defStat * this.defMod);
			break;
		case "spDef":
			if (this.spDefStage > 0) {
				this.spDefMod = 1 + this.spDefStage * 0.5;
			}
			else {
				this.spDefMod = (double) 1/(1 + Math.abs(this.spDefStage) * 0.5);
			}
			this.currentSpDef = (int) (this.spDefStat * this.spDefMod);
			break;
		case "speed":
			if (this.speedStage > 0) {
				this.speedMod = 1 + this.speedStage * 0.5;
			}
			else {
				this.speedMod = (double) 1/(1 + Math.abs(this.speedStage) * 0.5);
			}
			this.currentSpeed = (int) (this.speedStat * this.speedMod);
			break;
		case "acc":
			if (this.accStage > 0) {
				this.accMod = 1 + this.accStage * 0.5;
			}
			else {
				this.accMod = (double) 1/(1 + Math.abs(this.accStage) * 0.5);
			}
			break;
		}

	}

	// takes damage calc as input
	public void takeDamage(int damage, double lifeSteal, Moves effectMove) {
		if (lifeSteal > 0) {
			this.currentHP = (int) (this.currentHP * (1 - lifeSteal));
		}
		if (this.statusCond != null) {
			switch(this.statusCond) {
		case "Burn":
			this.currentHP = this.currentHP - this.hpStat/10;
//			this.currentAtk = this.atkStat/2;
			break;
		case "Poison":
			this.currentHP = this.currentHP - this.hpStat/10;
			break;
		case "Paralyze":
//			this.currentSpeed = this.speedStat/2;
			break;
			}	
		}
		
		this.currentHP = this.currentHP - damage;
	}
	
	public void lifeSteal(int lifeSteal) {
		this.currentHP = this.currentHP + lifeSteal;
	}
	
}	

package entities;

public class Moves {
	private String name;
	private String type;
	
	// for damaging moves
	private int power = 0;
	private int accuracy = 0;
	private String damageType = "None";
	
	// for buff/debuffs
	private boolean buff = false;
	private int multiplier = 0;
	private boolean attack = false;
	private boolean status = false;
	private String statTarget = "None";
	
	// for status effects
	private String statusEffect = "None";
	private int statusProc = 0;
	
	// for DoTs/LifeSteal
	private double dmgPcntg = 0;
	private double lifeSteal = 0; 
	
	// constructor for moves that are Life Steals
	public Moves(String name, String type, boolean attack, int power, double dmgPcntg, double lifeSteal, int accuracy, String damageType) {
		this.name = name;
		this.type = type;
		this.attack = attack;
		this.power = power;
		this.dmgPcntg = dmgPcntg;
		this.setLifeSteal(lifeSteal);
		this.accuracy = accuracy; 
		this.damageType = damageType;
	}

	// constructor for moves that buff/debuff
	public Moves(String name,  String type, boolean status, int multiplier, int accuracy, boolean buff, String stat) {
		this.name = name;
		this.type = type;
		this.multiplier = multiplier;
		this.status = status;
		this.buff = buff;
		this.statTarget = stat;
	}
	// constructor for moves that deal head-on damage
	public Moves(String name, String type, boolean attack, int power, int accuracy, 
				String damageType) {
		this.name = name;
		this.type = type;
		this.attack = attack;
		this.power = power;
		this.accuracy = accuracy; 
		this.damageType = damageType;
		}
	
	// constructor for moves that deal head-on damage with status effects that can be procced
	public Moves(String name, String type, boolean attack, int power, int accuracy, 
			String damageType, String statusEffect, int statusProc) {
		this.name = name;
		this.type = type;
		this.attack = attack;
		this.power = power;
		this.accuracy = accuracy; 
		this.damageType = damageType;
		this.statusEffect = statusEffect;
		this.statusProc = statusProc;
	}
	
	public double getSTAB(String type) {
		if (this.type == type) {
			return 1.5;
		}
		return 1;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public boolean getBuff() {
		return this.buff;
	}
	
	public boolean getAtk() {
		return this.attack;
	}
	
	public String getStatusEffect() {
		return this.statusEffect;
	}
	
	public String getStatusTarget() {
		return this.statTarget;
	}
	
	public String getName() {

		return this.name;
	}
	
	public String getType() {
		return this.type;
	}

	public int getPower() {
		return power;
	}

	public int getStatusProc() {
		return statusProc;
	}

	public double getDmgPcntg() {
		return dmgPcntg;
	}

	public double getLifeSteal() {
		return lifeSteal;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public String getDamageType() {
		return damageType;
	}
	
	public void setLifeSteal(double lifeSteal) {
		this.lifeSteal = lifeSteal;
	}
}

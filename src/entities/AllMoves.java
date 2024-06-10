package entities;

public class AllMoves {
	private Moves[] allMoves;
	
	// stat targets, "atk", "sp.atk", "sp.def", "def", "speed", "acc"
	
	public AllMoves() {
		this.allMoves = new Moves[21];
		this.allMoves[0] = new Moves("Scratch", "Normal", true, 40, 100, "Physical");
		this.allMoves[1] = new Moves("Growl", "Normal", true, 1, 100,false, "atk");
		this.allMoves[2] = new Moves("Ember", "Fire", true, 40, 100, "Special", "Burn", 10);
		this.allMoves[3] = new Moves("Smoke Screen", "Normal", true, 1, 100, false, "acc");
		this.allMoves[4] = new Moves("Dragon Breath", "Dragon", true, 60, 100, "Special", "Paralyze", 30);
		this.allMoves[5] = new Moves("Fire Fang", "Fire", true, 65, 100, "Physical", "Burn", 10);
		this.allMoves[6] = new Moves("Tackle", "Normal", true, 40, 100, "Physical");
		this.allMoves[7] = new Moves("Vine Whip", "Grass", true, 45, 100, "Physical");
		this.allMoves[8] = new Moves("Growth", "Normal", true, 1, 100, "sp.atk"); 
		this.allMoves[9] = new Moves("Leech Seed", "Grass", true, 0, 0.125, 0.125, 90,"sp.atk");
		this.allMoves[10] = new Moves("Poison Powder", "Poison", true, 0, 75, "sp.atk", "Poison", 75); 
		this.allMoves[11] = new Moves("Sleep Powder", "Normal", true, 0, 75, "sp.atk", "Sleep", 75); 
		this.allMoves[12] = new Moves("Seed Bomb", "Grass", true, 80, 100, "Physical");
		this.allMoves[13] = new Moves("Tackle", "Normal", true, 40, 100, "Physical");
		this.allMoves[14] = new Moves("Tail Whip", "Normal", true, 1, 100, false, "def");
		this.allMoves[15] = new Moves("Water Gun", "Water", true, 40, 100, "Special");
		this.allMoves[16] = new Moves("Growl", "Water", true, 1, 100, true, "def");
		this.allMoves[17] = new Moves("Rapid Spin", "Normal", true, 50, 100, "Physical");
		this.allMoves[18] = new Moves("Bite", "Dark", true, 60, 100, "Physical", "Flinch", 30);
		this.allMoves[19] = new Moves("Water Pulse", "Water", true, 60, 100, "Water Pulse", "Flinch", 30);
		this.allMoves[20] = new Moves("Razor Leaf", "Grass", true, 55, 95, "Water Pulse", "Flinch", 30);
	}
	
	public Moves getMove(int i) {
		return this.allMoves[i];
	}
	
	public int getSize() {
		return this.allMoves.length;
	}
	
}

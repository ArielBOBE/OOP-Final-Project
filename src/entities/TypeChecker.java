package entities;

import java.util.HashMap;
import java.util.Arrays;

public class TypeChecker {
	HashMap<String, String[]> attackChart;
	HashMap<String, String[]> ignoreChart;
	
	public TypeChecker() {
		this.attackChart = new HashMap<>();
		this.attackChart.put("Bug", new String[] {"Ground", "Psychic", "Grass"});
		this.attackChart.put("Dragon", new String[] {"Dragon"});
		this.attackChart.put("Electric", new String[] {"Water"});
		this.attackChart.put("Fighting", new String[] {"Normal", "Rock", "Dark"});
		this.attackChart.put("Fire", new String[] {"Grass", "Ice", "Fire"});
		this.attackChart.put("Flying", new String[] {"Fighting", "Grass", "Bug"});
		this.attackChart.put("Fighting", new String[] {"Normal", "Rock"});
		this.attackChart.put("Ghost", new String[] {"Ghost"});
		this.attackChart.put("Grass", new String[] {"Water", "Ground", "Grass"});
		this.attackChart.put("Ground", new String[] {"Rock", "Fire", "Poison", "Electric"});
		this.attackChart.put("Ice", new String[] {"Grass", "Flying", "Dragon"});
		this.attackChart.put("Normal", new String[] {});
		this.attackChart.put("Poison", new String[] {"Grass"});
		this.attackChart.put("Rock", new String[] {"Fire", "Bug"});
		this.attackChart.put("Water", new String[] {"Fire", "Rock", "Ground"});
		this.attackChart.put("Dark", new String[] {"Ghost", "Psychic"});
		
		this.ignoreChart = new HashMap<>();
		this.ignoreChart.put("Ground", new String[] {"Flying"});
		this.ignoreChart.put("Normal", new String[] {"Ghost"});
		this.ignoreChart.put("Ghost", new String[] {"Normal"});
		this.ignoreChart.put("Fighting", new String[] {"Ghost"});
		this.ignoreChart.put("Psychic", new String[] {"Dark"});
	}
	
	public boolean superEffective(String atkType, String defType) {
		String[] currentEffective = this.attackChart.get(atkType);
		for (int i = 0; i < currentEffective.length; i++) {
			if(currentEffective[i] == defType) { 
				return true;
			}
		}
		return false;
	}
	
	public boolean notEffective(String atkType, String defType) {
		String[] currentEffective = this.attackChart.get(defType);
		for (int i = 0; i < currentEffective.length; i++) {
			if(currentEffective[i] == atkType) { 
				return true;
			}
		}
		return false;
	}
	
	public boolean typeNeutral(String atkType, String defType) {
		if(!notEffective(atkType, defType) && superEffective(atkType, defType)) {
			return false;
		}
		else if (notEffective(atkType, defType) && !superEffective(atkType, defType)) {
			return false;
		}
		return true;
	}
	
	public boolean noEffect(String atkType, String defType) {
		String[] currentIgnore = this.ignoreChart.get(atkType);
		
		if (currentIgnore == null) {
			return false;
		}
		for (int i = 0; i < currentIgnore.length; i++) {
			if (defType == currentIgnore[i]) {
				return true;
			}
		}
		return false;
	}
	
	public String checkEffect(String atkType, String defType) {
		if (noEffect(atkType, defType)) {
			return "No Effect";
		}
		if (typeNeutral(atkType, defType)) {
			return "Neutral";
		}
		if (superEffective(atkType, defType)) {
			return "Super Effective";
		}
		if (notEffective(atkType, defType)) {
			return "Not Effective";
		}
		return "NONE";
	}
	
	// call noEffect first, then SE, NE, tN
}

package entities;

public interface Trainable {
	public void gainEXP(int enemyLvl, int evYield, String evStat);
	public void gainEV(int evYield, String evStat);
	public void levelUp();
}

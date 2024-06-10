package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entities.Player;
import maps.TileManager;
import pokemon.Spawner;

public class GamePanel extends JPanel implements Runnable {
	
	// Screen settings
	private int tileSize = 50;
	
	private int maxScreenCol = 22;
	private int maxScreenRow = 18;
	
	private final int screenWidth = tileSize * maxScreenCol; // 1100
	private final int screenHeight = tileSize * maxScreenRow; // 900
	
	// FPS
	private int fps = 60;
	
	// game state
	private boolean inFight = false;
	
	private boolean bgMusic = false;
	private boolean battleMusic = false;
	
	// Thread
	private Thread gameThread;
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void setInfight(boolean fight) {
		this.inFight = fight;
	}
	
	// Initializing Classes
	MouseHandler mouseH = new MouseHandler();
	KeyHandler keyH = new KeyHandler();
	TileManager tM = new TileManager(this, keyH);
	Player player = new Player(this, keyH, tM.getStartCol(), tM.getStartRow(), 
			tM.getScreenCol(), tM.getScreenRow());
	public CollisionChecker2 cChecker = new CollisionChecker2(this, this.keyH);
	Sound sound = new Sound();
	Sound sound2 = new Sound();
	Spawner spawner = new Spawner(this, this.keyH);
	public PokemonMenu pokeMenu = new PokemonMenu(this, this.mouseH);
	BattleHandler bH = null;
	// Constructing gamePanel
	public GamePanel() {
		// set window size
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		
		// all drawings from this component will be done in an off screen painting buffer (makes faster) 
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
		this.setFocusable(true);
	}
	
	// getter methods
	public int getScreenWidth() {
		return this.screenWidth;
	}
	
	public int getScreenHeight() {
		return this.screenHeight;
	}
	
	public int getTileSize() {
		return this.tileSize;
	}
	
	public int getFPS() {
		return this.fps;
	}
	
	public int getMaxScreenRow() {
		return this.maxScreenRow;
	}
	
	public int getMaxScreenCol() {
		return this.maxScreenCol;
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/60; // draws once every 60 frames
		double nextDrawTime = System.nanoTime() + drawInterval; // will draw at current + interval
		
		while (gameThread != null) {
			update();
			repaint();
			
			// setting frames per second to 60
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() {
		// have music play here
		// if in battle false
		// if not in battle play
		if(!this.bgMusic && !this.inFight) {
			playMusic(0);
			this.bgMusic = true;
		}

		// when exiting a fight this.music play is set to false to play it again
		// ONLY PLAY ONCE
		if (!this.inFight) {
			tM.update();
			player.update(tM.getWorldWidth(), tM.getWorldHeight());
			
			int counter = 0;
			for (int i = 0; i < this.player.getPokemons().length; i++) {
				if (this.player.getPokemons()[i] != null) {
					counter++;
				}
			}
			if (counter > 0) {
				spawner.update(player);
			}
			
//			for (int i = 0; i < 2; i++) {
//				System.out.println(this.player.getPokemons()[i].getName()+"'s selection: "+this.player.getPokemons()[i].getSelected());
//			}
		}
		if (!this.inFight && this.battleMusic) {
			stopMusic2();
			this.battleMusic = false;
		}
		
		if (this.inFight) {
			this.bgMusic = false;
			stopMusic();
			if (!this.battleMusic) {
				playMusic2(2);
				this.battleMusic = true;
			}
			if (this.bH == null) {
				this.bH = new BattleHandler(this, this.spawner);
			}
			else {
				bH.update();
			}
			
			if (!bH.getInBattle()) {
				this.inFight = false;
				this.bH = null;
			}
//			for (int i = 0; i < 2; i++) {
//				System.out.println(this.player.getPokemons()[i].getName()+"'s selection: "+this.player.getPokemons()[i].getSelected());
//			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
				
		if (!this.inFight) {
			tM.draw(g2, player.getWorldX(), player.getWorldY(), player.getScreenX(), player.getScreenY());
			player.draw(g2);
			spawner.draw(g2, player);
		}
		
		if (this.inFight) {
//			System.out.println("BRAH");
			if (this.bH != null) {
				bH.draw(g2);
			}
		}		

	}
	
	public void playMusic(int i) {
		sound.setFile(i); // 0 for bgm
		sound.play();
		sound.loop();
	}
	
	public void playMusic2(int i) {
		sound2.setFile(i); // 2 for battle
		sound2.play();
		sound2.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void stopMusic2() {
		sound2.stop();
	}
	
	public void playSE(int i) {
		sound.setFileSE(i); // 1 for thud sound
		sound.playSE();
	}
	
	public void stopSE() {
		sound.stopSE();
	}
 }

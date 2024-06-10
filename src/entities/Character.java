package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Character {
	protected String name;
	protected boolean trainer;
	
	// attributes related to sprite
	protected String direction;
	protected BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
	protected int height;
	protected int width;
	protected int collideBoxSize;
	protected int spriteCounter;
	
	// attributes related to position
	protected int worldX;
	protected int worldY;
	protected double movementSpeed;
	
	// collision box
	protected Rectangle solidArea;
	protected boolean collisionOn;
}

package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	private boolean upPressed, downPressed, leftPressed, rightPressed, escapePressed, pPressed, KeyPress;
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_W) {
			this.upPressed = true;
			this.KeyPress = true;
		}
		if (keyCode == KeyEvent.VK_A) {
			this.leftPressed = true;
			this.KeyPress = true;
		}
		if (keyCode == KeyEvent.VK_S) {
			this.downPressed = true;
			this.KeyPress = true;
		}
		if (keyCode == KeyEvent.VK_D) {
			this.rightPressed = true;
			this.KeyPress = true;
		}
		if (keyCode == KeyEvent.VK_ESCAPE) {
			this.escapePressed = true;
		}
		if (keyCode == KeyEvent.VK_P) {
			this.pPressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
				
		if (keyCode == KeyEvent.VK_W) {
			this.upPressed = false;
		}
		if (keyCode == KeyEvent.VK_A) {
			this.leftPressed = false;
		}
		if (keyCode == KeyEvent.VK_S) {
			this.downPressed = false;
		}
		if (keyCode == KeyEvent.VK_D) {
			this.rightPressed = false;
		}
		if (keyCode == KeyEvent.VK_ESCAPE) {
			this.escapePressed = false;
		}
		if (keyCode == KeyEvent.VK_P) {
			this.pPressed = false;
		}
		
		if (this.upPressed == false && this.leftPressed == false && this.downPressed == false && 
				this.rightPressed == false) {
			this.KeyPress = false;
		}
		
	}
	
	public boolean KeyPress() {
		return this.KeyPress;
	}
	
	// get methods
	public boolean upPressed() {
		return this.upPressed;
	}
	
	public boolean downPressed() {
		return this.downPressed;
	}
	
	public boolean leftPressed() {
		return this.leftPressed;
	}
	
	public boolean rightPressed() {
		return this.rightPressed;
	}
	
	public boolean escapePressed() {
		return this.escapePressed;
	}
	
	public boolean pPressed() {
		return this.pPressed;
	}
	
	

}

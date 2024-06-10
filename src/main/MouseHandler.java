package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputAdapter;

public class MouseHandler implements MouseMotionListener, MouseListener{
	
	// some boolean variables so that you can do shit with it like hovering over button yup
	private boolean mouseClick;
	private int mouseX;
	private int mouseY;
	
	public int getMouseX() {
		return this.mouseX;
	}
	public int getMouseY() {
		return this.mouseY;
	}
	
	public boolean getClicked() {
		return this.mouseClick;
	}
	
	public void resetClicked() {
		this.mouseClick = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseX = e.getX();
		this.mouseY = e.getY();
//		System.out.println("(X, Y): ("+this.mouseX+","+this.mouseY+")");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseClick = true;
//		System.out.println("Click");
		// click represents the whole pressing and releasing motion
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("mouse is Pressed");
//		System.out.println("Press");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("Release");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

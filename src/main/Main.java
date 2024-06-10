package main;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		// creating top level window
		JFrame window = new JFrame();
		
		// lets the window properly close when user clicks on X button
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// prevents resizing
		window.setResizable(false);
		
		// sets window name
		window.setTitle("maliq and de essentials");
		
		// Makes it so location of window is not specified 
		// The window will be displayed at the center of the screen by default
		window.setLocation(100, 100);
	
		// adding gamePanel
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		// makes window visible
		window.setVisible(true);
		
		// starts the main game panel
		gamePanel.startGameThread();
	}
}

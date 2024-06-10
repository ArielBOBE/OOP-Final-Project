package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	Clip clip2;
	URL soundURL[] = new URL[6];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/route1BGM.wav");
		soundURL[1] = getClass().getResource("/sound/thudsoundmaybe.wav");
		soundURL[2] = getClass().getResource("/sound/BattleMusic.wav");
		soundURL[3] = getClass().getResource("/sound/stat down.wav");
		soundURL[4] = getClass().getResource("/sound/stat raise.wav");
		soundURL[5] = getClass().getResource("/sound/hit-super-effective.wav");
		}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	
	public void setFileSE(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip2 = AudioSystem.getClip();
			clip2.open(ais);
			
		}catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void playSE() {
		clip2.start();
	}
	
	public void stopSE() {
		clip2.stop();
	}
	
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}

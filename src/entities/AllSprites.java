package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AllSprites {
	private BufferedImage[] charmanderBack = new BufferedImage[10];
	private BufferedImage[] charmanderFront = new BufferedImage[1];
	
	private BufferedImage[] bulbasaurBack = new BufferedImage[12];
	private BufferedImage[] bulbasaurFront = new BufferedImage[11];
	
	public AllSprites() {
		loadSprites();
	}
	
	public void loadSprites() {
		try {
			this.charmanderFront[0] = ImageIO.read(getClass().getResourceAsStream("/charmander/frontSprite icon.png"));
			
			this.charmanderBack[0] = ImageIO.read(getClass().getResourceAsStream("/charmander/back01.png"));
			this.charmanderBack[1] = ImageIO.read(getClass().getResourceAsStream("/charmander/back02.png"));
			this.charmanderBack[2] = ImageIO.read(getClass().getResourceAsStream("/charmander/back03.png"));
			this.charmanderBack[3] = ImageIO.read(getClass().getResourceAsStream("/charmander/back04.png"));
			this.charmanderBack[4] = ImageIO.read(getClass().getResourceAsStream("/charmander/back05.png"));
			this.charmanderBack[5] = ImageIO.read(getClass().getResourceAsStream("/charmander/back06.png"));
			this.charmanderBack[6] = ImageIO.read(getClass().getResourceAsStream("/charmander/back07.png"));
			this.charmanderBack[7] = ImageIO.read(getClass().getResourceAsStream("/charmander/back08.png"));
			this.charmanderBack[8] = ImageIO.read(getClass().getResourceAsStream("/charmander/back09.png"));
			this.charmanderBack[9] = ImageIO.read(getClass().getResourceAsStream("/charmander/back10.png"));
			
			this.bulbasaurBack[0] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back01.png"));
			this.bulbasaurBack[1] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back02.png"));
			this.bulbasaurBack[2] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back03.png"));
			this.bulbasaurBack[3] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back04.png"));
			this.bulbasaurBack[4] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back05.png"));
			this.bulbasaurBack[5] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back06.png"));
			this.bulbasaurBack[6] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back07.png"));
			this.bulbasaurBack[7] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back08.png"));
			this.bulbasaurBack[8] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back09.png"));
			this.bulbasaurBack[9] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back10.png"));
			this.bulbasaurBack[10] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back10.png"));
			this.bulbasaurBack[11] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/back11.png"));
			
			this.bulbasaurFront[0] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front01.png"));
			this.bulbasaurFront[1] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front02.png"));
			this.bulbasaurFront[2] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front03.png"));
			this.bulbasaurFront[3] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front04.png"));
			this.bulbasaurFront[4] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front05.png"));
			this.bulbasaurFront[5] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front06.png"));
			this.bulbasaurFront[6] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front07.png"));
			this.bulbasaurFront[7] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front08.png"));
			this.bulbasaurFront[8] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front09.png"));
			this.bulbasaurFront[9] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front10.png"));
			this.bulbasaurFront[10] = ImageIO.read(getClass().getResourceAsStream("/bulbasaur/front11.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getCharmanderSpritesB() {
		return this.charmanderBack;
	}
	
	public BufferedImage[] getCharmanderSpritesF() {
		return this.charmanderFront;
	}
	
	public BufferedImage[] getBulbasaurSpritesB() {
		return this.bulbasaurBack;
	}
	
	public BufferedImage[] getBulbasaurSpritesF() {
		return this.bulbasaurFront;
	}
}

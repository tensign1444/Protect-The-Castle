package a9;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Archer extends Defensive{
	
	private static final int HEALTH = 45;
	private static final int COOLDOWN = 2;
	private static final int ATTACKDAMAGE = 5;
	private static final BufferedImage ARCHER_IMAGE;

	static {
		BufferedImage archerImage = null;
		try {
			archerImage = ImageIO.read(new File("src/a9/Sprites/archer_sprite.png"));	
		} catch (IOException e) {
			System.out.println("A file was not found!");
			System.exit(0);
		}
		ARCHER_IMAGE = archerImage;
	}
	
	public Archer(Double startingPosition) {
		super(startingPosition, new Point2D.Double(ARCHER_IMAGE.getWidth(), ARCHER_IMAGE.getHeight()), ARCHER_IMAGE, HEALTH, COOLDOWN, ATTACKDAMAGE);
		
	}
	
	

}

package Project;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dragon extends Enemy{

    private static final int HEALTH = 100;
    private static final int COOLDOWN = 8;
    private static final int ATTACKDAMAGE = 2;
    private static final double SPEED = -1;
    private static final BufferedImage DRAGON_IMAGE;

    static {
        BufferedImage dragonImage = null;
        try {
            dragonImage = ImageIO.read(new File("src/Project/Sprites/dragon.png"));
        } catch (IOException e) {
            System.out.println("A file was not found!");
            System.exit(0);
        }
        DRAGON_IMAGE = dragonImage;
    }

    public Dragon(Double startingPosition) {
        super(startingPosition, new Point2D.Double(DRAGON_IMAGE.getWidth(), DRAGON_IMAGE.getHeight()), DRAGON_IMAGE, HEALTH, COOLDOWN, SPEED, ATTACKDAMAGE);
    }



}
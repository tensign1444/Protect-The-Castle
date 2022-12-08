package Project;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WolfTank extends Enemy{

    private static final int HEALTH = 750;
    private static final int COOLDOWN = 2;
    private static final int ATTACKDAMAGE = 5;
    private static final double SPEED = -.2;
    private static final BufferedImage WOLF_TANK_IMAGE;

    static {
        BufferedImage wolfTankImage = null;
        try {
            wolfTankImage = ImageIO.read(new File("src/Project/Sprites/Wolf_Sprite.png"));
        } catch (IOException e) {
            System.out.println("A file was not found!");
            System.exit(0);
        }
        WOLF_TANK_IMAGE = wolfTankImage;
    }
    public WolfTank(Double startingPosition) {
        super(startingPosition, new Point2D.Double(WOLF_TANK_IMAGE.getWidth(), WOLF_TANK_IMAGE.getHeight()), WOLF_TANK_IMAGE, HEALTH, COOLDOWN, SPEED, ATTACKDAMAGE);
    }

    public void attack(Actor other) {
        if(other instanceof Defensive)
        {
            if (this != other && this.isCollidingOther(other)) {
                if (this.isReadyForAction()) {
                    shiftPosition(new Point2D.Double(getSpeed(), 0));
                }
            }
        }
    }
}

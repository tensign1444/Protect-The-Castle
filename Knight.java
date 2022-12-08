package Project;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Knight extends Defensive{

    private static final int HEALTH = 100;
    private static final int COOLDOWN = 100;
    private static final int ATTACKDAMAGE = 10;
    private static final BufferedImage KNIGHT_IMAGE;

    static {
        BufferedImage knightImage = null;
        try {
            knightImage = ImageIO.read(new File("src/Project/Sprites/knight pixel.png"));
        } catch (IOException e) {
            System.out.println("A file was not found!");
            System.exit(0);
        }
        KNIGHT_IMAGE = knightImage;
    }

    public Knight(Double startingPosition) {
        super(startingPosition, new Point2D.Double(KNIGHT_IMAGE.getWidth(), KNIGHT_IMAGE.getHeight()), KNIGHT_IMAGE, HEALTH, COOLDOWN, ATTACKDAMAGE);

    }

    public void attack(Actor other)
    {
        if(other instanceof Enemy)
        {
            if(this!=other && this.isCollidingOther(other))
            {
                if(this.isReadyForAction())
                {
                    other.shiftPosition(new Point2D.Double(50, 0));
                    other.changeHealth(-attackDamage);
                    this.resetCoolDown();
                }
            }
        }
    }


}

package Project;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

/**
 * The Defensive class makes it so we know which characters on the defense of the game.
 *
 * @author Tanner Ensign and Liam Roberts
 */
public class Defensive extends Actor {

    public Defensive(Point2D.Double startingPosition, Point2D.Double initHitbox, BufferedImage img, int health, int coolDown, int attackDamage) {
        super(startingPosition, initHitbox, img, health, coolDown, 0 , attackDamage);
    }

    /**
     * An attack means the two hotboxes are overlapping and the
     * Actor is ready to attack again (based on its cooldown).
     *
     * Plants only attack Zombies.
     *
     * @param other
     */
    @Override
    public void attack(Actor other) {
        if (other instanceof Enemy)
            super.attack(other);
    }
}
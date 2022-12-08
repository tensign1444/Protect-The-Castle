package Project;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Enemy extends Actor {

    private boolean isColliding;
    private Point2D.Double position;
    private Point2D.Double actorPosition;

    public Enemy(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown, double speed,
                 int attackDamage) {
        super(startingPosition, initHitbox, img, health, coolDown, speed, attackDamage);
        isColliding = false;
        // TODO Auto-generated constructor stub
    }



    /**
     * Move if not colliding. Only moves in x.
     */
    @Override
    public void move() {
        if (!isColliding)
            shiftPosition(new Point2D.Double(getSpeed(), 0));
    }

    /**
     * Set the collision status
     * @param collisionStatus
     */
    public void setColliding(boolean collisionStatus) {
        isColliding = collisionStatus;
    }

    /**
     * Get the collision status.
     * @return
     */
    public boolean getColliding() {
        return isColliding;
    }

    /**
     * Set collision status on this if it overlaps with other.
     * @param other
     */
    public void setCollisionStatus(Actor other) {
        if (other instanceof Defensive && this.isCollidingOther(other))
            setColliding(true);
    }

    /**
     * Update the internal state of the Actor. This means reset the
     * collision status to false and decrement the cool down counter.
     */
    public void update() {
        isColliding = false;
        decrementCooldown();
    }

    /**
     * An attack means the two hotboxes are overlapping and the
     * Actor is ready to attack again (based on its cooldown).
     *
     * @param other
     */
    @Override
    public void attack(Actor other) {
        if (other instanceof Defensive) {
            super.attack(other);
        }
    }

}

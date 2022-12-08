package Project;

/**
 * @author Tanner Ensign and Liam Roberts
 *
 * The attack interface means that the class that implements it is
 * capable of checking for collision with other objects and can damage it.
 */
public interface Attack {
    public void attack(Actor other);
}

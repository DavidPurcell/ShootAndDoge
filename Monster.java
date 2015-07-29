import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.ArrayList;

/**
 * This is the abstract monster class that all other monster are based on.
 *
 * @author David Purcell
 * @version 1.0
 */

public class Monster {
    protected int health = 100;
    protected int x, y, dirX = 1, dirY = 1, timer = 25, value = 50;
    protected BufferedImage img;
    protected Random rand = new Random();
    protected ArrayList<PathBlock> path;
    protected Block current;

    /**
     * Normal monster constructor
     *
     * @param x the starting x value of the monster
     * @param y the starting y value of the monster
     * @param path the path the monster follows
     * @param powerLevel the updated powerlevel of the monsters
     */
    public Monster(int x, int y, ArrayList<PathBlock> path, double powerLevel) {
        this.x = x;
        this.y = y;
        this.path = path;
        current = path.get(0);
        try {
            img = ImageIO.read(new File("DOGE.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
        this.health = (int) (health * powerLevel);
    }

    /**
     * draw method that draws the monster and it health and age
     *
     * @param g the graphics object used to display everything
     */
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    /**
     * moves the monster by going from one path block to the next
     */
    public void move() {
        if (((PathBlock) current).getNext() instanceof PathBlock) {
            current = ((PathBlock) current).getNext();
        } else {
            health = -100;
        }
        x = current.getX();
        y = current.getY();
        timer--;
    }

    /**
     * returns health
     *
     * @return the health of the monster
     */
    public int getHealth() {
        return health;
    }

    /**
     * returns time left to next monster
     *
     * @return the number left on the countdown timer
     */
    public int getTimer() {
        if (timer == 0) {
            timer = 25;
            return 0;
        }
        return timer;
    }

    /**
     * sets health
     *
     * @param the new health of the monster
     */
    public void setHealth(int i) {
        health = i;
    }

    /**
     * subtracts damage from health
     *
     * @param damage to be removed
     */
    public void hasHit(int damage) {
        health -= damage;
    }

    /**
     * returns x
     *
     * @return the x coordinate of the monster
     */
    public int getX() {
        return x;
    }

    /**
     * returns y
     *
     * @return the y coordinate of the monster
     */
    public int getY() {
        return y;
    }

    /**
     * returns the current block
     *
     * @return the current path block the monster occupies
     */
    public Block getPath() {
        return current;
    }

    /**
     * returns sets a monsters health to -100, effectively killing it
     */
    public void kill() {
        health = -100;
    }

    /**
     * returns the money value of the monster
     *
     * @return the value of the monster
     */
    public int getValue() {
        return value;
    }
}

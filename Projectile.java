import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Random;

/**
 * This is a used as the default projectile
 *
 * @author David Purcell
 * @version 1.0
 */

public class Projectile {
    protected int speed, damage, x, y, pew, range;
    protected double angle, rotate;
    protected boolean beenHit;
    protected BufferedImage img;
    protected Random rand = new Random();

    /**
     * Normal projectile constructor
     *
     * @param x the starting x value of the monster
     * @param y the starting y value of the monster
     * @param angle the angle the tower rotates to
     * @param rotate the angle the projectiles are fired at
     */
    public Projectile(int x, int y, double angle, double rotate) {
        pew = 1;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 10;
        this.rotate = rotate;
        range = 400;
        damage = 5;
        try {
            img = ImageIO.read(new File("projectile.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
    }

    /**
     * returns x
     *
     * @return the x coordinate of the projectile
     */
    public int getX() {
        return x;
    }

    /**
     * returns y
     *
     * @return the y coordinate of the projectile
     */
    public int getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * returns range
     *
     * @return the range of the projectile
     */
    public int getRange() {
        return range;
    }

    /**
     * sets that the projectile has hit a monster
     */
    public void hasHit() {
        beenHit = true;
    }

    /**
     * returns if the projectile has been hit
     *
     * @return the if the projectile has been hit
     */
    public boolean hasBenHit() {
        return beenHit;
    }

    /**
     * tests if the projectile has hit a monster
     *
     * @param m the monster we are testing on
     * @return whether or not you collided with monster
     */
    public boolean hit(Monster m) {
        if (m.getX() >= x - 50 && m.getX() <= x + 50
            && m.getY() >= y - 50 && m.getY() <= y + 50) {
            return true;
        }
        return false;
    }

    /**
     * moves the projectile
     */
    public void move() {
        x += speed * Math.sin(Math.toRadians(angle)) + pew;
        y += speed * Math.cos(Math.toRadians(angle)) + pew;
        range -= speed;
    }

    /**
     * tests if the projectile has hit a monster
     *
     * @param m the monster we are testing on
     * @return whether or not you collided with monster
     */
    public boolean collidesWithMonster(Monster m) {
        if (m.getX() >= x - 50 && m.getX() <= x
                                 && m.getY() >= y - 50 && m.getY() <= y) {
            beenHit = true;
            return true;
        }
        return false;
    }

    /**
     * draw method that draws the monster and it health and age
     *
     * @param g the graphics object used to display everything
     */
    public void draw(Graphics g) {
        double rotationRequired = Math.toRadians(rotate);
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(
                                    rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx,
                                            AffineTransformOp.TYPE_BILINEAR);

        // Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(img, null), x, y, null);
    }
}

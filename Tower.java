import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This is a used as the default tower
 *
 * @author David Purcell
 * @version 1.0
 */

public class Tower {
    protected int range, damage, x, y;
    protected double rotate;
    protected boolean canShoot = false;
    protected BufferedImage img;
    protected int speed;
    protected Timer timey;

    /**
    * constructor for the default tower
    *
    * @param x the x position of the tower
    * @param y the y position of the tower
    */
    public Tower(int x, int y) {
        this.x = (x + 25) / 25 * 25; //rounding to nearest 25
        this.y = (y + 25) / 25 * 25;
        try {
            img = ImageIO.read(new File("tower.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
        range = 500;
        speed = 150;
        timey = new Timer(speed, new TimerListener());
        timey.start();
    }

    protected class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            canShoot = true;
        }
    }

    /**
    * getter for the tower's image
    *
    * @return the image of the tower
    */
    public BufferedImage getImage() {
        return img;
    }

    /**
     * draw method that draws the tower
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

    /**
     * returns x
     *
     * @return the x coordinate of the tower
     */
    public int getX() {
        return x;
    }

    /**
     * returns y
     *
     * @return the y coordinate of the tower
     */
    public int getY() {
        return y;
    }

    /**
     * tests if the tower can shoot a monster
     *
     * @param m the monster we are testing on
     * @return whether or not you can shoot the monster
     */
    public boolean canShoot(Monster m) {
        double deltaY = y - m.getY();
        double deltaX = x - m.getX();
        if (canShoot && range >= Math.sqrt(((m.getX() - x) * (m.getX() - x))
                     + ((m.getY() - y) * (m.getY() - y)))) {
            rotate = 180 +  Math.atan2(deltaY , deltaX) * 180 / Math.PI;
            canShoot = false;
            return true;
        }
        return false;
    }

    /**
     * shoots at a monster
     *
     * @param m the monster we are shooting
     * @return the projectile being fired
     */
    public Projectile shoot(Monster m) {
        return new Projectile(x, y, 90 - rotate, rotate);
    }
}

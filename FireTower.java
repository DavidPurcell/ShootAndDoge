import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * This is a used as the secondary tower
 *
 * @author David Purcell
 * @version 1.0
 */

public class FireTower extends Tower {
    /**
    * constructor for the fire tower
    *
    * @param x the x position of the tower
    * @param y the y position of the tower
    */
    public FireTower(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("firetower.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
        range = 100;
        speed = 10;
        timey = new Timer(speed, new TimerListener());
        timey.start();
    }

    /**
     * shoots at a monster
     *
     * @param m the monster we are shooting
     * @return the projectile being fired
     */
    public Projectile shoot(Monster m) {
        return new Fire(x, y, 90 - rotate, rotate);
    }
}

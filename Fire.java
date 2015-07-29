import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is a used as the secondary projectile
 *
 * @author David Purcell
 * @version 1.0
 */

public class Fire extends Projectile {

    /**
     * Fire projectile constructor
     *
     * @param x the starting x value of the monster
     * @param y the starting y value of the monster
     * @param angle the angle the tower rotates to
     * @param rotate the angle the projectiles are fired at
     */
    public Fire(int x, int y, double angle, double rotate) {
        super(x, y, angle, rotate);
        pew = rand.nextInt(3) - 1;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 4;
        this.rotate = rotate;
        range = 100;
        damage = 1;
        try {
            img = ImageIO.read(new File("fire.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
    }

    /**
     * fire does not stop when it hits a target
     */
    public void hasHit() {
        beenHit = false;
    }
}

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 * This is a used as the secondary tower
 *
 * @author David Purcell
 * @version 1.0
 */

public class OtherMonster extends Monster {

    /**
     * Normal other monster constructor
     *
     * @param x the starting x value of the monster
     * @param y the starting y value of the monster
     * @param path the path the monster follows
     * @param powerLevel the updated powerlevel of the monsters
     */
    public OtherMonster(
                   int x, int y, ArrayList<PathBlock> path, double powerLevel) {
        super(x, y, path, powerLevel);
        current = path.get(0);
        try {
            img = ImageIO.read(new File("otherMonster.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
        health = 200;
        value = 100;
    }
}

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 * This is a PathBlock that is used for monster control along the path
 *
 * @author David Purcell
 * @version 1.0
 */

public class PathBlock extends Block {
    private Block next;
    private BufferedImage img;

    /**
    * constructor for the default block
    *
    * @param x the x position of the block
    * @param y the y position of the block
    * @param pos the position of the block in the array
    * @param next the next block in the line
    */
    public PathBlock(int x, int y, int pos, Block next) {
        super(x, y, pos);
        this.next = next;
        try {
            img = ImageIO.read(new File("tower.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
    }

    /**
    * getter for next block in line
    *
    * @return the next block
    */
    public Block getNext() {
        return next;
    }

    /**
    * setter for the next block
    *
    * @param newPath the new block to be set as next
    */
    public void setNext(Block newPath) {
        next = newPath;
    }

    /**
    * draws the graphics
    *
    * @param g the graphics for the tower panel
    */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(super.getX(), super.getY(), 50, 50);
    }
}

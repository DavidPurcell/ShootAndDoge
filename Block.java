/**
 * This is a Block that is used for monster control
 *
 * @author David Purcell
 * @version 1.0
 */

public class Block {
    private int x, y, pos;

    /**
    * constructor for the default block
    *
    * @param x the x position of the block
    * @param y the y position of the block
    * @param pos the position of the block in the array
    * the next Tower to create
    */
    public Block(int x, int y, int pos) {
        this.x = x;
        this.y = y;
        this.pos = pos;
    }

    /**
    * getter for x of block
    *
    * @return the x value of the block
    */
    public int getX() {
        return x;
    }

    /**
    * getter for y of block
    *
    * @return the y value of the block
    */
    public int getY() {
        return y;
    }

    /**
    * getter for pos of block
    *
    * @return the pos value of the block
    */
    public int getPos() {
        return pos;
    }
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.Point;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class represents the Game Field
 *
 * @author David Purcell
 * @version 1.0
 */
public class TowerPanel extends JPanel {
    public static final int WIDTH = 600, HEIGHT = 600;

    private ArrayList<Tower> towers = new ArrayList<Tower>();
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private ArrayList<Monster> nextWave = new ArrayList<Monster>();
    private ArrayList<Projectile> projs = new ArrayList<Projectile>();
    private ControlPanel cPanel;
    private Timer timer;
    private Rectangle bounds;
    private ArrayList<PathBlock> path = new ArrayList<PathBlock>();
    private Block block = new Block(200, 200, -1);
    protected int pathLength = 1350, money = 100, health = 10;
    protected double powerLevel = 1;
    private boolean respawning, lost = false;
    private BufferedImage loser;

    /**
    * Constructor for towerPanel
    *
    * @param control A ControlPanel instance which is used to determine
    * the next Tower to create
    */
    public TowerPanel(ControlPanel control) {
        try {
            loser = ImageIO.read(new File("loser.png"));
        } catch (IOException e) {
            System.out.println("oops");
        }
        cPanel = control;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        path.add(new PathBlock(-1000, -1000, -1, block));

        for (int i = pathLength; i > 950; i--) { //Turn down, ends @500, 600
            path.add(new PathBlock(500, i - 850, i, path.get(pathLength - i)));
        }

        for (int i = 950; i > 650; i--) { //Turn left, ends @500, 100
            path.add(new PathBlock(i - 450, 100, i, path.get(pathLength - i)));
        }

        for (int i = 650; i > 450; i--) { //Turn upwards, ends @200, 100
            path.add(new PathBlock(200, -i + 750, i, path.get(pathLength - i)));
        }

        for (int i = 450; i > 250; i--) { //Initial straight part, ends @200,300
            path.add(new PathBlock(i - 250, 300, i, path.get(pathLength - i)));
        }

        for (int i = 250; i > 0; i--) { //Initial straight part, ends @200,300
            path.add(new PathBlock(-1000, -1000, i, path.get(pathLength - i)));
        }
        path.get(0).setNext(path.get(pathLength));
        setBackground(Color.WHITE);
        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);
        addMouseListener(new ClickListener());
        timer = new Timer(10, new TimerListener());
        timer.start();
    }

    /**
     * Move all the monsters and projectiles
     */
    public void moveAll() {
        for (Monster m:monsters) {
            m.move();
        }
        for (Projectile p:projs) {
            p.move();
        }
    }

    /**
     * Check to see whether any of the towers can shoot targets
     */
    public void checkShooting() {
        for (Tower t:towers) {
            for (Monster m:monsters) {
                if (t.canShoot(m)) {
                    projs.add(t.shoot(m));
                }
            }
        }
    }

    /**
     * Check to see whether any of the projectiles hit monsters
     */
    public void checkHit() {
        for (Projectile p:projs) {
            for (Monster m:monsters) {
                if (p.collidesWithMonster(m)) {
                    p.hasHit();
                    m.hasHit(p.getDamage());
                }
            }
        }
    }

    /**
     * Deletes monsters with no health and projectiles on hit
     */
    public void checkDead() {
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getHealth() <= 0) {
                money += monsters.get(i).getValue();
                monsters.remove(monsters.get(i));
                i--;
            } else if (monsters.get(i).getPath().getPos() == pathLength) {
                health--;
                monsters.remove(monsters.get(i));
                i--;
            }
        }
        for (int i = 0; i < projs.size(); i++) {
            if (projs.get(i).hasBenHit()) {
                projs.remove(projs.get(i));
                i--;
            } else if (projs.get(i).getX() > 800 || projs.get(i).getX() < -200
                   || projs.get(i).getY() > 800 || projs.get(i).getY() < -200) {
                projs.remove(projs.get(i));
                i--;
            }
        }
        for (int i = 0; i < projs.size(); i++) {
            if (projs.get(i).getRange() <= 0) {
                projs.remove(projs.get(i));
                i--;
            }
        }
        if (health == 0) {
            lost = true;
        }
    }

    /**
     * Respawns new monsters
     */
    public void respawn() {
        if (cPanel.getWave()) {
            respawning = true;
        }
        if (respawning && monsters.size() == 0) {
            monsters.add(new Monster(0, 300, path, powerLevel));
        } else if (respawning && monsters.size() < 9
                && monsters.get(monsters.size() - 1).getTimer() == 0) {
            monsters.add(new Monster(0, 300, path, powerLevel));
        } else if (respawning && monsters.size() == 9) {
            monsters.add(new OtherMonster(0, 300, path, powerLevel));
            respawning = false;
            powerLevel += 1;
        }
    }

    /**
     * Draw all the components
     */
    public void paintComponent(Graphics g) {
    //***Does not need to be edited
        super.paintComponent(g); //Call to the super constructor to make sure
        //all of JPanel's paintComponent stuff is called first
        for (int i = 0; i < path.size(); i++) {
            path.get(i).draw(g);
        }
        for (Monster m:monsters) {
            m.draw(g);
        }
        for (Tower t:towers) {
            t.draw(g);
        }
        for (Projectile p:projs) {
            p.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("$" + money, 10, 10);
        g.drawString("Next Wave Level: " + powerLevel, 250, 10);
        g.drawString("Health: " + health, 500, 10);
        if (lost) {
            g.drawImage(loser, 0, 0, null);
            g.drawImage(loser, 0, 0, null);
            g.drawImage(loser, 0, 0, null);
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            moveAll();
            checkShooting();
            checkHit();
            checkDead();
            respawn();
            repaint();
        }
    }

    private class ClickListener extends MouseAdapter {
       /**
        *@param Point p is where the tower will be placed to start.
        *@param String className is the class name for the type of Tower that
        *       we want to instantiate.
        *@return Tower that is exactly the type of Tower that we want to add
        *        to the panel.
        */
        public Tower instantiateTurretType(Point p, String className) {
            try {
                Class cl = Class.forName(className);
                return (Tower) (cl.getDeclaredConstructor(
                    int.class, int.class).newInstance(
                        p.x - 50, p.y - 50));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
            return null;
        }

        public void mousePressed(MouseEvent e) {
            String towerType = cPanel.getTowerType();
            Point p = e.getPoint();
            if (towerType == "Tower" && money >= 100) {
                if (money >= 100) {
                    towers.add(instantiateTurretType(p, towerType));
                    money -= 100;
                }
            }
            if (towerType == "FireTower") {
                if (money >= 500) {
                    towers.add(instantiateTurretType(p, towerType));
                    money -= 500;
                }
            }
            repaint();
        }
    }
}

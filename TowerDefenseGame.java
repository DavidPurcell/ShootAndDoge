import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * This is the runner for everything, displays a control and tower
 *
 * @author David Purcell
 * @version 1.0
 */

public class TowerDefenseGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shoot and Doge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ControlPanel control = new ControlPanel();
        frame.add(control, BorderLayout.WEST);
        frame.add(new TowerPanel(control));
        frame.pack();
        frame.setVisible(true);
    }
}

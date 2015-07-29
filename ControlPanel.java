import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This is the ControlPanel for the Tower Defense. It allows the
 * user to pick which towers it would like to add next.
 *
 * @author David Purcell
 * @version 1.0
 */
public class ControlPanel extends JPanel {
    private JButton tower, fire, wave;
    private boolean addWave;
    private JLabel current;
    private String towerType;

    /**
     * Constructor for the control panel
     */
    public ControlPanel() {
        setPreferredSize(new Dimension(150, TowerPanel.HEIGHT));

        tower = new JButton("Tower \n $100");
        tower.addActionListener(new ButtonListener("Tower"));
        add(tower);

        fire = new JButton("FireTower \n $500");
        fire.addActionListener(new ButtonListener("FireTower"));
        add(fire);

        wave = new JButton("Wave");
        wave.addActionListener(new WaveListener());
        add(wave);

        towerType = "Tower";
        add(new JLabel("Current Tower"));
        current = new JLabel("Tower");
        add(current);
    }

    public boolean getWave() {
        if (addWave) {
            addWave = false;
            return true;
        }
        return addWave;
    }

    public void setWave(boolean b) {
        addWave = b;
    }

    /**
     * Invoked by TowerPanel to determine which Tower
     * was chosen.
     *
     * @return The currently selected Tower type
     */
    public String getTowerType() {
        return towerType;
    }

    public class WaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setWave(true);
        }
    }

    public class ButtonListener implements ActionListener {
        private String name;

        /**
         * constructor for button listener
         *
         * @param className the name of the class associated with the button
         */
        public ButtonListener(String className) {
            name = className;
        }

        /**
         * method that runs when action performed, changes current text and
         * towerType
         *
         * @param e the even that was performed
         */
        public void actionPerformed(ActionEvent e) {
            towerType = name;
            current.setText(name);
        }
    }
}

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Woodbin on 1.10.2014.
 */
public class SettingsWindow {
    private JCheckBox testModeCheckBox;
    private JCheckBox autoLoginCheckBox;
    private JTextField autoLoginTextField;
    private JPasswordField autoPasswordField;
    private JLabel fakedataCountLabel;
    private JSpinner fakeDataCountSpinner;
    private JButton recreateFakeDataButton;
    private JPanel settingsPanel;

    public void createAndShow() {
        JFrame frame = new JFrame("SettingsWindow");
        frame.setContentPane(new SettingsWindow().settingsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public SettingsWindow() {
        testModeCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        autoLoginCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        recreateFakeDataButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}

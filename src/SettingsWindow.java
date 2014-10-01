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
    private JButton autoLoginSaveButton;
    JFrame frame;

    public void createAndShow() {
        frame = new JFrame("SettingsWindow");
        frame.setContentPane(settingsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public SettingsWindow() {
        testModeCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testModeSwitch();
                super.mouseClicked(e);
            }
        });
        autoLoginCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                autoLoginSwitch();
                super.mouseClicked(e);
            }
        });
        recreateFakeDataButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recreateFakeData();
                super.mouseClicked(e);
            }
        });
        autoLoginSaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveAutoLoginCredentials();
                super.mouseClicked(e);
            }
        });
    }

    private void testModeSwitch(){
        if(testModeCheckBox.isEnabled()){
            fakedataCountLabel.setEnabled(true);
            recreateFakeDataButton.setEnabled(true);
            fakedataCountLabel.setEnabled(true);
            Core.setTestModeState(true);
        }if(!testModeCheckBox.isEnabled()){
            fakedataCountLabel.setEnabled(false);
            recreateFakeDataButton.setEnabled(false);
            fakedataCountLabel.setEnabled(false);
            Core.setTestModeState(false);
        }
    }

    private void autoLoginSwitch(){
        if(autoLoginCheckBox.isEnabled()){
            autoLoginTextField.setEnabled(true);
            autoPasswordField.setEnabled(true);
            autoLoginSaveButton.setEnabled(true);
        }
        if(!autoLoginCheckBox.isEnabled()){
            autoLoginTextField.setEnabled(false);
            autoPasswordField.setEnabled(false);
            autoLoginSaveButton.setEnabled(false);
            Core.setAutoLoginState(false);
        }
    }


    private void saveAutoLoginCredentials(){

    }

    private void recreateFakeData(){

    }





}

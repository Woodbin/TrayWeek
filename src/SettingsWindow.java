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
    private JButton closeButton;
    JFrame frame;

    public void createAndShow() {
        frame = new JFrame("SettingsWindow");
        frame.setContentPane(settingsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public SettingsWindow() {
        fakeDataCountSpinner.setValue(new Integer(Core.getFakeDataCount()));
        testModeCheckBox.setSelected(Core.getTestModeState());
        autoLoginCheckBox.setSelected(Core.getAutoLoginState());
        testModeSwitch(Core.getTestModeState());
        autoLoginSwitch(Core.getAutoLoginState());
        testModeCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testModeClick();
                super.mouseClicked(e);
            }
        });
        autoLoginCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                autoLoginClick();
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
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeWindow();
                super.mouseClicked(e);
            }
        });
    }



    private void testModeClick(){
        if(testModeCheckBox.isSelected()) testModeSwitch(true);
        else testModeSwitch(false);

        frame.revalidate();
    }
    private void testModeSwitch(boolean state){
            fakedataCountLabel.setEnabled(state);
            recreateFakeDataButton.setEnabled(state);
            fakeDataCountSpinner.setEnabled(state);
            Core.setTestModeState(state);

    }
    private void autoLoginClick(){
        if(autoLoginCheckBox.isSelected()) autoLoginSwitch(true);
        else autoLoginSwitch(false);

        frame.revalidate();
    }
    private void autoLoginSwitch(boolean state){
            autoLoginTextField.setEnabled(state);
            autoPasswordField.setEnabled(state);
            autoLoginSaveButton.setEnabled(state);
            if(!state)Core.setAutoLoginState(state);
    }


    private void saveAutoLoginCredentials(){
        //TODO Save login credentials for auto login
    }

    private void recreateFakeData(){
        try {
            Core.setFakeDataCount((Integer)fakeDataCountSpinner.getValue());
            Core.action(CoreAction.RECREATEFAKES);
            App.rebuildProjectsView();
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    private void closeWindow(){
        frame.dispose();
    }




}

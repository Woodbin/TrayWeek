import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Woodbin on 24.9.2014.
 */
public class LoginWindow {
    private JTextField loginTextField;
    private JButton loginButton;
    private JPanel loginWindowPane;
    private JPasswordField passwordField;
    private JFrame frame;

    //REFERENCES
    private static Core core = Core.getInstance();
    private static App app = App.getInstance();
    private static DebugModule debug = DebugModule.getInstance();


    public LoginWindow() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                sendLogin();
                windowHide();

            }
        });
    }

    public void createAndShow(){
        frame = new JFrame("LoginWindow");
        frame.setContentPane(this.loginWindowPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    public void windowHide(){
        debug.debugOut("Disposing LoginWindow");
        frame.dispose();
    }

    private void sendLogin(){
        String args[] = new String[2];
        args[0] = loginTextField.getText();
        args[1] = passwordField.getText();
        core.action(CoreAction.LOGIN,args);
    }


}

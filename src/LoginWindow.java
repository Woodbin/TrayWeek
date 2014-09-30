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

    /**
     * Constructor
     */
    public LoginWindow() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                sendLogin();
                windowHide();

            }
        });
    }

    /**
     * Factory method
     */
    public void createAndShow(){
        frame = new JFrame("LoginWindow");
        frame.setContentPane(this.loginWindowPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Hides window
     */
    public void windowHide(){
        debug.debugOut("Disposing LoginWindow");
        frame.dispose();
    }

    /**
     * Sends login credentials to core and tries to login
     */
    private void sendLogin(){
        String args[] = new String[2];
        args[0] = loginTextField.getText();
        args[1] = passwordField.getText();
        try {
            core.action(CoreAction.LOGIN,args);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }


}

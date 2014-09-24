import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Woodbin on 24.9.2014.
 */
public class DebugConsole{
    private JPanel consolePanel;
    private JTextField commandLine;
    private JButton executeButton;
    private JTextArea consoleTextArea;
    private JScrollPane consoleArea;
    JFrame consoleFrame = new JFrame("DebugConsole");


    //REFERENCES
    private static DebugModule debugmodule = DebugModule.getInstance();


    public DebugConsole() {
        executeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                debugmodule.debugOut("Execute button pressed");
                super.mouseReleased(e);
            }
        });
    }

    public void consolePrint(String message){
        //TODO fix console window textarea update
        consoleTextArea.append(message+"\n");
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
        consoleTextArea.revalidate();

    }




    public void create(){
        consoleFrame.setContentPane(this.consolePanel);
        consoleFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        consoleFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowHide();
            }
        });

        consoleTextArea.setEditable(false);

        consoleFrame.pack();

        debugmodule.debugOut("Console Window created");


    }




    public void windowShow(){
        consoleFrame.show();
        debugmodule.debugOut("Debug Console showed");
    }

    public void windowHide(){
        consoleFrame.hide();
        debugmodule.debugOut("Debug Console hidden");

    }
}

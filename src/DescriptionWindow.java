import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class DescriptionWindow {
    private JTextPane descriptionTextPane;
    private JPanel descriptionPanel;
    private JLabel descriptionLabel;
    private JButton commitButton;
    private JButton cancelButton;
    private String project;
    private TasksWindow tasksWindow;
    private JFrame frame;
    private boolean finishing;

    public void createAndShow(){
        frame = new JFrame("DescriptionWindow");
        frame.setContentPane(descriptionPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DescriptionWindow(String _project, final boolean _finishing, TasksWindow parent) {
        commitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commitTask();
                super.mouseClicked(e);
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancel();
                super.mouseClicked(e);
            }

        });
        tasksWindow=parent;
        project =_project;
        finishing = _finishing;
        descriptionLabel.setText("Task for project "+ project);

        if(finishing){
            commitButton.setEnabled(false);
        }
        descriptionTextPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(finishing){
                    if(!descriptionTextPane.getText().equals("")){ commitButton.setEnabled(true);}
                    else{
                        commitButton.setEnabled(false);
                    }
                }
                super.keyPressed(e);
            }
        });


    }

    private void commitTask(){
        tasksWindow.changeButtons(TasksWindowButtonStateChange.STARTTASK);
        tasksWindow.windowHide();
        frame.dispose();
        //TODO Commit task description and start task
    }
    private void cancel(){
        //TODO Cancel
    }


}

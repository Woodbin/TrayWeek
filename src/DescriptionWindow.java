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
    private JFrame frame;
    private boolean finishing;

    //REFERENCES
    private ProjectsWindow projectsWindow;
    private static Core core = Core.getInstance();
    private static DebugModule debug = DebugModule.getInstance();

    /**
     * Factory method
     */
    public void createAndShow(){
        frame = new JFrame("DescriptionWindow");
        frame.setContentPane(descriptionPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Constructor
     * @param _project project id
     * @param _finishing is the task being finished?
     * @param parent ProjectsWindow
     */
    public DescriptionWindow(String _project, final boolean _finishing, ProjectsWindow parent) {
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
        projectsWindow =parent;
        project =_project;
        finishing = _finishing;
        descriptionLabel.setText("Task for project "+ project);

        if(finishing){
            if(projectsWindow.getCurrentTask().getDescription().equals(""))commitButton.setEnabled(false);
            descriptionTextPane.setText(projectsWindow.getCurrentTask().getDescription());

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

    /**
     * Commits task - if !finishing, creates a new one, if finishing, completes the task
     */
    private void commitTask(){
        if(!finishing){
            try{
            String args[] = new String[2];
            args[0]=project;
            args[1]=descriptionTextPane.getText();
            core.action(CoreAction.NEWTASK,args);
            debug.debugOut("Creating new task for project "+core.getProjectById(project).getName()+" with description: \n"+descriptionTextPane.getText()+"\n and timestamp "+core.getCurrentTask().getStartTimestamp().toString());
            projectsWindow.changeButtons(ProjectsWindowButtonStateChange.STARTTASK);
            projectsWindow.setCurrentTask(core.getCurrentTask());
            projectsWindow.windowHide();
            frame.dispose();

            }catch(ProjectDoesNotExistException e){
                debug.debugOut("Task can't be added to nonexisting project!");
            }catch(CoreException c){

            }
        }

        if(finishing){
            try {
                String args[] = new String[1];
                args[0]=descriptionTextPane.getText();
                core.action(CoreAction.COMPLETETASK,args);
                projectsWindow.changeButtons(ProjectsWindowButtonStateChange.COMPLETETASK);
                frame.dispose();
            }catch (ProjectDoesNotExistException e){
                debug.debugOut("Task can't be added to nonexisting project!");
            }catch(CoreException c){

            }
        }
    }

    /**
     * DIE POTATO
     */
    private void cancel(){
        frame.dispose();
    }


}

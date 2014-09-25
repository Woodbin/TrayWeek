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
    private int project;
    private JFrame frame;
    private boolean finishing;

    //REFERENCES
    private ProjectsWindow projectsWindow;
    private static Core core = Core.getInstance();
    private static DebugModule debug = DebugModule.getInstance();


    public void createAndShow(){
        frame = new JFrame("DescriptionWindow");
        frame.setContentPane(descriptionPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DescriptionWindow(int _project, final boolean _finishing, ProjectsWindow parent) {
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

    private void commitTask(){
        if(!finishing){
            projectsWindow.changeButtons(ProjectsWindowButtonStateChange.STARTTASK);
            //TODO Commit task description and start task
            Task newTask = new Task(project, descriptionTextPane.getText());
            debug.debugOut("Creating new task for project "+core.getProjects().get(project).getName()+" with description: \n"+descriptionTextPane.getText()+"\n and timestamp "+newTask.getStartTimestamp().toString());
            projectsWindow.setCurrentTask(newTask);}
           projectsWindow.windowHide();
            frame.dispose();
        if(finishing){
            Task appendedTask = projectsWindow.getCurrentTask();
            appendedTask.finishTask();
            appendedTask.setDescription(descriptionTextPane.getText());
            debug.debugOut("Finishing task for project "+core.getProjects().get(project).getName()+" with description: \n"+appendedTask.getDescription()+"\n and timestamp "+appendedTask.getFinishTimestamp().toString());
            core.getProjects().get(appendedTask.getProjectId()).appendTask(appendedTask);
            projectsWindow.changeButtons(ProjectsWindowButtonStateChange.COMPLETETASK);
            frame.dispose();
        }
    }
    private void cancel(){
        //TODO Cancel
        frame.dispose();
    }


}

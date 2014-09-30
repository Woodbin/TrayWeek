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


    public void createAndShow(){
        frame = new JFrame("DescriptionWindow");
        frame.setContentPane(descriptionPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

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

    private void commitTask(){
        if(!finishing){
            try{
            projectsWindow.changeButtons(ProjectsWindowButtonStateChange.STARTTASK);
            core.newTask(project, descriptionTextPane.getText());
            debug.debugOut("Creating new task for project "+core.getProjectById(project).getName()+" with description: \n"+descriptionTextPane.getText()+"\n and timestamp "+core.getCurrentTask().getStartTimestamp().toString());
            projectsWindow.setCurrentTask(core.getCurrentTask());
            projectsWindow.windowHide();
            frame.dispose();

            }catch(ProjectDoesntExistException e){
                debug.debugOut("Task can't be added to nonexisting project!");
            }
        }

        if(finishing){
            try {
                Task appendedTask = core.getCurrentTask();
                appendedTask.finishTask();
                appendedTask.setDescription(descriptionTextPane.getText());
                debug.debugOut("Finishing task for project " + core.getProjectById(appendedTask.getProjectId()).getName() + " with description: \n" + appendedTask.getDescription() + "\n and timestamp " + appendedTask.getFinishTimestamp().toString());
                core.getProjectById(appendedTask.getProjectId()).appendTask(appendedTask);
                projectsWindow.changeButtons(ProjectsWindowButtonStateChange.COMPLETETASK);
                frame.dispose();
            }catch (ProjectDoesntExistException e){
                debug.debugOut("Task can't be added to nonexisting project!");
            }
        }
    }
    private void cancel(){
        frame.dispose();
    }


}

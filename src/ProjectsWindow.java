import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Woodbin on 24.9.2014.
 */
public class ProjectsWindow {
    private JList projectsList;
    private JPanel projectsPanel;
    private JButton startTaskButton;
    private JButton postponeTaskButton;
    private JFrame frame;
    private JButton refreshButton;
    private JButton finishTaskButton;
    private JScrollPane projectsScrollPane;
    private String currentProjectId;
    private Task currentTask;
    private DefaultListModel listModel;


    //REFERENCES
    private static Core core = Core.getInstance();
    private static DebugModule debug = DebugModule.getInstance();
    private static App app = App.getInstance();

    /**
     * Factory method
     */
    public void create() {
        frame = new JFrame("ProjectsWindow");
        frame.setContentPane(projectsPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        listModel = new DefaultListModel();
        refresh();
        projectsList.setModel(listModel);

        frame.pack();

        debug.debugOut("ProjectsWindow created");
    }

    /**
     * Constructor
     */
    public ProjectsWindow() {
        startTaskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                startTask();
                super.mouseReleased(e);
            }
        });
        postponeTaskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                postponeTask();
                super.mouseReleased(e);
            }
        });
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                refresh();
                super.mouseReleased(e);
            }
        });
        finishTaskButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                finishTask();
                super.mouseReleased(e);
            }
        });
        finishTaskButton.setEnabled(true);


    }

    /**
     * Shows window
     */
    public void windowShow() {
        frame.show();
        refresh();
        debug.debugOut("ProjectsWindow showed");
    }

    /**
     * Hides window
     */
    public void windowHide() {
        frame.hide();
        debug.debugOut("ProjectsWindow hidden");
    }

    /**
     * Enables and disables buttons
     * @param change which king of change we want
     */
    public void changeButtons(ProjectsWindowButtonStateChange change) {
        switch (change) {
            case STARTTASK:
                startTaskButton.setEnabled(false);
                finishTaskButton.setEnabled(true);
                app.setTaskFinishItemState(true);
                break;
            case COMPLETETASK:
                startTaskButton.setEnabled(true);
                finishTaskButton.setEnabled(false);
                app.setTaskFinishItemState(false);
                break;
            case POSTPONETASK:
                startTaskButton.setEnabled(true);
                finishTaskButton.setEnabled(false);
                app.setTaskFinishItemState(false);
                break;
        }
    }

    /**
     * Starts new task
     */
    private void startTask() {
        if (!projectsList.isSelectionEmpty()){
            ArrayList<String> id = new ArrayList<String>(Arrays.asList(listModel.get(projectsList.getSelectedIndex()).toString().split(" ")));
            debug.debugOut(id.get(0));

            try {
                currentProjectId=core.getProjectById(id.get(0)).getId();
            } catch (ProjectDoesNotExistException e) {
                e.printStackTrace();
            }
            DescriptionWindow descriptionWindow = new DescriptionWindow(currentProjectId, false, this);
        descriptionWindow.createAndShow();
        }else {
            debug.debugOut("No project selected!");
        }
    }

    /**
     * Finishes task
     */
    private void finishTask(){
        DescriptionWindow descriptionWindow = new DescriptionWindow(currentProjectId, true, this);
        descriptionWindow.createAndShow();
    }

    /**
     * Postpones task
     */
    private void postponeTask(){
        //TODO Postponing task in TaskWindow
        changeButtons(ProjectsWindowButtonStateChange.POSTPONETASK);
    }

    /**
     * Refreshses list of projects
     */
    public void refresh(){
        listModel.clear();
        for(int i=0;i<core.getProjects().size();i++){
            listModel.addElement(core.getProjects().get(i).getId()+" "+core.getProjects().get(i).getName());
            debug.debugOut("Listmodel element "+i+": "+listModel.get(i).toString());
        }
    }


    public Task getCurrentTask(){
        return currentTask;
    }
    public void setCurrentTask(Task t){
        currentTask=t;
    }





}

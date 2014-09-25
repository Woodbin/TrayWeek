import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private int currentProjectId = 0;
    private Task currentTask;
    private DefaultListModel listModel;


    //REFERENCES
    private static Core core = Core.getInstance();
    private static DebugModule debug = DebugModule.getInstance();
    private static App app = App.getInstance();

    public void create(){
        frame = new JFrame("ProjectsWindow");
        frame.setContentPane(projectsPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        listModel = new DefaultListModel();
        refresh();
        projectsList.setModel(listModel);

        frame.pack();

        debug.debugOut("ProjectsWindow created");
    }

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

    public void windowShow(){
        frame.show();
        refresh();
        debug.debugOut("ProjectsWindow showed");
    }
    public void windowHide(){
        frame.hide();
        debug.debugOut("ProjectsWindow hidden");
    }


    public void changeButtons(ProjectsWindowButtonStateChange change){
        switch (change){
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

    private void startTask(){
        //TODO Starting task in TaskWindow
        DescriptionWindow descriptionWindow = new DescriptionWindow(core.getProjects().get(currentProjectId).getName(), false,this);
        descriptionWindow.createAndShow();
    }
    private void finishTask(){
        //TODO Finishing task in TaskWindow
        changeButtons(ProjectsWindowButtonStateChange.COMPLETETASK);
    }
    private void postponeTask(){
        //TODO Postponing task in TaskWindow
        changeButtons(ProjectsWindowButtonStateChange.POSTPONETASK);
    }
    public void refresh(){
        //TODO Refreshing list of tasks in TaskWindow
        listModel.clear();
        for(int i=0;i<core.getProjects().size();i++){
            listModel.addElement(core.getProjects().get(i).getName());
        }
    }


    public Task getCurrentTask(){
        return currentTask;
    }
    public void setCurrentTask(Task t){
        currentTask=t;
    }





}

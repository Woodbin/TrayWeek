import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Woodbin on 24.9.2014.
 */
public class TasksWindow {
    private JList tasksList;
    private JPanel tasksPanel;
    private JButton startTaskButton;
    private JButton postponeTaskButton;
    private JFrame frame;
    private JButton refreshButton;
    private JButton finishTaskButton;
    private int taskId = 0;


    //REFERENCES
    private static Core core = Core.getInstance();
    private static DebugModule debug = DebugModule.getInstance();
    private static App app = App.getInstance();

    public void create(){
        frame = new JFrame("TasksWindow");
        frame.setContentPane(tasksPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();

        debug.debugOut("TasksWindow created");
    }

    public TasksWindow() {
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
        debug.debugOut("TasksWindow showed");
    }
    public void windowHide(){
        frame.hide();
        debug.debugOut("TasksWindow hidden");
    }


    public void changeButtons(TasksWindowButtonStateChange change){
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
        DescriptionWindow description = new DescriptionWindow(taskId, false,this);
        description.createAndShow();
    }
    private void finishTask(){
        //TODO Finishing task in TaskWindow
        changeButtons(TasksWindowButtonStateChange.COMPLETETASK);
    }
    private void postponeTask(){
        //TODO Postponing task in TaskWindow
        changeButtons(TasksWindowButtonStateChange.POSTPONETASK);
    }
    public void refresh(){
        //TODO Refreshing list of tasks in TaskWindow
    }







}

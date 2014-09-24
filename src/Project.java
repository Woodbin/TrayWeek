import java.util.ArrayList;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Project {
    private int projectId;
    private String name;
    private ArrayList<Task> finishedTasks;


    public Project(int _projectId, String _name){
        projectId=_projectId;
        name = _name;
        finishedTasks = new ArrayList<Task>();
    }

    public void appendTask(Task _t){
        finishedTasks.add(_t);
    }

    public Task getTask(int _taskId){
        return finishedTasks.get(_taskId);
    }




}

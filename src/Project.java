import java.util.ArrayList;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Project {
    private String name;
    private ArrayList<Task> finishedTasks;


    public Project(String _name){
        name = _name;
        finishedTasks = new ArrayList<Task>();
    }

    public void appendTask(Task _t){
        finishedTasks.add(_t);
    }

    public Task getTask(int _taskId){
        return finishedTasks.get(_taskId);
    }

    public String getName(){
        return name;
    }


}

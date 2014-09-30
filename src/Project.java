import java.util.ArrayList;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Project {
    private String name;
    private String id;
    private ArrayList<Task> finishedTasks;




    public Project(String _id, String _name) throws ProjectAlreadyExitsException{

            if(!checkAvailability(_id)) throw new ProjectAlreadyExitsException("Project exists!");
        else
            name = _name;
            id = _id;
            finishedTasks=new ArrayList<Task>();
    }

    public void appendTask(Task _t){
        try {
            Core.action(CoreAction.COMPLETETASK);
            finishedTasks.add(_t);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public String getId(){
        return id;
    }

    public Task getTask(int _taskId){
        return finishedTasks.get(_taskId);
    }

    public String getName(){
        return name;
    }

    private boolean checkAvailability(String id){
        boolean available=true;
        for(int i =0;i<Core.getProjects().size();i++){
            if(Core.getProjects().get(i).getId().equals(id)) available=false;
            break;
        }
        return available;
    }

}

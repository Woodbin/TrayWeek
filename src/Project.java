import java.util.ArrayList;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Project {
    private String name;
    private String id;
    private ArrayList<Task> finishedTasks;
    private ArrayList<Project> parent;


    /**
     * Project constructor
     * @param _id must be unique
     * @param _name doesn't have to be unique
     * @throws ProjectAlreadyExitsException thrown if _id isn't unique
     */
    public Project(String _id, String _name) throws ProjectAlreadyExitsException{
        this(_id,_name,Core.getProjects());
    }

    public Project(String _id, String _name, ArrayList<Project> _parent) throws ProjectAlreadyExitsException{
        parent = _parent;
        if(!checkAvailability(_id)) throw new ProjectAlreadyExitsException("Project exists!");
        else
            name = _name;
        id = _id;
        finishedTasks=new ArrayList<Task>();
    }
    /**
     * Appends task _t to ArrayList of finished tasks
     * @param _t
     */
    public void appendTask(Task _t){

            finishedTasks.add(_t);

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

    /**
     * Checks if project id is available
     * @param id
     * @return available/not available
     */
    private boolean checkAvailability(String id){
        boolean available=true;
        if(!parent.isEmpty()){
            for(int i =0;i<parent.size();i++){
                if(parent.get(i).getId().equals(id)) available=false;
                break;
            }
        }

        return available;
    }

}

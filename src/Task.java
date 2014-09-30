import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Task {
    private String projectId;
    private String description;
    private Timestamp startTimestamp;
    private Timestamp finishTimestamp;

    public Task( String _projectId, String _description) throws ProjectDoesntExistException{
        if(Core.checkProjectId(_projectId)){
            projectId=_projectId;
            description=_description;
            Date date = new Date();
            startTimestamp = new Timestamp(date.getTime());
        }else{

            DebugModule.debugOut(DebugModule.getErrorMessage(DebugModule.errorCodeProjectNA));
        }

    }

    public void setDescription(String _description){
        description=_description;
    }

    public void finishTask(){
        Date date = new Date();
        finishTimestamp = new Timestamp(date.getTime());
    }
    public String getProjectId(){
        return projectId;
    }

    public Timestamp getStartTimestamp(){
        return startTimestamp;
    }
    public  Timestamp getFinishTimestamp(){
        return finishTimestamp;
    }
    public String getDescription(){
        return description;
    }


}

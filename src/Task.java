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

    /**
     * Task constructor
     * @param _projectId project must already exist!
     * @param _description Can be empty
     * @throws ProjectDoesNotExistException
     */
    public Task( String _projectId, String _description) throws ProjectDoesNotExistException {
        if(Core.checkProjectId(_projectId)){
            projectId=_projectId;
            description=_description;
            Date date = new Date();
            startTimestamp = new Timestamp(date.getTime());
        }else{

            DebugModule.debugOut(DebugModule.getErrorMessage(DebugModule.errorCodeProjectNA));
        }

    }

    /**
     * Sets description (used when finishing task)
     * @param _description
     */
    public void setDescription(String _description){
        description=_description;
    }

    /**
     * Finishes task - gives it finishing timestamp
     */
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

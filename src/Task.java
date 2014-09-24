import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Woodbin on 25.9.2014.
 */
public class Task {
    private int id;
    private int projectId;
    private String description;
    private Timestamp startTimestamp;
    private Timestamp finishTimestamp;

    public Task(int _id, int _projectId, String _description){
        id=_id;
        projectId=_projectId;
        description=_description;
        Date date = new Date();
        startTimestamp = new Timestamp(date.getTime());
    }

    public void setDescription(String _description){
        description=_description;
    }

    public void finishTask(){
        Date date = new Date();
        finishTimestamp = new Timestamp(date.getTime());

    }




}

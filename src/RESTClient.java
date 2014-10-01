import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Woodbin on 1.10.2014.
 */
public class RESTClient {
    private static RESTClient ourInstance = new RESTClient();

    public static RESTClient getInstance() {
        return ourInstance;
    }

    private RESTClient() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Project getProject(String name){
        Project ret = null;
        try {
            ret = new Project("","");
        } catch (ProjectAlreadyExitsException e) {
            e.printStackTrace();
        }

        //TODO REST get project

        return ret;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static ArrayList<String> getProjectList(){
        ArrayList<String> projects = new ArrayList<String>();

        //TODO REST get projects list

        return projects;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public static Response commitTask(Task t){
        String output = t.getDescription();

        //TODO REST task commit

        return Response.status(200).entity(output).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public static Response login(String[] credentials){

        String output = "login";



        return Response.status(200).entity(output).build();


    }
}

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Woodbin on 1.10.2014.
 */
public class RESTClient {
    private static RESTClient ourInstance = new RESTClient();
    private static ClientConfig config;
    private static Client client;
    private static WebTarget target;
    private static String url;



    public static RESTClient getInstance() {
        return ourInstance;
    }




    private RESTClient() {
        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(getURI());
    }

    private static URI getURI(){
        return UriBuilder.fromUri(url).build();
    }

    public static Project getProject(String name){
        Project ret = null;
        WebTarget resource = target.path(name);


        try {
            ret = new Project("","");
        } catch (ProjectAlreadyExitsException e) {
            e.printStackTrace();
        }

        //TODO REST get project

        return ret;
    }


    public static ArrayList<String> getProjectList(){
        ArrayList<String> projects = new ArrayList<String>();

        //TODO REST get projects list

        return projects;
    }


    public static boolean commitTask(Task t){
        boolean success = false;
        //TODO REST task commit

        return success;
    }


    public static boolean login(String login, String password){

            boolean success = false;
        //TODO REST Login logic


            return success;
    }

    public static ArrayList<Project> assembleProjects(){
        ArrayList<Project> ret = new ArrayList<Project>();

        //TODO assemble projects

        return ret;

    }
}

import java.util.ArrayList;

/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static Core coreObject = new Core();
    private static ArrayList<Project> projects;

    private static boolean loggedIn=false;

    //REFERENCES
    private static App app = App.getInstance();
    private static DebugModule debug = DebugModule.getInstance();

    public static Core getInstance() {
        return coreObject;
    }

    private Core() {
        projects = new ArrayList<Project>();
        createFakeData();
    }

    /** Does magic corresponding to action
     *
     * @param ac enum-which action we do
     * @param args parameters
     */
    public static void action(CoreAction ac, String args[]){
        switch (ac){
            case CLOSE: closeCore(Integer.parseInt(args[0])); break;
            case LOGIN: login(args); break;
            case LOGOUT: logout(); break;
            case COMPLETETASK: completeTask(); break;
            case NEWPROJECT: newProject(args); break;

        }
    }

    public static void action(CoreAction ac){
        String args[]=new String[1];
        args[0] = "1";
        action(ac,args);
    }



    /** Close Core action
     * Closes the app with corresponding errorcode
     *
     * @param errorcode
     */
    private static void closeCore(int errorcode){
        debug.debugOut("Closing core with errorcode: " + errorcode + " ~ " + debug.getErrorMessage(errorcode));
        System.exit(errorcode);


    }

    private static void login(String args[]){
        debug.debugOut("Login command called");
        debug.debugOut("Logging in with login: " + args[0] + "; password: " + args[1]);
        //TODO Login logic here

        loggedIn=true;
        app.setLoginItemState(false);
        app.setLogoutItemState(true);
        app.setTasksItemState(true);
    }

    private static void logout(){
        debug.debugOut("Logging out");
        //TODO Logout Logic here

        loggedIn=false;
        app.setLoginItemState(true);
        app.setLogoutItemState(false);
        app.setTasksItemState(false);

    }

    private static void completeTask(){
        //TODO CompleteTask logic here
        debug.debugOut("Completing task...");

        app.setTaskFinishItemState(false);
    }
    private static void newProject(String args[]){
        debug.debugOut("Creating new project with id: " + args[0]+" and name: "+args[1]);
        try{projects.add(new Project(args[0],args[1]));}
        catch(ProjectAlreadyExitsException e){
            debug.debugOut("Project with id: "+args[0]+" already exists!");
        }
        ;
    }

    public static boolean getLoginState(){
        return loggedIn;

    }


    private static void createFakeData(){
        debug.debugOut("Creating FakeData");
        for(int i = 0;i<10;i++){
            try{
                projects.add(new Project(i+"","fakeProject"+i));
                debug.debugOut("Created Fake Project: " + projects.get(i).getName());

            }catch (ProjectAlreadyExitsException e){
                    debug.debugOut("Project with id: "+i+" already exists!");
                }
            }
        }


    public static Project getProjectById(String id) throws ProjectDoesntExistException{
        int temp=-1;
        for(int i = 0; i<projects.size();i++){
            if(id.equals(projects.get(i).getId()))
            { temp = i;
            debug.debugOut("Found project with id: "+projects.get(i).getId());
            }
        }
        if(temp==-1 ) throw new ProjectDoesntExistException("Project doesn't exist!");
        return projects.get(temp);

    }

    public static ArrayList<Project> getProjects(){
        return projects;
    }
}

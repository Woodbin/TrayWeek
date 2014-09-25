import java.util.ArrayList;

/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static Core coreObject = new Core();
    private static DebugModule debugmodule = DebugModule.getInstance();
    private static ArrayList<Project> projects;

    private static boolean loggedIn=false;

    //REFERENCES
    private static App app = App.getInstance();

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
        debugmodule.debugOut("Closing core with errorcode: " + errorcode + " ~ " + debugmodule.getErrorMessage(errorcode));
        System.exit(errorcode);


    }

    private static void login(String args[]){
        debugmodule.debugOut("Login command called");
        debugmodule.debugOut("Logging in with login: "+args[0]+"; password: "+args[1] );
        //TODO Login logic here

        loggedIn=true;
        app.setLoginItemState(false);
        app.setLogoutItemState(true);
        app.setTasksItemState(true);
    }

    private static void logout(){
        debugmodule.debugOut("Logging out");
        //TODO Logout Logic here

        loggedIn=false;
        app.setLoginItemState(true);
        app.setLogoutItemState(false);
        app.setTasksItemState(false);

    }

    private static void completeTask(){
        //TODO CompleteTask logic here
        debugmodule.debugOut("Completing task...");

        app.setTaskFinishItemState(false);
    }
    private static void newProject(String args[]){
        debugmodule.debugOut("Creating new project: "+args[0]);
        projects.add(new Project(args[0]));
    }

    public static boolean getLoginState(){
        return loggedIn;

    }


    private static void createFakeData(){
        debugmodule.debugOut("Creating FakeData");
        for(int i = 0;i<10;i++){
            projects.add(new Project("fakeProject"+i));
            debugmodule.debugOut("Created Fake Project: "+projects.get(i).getName());
        }
    }

    public static ArrayList<Project> getProjects(){
        return projects;
    }
}

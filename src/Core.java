import java.util.ArrayList;

/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static ArrayList<Project> projects;
    private static Task currentTask;


    private static boolean loggedIn=false;

    //REFERENCES
    private static App app = App.getInstance();
    private static DebugModule debug = DebugModule.getInstance();
    private static Core coreObject = new Core();

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
    public static void action(CoreAction ac, String args[]) throws CoreException{
        switch (ac){
            case CLOSE: closeCore(Integer.parseInt(args[0])); break;
            case LOGIN: login(args); break;
            case LOGOUT: logout(); break;
            case COMPLETETASK: completeTask(args); break;
            case NEWPROJECT: newProject(args); break;
            case NEWTASK:
                try{
                    newTask(args[0],args[1]);
                    break;
                }catch(ProjectDoesntExistException p){

                }

        }
    }

    public static void action(CoreAction ac)throws CoreException{
        String args[]=new String[1];
        args[0] = "1";
        try {
            action(ac,args);
        } catch (CoreException e) {
            throw e;
        }
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

    private static void completeTask(String args[]){
        //TODO CompleteTask logic here
        try {
            debug.debugOut("Completing task...");
            currentTask.finishTask();
            currentTask.setDescription(args[0]);
            debug.debugOut("Finishing task for project " + getProjectById(currentTask.getProjectId()).getName() + " with description: \n" + currentTask.getDescription() + "\n and timestamp " + currentTask.getFinishTimestamp().toString());
            getProjectById(currentTask.getProjectId()).appendTask(currentTask);


            app.setTaskFinishItemState(false);
        }catch (ProjectDoesntExistException p){

        }
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

    public static Task getCurrentTask(){
        return currentTask;
    }

    public static void setCurrentTask(Task _t){
        currentTask = _t;
    }

    private static void newTask(String projectId, String description) throws ProjectDoesntExistException{
        try {
            currentTask = new Task(projectId, description);
        }catch(ProjectDoesntExistException p) {
        throw p;
        }
    }

    public static boolean checkProjectId(String id){
        boolean ret = false;
        for(Project p :projects ){
            if(p.getId().equals(id)){
                ret = true;
                break;
            }
        }
        return ret;
    }



}

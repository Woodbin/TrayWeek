import java.util.ArrayList;

/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static ArrayList<Project> projects;
    private static Task currentTask;


    private static boolean loggedIn=false;
    private static boolean autoLogin=false;
    private static boolean testMode=true;

    private static int fakeDataCount = 10;
    private static String login;
    private static String password;

    //REFERENCES
    private static App app = App.getInstance();
    private static DebugModule debug = DebugModule.getInstance();
    private static Core coreObject = new Core();

    public static Core getInstance() {
        return coreObject;
    }

    /**
     * Constructor
     */
    private Core() {
        projects = new ArrayList<Project>();
        try {
            action(CoreAction.FETCH);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /** Does magic corresponding to action
     *
     * @param ac enum-which action we do
     * @param args parameters
     * @throws CoreException
     */
    public static void action(CoreAction ac, String args[]) throws CoreException{
        switch (ac){
            case CLOSE: closeCore(Integer.parseInt(args[0])); break;
            case LOGIN:{
                setLoginCredentials(args[0],args[1]);
                login();
                break;
            }
            case LOGOUT: logout(); break;
            case COMPLETETASK: completeTask(args); break;
            case NEWPROJECT: newProject(args); break;
            case NEWTASK:
                try{
                    newTask(args[0],args[1]);
                    break;
                }catch(ProjectDoesNotExistException p){

                }
            case RECREATEFAKES: createFakeData(); break;
            case FETCH: fetchData();
        }
    }

    /**
     * Does magic corresponding to action - for actions with no parameters
     * @param ac enum-which action we do
     * @throws CoreException
     */
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
        if(currentTask!=null){
            if(currentTask.getDescription().equals("")){
                debug.debugOut("Finish Task first!");
                App.createFinishingWindow();
                return;
            }else{
                try {
                    debug.debugOut("Closing core with errorcode: " + errorcode + " ~ " + debug.getErrorMessage(errorcode));
                    action(CoreAction.COMPLETETASK);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }

        }
        System.exit(errorcode);


    }

    /**
     * Login method
     *
     */
    private static void login(){
        debug.debugOut("Login command called");
        debug.debugOut("Logging in with login: " + login + "; password: " + password);
        if((RESTClient.login(login,password))||testMode){
            try {
                action(CoreAction.FETCH);
                loggedIn=true;
                app.setLoginItemState(false);
                app.setLogoutItemState(true);
                app.setTasksItemState(true);
            }catch(CoreException ce){
            }

        }
    }

    /**
     * Logout method
     */
    private static void logout(){
        debug.debugOut("Logging out");
        loggedIn=false;
        projects.clear();
        app.setLoginItemState(true);
        app.setLogoutItemState(false);
        app.setTasksItemState(false);

    }

    private static void fetchData(){
        debug.debugOut("Called fetch");
        if(testMode){
            createFakeData();
        }else{
            if(loggedIn){
                debug.debugOut("Fetching projects");
                projects = RESTClient.assembleProjects();
            }
        }

    }

    /**Complete Task
     * Completes task, sets it's description, appends it to it's project
     * @param args
     */
    private static void completeTask(String args[]){
        try {
            debug.debugOut("Completing task...");
            currentTask.finishTask();
            currentTask.setDescription(args[0]);
            debug.debugOut("Finishing task for project " + getProjectById(currentTask.getProjectId()).getName() + " with description: \n" + currentTask.getDescription() + "\n and timestamp " + currentTask.getFinishTimestamp().toString());
            getProjectById(currentTask.getProjectId()).appendTask(currentTask);
            if(!testMode){
                if(RESTClient.commitTask(currentTask));

            }

        }catch (ProjectDoesNotExistException p){
            debug.debugOut("Project with id "+currentTask.getProjectId()+" doesn't exist!");

        }
    }

    /**
     * Creates new project
     * @param args Name of project
     */
    private static void newProject(String args[]){
        debug.debugOut("Creating new project with id: " + args[0]+" and name: "+args[1]);
        try{projects.add(new Project(args[0],args[1]));}
        catch(ProjectAlreadyExitsException e){
            debug.debugOut("Project with id: "+args[0]+" already exists!");
        }
        ;
    }

    /**
     * Returns if logged in or not
     * @return
     */
    public static boolean getLoginState(){
        return loggedIn;

    }
    public static void setLoginCredentials(String log, String pass){
        login=log;
        password=pass;
    }
    /**
     * Creates fake projects
     */
    private static void createFakeData(){
        debug.debugOut("Creating FakeData");
        projects.clear();
        for(int i = 0;i<fakeDataCount;i++){
            try{
                projects.add(new Project(i+"","fakeProject"+i));
                debug.debugOut("Created Fake Project: " + projects.get(i).getName());

            }catch (ProjectAlreadyExitsException e){
                    debug.debugOut("Project with id: "+i+" already exists!");
                }
            }
        }

    /**
     * Sets number of fake projects to be created.
     * @param count of fake projects
     */
    public static void setFakeDataCount(int count){
        fakeDataCount=count;
    }


    /**
     * Find Project with specified projectId and returns it
     * @param id
     * @return Project
     * @throws ProjectDoesNotExistException  throws in case the project with specified id doesn't exist
     */
    public static Project getProjectById(String id) throws ProjectDoesNotExistException {
        int temp=-1;
        for(int i = 0; i<projects.size();i++){
            if(id.equals(projects.get(i).getId()))
            { temp = i;
            debug.debugOut("Found project with id: "+projects.get(i).getId());
            }
        }
        if(temp==-1 ) throw new ProjectDoesNotExistException("Project doesn't exist!");
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

    public static boolean getAutoLoginState(){
        return autoLogin;
    }
    public static void setAutoLoginState(boolean _b){
        autoLogin=_b;
    }
    public static boolean getTestModeState(){
        return testMode;
    }
    public static void setTestModeState(boolean _b){
        testMode=_b;
    }
    public static int getFakeDataCount(){
        return fakeDataCount;
    }

    /**
     * Creates new Task with specified projectId and description
     * @param projectId
     * @param description
     * @throws ProjectDoesNotExistException throws if the specified project doesn't exist
     */
    private static void newTask(String projectId, String description) throws ProjectDoesNotExistException {
        try {
            currentTask = new Task(projectId, description);
        }catch(ProjectDoesNotExistException p) {
        throw p;
        }
    }

    /**
     * Checks if project with id exists
     * @param id
     * @return exists or not
     */
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

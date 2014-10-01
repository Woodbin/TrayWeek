import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Woodbin on 24.9.2014.
 */
public class DebugModule {
    private static DebugModule ourInstance = new DebugModule();
    private static  boolean consoleWindowExists;

    /**
     * Help message
     */
    private static String helpstring = "List of commands \n" +
            "close                        ukončí klienta\n" +
            "help                         zobrazuje tuto zprávu\n" +
            "version                      ukáže číslo verze\n" +
            "login [login] [password]     přihlášení na server za použití napsaných údajů\n" +
            "logout                       odhlášení ze serveru\n" +
            "completeTask                 dokončí aktuálně vybraný úkol\n" +
            "new project [id] [name]      vytvoří nový fakeproject\n" +
            "fetch                        vytvoří fakedata/stáhne data ze serveru\n" +
            "set fakedatacount [count]    nastaví množství fakeprojektů";


    public static DebugModule getInstance() {
        return ourInstance;
    }

    //REFERENCES
    public static App app = App.getInstance();
    public static Core core = Core.getInstance();
    public static Parser parser = Parser.getInstance();

    private DebugModule() {
        consoleWindowExists=false;
        debugOut("DebugModule active");
    }

    //ERRORCODE VARIABLES
    public static final int errorCodeCloseOkay = 1;
    public static final int errorCodeCloseOkayConsole = 2;
    public static final int errorCodeProjectNA = 3;


    public static void debugOut(String message){
        processOut(message);
    }

    public static void debugIn(String command){
     processIn(command);
    }

    public static void setConsoleWindowState(boolean state){
        consoleWindowExists = state;
    }

    public static boolean getConsoleWindowState(){
        return consoleWindowExists;
    }

    /**
     * Prints message to System.out and DebugConsole (if exists)
     * @param message
     */
    private static void processOut(String message){
        Date date = new Date();

        Timestamp timestamp = new Timestamp(date.getTime());
        if(consoleWindowExists){
            app.forwardToConsoleWindow("["+ timestamp.toString()+"] "+message);

        }
        System.out.println("["+ timestamp.toString()+"] "+message);
    }

    /**
     * Processes command from DebugConsole
     * @param command
     */
    private static void processIn(String command){
        //TODO more command parsing and execution through core
        ArrayList<String> commands = parser.parseCommand(command);
        if(commands.get(0).toLowerCase().equals("close")){
            String args[]=new String[1];
            args[0] = "2";
            try {
                core.action(CoreAction.CLOSE,args);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        if(commands.get(0).toLowerCase().equals("help")){
            debugOut(helpstring);
        }
        if(commands.get(0).toLowerCase().equals("version")){
            debugOut("WIP Alpha");
        }if(commands.get(0).toLowerCase().equals("login")){
            String args[]=new String[2];
            args[0]=commands.get(1);
            args[1]=commands.get(2);
            try {
                core.action(CoreAction.LOGIN,args);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        if(commands.get(0).toLowerCase().equals("logout")){
            try {
                core.action(CoreAction.LOGOUT);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        if(commands.get(0).toLowerCase().equals("completetask")){
            try {
                core.action(CoreAction.COMPLETETASK);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }if(commands.get(0).toLowerCase().equals("new")){
            if(commands.get(1).toLowerCase().equals("project")){
                String args[]=new String[1];
                args[0]=commands.get(2);
                args[1]=commands.get(3);
                try {
                    core.action(CoreAction.NEWPROJECT,args);
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }if(commands.get(0).toLowerCase().equals("fetch")){
            try {
                core.action(CoreAction.FETCH);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }if(commands.get(0).toLowerCase().equals("set")){
            if(commands.get(1).toLowerCase().equals("fakedatacount")){
                core.setFakeDataCount(Integer.parseInt(commands.get(2)));
            }
        }



    }


    /**
     * Returns message corresponding to errorcode
     * @param errorcode
     * @return
     */
    public static String getErrorMessage(int errorcode){
        switch (errorcode){
            case errorCodeCloseOkay: return "Everything went better than expected";
            case errorCodeCloseOkayConsole: return "Close from Debugconsole, everything okay";
            case errorCodeProjectNA: return "Project doesn't exits!";
            default: return "Unknown errorcode: "+errorcode;
        }
    }


}

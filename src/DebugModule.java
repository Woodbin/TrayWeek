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

    private static String helpstring = "List of commands \n" +
            "close                        closes the client\n" +
            "help                         displays this message\n" +
            "version                      shows version\n" +
            "login [login] [password]     logs into server using specified credentials";


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

    private static void processOut(String message){
        Date date = new Date();

        Timestamp timestamp = new Timestamp(date.getTime());
        if(consoleWindowExists){
            app.forwardToConsoleWindow("["+ timestamp.toString()+"] "+message);

        }
        System.out.println("["+ timestamp.toString()+"] "+message);
    }

    private static void processIn(String command){
        //TODO command parsing and execution through core
        ArrayList<String> commands = parser.parseCommand(command);
        if(commands.get(0).toLowerCase().equals("close")){
            String args[]=new String[1];
            args[0] = "2";
            core.action(CoreAction.CLOSE,args);
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
            core.action(CoreAction.LOGIN,args);
        }
    }


    public static String getErrorMessage(int errorcode){
        switch (errorcode){
            case 1: return "Everything went better than expected";
            case 2: return "Close from Debugconsole, everything okay";
            default: return "Unknown errorcode: "+errorcode;
        }
    }


}

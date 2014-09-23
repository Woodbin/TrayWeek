/**
 * Created by Woodbin on 24.9.2014.
 */
public class DebugModule {
    private static DebugModule ourInstance = new DebugModule();
    private static  boolean consoleWindowExists;
    public static DebugModule getInstance() {
        return ourInstance;
    }

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

    public static void consoleWindowState(boolean state){
        consoleWindowExists = state;
    }



    private static void processOut(String message){
        if(consoleWindowExists){
            //TODO console window output
        }
        System.out.println(message);
    }

    private static void processIn(String command){
        //TODO command parsing and execution through core
    }


    public static String getErrorMessage(int errorcode){
        switch (errorcode){
            case 1: return "Everything went better than expected";
            default: return "Unknown errorcode: "+errorcode;
        }
    }


}

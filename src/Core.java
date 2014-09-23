/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static Core coreObject = new Core();
    private static DebugModule debugmodule = DebugModule.getInstance();




    public static Core getInstance() {
        return coreObject;
    }

    private Core() {
    }

    public static void action(CoreAction ac, String args[]){
        if(ac==CoreAction.CLOSE) closeCore(Integer.parseInt(args[0]));
    }


    private static void closeCore(int errorcode){
        debugmodule.debugOut("Closing core with errorcode: " + errorcode + " ~ " + debugmodule.getErrorMessage(errorcode));
        System.exit(errorcode);


    }






}

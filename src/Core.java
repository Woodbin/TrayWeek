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

    /** Does magic corresponding to action
     *
     * @param ac enum-which action we do
     * @param args parameters
     */
    public static void action(CoreAction ac, String args[]){
        if(ac==CoreAction.CLOSE) closeCore(Integer.parseInt(args[0]));
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






}

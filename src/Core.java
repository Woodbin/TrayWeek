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
        switch (ac){
            case CLOSE: closeCore(Integer.parseInt(args[0])); break;
            case LOGIN: login(args); break;
        }
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

    }






}

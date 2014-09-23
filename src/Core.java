/**
 * Created by Woodbin on 23.9.2014.
 */
public class Core {
    private static Core coreObject = new Core();



    public static Core getInstance() {
        return coreObject;
    }

    private Core() {
    }

    public static void action(CoreAction ac, String args[]){
        if(ac==CoreAction.CLOSE) closeCore(Integer.parseInt(args[0]));
    }


    private static void closeCore(int errorcode){
        System.out.print("Closing core with errorcode: "+errorcode+" ~ "+getError(errorcode));
        System.exit(errorcode);


    }

    private static String getError(int errorcode){
        switch (errorcode){
            case 1: return "Alles in Ordnung";
            default: return "Unknown errorcode: "+errorcode;
        }
    }




}

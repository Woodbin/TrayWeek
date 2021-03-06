/**
 * Created by Woodbin on 23.9.2014.
 */
public class Main {
    private static Main mainClass = new Main();
    private static Core coreObject;
    private static App app;

    /**
     * Singleton
     * @return singleton main class
     */
    public static Main getInstance() {
        return mainClass;
    }

    /**
     * Overriden constructor
     */
    private Main() {
        coreObject = Core.getInstance();
        app = App.getInstance();


    }

    /** Main method
     *
     * @param args parameters
     */
    public static void main(String args[]){
        app = App.getInstance();
        app.init(args);


    }


}

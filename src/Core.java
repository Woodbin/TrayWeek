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
}

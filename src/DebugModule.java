/**
 * Created by Woodbin on 24.9.2014.
 */
public class DebugModule {
    private static DebugModule ourInstance = new DebugModule();

    public static DebugModule getInstance() {
        return ourInstance;
    }

    private DebugModule() {
    }
}

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser class
 * Created by Woodbin on 24.9.2014.
 */
public class Parser {
    private static Parser ourInstance = new Parser();

    public static Parser getInstance() {
        return ourInstance;
    }

    private Parser() {
    }

    /**
     * Parses command string to tokens (ArrayList<String>)
     * @param command
     * @return
     */
    public static ArrayList<String> parseCommand(String command){
        ArrayList<String> ret = new ArrayList<String>(Arrays.asList(command.split(" ")));
        return ret;

    }


}

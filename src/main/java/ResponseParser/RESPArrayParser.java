package ResponseParser;

import java.io.PrintWriter;
import java.util.List;

public class RESPArrayParser {
    public static void sendArray(List<String> responseString, PrintWriter out){
        out.println("*"+responseString.size()+"\r");
        for (String s : responseString) {
            out.println("$" + s.length() + "\r");
            out.println(s + "\r");
        }
    }
}

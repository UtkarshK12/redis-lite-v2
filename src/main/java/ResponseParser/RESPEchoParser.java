package ResponseParser;

import java.io.PrintWriter;

public class RESPEchoParser {
    public static void echoResponse(String response, PrintWriter out){
        out.println("$" + response.length()+"\r");
        out.println(response+"\r");
    }
}

package Services;

import java.io.PrintWriter;
import java.util.List;

public class EchoResponseService {
    public static void echoResponse(String response, PrintWriter out){
        // Format as RESP bulk string
        out.println("$" + response.length()+"\r");
        out.println(response+"\r");
    }
}

package org.example;

import java.io.IOException;
import java.io.PrintWriter;

public class RequestHandler {

    public void handleRequest(String request, PrintWriter out) throws IOException {
        if(request.equals("PING")){
            out.println("+PONG\r");
        }
    }

}

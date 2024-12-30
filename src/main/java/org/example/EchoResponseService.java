package org.example;

import java.io.PrintWriter;
import java.util.List;

public class EchoResponseService {
    public static void echoResponse(List<String> input, PrintWriter out){
        String response = input.get(1);  // For ECHO command, get the second argument
        // Format as RESP bulk string
        out.println("$" + response.length()+"\r");
        out.println(response+"\r");
    }
}

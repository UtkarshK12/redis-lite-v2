package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BulkStringProcessor {

    public static void handleRequest(String input, BufferedReader in, PrintWriter out) throws IOException {

            //getting number of arguments
            int numOfArguments= Integer.parseInt(input.substring(1));

            //storing the request as list
            List<String> reqStrings=new ArrayList<>();

            for(int i=0;i<numOfArguments;i++){
                // to ignore the size of the word
                in.readLine();
                // getting each word here
                String req = in.readLine();
                reqStrings.add(req);
            }

            if(reqStrings.getFirst().equalsIgnoreCase("echo")){
                EchoResponseService.echoResponse(reqStrings,out);
            }
            else if (reqStrings.getFirst().equalsIgnoreCase("ping")){
                System.out.println("*********** ping inside");
                PingResponseService.replyPong(out);
            }
    }

}

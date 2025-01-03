package Processors;

import ResponseParser.RESPEchoParser;
import Services.*;
import memoryStore.InMemDataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BulkStringProcessor {

    public static void handleRequest(String input, BufferedReader in, PrintWriter out, InMemDataStore inMemDataStore) throws IOException {

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
                RESPEchoParser.echoResponse(reqStrings.get(1),out);
            }
            else if (reqStrings.getFirst().equalsIgnoreCase("ping")){
                PingResponseService.replyPong(out);
            }

            else if(reqStrings.getFirst().equalsIgnoreCase("set")){
                SetKeyResponseService.add(inMemDataStore,reqStrings,out);
            }
            else if(reqStrings.getFirst().equalsIgnoreCase("get")){
                GetKeyResponseService.getKey(inMemDataStore,reqStrings,out);
            }
            else if(reqStrings.getFirst().equalsIgnoreCase("config")){
                System.out.println("***** setting config");
                if(reqStrings.get(1).equalsIgnoreCase("get")){
                    System.out.println("***** setting get");
                    if(reqStrings.get(2).equalsIgnoreCase("dir")){
                        RdbConfigResponseService.sendDbDir(inMemDataStore,reqStrings,out);
                    }
                    else if(reqStrings.get(2).equalsIgnoreCase("dbfilename")){
                        RdbConfigResponseService.sendDbFileName(inMemDataStore,reqStrings,out);
                    }
                }
            }
    }

}

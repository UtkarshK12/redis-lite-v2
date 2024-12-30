package Services;

import memoryStore.InMemDataStore;

import java.io.PrintWriter;
import java.util.List;

public class GetKeyResponseService {

    public static void getKey( InMemDataStore inMemDataStore, List<String> reqString,PrintWriter out){
        String key = reqString.get(1);
        String response = inMemDataStore.get(key);
        if(response!=null){
            EchoResponseService.echoResponse(response,out);
        }
        else{
            out.println("$-1\r");
        }
    }
}

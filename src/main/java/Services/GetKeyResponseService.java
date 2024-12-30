package Services;

import memoryStore.InMemDataStore;

import java.io.PrintWriter;
import java.util.List;

public class GetKeyResponseService {

    public static void getKey( InMemDataStore inMemDataStore, List<String> reqString,PrintWriter out){
        String key = reqString.get(1);
        String response = inMemDataStore.get(key);
        EchoResponseService.echoResponse(response,out);
    }
}

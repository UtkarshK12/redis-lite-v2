package Services;

import memoryStore.InMemDataStore;

import java.io.PrintWriter;
import java.util.List;

public class SetKeyResponseService {

    public static void add(InMemDataStore inMemDataStore, List<String> reqStrings, PrintWriter out){
        String key = reqStrings.get(1);
        String val = reqStrings.get(2);
        System.out.println("****** setting -> "+key + " : "+ val);
        inMemDataStore.add(key,val);
        out.println("+OK\r");
    }

}

package Services;

import ResponseParser.RESPArrayParser;
import memoryStore.InMemDataStore;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RdbConfigResponseService {
    public static void sendDbDir(InMemDataStore inMemDataStore, List<String> reqString, PrintWriter out){
        System.out.println("***** setting dir");
        List<String> responseString=new ArrayList<>();
        responseString.add("dir");
        responseString.add(inMemDataStore.getRdbFilesConfig().getDir());
        RESPArrayParser.sendArray(responseString,out);
    }

    public static void sendDbFileName(InMemDataStore inMemDataStore, List<String> reqString, PrintWriter out){
        System.out.println("***** setting dbfilename");

        List<String> responseString=new ArrayList<>();
        responseString.add("dbfilename");
        responseString.add(inMemDataStore.getRdbFilesConfig().getDbFileName());
        RESPArrayParser.sendArray(responseString,out);

    }
}

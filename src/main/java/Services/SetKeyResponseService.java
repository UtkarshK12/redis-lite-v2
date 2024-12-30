package Services;

import memoryStore.InMemDataStore;

import java.io.PrintWriter;
import java.util.List;

import static java.lang.Thread.sleep;

public class SetKeyResponseService {

    public static void add(InMemDataStore inMemDataStore, List<String> reqStrings, PrintWriter out){
        String key = reqStrings.get(1);
        String val = reqStrings.get(2);
        if(reqStrings.size()<=3){
            System.out.println("****** setting -> "+key + " : "+ val);
            inMemDataStore.add(key,val);
            out.println("+OK\r");
        }
        else{
            String commandChain = reqStrings.get(3);
            if(commandChain.equalsIgnoreCase("px")){
                String timeout = reqStrings.get(4);
                System.out.println("****** setting -> "+key + " : "+ val);
                out.println("+OK\r");
                inMemDataStore.add(key,val);
                new Thread(()->{
                    try {
                        long startTime = System.currentTimeMillis();
                        Thread.sleep(Long.parseLong(timeout));
                        inMemDataStore.removeKey(key);

                        long endTime = System.currentTimeMillis();
                        long actualTimeout = endTime - startTime;

                        System.out.println("Removing key: " + key);
                        System.out.println("Requested timeout: " + timeout + "ms");
                        System.out.println("Actual timeout: " + actualTimeout + "ms");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }

}

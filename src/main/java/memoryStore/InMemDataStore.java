package memoryStore;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InMemDataStore {
    public HashMap<String,String> dataStore;
    static ReentrantLock lock;

    public InMemDataStore(){
        dataStore=new HashMap<>();
        lock=new ReentrantLock(true);
    }
    public void add(String key, String value){
        try{
            lock.lock();
            dataStore.put(key,value);
        }
        finally {
            lock.unlock();
        }
    }

    public String get(String key){
        try {
            lock.lock();
            if(dataStore.containsKey(key)){
                return dataStore.get(key);
            }
        }
        finally {
            lock.unlock();
        }
        return null;
    }
}

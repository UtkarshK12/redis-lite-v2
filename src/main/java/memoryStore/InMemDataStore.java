package memoryStore;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InMemDataStore {
    private final ConcurrentHashMap<String, String> dataStore;

    public InMemDataStore() {
        dataStore = new ConcurrentHashMap<>();
    }

    public void add(String key, String value) {
        System.out.println("***** adding key : "+ key+" : "+ value);
        dataStore.put(key, value);
    }

    public String get(String key) {
        System.out.println("****** fetching key : "+ dataStore.get(key));
        return dataStore.get(key);
    }

    public void removeKey(String key) {
        System.out.println("****** removing key : "+ key);
        dataStore.remove(key);
    }
}

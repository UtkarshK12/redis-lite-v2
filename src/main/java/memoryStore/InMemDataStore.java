package memoryStore;



import java.util.concurrent.ConcurrentHashMap;

public class InMemDataStore {
    private final ConcurrentHashMap<String, String> dataStore;
    private RDBFilesConfig rdbFilesConfig;

    public InMemDataStore(RDBFilesConfig rdbFilesConfig) {
        dataStore = new ConcurrentHashMap<>();
        this.rdbFilesConfig=rdbFilesConfig;
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

    public RDBFilesConfig getRdbFilesConfig() {
        return rdbFilesConfig;
    }
}

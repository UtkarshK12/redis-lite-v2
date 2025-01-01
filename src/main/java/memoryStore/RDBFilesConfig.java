package memoryStore;


public class RDBFilesConfig {
    private String dir;
    private String dbFileName;

    public RDBFilesConfig(){
        dir=new String("/tmp");
        dbFileName=new String("temp");
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDbFileName() {
        return dbFileName;
    }

    public void setDbFileName(String dbFileName) {
        this.dbFileName = dbFileName;
    }

    public void setConfig(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--dir":
                    if (i + 1 < args.length) {
                        this.setDir(args[i + 1]);
                        i++; // Skip the next argument since we just used it
                    }
                    break;
                case "--dbfilename":
                    if (i + 1 < args.length) {
                        this.setDbFileName(args[i + 1]);
                        i++; // Skip the next argument since we just used it
                    }
                    break;
            }
        }
        System.out.println("***Directory: " + this.dir);
        System.out.println("***DB Filename: " + this.dbFileName);

    }
}

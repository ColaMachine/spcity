package cola.machine.util.log;

public class LogKey {
    private static ThreadLocal<String> logKeyLocal = new ThreadLocal<String>();
    
    public  void init(String key ){
        String  keyValue = logKeyLocal.get();
        logKeyLocal.set(key);
       
    }
    public String  get(){
        return logKeyLocal.get();
    }
}

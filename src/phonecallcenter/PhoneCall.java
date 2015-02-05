package phonecallcenter;

import java.util.concurrent.Callable;

public class PhoneCall implements Callable<String> {
 
    private long waitTime;
    private PhoneCallManager manager;
    private String from;
    private String to;
     
    public PhoneCall(String from, String to, int timeInMillis){
    	manager = new PhoneCallManager();
    	this.from = from;
    	this.to = to;
        this.waitTime=timeInMillis;
    }
    
    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime); // PhoneCall duration
        manager.phoneCallHappened(this.from, this.to);
        return "Call from: "+this.from +" to: "+this.to+" is done.";
    }
 
}

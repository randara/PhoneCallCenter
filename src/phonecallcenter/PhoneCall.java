package phonecallcenter;

import java.util.concurrent.Callable;

public class PhoneCall implements Callable<Boolean> {
 
    private long callDuration;
    private PhoneCallManager manager;
    private String from;
    private String to;
     
    public PhoneCall(String from, String to, int duration) {
    	manager = PhoneCallManager.getInstance();
    	this.from = from;
    	this.to = to;
        this.callDuration = duration;
    }
    
    @Override
    public Boolean call() throws Exception {
        Thread.sleep(callDuration);
        manager.phoneCallHappened(this.from, this.to);
		return Boolean.TRUE;
    }
 
    public String toString(){
    	return "call from: "+this.from +" to: "+this.to;
    }
}

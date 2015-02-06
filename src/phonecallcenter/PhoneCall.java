package phonecallcenter;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PhoneCall implements Callable<Boolean> {

	// Call will be FutureTask so it can be executed. 
	private final FutureTask<Boolean> futureTask;
	
	private long callDuration;
	private PhoneCallManager manager;
	private String from;
	private String to;
	private boolean processCall;
	
	public PhoneCall(String from, String to, int duration, boolean process) {
		manager = PhoneCallManager.getInstance();
		this.from = from;
		this.to = to;
		this.callDuration = duration;
		this.processCall = process;
		this.futureTask = new FutureTask<Boolean>(this);
	}
	
	@Override
	public Boolean call() throws Exception {
		if(this.processCall) {
			System.out.println("Doing "+this.toString());
			Thread.sleep(callDuration);
			manager.phoneCallHappened(this.from, this.to);
		}
		else{
			System.out.println(this.toString()+" was canceled.");
		}
		return Boolean.TRUE;
	}
	
	public FutureTask<Boolean> getFutureTask() {
		return this.futureTask;
	}
	
	public String toString() {
		return String.format("call from: %s to: %s", this.from, this.to);
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
}

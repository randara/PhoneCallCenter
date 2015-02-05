package phonecallcenter;

import java.util.concurrent.Callable;

public class PhoneCall implements Callable<Boolean> {

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
	}
	
	@Override
	public Boolean call() throws Exception {
		if(this.processCall) {
			Thread.sleep(callDuration);
			manager.phoneCallHappened(this.from, this.to);
		}
		else{
			System.out.println(this.toString()+" was canceled.");
		}
		return Boolean.TRUE;
	}
	
	public String toString() {
		return "call from: "+this.from +" to: "+this.to;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
}

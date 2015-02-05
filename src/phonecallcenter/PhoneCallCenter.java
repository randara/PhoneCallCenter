package phonecallcenter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class PhoneCallCenter {
	
	public static void main(String[] args) {
		
		PhoneCallManager manager = PhoneCallManager.getInstance();
		
		ArrayList<PhoneCall> callList = new ArrayList<PhoneCall>();
		ArrayList<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
		
		callList.add(new PhoneCall("a", "b", 5000, true));
		callList.add(new PhoneCall("b", "a", 2000, true));
		callList.add(new PhoneCall("x", "y", 2000, true));
		callList.add(new PhoneCall("a", "y", 5000, false)); // call to be canceled
		
		for(PhoneCall call : callList) {
			taskList.add(new FutureTask<Boolean>(call));
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(FutureTask<Boolean> task : taskList) {
			executor.execute(task);
		}
		
		System.out.println("Waiting for calls...");
		
		boolean allCallsDone;
		
		while(true) {
			try {
				allCallsDone = true;
				
				for(FutureTask<Boolean> task : taskList) {
					if(!task.isDone()) {
						allCallsDone = false;
					}
				}
				if(allCallsDone) {
					System.out.println("All calls are Done");
					System.out.println("Checking with PhoneCallManager ...");
					
					for(PhoneCall call : callList) {
						System.out.println("Did " +call.toString()+ " happen? "+ manager.didPhoneCallHappen(call.getFrom(), call.getTo()));
					}
					executor.shutdown();
					return;
				}
				
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
	}

}

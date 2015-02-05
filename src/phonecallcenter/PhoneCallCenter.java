package phonecallcenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
 
public class PhoneCallCenter {
 
    public static void main(String[] args) {
    	
    	PhoneCallManager manager = PhoneCallManager.getInstance();
    	
    	PhoneCall call1 = new PhoneCall("a","b",5000);
    	PhoneCall call2 = new PhoneCall("b","a",5000);
    	PhoneCall call3 = new PhoneCall("x","y",5000);
    	PhoneCall call4 = new PhoneCall("a","y",5000);
 
        FutureTask<Boolean> futureTask1 = new FutureTask<Boolean>(call1);
        FutureTask<Boolean> futureTask2 = new FutureTask<Boolean>(call2);
        FutureTask<Boolean> futureTask3 = new FutureTask<Boolean>(call3);
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(futureTask1);
        executor.execute(futureTask2);
        executor.execute(futureTask3);
        
        System.out.println("Waiting for calls...");
        
        while(true) {
            try {
                if(futureTask1.isDone() && futureTask2.isDone() && futureTask3.isDone()) {
                    System.out.println("All calls are Done");
                    
                    System.out.println("Checking with PhoneCallManager ...");
                    System.out.println("Did " +call1.toString()+ " happen? "+ manager.didPhoneCallHappen("a", "b"));
                    System.out.println("Did " +call2.toString()+ " happen? "+ manager.didPhoneCallHappen("b", "a"));
                    System.out.println("Did " +call3.toString()+ " happen? "+ manager.didPhoneCallHappen("x", "y"));
                    System.out.println("Did " +call4.toString()+ " happen? "+ manager.didPhoneCallHappen("a", "y"));
                    
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

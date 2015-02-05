package phonecallcenter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
 
public class PhoneCallCenter {
 
    public static void main(String[] args) {
    	
    	PhoneCallManager manager = new PhoneCallManager();
    	
    	PhoneCall call1 = new PhoneCall("a","b",5000);
    	PhoneCall call2 = new PhoneCall("b","a",5000);
    	PhoneCall call3 = new PhoneCall("x","y",5000);
 
        FutureTask<String> futureTask1 = new FutureTask<String>(call1);
        FutureTask<String> futureTask2 = new FutureTask<String>(call2);
        FutureTask<String> futureTask3 = new FutureTask<String>(call3);
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(futureTask1);
        executor.execute(futureTask2);
        executor.execute(futureTask3);
        
        System.out.println("Waiting for calls...");
        
        while (true) {
            try {
                if(futureTask1.isDone() && futureTask2.isDone() && futureTask3.isDone()){
                    System.out.println("All calls are Done");
                    
                    System.out.println("Checking with PhoneCallManager ...");
                    System.out.println("Did " +call1.toString()+ " happen? "+ manager.didPhoneCallHappen("a", "b"));
                    System.out.println("Did " +call2.toString()+ " happen? "+ manager.didPhoneCallHappen("b", "a"));
                    System.out.println("Did " +call3.toString()+ " happen? "+ manager.didPhoneCallHappen("x", "y"));
                    
                    executor.shutdown();
                    return;
                }
                
                
				getCallResult(futureTask1);
				
				getCallResult(futureTask2);
				
				getCallResult(futureTask3);
               
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
         
    }
 
    private static void getCallResult( FutureTask<String> futureTask) throws InterruptedException, ExecutionException, TimeoutException{
    	 String s = futureTask.get(200L, TimeUnit.MILLISECONDS);
         
         if(s != null ){
             System.out.println(s);
         }
         
    }
    
}

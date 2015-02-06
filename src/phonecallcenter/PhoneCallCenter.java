package phonecallcenter;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneCallCenter {
	
	private static int MIN_CALLS_VALUE = 5;
	private static int MAX_CALLS_VALUE = 20;
	
	private static ArrayList<PhoneCall> callList = new ArrayList<PhoneCall>();
	
	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
	
	public static void main(String[] args) {
		
		PhoneCallManager manager = PhoneCallManager.getInstance();
		
		ArrayList<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
		
		prepareCallList();
		
		for(PhoneCall call : callList) {
			taskList.add(new FutureTask<Boolean>(call));
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(MIN_CALLS_VALUE);
		
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
					System.out.println("Checking with PhoneCallManager...");
					
					for(PhoneCall call : callList) {
						System.out.println(String.format("Did %s happen? %s", call.toString(), manager.didPhoneCallHappen(call.getFrom(), call.getTo())));
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
	
	private static void prepareCallList(){
		// Generate random calls
		int max = MAX_CALLS_VALUE + 1;
		int min = MIN_CALLS_VALUE;
		
		int callsToBeGenerated = (int)(Math.random()*(max-min))+min;
		
		for(int i = 0; i < callsToBeGenerated; i++) {
			
			PhoneNumber exampleNumber;
			PhoneNumber exampleNumber2;
			
			try {
				exampleNumber = phoneNumberUtil.getExampleNumber(getRandomCountryCode());
				exampleNumber2 = phoneNumberUtil.getExampleNumber(getRandomCountryCode());
				callList.add(new PhoneCall(getPhoneNumberAsString(exampleNumber), getPhoneNumberAsString(exampleNumber2), getRandomDuration(), true));
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
			
		}
		
	}
	
	private static String getRandomCountryCode() {
		String[] randomCountry = java.util.Locale.getISOCountries();
		return randomCountry[new Random().nextInt(randomCountry.length)];
	}
	
	private static String getPhoneNumberAsString(PhoneNumber number) {
		return String.format("+%d %d",number.getCountryCode(), number.getNationalNumber());
	}
	
	private static int getRandomDuration() {
		int max = 5000 + 1;
		int min = 2000;
		
		return (int)(Math.random()*(max-min))+min;
	}
}

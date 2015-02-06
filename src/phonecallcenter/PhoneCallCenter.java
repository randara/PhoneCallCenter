package phonecallcenter;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneCallCenter {
	
	private static int MIN_CALLS_VALUE = 5;
	private static int MAX_CALLS_VALUE = 20;
	
	private static int MIN_CALL_DURATION = 2000;
	private static int MAX_CALL_DURATION = 5000;
	
	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
	
	public static void main(String[] args) {
		
		PhoneCallManager manager = PhoneCallManager.getInstance();
		
		ArrayList<PhoneCall> callList = generateCallList();
		
		ExecutorService executor = Executors.newFixedThreadPool(MIN_CALLS_VALUE);
		
		for(PhoneCall call : callList) {
			executor.execute(call.getFutureTask());
		}
		
		System.out.println("Waiting for calls...");
		
		boolean allCallsDone;
		
		while(true) {
			try {
				allCallsDone = true;
				
				for(PhoneCall call : callList) {
					if(!call.getFutureTask().isDone()) {
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
	
	private static ArrayList<PhoneCall> generateCallList(){
		// Generate random calls
		int callsToBeGenerated = getRandomIntValue(MAX_CALLS_VALUE, MIN_CALLS_VALUE);
		
		ArrayList<PhoneCall> list = new ArrayList<PhoneCall>();
		
		for(int i = 0; i < callsToBeGenerated; i++) {
			
			PhoneNumber exampleNumber;
			PhoneNumber exampleNumber2;
			
			try {
				exampleNumber = phoneNumberUtil.getExampleNumber(getRandomCountryCode());
				exampleNumber2 = phoneNumberUtil.getExampleNumber(getRandomCountryCode());
				list.add(new PhoneCall(getPhoneNumberAsString(exampleNumber), getPhoneNumberAsString(exampleNumber2), getRandomCallDuration(), true));
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
			
		}
		
		return list;
		
	}
	
	private static String getRandomCountryCode() {
		String[] randomCountry = java.util.Locale.getISOCountries();
		return randomCountry[new Random().nextInt(randomCountry.length)];
	}
	
	private static String getPhoneNumberAsString(PhoneNumber number) {
		return String.format("+%d %d",number.getCountryCode(), number.getNationalNumber());
	}
	
	private static int getRandomCallDuration() {
		return getRandomIntValue(MAX_CALL_DURATION, MIN_CALL_DURATION);
	}
	
	private static int getRandomIntValue(int max, int min){
		return (int)(Math.random()*((max+1)-min))+min;
	}
}

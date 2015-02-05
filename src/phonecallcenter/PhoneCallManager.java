package phonecallcenter;

public class PhoneCallManager {

	static java.util.Vector<PhoneCallRecord> phoneCallRecordList;
	
		public PhoneCallManager(){
			PhoneCallManager.phoneCallRecordList = new java.util.Vector<PhoneCallRecord>();
		}
	
		/*
	    * Record the fact that a phone call happened between 2 numbers, this will
	    * be called by some other class that actually does the phone call
	    */
	    public void phoneCallHappened(final String from, final String to) {
	        
	    	if(isPhoneNumberValid(from) && isPhoneNumberValid(to)){
	    		PhoneCallManager.phoneCallRecordList.add(new PhoneCallRecord(from,to));
	    	}
	    	
	    }

	    /*
	    * Return whether a phone call has happened between the 2 numbers for the lifetime
	    * of this application
	    */
	    public boolean didPhoneCallHappen(final String from, final String to) {
	    	return isPhoneNumberValid(from) && isPhoneNumberValid(to) &&
	    			PhoneCallManager.phoneCallRecordList.contains(new PhoneCallRecord(from,to));
	    }
	
	    public static boolean isPhoneNumberValid(String number){
	    	// E.164 regex matcher
	    	return true;
	    }
	    
}

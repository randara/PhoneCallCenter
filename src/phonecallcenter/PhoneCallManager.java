package phonecallcenter;
// Singleton
public class PhoneCallManager {
	
	private static PhoneCallManager instance = new PhoneCallManager();
	
	// Vector is thread safe
	private static java.util.Vector<PhoneCallRecord> phoneCallRecordList;
	
	protected PhoneCallManager() {
		PhoneCallManager.phoneCallRecordList = new java.util.Vector<PhoneCallRecord>();
	}
	
	public static PhoneCallManager getInstance() {
		return instance;
	}
	
	/*
	 * Record the fact that a phone call happened between 2 numbers, this will
	 * be called by some other class that actually does the phone call
	 */
	public void phoneCallHappened(final String from, final String to) {
		// Manager does not validate if a number is valid
		PhoneCallManager.phoneCallRecordList.add(new PhoneCallRecord(from, to));
	}
	
	/*
	 * Return whether a phone call has happened between the 2 numbers for the lifetime
	 * of this application
	 */
	public boolean didPhoneCallHappen(final String from, final String to) {
		/*
		 * Return if the call has happened in the case that FROM call TO and not the other way.
		 * If we wanted to ignore origin and destination we could use:
		 * 
		 * return PhoneCallManager.phoneCallRecordList.contains(new PhoneCallRecord(from, to)) or
		 *  PhoneCallManager.phoneCallRecordList.contains(new PhoneCallRecord(to, from));
		 * 
		*/
		return PhoneCallManager.phoneCallRecordList.contains(new PhoneCallRecord(from, to));
	}
	
	public void clearPhoneCallRecordList() {
		phoneCallRecordList.clear();
	}
	
	public int countPhoneCallRecordList() {
		return phoneCallRecordList.size();
	}
}

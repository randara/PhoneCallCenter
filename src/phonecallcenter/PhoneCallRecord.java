package phonecallcenter;

public class PhoneCallRecord {

	final String from;
	final String to;
	
	public PhoneCallRecord(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean equals(Object obj) {
		boolean areEquals = false;
		
		if(obj instanceof PhoneCallRecord) {
			PhoneCallRecord record = (PhoneCallRecord) obj;
			
			if(this.from.equals(record.from) && this.to.equals(record.to)) {
				areEquals = true;
			}
		}
		
		return areEquals;
	}

}

package phonecallcenter;

public class PhoneCallRecord {

	final String from;
	final String to;
	
	public PhoneCallRecord(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof PhoneCallRecord)
		{
			PhoneCallRecord record = (PhoneCallRecord) obj;
			if(this.from.equals(record.from) && this.to.equals(record.to)){
				return true;
			}
			else{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}

}

package phonecallcentertest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import phonecallcenter.PhoneCallManager;


public class PhoneCallManagerTest {

	static PhoneCallManager manager;
	
	@Before
	public void setUp(){
		manager = new PhoneCallManager();
	}
	
	@Test
	public void noCalls() {
		Assert.assertFalse(manager.didPhoneCallHappen("from", "to"));
		Assert.assertFalse(manager.didPhoneCallHappen("to", "from"));
	}

	@Test
	public void FromToCall() {
		manager.phoneCallHappened("from", "to");
		Assert.assertTrue(manager.didPhoneCallHappen("from", "to"));
		Assert.assertFalse(manager.didPhoneCallHappen("to", "from"));
	}
	
	@Test
	public void ToFromCall() {
		manager.phoneCallHappened("to", "from");
		Assert.assertFalse(manager.didPhoneCallHappen("from", "to"));
		Assert.assertTrue(manager.didPhoneCallHappen("to", "from"));
	}
	
}

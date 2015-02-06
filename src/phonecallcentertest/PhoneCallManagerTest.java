package phonecallcentertest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import phonecallcenter.PhoneCall;
import phonecallcenter.PhoneCallManager;

public class PhoneCallManagerTest {

	private PhoneCallManager manager;
	private ExecutorService executor;
	
	@Before
	public void setUp() {
		manager = PhoneCallManager.getInstance();
		manager.clearPhoneCallRecordList();
		executor = Executors.newFixedThreadPool(1);
	}
	
	@After
	public void tearDown() {
		executor.shutdown();
	}
	
	@Test
	public void noCallsManagerRecordsTest() {
		// Manager does not validate if a number is valid
		Assert.assertFalse(manager.didPhoneCallHappen("from", "to"));
		Assert.assertFalse(manager.didPhoneCallHappen("to", "from"));
		Assert.assertEquals(0, manager.phoneCallRecordsCount());
	}
	
	@Test
	public void FromToCallManagerRecordsTest() {
		// Manager does not validate if a number is valid
		manager.phoneCallHappened("from", "to");
		Assert.assertTrue(manager.didPhoneCallHappen("from", "to"));
		Assert.assertFalse(manager.didPhoneCallHappen("to", "from"));
		Assert.assertEquals(1, manager.phoneCallRecordsCount());
	}
	
	@Test
	public void ToFromCalManagerRecordsTestl() {
		// Manager does not validate if a number is valid
		manager.phoneCallHappened("to", "from");
		Assert.assertFalse(manager.didPhoneCallHappen("from", "to"));
		Assert.assertTrue(manager.didPhoneCallHappen("to", "from"));
		Assert.assertEquals(1, manager.phoneCallRecordsCount());
	}
	
	@Test
	public void ToFromAndFromToCallManagerRecordsTest() {
		// Manager does not validate if a number is valid
		manager.phoneCallHappened("to", "from");
		manager.phoneCallHappened("from", "to");
		Assert.assertTrue(manager.didPhoneCallHappen("from", "to"));
		Assert.assertTrue(manager.didPhoneCallHappen("to", "from"));
		Assert.assertEquals(2, manager.phoneCallRecordsCount());
	}
	
	@Test
	public void phoneCallProcessedAndManagerRecordsTest() {
		PhoneCall call = new PhoneCall("from", "to", 2000, true);
		executor.execute(call.getFutureTask());
		while(true) {
			if(call.getFutureTask().isDone()) {
				Assert.assertTrue(manager.didPhoneCallHappen("from", "to"));
				Assert.assertEquals(1, manager.phoneCallRecordsCount());
				return;
			}
		}
		
	}
	
	@Test
	public void phoneCallCanceledAndManagerRecordsTest() {
		PhoneCall call = new PhoneCall("from", "to", 2000, false);
		executor.execute(call.getFutureTask());
		while(true) {
			if(call.getFutureTask().isDone()) {
				Assert.assertFalse(manager.didPhoneCallHappen("from", "to"));
				Assert.assertEquals(0, manager.phoneCallRecordsCount());
				return;
			}
		}
		
	}
}

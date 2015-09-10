package com.discover.testhydra;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.discover.hydra.ManagedZPK;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.Transaction;
import com.kabira.platform.highavailability.Partition;
import com.kabira.platform.highavailability.PartitionManager;
import com.kabira.test.management.CommandFailed;
import com.kabira.test.management.Client.Configuration;

public class HATest extends Base {

	@Test
	public void loadHAConfig() throws CommandFailed {
		String testName = HATest.class.getName() + ": loadHAConfig";

		startTest(testName);

		Configuration config = loadActivate("haservice.kcs");

		Coordinator.waitForState("CONFIG_LOADED", testName);

		// display partition info
		//
		/*
		Client client = new Client("guest", "guest");
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.clear();
		String[] response = client.runCommand("display", "partition", parameters);
		for (String s : response) {
			System.out.println(s);
		}
		 */

		// create an object
		//
		new Transaction() {	
			@Override
			protected void run() throws Rollback {
				for (int i=0; i<3; i++) {
					ManagedZPK.getOrCreateZPK(Base.thisNode, ""+i, new Date(System.currentTimeMillis()-10000*i), "secret");
				}
			}
		}.execute();

		Coordinator.waitForState("OBJECTS_CREATED", testName);

		// display
		//
		new Transaction() {	
			@Override
			protected void run() throws Rollback {
				for (ManagedZPK zpk : ManagedObject.extent(ManagedZPK.class)) {
					Partition p = PartitionManager.getObjectPartition(zpk);
					System.out.println("Object:"+zpk+" partition:"+p.getName()+" active:"+p.getActiveNode()+" replicas:"+Arrays.toString(p.getReplicaNodes()));
				}
			}
		}.execute();

		Coordinator.waitForState("OBJECTS_DISPLAYED", testName);

		// expire
		//
		for (int i=0; i<3; i++) {
			final int j=i;
			new Transaction() {	
				@Override
				protected void run() throws Rollback {
					ManagedZPK.getOrCreateZPK(Base.thisNode, "0", new Date(System.currentTimeMillis()-100000*j), "secret");
				}
			}.execute();
		}

		Coordinator.waitForState("OBJECTS_QUERIED", testName);

		// delete
		//
		new Transaction() {	
			@Override
			protected void run() throws Rollback {
				for (ManagedZPK zpk : ManagedObject.extent(ManagedZPK.class)) {
					Partition p = PartitionManager.getObjectPartition(zpk);
					if (p.getActiveNode().equals(Base.thisNode)) {
						System.out.println("Deleting:"+zpk);
						ManagedObject.delete(zpk);
					}
				}
			}
		}.execute();
		
		Coordinator.waitForState("OBJECTS_DELETED", testName);

		config.deactivate();
		config.remove();

		Coordinator.waitForState("CONFIG_REMOVED", testName);

		endTest(testName);
	}
}

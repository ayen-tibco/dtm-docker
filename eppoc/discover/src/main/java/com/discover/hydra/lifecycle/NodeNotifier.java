package com.discover.hydra.lifecycle;

import java.util.HashSet;

import com.discover.hydra.ManagedZPK;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.annotation.Asynchronous;
import com.kabira.platform.highavailability.Partition;
import com.kabira.platform.highavailability.PartitionManager;
import com.kabira.platform.property.Status;


public class NodeNotifier extends com.kabira.platform.highavailability.NodeNotifier {

	private final static String nodeName = System.getProperty(Status.NODE_NAME);
	private final static HashSet<String> remoteNodes = new HashSet<String>();
	
	@Override
	protected void active(String node) {
		System.out.println("NodeNotifier: Node "+node+" is active ");	

		// Keep track of nodes known about by this node
		//
		remoteNodes.add(node);
		
		// simple check to see if we now have all nodes
		//
		if (remoteNodes.size() == expectedRemoteNodes()) {
			// FIX THIS - resume key exchange
			//
		}
		
		System.out.println("NodeNotifier: Node "+node+" is active end");	

	}

	@Override
	protected void unavailable(String node) {
		System.out.println("NodeNotifier: Node "+node+" is unavailable");
		
		// Keep track of nodes known about by this node
		//
		remoteNodes.remove(node);
		
		// at this point, a partition could be active now on this node
		//
		this.checkMigration();
		
		System.out.println("NodeNotifier: Node "+node+" is unavailable end");

	}
	
	/**
	 * Check if a partition has been migrated
	 * 
	 * This is run in a separate transaction to avoid lock contention with any locks
	 * held by the node notifier
	 */
	@Asynchronous
	private void checkMigration() {		

		// if a partition is now active on this node, do an extent query
		// which will trigger the secondary store to sync to disk
		//
		for (Partition p : PartitionManager.getPartitions()) {
			if (p.getActiveNode().equals(nodeName)) {
				
				// This takes a read lock on all ZPK's - this is not expected to be a problem since ZPKs
				// are not updated.
				//
				for (ManagedZPK zpk : ManagedObject.extent(ManagedZPK.class)) {

					// FIX THIS - this assumes one partition - if multiple partitions then zpk need to be checked that
					// its active on this local node
					//
				}
			}
		}
	}
	
	/**
	 * Find the expected number of remote nodes
	 * 
	 * @return
	 */
	private int expectedRemoteNodes() {
		int expected = 0;
		
		// find expected number of nodes 
		//
		for (com.tibco.haservice.config.Partition p : ManagedObject.extent(com.tibco.haservice.config.Partition.class)) {
			if (p.replicas.length > expected) {
				expected = p.replicas.length;
			}
		}
		
		return expected;
	}
}

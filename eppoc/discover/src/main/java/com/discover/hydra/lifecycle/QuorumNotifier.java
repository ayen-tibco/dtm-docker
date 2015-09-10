package com.discover.hydra.lifecycle;

import com.kabira.platform.annotation.KeyField;

public class QuorumNotifier extends com.tibco.haservice.notifiers.QuorumNotifier {

	protected QuorumNotifier(@KeyField(fieldName = "m_nodeGroup") String nodeGroup) {
		super(nodeGroup);
	}

	@Override
	public void postDeactivate() {
	}

	@Override
	public boolean preDeactivate(String reason) {
		System.out.println("QuorumNotifier: preDeactivate "+reason);

		// FIX THIS - add in disable of DKE

		// Return false to avoid disabling the partitions
		//
		return false;
	}
}

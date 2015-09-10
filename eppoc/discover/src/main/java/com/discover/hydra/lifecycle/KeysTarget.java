package com.discover.hydra.lifecycle;

import com.kabira.platform.management.Command;
import com.kabira.platform.management.ManagementTarget;
import com.kabira.platform.management.Target;

@ManagementTarget(name = "keys", description = "Keys administration")
public class KeysTarget extends Target {
	
	@Command(description = "Re-load keys from disk")
	public void load() {
		
		// FIX THIS - Re-load the keys from disk by executing an extent query
		//
		commandComplete();
	}
}

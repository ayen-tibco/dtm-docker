package com.discover.hydra.lifecycle;

import com.kabira.platform.management.Command;
import com.kabira.platform.management.ManagementTarget;
import com.kabira.platform.management.Target;

@ManagementTarget(name = "keyexchange", description = "Key exchange administration")
public class KeyExchangeTarget extends Target {
	
	@Command(description = "Stop key exchange")
	public void stop() {
		
		// FIX THIS - stop the key exchange on this node
		//
		commandComplete();
	}

	@Command(description = "Start key exchange")
	public void start() {
		
		// FIX THIS - start the key exchange on this node
		//
		commandComplete();
	}
	
	@Command(description = "Display key exchange")
	public void display() {
		
		// FIX THIS - Display the key exchange status on this node
		//
		commandComplete();
	}
}

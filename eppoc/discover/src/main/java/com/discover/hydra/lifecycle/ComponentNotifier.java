package com.discover.hydra.lifecycle;

import com.discover.hydra.StoreZPK;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.component.Notifier;
import com.kabira.platform.management.Target;

public class ComponentNotifier extends Notifier {

	@Override
	protected void preConfigurationInitialize() {
		
		// On startup, create node notifier
		//
		new NodeNotifier();
		
		// create store
		//
		new StoreZPK();
		
		// Register admin target
		//
		Target.register(KeyExchangeTarget.class);
		Target.register(KeysTarget.class);

	}

	@Override
	protected void postConfigurationTerminate() {
		
		// Tidy-up
		//
		for (NodeNotifier notifier : ManagedObject.extent(NodeNotifier.class)) {
			ManagedObject.delete(notifier);
		}	
		
		for (StoreZPK store : ManagedObject.extent(StoreZPK.class)) {
			ManagedObject.delete(store);
		}
		
		Target.unregister("keyexchange");
		Target.unregister("keys");
	}
	
}

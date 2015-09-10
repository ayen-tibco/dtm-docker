package com.discover.hydra;

import com.kabira.platform.DeleteTrigger;
import com.kabira.platform.LockMode;
import com.kabira.platform.ManagedClassError;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.annotation.Managed;
import com.kabira.store.Extent;
import com.kabira.store.Record;

@Managed
public class StoreZPK implements DeleteTrigger {

	private RecordZPK recordZPK;
	private ExtentZPK extentZPK;

	public StoreZPK() {
		// Create the secondary store objects that we need
		//
		this.recordZPK = new RecordZPK();
		this.extentZPK = new ExtentZPK();
	}

	@Override
	public void uponDelete() {
		// tidy-up
		//
		if (this.recordZPK != null) {
			ManagedObject.delete(this.recordZPK);
		}
		if (this.extentZPK != null) {
			ManagedObject.delete(this.extentZPK);
		}
	}

	public class RecordZPK extends Record<ManagedZPK> {

		public RecordZPK() {
			super(ManagedZPK.class);
		}

		protected RecordZPK(Class<ManagedZPK> klass) throws ManagedClassError {
			super(klass);
		}


		@Override
		public void deleted(ManagedZPK zpk) {
			System.out.println("StoreZPK: Deleted " + zpk);

			// FIX THIS - here the external file can be removed 
			//
		}

		@Override
		public void modified(ManagedZPK zpk) {
			System.out.println("StoreZPK: Modified " + zpk);

			// FIX THIS - expect this to be unused since keys arn't modified
			//
		}

		@Override
		public void created(ManagedZPK zpk) {
			System.out.println("StoreZPK: Created " + zpk);

			// FIX THIS - here the zpk object can be written to an external file
			//
			// There is one key per file, so the filename is based on clientNodeId
			//
                        // If all indexes are required to be written to disk, then the filename
                        // should also contain the index.
                        // 
		}
	}
	
	
	public class ExtentZPK extends Extent<ManagedZPK> {

		public ExtentZPK() {
			super(ManagedZPK.class);
		}

		protected ExtentZPK(Class<ManagedZPK> klass) throws ManagedClassError {
			super(klass);
		}

		@Override
		public void extent(Class<? extends ManagedZPK> zpk, LockMode lockModes) {
			System.out.println("StoreZPK: Queried");

			// FIX THIS - here the zpk object can be synced to disk.  So :-
			//
			// 1) if object in memory is not on disk, export
			// 2) if a file on disk is not in memory, import
			//
			// The administration command, rehydrate, will perform an extent query which
			// will call this operation.  
			//
			// On partition failover, the partition notifier will also perform an extent
			// query
			//
		}
	}

}

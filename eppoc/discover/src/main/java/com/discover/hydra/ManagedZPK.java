package com.discover.hydra;

import java.util.Date;

import com.kabira.platform.KeyFieldValueList;
import com.kabira.platform.KeyManager;
import com.kabira.platform.KeyOrderedBy;
import com.kabira.platform.KeyQuery;
import com.kabira.platform.LockMode;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.annotation.Asynchronous;
import com.kabira.platform.annotation.KeyField;
import com.kabira.platform.annotation.KeyList;
import com.kabira.platform.annotation.Key;
import com.kabira.platform.annotation.Managed;

@Managed
@KeyList (keys = {
		@Key(name = "ByClient", fields = { "clientNodeId" }, unique = false),
		@Key(name = "ByDate", fields = { "clientNodeId", "index", "activeDate" }, unique = false, ordered=true),
		@Key(name = "ByDistinct", fields = { "clientNodeId", "index", "activeDate" }, unique = true),	
})
public class ManagedZPK  {

	private final static int MAX_KEYS = 3;

	public final String clientNodeId;
	public final String index;
	public String keyValue;
	public final Date activeDate;

	public ManagedZPK(@KeyField(fieldName = "clientNodeId") String clientNodeId, 
			@KeyField(fieldName = "index") String index, 
			@KeyField(fieldName = "activeDate") Date activeDate) {
		this.clientNodeId = clientNodeId;
		this.index = index;
		this.activeDate = activeDate;	
	}

	/**
	 * For debug purposes only - note this will be executed on the currently active node
	 */
	@Override
	public String toString() {
		return "ManagedZPK [clientNodeId=" + clientNodeId + ", index=" + index
				+ ", keyValue=" + keyValue + ", activeDate=" + activeDate + "]";
	}

	/**
	 * Get or create an unique ZPK instance.  This will be called when a new key is created by
	 * the key exchange process and also when the secondary store reads keys from disk (caused
	 * by the administration command executing an extent query ).  In both cases we need to
	 * make sure we don't create two objects representing the same ZPK - hence the use of a 
	 * unique key
	 * 
	 * @param clientNodeId	Client Node ID
	 * @param index			Index
	 * @param activeDate	active date
	 * @param keyValue		contents of the key
	 * @return				Instance of ManagedZPK
	 */
	public static ManagedZPK getOrCreateZPK(String clientNodeId, String index, Date activeDate, String keyValue) {
		KeyManager<ManagedZPK> km = new KeyManager<ManagedZPK>();
		KeyQuery<ManagedZPK> keyQuery = km.createKeyQuery(ManagedZPK.class, "ByDistinct");
		KeyFieldValueList keyList = new KeyFieldValueList();
		keyList.add("clientNodeId", clientNodeId);
		keyList.add("index", index);
		keyList.add("activeDate", activeDate);

		KeyFieldValueList additionalFields = new KeyFieldValueList();
		additionalFields.add("keyValue", keyValue);

		keyQuery.defineQuery(keyList);

		ManagedZPK zpk = keyQuery.getOrCreateSingleResult(LockMode.READLOCK, additionalFields);

		// check if we should expire this key
		//
		km = new KeyManager<ManagedZPK>();
		keyQuery = km.createKeyQuery(ManagedZPK.class, "ByDate");
		keyList = new KeyFieldValueList();
		keyList.add("clientNodeId", clientNodeId);
		keyList.add("index", index);
		keyQuery.defineQuery(keyList);

		if (zpk != null && keyQuery.cardinality() > MAX_KEYS) {
			zpk.expire();
		}

		return zpk;
	}

	/**
	 * Get the latest instance of the ZPK.  This will be called during normal processing.
	 * 
	 * Note that we believe this code already exists in the current implementation
	 * 
	 * @param clientNodeId	Client Node ID
	 * @param index			Index
	 * @return				Instance of ManagedZPK, or null if not found
	 */
	public static ManagedZPK getLatestZPK(String clientNodeId, String index) {
		KeyManager<ManagedZPK> km = new KeyManager<ManagedZPK>();
		KeyQuery<ManagedZPK> keyQuery = km.createKeyQuery(ManagedZPK.class, "ByDate");
		KeyFieldValueList keyList = new KeyFieldValueList();
		keyList.add("clientNodeId", clientNodeId);
		keyList.add("index", index);
		keyQuery.defineQuery(keyList);

		// date is not included in the query, so the results are sorted by date
		//

		// Specifically this is a local ( not cluster ) query
		//

		return keyQuery.getMaximumResult(LockMode.READLOCK);
	}

	/**
	 * Called on the latest instance of the ZPK to remove older keys
	 * 
	 * This is executed in a separate transaction on the currently active node
	 */
	@Asynchronous
	public void expire() {
		KeyManager<ManagedZPK> km = new KeyManager<ManagedZPK>();
		KeyQuery<ManagedZPK> keyQuery = km.createKeyQuery(ManagedZPK.class, "ByDate");
		KeyFieldValueList keyList = new KeyFieldValueList();
		keyList.add("clientNodeId", this.clientNodeId);
		keyList.add("index", this.index);

		keyQuery.defineQuery(keyList);

		int i=1;
		for (ManagedZPK zpk : keyQuery.getResults(KeyOrderedBy.DESCENDING, LockMode.NOLOCK) ) {
			if (i++ > MAX_KEYS) {
				System.out.println("expire: deleting "+zpk);
				ManagedObject.delete(zpk);
			}
		}

	}
}

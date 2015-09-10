package com.discover.testhydra;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.kabira.platform.KeyFieldValueList;
import com.kabira.platform.KeyManager;
import com.kabira.platform.KeyQuery;
import com.kabira.platform.LockMode;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.QueryScope;
import com.kabira.platform.ResourceUnavailableException;
import com.kabira.platform.Transaction;
import com.kabira.platform.annotation.ByReference;
import com.kabira.platform.annotation.Key;
import com.kabira.platform.annotation.KeyField;
import com.kabira.platform.annotation.KeyList;
import com.kabira.platform.annotation.Managed;
import com.kabira.platform.highavailability.PartitionManager;
import com.kabira.platform.property.Status;

public final class Coordinator {

	private final static String nodeName = System.getProperty(Status.NODE_NAME);
	private final static String[] nodeList = System.getProperty(
			"com.kabira.fluency.nodelist").split(",");

	private static boolean isInitialized = false;

	// @Key(fields = { "nodeName" }, name = "ByNodeName", unique = false)

	@Managed
	@KeyList(keys = {
			@Key(fields = { "nodeName" }, name = "ByNodeName", unique = false),
			@Key(fields = { "nodeName", "source" }, name = "ByNodeNameSource", unique = true), })
	private static class Worker {
		private final String nodeName;
		private final String source;

		@ByReference
		private final AtomicReference<String> state = new AtomicReference<String>();

		private String getState() {
			return state.get();
		}

		private String getSource() {
			return source;
		}

		private void setState(String state) {
			this.state.set(state);
		}

		private Worker(@KeyField(fieldName = "nodeName") String nodeName,
				@KeyField(fieldName = "source") String source) {
			this.nodeName = nodeName;
			this.source = source;
		}

		private static Worker getOrCreate(String nodeName, String source,
				LockMode lockMode) {
			KeyManager<Worker> km = new KeyManager<Worker>();
			KeyQuery<Worker> keyQuery = km.createKeyQuery(Worker.class,
					"ByNodeNameSource");
			KeyFieldValueList keyList = new KeyFieldValueList();
			keyList.add("nodeName", nodeName);
			keyList.add("source", source);
			keyQuery.defineQuery(keyList);

			return keyQuery.getOrCreateSingleResult(lockMode, null);
		}

		private static Worker getByNodeNameSource(String nodeName,
				String source, QueryScope scope, LockMode lockMode) {
			KeyManager<Worker> km = new KeyManager<Worker>();
			KeyQuery<Worker> keyQuery = km.createKeyQuery(Worker.class,
					"ByNodeNameSource");
			KeyFieldValueList keyList = new KeyFieldValueList();
			keyList.add("nodeName", nodeName);
			keyList.add("source", source);
			keyQuery.defineQuery(keyList);
			keyQuery.setQueryScope(scope);

			return keyQuery.getSingleResult(lockMode);
		}

		private static Iterable<Worker> getByNodeName(String nodeName,
				QueryScope scope, LockMode lockMode) {
			KeyManager<Worker> km = new KeyManager<Worker>();
			KeyQuery<Worker> keyQuery = km.createKeyQuery(Worker.class,
					"ByNodeName");
			KeyFieldValueList keyList = new KeyFieldValueList();
			keyList.add("nodeName", nodeName);
			keyQuery.defineQuery(keyList);
			keyQuery.setQueryScope(scope);

			return keyQuery.getResults(lockMode);
		}
	}

	/**
	 * Initialise co-ordinator
	 */
	public static void initialize() {
		if (isInitialized) {
			return;
		}

		new Transaction("Initialize Monitor") {
			@Override
			protected void run() throws Rollback {
				for (Worker w : Worker.getByNodeName(nodeName,
						QueryScope.QUERY_LOCAL, LockMode.WRITELOCK)) {
					ManagedObject.delete(w);
				}
			}
		}.execute();

		isInitialized = true;
	}

	/**
	 * Wait for state - give up after 300 seconds
	 * 
	 * @param state State to wait for
	 * @param source Source
	 * @return fail if timeout
	 */
	public static boolean waitForState(final String state, final String source) {
		return waitForState(state, source, 300);
	}

	/**
	 * Wait for state - give up after waitSeconds seconds
	 * 
	 * @param state State to wait for
	 * @param source Source
	 * @param waitSeconds seconds to wait
	 * @return fail if timeout
	 */
	public static boolean waitForState(final String state, final String source,
			final int waitSeconds) {

		new Transaction("Set State") {
			@Override
			protected void run() throws Rollback {
				Worker worker = Worker.getOrCreate(nodeName, source,
						LockMode.READLOCK);
				worker.setState(state);
			}
		}.execute();

		boolean allfound;
		int maxTries = waitSeconds;
		int retrySeconds = 1;

		do {
			allfound = true;
			for (final String node : nodeList) {
				allfound = new Transaction("Wait For State") {

					private boolean isMatch = false;

					@Override
					protected void run() throws Rollback {
						Worker worker = Worker.getByNodeNameSource(node,
								source, QueryScope.QUERY_CLUSTER,
								LockMode.READLOCK);
						if (worker != null && worker.getState().equals(state)
								&& worker.getSource().equals(source)) {
							isMatch = true;
						}
					}

					private boolean go() {
						execute();
						return isMatch;
					}
				}.go();

				if (!allfound) {
					break;
				}
			}

			try {
				TimeUnit.SECONDS.sleep(retrySeconds);
			} catch (InterruptedException e) {
			}

			maxTries--;

		} while (allfound == false && maxTries > 0);

		if (!allfound) {
			System.out.println("WaitForState FAILED, source=" + source + ", state=" + state);
			return false;

		} else {
			try {
				// this needs to be set to a higher value than the re-try delay,
				// in order to ensure we don't overwrite the state and label
				// attributes on the Worker
				//
				TimeUnit.SECONDS.sleep(retrySeconds * 5);
			} catch (InterruptedException e) {
			}

			System.out.println("WaitForState OK, source=" + source + ", state=" + state);
		}
		return true;
	}

	public static void waitForNodes() {
		if (nodeList == null || nodeList.length == 0) {
			throw new RuntimeException(
					"Cannot wait for nodes when node list is empty!");
		}

		new Transaction("Wait for Nodes") {

			@Override
			protected void run() throws Rollback {
				for (String node : nodeList) {
					if (!node.equals(nodeName)) {
						System.out.print(nodeName + " is waiting for node "
								+ node + "...");
						boolean waiting=true;
						while (waiting) {
							try {
								PartitionManager.waitForNode(node);
								waiting=false;
							} catch (ResourceUnavailableException e) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
								}
							}
						}
						System.out.print("done \n");
					}
				}
			}

		}.execute();
	}

	public static void staggeredDelay(int incrementalSeconds) {
		for (int i = 0; i < nodeList.length; i++) {
			if (nodeName.equals(nodeList[i])) {
				try {
					TimeUnit.SECONDS.sleep(i * incrementalSeconds);
				} catch (InterruptedException e) {
				}
				break;
			}
		}
	}
}

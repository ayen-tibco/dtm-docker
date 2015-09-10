
package com.discover.testhydra;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;

import com.kabira.platform.ManagedClassError;
import com.kabira.platform.ManagedObject;
import com.kabira.platform.Transaction;


/**
 * Tools to detect manage object leaks
 *
 */
public class Leak {

	private final static Logger Logging = Logger.getLogger(Leak.class.getName()); 

	private static final HashSet<String> ignoreList = new HashSet<String>();
	static {
		ignoreList.add("com.tibco.xp.runtime.Concept"); // base concept
	}
	
	/**
	 * Get a list of managed objects and their cardinalities
	 * 
	 * @return hashmap of class name and cardinality
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Integer> getCardinalities() {
	
		return new Transaction() {
			HashMap<String, Integer> m_classList = new HashMap<String, Integer>();

			@Override
			protected void run() throws Rollback {
				try {
					Field f = ClassLoader.class.getDeclaredField("classes");
					f.setAccessible(true);

					Vector<Class> classes =  (Vector<Class>) f.get(ManagedObject.class.getClassLoader());
					for (int i=0; i<classes.size(); i++) {
						Class klass = classes.get(i);
						String name = klass.getCanonicalName();
						if (!ignoreList.contains(name) && ManagedObject.isManagedClass(klass)) {
							try {
								m_classList.put(klass.getCanonicalName(), ManagedObject.cardinality(klass));
							} catch (ManagedClassError e) {
								// ignore
							}
						}
					}
				} catch (NoSuchFieldException e) {
					Logging.warning(e.getMessage());
				} catch (IllegalAccessException e) {
					Logging.warning(e.getMessage());			
				}	
			}

			Map<String, Integer> go() {
				execute();
				return new TreeMap<String, Integer>(this.m_classList);
			}
		}.go();
	}

	/**
	 * Compare start list of manage objects and end list of managed objects to find any leaks
	 * 
	 * @param startList cardinality list at start of test case
	 * @param endList cardinality list at end of test case
	 * @return true if leak found
	 */
	public static boolean findLeaks(Map<String, Integer> startList, Map<String, Integer> endList) {
		boolean leakFound = false;

		Iterator<String> it = endList.keySet().iterator();
		while (it.hasNext()) {
			String className = it.next();
			Integer endCardinality = endList.get(className);
			Integer startCardinality = startList.get(className);
			if (startCardinality == null && endCardinality > 0) {
				Logging.warning("UNITTEST: Managed object \""+className+"\" leaked "+endCardinality+" instances");
				leakFound = true;
			} else if (startCardinality != null && endCardinality > startCardinality) {
				Logging.warning("UNITTEST: Managed object \""+className+"\" leaked "+(endCardinality-startCardinality)+" instances");	
				leakFound = true;
			}
		}

		return leakFound;
	}
}

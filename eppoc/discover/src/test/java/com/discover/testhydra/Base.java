
package com.discover.testhydra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;

import com.kabira.test.management.Client;
import com.kabira.test.management.Client.Configuration;
import com.kabira.test.management.CommandFailed;

public class Base {

	final static Logger Logging = Logger.getLogger(Base.class.getName()); 
	
    static String thisNode = System
            .getProperty("com.kabira.platform.node.name");

    // leak detection
    //
    private Map<String, Integer> startCardinalities;

    /**
     * Wait for startup to complete
     */
    @BeforeClass
    public static void before() {
    	Logging.info("UNITTEST: Checking for BE-X runtime is started");

        // needed only if running outside of the deploy tool
        //
        // Lifecycle.waitForStartup();

        Coordinator.waitForNodes();
        Coordinator.initialize();

        Logging.info("UNITTEST: BE-X is started");
    }

    /**
     * Find an unused TCP port
     * 
     * @return port number
     * @throws IOException
     *             IOException
     */
    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }

    /**
     * Load and active a kcs configuration
     * 
     * @param filename
     *            filename to load
     * @return configuration
     * @throws CommandFailed Command failed
     */
    public static Configuration loadActivate(String filename)
            throws CommandFailed {
        Client client = new Client("guest", "guest");
        URL url = client.getClass().getClassLoader().getResource(filename);
        assert (url != null);
        Configuration config = client.new Configuration(url);
        config.load();
        config.activate();
        return config;
    }

    /**
     * Load and active a kcs configuration with substitutions
     * 
     * @param filename
     *            filename to load
     * @param subs
     *            substitutions to make
     * @return configuration
     * @throws CommandFailed Command failed
     * @throws IOException I/O Exception
     */
    public static Configuration loadActivateSubs(String filename,
            HashMap<String, String> subs) throws CommandFailed, IOException {
        Client client = new Client("guest", "guest");

        // temp file
        //
        File temp = File.createTempFile("tmp", ".kcs");
        BufferedWriter out = new BufferedWriter(new FileWriter(temp));
        BufferedReader in = null;

        // First try to load via CLASSPATH
        //
        URL inUrl = client.getClass().getClassLoader().getResource(filename);
        if (inUrl != null) {
            // open URL input
            //
            URLConnection inConnection = inUrl.openConnection();
            in = new BufferedReader(new InputStreamReader(inConnection.getInputStream()));
        } else {
            // open file
            //
            in = new BufferedReader(new FileReader(filename));
        }

        // copy and substitute
        //
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            Iterator<String> it = subs.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                inputLine = inputLine.replaceAll("\\$\\{" + key + "\\}",
                        subs.get(key));
            }
            out.write(inputLine + "\n");
        }

        // close
        //
        out.close();
        in.close();

        // load new file
        //
        Configuration config = client.new Configuration(new URL("file:"
                + temp.getPath()));
        config.load();
        config.activate();

        temp.delete();

        return config;
    }

    public boolean startTest(final String testName) {

        // check we have all finished
        //
        assertTrue(Coordinator.waitForState("TEST_STARTED", testName, 200));

        // record instances
        //
        this.startCardinalities = Leak.getCardinalities();

        if (Base.thisNode.equals("SSB1SWITCH")) {

            Logging.info("UNITTEST: ---------------------------------------------------");
            Logging.info("UNITTEST: Start test " + testName);
            Logging.info("UNITTEST: ---------------------------------------------------");

            return true;
        } else {
            return false;
        }
    }

    public void endTest(final String testName) {

        // check we have all finished
        //
    	assertTrue(Coordinator.waitForState("TEST_FINISHED", testName, 100));

        Leak.findLeaks(startCardinalities, Leak.getCardinalities());

 
        if (Base.thisNode.equals("SSB1SWITCH")) {
            Logging.info("UNITTEST: ---------------------------------------------------");
            Logging.info("UNITTEST: End test " + testName);
            Logging.info("UNITTEST: ---------------------------------------------------");
        }
    }
}

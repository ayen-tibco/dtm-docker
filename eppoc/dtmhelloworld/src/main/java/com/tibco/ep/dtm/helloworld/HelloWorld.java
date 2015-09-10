package com.tibco.ep.dtm.helloworld;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.ep.dtm.management.DtmCommand;
import com.tibco.ep.dtm.management.DtmContext;
import com.tibco.ep.dtm.management.DtmDeployFragmentCommand;
import com.tibco.ep.dtm.management.DtmDeployFragmentCommand.FragmentType;
import com.tibco.ep.dtm.management.DtmInstallNodeCommand;
import com.tibco.ep.dtm.management.DtmNode;

/**
 * Hello DTM!
 */
public class HelloWorld 
{
    /**
     * Main entry point for hello world example
     * @param args  Name value pairs specified as &lt;name&gt;=&lt;value&gt;.
     * <p>
     * Two arguments are required:
     * <ul>
     * <li>fragment=&lt;path to fragment&gt; - Application fragment to deploy</li>
     * <li>configuration=&lt;path to fragment configuration&gt; - Application fragment configuration</li>
     * </ul>
     */
    public static void main(final String [ ] args)
    {
    	//
    	//	Read environment
    	//
        assert System.getenv("SW_HOME") != null : "SW_HOME not set";
        assert System.getenv("STREAMBASE_HOME") != null : "STREAMBASE_HOME not set";

    	Path dtmPath = Paths.get(System.getenv("SW_HOME"));
    	Path sbPath = Paths.get(System.getenv("STREAMBASE_HOME"));
         
        System.out.println("INFO: SW_HOME is " + dtmPath);
        System.out.println("INFO: STREAMBASE_HOME is " + sbPath);
        
        parseArguments(args);
        
        //
        //	Initialization
        //
        DtmContext  context = new DtmContext(dtmPath, sbPath);
        
        //
        //	Enable command tracing
        //
        context.enableTracing();
        
        //
        //	Set resource paths to application fragment directory and java-bin
        //	so include files and Java classes are found when deploying
        //
        Path base = m_fragment.getParent();
        context.addResourceSearchPath(base);
        context.addResourceSearchPath(Paths.get(base.toString(), "java-bin"));
       
        //
        //	Install a node
        //
        DtmNode node = new DtmNode(getNodeName(), context);
        DtmInstallNodeCommand install = new DtmInstallNodeCommand(node);        
        install.execute(null, new Monitor("install", "node"));
        int rc = install.waitForCompletion();
        assert rc == 0 : rc;
        
        //
        //	Start a node
        //
        DtmCommand command = new DtmCommand("start", "node", node);
        command.execute(null, new Monitor("start", "node"));
        rc = command.waitForCompletion();
        assert rc == 0 : rc;
        
        //
        //	Deploy an application fragment on the node
        //
        deployApplicationFragment(dtmPath, node);
 
        //
        //	Stop a node
        //
        command = new DtmCommand("stop", "node", node);
        command.execute(null, new Monitor("stop", "node"));
        rc = command.waitForCompletion();
        assert rc == 0 : rc;
        
        //
        //	Remove a node
        //
        command = new DtmCommand("remove", "node", node);
        command.execute(null, new Monitor("remove", "node"));
        rc = command.waitForCompletion();
        assert rc == 0 : rc;
        
        System.out.println("INFO: Done!");
    }
    
    //
    //	Validate user arguments
    //
    private static void parseArguments(final String [ ] args)
    {
    	if (args.length != 2)
    	{
    		usage("Incorrect number of arguments (" + args.length + ")");
    	}
    	
    	for (String a : args)
    	{
    		String [ ] argument = a.split("=");
    		
    		if (argument.length != 2)
    		{
    			usage("Malformed argument (" + a + ")");
    		}
    		
    		if (argument[0].equals("fragment") == true)
    		{
    			m_fragment = Paths.get(argument[1]);
    			System.out.println("INFO: " + a);
    			continue;
    		}
    		
       		if (argument[0].equals("configuration") == true)
    		{
    			m_configuration = Paths.get(argument[1]);
    			System.out.println("INFO: " + a);
    			continue;
    		}
       		
			usage("Unknown argument (" + argument[0] + ")");
    	}
    }
    
    //
    //	Deploy an application fragment on the node
    //
    private static void deployApplicationFragment(Path dtmPath, DtmNode node)
    {    	
    	//
    	//	Create a deploy fragment command
    	//
        DtmDeployFragmentCommand command = new DtmDeployFragmentCommand(
                FragmentType.STREAMBASE, 
                m_fragment.toString(), 
                node);
        
        //
        //  Set some deploy options - ignore user's options file if it exists
        //
        Map<String,String> parameters = new HashMap<>();
        parameters.put("ignoreoptionsfile", "true");
        
        //
        //  Set some JVM options - enable assertions
        //
        List<String> jvmOptions =  new ArrayList<>();
        jvmOptions.add("-enableassertions");
        command.setExecutionOptions(jvmOptions);
        
        //
        //  Set some application fragment parameters - the configuration file
        //
        List<String> arguments =  new ArrayList<>();
        String argument = "configuration=" + m_configuration.toString();
        arguments.add(argument);
        command.setApplicationArguments(arguments);
        
        //
        //	Execute the command
        //
        Monitor monitor = new Monitor("deploy", "fragment");
        command.execute(parameters, monitor);
        
        //
        //	Fragment is running - we just sleep here to let it run for a while
        //	This is an ugly hack and is just being done to keep this example simple.
        //
        try 
        {
			Thread.sleep(30000);
		} 
        catch (InterruptedException e) 
        {
			// nothing to see here - move along
		}
        
        //
        //	Terminate the fragment - ignore the return code
        //
        command.cancel();
        command.waitForCompletion();
    }
    
    //
    //	Generate a scoped node name.  Use user.name as the cluster name
    //
    private static String getNodeName()
    {
    	String clustername = System.getProperty("user.name");
    	return "A." + clustername;
    }
    
    private static void usage(final String message)
    {
    	throw new IllegalArgumentException(message);
    }
    
    private static Path m_fragment;
    private static Path m_configuration;
}

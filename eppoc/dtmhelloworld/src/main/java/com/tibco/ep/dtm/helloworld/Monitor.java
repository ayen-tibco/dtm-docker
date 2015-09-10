package com.tibco.ep.dtm.helloworld;

import com.tibco.ep.dtm.management.DtmResults;
import com.tibco.ep.dtm.management.IDtmProgress;

/**
 * Command monitor
 */
class Monitor implements IDtmProgress 
{
	/**
	 * Constructor
	 * @param command Command being monitored
	 * @param target Target being monitored
	 */
	Monitor(final String command, final String target) 
	{
		m_target = target;
		m_command = command;
	}

	@Override
	public void start() 
	{
		System.out.println(getPrefix("INFO") + "command starting");
	}

	@Override
	public void results(DtmResults results) 
	{
		System.out.println(getPrefix("RESULTS") + "\n\n" + results.prettyPrint());
	}

	@Override
	public void message(String source, String message) 
	{
		System.out.println(getPrefix("INFO") + source + " - " + message);
	}

	@Override
	public void cancel() 
	{
		System.out.println(getPrefix("WARN") + "command canceled");
	}

	@Override
	public void complete() 
	{
		System.out.println(getPrefix("INFO") + "command complete");
	}

	@Override
	public void failed(String reason) 
	{
		System.out.println(getPrefix("FATAL") + reason);
	}
	
	//
	//	Get print prefix
	//
	private String getPrefix(final String severity)
	{
		return severity + " (" + m_command + " " + m_target + "): ";
	}
	
	private final String m_target;
	private final String m_command;
}

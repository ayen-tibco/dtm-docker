
   StreamBase Documentation
   
Aggregate Operator Time Dimension Sample

   This topic describes how to run AggregateByTime.sbapp, a sample
   application that contains an Aggregate operator with a time-based
   dimension. For detailed information about this and other Aggregate
   operator samples, see the Samples Guide in the StreamBase Studio Help.
   
Running AggregateByTime.sbapp in StreamBase Studio

   This sample demonstrates use of the time-based Aggregate operator. The
   time-based aggregate uses elapsed time to manage windows. This example
   uses 2-second windows to compute average price per share of symbols,
   grouping its computations by stock symbol.
    1. In the Package Explorer, double-click to open the
       AggregateByTime.sbapp application. Make sure the application is
       the currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. No output is displayed in the Application Output view yet, but the
       dequeuer is prepared to receive output. This view will eventually
       show the output of the application.
    4. In the Feed Simulations view, right click on AggregateByTime.sbfs
       and select Run Feed Simulation.
    5. Observe application output in the Application Output view. The
       format is similar to the following:
       
   12:06:42 AvgOut stock=MSFT, TimeBasedAverage=26.05,
       start_time=2013-12-05 12:06:40.000-0500, end_time=2013-12-05 12:06
   :42.000-0500,
       first_time=2013-12-05 12:06:40.513-0500, last_time=2013-12-05 12:0
   6:41.620-0500, n=4
   12:06:44 AvgOut stock=AAPL, TimeBasedAverage=43.95,
       start_time=2013-12-05 12:06:42.000-0500, end_time=2013-12-05 12:06
   :44.000-0500,
       first_time=2013-12-05 12:06:42.223-0500, last_time=2013-12-05 12:0
   6:43.725-0500, n=4
       
Tip

   If output rows are too long to see all the data, click a row to
   display its fields in the Display Fields pane below the table.
       Each row is an average for a group. The output fields represent
       the following, per window:
       
          + Symbol -- The stock symbol, used to sort tuples into groups
          + Average -- Time-based Average price per share, calculated
            with the aggregate function avg()
          + start_time -- The time the window opened (its openval())
          + end_time -- The time the window closed (its closeval())
          + first_time -- Time at which the first tuple arrived in the
            window (firstval(time))
          + last_time -- Time at which the last tuple arrived in the
            window (lastval(time))
          + n -- The number of tuples over which average price per share
            was computed (count())
            
       Averages and times will all vary depending on the rate of input.
       For example, the first time you run the sample, the first tuple
       may occur just before the 2-second boundary and the second time it
       may occur just after the 2-second boundary. The averages will be
       different because the tuples are seen in different windows.
    6. When the feed simulation has stopped, you can observe how emission
       occurs in two seconds (the difference between end_time and
       start_time is 2.0) regardless of whether the operator continues to
       receive input. If you run again, speeding up or slowing down the
       feed simulation, you will see n go down or up, accordingly.
    7. Click the Manual Input tab, type a string for Symbol and number
       for PricePerShare, and then hit Send Data. After two seconds, you
       will observe the operator time out and emit output.
    8. When done, press F9 or click the Stop Running Application button.
       
Running AggregateByTime.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd AggregateByTime.sbapp
    3. In window 2, type:
       sbc dequeue AvgOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbfeedsim ResourceFiles\AggregateByTime.sbfs
       Data starts flowing into the application. Observe several lines in
       the dequeue window, similar to the following:
       
   MSFT,26.05,2007-04-26 13:29:52.000-0400,2007-04-26 13:29:54.000-0400
   AAPL,43.95,2007-04-26 13:29:52.000-0400,2007-04-26 13:29:54.000-0400
       Average, start, and end times all vary depending on where the
       tuples fall relative to the wall-clock time. For example, the
       first time you run the sample, the first tuple may occur just
       before the 2-second boundary and the second time may occur just
       after the 2-second boundary. The averages will be different
       because the tuples occur in different windows. The start times and
       end times will, of course, be different.
    5. In window 3, type sbadmin shutdown to terminate the daemon and
       dequeuer.
       
Sample Location

   When you load the sample into StreamBase Studio, Studio copies the
   sample's files to a project folder in your Studio workspace directory.
   Because the Studio workspace location is normally part of your home
   directory, you have full access rights there by default.
   
Important

   Load this sample in StreamBase Studio, and thereafter use the Studio
   workspace copy of the sample to run and test it, even when running
   from the command prompt.
   
   Using the workspace copy of the sample avoids the permission problems
   that can occur when trying to work with the initially installed
   location of the sample. The default workspace location for this sample
   is:
studio-workspace/sample_operator

   See Default Installation Directories in the Installation Guide for the
   location of studio-workspace on your system.
   
   In the default TIBCO StreamBase installation, this sample's files are
   initially installed in:
streambase-install-dir/sample/operator

   See Default Installation Directories in the Installation Guide for the
   location of streambase-install-dir on your system. This location may
   require administrator privileges for write access, depending on your
   platform.
   
   Copyright © 2004-2015 TIBCO Software Inc. All rights reserved.

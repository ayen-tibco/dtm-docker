
   StreamBase Documentation
   
Heartbeat Merge Sample

   This sample uses two Heartbeat operators to prevent a Merge operator
   from starving when either of its inputs stop receiving tuples. Without
   the Heartbeat operators, the Merge operator would stop outputting
   tuples as soon as either of its two input streams is inactive, even if
   it continued to receive tuples on the other input stream.
   
   This sample also demonstrates how to flag the non-heartbeat tuples on
   an input stream, so that the heartbeat tuples can be filtered out
   later on.
   
   This README describes how to run the Heartbeat Merge sample
   application.
   
Running HeartbeatMerge.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       HeartbeatMerge.sbapp application. Make sure the application is the
       currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the MergedFeeds stream. No
       output is displayed at this point, but the Output View is prepared
       to receive output. This view will eventually show the output of
       the application.
    4. In the Manual Input view, select the Feed1 input stream.
    5. Enter IBM for symbol, 83.17 for price, and 2005-11-02 12:00:00 for
       time.
    6. Click Send Data. You should not see any output in the Output View
       yet. The tuple you just sent is currently buffered in the Merge
       operator.
    7. Select the Feed2 input stream.
    8. Enter MSFT for symbol, 24.89 for price, and 2005-11-02 12:00:30
       for time.
    9. Click Send Data. You should now see the IBM tuple in Output View.
   10. Wait a few moments. Within 30 seconds, look for the MSFT tuple to
       appear in the Output View. If this application had not made use of
       Heartbeat operators, the MSFT tuple would have remained inside the
       Merge operator until Feed1 received a tuple with a timestamp
       greater than or equal to 2005-11-02 12:00:30. Instead, the
       Heartbeat operators continually emit such tuples, thereby
       preventing the Merge operator from getting stuck. You can see this
       for yourself by selecting the Feed1WithHeartbeats output stream in
       the Output View.
   11. When done, press F9 or click the Stop Running Application button.
       
Running HeartbeatMerge.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd HeartbeatAggregate.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2, type:
       sbc dequeue TicCounts
       No output is displayed at this point, but the dequeuer is prepared
       to receive output of the application.
    4. In window 3, type:
       echo IBM,84.17,2005-11-02 12:00:00 | sbc enqueue Feed1
       You should not see any output in the dequeuer window yet. The
       tuple you just sent is currently buffered in the Merge operator.
    5. In window 3, type:
       echo MSFT,24.89,2005-11-02 12:00:30 | sbc enqueue Feed2
       Wait a few moments. Within 30 seconds, look for the MSFT tuple to
       appear in the dequeuer window. If this application had not made
       use of Heartbeat operators, the MSFT tuple would have remained
       stuck inside the Merge operator until Feed1 received a tuple with
       a timestamp greater than or equal to 2005-11-02 12:00:30. Instead,
       the Heartbeat operators continually emit such tuples, thereby
       preventing the Merge operator from getting stuck. You can see this
       for yourself by dequeuing the Feed1WithHeartbeats output stream.
    6. In window 2, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
    7. In window 3, type the following command to terminate the server
       and dequeuer:
       sbadmin shutdown
       
   [1]Back to Top ^
   
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



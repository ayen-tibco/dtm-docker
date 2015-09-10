
   StreamBase Documentation
   
Heartbeat Aggregate Sample

   The HeartbeatAggregate.sbapp sample uses a Heartbeat operator to
   output the number of stock quotes received every five seconds. The
   actual counting is done by an Aggregate operator, but it depends on
   the Heartbeat to trigger output during periods when no quotes are
   received.
   
   This topic describes how to run the Heartbeat Aggregate sample
   application. For detailed information about this and other samples,
   please see the Samples Guide in the StreamBase Studio Help.
   
Running HeartbeatAggregate.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       HeartbeatAggregate.sbapp application. Make sure the application is
       the currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the TicCounts stream. No
       output is displayed at this point, but the Output View is prepared
       to receive output. This view will eventually show the output of
       the application.
    4. In the Manual Input view, select the InputTics input stream
    5. Enter IBM for symbol, and 83.17 for price.
    6. Click Send Data. Within five seconds, look for a tuple on the
       TicCounts output stream, stating that one quote was received in
       the last five seconds.
    7. Click Send Data three times in rapid succession. Within five
       seconds, look for a tuple on the TicCounts output stream, stating
       that three quotes were received in the last five seconds.
    8. Wait five seconds and look for a tuple on the TicCounts output
       stream, stating that zero quotes were received in the last five
       seconds.
    9. When done, press F9 or click the Stop Running Application button.
       
Running HeartbeatAggregate.sbapp in Terminal Windows

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
       echo IBM,84.17 | sbc enqueue SetPrice
       Within five seconds, look for a tuple in the dequeue window
       stating that one quote was received in the last five seconds.
    5. Wait five seconds and look for another tuple in the dequeue window
       stating that zero quotes were received in the last five seconds.
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



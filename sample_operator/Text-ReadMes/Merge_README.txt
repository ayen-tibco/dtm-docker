
   StreamBase Documentation
   
Merge Operator Sample

   This sample uses a Merge operator to interlace trades from a Reuters
   and the Interactive Data PlusFeed (formerly Comstock), based on trade
   time. The input streams contain the fields Symbol, Volume, PricePS
   (per share), Time, and FeedID.
   
   This topic describes how to run the Merge sample application. For more
   information about this and other samples, please see the Samples Guide
   in the StreamBase Studio Help.
   
Running Merge.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Merge.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the MergeOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view eventually shows the output of the
       application.
    4. In the Manual Input view, select the ComstockIn input stream
    5. Enter amat, 100, 23, 10, and 0 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    6. Enter amat, 23, 10, and 1 in the Symbol, Volume, PricePS, Time,
       and FeedID fields, respectively.
       Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    7. Select the ReutersIn input stream.
    8. Enter intc, 200, 21, 11, and 0 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=amat, Volume=100, PricePS=23.0, Time=10, FeedID=0
    9. Enter intc, 200, 21, 11, and 1 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe this data appears in the Application
       Output view.
Symbol=amat, Volume=100, PricePS=23.0, Time=10, FeedID=1
   10. Enter msft, 100, 40, 12, and 0 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe that no further output is displayed
       yet in the Application Output view.
   11. Enter msft, 100, 40, 12, and 1 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe that no further output is displayed
       yet in the Application Output view.
   12. Select the ComstockIn input stream.
   13. Enter amat, 300, 24, 15, and 0 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe this data appears in the Application
       Output view.
Symbol=intc, Volume=200, PricePS=21.0, Time=11, FeedID=0
Symbol=msft, Volume=100, PricePS=40.0, Time=12, FeedID=0
   14. Enter amat, 300, 24, 15, and 1 in the Symbol, Volume, PricePS,
       Time, and FeedID fields, respectively.
       Click Send Data, and observe this data appears in the Application
       Output view.
Symbol=intc, Volume=200, PricePS=21.0, Time=11, FeedID=1
Symbol=msft, Volume=100, PricePS=40.0, Time=12, FeedID=1
   15. When done, press F9 or click the Stop Running Application button.
       
Running Merge.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Merge.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2, type:
       sbc dequeue MergeOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window eventually shows the output of the
       application.
    4. In window 3, type:
       sbc enqueue
       The sbc command is now waiting for keyboard input. Type:
       ComstockIn,amat,100,23,10,0
       No output is displayed yet in the dequeue window.
    5. Type:
       ComstockIn,amat,100,23,10,1.
       No output is displayed yet in the dequeue window.
    6. Type:
       ReutersIn,intc,200,21,11,0.
       Observe this line in the dequeue window:
       amat,100,23.000000,10,0.
    7. Type:
       ReutersIn,intc,200,21,11,1.
       Observe this line in the dequeue window:
       amat,100,23.000000,10,1.
    8. Type:
       ReutersIn,msft,100,40,12,0.
       No further output is displayed yet in the dequeue window.
    9. Type:
       ReutersIn,msft,100,40,12,1.
       No further output is displayed yet in the dequeue window.
   10. Type:
       ComstockIn,amat,300,24,15,0.
       Observe these lines in the dequeue window:
intc,200,21.000000,11,0
msft,100,40.000000,12,0
   11. Type:
       ComstockIn,amat,300,24,15,1.
       Observe these lines in the dequeue window:
intc,200,21.000000,11,1
msft,100,40.000000,12,1
   12. In window 2, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
   13. In window 3, type the following command to terminate the server
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



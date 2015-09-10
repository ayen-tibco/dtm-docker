
   StreamBase Documentation
   
Union Operator Sample

   The Union operator sample uses a Union operator to interleave trades
   from a Reuters feed and the Interactive Data PlusFeed (formerly
   Comstock). The input streams contain schemas with the fields Symbol,
   PricePS (per share), Time, and FeedID.
   
   This topic describes how to run the Union sample application.
   
Running Union.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Union.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the UnionOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view eventually shows the output of the
       application.
    4. In the Manual Input view, select the ComstockIn input stream.
    5. Enter amat, 24, 1, and 0 in the Symbol, PricePS, Time, and FeedID
       fields, respectively.
    6. Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=amat, PricePS=24.0, Time=1, FeedID=0
    7. Select the ReutersIn input stream.
    8. Enter msft, 40, 2, and 1 in the Symbol, PricePS, Time, and FeedID
       fields, respectively.
    9. Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=msft, PricePS=40.0, Time=2, FeedID=1
   10. Enter intc, 24, 3, and 1 in the Symbol, PricePS, Time, and FeedID
       fields, respectively.
   11. Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=intc, PricePS=24.0, Time=3, FeedID=1
   12. Select the ComstockIn input stream.
   13. Enter amat, 23, 4, and 0 in the Symbol, PricePS, Time, and FeedID
       fields, respectively.
   14. Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=amat, PricePS=23.0, Time=4, FeedID=0
   15. Select the ReutersIn input stream.
   16. Enter intc, 22, 5, and 1 in the Symbol, PricePS, Time, and FeedID
       fields, respectively.
   17. Click Send Data, and observe this data appears in the Application
       Output view:
Symbol=intc, PricePS=22.0, Time=5, FeedID=1
       Note that the time in this tuple is before the time in the
       previous tuple. In other words, the Union operator is agnostic to
       the order of input tuples. Use a Merge operator if you want to
       order on a field by using a criterion such as time.
   18. When done, press F9 or click the Stop Running Application button.
       
Running Union.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
sbd Union.sbapp
    3. In window 2, type:
sbc dequeue UnionOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window eventually shows the output of the
       application.
    4. In window 3, type:
sbc enqueue
       The sbc command now waits for keyboard input. Ttype:
ComstockIn,amat,24,1,0
       Observe this line in the dequeue window:
amat,24.000000,1,0
    5. Type:
ReutersIn,msft,40,2,1
       Observe this line in the dequeue window:
msft,40.000000,2,1
    6. Type:
ReutersIn,intc,24,3,1
       Observe this line in the dequeue window:
intc,24.000000,3,1
    7. Type:
       ComstockIn,amat,23,5,0
       Observe this line in the dequeue window:
amat,23.000000,5,0
    8. Type:
ReutersIn,intc,22,4,1
       Observe this line in the dequeue window:
intc,22.000000,4,1
       Note that the time in this tuple is before the time in the
       previous tuple. In other words, the Union operator is agnostic to
       the order of input tuples. Use a Merge operator if you want to
       order on a field by using a criterion such as time.
    9. In window 3, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
   10. In window 3, type the following command to terminate the server
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



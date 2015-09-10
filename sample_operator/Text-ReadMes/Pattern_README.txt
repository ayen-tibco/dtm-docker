
   StreamBase Documentation
   
Pattern Operator Sample

   This sample uses a Pattern operator to pass in only market data for
   securities that have already been traded. The MarketData input stream
   contains the fields symbol, price, and time. The TradeData input
   stream has the same fields plus a shares field.
   
   This topic describes how to run Pattern.sbapp, the Pattern operator
   sample application.
   
Running Pattern.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Pattern.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the RelevantMarketData
       output stream.
    4. In the Manual Input view, select the MarketData input stream.
    5. Enter GOOG, 10 and 1 in the symbol, price, and time fields,
       respectively.
    6. Click Send Data, and observe that no data appears on the
       RelevantMarketData stream, as there have been no trades in GOOG.
    7. In the Manual Input view, select the TradeData input stream.
    8. Enter GOOG, 10, 20, and 2 in the symbol, price, shares, and time
       fields, respectively.
    9. In the Manual Input view, reselect the MarketData input stream.
   10. Change the values of price and time to 11 and 3, respectively.
   11. Click Send Data, and observe that this data shows up on the output
       stream.
   12. Reselect the TradeData input stream.
   13. Change the values of symbol and time to YHOO and 4, respectively,
       and click Send Data.
   14. Reselect the MarketData input stream.
   15. Change the value of time to 5 and click Send Data. Notice that
       this information still shows up on the output stream.
   16. Change the value of symbol and time to YHOO and 6, respectively.
   17. Click Send Data, and observe that this data shows up on the output
       stream.
   18. When done, press F9 or click the Stop Running Application button.
       
Running Pattern.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open four terminal windows on UNIX, or four StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Pattern.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2: type:
       sbc dequeue RelevantMarketData
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue TradeData
       The sbc command is now awaiting keyboard input.
    5. In window 4, type:
       sbc enqueue MarketData
       The sbc command is now awaiting keyboard input. Then type: GOOG,
       10, 1.
       No output is displayed yet in the dequeue window.
    6. In window 3, type:
       GOOG, 10, 20, 2.
       In window 4, type:
       GOOG, 11, 3.
       Observe this line in the dequeue window: GOOG,11,3.000.
    7. In window 3, type:
       YHOO, 10, 20, 4.
       In window 4, type:
       GOOG, 11, 5.
       Observe that this is still output in the dequeue window.
       In window 4, type:
       YHOO, 11, 6.
       Observe this line in the dequeue window: YHOO,11,6.000.
    8. In window 3 and 4 type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to stop
       the sbc command.
    9. In window 3 or 4, type the following command to terminate the
       server and dequeuer:
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



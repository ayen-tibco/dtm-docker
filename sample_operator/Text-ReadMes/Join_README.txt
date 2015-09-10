
   StreamBase Documentation
   
Join Operator Sample

   This sample uses a Join operator to combine trades from a Reuters and
   the Interactive Data PlusFeed (formerly Comstock), where the stocks
   are the same and the price per share is greater than or equal to $1,
   over a period of 60 seconds. The input streams contain the fields
   Symbol, PricePS_R (per share), and Time.
   
   This topic describes how to run the Join sample application. For more
   information about this and other samples, please see the Samples Guide
   in the StreamBase Studio Help.
   
Running Join.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Join.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the JoinOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view will eventually show the output of
       the application.
    4. In the Manual Input view, select the ReutersIn input stream.
    5. Enter intc, 23.0, and 10 in the Symbol_R, PricePS_R, and Time_R
       fields, respectively.
    6. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    7. Select the ComstockIn input stream.
    8. Enter intc, 23.5, and 20 in the Symbol_C, PricePS_C, and Time_C
       fields, respectively.
    9. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   10. Select the ReutersIn input stream.
   11. Enter intc, 24.5, and 30 in the Symbol_R, PricePS_R, and Time_R
       fields, respectively.
   12. Click Send Data, and observe this data appears in the Application
       Output view
DeltaPricePS=1.0, PricePS_R=24.5, PricePS_C=23.5, Time_R=30
   13. Select the ComstockIn input stream.
   14. Enter amat, 34.5, and 40 in the Symbol_C, PricePS_C, and Time_C
       fields, respectively.
   15. Click Send Data, and observe that no further output is displayed
       yet in the Application Output view.
   16. Enter intc, 26.5, and 50 in the Symbol_C, PricePS_C, and Time_C
       fields, respectively.
   17. Click Send Data, and observe this data appears in the Application
       Output view
DeltaPricePS=-3.5, PricePS_R=23.0, PricePS_C=26.5, Time_R=10
DeltaPricePS=-2.0, PricePS_R=24.5, PricePS_C=26.5, Time_R=30
   18. Select the ReutersIn input stream.
   19. Enter amat, 32.5, and 60 in the Symbol_R, PricePS_R, and Time_R
       fields, respectively
   20. Click Send Data, and observe this data appears in the Application
       Output view
DeltaPricePS=-2.0, PricePS_R=32.5, PricePS_C=34.5, Time_R=60
   21. Select the ComstockIn input stream.
   22. Enter intc, 27.5, and 100 in the Symbol_C, PricePS_C, and Time_C
       fields, respectively.
   23. Click Send Data, and observe that no further output is displayed
       yet in the Application Output view.
   24. When done, press F9 or click the Stop Running Application button.
       
Running Join.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, enter:
       sbd Join.sbapp
       A message indicates that the StreamBase Server is listening and
       waiting for tuples.
    3. In window 2, enter:
       sbc dequeue JoinOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, enter:
       sbc enqueue ReutersIn
       The sbc command is now awaiting keyboard input in window 3.
    5. Enter these two lines, pressing Enter after each:
       intc, 23.0, 10
       intc, 23.5, 20
       No output is displayed yet in the dequeue window.
    6. Enter a new line:
       intc, 24.5, 30
       Observe this output in the dequeue window:
1,24.5,23.5,30
    7. Enter:
       amat, 34.5, 40
       No further output is displayed yet in the dequeue window.
    8. Enter:
       intc, 26.5, 50
       Observe this output in the dequeue window:
-3.5,23,26.5,10
-2,24.5,26.5,30
    9. Enter:
       amat, 32.5, 60
       Observe this output in the dequeue window:
-2,32.5,34.5,60
   10. Enter:
       intc, 27.5, 100
       No output is displayed yet in the dequeue window.
   11. In window 2, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
   12. In window 3, type the following command to terminate the server
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



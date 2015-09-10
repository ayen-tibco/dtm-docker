
   StreamBase Documentation
   
BSort Operator Sample

   The Bsort.sbapp sample application uses a BSort operator to reorder
   trades by time, for an input stream where trades come in in random
   order. The input stream contains the fields Symbol, Volume, and Time.
   It is interesting to compare the results of running this application
   with two passes and with six passes.
   
   This topic describes how to run the Bsort sample application. For
   detailed information about this and other samples, please see the
   Samples Guide in the StreamBase Studio Help.
   
Running Bsort.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Bsort.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the StockOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view will eventually show the output of
       the application.
    4. In the Manual Input view, select the StockIn input stream.
    5. Enter msft, 100, and 10 in the Symbol, Volume, and Time fields,
       respectively.
    6. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    7. Enter msft, 100, and 30 in the Symbol, Volume, and Time fields,
       respectively.
    8. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    9. Enter intc, 100, and 5 in the Symbol, Volume, and Time fields,
       respectively.
   10. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=intc, Volume=100, Time=5
   11. Enter msft, 100, and 25 in the Symbol, Volume, and Time fields,
       respectively.
   12. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=msft, Volume=100, Time=10
   13. Enter amat, 100, and 2 in the Symbol, Volume, and Time fields,
       respectively.
   14. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=amat, Volume=100, Time=2
   15. Enter intc, 100, and 15 in the Symbol, Volume, and Time fields,
       respectively.
   16. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=intc, Volume=100, Time=15
   17. Enter msft, 100, and 40 in the Symbol, Volume, and Time fields,
       respectively.
   18. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=msft, Volume=100, Time=25
       Note that two passes are insufficient to order this input stream.
   19. When done, press F9 or click the Stop Running Application button.
       
Running Bsort.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Bsort.sbapp.
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2, type:
       sbc dequeue
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue StockIn
       The sbc command is now awaiting keyboard input. Then type:
       msft,100,10.
       No output is displayed yet in the dequeue window.
    5. Type:
       msft, 100, 30
       No output is displayed yet in the dequeue window.
    6. Type:
       intc, 100, 5
       Observe this line in the dequeue window: StockOut,intc,100,5.
    7. Type:
       msft, 100, 25
       Observe this line in the dequeue window: StockOut,msft,100,10.
    8. Type:
       amat, 100, 2
       Observe this line in the dequeue window: StockOut,amat,100,2.
    9. Type:
       intc, 100, 15
       Observe this line in the dequeue window: StockOut,intc,100,15.
   10. Type:
       msft, 100, 40
       Observe this line in the dequeue window: StockOut,msft,100,25.
       Note that two passes are insufficient to order this input stream.
   11. Press Ctrl+Z (Windows) or Ctrll+D (UNIX) in windows 2 and 3 to
       stop the sbc sessions.
   12. In window 2 or 3, enter sbadmin shutdown to stop the server.
       
Modifying and Running Bsort.sbapp to Fully Sort the Input Stream in
StreamBase Studio

    1. Return to the Authoring Perspective.
    2. In the Package Explorer, right-click on Bsort.sbapp, and select
       Open.
    3. In the Bsort.sbapp view, click on the myBSort operator.
    4. In the Properties view, click on the Sort Settings tab.
    5. Change the value in the Tuples, using a buffer size of field from
       2 to 6.
    6. Click File -> Save As, enter a new application name, and click OK.
    7. Select the modified application in the Package Explorer, then
       click the Run button.
    8. In the Application Output view, select the StockOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view will eventually show the output of
       the application.
    9. In the Manual Input view, select the StockIn input stream.
   10. Enter msft, 100, and 10 in the Symbol, Volume, and Time fields,
       respectively.
   11. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   12. Enter msft, 100, and 30 in the Symbol, Volume, and Time fields,
       respectively.
   13. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   14. Enter intc, 100, and 5 in the Symbol, Volume, and Time fields,
       respectively.
   15. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   16. Enter msft, 100, and 25 in the Symbol, Volume, and Time fields,
       respectively.
   17. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   18. Enter amat, 100, and 2 in the Symbol, Volume, and Time fields,
       respectively.
   19. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   20. Enter intc, 100, and 15 in the Symbol, Volume, and Time fields,
       respectively.
   21. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   22. Enter msft, 100, and 40 in the Symbol, Volume, and Time fields,
       respectively.
   23. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=amat, Volume=100, Time=2
   24. Enter intc, 100, and 20 in the Symbol, Volume, and Time fields,
       respectively.
   25. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=intc, Volume=100, Time=5
   26. Enter intc, 100, and 18 in the Symbol, Volume, and Time fields,
       respectively.
   27. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=msft, Volume=100, Time=10
       Note that 6 passes are sufficient to order this input stream.
   28. When done, press F9 or click the Stop Running Application button.
       
Modifying and Running Bsort.sbapp to Fully Sort the Input Stream in Terminal
Windows

    1. In StreamBase Studio, double-click your copy of the sbapp file to
       open it.
    2. Change the value in the Tuple buffer size field from 2 to 6.
    3. Click OK.
    4. Save the application.
    5. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to your workspace
       copy of the sample, as described above.
    6. In window 1, type:
       sbd Bsort.sbapp
       The window shows this output: notice[StreamBaseServer] listening
       on port 10000.
    7. In window 2, type:
       sbc dequeue
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    8. In window 3, type:
       sbc enqueue StockIn
       The sbc command is now awaiting keyboard input. Then type:
       msft,100,10.
       No output is displayed yet in the dequeue window.
    9. Type:
       msft, 100, 30
       No output is displayed yet in the dequeue window.
   10. Type:
       intc, 100, 5
       No output is displayed yet in the dequeue window.
   11. Type:
       msft, 100, 25
       No output is displayed yet in the dequeue window.
   12. Type:
       amat, 100, 2
       No output is displayed yet in the dequeue window.
   13. Type:
       intc, 100, 15
       No output is displayed yet in the dequeue window.
   14. Type:
       msft, 100, 40
       Observe this line in the dequeue window: StockOut,amat,100,2.
   15. Type:
       intc, 100, 20
       Observe this line in the dequeue window: StockOut,intc,100,5.
   16. Type:
       intc, 100, 18
       Observe this line in the dequeue window: StockOut,msft,100,10
       Note that 6 passes are sufficient to order this input stream.
   17. Press Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the sbc session.
   18. In window 3, type:
       sbadmin shutdown
       This command terminates the server and dequeuer.
       
Tip

   The Group By fields can be used to order within a set of tuples, where
   the value of the Expression is the same. (The Expression can simply be
   an input field.) This sets up a buffer for each set of tuples rather
   one buffer for all the tuples in the stream.
   
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




   StreamBase Documentation
   
Aggregate Operator Field Dimension Sample

   This topic describes how to run AggregateByField.sbapp, a sample
   application that contains an Aggregate operator with a field-based
   dimension. For detailed information about this and other Aggregate
   operator samples, see the Samples Guide in the StreamBase Studio Help.
   
Running AggregateByField.sbapp in StreamBase Studio

   In this sample, the Aggregate operator uses a field-based dimension to
   sum the volume of trades in a particular stock over 30 second windows,
   advancing every 30 seconds. It also ensures that the aggregation works
   for intermixed tuples for different stocks.
    1. In the Package Explorer, double-click to open the
       AggregateByField.sbapp application. Make sure the application is
       the currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the OutputChunkedTrades
       output stream. No output is displayed at this point, but the
       dequeuer is prepared to receive output. This view will eventually
       show the output of the application.
    4. In the Manual Input view, enter 7, AMAT, and 100 in the Time,
       Symbol, and Volume fields, respectively. Note that strings in
       StreamBase are case sensitive, so "AMAT" is not the same as
       "amat".
    5. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    6. Enter 10, AMAT, and 100 in the Time, Symbol, and Volume fields,
       respectively.
    7. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    8. Enter 20, AMAT, and 200 in the Time, Symbol, and Volume fields,
       respectively.
    9. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   10. Enter 40, AMAT, and 100 in the Time, Symbol, and Volume fields,
       respectively.
   11. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, TimeChunk=0, TotalVolume=400
       
Tip

   If output data is too long to easily see in the Application Output
   table, click a row to display its field data in the Display Fields
   pane below the table.
   12. Enter 41, AMAT, and 100 in the Time, Symbol, and Volume fields,
       respectively.
   13. Click Send Data, and observe that no output is displayed in the
       Application Output view.
   14. Enter 45, INTC, and 100 in the Time, Symbol, and Volume fields,
       respectively.
   15. Click Send Data, and observe that no output is displayed in the
       Application Output view.
   16. Enter 50, AMAT, and 200 in the Time, Symbol, and Volume fields,
       respectively.
   17. Click Send Data, and observe that no output is displayed in the
       Application Output view.
   18. Enter 55, INTC, and 300 in the Time, Symbol, and Volume fields,
       respectively.
   19. Click Send Data, and observe that no output is displayed in the
       Application Output view.
   20. Enter 65, AMAT, and 100 in the Time, Symbol, and Volume fields,
       respectively.
   21. Click Send Data, and observe these lines in the Application Output
       view.
       
   Symbol=AMAT, TimeChunk=30, TotalVolume=400
   Symbol=INTC, TimeChunk=30, TotalVolume=400
   22. When done, press F9 or click the Stop Running Application button.
       
Running AggregateByField.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type: sbd AggregateByField.sbapp.
       The window shows: .
       
   notice[StreamBaseServer] listening on port 10000
    3. In window 2, type sbc dequeue OutputChunkedTrades.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type sbc enqueue TradesIn.
       The sbc command is now awaiting keyboard input. Then type:
       7,AMAT,100
       No output is displayed yet in the dequeue window.
    5. Type:
       10,AMAT,100
       No output is displayed yet in the dequeue window.
    6. Type:
       20,AMAT,200
       No output is displayed yet in the dequeue window.
    7. Type:
       40,AMAT,100
       Observe this line in the dequeue window: AMAT,0,400
    8. Type:
       41,AMAT,100
       No output is displayed in the dequeue window.
    9. Type:
       45,INTC,100
       No output is displayed in the dequeue window.
   10. Type:
       50,AMAT,200
       No output is displayed in the dequeue window.
   11. Type:
       55,INTC,300
       No output is displayed in the dequeue window.
   12. Type:
       65,AMAT,100
       Observe these lines in the dequeue window:
       
   AMAT,30,400
   INTC,30,400
   13. Press Control+Z (Windows) or Control+D (UNIX).
       The sbc process will exit.
   14. In window 3, type the following command to terminate the server
       and dequeuer:
       sbadmin shutdown
       
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

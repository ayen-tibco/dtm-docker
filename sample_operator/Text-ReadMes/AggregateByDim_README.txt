
   StreamBase Documentation
   
Aggregate Operator Two-Dimension Sample

   This topic describes how to run AggregateByDim.sbapp, the Aggregate
   operator two-dimension sample. For detailed information about this and
   other Aggregate operator samples, see the Samples Guide in the
   StreamBase Studio Help.
   
   Consider the following problem: You are interested in the average
   price per share of a stock over some number of trades. You also want
   to know if the stock is active or not. If you get fewer than the
   requisite number of trades during some time period, then you conclude
   that the stock is relatively inactive. If you see more than that
   number of trades in the time period, the stock is very active.
   
   This problem can be solved using an Aggregate with two window
   dimensions, one for the number of trades (as tuples), and another for
   time period. The time period is computed as a field-based aggregate
   using a timestamp field. In the following example, the first tuple
   emitted from Aggregate2Dimensions shows the average of five tuples.
   The second emitted tuple shows the average of only two tuples because
   only those two tuples fall within the time window as defined by the
   second dimension. The third tuple is emitted because five tuples had
   been received by Aggregate2Dimensions since the last five tuple group.
   However, because the first two of those input tuples had been
   calculated into the second emitted tuple, there are only three tuples
   available to be used to calculate the average. The numberShares,
   firstSeqnum, and lastSeqnum fields reflect this fact.
   
Running AggregateByDim.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       AggregateByDim.sbapp application. Make sure the application is the
       currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the AvgPricePSOut output
       stream. No output is displayed at this point, but the dequeuer is
       prepared to receive output. This view will eventually show the
       output of the application: the first tuple received will open a
       window that will close after receiving either five tuples or a
       tuple with time greater than or equal to 60.
    4. In the Manual Input view, enter 1, AMAT, 20, and 1 in the seqnum,
       symbol, price, and time fields, respectively.
    5. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    6. Enter 2, AMAT, 21, and 11 in the seqnum, symbol, price, and time
       fields, respectively.
    7. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    8. Enter 3, AMAT, 22, and 21 in the seqnum, symbol, price, and time
       fields, respectively.
    9. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   10. Enter 4, AMAT, 23, and 31 in the seqnum, symbol, price, and time
       fields, respectively.
   11. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   12. Enter 5, AMAT, 24, and 41 in the seqnum, symbol, price, and time
       fields, respectively.
   13. Click Send Data, and observe this line in the Application Output
       view:
       
   symbol=AMAT, numberShares=5, averagePricePerShare=22.0,
   lowerBoundTimeWindow=0.0, upperBoundTimeWindow=60.0, firstSeqnum=1, la
   stSeqnum=5
       This input causes the Aggregate operator to close the first
       window, which triggers the release of the output tuple.
       
Tip

   If output data is too long to easily see in the Application Output
   table, click a row to display its field data in the Display Fields
   pane below the table.
   14. Enter 6, AMAT, 25, and 61 in the seqnum, symbol, price, and time
       fields, respectively.
       This input causes a new window to open. Like the first window, it
       will close after receiving either five tuples or a tuple with time
       greater than or equal to 60.
   15. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   16. Enter 7, AMAT, 26, and 119 in the seqnum, symbol, price, and time
       fields, respectively.
   17. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   18. Enter 8, AMAT, 27, and 121 in the seqnum, symbol, price, and time
       fields, respectively.
   19. Click Send Data, and observe this line in the Application Output
       view:
       
   symbol=AMAT, numberShares=3, averagePricePerShare=25.5,
   lowerBoundTimeWindow=60.0, upperBoundTimeWindow=120.0, firstSeqnum=6,
   lastSeqnum=7
   20. Enter 9, AMAT, 26, and 150 in the seqnum, symbol, price, and time
       fields, respectively.
   21. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
   22. Enter 10, AMAT, 26, and 151 in the seqnum, symbol, price, and time
       fields, respectively.
   23. Click Send Data, and observe this line in the Application Output
       view:
       
   symbol=AMAT, numberShares=3, averagePricePerShare=28,
   lowerBoundTimeWindow=120.0, upperBoundTimeWindow=180.0, firstSeqnum=8,
    lastSeqnum=10
   24. When done, press F9 or click the Stop Running Application button.
       
Running AggregateByDim.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd AggregateByDim.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2, type:
       sbc dequeue AvgPricePSOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue TradesIn
       The sbc command is now awaiting keyboard input. Then type:
       1,AMAT,20,1
       No output is displayed yet in the dequeue window.
    5. Type:
       2,AMAT,21,11
       No output is displayed yet in the dequeue window.
    6. Type:
       3,AMAT,22,21
       No output is displayed yet in the dequeue window.
    7. Type:
       4,AMAT,23,31
       No output is displayed yet in the dequeue window.
    8. Type:
       5,AMAT,24,41
       Observe this line in the dequeue window:
       AMAT,5,22,0,60,1,5
    9. Type:
       6,AMAT,25,61
       No output is displayed yet in the dequeue window.
   10. Type:
       7,AMAT,26,119
       No output is displayed yet in the dequeue window.
   11. Type:
       8,AMAT,27,121
       Observe this line in the dequeue window:
       AMAT,2,25.5,60,120,6,7
   12. Type:
       9,AMAT,28,150
       No output is displayed yet in the dequeue window.
   13. Type:
       10,AMAT,29,151
       Observe this line in the dequeue window:
       AMAT,3,28,120,180,8,10
   14. Press Control+Z (Windows) or Control+D (UNIX).
       The sbc process will exit.
   15. In window 3, type:
       sbadmin shutdown
       The sbadmin shutdown command terminates the server and dequeuer.
       
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

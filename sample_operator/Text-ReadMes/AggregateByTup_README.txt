
   StreamBase Documentation
   
Aggregate Operator Tuple Dimension Sample

   This topic describes how to run AggregateByTup.sbapp, a sample
   application that contains an Aggregate operator with a tuple-based
   dimension. For detailed information about this and other Aggregate
   operator samples, see the Samples Guide in the StreamBase Studio Help.
   
   This sample demonstrates two uses of the tuple-based Aggregate
   operator. The first uses a tuple-based Aggregate operator to calculate
   a moving average of price for each of four successive trades of a
   given stock. It also ensures that the aggregation works for intermixed
   tuples for different stocks. The second provides a sequence number for
   input tuples, using a window which does not close and the emit feature
   to emit an output tuple for every input tuple.
   
Running AggregateByTup.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       AggregateByTup.sbapp application. Make sure the application is the
       currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the AvgPricePSOut output
       stream. No output is displayed at this point, but the dequeuer is
       prepared to receive output. This view will eventually show the
       output of the application.
    4. In the Manual Input view, select the TradesIn input stream.
    5. Enter AMAT and 23 in the Symbol, and PricePerShare fields,
       respectively.
    6. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    7. Enter AMAT and 24 in the Symbol, and PricePerShare fields,
       respectively.
    8. Click Send Data, and observe that no output is displayed yet in
       the Application Output view.
    9. Again enter AMAT and 24 in the Symbol, and PricePerShare fields,
       respectively.
   10. Click Send Data. There is still no output in the Application
       Output view.
   11. Enter AMAT and 25 in the Symbol, and PricePerShare fields,
       respectively.
   12. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=24.0
       
Tip

   If output data is too long to easily see in the Application Output
   table, click a row to display its field data in the Display Fields
   pane below the table.
   13. Enter AMAT and 20 in the Symbol, and PricePerShare fields,
       respectively.
   14. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=23.25
   15. Enter AMAT and 21 in the Symbol, and PricePerShare fields,
       respectively.
   16. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=22.5
   17. Enter AMAT and 21 in the Symbol, and PricePerShare fields,
       respectively.
   18. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=21.75
   19. Enter AMAT and 22 in the Symbol, and PricePerShare fields,
       respectively.
   20. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=21.0
   21. Enter INTC and 27 in the Symbol, and PricePerShare fields,
       respectively.
   22. Click Send Data, and observe no further output is displayed yet in
       the Application Output view.
   23. Enter INTC and 28 in the Symbol, and PricePerShare fields,
       respectively.
   24. Click Send Data, and observe no further output is displayed yet in
       the Application Output view:
   25. Enter AMAT and 22 in the Symbol, and PricePerShare fields,
       respectively.
   26. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=21.5
   27. Enter INTC and 27 in the Symbol, and PricePerShare fields,
       respectively.
   28. Click Send Data, and observe no further output is displayed yet in
       the Application Output view:
   29. Enter AMAT and 23 in the Symbol, and PricePerShare fields,
       respectively.
   30. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=AMAT, MovingAverage=22.0
   31. Enter INTC and 28 in the Symbol, and PricePerShare fields,
       respectively.
   32. Click Send Data, and observe this line in the Application Output
       view:
       
   Symbol=INTC, MovingAverage=27.5
   33. Next, view the sequence number.
   34. In the Application Output view, select the SeqOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output.
   35. Change from Manual Input to the Feed Simulations view.
   36. Right click on SeqFeed.sbfs and select Run Feed Simulation.
   37. Notice on output how sequence_number increases by 1.
   38. When done, press F9 or click the Stop Running Application button.
       
Running AggregateByTup.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd AggregateByTup.sbapp
    3. In window 2, type:
       sbc dequeue AvgPricePSOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue TradesIn
       The sbc command is now awaiting keyboard input. Then type:
       AMAT,23
       No output is displayed yet in the dequeue window.
    5. Type:
       AMAT,24
       No output is displayed yet in the dequeue window.
    6. Type:
       AMAT,24
       No output is displayed yet in the dequeue window.
    7. Type:
       AMAT,25
       Observe this line in the dequeue window:
       AMAT,24
    8. Type:
       AMAT,20
       Observe this line in the dequeue window:
       AMAT,23.25
    9. Type:
       AMAT,21
       Observe this line in the dequeue window:
       AMAT,22.5
   10. Type:
       AMAT,21
       Observe this line in the dequeue window:
       AMAT,21.750000
   11. Type:
       AMAT,22
       Observe this line in the dequeue window:
       AMAT,21
   12. Type:
       INTC,27
       No further output is displayed yet in the dequeue window.
   13. Type:
       INTC,28
       No further output is displayed yet in the dequeue window.
   14. Type:
       AMAT,22
       Observe this line in the dequeue window:
       AMAT,21.5
   15. Type:
       INTC,27
       No further output is displayed yet in the dequeue window.
   16. Type:
       AMAT,23
       Observe this line in the dequeue window:
       AMAT,22
   17. Type:
       INTC,28
       Observe this line in the dequeue window: INTC,27.5
   18. In window 3, press Ctrl+C.
       The sbc enqueue command exits.
   19. In window 2, press Ctrl+C.
       The sbc dequeue command exits.
   20. In window 2, type:
       sbc dequeue SeqOut
   21. In window 3, type:
       sbfeedsim -a ResourceFiles\SeqFeed.sbfs
       Observe the sequence numbers in the dequeue window.
   22. Stop the feed simulation:
       In window 3, press Ctrl+C.
   23. In window 3, type the following command to terminate the server
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

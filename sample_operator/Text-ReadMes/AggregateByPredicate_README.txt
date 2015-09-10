
   StreamBase Documentation
   
Aggregate Operator Predicate Dimension Sample

   This topic describes how to run AggregateByPredicate.sbapp, a sample
   application that contains an Aggregate operator with a predicate-based
   dimension. For detailed information about this and other Aggregate
   operator samples, see the Samples Guide in the StreamBase Studio Help.
   
Running the Aggregate Predicate-Based Sample

   This sample demonstrates use of the predicate-based Aggregate
   operator. The predicate-based aggregate uses Boolean strings sent to a
   control port to open, emit from, and close aggregate windows. Data
   entered on the TradesIn port are not processed until true is sent to
   the EmitWindow or CloseWindow field of the control port, or both.
   
   The following table lists the predicate expressions entered in the
   three predicate dimension fields of the Edit Dimension dialog for this
   sample.
   Predicate Field Expression Entered
   Open a window in the current group when input1.OpenWindow
   For windows in the current group, Emit if input1.EmitWindow ||
   input1.CloseWindow
   For windows in the current group, Close if input1.CloseWindow
   
   The input1 in these expressions refers to input port 1 of the
   Aggregate operator. In this sample's case, a Union operator precedes
   the input port of the Aggregate operator, which combines two input
   streams, WindowControl and TradesIn. You send tuples to the
   WindowControl stream to open, emit, or close windows. OpenWindow,
   EmitWindow, and CloseWindow are all fields of type bool in the schema
   of the WindowControl stream. So you need only send true or false to
   one or more of these ports to cause a window control action.
   
   Thus, opening an aggregate window is a matter of sending a tuple
   containing true in the OpenWindow field of the WindowControl port.
   With a window open, you can now send tuples to the TradesIn stream.
   However, a computation based on the input trade values is not emitted
   from the window until you send a tuple containing true to the
   EmitWindow field of the WindowControl port.
   
Running AggregateByPredicate.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       AggregateByPredicate.sbapp application. Make sure the application
       is the currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. No output is displayed in the Application Output view yet, but the
       dequeuer is prepared to receive output. This view will eventually
       show the output of the application.
    4. In the Manual Input view, select the WindowControl stream from the
       Input stream drop-down list.
    5. In the OpenWindow field, type true, then click Send Data.
    6. Select TradesIn in the Input stream drop-down list.
    7. Enter several volume and price pairs, clicking Send Data after
       each pair. For example, enter Volume=1000, Price=39.45. Make up
       any pairs of numbers and send them to the application. Notice that
       no output is showing in the Application Output view, but you can
       see the tuples you are sending by switching to the Application
       Input view.
    8. In the Input stream drop-down, re-select WindowControl.
    9. Click Clear, then enter true in the EmitWindow field and click
       Send Data. Notice that a single tuple now appears in the
       Application Output view, showing the calculated VWAP for the
       volume and price tuples you sent. (Select the tuple to see it in
       more detail in the Details pane.)
   10. Re-select TradesIn in the Input stream drop-down list, and send
       several more pairs of volume and price data.
   11. Re-select WindowControl in the Input stream drop-down list. Again
       click Clear, then enter true in both the EmitWindow and
       CloseWindow fields. Click Send Data. Another tuple displays in the
       Application Output view.
   12. When done, press F9 or click the Stop Running Application button.
       
Running AggregateByPredicate.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open four terminal windows on UNIX, or four StreamBase Command
       Prompts on Windows. In each window, navigate to your workspace
       copy of the sample, as described above.
    2. In window 1, type:
       sbd AggregateByPredicate.sbapp
       The window shows an INFO line reporting that the server is now
       listening.
    3. In window 2, type:
       sbc dequeue
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enq WindowControl
    5. In window 4, type:
       sbc enq TradesIn
    6. In window 3, send true to the OpenWindow field of the
       WindowControl port:
       true,null,null
    7. In window 4, send several pairs of volume and price pairs. Use any
       numbers that come to mind, such as:
       4000,23.45
       5000,45.22
       550,119.55
       4000.45.55
       Notice that no output shows in the dequeue window 2 yet.
    8. In window 3, send true to the EmitWindow field of the
       WindowControl port:
       null,true,null
       Now you see a tuple in the dequeue window 2.
    9. In window 4, send several more pairs of volume and price pairs.
   10. In window 3, send true to the EmitWindow and CloseWindow fields of
       the WindowControl port:
       null,true,true
       Another tuple shows in the dequeue window 2.
   11. In windows 3 and 4, type Ctrl+C to stop the enqueuer. Then type
       sbadmin shutdown to terminate the server and the dequeuer.
       
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


   StreamBase Documentation
   
Gather Operator Sample

   The Gather.sbapp sample application has two EventFlows. The
   SimpleGather example uses a Gather operator to generate an alarm (a
   true value) for trades of 10,000 shares or more for a stock whose
   price is $200 or greater. The input stream contains the fields Symbol,
   Volume, and Price.
   
Note

   This is a very simple example which might be better done using a
   single Map operator, but it is instructive. For an example that truly
   requires a Gather operator, see the Compliance sample application.
   
   The TimeoutGather example demonstrates the effect of the Optional
   Timeouts settings. In this case, when no input is received for ten
   seconds, Gather is forced to emit whatever tuple values it has
   accumulated, setting any fields not received to null. The input stream
   has two string fields: Type (which must be enqueued as either Buy or
   Sell) and Symbol (which can be any string).
   
Running Gather.sbapp in StreamBase Studio

   To run the SimpleGather example:
    1. In the Package Explorer, double-click to open the Gather.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the BigTradeAlarm output
       stream. No output is displayed at this point, but the dequeuer is
       prepared to receive output. This view will eventually show the
       output of the application.
    4. In the Manual Input view, select the TradesIn input stream.
    5. Enter intc, 10000, and 201 in the Symbol, Volume, and Price
       fields, respectively
    6. Click Send Data, and observe this data appears in the
       BigTradeAlarm stream:
BigTradeAlarm=true
    7. Enter intc, 10000, and 199 in the Symbol, Volume, and Price
       fields, respectively
    8. Click Send Data, and observe this data appears in the
       BigTradeAlarm stream:
BigTradeAlarm=false
    9. Enter intc, 1000, and 201 in the Symbol, Volume, and Price fields,
       respectively
   10. Click Send Data, and observe this data appears in the
       BigTradeAlarm stream:
BigTradeAlarm=false
   11. Enter intc, 1000, and 187 in the Symbol, Volume, and Price fields,
       respectively
   12. Click Send Data, and observe this data appears in the
       BigTradeAlarm stream:
BigTradeAlarm=false
       
   To run the TimeoutGather example:
    1. In the Package Explorer, double-click to open the Gather.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the Matches output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view will eventually show the output of
       the application.
    4. In the Manual Input view, select the SimpleBuySellMatch input
       stream.
    5. Enter Buy and intc in the Type and Symbol fields, respectively,
       and press Send Data.
    6. Quickly change Type from Buy to Sell and press Send Data again.
    7. Observe this data appear in the Matches stream:
MatchedSymbol=intc
    8. Now change Type from Sell back to Buy again, and press Send Data.
    9. Do not send a Sell message. After 10 seconds, observe that the
       Gather operator times out, and writes the following to the Matches
       output stream:
MatchedSymbol=<No Match: intc,null>
       
   When done, press F9 or click the Stop Running Application button.
   
Running Gather.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
   
   To run the SimpleGather example:
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Gather.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2: type:
       sbc dequeue BigTradeAlarm
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue TradesIn
       The sbc command is now awaiting keyboard input. Then type: intc,
       10000, 201
       Observe this line in the dequeue window: true
    5. Type:
       intc, 10000, 199.
       Observe this line in the dequeue window: false
    6. Type:
       intc, 1000, 201.
       Observe this line in the dequeue window: false
    7. Type:
       intc, 1000, 187.
       Observe this line in the dequeue window: false
    8. In window 3, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
    9. In window 3, type the following command to terminate the server
       and dequeuer:
       sbadmin shutdown
       
   To run the TimeoutGather example:
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Gather.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2: type:
       sbc dequeue Matches
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue SimpleBuySellMatch
       The sbc command is now awaiting keyboard input. Then type: Buy,
       intc
    5. Still in window 3, quickly type:
       Sell, intc
       Observe this line in the dequeue window: intc
    6. In window 3, now type:
       Buy, intc
       Stop typing now and wait until the dequeue window displays: <No
       Match: intc,null>
       The application's Gather operator reports any unmatched tuples due
       to its timeout setting, which in this example is set to 10
       seconds.
    7. In window 3, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
    8. In window 3, type the following command to terminate the server
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



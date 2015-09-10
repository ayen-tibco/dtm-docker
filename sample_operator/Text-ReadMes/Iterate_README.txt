
   StreamBase Documentation
   
Iterate Operator Sample

   This topic describes Iterate.sbapp, the Iterate operator's sample
   application.
   
   This sample uses an Aggregate operator and the aggregatelist()
   expression language function to aggregate incoming tuples and collect
   them by their Symbol field into three-element lists of tuples, so that
   every list output contains three input tuples of the same stock. An
   Iterate operator then takes these lists, extracts their component
   tuples, and feeds them to a Map operator, which reorders and
   supplements the output fields.
   
   This sample demonstrates one use of the aggregatelist() function, to
   create lists with elements from individual tuples, and demonstrates a
   use of the Iterate operator, to extract the elements from a list back
   into individual tuples again.
   
   The incoming tuple contains fields named Symbol, Name, Description,
   Volume, and Price.
   
Running Iterate.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Iterate.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Manual Input view, select the StockIn input stream.
    4. Enter a dozen or so tuples, alternating the Symbol field, but
       re-using the same Symbol several times, clicking Send Data after
       each entry. For example, enter:
IBM, IBM Corporation, 4000, 127.81
DELL, Dell Inc, 2500, 13.62
IBM, IBM Corporation, 1000, 128.00
DELL, Dell Inc, 500, 13.64
MSFT, Microsoft Corporation, 6000, 28.70
DELL, Dell Inc, 2000, 13.75
MSFT, Microsoft Corporation, 400, 27.75
IBM, IBM Corporation, 2500, 129.62
       
Tip

   Instead of typing in the Manual Input view, while Iterate.sbapp is
   still running in Studio, open a [1]StreamBase Command Prompt. Start an
   enqueue session for the sample's input stream by typing
   sbc enqueue StockIn. Then copy the contents of the gray box above and
   paste them into the command prompt window.
    5. Inspect the Application Output view. Notice that there are three
       entries for Dell and three for IBM. There is no output for
       Microsoft yet because we have only entered two tuples. Enter one
       more Microsoft tuple to trigger its entries in the Application
       Output view:
MSFT, Microsoft Corporation, 1400, 29.45
    6. Enter more tuples and watch them accumulate in groups of three.
    7. When done, press F9 or click the Stop Running Application button.
       
Running Iterate.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open terminal windows on UNIX, or three StreamBase Command Prompts
       on Windows. In each window, navigate to the directory where the
       sample is installed, or to your workspace copy of the sample, as
       described above.
    2. In window 1, type:
       sbd Iterate.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2: type:
       sbc dequeue StockOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue StockIn
       The sbc command now awaits keyboard input.
    5. In window 3, type:
IBM, IBM Corporation, 4000, 127.81
DELL, Dell Inc, 2500, 13.62
IBM, IBM Corporation, 1000, 128.00
DELL, Dell Inc, 500, 13.64
MSFT, Microsoft Corporation, 6000, 28.70
DELL, Dell Inc, 2000, 13.75
       Observe that nothing appears in window 2 until you type the last
       line above, which is the third entry for Dell.
       
Tip

   Instead of typing the entries shown, you can copy and paste into
   window 3.
    6. In window 3, type two more lines:
MSFT, Microsoft Corporation, 400, 27.75
IBM, IBM Corporation, 2500, 129.62
       Observe that output lines for IBM now appear in window 2.
    7. In window 3, type one more line:
MSFT, Microsoft Corporation, 1400, 29.45
       Observe that output lines for Microsoft now appear in window 2.
    8. Enter more tuples and watch them accumulate in groups of three.
    9. In window 3 type Ctrl+C to stop the sbc enqueue command.
   10. In window 3, type the following command to terminate the server
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



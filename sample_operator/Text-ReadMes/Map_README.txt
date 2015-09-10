
   StreamBase Documentation
   
Map Operator Sample

   This sample uses a Map operator to accept tuples containing fields
   called Stockname, Symbol, Description, Volume, and Price. For each
   input tuple, a tuple containing only the fields called Symbol,
   Stockname, and Stockcost are output. This demonstrates one use of the
   Map operator to reorder and drop output fields, as well as calculate
   new output fields.
   
   This topic describes how to run the Map sample application.
   
Running Map.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Map.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the StockOut output stream.
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This view eventually shows the output of the
       application.
    4. In the Manual Input view, select the StockIn input stream
    5. Enter the following field values:
       
   Field Enter this value
   Symbol GE
   Name General Electric
   Description Makes everything from light bulbs to airplane engines.
   Volume 40487000
   Price 30.29
    6. Click Send Data, and a tuple arrives in the Application Output
       view. Select the new tuple and observe this data below the output
       stream table:
StockName=General Electric, StockSymbol=GE, StockCost=1.22635123E9
    7. When done, press F9 or click the Stop Running Application button.
       
Running Map.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type: sbd Map.sbapp
       The window shows
notice[StreamBaseServer] listening on port 10000
    3. In window 2, type: sbc deq StockOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type: sbc enq StockIn
       The sbc command in window 3 now waits for keyboard input. Type:
       GE, General Electric, Light bulbs to airplane engines, 40487000,
       30.29
       Observe this line in window 2:
       General Electric, GE, 1226351230
    5. In window 2, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
    6. In window 3, type the following command to terminate the server
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



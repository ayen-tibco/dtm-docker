
   StreamBase Documentation
   
Filter Operator Sample

   This sample uses a Filter operator to pass only trades of 10000 shares
   or more. The input stream contains the fields Symbol and Volume.
   
   This topic describes how to run Filter.sbapp, the Filter operator
   sample application. For detailed information about this and other
   samples, please see the Samples Guide in the StreamBase Studio Help.
   
Running Filter.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Filter.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the StockOut output stream
    4. In the Manual Input view, select the StockIn input stream
    5. Enter intc and 10000 in the Symbol and Volume fields, respectively
    6. Click Send Data, and observe that data appears in the StockOut
       stream
    7. Enter 9999 in the Volume field.
    8. Click Send Data, and observe no new data appears in the StockOut
       stream.
    9. When done, press F9 or click the Stop Running Application button.
       
Running Filter.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Filter.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2: type:
       sbc dequeue StockOut
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue StockIn
       The sbc command is now awaiting keyboard input. Then type: intc,
       100.
       No output is displayed yet in the dequeue window.
    5. Type:
       intc, 10000
       Observe this line in the dequeue window: intc,10000.
    6. Type:
       intc, 9999
       No further output is displayed yet in the dequeue window.
    7. Type:
       intc, 10001
       Observe this line in the dequeue window: intc,10001.
    8. Type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to stop the sbc command.
    9. In window 3, type the following command to terminate the server
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



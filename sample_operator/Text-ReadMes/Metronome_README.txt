
   StreamBase Documentation
   
Metronome Operator Sample

   This sample uses a Metronome operator to output the current share
   price of all stocks every five seconds. The current prices are stored
   in a query table.
   
   This topic describes how to run the Metronome sample application. For
   more information about this and other samples, please see the Samples
   Guide in the StreamBase Studio Help.
   
Running Metronome.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Metronome.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select the CurrentPrices stream.
       Observe a tuple showing every five seconds, with the current time,
       a null symbol, and a null price. This indicates that no prices are
       currently known. Once we record some share prices, the nulls will
       be replaced with actual values.
    4. In the Manual Input view, select the SetPrice input stream
    5. Enter INTC for symbol, and 23.25 for price.
    6. Click Send Data. Within five seconds, look for a tuple on the
       CurrentPrices output stream, stating that the current price of
       INTC is 23.25.
    7. Enter MSFT for symbol, and 25.75 for price.
    8. Click Send Data. Within five seconds, look for two tuples emitted
       on the CurrentPrices output stream. These tuples are repeated
       every five seconds.
    9. When done, press F9 or click the Stop Running Application button.
       
Running Metronome.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
       sbd Metronome.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000.
    3. In window 2, type:
       sbc dequeue CurrentPrices
       Look for a tuple every five seconds tuple with the current time, a
       null symbol, and a null price. This indicates that no prices are
       currently known. Once we record some share prices, the nulls will
       be replaced with actual values.
    4. In window 3, type:
       sbc enqueue SetPrice
       The sbc command is now waiting for keyboard input. Type:
       INTC, 23.25
       Within five seconds, look for a tuple in the dequeue window,
       stating that the current price of INTC is 23.25.
    5. In window 3, type:
       MSFT, 25.75
       Within five seconds, look for two tuples emitted in the dequeue
       window. These tuples are repeated every five seconds.
    6. In window 3, type: Ctrl+Z (Windows) or Ctrl+D (UNIX) to exit the
       sbc session.
    7. In window 3, type the following command to terminate the server
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



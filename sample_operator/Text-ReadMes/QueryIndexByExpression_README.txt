
   StreamBase Documentation
   
Query Table Index by Expression Sample

Introduction

   This sample consists of a simple EventFlow application,
   QueryIndexByExpression.sbapp, that illustrates the Query Table's index
   by expression feature introduced in release 7.3.0. This feature allows
   you to use an expression as part of specifying the field or fields
   that comprise a secondary index for a Query Table. The expression must
   be statically resolvable, and must contain the name of at least one
   field in the Query Table's schema.
   
   This sample's Query Table has the schema {string symbol, double
   lastPriceChange} and is automatically populated with the following
   values by means of the Initial Contents tab of the table's Properties
   view:
   IBM, -4.3
   MSFT, 6.7
   GOOG, 2.3
   APPL, 9.3
   
   The field name portion of the Query Table's secondary index is
   specified with the expression abs(lastPriceChange). This simple
   expression allows you to enter either a query value either as a
   positive or negative number and obtain the same result. For example,
   if a Query operator specifies a Read operation based on this secondary
   index, you could enter either 4.3 or -4.3 to obtain the result IBM.
   
Running This Sample in StreamBase Studio

    1. In the Package Explorer, double-click to open the
       QueryIndexByExpression.sbapp application. Make sure the
       application is the currently active tab in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Manual Input view, the QueryByPriceDelta input stream is
       preselected. Enter one of the values from the initially loaded
       contents shown above, as either a positive or negative number. For
       example, enter -4.3, then enter 4.3.
    4. Click Send Data, and a tuple arrives in the Application Output
       view. In both cases, the Application Output view shows the IBM row
       of the Query Table returned on the QueryResults stream.
    5. Continue experimenting with values from the list above, or values
       not in the list.
    6. When done, press F9 or click the Stop Running Application button.
       
Running This Sample in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type: sbd QueryIndexByExpression.sbapp
    3. In window 2, type: sbc deq QueryResults
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type: sbc enq queryPriceChange
       The sbc command in window 3 now waits for keyboard input. Type:
       4.3
       Observe this table row emitted in window 2:
       IBM,-4.3
    5. In window 3, type:
       -4.3
       Observe the same table row emitted in window 2:
       IBM,-4.3
    6. Continue typing values in window 2.
    7. When down, in window 3, type: Ctrl+C to exit the enqueue session.
    8. In window 3, type the following command to terminate the server
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
   
   [1]Back to Top ^
   
   Copyright © 2004-2015 TIBCO Software Inc. All rights reserved.



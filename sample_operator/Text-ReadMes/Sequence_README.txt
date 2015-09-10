
   StreamBase Documentation
   
Sequence Operator Sample

   This sample uses a Sequence operator to generate unique ID numbers for
   generic product orders, allowing for easy querying when updating
   orders. The Orders input stream contains the fields named productNum,
   quantity, and price. The OrdersChanges input stream contains the same
   fields plus an orderID field.
   
Running Sequence.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Sequence.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Manual Input view, select the Orders input stream.
    4. Enter G Washington, 12345678, 1000, and 4.30 in the name,
       productNum, quantity, and price fields, respectively.
    5. Click Send Data and observe that this order has been assigned a
       field orderID of 1.
    6. In the Manual Input view, select the OrderChanges input stream.
    7. Enter 1 and 1350 in the orderID and quantity fields, respectively.
    8. In the Application Output view, note that the quantity of the
       previously entered order has been updated.
    9. Reselect the Orders input stream.
   10. Enter G Washington, 23456789, 900, and 12.50 in the name,
       productNum, quantity, and price fields, respectively.
   11. Click Send Data and observe that this order has been assigned a
       field orderID of 2.
   12. Reselect the OrderChanges input stream.
   13. Enter 2, 98765432, and 1900 in the orderID, productNum, and
       quantity fields, respectively.
   14. Click Send Data and observe that the second order input has been
       updated.
   15. When done, press F9 or click the Stop Running Application button.
       
Running Sequence.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open terminal windows on UNIX, or three StreamBase Command Prompts
       on Windows. In each window, navigate to the directory where the
       sample is installed, or to your workspace copy of the sample, as
       described above.
    2. In window 1, type:
       sbd Sequence.sbapp
       The window shows notice[StreamBaseServer] listening on port 10000
    3. In window 2: type:
       sbc dequeue OutputOrders
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    4. In window 3, type:
       sbc enqueue OrderChanges
       The sbc command is now awaiting keyboard input.
    5. In window 4, type:
       sbc enqueue Orders
       The sbc command is now awaiting keyboard input. Then type: G
       Washington, 12345678, 1200, 4.30
       Observe this line in the dequeue window: G
       Washington,12345678,1200,4.300,1,null,null,null,null. Notice that
       this order has been assigned an orderID of 1 in the fifth field.
       (The null values at the end mean that this is a new order.)
    6. In window 3, type:
       1,null,null,1350,null
       Observe this line in the dequeue window: G
       Washington,12345678,1350,4.3,1,G Washington,12345678,1200,4.3
       The first set of values are the new order values and the second
       set of values (that were null before) show the old values.
    7. In window 4, type:
       G Washington, 23456789, 900, 12.50
       Observe this line in the dequeue window: G
       Washington,23456789,900,12.5,1,null,null,null,null
    8. In window 3, type:
       4, null, 98765432, 1900, null
       Observe this line in the dequeue window: G
       Washington,98765432,1900,12.5,4,G Washington,23456789,900,12.5
    9. In window 3 and 4 type Ctrl+C to stop the sbc commands.
   10. In window 3 or 4, type the following command to terminate the
       server and dequeuer:
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



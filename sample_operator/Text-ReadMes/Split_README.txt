
   StreamBase Documentation
   
Split Operator Sample

   The Split operator is used to make sure tuples on one stream are
   processed before tuples on a different stream, when two or more
   streams originate in a single output port. For this sample
   application, the application designer decided it is more important to
   process users on an IP address before multiple IP addresses for a
   single user. This application sends the tuple to two streams because
   each stream processes the data in a way that is incompatible with the
   other stream.
   
This Sample's Files

   The sample includes the following files in the ResourceFiles
   subdirectory:
     * A CSV trace file containing sample data, Split-IPandUserLogin.csv.
     * A feed simulation file, Split.sbfs, that references the trace
       file.
       
Running Split.sbapp in StreamBase Studio

    1. In the Package Explorer, double-click to open the Split.sbapp
       application. Make sure the application is the currently active tab
       in the EventFlow Editor.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, select All Streams.
    4. In the Feed Simulations view, select Split.sbfs and click Run.
    5. As the Feed Simulation sends input tuples to the application, they
       appear in the Application Input view.
    6. In the Application Output view, notice that as the Split operator
       receives each tuple, it first sends the tuple along the top path
       (which is processed as far to the right in the diagram as it can
       proceed) before the Split operator releases a copy of the same
       tuple along the bottom path. This control eventually results in
       the Application Output display of:
INTRUSION_TooManyUsersForIP IP=127.0.0.1, CountOfUsers=11
       before we see:
INTRUSION_TooManyIPsForUser UserID=101, CountOfIPs=11
       from the Output Stream on the lower path.
    7. After the CSV file reaches the last record, the Feed Simulation
       stops automatically.
    8. When done, press F9 or click the Stop Running Application button.
       
Running Split.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to the directory
       where the sample is installed, or to your workspace copy of the
       sample, as described above.
    2. In window 1, type:
sbd Split.sbapp
    3. In window 2: type:
sbc dequeue
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window eventually shows the output of the
       application.
    4. In window 3, type:
sbfeedsim ResourceFiles\Split.sbfs
    5. Observe lines like the following in the dequeue window:
INTRUSION_TooManyIPsForUser,101,11
INTRUSION_TooManyUsersForIP,127.0.0.1,11
INTRUSION_TooManyUsersForIP,127.0.0.1,5
INTRUSION_TooManyUsersForIP,127.0.0.3,4
INTRUSION_TooManyUsersForIP,127.0.7.1,4
       Notice what happened: as the Split operator received each tuple,
       it first sent the tuple along the top path (which was processed as
       far to the right in the diagram as it could proceed) before the
       Split operator released a copy of the same tuple along the bottom
       path.
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



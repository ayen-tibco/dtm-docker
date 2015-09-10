
   StreamBase Documentation
   
Extension Point Operator Sample

   This sample uses an Extension Point operator to dispatch tuples to one
   of two sub-modules, based on a user-specified action at runtime. The
   sub-modules simply double or triple an integer field within the
   incoming tuple. If no action is specified, the tuple is sent to both
   modules.
   
   This sample illustrates StreamBase interfaces used with the Extension
   Point operator, and illustrates the dispatch of tuples to one of two
   modules based on an expression that evaluates to the identifier of one
   of the modules. This sample also illustrates the use of a PNG image
   file to provide the Extension Point operator with a custom icon.
   
   There are two related samples that complete the picture on the subject
   of Extension Point operators and StreamBase interfaces:
   Sample Description
   [1]Interfaces Sample Demonstrates how to use StreamBase interfaces to
   enforce a set of stream and schema definitions, and to use different
   implementations of the same interface for different purposes.
   [2]Extension Point Deployment Sample This sample is a variation of the
   Extension Point operator sample to illustrate the use of StreamBase
   deployment files. The StreamBase interface in this sample specifies
   externally defined modules. Two example .sbdeploy files are provided,
   which you use to run this sample.
   
   First run and understand the current sample, then turn to the
   deployment file variation.
   
This Sample's Files

   The Extension Point sample is composed of following files:
   File Purpose
   ExtensionPoint.sbapp The top-level application for this sample. Its
   input stream accepts an integer value and a string field, action, that
   determines what to do with the integer. The recognized values for
   action are double, triple, and null.
   The following files are located in the Modules subdirectory of the
   operator project directory.
   Multiplier.sbint A StreamBase interface that defines two input streams
   and one output stream, and defines named schemas used by those
   streams.
   Doubler.sbapp A module that implements the streams and schemas defined
   in the Multiplier.sbint interface. This module doubles its input
   value, and appends as a list(string) the StreamBase path through this
   module.
   Tripler.sbapp Another module that implements the Multiplier.sbint
   interface. This module performs the same actions as Doubler.sbapp
   except that it triples its input value.
   
   Open ExtensionPoint.sbapp in Studio and look at the Properties view
   for the Extension Point operator named Multiply. The Concurrency tab
   is entirely responsible for directing input tuples to one or both of
   the referenced modules, either Doubler.sbapp or Tripler.sbapp:
     * If a tuple enters input port 2, the dispatch style is Broadcast,
       which means the tuple is sent to both modules.
     * If a tuple enters input port 1, the dispatch style is Name. In
       this case, the expression lower(action) must resolve to the
       identifier of one of the two modules ("double" or "triple") as
       defined in the Modules tab.
       
Running ExtensionPoint.sbapp in StreamBase Studio

    1. In the Package Explorer view, open the sample_operator project
       folder.
    2. Double-click to open the ExtensionPoint.sbapp application. Make
       sure the application is the currently active tab in the EventFlow
       Editor.
    3. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    4. In the Application Output view, select the Output output stream.
       No output is displayed at this point.
    5. In the Manual Input view, select the Input input stream
    6. Enter the following field values:
       
   Field Enter this value
   value 2
   action double
    7. Click Send Data, and a tuple arrives in the Application Output
       view. Select the new tuple and observe this data below the output
       stream table:
value=2, action=double, newValue=4, path=[Entering port 1 for Name dispatch,Dou
bler.sbapp]
    8. In the Manual Input view, enter the following field values:
       
   Field Enter this value
   value 2
   action triple
    9. Click Send Data, and a tuple arrives in the Application Output
       view. Select the new tuple and observe this data below the output
       stream table:
value=2, action=triple, newValue=6, path=[Entering port 1 for Name dispatch,Tri
pler.sbapp]
   10. In the Manual Input view, enter the following field values:
       
   Field Enter this value
   value 3
   action null
   11. Click Send Data, and two tuples arrive in the Application Output
       view. Select the new tuples and observe this data below the output
       stream table:
value=3, action=null, newValue=6, path=[Entering port 2 for Broadcast dispatch,
Doubler.sbapp]
value=3, action=null, newValue=9, path=[Entering port 2 for Broadcast dispatch,
Tripler.sbapp]
   12. When done, press F9 or click the Stop Running Application button.
       
Running ExtensionPoint.sbapp in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to your workspace
       copy of the sample, as described above.
    2. In window 1, start StreamBase Server running the Extension Point
       sample application. You must specify the provided server
       configuration file so that the application can locate the
       interface file and modules in the Modules folder. Enter:
sbd -f op-samples.sbconf ExtensionPoint.sbapp
    3. In window 2, type:
sbc deq Output
       No output is displayed at this point, but the dequeuer is prepared
       to receive output.
    4. In window 3, type:
sbc enq Input
       The sbc command in window 3 now waits for keyboard input. Type:
2, double
2, triple
3, null
       Look for output lines like the following in window 2:
2,double,4,"[Entering port 1 for Name dispatch,Doubler.sbapp]"
2,triple,6,"[Entering port 1 for Name dispatch,Tripler.sbapp]"
3,null,6,"[Entering port 2 for Broadcast dispatch,Doubler.sbapp]"
3,null,9,"[Entering port 2 for Broadcast dispatch,Tripler.sbapp]"
    5. In window 3, type: Ctrl+C to exit the sbc session.
    6. In window 3, type the following command to terminate the server
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



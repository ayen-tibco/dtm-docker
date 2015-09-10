
   StreamBase Documentation
   
Extension Point Deployment Sample

   This sample is a variation on the Extension Point Operator sample. For
   maximum benefit, first run and understand the Extension Point sample
   before working through this variation.
   
   The purpose of this variation is to illustrate the use of StreamBase
   deployment files to define the modules to be run by an Extension Point
   operator. The StreamBase interface in this sample specifies externally
   defined modules. Two example .sbdeploy deployment files are provided,
   which you use to run this sample.
   
Note

   Do not run this sample's EventFlow file, ExtPtDeployment.sbapp.
   Instead, run one or both of this sample's StreamBase deployment files
   as described in the steps below.
   
   This sample uses an Extension Point operator to dispatch tuples to two
   pairs of sub-modules, based on a user-specified action at runtime. The
   first pair of sub-modules either doubles or triples the integer field
   in the incoming tuple, depending on the specification in the action
   field of the incoming tuple. If the action field is null, the incoming
   tuple is sent to both sub-modules. You run ExtPtDeployment1.sbdeploy
   to run this sample with the first pair of sub-modules.
   
   The second pair of sub-modules have the same relationship to the
   top-level module, but they triple or quadruple the incoming tuple's
   integer field (instead of double or triple). Run
   ExtPtDeployment2.sbdeploy to run this sample with the second pair of
   sub-modules.
   
   This sample illustrates several aspects of Extension Point operators
   and StreamBase interfaces. There are two related samples that complete
   the picture on these subjects:
   Sample Description
   [1]Interfaces Sample Demonstrates how to use StreamBase interfaces to
   enforce a set of stream and schema definitions, and to use different
   implementations of the same interface for different purposes.
   [2]Extension Point Operator Sample This sample uses an Extension Point
   operator to dispatch tuples based on a user-specified action to one of
   two modules, which double or triple an integer field within the tuple.
   If no action is specified, the tuple is sent to both modules. This
   sample illustrates StreamBase interfaces used with the Extension Point
   operator, and illustrates [3]module dispatch based on a predicate
   condition.
   
This Sample's Files

   The Extension Point Deployment sample is composed of following files:
   File Purpose
   ExtPtDeployment.sbapp The top-level application for this sample. To
   run this sample, DO NOT RUN this EventFlow module. Instead, run one of
   the two StreamBase deployment files described next.
   ExtPtDeployment1.sbdeploy A StreamBase deployment file that specifies
   running either Doubler.sbapp or Tripler.sbapp as modules that
   implement the Multiplier interface.
   ExtPtDeployment2.sbdeploy A StreamBase deployment file that specifies
   running either Tripler.sbapp or Quadrupler.sbapp as modules that
   implement the Multiplier interface.
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
   Quadrupler.sbapp Yet another implementation of the Multiplier.sbint
   interface. This module quadruples its input value.
   
   Open ExtPtDeployment.sbapp in Studio and look at the Properties view
   for the Extension Point operator named Multiply. The Concurrency tab
   is entirely responsible for directing input tuples to one or both of
   the referenced modules.
     * If a tuple enters input port 2, the dispatch style is Broadcast,
       which means the tuple is sent to both modules.
     * If a tuple enters input port 1, the dispatch style is Name. In
       this case, the expression lower(action) must resolve to the
       identifier of one of the modules defined in the StreamBase
       deployment file.
       
   Unlike the Extension Point sample, the ExtPtDeployment.sbapp module
   specifies Defined: Externally in the Modules tab of the Multiply
   interface's Properties view, and defines an Extension Point ID of
   extpt.deployment.multiplier. The Extension Point ID is what links the
   EventFlow module to one or more StreamBase deployment files. To be
   able run this EventFlow application, a deployment file must specify:
     * This EventFlow module's name in the module attribute of the
       <application> element.
     * The same Extension Point ID in the target-id attribute of an
       <extension-point> element as specified in the EventFlow module.
       
   The following fragment of this sample's deployment files shows the
   required lines:
...
<runtime>
  <application container="default" module="ExtPtDeployment.sbapp">
    <extension-point-contents>
      <extension-point target-id="extpt.deployment.multiplier">
...

Running This Sample in StreamBase Studio

    1. In the Package Explorer, open the sample_operator project folder.
    2. Double-click to open the ExtPtDeploymnent1.sbdeploy deployment
       file. Make sure the Deployment File Editor is the currently active
       tab in Studio.
    3. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application module named in the deployment file.
    4. In the Application Output view, select the Output output stream.
       No output is displayed at this point.
    5. In the Manual Input view, select the Input input stream.
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
   12. Press F9 or click the Stop Running Application button to stop the
       first deployment application.
   13. Double-click to open the ExtPtDeploymnent2.sbdeploy deployment
       file, and make sure the Deployment File Editor is the currently
       active tab in Studio.
   14. Click the Run button to run this second deployment file. As
       before, this opens the SB Test/Debug perspective and starts the
       application.
   15. Enter input tuples in the Manual Input view using steps similar to
       steps 6 through 11, but this time use action values triple,
       quadruple, and null. Compare the results with running the first
       deployment file.
   16. When done, press F9 or click the Stop Running Application button
       to stop the second deployment application.
       
Running This Sample in Terminal Windows

   This section describes how to run the sample in UNIX terminal windows
   or Windows command prompt windows. On Windows, be sure to use the
   StreamBase Command Prompt from the Start menu as described in the
   Test/Debug Guide, not the default command prompt.
    1. Open three terminal windows on UNIX, or three StreamBase Command
       Prompts on Windows. In each window, navigate to your workspace
       copy of the sample, as described above.
    2. In window 1, start StreamBase Server running the first deployment
       file. You must specify the provided server configuration file so
       that the application can locate the provided interface and module
       files in the Modules folder. Enter:
sbd -f op-samples.sbconf ExtPtDeployment1.sbdeploy
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
       and dequeuer: sbadmin shutdown
    7. Now, in window 1, run the second deployment file:
sbd -f op-samples.sbconf ExtPtDeployment2.sbdeploy
    8. In window 2, type:
sbc deq Output
       No output is displayed at this point, but the dequeuer is prepared
       to receive output. This window will eventually show the output of
       the application.
    9. In window 3, type:
sbc enq Input
       The sbc command in window 3 now waits for keyboard input. Type:
2, triple
2, quadruple
3, null
       Look for output lines like the following in window 2:
2,triple,6,"[Entering port 1 for Name dispatch,Tripler.sbapp]"
2,quadruple,8,"[Entering port 1 for Name dispatch,Quadrupler.sbapp]"
3,null,9,"[Entering port 2 for Broadcast dispatch,Tripler.sbapp]"
3,null,12,"[Entering port 2 for Broadcast dispatch,Quadrupler.sbapp]"
   10. In window 3, type: Ctrl+C to exit the sbc session.
   11. In window 3, type the following command to terminate the server
       and dequeuer: sbadmin shutdown
       
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



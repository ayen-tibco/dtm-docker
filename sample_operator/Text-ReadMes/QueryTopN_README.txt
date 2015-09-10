
   StreamBase Documentation
   
Query TopN Sample

   This topic describes how to run the Query TopN sample application. For
   detailed information about this and other samples, see the Samples
   Guide in the StreamBase Studio Help.
   
   Suppose you want to extract a range of values from a query table, such
   as the ten highest or lowest stocks or best- or worst-selling items in
   an inventory. One method is to use a Query operator to repeatedly read
   all the rows in the table, and then use a series of other operators to
   process the result, compare prices across stock symbols or products to
   find the top n. Conversely, you could pre-process tuples upstream from
   a query table that has just one row containing n fields. Each time a
   tuple arrives on the input stream, compare values to see if the table
   needs to be updated.
   
   Both of these options require complicated processing to compare the
   values and continually calculate the top n values. The QueryTopN
   sample application demonstrates a simpler method, using the Query
   operator's built-in option to limit the number of output rows and
   b-tree indexing.
   
   The sample has one input stream, in which you enter the value of n as
   the int field howMany. The table is populated from a CSV data file
   containing randomly generated values, but in a real application data
   from an input stream or another module would be updating the table
   dynamically.
   
   The table is indexed by field value in descending order, so that the
   first n values of the index are always the largest ones. Sending a
   tuple from the enterN input stream triggers a read operation that
   outputs just the current top n highest values by setting the Limit
   field in the Query operator to the input value howMany.
   
   The tuples output from the table are split into two streams and
   processed by an Aggregate operator and a Map operator, respectively.
   The aggregate operator uses aggregatelist(tuple(...)) in a predicate
   dimension to generate a list of the top n tuples. The dimension just
   has a Close expression, count()=howMany, to do this. The Map operator
   restores the original field names and drops input field howMany to
   output n individual tuples on the lower stream.
   
Running QueryTopN.sbapp in StreamBase Studio

    1. In Package Explorer, load the sample_operator project if it is not
       present and expand it. Find and double-click the QueryTopN.sbapp
       application to open it. Make sure the application is the currently
       active tab in the EventFlow Editor. Notes on the canvas explain
       how the application works.
    2. Click the Run button. This opens the SB Test/Debug perspective and
       starts the application.
    3. In the Application Output view, make sure that All streams is
       selected in the Output stream control.
    4. Enter 1 for howMany (the number of values you want to output) in
       in the Manual Input view and click Send Data.
    5. Observe the output streams in the Application Output view. Note
       that:
       
         a. The topNtuples stream contains one tuple having fields value
            and symbol. It is the highest value in the table.
         b. The topNlist stream contains one tuple, a list containing the
            above tuple.
            
    6. Repeat steps 4 and 5, increasing howMany to 2, 3, ..., to see the
       set of top n values grow.
    7. When done, press F9 or click the Stop Running Application button.
       
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

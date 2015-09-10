#!/bin/bash
cd $CONFIGDIR/dtmhelloworld
# Install node A in cluster X - administration port set to 5556
administrator install node adminport=5556 nodename=$HOSTNAME.$1 deploydirectories=${2}/lib:/opt/tibco/cfgmgt/sample_operator:/opt/tibco/cfgmgt/sample_operator/java-bin buildtype=PRODUCTION

#Start the node using the assigned administration port
administrator adminport=5556 start node

#Deploy an .sbapp "detached" mode - the configuration parameter is optional
java -jar $SW_HOME/distrib/tibco/uepp/java/deploy.jar adminport=5556 detach=false /opt/tibco/cfgmgt/sample_operator/Map.sbapp configuration=/opt/tibco/cfgmgt/sample_operator/sbd.sbconf

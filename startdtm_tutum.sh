#!/bin/bash
# Docker Container Script to startup DTM/SB8
# Provides pre-flight node directory deployment cleanup before and after shutdown.
# Assumes ENV variables STREAMBASE_HOME, JAVA_HOME, ans SW_HOME set in container already.
# When stopping container, give -t 30 grace timeout before killing the entire container
# Example: 
#
#  docker run -d --name node1 --hostname=node1 -P docker.tibco.com/ep/dtm_sb_helloworld:rhel7latest
#  docker run -d --name node2 --hostname=node2 -P docker.tibco.com/ep/dtm_sb_helloworld:rhel7latest
#  ./update-docker-dns.sh
#
#  docker stop -t 30 node1
#  docker start node1
#
set -x
NODE_INSTALL_PATH=/opt/tibco/deploy/nodes
export _JAVA_OPTIONS=-DSW_HOME=$SW_HOME

if [ ! -z "$1" ]; then
  ClusterName=$1
else
  ClusterName=Cluster1
fi

if [ ! -z "$2" ]; then
  APPLIB_PATH=$2
else
  APPLIB_PATH=$STREAMBASE_HOME/lib
fi

if [ ! -d "$NODE_INSTALL_PATH" ]; then
	mkdir -p $NODE_INSTALL_PATH
else
	#clean host config directories in case there was a abrupt shutdown
	administrator remove node installpath=$NODE_INSTALL_PATH/$HOSTNAME.$1
	rm -rf $NODE_INSTALL_PATH/*
fi

sleep 5
# use weave ip address to do discovery
WEAVE_HOSTNAME=`echo $TUTUM_IP_ADDRESS | sed 's/\/16//'`

if [ -z "$WEAVE_HOSTNAME" ]; then
	DISCOVERYHOSTS=
else
	DISCOVERYHOSTS=discoveryhosts=$WEAVE_HOSTNAME
fi

#Set eth0 and Weave network broadcast address to subnet only 
#must run container in privilege mode to execute command
#ifconfig eth0 broadcast 172.17.255.255
#ifconfig ethwe broadcast 10.7.255.255

# Install node A in cluster X - administration port set to 5556
administrator install node $DISCOVERYHOSTS installpath=$NODE_INSTALL_PATH/$HOSTNAME.$ClusterName adminport=5556 nodename=$HOSTNAME.$ClusterName deploydirectories=$APPLIB_PATH:$SW_HOME/operator:$SW_HOME/operator/java-bin buildtype=PRODUCTION

#Start the node using the assigned administration port
administrator adminport=5556 start node

# throwing in trap/wait to capture docker stop SIGTERM/INT signals
trap 'kill -TERM $PID' TERM INT
#Deploy an .sbapp "detached" mode - the configuration parameter is optional
java -jar $SW_HOME/distrib/kabira/java/deploy.jar adminport=5556 detach=false detachtimeout=300 $SW_HOME/operator/Map.sbapp configuration=$SW_HOME/operator/map_operator.sbconf &
PID=$!
wait $PID
trap - TERM INT
wait $PID
EXIT_STATUS=$?

# Cleanup: Stop DTM and Remove DTM after - need at least 30 seconds before container exits entirely
administrator adminport=5556 stop node
administrator remove node installpath=$NODE_INSTALL_PATH/$HOSTNAME.$ClusterName

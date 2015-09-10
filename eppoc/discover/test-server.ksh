#!/bin/ksh -p
#
#	$Revision: 1.6 $ $Date: 2011/07/28 19:51:21 $
#
#	default test server init script for maven projects
#

. $SW_HOME/distrib/kabira/ast/include/test-server.env

Pwd="$(cd "$(dirname "$0")" && pwd)"

InstallPath=$Pwd/BUILD/run
ProductName=hydra
BuildType=PRODUCTION

Application=kabira/ast

Node[0]="SSB1SWITCH memorysize=512"
Node[1]="SSB2SWITCH memorysize=512"
Node[2]="DAL1SWITCH memorysize=512"
Node[3]="DAL2SWITCH memorysize=512"
Node[4]="COL1SWITCH memorysize=512"
Node[5]="COL2SWITCH memorysize=512"

function DisableDatagrid
{
	print "Disabling data grid"

	typeset kdmconfig=$Run/configuration/domainmanager/node/kabira/kdm

	sed -e "/dataGrid =/,/};/s+^+\/\/+" $kdmconfig/90-applicationconfig/kdm.kcs \
		> $kdmconfig/90-applicationconfig/kdm.kcs.tmp

	rm $kdmconfig/90-applicationconfig/kdm.kcs
	mv $kdmconfig/90-applicationconfig/kdm.kcs.tmp $kdmconfig/90-applicationconfig/kdm.kcs
}

if [ "$1" == "install" -o "$1" == "start" ]
then
	set -e

	ParseArgs "$@"

	Initialize
	Install  

	DisableDatagrid

	Start

	list=""
	for nodespec in "${Node[@]}"
	do
		typeset nodename=${nodespec%% *}
		if [ "$list" = "" ]
		then
			list="$nodename"
		else
			list="$list,$nodename"
		fi
	done

	mkdir -p target
	print -- "-Dcom.kabira.fluency.administrationPort=$domainmanager_adminport" \
		"-Dcom.kabira.fluency.nodelist=$list" \
		> target/server.options

	return 0
elif [ "$1" == "stop" ]
then
	Initialize
	Stop
elif [ "$1" == "remove" ]
then
	Initialize
	Remove
else
	InitMain "$@"
	return 0
fi

# This Dockerfile version utilizes StreamBase UEP Silver Fabric gridlib and supports Tutum/Docker Hub/GitHub CI.
#
# Requirements:
#  - SB+DTM binary gridlib
#  - Maven build of DTM in Jenkins workspace
#  - Git repo checkout of eppoc (helloworld), SB map operator sample
#  - JDK (must be native centos since jenkins jdk version does not work on boot2docker Mac OS X)
# Centos 7 is required since RHEL7 is not the default OS for the Docker Toolbox VM and requires RH subscription
FROM centos:7

MAINTAINER Adam Yen <ayen@tibco.com>

# Locations for SB/DTM and Samples
ENV TIBCO_INSTALL_DIR=/opt/tibco
ENV DTMHOSTNAME=dtm1
ENV SB_CEP_VERSION=7.7
ENV STREAMBASE_HOME=$TIBCO_INSTALL_DIR/sb-cep/$SB_CEP_VERSION
ENV SW_HOME=$STREAMBASE_HOME/dtm
ENV SB_CEP_SF_TARBALL=streambase-sf-distribution-7.7.0uep1_227593.linux.x86_64.tar.gz
ENV SB_CEP_SF_GRIDLIB=tibco/sb-cep/7.7/gridlib/TIB_sb-cep_7.7.0_runtime_gridlib_linux64.tar.gz

# Update image and Add supporting Centos packages
RUN yum update -y && \
 yum -y install wget tar git java-1.8.0-openjdk java-1.8.0-openjdk-devel bind-utils tcpdump net-tools hostname && \
 yum clean all

ENV JAVA_HOME=/etc/alternatives/java_sdk_1.8.0

RUN \
  cd /opt && \
  wget http://download.nextag.com/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz && \
  tar -xzvf ./apache-maven-3.3.3-bin.tar.gz -C /opt && \
  ln -s /opt/apache-maven-3.3.3 /opt/maven && \
  mkdir /root/.m2
ENV MAVEN_HOME=/opt/maven

# use non-root if required - otherwise everything will be installed and run as root
# create tibco user
# RUN useradd -c "TIBCO user" -m -p tibco123 -g users -s /bin/bash tibco
# USER tibco

# Download and extract SB-LDM SF gridlib distro package
# need to parametize or have jenkins pass in version number
COPY $SB_CEP_SF_TARBALL $TIBCO_INSTALL_DIR/
RUN \
  cd $TIBCO_INSTALL_DIR && \
  tar -xzvf $SB_CEP_SF_TARBALL -C /opt && \
  tar -xzvf /opt/$SB_CEP_SF_GRIDLIB -C / && \
  chown -R sbuser:sbusers $TIBCO_INSTALL_DIR && \
  rm $SB_CEP_SF_TARBALL /opt/$SB_CEP_SF_GRIDLIB

# Download SB Map Operator Sample for hello world testing
# Can be from the public cloud, for automated build testing, pull from local host or SB svn repo
#RUN \
#  mkdir -p $CONFIGDIR && \
#  cd $CONFIGDIR && \
#  wget https://ldm-sb.s3.amazonaws.com/sample_operator.tar && \
#  tar -xvf sample_operator.tar
COPY operator $SW_HOME/operator

# Add detachtimeout in sbuser ast option file so maven build won't error when starting up cluster nodes on slow machines
COPY sbuser_dtm_options /home/sbuser/.ast/options
RUN chown -R sbuser:sbusers $SW_HOME && \
 chown -R sbuser:sbusers /home/sbuser/.ast

USER sbuser

ENV PATH=$SW_HOME/distrib/kabira/bin:$PATH

# expose network ports per AST 2.5.1 adminguide.pdf
# still require -p in docker run command to map external to these internal ports

# expose internal admin port
EXPOSE 5556/tcp

# expose internal Distribution Transport
EXPOSE 5557/tcp

# expose internal jmx admin port
EXPOSE 5558/tcp

# expose DTM discover service port
EXPOSE 54321/udp

# expose SB default Server  port
EXPOSE 10000

# need to copy initialization/startup script since it's not provided in the DTM build
COPY startdtm_tutum.sh $SW_HOME/distrib/kabira/bin/

# exec first node - you only get to exec once in docker thus the need for a custom initialization script
ENTRYPOINT ["/opt/tibco/dtm/distrib/kabira/bin/startdtm_tutum.sh", "Cluster1"]

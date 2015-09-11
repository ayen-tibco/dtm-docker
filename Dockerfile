# If you loaded redhat-rhel-server-7.0-x86_64 to your local registry, uncomment this FROM line instead:
# FROM registry.access.redhat.com/rhel 
# Pull the rhel image from the local repository
# Build DTM from rhel6 latest
FROM registry.access.redhat.com/rhel6

MAINTAINER Adam Yen

# Location for DTM and SB Hello World Samples
ENV CONFIGDIR=/opt/tibco/cfgmgt

ENV DTMHOSTNAME=dtm1


# Update image
RUN yum update -y

# Add supporting RHE packages
RUN yum -y install wget tar git

# Add supporting external packages
RUN \
  cd /opt && \
  wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.tar.gz" && \
  tar xzf jdk-8u60-linux-x64.tar.gz && \
  alternatives --install /usr/bin/java java /opt/jdk1.8.0_60/bin/java 1 && \
  alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_60/bin/jar 1 && \
  alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_60/bin/javac 1 && \
  alternatives --set jar /opt/jdk1.8.0_60/bin/jar && \
  alternatives --set javac /opt/jdk1.8.0_60/bin/javac
ENV JAVA_HOME=/opt/jdk1.8.0_60

RUN \
  cd /opt && \
  wget http://download.nextag.com/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz && \
  tar -xzvf ./apache-maven-3.3.3-bin.tar.gz -C /opt && \
  ln -s /opt/apache-maven-3.3.3 /opt/maven && \
  mkdir /root/.m2
ENV MAVEN_HOME=/opt/maven

# copy the global xml that points to the DTM Maven serverl
COPY settings.xml /root/.m2/

# use non-root if required - otherwise everything will be installed and run as root
# create tibco user
# RUN useradd -c "TIBCO user" -m -p tibco123 -g users -s /bin/bash tibco
# USER tibco

# Download and extract SB-LDM SF gridlib distro package
RUN \
  cd /opt && \
  wget https://hermes.streambase.com/dist/official/7.5.4.3/linux64/streambase-sf-distribution-7.5.4.3.linux.x86_64.tar.gz && \
  tar -xzvf streambase-sf-distribution-7.5.4.3.linux.x86_64.tar.gz -C /opt && \
  tar -xzvf /opt/tibco/sb-cep/7.5/gridlib/TIB_sb-cep_7.5.4_runtime_gridlib_linux64.tar.gz -C /
ENV STREAMBASE_HOME=/opt/tibco/sb-cep/7.5

# Download SB Map Operator Sample for hello world testing
# Can be from the public cloud, for automated build testing, pull from local host or SB svn repo
#RUN \
#  mkdir -p $CONFIGDIR && \
#  cd $CONFIGDIR && \
#  wget https://ldm-sb.s3.amazonaws.com/sample_operator.tar && \
#  tar -xvf sample_operator.tar
COPY sample_operator.tar $CONFIGDIR/
RUN tar -xvf $CONFIGDIR/sample_operator.tar -C $CONFIGDIR

# Copy local host git clone of DTM Hello World into container
# The pom.xml should contain fragment and configuration entries pointing to $CONFIGDIR/sample_operator/Map.sbapp and $CONFIGDIR/sample_operator/sbd.sbconf
# For automated builds, a read-only git username without password would be the way to go
# RUN \ 
#  cd $CONFIGDIR
#  git clone http://${BUILDER_USERNAME}@git.tibco.com/git/product/ep/eppoc.git
COPY eppoc $CONFIGDIR/

# Build DTM in current DTM Hello World example
ENV SW_HOME=/opt/tibco/dtm
RUN mkdir -p $SW_HOME
RUN cd $CONFIGDIR/dtmhelloworld && /opt/maven/bin/mvn -U install

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
COPY startdtm.sh $SW_HOME/distrib/kabira/bin/

# exec first node - you only get to exec once in docker thus the need for a custom initialization script
ENTRYPOINT ["/opt/tibco/dtm/distrib/kabira/bin/startdtm.sh", "Cluster1", "/opt/tibco/sb-cep/7.5"]

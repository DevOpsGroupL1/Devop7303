#!/bin/bash
#
# We configure the distro, here before it gets imported into docker
# to reduce the number of UFS layers that are needed for the Docker container.
#

#Change OS time zone
#mv -f /etc/localtime /etc/localtime.bak
#ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime

#download keystore from repo
set -e
#cd /opt/$VAR_SERVICE_NAME/config/


cd /opt/$VAR_SERVICE_NAME/

java -jar devop7303.jar
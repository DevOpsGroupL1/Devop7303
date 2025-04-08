#!/bin/bash

set -e
mkdir -p /opt/$VAR_SERVICE_NAME
#mkdir -p /opt/$VAR_SERVICE_NAME/config/
mkdir -p /opt/$VAR_SERVICE_NAME/log/
ln -s /opt/$VAR_SERVICE_NAME/log/logs
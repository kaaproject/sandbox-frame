#!/bin/bash
#
#  Copyright 2014-2016 CyberVision, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#


CONF_PATH="/usr/lib/kaa-sandbox/conf"

newHost=$1

function setNewHost() {

    propFile=$1
    property=$2

    sed -i "s/\(${property}=\).*\$/\1${newHost}/" ${propFile}

}

echo "Stopping services..."

service kaa-node stop

echo "Services stopped!"

echo "Setting services host/ip to ${newHost}"

setNewHost /etc/kaa-node/conf/kaa-node.properties transport_public_interface

touch $CONF_PATH/ip_manually_changed

echo "Starting services..."

service kaa-node start

echo "Waiting 10 sec for services startup..."

sleep 10

echo "Done!"

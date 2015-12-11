#!/bin/bash

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

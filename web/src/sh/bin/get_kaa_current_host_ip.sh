#!/bin/bash

function getHost() {

    propFile=$1
    property=$2

    for i in $(grep -i "${property}" ${propFile} | cut -d= -f2)
    do
       echo ${i}
    done
}

getHost /etc/kaa-node/conf/kaa-node.properties transport_public_interface

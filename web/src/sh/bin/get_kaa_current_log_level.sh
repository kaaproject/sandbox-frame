#!/bin/bash

function getLogLevel() {

    file=$1

    sed -n "s/\(<[^\"]*\"org.kaaproject.kaa.server\" level=\"\)\([^<]*\)\(\"\/>\)/\2/p" ${file}
}

getLogLevel /usr/lib/kaa-node/conf/logback.xml

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


log_level=$1
remove_logs=$2

function changeLogLevel() {

    sed -i "s/\(<[^\"]*\"org.kaaproject.kaa.server\" level=\"\)\([^<]*\)\(\"\/>\)/\1${log_level}\3/g" /usr/lib/kaa-node/conf/logback.xml

}

function getCurrentLogLevel() {

    var=$(sed -n 's/\(<[^\"]*\"org.kaaproject.kaa.server\" level=\"\)\([^<]*\)\(\"\/>\)/\2/p' /usr/lib/kaa-node/conf/logback.xml)
    echo ${var}

}

if [[ $(getCurrentLogLevel) != *${log_level}* ]]

then

    echo "Stopping services..."

    service kaa-node stop

    echo "Services stopped!"

    if [ ${remove_logs} -eq 1 ]
    then

        echo "Removing old log files..."

        rm -rf /var/log/kaa/*

        echo "Old log files removed!"

    fi

    echo "Setting log-level to ${log_level}"

    changeLogLevel

    echo "Starting services..."

    service kaa-node start

    echo "Waiting 10 sec for services startup..."

    sleep 10

fi

echo "Done!"

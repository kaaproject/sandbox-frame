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


function get_ip() {
    ip addr | grep 'inet ' | grep -v -E " lo" | awk '{ print $2 }' | awk -F'/' '{print $1}'
}

HOST=$(get_ip)
NUM=5
while [ -z "$HOST" ]; do
    HOST=$(get_ip)
    sleep 5
    NUM=$(($NUM-1))
    if [ $NUM -le 0 ]; then
        HOST="127.0.0.1"
        break
    fi
done

echo $HOST

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


SCRIPT=$(readlink -f "$0")
echo "# File is generated from ${SCRIPT}" > /etc/hosts
echo "# Do not remove the following line, or various programs" >> /etc/hosts
echo "# that require network functionality will fail." >> /etc/hosts
echo "127.0.0.1		localhost.localdomain localhost" >> /etc/hosts
SCRIPTS_PATH="/usr/lib/kaa-sandbox/bin"
CONF_PATH="/usr/lib/kaa-sandbox/conf"

HOST="$( $SCRIPTS_PATH/get_ip.sh )"

if [ "$HOST" == "127.0.0.1" ]; then
		echo "Failed to update IP"
fi

echo "$HOST	`hostname` kaa-sandbox" >> /etc/hosts

platform=
if [ -f /virt_env ]; then
    platform="$(cat /virt_env)"
fi

if [ "$platform" == "vbox" ] && \
   [ "$HOST" != "127.0.0.1" ] && \
   [ "$HOST" != "10.0.2.15" ] && \
   [ ! -f $CONF_PATH/ip_manually_changed ]; then
       sed -i "s/\(transport_public_interface=\).*\$/\1${HOST}/" /etc/kaa-node/conf/kaa-node.properties
       sed -i "s/\(gui_show_change_host_dialog=\).*\$/\1false/" $CONF_PATH/sandbox-server.properties
fi

echo 0 > /proc/sys/kernel/hung_task_timeout_secs

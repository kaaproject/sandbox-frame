#!/bin/bash

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

#!/bin/bash

# script starts synchronously on tty1

if [ -f /usr/lib/kaa-sandbox/bin/sandbox-splash.py ]; then
    python /usr/lib/kaa-sandbox/bin/sandbox-splash.py
fi

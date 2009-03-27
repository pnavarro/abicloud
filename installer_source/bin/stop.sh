#!/bin/bash

tomcat/bin/shutdown.sh
sleep 5

if [ -d mysql/ ];then
    kill -15  `cat mysql/data/*.pid`
    sleep 2
fi

exit 0

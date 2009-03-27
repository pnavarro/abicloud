#!/bin/bash

if [ -d mysql/ ];then
    echo -n  "Loading mysql... "
    cd mysql
    ./bin/mysqld_safe --defaults-file=my.cnf &>/dev/null &
    sleep 2
    echo " done"
    cd ..
fi

echo -n "Loading tomcat... "
cd tomcat/
bin/startup.sh &>/dev/null

sleep 5
echo "done"

exit 1

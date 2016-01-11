#!/bin/bash
curDir="$(pwd)"
scriptDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $scriptDir

cpth="Tern.jar"
cpth=$cpth":bluecove-2.1.1-SNAPSHOT-63.jar"
cptp=$cpth":bluecove-gpl-2.1.1-SNAPSHOT-63-arm.jar"
cpth=$cpth":bluecove-bluez-2.1.1-SNAPSHOT.jar"
cpth=$cpth":ev3j-0.1.1.jar"

echo "Starting Tern"
echo "Folder $scriptDir"
echo "Using classpath $cpth"

java -cp "$cpth" tern.Main

cd $curDir

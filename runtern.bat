@echo off
set curDir=%cd%
set scriptDir="%~dp0"
pushd %scriptDir%

set cpth=Tern.jar
set cpth=%cpth%;bluecove-2.1.1-SNAPSHOT-63.jar
set cpth=%cpth%;ev3j-0.1.1.jar

echo Starting Tern
echo Folder %scriptDir%
echo Using classpath %cpth%

java -cp %cpth% tern.Main

popd

pause
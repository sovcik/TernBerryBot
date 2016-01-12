@echo off
set curDir=%cd%
set scriptDir="%~dp0"
pushd %scriptDir%

set cpth=Tern.jar;lib\*

echo Starting Tern
echo Folder %scriptDir%
echo Using classpath %cpth%

java -cp %cpth% tern.Main

popd

pause
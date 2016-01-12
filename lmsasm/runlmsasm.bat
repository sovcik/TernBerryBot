@echo off
rem This script is helping Tern to execute assembler located in subfolder lmsasm.
rem Assembler (including all its files) should be otherwise put in the root folder,
rem which would not be nice...

rem Get current directory
set curDir=%cd%

rem Get directory in which this script is stored
set scriptDir="%~dp0"

pushd %scriptDir%

java -jar assembler.jar %curDir%\%1

popd
rem This script is helping Tern to execute assembler located in subfolder lmsasm.
rem Assembler (including all its files) should be otherwise put in the root folder,
rem which would not be nice...
set curDir=%cd%
set scriptDir="%~dp0"
pushd %scriptDir%
java -jar assembler.jar %curDir%\%1
popd
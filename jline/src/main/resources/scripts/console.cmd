@echo on

title Console

set SCRIPTPATH=%~dp0

"%JAVA_HOME%\bin\java" -Xms128m -Xmx128m -cp "%~dp0lib\*" com.blogspot.pdrobushevich.jline.Main %1
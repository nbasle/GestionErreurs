@echo off

set JAVA=%JAVA_HOME%\bin\java
set CLASS_DIR=..\classes

set CLASSPATH=%CLASS_DIR%

%JAVA% -cp %CLASSPATH% com.yaps.petstore.Menu
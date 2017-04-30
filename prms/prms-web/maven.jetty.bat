@echo off

cd /d %~dp0

set MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Xms128m -Xmx384m -XX:MaxPermSize=128m -noverify

call mvn jetty:run -U
if %errorlevel% == 0 (echo. & echo [Building] Successful. & echo. & goto :eof)
if %errorlevel% == 1 (echo. & echo [Building] Failed. & echo. & pause)
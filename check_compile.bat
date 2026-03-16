@echo off
echo Compiling project to check for errors...
echo.

cd /d "c:\Users\DELL\Downloads\sargsoftechbackend"

echo Running Maven compile...
mvn clean compile 2>&1

echo.
echo.
echo If there are errors above, please share them.
echo.
pause

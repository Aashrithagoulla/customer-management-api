@echo off
REM Build the client application
echo Building client application...
cd /d "%~dp0"
call mvn clean package

REM Check if build was successful
if %ERRORLEVEL% == 0 (
    echo.
    echo Build successful!
    echo Running client application...
    echo.
    
    REM Run the client application with dependencies
    java -jar target\customer-api-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar
) else (
    echo Build failed! Please check the error messages above.
)

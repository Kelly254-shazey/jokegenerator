@echo off
echo.
echo ========================================
echo    🧠 BrainBreak - Startup Script 🎮
echo ========================================
echo.

echo Starting BrainBreak application...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js is not installed or not in PATH
    echo Please install Node.js 16 or higher
    pause
    exit /b 1
)

echo ✅ Java and Node.js are available
echo.

REM Start backend in a new window
echo 🚀 Starting Backend Server...
start "BrainBreak Backend" cmd /k "cd /d \"%~dp0backend\" && mvn spring-boot:run"

REM Wait a moment for backend to start
timeout /t 5 /nobreak >nul

REM Start frontend in a new window
echo 🌐 Starting Frontend Server...
start "BrainBreak Frontend" cmd /k "cd /d \"%~dp0frontend\" && npm start"

echo.
echo ========================================
echo    🎉 BrainBreak is Starting Up! 🎉
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo.
echo The application will open automatically in your browser.
echo.
echo To stop the servers:
echo - Close the Backend and Frontend command windows
echo - Or press Ctrl+C in each window
echo.
echo ========================================
echo    Enjoy your BrainBreak experience! 😄
echo ========================================
echo.

pause
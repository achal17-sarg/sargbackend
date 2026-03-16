@echo off
echo ============================================
echo VERIFY YOUR JOURNEY ENQUIRY (ID: 23)
echo ============================================
echo.

echo 1. Check Journey enquiries at CORRECT admin endpoint:
echo URL: http://localhost:8090/sargweb/api/journey/all
echo.
curl -X GET "http://localhost:8090/sargweb/api/journey/all"
echo.
echo.

echo 2. Check specific Journey enquiry by ID:
echo URL: http://localhost:8090/sargweb/api/journey/23
echo.
curl -X GET "http://localhost:8090/sargweb/api/journey/23"
echo.
echo.

echo 3. Check Journey count:
echo URL: http://localhost:8090/sargweb/api/journey/count
echo.
curl -X GET "http://localhost:8090/sargweb/api/journey/count"
echo.
echo.

echo 4. Check new Journey enquiries:
echo URL: http://localhost:8090/sargweb/api/journey/new-enquiries
echo.
curl -X GET "http://localhost:8090/sargweb/api/journey/new-enquiries"
echo.
echo.

echo ============================================
echo If you see your enquiry above, then:
echo - Your API is working correctly
echo - Update your admin panel to call:
echo   /sargweb/api/journey/all (for Journey)
echo   NOT /sargweb/api/contact/all (for Contact)
echo ============================================

pause

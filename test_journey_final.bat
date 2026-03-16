@echo off
echo ============================================
echo TESTING ALL JOURNEY ENDPOINTS
echo ============================================
echo.

echo TEST 1: Submit via website (/api/journey/submit)
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Test User\",\"emailAddress\":\"test@example.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build website\"}"
echo.
echo.

echo TEST 2: Get all via website (/api/journey/all)
curl -X GET "http://localhost:8090/api/journey/all"
echo.
echo.

echo TEST 3: Get count via website (/api/journey/count)
curl -X GET "http://localhost:8090/api/journey/count"
echo.
echo.

echo TEST 4: Get all via admin (sargweb/api/journey/all)
curl -X GET "http://localhost:8090/sargweb/api/journey/all"
echo.
echo.

echo TEST 5: Get new enquiries via admin (sargweb/api/journey/new-enquiries)
curl -X GET "http://localhost:8090/sargweb/api/journey/new-enquiries"
echo.
echo.

echo TEST 6: Get count via admin (sargweb/api/journey/count)
curl -X GET "http://localhost:8090/sargweb/api/journey/count"
echo.
echo.

pause

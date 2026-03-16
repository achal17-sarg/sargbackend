@echo off
echo Testing Journey Enquiries...
echo.

echo 1. Submit journey enquiry (website path):
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Test Journey User\",\"emailAddress\":\"journey@test.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build a website\"}"
echo.
echo.

echo 2. Get all journey enquiries (admin path):
curl -X GET "http://localhost:8090/sargweb/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 3. Get journey count:
curl -X GET "http://localhost:8090/sargweb/api/journey/count" -H "Content-Type: application/json"
echo.

pause

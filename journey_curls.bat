@echo off
echo ============================================
echo JOURNEY CONTROLLER - ALL CURL COMMANDS
echo Base Path: sargweb/api/journey
echo ============================================
echo.

echo 1. POST - Submit Journey Enquiry
echo URL: http://localhost:8090/sargweb/api/journey/submit
echo --------------------------------------------------
curl -X POST "http://localhost:8090/sargweb/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"John Doe\",\"emailAddress\":\"john@example.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build an e-commerce website with payment integration\"}"
echo.
echo.

echo 2. GET - Get All Journey Enquiries
echo URL: http://localhost:8090/sargweb/api/journey/all
echo --------------------------------------------------
curl -X GET "http://localhost:8090/sargweb/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 3. GET - Get Journey Enquiry by ID (Replace {id} with actual ID)
echo URL: http://localhost:8090/sargweb/api/journey/1
echo --------------------------------------------------
curl -X GET "http://localhost:8090/sargweb/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo 4. GET - Get Journey Enquiry Count
echo URL: http://localhost:8090/sargweb/api/journey/count
echo --------------------------------------------------
curl -X GET "http://localhost:8090/sargweb/api/journey/count" -H "Content-Type: application/json"
echo.
echo.

echo 5. DELETE - Delete Journey Enquiry by ID (Replace {id} with actual ID)
echo URL: http://localhost:8090/sargweb/api/journey/1
echo --------------------------------------------------
curl -X DELETE "http://localhost:8090/sargweb/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo SUMMARY OF ALL ENDPOINTS:
echo ============================================
echo POST   /sargweb/api/journey/submit     - Submit new journey enquiry
echo GET    /sargweb/api/journey/all        - Get all journey enquiries
echo GET    /sargweb/api/journey/{id}       - Get journey enquiry by ID
echo GET    /sargweb/api/journey/count      - Get total count
echo DELETE /sargweb/api/journey/{id}       - Delete journey enquiry
echo ============================================

pause

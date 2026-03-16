@echo off
echo ============================================
echo JOURNEY CONTROLLER - ALL ENDPOINTS TEST
echo ============================================
echo.

echo 1. Submit Journey Enquiry (POST /api/journey/submit)
echo -----------------------------------------------------
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"John Doe\",\"emailAddress\":\"john@example.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build an e-commerce website\"}"
echo.
echo.

echo 2. Get All Journey Enquiries (GET /api/journey/all)
echo ----------------------------------------------------
curl -X GET "http://localhost:8090/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 3. Get Journey Enquiry Count (GET /api/journey/count)
echo ------------------------------------------------------
curl -X GET "http://localhost:8090/api/journey/count" -H "Content-Type: application/json"
echo.
echo.

echo 4. Get Journey Enquiry by ID (GET /api/journey/1)
echo -------------------------------------------------
curl -X GET "http://localhost:8090/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo 5. Delete Journey Enquiry (DELETE /api/journey/1)
echo -------------------------------------------------
curl -X DELETE "http://localhost:8090/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo ADMIN PANEL PATHS (with sargweb prefix)
echo ============================================
echo.

echo 6. Get All Journey Enquiries - Admin (GET sargweb/api/journey/all)
echo -------------------------------------------------------------------
curl -X GET "http://localhost:8090/sargweb/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 7. Get New Journey Enquiries - Admin (GET sargweb/api/journey/new-enquiries)
echo -----------------------------------------------------------------------------
curl -X GET "http://localhost:8090/sargweb/api/journey/new-enquiries" -H "Content-Type: application/json"
echo.
echo.

echo 8. Delete Journey Enquiry - Admin (DELETE sargweb/api/journey/delete/1)
echo ------------------------------------------------------------------------
curl -X DELETE "http://localhost:8090/sargweb/api/journey/delete/1" -H "Content-Type: application/json"
echo.
echo.

echo 9. Hide Journey Enquiry - Admin (PUT sargweb/api/journey/hide/1)
echo -----------------------------------------------------------------
curl -X PUT "http://localhost:8090/sargweb/api/journey/hide/1" -H "Content-Type: application/json"
echo.

pause

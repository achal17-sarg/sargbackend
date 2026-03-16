@echo off
echo ============================================
echo JOURNEY CONTROLLER - ALL ENDPOINTS
echo ============================================
echo.

echo ============================================
echo WEBSITE PATH: /api/journey
echo ============================================
echo.

echo 1. POST - Submit Journey (Website)
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Website User\",\"emailAddress\":\"website@test.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build e-commerce site\"}"
echo.
echo.

echo 2. GET - All Journey Enquiries (Website)
curl -X GET "http://localhost:8090/api/journey/all"
echo.
echo.

echo 3. GET - Journey by ID (Website)
curl -X GET "http://localhost:8090/api/journey/1"
echo.
echo.

echo 4. GET - Journey Count (Website)
curl -X GET "http://localhost:8090/api/journey/count"
echo.
echo.

echo 5. GET - Test Endpoint (Website)
curl -X GET "http://localhost:8090/api/journey/test"
echo.
echo.

echo 6. DELETE - Delete Journey (Website)
curl -X DELETE "http://localhost:8090/api/journey/1"
echo.
echo.

echo ============================================
echo ADMIN PATH: sargweb/api/journey
echo ============================================
echo.

echo 7. POST - Submit Journey (Admin)
curl -X POST "http://localhost:8090/sargweb/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Admin User\",\"emailAddress\":\"admin@test.com\",\"expertiseNeeded\":\"Mobile App\",\"projectGoals\":\"Build iOS app\"}"
echo.
echo.

echo 8. GET - All Journey Enquiries (Admin)
curl -X GET "http://localhost:8090/sargweb/api/journey/all"
echo.
echo.

echo 9. GET - New Journey Enquiries (Admin)
curl -X GET "http://localhost:8090/sargweb/api/journey/new-enquiries"
echo.
echo.

echo 10. GET - Journey by ID (Admin)
curl -X GET "http://localhost:8090/sargweb/api/journey/1"
echo.
echo.

echo 11. GET - Journey Count (Admin)
curl -X GET "http://localhost:8090/sargweb/api/journey/count"
echo.
echo.

echo 12. DELETE - Delete Journey (Admin)
curl -X DELETE "http://localhost:8090/sargweb/api/journey/delete/1"
echo.
echo.

echo 13. PUT - Hide Journey (Admin)
curl -X PUT "http://localhost:8090/sargweb/api/journey/hide/1"
echo.
echo.

echo ============================================
echo QUICK TEST - Check if data is saving
echo ============================================
echo.

echo Step 1: Submit via website path
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Quick Test\",\"emailAddress\":\"quicktest@test.com\",\"expertiseNeeded\":\"Testing\",\"projectGoals\":\"Verify data saves\"}"
echo.
echo.

echo Step 2: Check count via website path
curl -X GET "http://localhost:8090/api/journey/count"
echo.
echo.

echo Step 3: Get all via website path
curl -X GET "http://localhost:8090/api/journey/all"
echo.
echo.

echo Step 4: Get all via admin path
curl -X GET "http://localhost:8090/sargweb/api/journey/all"
echo.
echo.

pause

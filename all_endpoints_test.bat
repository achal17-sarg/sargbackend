@echo off
echo ============================================
echo ALL API ENDPOINTS - COMPREHENSIVE TEST
echo ============================================
echo.

echo ============================================
echo CONTACT CONTROLLER
echo ============================================
echo.

echo 1. Submit Contact Form (POST sargweb/api/contact/submit)
curl -X POST "http://localhost:8090/sargweb/api/contact/submit" ^
-H "Content-Type: application/json" ^
-d "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"phoneno\":\"1234567890\",\"business_nm\":\"Test Business\",\"message\":\"Test message\"}"
echo.
echo.

echo 2. Get All Contact Enquiries (GET sargweb/api/contact/all)
curl -X GET "http://localhost:8090/sargweb/api/contact/all" -H "Content-Type: application/json"
echo.
echo.

echo 3. Get New Contact Enquiries (GET sargweb/api/contact/new-enquiries)
curl -X GET "http://localhost:8090/sargweb/api/contact/new-enquiries" -H "Content-Type: application/json"
echo.
echo.

echo 4. Get All Including Hidden (GET sargweb/api/contact/all-including-hidden)
curl -X GET "http://localhost:8090/sargweb/api/contact/all-including-hidden" -H "Content-Type: application/json"
echo.
echo.

echo 5. Hide Contact Enquiry (PUT sargweb/api/contact/hide/1)
curl -X PUT "http://localhost:8090/sargweb/api/contact/hide/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo JOURNEY CONTROLLER
echo ============================================
echo.

echo 6. Submit Journey Enquiry (POST /api/journey/submit)
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"John Doe\",\"emailAddress\":\"john@example.com\",\"expertiseNeeded\":\"Web Development\",\"projectGoals\":\"Build website\"}"
echo.
echo.

echo 7. Get All Journey Enquiries (GET /api/journey/all)
curl -X GET "http://localhost:8090/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 8. Get Journey Enquiry by ID (GET /api/journey/1)
curl -X GET "http://localhost:8090/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo 9. Get Journey Count (GET /api/journey/count)
curl -X GET "http://localhost:8090/api/journey/count" -H "Content-Type: application/json"
echo.
echo.

echo 10. Delete Journey Enquiry (DELETE /api/journey/1)
curl -X DELETE "http://localhost:8090/api/journey/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo JOURNEY ADMIN CONTROLLER
echo ============================================
echo.

echo 11. Get All Journey - Admin (GET sargweb/api/journey/all)
curl -X GET "http://localhost:8090/sargweb/api/journey/all" -H "Content-Type: application/json"
echo.
echo.

echo 12. Get New Journey Enquiries - Admin (GET sargweb/api/journey/new-enquiries)
curl -X GET "http://localhost:8090/sargweb/api/journey/new-enquiries" -H "Content-Type: application/json"
echo.
echo.

echo 13. Delete Journey - Admin (DELETE sargweb/api/journey/delete/1)
curl -X DELETE "http://localhost:8090/sargweb/api/journey/delete/1" -H "Content-Type: application/json"
echo.
echo.

echo 14. Hide Journey - Admin (PUT sargweb/api/journey/hide/1)
curl -X PUT "http://localhost:8090/sargweb/api/journey/hide/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo DEMO CONTROLLER
echo ============================================
echo.

echo 15. Submit Demo Request (POST sargweb/api/demo/submit)
curl -X POST "http://localhost:8090/sargweb/api/demo/submit" ^
-H "Content-Type: application/json" ^
-d "{\"name\":\"Demo User\",\"email\":\"demo@example.com\",\"phone\":\"9876543210\",\"company\":\"Demo Company\"}"
echo.
echo.

echo ============================================
echo CAREER CONTROLLER
echo ============================================
echo.

echo 16. Submit Career Application (POST sargweb/api/careers/apply)
curl -X POST "http://localhost:8090/sargweb/api/careers/apply" ^
-H "Content-Type: multipart/form-data" ^
-F "fullName=Career Applicant" ^
-F "email=career@example.com" ^
-F "phone=5555555555" ^
-F "position=Developer" ^
-F "resume=@C:\path\to\resume.pdf"
echo.
echo.

echo 17. Get All Career Applications (GET sargweb/api/careers/applications)
curl -X GET "http://localhost:8090/sargweb/api/careers/applications" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo JOB POST CONTROLLER
echo ============================================
echo.

echo 18. Create Job Post (POST sargweb/api/jobs/create)
curl -X POST "http://localhost:8090/sargweb/api/jobs/create" ^
-H "Content-Type: application/json" ^
-d "{\"title\":\"Software Developer\",\"description\":\"Job description\",\"location\":\"Remote\",\"type\":\"Full-time\"}"
echo.
echo.

echo 19. Get All Job Posts (GET sargweb/api/jobs/all)
curl -X GET "http://localhost:8090/sargweb/api/jobs/all" -H "Content-Type: application/json"
echo.
echo.

echo 20. Get Job Post by ID (GET sargweb/api/jobs/1)
curl -X GET "http://localhost:8090/sargweb/api/jobs/1" -H "Content-Type: application/json"
echo.
echo.

echo 21. Update Job Post (PUT sargweb/api/jobs/update/1)
curl -X PUT "http://localhost:8090/sargweb/api/jobs/update/1" ^
-H "Content-Type: application/json" ^
-d "{\"title\":\"Senior Developer\",\"description\":\"Updated description\",\"location\":\"Remote\",\"type\":\"Full-time\"}"
echo.
echo.

echo 22. Delete Job Post (DELETE sargweb/api/jobs/delete/1)
curl -X DELETE "http://localhost:8090/sargweb/api/jobs/delete/1" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo DEBUG CONTROLLER
echo ============================================
echo.

echo 23. Debug Enquiries (GET sargweb/api/debug/enquiry-debug)
curl -X GET "http://localhost:8090/sargweb/api/debug/enquiry-debug" -H "Content-Type: application/json"
echo.
echo.

echo ============================================
echo TEST COMPLETE
echo ============================================

pause

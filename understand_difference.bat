@echo off
echo ============================================
echo UNDERSTANDING THE DIFFERENCE
echo ============================================
echo.
echo You have TWO types of enquiries:
echo.
echo 1. CONTACT ENQUIRIES (ContactForm table)
echo    - Website submits to: /sargweb/api/contact/submit
echo    - Admin checks at: /sargweb/api/contact/all
echo.
echo 2. JOURNEY ENQUIRIES (JourneyInquiry table)
echo    - Website submits to: /api/journey/submit
echo    - Admin should check at: /sargweb/api/journey/all
echo.
echo ============================================
echo YOUR ISSUE:
echo ============================================
echo You submitted to: /api/journey/submit (Journey)
echo But checking at: /sargweb/api/contact/all (Contact)
echo.
echo These are DIFFERENT tables!
echo ============================================
echo.
echo.

echo TEST: Submit Journey enquiry
curl -X POST "http://localhost:8090/api/journey/submit" ^
-H "Content-Type: application/json" ^
-d "{\"fullName\":\"Test Journey\",\"emailAddress\":\"journey@test.com\",\"expertiseNeeded\":\"Web Dev\",\"projectGoals\":\"Test goals\"}"
echo.
echo.

echo CHECK: Get Journey enquiries (CORRECT endpoint for admin)
curl -X GET "http://localhost:8090/sargweb/api/journey/all"
echo.
echo.

echo CHECK: Get Journey count
curl -X GET "http://localhost:8090/sargweb/api/journey/count"
echo.
echo.

echo ============================================
echo SOLUTION:
echo ============================================
echo In your admin panel, use this URL to see Journey enquiries:
echo http://localhost:8090/sargweb/api/journey/all
echo.
echo NOT this URL (which shows Contact enquiries):
echo http://localhost:8090/sargweb/api/contact/all
echo ============================================

pause

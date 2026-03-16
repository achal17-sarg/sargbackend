@echo off
echo Submitting Test Enquiry...
echo.

curl -X POST "http://localhost:8090/sargweb/api/contact/submit" ^
-H "Content-Type: application/json" ^
-d "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"phoneno\":\"1234567890\",\"business_nm\":\"Test Business\",\"message\":\"This is a test enquiry to check if new enquiries are showing up\"}"

echo.
echo Test enquiry submitted!
echo.
echo Now checking if it appears in new enquiries...
echo.

curl -X GET "http://localhost:8090/sargweb/api/contact/new-enquiries" -H "Content-Type: application/json"

pause
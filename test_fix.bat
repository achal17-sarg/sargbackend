@echo off
echo Testing New Enquiries Fix...
echo.

echo 1. Submit a test enquiry:
curl -X POST "http://localhost:8090/sargweb/api/contact/submit" ^
-H "Content-Type: application/json" ^
-d "{\"name\":\"Fix Test\",\"email\":\"fixtest@example.com\",\"phoneno\":\"1111111111\",\"business_nm\":\"Fix Test Business\",\"message\":\"Testing if new enquiries show up after fix\"}"
echo.
echo.

echo 2. Check new enquiries:
curl -X GET "http://localhost:8090/sargweb/api/contact/new-enquiries" -H "Content-Type: application/json"
echo.
echo.

echo 3. Check all enquiries:
curl -X GET "http://localhost:8090/sargweb/api/contact/all" -H "Content-Type: application/json"
echo.

pause
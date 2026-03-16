@echo off
echo Testing New Enquiries Issue...
echo.

echo Step 1: Check current new enquiries count
curl -s -X GET "http://localhost:8090/sargweb/api/debug/enquiry-debug" -H "Content-Type: application/json" | jq .
echo.

echo Step 2: Submit a test enquiry
curl -s -X POST "http://localhost:8090/sargweb/api/contact/submit" ^
-H "Content-Type: application/json" ^
-d "{\"name\":\"Debug Test\",\"email\":\"debug@test.com\",\"phoneno\":\"9999999999\",\"business_nm\":\"Debug Business\",\"message\":\"Testing new enquiry visibility\"}" | jq .
echo.

echo Step 3: Check debug info again
curl -s -X GET "http://localhost:8090/sargweb/api/debug/enquiry-debug" -H "Content-Type: application/json" | jq .
echo.

echo Step 4: Check new enquiries endpoint
curl -s -X GET "http://localhost:8090/sargweb/api/contact/new-enquiries" -H "Content-Type: application/json" | jq .
echo.

pause
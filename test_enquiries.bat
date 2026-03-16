@echo off
echo Testing Enquiry Endpoints...
echo.

echo 1. Testing /new-enquiries endpoint:
curl -X GET "http://localhost:8090/sargweb/api/contact/new-enquiries" -H "Content-Type: application/json"
echo.
echo.

echo 2. Testing /all endpoint:
curl -X GET "http://localhost:8090/sargweb/api/contact/all" -H "Content-Type: application/json"
echo.
echo.

echo 3. Testing /all-including-hidden endpoint:
curl -X GET "http://localhost:8090/sargweb/api/contact/all-including-hidden" -H "Content-Type: application/json"
echo.

pause
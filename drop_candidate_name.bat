@echo off
echo Dropping candidate_name column from career_applications table...

mysql -h 72.61.235.192 -P 3306 -u sargweb_user -pSargweb@2026 sargweb_db -e "ALTER TABLE career_applications DROP COLUMN candidate_name;"

if %ERRORLEVEL% EQU 0 (
    echo Column dropped successfully!
) else (
    echo Failed to drop column. Error code: %ERRORLEVEL%
)

pause

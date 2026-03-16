-- SQL queries to check enquiry data in database

-- 1. Check all contact forms with their status and hidden values
SELECT id, name, email, business_nm, status, hidden, submitted_at 
FROM contact_forms 
ORDER BY submitted_at DESC 
LIMIT 10;

-- 2. Check for records with NULL status or hidden values
SELECT id, name, email, status, hidden, submitted_at 
FROM contact_forms 
WHERE status IS NULL OR hidden IS NULL 
ORDER BY submitted_at DESC;

-- 3. Count enquiries by status
SELECT status, COUNT(*) as count 
FROM contact_forms 
GROUP BY status;

-- 4. Count hidden vs visible enquiries
SELECT 
    CASE 
        WHEN hidden IS NULL THEN 'NULL'
        WHEN hidden = true THEN 'Hidden'
        ELSE 'Visible'
    END as visibility_status,
    COUNT(*) as count
FROM contact_forms 
GROUP BY hidden;

-- 5. Get new enquiries (matching our application logic)
SELECT id, name, email, business_nm, status, hidden, submitted_at 
FROM contact_forms 
WHERE (hidden IS NULL OR hidden = false) 
AND (status = 'New' OR status IS NULL)
ORDER BY submitted_at DESC;
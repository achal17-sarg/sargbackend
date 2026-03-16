-- Check if full_name column exists and rename it to candidate_name
ALTER TABLE career_applications CHANGE COLUMN full_name candidate_name VARCHAR(255) NOT NULL;

-- Or if the column doesn't exist, add it
-- ALTER TABLE career_applications ADD COLUMN candidate_name VARCHAR(255) NOT NULL;

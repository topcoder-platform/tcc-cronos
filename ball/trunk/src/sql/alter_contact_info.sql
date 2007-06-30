-- ------------------------------------------------------------------------------------------------------
-- Alters CONTACT_INFO table adding new COUNTRY column and removing NOT NULL constraint for STATE column
-- The value of COUNTRY column for existing records is set with 'United States' and then will have to be
-- updated individually for each record 
-- ------------------------------------------------------------------------------------------------------
ALTER TABLE contact_info ALTER COLUMN state VARCHAR(29);
ALTER TABLE contact_info ADD country VARCHAR(40);
UPDATE contact_info SET country='United States';
ALTER TABLE contact_info ALTER COLUMN country VARCHAR(40) NOT NULL;


-- The standard reject reasons

INSERT INTO reject_reason (reject_reason_id, description, active, creation_date, creation_user, modification_date, modification_user)
    VALUES (1, 'Work Incomplete', 0, CURRENT, 'System', CURRENT, 'System');

INSERT INTO reject_reason (reject_reason_id, description, active, creation_date, creation_user, modification_date, modification_user)
    VALUES (2, 'Unapproved Hours', 0, CURRENT, 'System', CURRENT, 'System');

INSERT INTO reject_reason (reject_reason_id, description, active, creation_date, creation_user, modification_date, modification_user)
    VALUES (3, 'Client Review', 0, CURRENT, 'System', CURRENT, 'System');


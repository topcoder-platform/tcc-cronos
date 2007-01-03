
-- Required account status for Time Tracker application
INSERT INTO account_status (account_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (1, 'Active', CURRENT, 'System', CURRENT, 'System');

INSERT INTO account_status (account_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (2, 'Inactive', CURRENT, 'System', CURRENT, 'System');

INSERT INTO account_status (account_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (3, 'Locked', CURRENT, 'System', CURRENT, 'System');

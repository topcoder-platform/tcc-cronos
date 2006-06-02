
-- The standard expense statuses

INSERT INTO expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (1, 'Entered', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (2, 'Pending', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (3, 'Accepted', CURRENT, 'System', CURRENT, 'System');

INSERT INTO expense_status (expense_status_id, description, creation_date, creation_user, modification_date, modification_user)
    VALUES (4, 'Rejected', CURRENT, 'System', CURRENT, 'System');


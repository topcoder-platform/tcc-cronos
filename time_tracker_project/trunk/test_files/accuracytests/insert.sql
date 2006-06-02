INSERT INTO company VALUES (0, "a", "a", today, "user", today, "user");
INSERT INTO company VALUES (1, "b", "b", today, "user", today, "user");
INSERT INTO company VALUES (2, "c", "c", today, "user", today, "user");

INSERT INTO user_account VALUES (0, 1, "u1", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (1, 1, "u2", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (2, 1, "u3", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (3, 1, "u4", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (300, 1, "u5", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (400, 1, "u6", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (401, 1, "u7", "u1", today, "user", today, "user");
INSERT INTO user_account VALUES (402, 1, "u8", "u1", today, "user", today, "user");


INSERT INTO time_entry(time_entry_id, company_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (0, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (1, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (2, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (3, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (500, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (501, 1, "desc", today, 1, 1, today, "user", today, "modifier");
INSERT INTO time_entry(time_entry_id, company_id, Description, entry_date, hours, billable, creation_date, creation_user, modification_date, modification_user)
VALUES (502, 1, "desc", today, 1, 1, today, "user", today, "modifier");


INSERT INTO expense_entry VALUES (0, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (1, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (2, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (3, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (500, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (501, 1, "desc", today, 0, 0, today, "user", today, "modifier");
INSERT INTO expense_entry VALUES (502, 1, "desc", today, 0, 0, today, "user", today, "modifier");
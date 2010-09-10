insert into jiraissue(id) values(1)
insert into customfield(id, cfname) values(1, 'ProjectID')
insert into customfieldvalue(id, issue, numbervalue, customfield) values(1, 1, 1001, 1)

insert into payment(payment_id, payment_type_id, charity_ind, show_in_profile_ind, show_details_ind) values(1, 20, 0, 0, 0)
insert into user_payment(payment_id, gross_amount, user_id) values(1, 500, 1)

insert into jiraissue(id) values(2)
insert into customfield(id, cfname) values(2, 'ProjectID')
insert into customfield(id, cfname) values(3, 'Resolution Date')
insert into customfieldvalue(id, issue, numbervalue, customfield) values(2, 2, 1, 2)
insert into customfieldvalue(id, issue, datevalue, customfield) values(3, 2, '2010-09-02 12:00:00', 3)

insert into project(project_id, tc_direct_project_id, project_status_id, project_category_id, create_date, create_user, modify_date, modify_user) values(1, 101, 1, 1, '2010-09-02 12:00:00', 'user', '2010-09-02 12:00:00', 'user')

insert into resource(resource_id, project_id, resource_role_id, create_date, create_user, modify_date, modify_user) values(1, 1, 14, '2010-09-02 12:00:00', 'user', '2010-09-02 12:00:00', 'user')

insert into resource_info(resource_id, resource_info_type_id, value, create_date, create_user, modify_date, modify_user) values(1, 1, '1', '2010-09-02 12:00:00', 'user', '2010-09-02 12:00:00', 'user')

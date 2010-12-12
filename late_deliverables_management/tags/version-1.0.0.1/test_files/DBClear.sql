# Database 'tcs_catalog';
DELETE FROM tcs_catalog:'informix'.late_deliverable;
DELETE FROM tcs_catalog:'informix'.project_phase;
DELETE FROM tcs_catalog:'informix'.project;
DELETE FROM tcs_catalog:'informix'.resource_info;
DELETE FROM tcs_catalog:'informix'.resource;

# Database 'corporate_oltp';
DELETE FROM corporate_oltp:'informix'.tc_direct_project;
DELETE FROM corporate_oltp:'informix'.user_permission_grant;
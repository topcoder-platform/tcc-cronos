--
-- to be executed both in studio_oltp and tcs_catalog
--
create synonym 'informix'.permission_type FOR corporate_oltp:'informix'.permission_type;
create synonym 'informix'.user_permission_grant FOR corporate_oltp:'informix'.user_permission_grant;
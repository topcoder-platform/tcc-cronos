
Hi Reviewer,

Have a good time,

WHAT WAS CHANGED
0) no java code has been changed since FF4, only the database and generated reports.

1) project_management.jar is now version 1.2.1
2) project_management_persistence.jar is now version 1.2.1
3) NEW ID SEQUENCE ADDED:
	file_type_id_seq, prize_id_seq, studio_spec_id_seq, and review_application_id_seq

4) Merge the tables entries from {project_management_persistence.jar}/test_files/project_management.sql
	into ./test_files/sqls/merge-all.sql

5) Changes on database is shown in context diff at ./test_files/sqls/merge-all_sql.diff
	old version: ./test_files/sqls/data/old-merge-all.sql
	new version: ./test_files/sqls/merge-all.sql

For all fixes, please refer to:
	./test_files/ChangeLogs/Final_Fix_README.txt
	./test_files/ChangeLogs/Final_Fix_ChangeLog.txt

Test logs details:
	./log/unit_test/*.txt      -- the Unit test logs
	./log/accuracy_test/*.txt  -- the Accuracy test logs
	./log/failure_test/*.txt   -- the Failure test logs

Thank your for reviewing my work.
Good day.
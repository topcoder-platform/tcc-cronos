Steps to run tests:

(1) modify JiraManagementServiceBean.DEFAULT_CONFIG_PATH to your local file pointing to
    accuracy/test_files/accuracy/com/topcoder/jira/webservices/bean/JiraManagementServiceBean.properties

(2) put compiled source code to ejb/com/topcoder/jira/webservices,
    note that ejb/com/topcoder/jira/webservices/accuracytests/* should NOT be removed,
    it is used for accuracy tests.
	
(3) modify file paths in test_files/com/topcoder/jira/webservices/bean/JiraManagementServiceBean.properties,
    test_files/ejb/ejb-jar.xml and test_files/accuracy/ejb/META-INF/ejb-jar.xml

(4) cd test_files/accuracy, then run "ant ear" to get ear file

(5) run "ant deploy_ear", "ant run_jboss" and "ant test"




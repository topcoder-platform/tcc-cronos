Steps to run accuracy tests:

(1) modify JiraManagementServiceBean.DEFAULT_CONFIG_PATH to your local file pointing to
    accuracy/test_files/accuracy/com/topcoder/jira/webservices/bean/JiraManagementServiceBean.properties

(2) put compiled source code to ejb/com/topcoder/jira/webservices,
    note that ejb/com/topcoder/jira/webservices/accuracytests/* should NOT be removed,
    it is used for accuracy tests.

(3) run "ant ear" to get ear file

(4) deploy this ear file to %JBOSS_HOME%/server/default/deploy

(5) start jboss

(6) run "ant test"




Note that when aggregating these accuracy tests into final submission,
the config file and namespace should be injected by EJB3 deployment descriptor,
I can not do so in review phase, there seems to be something wrong with the submissions.
And this issue should be fixed.


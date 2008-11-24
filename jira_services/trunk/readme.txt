First of all, please pay attention on this issue:
http://forums.topcoder.com/?module=Thread&threadID=627965
As temporary workaround, I've modified JiraManagementServiceBean.DEFAULT_CONFIG_PATH to point to the concrete file in the project directory. Please change project directory there. Same thing was done with JiraManagementServiceBean.properties in test_files/com/...   If you know how to make configuration persistence to work on server without that, feel free to punish me.

I provide my own build scripts, so you should be able to build ear without big problems. Main command there is "deploy_ear".

Have fun and to see you again in final fixes,
Developer
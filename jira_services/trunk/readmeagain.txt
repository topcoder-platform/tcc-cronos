Firstly, issue with configuration is not resolved yet. I've posted some details in the public forum (as requested by PM):
http://forums.topcoder.com/?module=Thread&threadID=628101
But, as requested by reviewers, I've changed DEFAULT_CONFIG_PATH in the bean to it's old value. Now absolute reference is injected by container (please correct it in test_files/ejb/ejb-jar.xml before deploying).

about zdurasco 5.1.3#3
I'm not entirely sure what is expected here. By "only the login method is tested" sentence I conclude that it's about second test in demo. But there is no big reason to duplicate first demo test here, since JiraManagerWebServiceDelegate uses exactly same JiraManagementServiceClient to access web service (just hide details from user). And this way demo was provided by designer also.
Please clarify what you expect from me here, if anything.
<html>
<head>
<script language='javascript' src="js/AJAXProcessor.js"></script>
<script language='javascript' src="js/WidgetBridgeNamespace.js"></script>
<script language='javascript' src="js/StudioService.js"></script>
<script language='javascript' src="js/Contest.js"></script>
<script language='javascript' src="js/ContestCategory.js"></script>
<script language='javascript' src="js/ContestPayload.js"></script>
<script language='javascript' src="js/ContestStatus.js"></script>
<script language='javascript' src="js/Prize.js"></script>
<script language='javascript' src="js/Submission.js"></script>
<script language='javascript' src="js/UploadedDocument.js"></script>
<script language='javascript' src="js/IllegalArgumentException.js"></script>
<script language='javascript' src="js/InvalidResponseException.js"></script>

<script language="JavaScript">

/**
 * <p>Tests Studtio.createContest().</p>
 */
function testStudioCreateContest() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var projectID = 1;

	// success callback	
	var success = function() {
		alert("Success = Contest created!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var contest = getContest1();

	studioService.createContest(contest,projectID,success,error);
}

/**
 * <p>Tests Erro Studtio.createContest().</p>
 */
function testStudioCreateContestError() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var projectID = 50;

	// success callback	
	var success = function() {
		alert("Success = Contest created!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var contest = getContest1();

	studioService.createContest(contest,projectID,success,error);
}

/**
 * <p>Tests Studtio.getContest().</p>
 */
function testStudioGetContest() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 1;

	// success callback	
	var success = function(contest) {
		alert("Success = Contest retrieved : "+contest.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContest(contestID,success,error);
}

/**
 * <p>Tests Error Studtio.getContest().</p>
 */
function testStudioGetContestError() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 50;

	// success callback	
	var success = function(contest) {
		alert("Success = Contest retrieved : "+contest.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContest(contestID,success,error);
}

/**
 * <p>Tests Studtio.getContestsForClient().</p>
 */
function testStudioGetContestsForClient() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var clientID = 1;
	var contestStatusID = 1;

	// success callback	
	var success = function(contests) {
		for (var i = 0; i < contests.length; i++) {
			alert("Success = Contest returned index["+i+"]: "+contests[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContestsForClient(clientID,contestStatusID,success,error);
}

/**
 * <p>Tests Error Studtio.getContestsForClient().</p>
 */
function testStudioGetContestsForClientError() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var clientID = 50;
	var contestStatusID = 50;

	// success callback	
	var success = function(contests) {
		for (var i = 0; i < contests.length; i++) {
			alert("Success = Contest returned index["+i+"]: "+contests[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContestsForClient(clientID,contestStatusID,success,error);
}

/**
 * <p>Tests Studtio.getContestsForProject().</p>
 */
function testStudioGetContestsForProject() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var projectID = 1;

	// success callback	
	var success = function(contests) {
		for (var i = 0; i < contests.length; i++) {
			alert("Success = Contest returned index["+i+"]: "+contests[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContestsForProject(projectID,success,error);
}

/**
 * <p>Tests Error Studtio.getContestsForProject().</p>
 */
function testStudioGetContestsForProjectError() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var projectID = 50;

	// success callback	
	var success = function(contests) {
		for (var i = 0; i < contests.length; i++) {
			alert("Success = Contest returned index["+i+"]: "+contests[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContestsForProject(projectID,success,error);
}

/**
 * <p>Tests Studtio.updateContestStatus().</p>
 */
function testStudioUpdateContestStatus() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 1;
	var newStatusID = 1;

	// success callback	
	var success = function(contest) {
		alert("Success = Contest updated : "+contest.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.updateContestStatus(contestID,newStatusID,success,error);
}

/**
 * <p>Tests Error Studtio.updateContestStatus().</p>
 */
function testStudioUpdateContestStatusError() {
	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 50;
	var newStatusID = 50;

	// success callback	
	var success = function(contest) {
		alert("Success = Contest updated : "+contest.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.updateContestStatus(contestID,newStatusID,success,error);
}

/**
 * <p>Tests Studtio.removeDocumentFromContest().</p>
 */
function testStudioRemoveDocumentFromContest() {

	var updoc = new js.topcoder.widgets.bridge.UploadedDocument();
	updoc.setDocumentID(1);
	updoc.setContestID(1);
	updoc.setFileName("C:\\test.txt");
	updoc.setDescription("descrip");

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Uploaded Document removed!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.removeDocumentFromContest(updoc,success,error);
}

/**
 * <p>Tests Error Studtio.removeDocumentFromContest().</p>
 */
function testStudioRemoveDocumentFromContestError() {

	var updoc = new js.topcoder.widgets.bridge.UploadedDocument();
	updoc.setDocumentID(50);
	updoc.setContestID(50);
	updoc.setFileName("C:\\test.txt");
	updoc.setDescription("descrip");

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Uploaded Document removed!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.removeDocumentFromContest(updoc,success,error);
}

/**
 * <p>Tests Studtio.retrieveSubmissionsForContest().</p>
 */
function testStudioRetrieveSubmissionsForContest() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 1;

	// success callback	
	var success = function(submissions) {
		for (var i = 0; i < submissions.length; i++) {
			alert("Success = Submissions returned index["+i+"]: "+submissions[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveSubmissionsForContest(contestID,success,error);
}

/**
 * <p>Tests Error Studtio.retrieveSubmissionsForContest().</p>
 */
function testStudioRetrieveSubmissionsForContestError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var contestID = 50;

	// success callback	
	var success = function(submissions) {
		for (var i = 0; i < submissions.length; i++) {
			alert("Success = Submissions returned index["+i+"]: "+submissions[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveSubmissionsForContest(contestID,success,error);
}

/**
 * <p>Tests Studtio.updateSubmission().</p>
 */
function testStudioUpdateSubmission() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Submission Updated!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var submission = getSubmission1();
	
	studioService.updateSubmission(submission,success,error);
}

/**
 * <p>Tests Error Studtio.updateSubmission().</p>
 */
function testStudioUpdateSubmissionError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Submission Updated!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var submission = getSubmission1();
	submission.setSubmissionID(50);
	
	studioService.updateSubmission(submission,success,error);
}

/**
 * <p>Tests Studtio.updateContest().</p>
 */
function testStudioUpdateContest() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Contest Updated!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var contest = getContest1();
	
	studioService.updateContest(contest,success,error);
}

/**
 * <p>Tests Erro Studtio.updateContest().</p>
 */
function testStudioUpdateContestError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function() {
		alert("Success = Contest Updated!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var contest = getContest1();
	contest.setContestID(50);
	
	studioService.updateContest(contest,success,error);
}

/**
 * <p>Tests Studtio.retrieveSubmission().</p>
 */
function testStudioRetrieveSubmission() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var submissionID = 1;

	// success callback	
	var success = function(submission) {
		alert("Success = Submission Retrieved! "+submission.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveSubmission(submissionID,success,error);
}

/**
 * <p>Tests Error Studtio.retrieveSubmission().</p>
 */
function testStudioRetrieveSubmissionError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var submissionID = 50;

	// success callback	
	var success = function(submission) {
		alert("Success = Submission Retrieved! "+submission.toJSON());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveSubmission(submissionID,success,error);
}

/**
 * <p>Tests Studtio.removeSubmission().</p>
 */
function testStudioRemoveSubmission() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var submissionID = 1;

	// success callback	
	var success = function() {
		alert("Success = Submission removed!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.removeSubmission(submissionID,success,error);
}

/**
 * <p>Tests Error Studtio.removeSubmission().</p>
 */
function testStudioRemoveSubmissionError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var submissionID = 50;

	// success callback	
	var success = function() {
		alert("Success = Submission removed!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.removeSubmission(submissionID,success,error);
}

/**
 * <p>Tests Studtio.retrieveAllSubmissionsByMember().</p>
 */
function testStudioRetrieveAllSubmissionsByMember() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var userID = 1;

	// success callback	
	var success = function(submissions) {
		for (var i = 0; i < submissions.length; i++) {
			alert("Success = Submissions returned index["+i+"]: "+submissions[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveAllSubmissionsByMember(userID,success,error);
}

/**
 * <p>Tests Error Studtio.retrieveAllSubmissionsByMember().</p>
 */
function testStudioRetrieveAllSubmissionsByMemberError() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");
	var userID = 50;

	// success callback	
	var success = function(submissions) {
		for (var i = 0; i < submissions.length; i++) {
			alert("Success = Submissions returned index["+i+"]: "+submissions[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.retrieveAllSubmissionsByMember(userID,success,error);
}

/**
 * <p>Tests Studtio.getContestCategories().</p>
 */
function testStudioGetContestCategories() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function(categories) {
		for (var i = 0; i < categories.length; i++) {
			alert("Success = Contest Category returned index["+i+"]: "+categories[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	var params = new Array();
	params[0] = "param1";
	params[1] = "param2";
	params[2] = "param3";
	
	studioService.getContestCategories(params,success,error);
}

/**
 * <p>Tests Studtio.getSubmissionFileTypes().</p>
 */
function testStudioGetSubmissionFileTypes() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function(fileTypes) {
		for (var i = 0; i < fileTypes.length; i++) {
			alert("Success = File Types returned index["+i+"]: "+fileTypes[i]);
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getSubmissionFileTypes(success,error);
}

/**
 * <p>Tests Studtio.getContestStatuses().</p>
 */
function testStudioGetContestStatuses() {

	// initialize the studio service
	var studioService = new js.topcoder.widgets.bridge.StudioService("ajaxBridge");

	// success callback	
	var success = function(contestStatuses) {
		for (var i = 0; i < contestStatuses.length; i++) {
			alert("Success = Contest Status returned index["+i+"]: "+contestStatuses[i].toJSON());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	studioService.getContestStatuses(success,error);
}


/**
 * Returns a Contest for testing.
 */
function getContest1() {
	// Prize #1	
	var prize1 = 
		"{\"place\" : 2,\"amount\" : 10000}";
 	// Prize #2
	var prize2 = 
		"{\"place\" : 1,\"amount\" : 20000}";
	// Doc Up #1
	var docup1 = 
		"{\"fileName\" : \"JSON file name1\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip1\"}";
	// Doc Up #2
	var docup2 = 
		"{\"fileName\" : \"JSON file name2\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip2\"}";

	// Contest Payload #1
	var payload1 = 
		"{\"name\" : \"JSON Payload Name1\",\"value\" : \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\","+
		"\"required\" : true, \"contestTypeID\" : 4444}";
	// Contest Payload #2
	var payload2 = 
		"{\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : \"JSON Payload Desc2\","+
		"\"required\" : true, \"contestTypeID\" : 4444}";
	
	var contest = new js.topcoder.widgets.bridge.Contest();
	contest.setContestID(1);
	contest.setProjectID(1);
	contest.setName("contest name");
	contest.setShortSummary("short summary");
	contest.setPrizes([prize2, prize1]); // array
	contest.setLaunchDateAndTime("2008-03-07 09:00");
	contest.setDurationInHours(3);
	contest.setWinnerAnnouncementDeadline("2008-03-20 09:00");
	contest.setContestTypeID(2);
	contest.setFinalFileFormatList(["\"format 1\"","\"format2\"","\"format3\""]); // array
	contest.setFinalFileFormatOther("zip");
	contest.setDocumentationUploads([docup1, docup2]); // array
	contest.setStatusID(3221123);
	contest.setContestPayloads([payload1, payload2]); // array
	contest.setContestCategoryID(2);
	contest.setContestDescriptionAndRequirements("This is a long desc");
	contest.setRequiredOrRestrictedColors("red");
	contest.setRequiredOrRestrictedFonts("arial");
	contest.setSizeRequirements("11 pt.");
	contest.setOtherRequirementsOrRestrictions("other reqs");
	contest.setTcDirectProjectID(1000);
	contest.setCreatorUserID(7000);
	
	return contest;
}

function getSubmission1() {
	var submission = new js.topcoder.widgets.bridge.Submission();
	submission.setSubmissionID(1);
	submission.setSubmitterID(1);
	submission.setSubmissionTimeStamp("2008-03-04 09:00");
	submission.setSubmissionContent("content");
	submission.setContestID(9999);
	submission.setPassedScreening(true);
	submission.setPlacement(3);
	submission.setPaidFor(true);
	submission.setPrice(4443332);
	submission.setMarkedForPurchase(true);
	submission.setRemoved(true);
	submission.setPassedReview(true);
	return submission;
}

</script>
</head>

<body>
Widget Webservices Bridge Demo
<br />
<br />
<span style="color:blue"><b>Studio Services Demo</b></span>
<br />
<ul>
	<li>Studio.createContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioCreateContest()" /></li>
	<li>Studio.getContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContest()" /></li>
	<li>Studio.getContestsForClient() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestsForClient()" />NOT SUPPORTED YET!!</li>
	<li>Studio.getContestsForProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestsForProject()" /></li>
	<li>Studio.updateContestStatus() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateContestStatus()" /></li>
	<li>Studio.removeDocumentFromContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRemoveDocumentFromContest()" /></li>
	<li>Studio.updateSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateSubmission()" /></li>
	<li>Studio.updateContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateContest()" /></li>
	<li>Studio.retrieveSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveSubmission()" />NOT SUPPORTED YET!!</li>
	<li>Studio.removeSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRemoveSubmission()" /></li>
	<li>Studio.retrieveSubmissionsForContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveSubmissionsForContest()" /></li>
	<li>Studio.retrieveAllSubmissionsByMember() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveAllSubmissionsByMember()" /></li>
	<li>Studio.getContestCategories() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestCategories()" /></li>
	<li>Studio.getSubmissionFileTypes() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetSubmissionFileTypes()" /></li>
	<li>Studio.getContestStatuses() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestStatuses()" /></li>
</ul>
<br />
<span style="color:red;"><b>Some Studio Services Error Demo</b></span>
<br />
<ul>
	<li>Studio.createContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioCreateContestError()" /></li>
	<li>Studio.getContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestError()" /></li>
	<li>Studio.getContestsForClient() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestsForClientError()" /></li>
	<li>Studio.getContestsForProject() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioGetContestsForProjectError()" /></li>
	<li>Studio.updateContestStatus() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateContestStatusError()" /></li>
	<li>Studio.removeDocumentFromContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRemoveDocumentFromContestError()" /></li>
	<li>Studio.updateSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateSubmissionError()" /></li>
	<li>Studio.updateContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioUpdateContestError()" /></li>
	<li>Studio.retrieveSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveSubmissionError()" /></li>
	<li>Studio.removeSubmission() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRemoveSubmissionError()" /></li>
	<li>Studio.retrieveSubmissionsForContest() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveSubmissionsForContestError()" /></li>
	<li>Studio.retrieveAllSubmissionsByMember() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testStudioRetrieveAllSubmissionsByMemberError()" /></li>
</ul>
<br />
<br />
<a href="index.jsp">Back to Index</a>
</body>
</html>
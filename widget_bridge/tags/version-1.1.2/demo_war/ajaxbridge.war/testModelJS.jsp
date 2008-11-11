<html>
<head>
<script language='javascript' src="js/AJAXProcessor.js"></script>
<script language='javascript' src="js/WidgetBridgeNamespace.js"></script>
<script language='javascript' src="js/Contest.js"></script>
<script language='javascript' src="js/ContestCategory.js"></script>
<script language='javascript' src="js/ContestPayload.js"></script>
<script language='javascript' src="js/ContestStatus.js"></script>
<script language='javascript' src="js/Prize.js"></script>
<script language='javascript' src="js/Project.js"></script>
<script language='javascript' src="js/Submission.js"></script>
<script language='javascript' src="js/UploadedDocument.js"></script>
<script language='javascript' src="js/IllegalArgumentException.js"></script>
<script language='javascript' src="js/InvalidResponseException.js"></script>

<script language="JavaScript">

function testContestEmpty() {

	// Prize #1	
	var prize1 = 
		"{\"place\" : 2,\"amount\" : 10000}";
 	// Prize #2
	var prize2 = 
		"{\"place\" : 1,\"amount\" : 20000}";
	// Doc Up #1
	var docup1 = 
		"{\"fileName\" : \"JSON file name1\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip1\"}";
	// Doc Up #2
	var docup2 = 
		"{\"fileName\" : \"JSON file name2\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip2\"}";

	// Contest Payload #1
	var payload1 = 
		"{\"name\" : \"JSON Payload Name1\",\"value\" : \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\","+
		"\"required\" : true, \"contestTypeID\" : 4444}";
	// Contest Payload #2
	var payload2 = 
		"{\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : \"JSON Payload Desc2\","+
		"\"required\" : true, \"contestTypeID\" : 4444}";
	
	var contest = new js.topcoder.widgets.bridge.Contest();
	contest.setContestID(222);
	contest.setProjectID(333);
	contest.setName("contest name");
	contest.setShortSummary("short summary");
	contest.setPrizes([prize2, prize1]); // array
	contest.setLaunchDateAndTime("2008-03-07 01:00");
	contest.setDurationInHours(3);
	contest.setWinnerAnnouncementDeadline("2008-03-20 01:00");
	contest.setContestTypeID(1);
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
	
	alert("created Contest using empty constructor : \n"+contest.toJSON());
}

function testContestJSON() {
	var jsonString = 
		"{\"contestID\" : 222, \"projectID\" : 333, \"name\" : \"contest name\", \"shortSummary\" : \"short summary\","+
		" \"prizes\" : [{\"place\" : 1,\"amount\" : 20000},{\"place\" : 2,\"amount\" : 10000}], \"launchDateAndTime\" : \"2008-03-07 01:00\","+
		" \"durationInHours\" : 3, \"winnerAnnouncementDeadline\" : \"2008-03-20 01:00\", \"contestTypeID\" : 1,\"finalFileFormatList\" : [\"format 1\",\"format2\",\"format3\"],"+
		" \"finalFileFormatOther\" : \"zip\", \"documentationUploads\" : [{\"fileName\" : \"JSON file name1\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : "+
		" 77777, \"description\" : \"JSON descrip1\"},{\"fileName\" : \"JSON file name2\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" "+
		" : \"JSON descrip2\"}], \"statusID\" : 3221123, \"contestPayloads\" : [{\"name\" : \"JSON Payload Name1\",\"value\" : "+
		" \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\",\"required\" : true, \"contestTypeID\" : 4444},"+
		" {\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : \"JSON Payload Desc2\",\"required\" : true,"+
		" \"contestTypeID\" : 4444}], \"contestCategoryID\" : 2, \"contestDescriptionAndRequirements\" : \"This is a long desc\", "+
		" \"requiredOrRestrictedColors\" : \"red\", \"requiredOrRestrictedFonts\" : \"arial\", \"sizeRequirements\" : \"11 pt.\", "+
		" \"otherRequirementsOrRestrictions\" : \"other reqs\", \"tcDirectProjectID\" : 1000, \"creatorUserID\" : 7000}";

	var json = eval("(" + jsonString + ")");
	// initialize	
	var contest = new js.topcoder.widgets.bridge.Contest(json);
	
	alert("created Contest using JSON constructor : \n"+contest.toJSON());
}

function testInvalidContestJSON() {
// missing statusID
	var jsonString = 
		"{\"contestID\" : 222, \"projectID\" : 333, \"name\" : \"contest name\", \"shortSummary\" : \"short summary\","+
		" \"prizes\" : [{\"place\" : 1,\"amount\" : 20000},{\"place\" : 2,\"amount\" : 10000}], \"launchDateAndTime\" : \"2008-03-07 01:00\","+
		" \"durationInHours\" : 3, \"winnerAnnouncementDeadline\" : \"2008-03-20 01:00\", \"contestTypeID\" : 1,\"finalFileFormatList\" : [\"format 1\",\"format2\",\"format3\"],"+
		" \"finalFileFormatOther\" : \"zip\", \"documentationUploads\" : [{\"fileName\" : \"JSON file name1\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : "+
		" 77777, \"description\" : \"JSON descrip1\"},{\"fileName\" : \"JSON file name2\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" "+
		" : \"JSON descrip2\"}], \"contestPayloads\" : [{\"name\" : \"JSON Payload Name1\",\"value\" : "+
		" \"JSON Payload Value1\",\"description\" : \"JSON Payload Desc1\",\"required\" : true, \"contestTypeID\" : 4444},"+
		" {\"name\" : \"JSON Payload Name2\",\"value\" : \"JSON Payload Value2\",\"description\" : \"JSON Payload Desc2\",\"required\" : true,"+
		" \"contestTypeID\" : 4444}], \"contestCategoryID\" : 2, \"contestDescriptionAndRequirements\" : \"This is a long desc\", "+
		" \"requiredOrRestrictedColors\" : \"red\", \"requiredOrRestrictedFonts\" : \"arial\", \"sizeRequirements\" : \"11 pt.\", "+
		" \"otherRequirementsOrRestrictions\" : \"other reqs\", \"tcDirectProjectID\" : 1000, \"creatorUserID\" : 7000}";

	var json = eval("(" + jsonString + ")");
	// initialize
	try {	
		var contest = new js.topcoder.widgets.bridge.Contest(json);
		alert("created Contest using JSON constructor : \n"+contest.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}

function testContestCategoryEmpty() {
	var category = new js.topcoder.widgets.bridge.ContestCategory();
	category.setContestCategoryID(11111);
	category.setContestName("Name of contest");
	category.setContestDescription("contest Desc");
	category.setContestCategory("contest category");
	
	alert("created Contest Category using empty constructor : \n"+category.toJSON());
}

function testContestCategoryJSON() {
	var jsonString = 
		"{\"contestCategoryID\" : 2222,\"contestName\" : \"JSON Name of contest\","+
		"\"contestDescription\" : \"JSON contest Desc\",\"contestCategory\" : \"JSON contest category\"}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var category = new js.topcoder.widgets.bridge.ContestCategory(json);
	
	alert("created Contest Category using JSON constructor : \n"+category.toJSON());
}

function testInvalidContestCategoryJSON() {
// missing contestName
	var jsonString = 
		"{\"contestCategoryID\" : 2222,"+
		"\"contestDescription\" : \"JSON contest Desc\",\"contestCategory\" : \"JSON contest category\"}";
	var json = eval("(" + jsonString + ")");
	// initialize
	try {	
		var category = new js.topcoder.widgets.bridge.ContestCategory(json);
		alert("created Contest Category using JSON constructor : \n"+category.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}	
}

function testContestPayloadEmpty() {
	var payload = new js.topcoder.widgets.bridge.ContestPayload();
	payload.setName("Payload Name");
	payload.setValue("Payload Value");
	payload.setDescription("Payload Desc");
	payload.setRequired(true);
	payload.setContestTypeID(33333);
		
	alert("created Contest Payload using empty constructor : \n"+payload.toJSON());
}

function testContestPayloadJSON() {
	var jsonString = 
		"{\"name\" : \"JSON Payload Name\",\"value\" : \"JSON Payload Value\",\"description\" : \"JSON Payload Desc\","+
		"\"required\" : true, \"contestTypeID\" : 4444}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var payload = new js.topcoder.widgets.bridge.ContestPayload(json);
	
	alert("created Contest Payload using JSON constructor : \n"+payload.toJSON());
}

function testInvalidContestPayloadJSON() {
// missing required
	var jsonString = 
		"{\"name\" : \"JSON Payload Name\",\"value\" : \"JSON Payload Value\",\"description\" : \"JSON Payload Desc\","+
		"\"contestTypeID\" : 4444}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	try {
		var payload = new js.topcoder.widgets.bridge.ContestPayload(json);
		alert("created Contest Payload using JSON constructor : \n"+payload.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}

function testContestStatusEmpty() {
	var status = new js.topcoder.widgets.bridge.ContestStatus();
	status.setStatusID(32323);
	status.setName("Status Name");
	status.setDescription("Status Desc");
	status.setAllowableNextStatuses([1,2,3,4,5]);
	status.setDisplayIcon("Display ICON");
		
	alert("created Contest Status using empty constructor : \n"+status.toJSON());
}

function testContestStatusJSON() {
	var jsonString = 
		"{\"statusID\" : 32323,\"name\" : \"JSON Status Name\",\"description\" : \"JSON Status Desc\",\"displayIcon\" : \"JSON Display ICON\","+
		" allowableNextStatuses : [5,4,3,2,1]}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var status = new js.topcoder.widgets.bridge.ContestStatus(json);
	
	alert("created Contest Status using JSON constructor : \n"+status.toJSON());
}

function testInvalidContestStatusJSON() {
// missing displayIcon
	var jsonString = 
		"{\"statusID\" : 32323,\"name\" : \"JSON Status Name\",\"description\" : \"JSON Status Desc\","+
		" allowableNextStatuses : [5,4,3,2,1]}";
	var json = eval("(" + jsonString + ")");
	// initialize
	try {	
		var status = new js.topcoder.widgets.bridge.ContestStatus(json);
		alert("created Contest Status using JSON constructor : \n"+status.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}


function testPrizeEmpty() {
	var prize = new js.topcoder.widgets.bridge.Prize();
	prize.setPlace(1);
	prize.setAmount(5000);
		
	alert("created Prize using empty constructor : \n"+prize.toJSON());
}

function testPrizeJSON() {
	var jsonString = 
		"{\"place\" : 2,\"amount\" : 10000}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var prize = new js.topcoder.widgets.bridge.Prize(json);
	
	alert("created Prize using JSON constructor : \n"+prize.toJSON());
}

function testInvalidPrizeJSON() {
// missing amount
	var jsonString = 
		"{\"place\" : 2}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	try {
		var prize = new js.topcoder.widgets.bridge.Prize(json);
		alert("created Prize using JSON constructor : \n"+prize.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}	
}

function testProjectEmpty() {
	var project = new js.topcoder.widgets.bridge.Project();
	project.setProjectID(3321);
	project.setDescription("proj desc");
	project.setName("proj name");
		
	alert("created Project using empty constructor : \n"+project.toJSON());
}

function testProjectJSON() {
	var jsonString = 
		"{\"projectID\" : 2222,\"name\" : \"JSON Proj name\",\"description\" : \"JSON Proj desc\"}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var project = new js.topcoder.widgets.bridge.Project(json);
	
	alert("created Project using JSON constructor : \n"+project.toJSON());
}

function testInvalidProjectJSON() {
// missing projectID
	var jsonString = 
		"{\"name\" : \"JSON Proj name\",\"description\" : \"JSON Proj desc\"}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	try {
		var project = new js.topcoder.widgets.bridge.Project(json);
		alert("created Project using JSON constructor : \n"+project.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}

function testSubmissionEmpty() {
	var submission = new js.topcoder.widgets.bridge.Submission();
	submission.setSubmissionID(1234);
	submission.setSubmitterID(321);
	submission.setSubmissionTimeStamp("2008-03-04 09:00");
	submission.setSubmissionContent("content");
	submission.setContestID(9999);
	submission.setPassedReview(true);
	submission.setPassedScreening(true);
	submission.setPlacement(3);
	submission.setPaidFor(true);
	submission.setPrice(4443332);
	submission.setMarkedForPurchase(true);
	submission.setRemoved(true);
	
	alert("created Submission using empty constructor : \n"+submission.toJSON());
}

function testSubmissionJSON() {
	var jsonString = 
		"{ \"submissionID\" : 1234, \"submitterID\" : 321, \"submissionTimeStamp\" : \"2008-03-04 09:00\" , \"submissionContent\" : \"content\" , "+
		" \"contestID\" : 9999, \"passedScreening\" : true, \"passedReview\" : true, \"placement\" : 3, \"paidFor\" : true, \"price\" : 4443332,"+
		" \"markedForPurchase\" : true, \"removed\" : true}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var submission = new js.topcoder.widgets.bridge.Submission(json);
	
	alert("created Submission using JSON constructor : \n"+submission.toJSON());
}


function testInvalidSubmissionJSON() {
// missing passedReview
	var jsonString = 
		"{ \"submissionID\" : 1234, \"submitterID\" : 321, \"submissionTimeStamp\" : \"2008-03-04 09:00\" , \"submissionContent\" : \"content\" , "+
		" \"contestID\" : 9999, \"passedScreening\" : true, \"placement\" : 3, \"paidFor\" : true, \"price\" : 4443332,"+
		" \"markedForPurchase\" : true, \"removed\" : true}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	try {
		var submission = new js.topcoder.widgets.bridge.Submission(json);
		alert("created Submission using JSON constructor : \n"+submission.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}

function testUpDocEmpty() {
	var updoc = new js.topcoder.widgets.bridge.UploadedDocument();
	updoc.setDocumentID(321321);
	updoc.setContestID(77777);
	updoc.setFileName("file name");
	updoc.setDescription("descrip");
	updoc.setPath("path");
		
	alert("created UploadedDocument using empty constructor : \n"+updoc.toJSON());
}

function testUpDocJSON() {
	var jsonString = 
		"{\"fileName\" : \"JSON file name\",\"path\":\"filePath\",\"documentID\" : 321321,\"contestID\" : 77777, \"description\" : \"JSON descrip\"}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	var updoc = new js.topcoder.widgets.bridge.UploadedDocument(json);
	
	alert("created UploadedDocument using JSON constructor : \n"+updoc.toJSON());
}

function testInvalidUpDocJSON() {
// missing documentID
	var jsonString = 
		"{\"fileName\" : \"JSON file name\",\"path\":\"filePath\",\"contestID\" : 77777, \"description\" : \"JSON descrip\"}";
	var json = eval("(" + jsonString + ")");
	// initialize	
	try {
		var updoc = new js.topcoder.widgets.bridge.UploadedDocument(json);
		alert("created UploadedDocument using JSON constructor : \n"+updoc.toJSON());
	} catch(x) {
		alert("An exception occurred!");
	}
}

</script>
</head>

<body>
Widget Webservices Bridge Demo
<br />
<br />
<span style="color:blue"><b>Tests for Javascript Model/Domain Classes</b></span>
<br />
<ul>
	<li>Test Contest.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestEmpty()" /></li>
	<li>Test Contest.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestJSON()" /></li>
	<li>Test Contest.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidContestJSON()" /></li>
	<li>&nbsp;</li>	
	<li>Test ContestCategory.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestCategoryEmpty()" /></li>
	<li>Test ContestCategory.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestCategoryJSON()" /></li>
	<li>Test ContestCategory.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidContestCategoryJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test ContestPayload.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestPayloadEmpty()" /></li>
	<li>Test ContestPayload.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestPayloadJSON()" /></li>
	<li>Test ContestPayload.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidContestPayloadJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test ContestStatus.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestStatusEmpty()" /></li>
	<li>Test ContestStatus.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testContestStatusJSON()" /></li>
	<li>Test ContestStatus.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidContestStatusJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test Prize.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testPrizeEmpty()" /></li>
	<li>Test Prize.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testPrizeJSON()" /></li>
	<li>Test Prize.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidPrizeJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test Project.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectEmpty()" /></li>
	<li>Test Project.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testProjectJSON()" /></li>
	<li>Test Project.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidProjectJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test Submission.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testSubmissionEmpty()" /></li>
	<li>Test Submission.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testSubmissionJSON()" /></li>
	<li>Test Submission.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidSubmissionJSON()" /></li>	
	<li>&nbsp;</li>
	<li>Test UploadedDocument.js using Empty Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testUpDocEmpty()" /></li>
	<li>Test UploadedDocument.js using JSON Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testUpDocJSON()" /></li>
	<li>Test UploadedDocument.js using invalid JSON to Constructor &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testInvalidUpDocJSON()" /></li>	
</ul>
<br />
<br />
<a href="index.jsp">Back to Index</a>
</body>
</html>
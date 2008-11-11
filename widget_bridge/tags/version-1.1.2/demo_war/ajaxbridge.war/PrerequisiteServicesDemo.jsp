<html>
<head>
<script language='javascript' src="js/AJAXProcessor.js"></script>
<script language='javascript' src="js/WidgetBridgeNamespace.js"></script>
<script language='javascript' src="js/Helper.js"></script>
<script language='javascript' src="js/IllegalArgumentException.js"></script>
<script language='javascript' src="js/InvalidResponseException.js"></script>
<script language='javascript' src="js/PrerequisiteDocument.js"></script>
<script language='javascript' src="js/PrerequisiteService.js"></script>

<script language="JavaScript">

/**
 * Demos getAllPrerequisiteDocuments.
 */
function testGetAllPrerequisiteDocuments() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function(documents) {
		for (var i = 0; i < documents.length; i++) {
			alert("Success = Document returned index["+i+"]: documentID : "+documents[i].getDocumentID()+"; version : "+documents[i].getVersion()+
				"; versionDate : "+documents[i].getVersionDate()+"; name : "+documents[i].getName()+
				"; content : "+documents[i].getContent());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	PrerequisiteService.getAllPrerequisiteDocuments(success,error);
}

/**
 * Demos getPrerequisiteDocuments.
 */
function testGetPrerequisiteDocuments() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function(documents) {
		for (var i = 0; i < documents.length; i++) {
			alert("Success = Document returned index["+i+"]: documentID : "+documents[i].getDocumentID()+"; version : "+documents[i].getVersion()+
				"; versionDate : "+documents[i].getVersionDate()+"; name : "+documents[i].getName()+
				"; content : "+documents[i].getContent());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	PrerequisiteService.getPrerequisiteDocuments(1,1,success,error);
}

/**
 * Demos getPrerequisiteDocument.
 */
function testGetPrerequisiteDocument() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function(prerequsitedocument) {
			alert("Success = Document returned : documentID : "+prerequsitedocument.getDocumentID()+"; version : "+prerequsitedocument.getVersion()+
				"; versionDate : "+prerequsitedocument.getVersionDate()+"; name : "+prerequsitedocument.getName()+
				"; content : "+prerequsitedocument.getContent());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

	PrerequisiteService.getPrerequisiteDocument(1,1,success,error);
}

/**
 * Demos recordMemberAnswer.
 */
function testRecordMemberAnswer() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function() {
	   alert("Successfully recorded it!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

    var prerequsitedocument = new js.topcoder.widgets.bridge.PrerequisiteDocument();
    prerequsitedocument.setDocumentID(1);
    prerequsitedocument.setVersion(1);
    prerequsitedocument.setVersionDate("2008-06-01 07:30");
    prerequsitedocument.setName("name");
    prerequsitedocument.setContent("content");
	PrerequisiteService.recordMemberAnswer(1,"2008-06-01 08:30",true,prerequsitedocument,1,success,error);
}

// ==== errors ====

/**
 * Demos error getPrerequisiteDocuments.
 */
function testGetPrerequisiteDocumentsError() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function(documents) {
		for (var i = 0; i < documents.length; i++) {
			alert("Success = Document returned index["+i+"]: documentID : "+documents[i].getDocumentID()+"; version : "+documents[i].getVersion()+
				"; versionDate : "+documents[i].getVersionDate()+"; name : "+documents[i].getName()+
				"; content : "+documents[i].getContent());
		}
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}
	
	//will trigger the error
	PrerequisiteService.getPrerequisiteDocuments(2,1,success,error);
}

/**
 * Demos error getPrerequisiteDocument.
 */
function testGetPrerequisiteDocumentError() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function(prerequsitedocument) {
			alert("Success = Document returned : documentID : "+prerequsitedocument.getDocumentID()+"; version : "+prerequsitedocument.getVersion()+
				"; versionDate : "+prerequsitedocument.getVersionDate()+"; name : "+prerequsitedocument.getName()+
				"; content : "+prerequsitedocument.getContent());
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

    //trigger the error
	PrerequisiteService.getPrerequisiteDocument(10,1,success,error);
}

/**
 * Demos error recordMemberAnswer.
 */
function testRecordMemberAnswerError() {
	var PrerequisiteService = new js.topcoder.widgets.bridge.PrerequisiteService("ajaxBridge");

	// success callback	
	var success = function() {
	   alert("Successfully recorded it!");
	}
	// error callback
	var error = function(errMsg) {
		alert("Error = "+errMsg);
	}

    var prerequsitedocument = new js.topcoder.widgets.bridge.PrerequisiteDocument();
    prerequsitedocument.setDocumentID(1);
    prerequsitedocument.setVersion(1);
    prerequsitedocument.setVersionDate("2008-06-01 07:30");
    prerequsitedocument.setName("name");
    prerequsitedocument.setContent("content");
    //trigger the error
	PrerequisiteService.recordMemberAnswer(2,"2008-06-01 08:30",true,prerequsitedocument,1,success,error);
}

</script>
</head>

<body>
Widget Webservices Bridge Demo
<br />
<br />
<span style="color:blue"><b>Prerequisite Services Demo</b></span>
<br />
<ul>
	<li>Prerequisite.getAllPrerequisiteDocuments() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testGetAllPrerequisiteDocuments()" /></li>
	<li>Prerequisite.getPrerequisiteDocuments() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testGetPrerequisiteDocuments()" /></li>
	<li>Prerequisite.getPrerequisiteDocument() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testGetPrerequisiteDocument()" /></li>
	<li>Prerequisite.recordMemberAnswer() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testRecordMemberAnswer()" /></li>
</ul>
<br />
<br />
<span style="color:red"><b>Some Document Services Error Demo</b></span>
<br />
<ul>
	<li>Prerequisite.getPrerequisiteDocuments() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testGetPrerequisiteDocumentsError()" /></li>
	<li>Prerequisite.getPrerequisiteDocument() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testGetPrerequisiteDocumentError()" /></li>
	<li>Prerequisite.recordMemberAnswer() &nbsp; : &nbsp;<input type="button" value="Execute" onclick="testRecordMemberAnswerError()" /></li>
</ul>
<br />
<br />
<a href="index.jsp">Back to Index</a>
</body>
</html>
/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This JavaScript class defines the operations related to the studio service.
 * It will use the AJAX Processor js component to communicate with the AjaxServlet.
 * Each method has onSuccess and onError callback functions as its arguments, and as
 * the operation is completed in asynchronous mode, so when the operation is completed
 * successfully, onSuccess will be called, otherwise, onError will be called to notify
 * user an error occured.</p>
 *
 * <p>Thread safe: It's immutable and thread-safe.</p>
 *
 * @constructor
 * @author Standlove, pinoydream
 * @version 1.0
 *
 */

/**
 * <p>Constructor with the servletUrl.</p>
 *
 * <p>Simply set the argument to this.servletUrl.</p>
 *
 * @throws IllegalArgumentException if the argument is null or empty string.
 */ 
js.topcoder.widgets.bridge.StudioService = function (/*String*/ servletUrlString) {

	/**
	 * <p>Represents the servlet url to communicate with.
	 * Initialized in constructor, and never changed afterwards.
	 * It must be non-null, non-empty string.</p>
	 *
	 * @private
	 */
	this.servletUrlString = null;
	
	/**
	 * <p>Construction code.</p>
	 */
	if (servletUrlString == null || servletUrlString == "") {
		throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.servletUrlString","servletUrlString should not be null or empty");
	} else {
		this.servletUrlString = servletUrlString;
	}

	/**
	 * <p>Remember this for all responses from Servlet.</p>
	 * <p>
	 * ============
	 * NOTE: the failure response is like (where the $error-message is the error message, and $json-object is
	 * the service response data):
	 * {  "success" : false,"error" : "$error-message"
	 * }
	 *
	 * the success response is like:
	 * { "success" : true,"json" : $json-object
	 * }
	 * ============
	 * </p>
	 */

	/**
	 * <p>
	 * Creates a contest of the specified type for the provided project  asynchronously, if the contest is created
	 * successfully, onSuccess callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or projectID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.createContest = createContest;
	function /* void */ createContest(/* Contest object*/ contest,/* long */ projectID, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check contest
		if (contest == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contest","contest should not be null");
		}
		// check projectID
		if (projectID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.projectID","projectID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=createContest&projectID=" + projectID + "&contest=" + escape(contest.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","createContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","createContest","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","createContest","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// create a contest
	                	var retContest = new js.topcoder.widgets.bridge.Contest(jsonResp.json);	                	
	                	// call the success callback
	                	onSuccess(retContest);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Retrieve the contest from the id  asynchronously, if the contest is retrieved successfully, onSuccess
	 * callback function will be called with the retrieved contest, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or contestID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getContest = getContest;
	function /* void */ getContest(/* long */ contestID, /* ContestHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check contestID
		if (contestID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contestID","contestID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=getContest&contestID=" + contestID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContest","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContest","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContest","Invalid response");
		                }	                
	                	// success
	                	// create a contest
	                	var retContest = new js.topcoder.widgets.bridge.Contest(jsonResp.json);
	                	// call the success callback passing the contest
	                	onSuccess(retContest);
	                }
	           }
	     	}
	     });
	}
	
	/**
	 * <p>
	 * Get all contests asynchronously, if the contests are retrieved successfully, onSuccess
	 * callback function will be called with the retrieved contests, otherwise onError will be called.
	 * </p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getAllContests = getAllContests;
	function /* void */ getAllContests(/* boolean */ onlyDirectProjects, /* ContestsHandler */ onSuccess, /* ErrorHandler */ onError ) {
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		var processor = new AJAXProcessor();
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	sendingText: "service=studio&method=getAllContests&onlyDirectProjects=" + onlyDirectProjects,
	     	onStateChange: function() {
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContests","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContests","Invalid response");
	                }	                
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContests","Invalid response");
		                }	                
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContests","Invalid response");
		                }	                
	                	// success
	                	var newContests = new Array();
	                	var contests = jsonResp.json;
	                	for(var x = 0; x < contests.length; x++) {
	                		var retContest = new js.topcoder.widgets.bridge.Contest(contests[x]);
	                		newContests[x] = retContest;
	                	}
	                	onSuccess(newContests);
	                }
	           }
	     	}
	     });
	}	

	
	/**
	 * <p>
	 * Get the contest for project asynchronously, if the contests are retrieved successfully, onSuccess
	 * callback function will be called with the retrieved contests, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or projectID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getContestsForProject = getContestsForProject;
	function /* void */ getContestsForProject(/* long */ projectID, /* ContestsHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check projectID
		if (projectID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.projectID","projectID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=getContestsForProject&projectID="+projectID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestsForProject","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestsForProject","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestsForProject","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestsForProject","Invalid response");
		                }	                
	                	// success
	                	// create new array of contests
	                	var newContests = new Array();
	                	// get the array of JSON objects
	                	var contests = jsonResp.json;
	                	// convert the array of JSON to array of Contests
	                	for(var x = 0; x < contests.length; x++) {
	                		var retContest = new js.topcoder.widgets.bridge.Contest(contests[x]);
	                		// set to new array
	                		newContests[x] = retContest;
	                	}
	                	// call the success callback passing the project
	                	onSuccess(newContests);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Update the contest status asynchronously, if the contest is updated successfully, onSuccess
	 * callback function will be called with the updated contest, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or contestID < 0 or newStatusID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.updateContestStatus = updateContestStatus;
	function /* void */ updateContestStatus(/* long */ contestID,/* long */ newStatusID, /* ContestHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check contestID
		if (contestID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contestID","contestID should not be less than 0");
		}
		// check newStatusID
		if (newStatusID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.newStatusID","newStatusID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=updateContestStatus&contestID="+contestID+"&newStatusID="+newStatusID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContestStatus","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContestStatus","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContestStatus","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.success) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContestStatus","Invalid response");
		                }	                
	                	// success
	                	// create a contest
	                	var retContest = new js.topcoder.widgets.bridge.Contest(jsonResp.json);
	                	// call the success callback passing the contest
	                	onSuccess(retContest);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Get all contest types asynchronously, if the contest typess are retrieved successfully, onSuccess
	 * callback function will be called with the retrieved contests, otherwise onError will be called.
	 * </p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getAllContestTypes = getAllContestTypes;
	function /* void */ getAllContestTypes(/* ContestTypesHandler */ onSuccess, /* ErrorHandler */ onError ) {
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		var processor = new AJAXProcessor();
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	sendingText: "service=studio&method=getAllContestTypes",
	     	onStateChange: function() {
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContestTypes","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContestTypes","Invalid response");
	                }	                
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContestTypes","Invalid response");
		                }	                
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getAllContestTypes","Invalid response");
		                }	                
	                	// success
	                	var types = new Array();
	                	var typesJSONArray = jsonResp.json;
	                	for(var x = 0; x < typesJSONArray.length; x++) {
	                		var type = new js.topcoder.widgets.bridge.ContestType(typesJSONArray[x]);
	                		types[x] = type;
	                	}
	                	onSuccess(types);
	                }
	           }
	     	}
	     });
	}	

	/**
	 * <p>
	 * Remove the document from contest asynchronously, if the document is removed successfully,
	 * onSuccess callback function will be called with the removed document, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.removeDocumentFromContest = removeDocumentFromContest;
	function /* void */ removeDocumentFromContest(/* Uploaded Document */ document, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check document
		if (document == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.document","document should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=removeDocumentFromContest&document="+ escape(document.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeDocumentFromContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeDocumentFromContest","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeDocumentFromContest","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Retrieve the submissions for a contest asynchronously, if the submissions are retrieved successfully, onSuccess
	 * callback function will be called with the retrieved submissions, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or contestID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.retrieveSubmissionsForContest = retrieveSubmissionsForContest;
	function /* void */ retrieveSubmissionsForContest(/* long */ contestID, /* SubmissionsHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check contestID
		if (contestID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contestID","contestID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=retrieveSubmissionsForContest&contestID="+contestID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmissionsForContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmissionsForContest","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmissionsForContest","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmissionsForContest","Invalid response");
		                }	                
	                	// success
	                	// create new array of submissions
	                	var newSubmissions = new Array();
	                	// get the array of JSON objects
	                	var submissions = jsonResp.json;
	                	// convert the array of JSON to array of Submissions
	                	for(var x = 0; x < submissions.length; x++) {
	                		var retSubmission = new js.topcoder.widgets.bridge.Submission(submissions[x]);
	                		// set to new array
	                		newSubmissions[x] = retSubmission;
	                	}
	                	// call the success callback passing the submission
	                	onSuccess(newSubmissions);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Update the submission asynchronously, if the submissions are updated successfully, onSuccess
	 * callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.updateSubmission = updateSubmission;
	function /* void */ updateSubmission(/* Submission */ submission, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check submission
		if (submission == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.submission","submission should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=updateSubmission&submission="+ escape(submission.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateSubmission","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateSubmission","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
	                	if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateSubmission","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}

	
	/**
	 * <p>
	 * Update the contest  asynchronously, if the contest is updated successfully,
	 * onSuccess callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.updateContest = updateContest;
	function /* void */ updateContest(/* Contest */ contest, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check contest
		if (contest == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contest","contest should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=updateContest&contest="+ escape(contest.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContest","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","updateContest","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Retrieve the submission from the related id  asynchronously, if the submission is retrieved successfully,
	 * onSuccess callback function will be called with the retrieved submission, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or submissionID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.retrieveSubmission = retrieveSubmission;
	function /* void */ retrieveSubmission(/* long */ submissionID, /* SubmissionHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check submissionID
		if (submissionID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.submissionID","submissionID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=retrieveSubmission&submissionID="+submissionID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmission","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmission","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmission","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveSubmission","Invalid response");
			            }	                
	                	// success
                		var retSubmission = new js.topcoder.widgets.bridge.Submission(jsonResp.json);
	                	// call the success callback passing the submission
	                	onSuccess(retSubmission);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Remove the submission non piad  asynchronously, if the submission is removed successfully, onSuccess
	 * callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or submissionID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.removeSubmission = removeSubmission;
	function /* void */ removeSubmission(/* long */ submissionID, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check submissionID
		if (submissionID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.submissionID","submissionID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=removeSubmission&submissionID="+submissionID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeSubmission","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeSubmission","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","removeSubmission","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Retrieve the submission from the related id  asynchronously, if the submission is retrieved successfully,
	 * onSuccess callback function will be called with the retrieved submission, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null or userID < 0
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.retrieveAllSubmissionsByMember = retrieveAllSubmissionsByMember;
	function /* void */ retrieveAllSubmissionsByMember(/* long */ userID, /* SubmissionsHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check userID
		if (userID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.userID","userID should not be less than 0");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=retrieveAllSubmissionsByMember&userID="+userID,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveAllSubmissionsByMember","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveAllSubmissionsByMember","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveAllSubmissionsByMember","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","retrieveAllSubmissionsByMember","Invalid response");
			            }	                
	                	// success
	                	// create new array of submissions
	                	var newSubmissions = new Array();
	                	// get the array of JSON objects
	                	var submissions = jsonResp.json;
	                	// convert the array of JSON to array of Submissions
	                	for(var x = 0; x < submissions.length; x++) {
	                		var retSubmission = new js.topcoder.widgets.bridge.Submission(submissions[x]);
	                		// set to new array
	                		newSubmissions[x] = retSubmission;
	                	}
	                	// call the success callback passing the submission
	                	onSuccess(newSubmissions);
	                }
	           }
	     	}
	     });
	}


	/**
	 * <p>
	 * Returns a list of all of the final formats allowed asynchronously, if the submission file
	 * types are retrieved successfully, onSuccess callback function will be called with the retrieved
	 * submission file types, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getSubmissionFileTypes = getSubmissionFileTypes;
	function /* void */ getSubmissionFileTypes(/* StringsHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=getSubmissionFileTypes",
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getSubmissionFileTypes","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getSubmissionFileTypes","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getSubmissionFileTypes","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getSubmissionFileTypes","Invalid response");
			            }	                
	                	// success
	                	// get the array of JSON objects
	                	var fileTypes = jsonResp.json;
	                	onSuccess(fileTypes);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Returns list of all available statuses asynchronously, if the contest statuses are retrieved successfully,
	 * onSuccess callback function will be called with the retrieved contest statuses, otherwise
	 * onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.getContestStatuses = getContestStatuses;
	function /* void */ getContestStatuses(/* ContestStatusesHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=getContestStatuses",
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestStatuses","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestStatuses","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestStatuses","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","getContestStatuses","Invalid response");
			            }
	                	// success
	                	// create new array of statuses
	                	var newContestStatuses = new Array();
	                	// get the array of JSON objects
	                	var statuses = jsonResp.json;
	                	// convert the array of JSON to array of Contests Statuses
	                	for(var x = 0; x < statuses.length; x++) {
	                		var retStatus = new js.topcoder.widgets.bridge.ContestStatus(statuses[x]);
	                		// set to new array
	                		newContestStatuses[x] = retStatus;
	                	}
	                	// call the success callback passing the project
	                	onSuccess(newContestStatuses);
	                }
	           }
	     	}
	     });
	}
	


	/**
	 * <p>
	 * Links a document to a contest asynchronously.
	 * onSuccess callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.addDocumentToContest = addDocumentToContest;
	function /* void */ addDocumentToContest(/* Document Id */ documentId, /* Contest Id */ contestId, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		if (documentId == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.documentId","documentId should not be null");
		}
		if (contestId == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.contestId","contestId should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=addDocumentToContest&documentId=" + documentId + "&contestId=" + contestId,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","addDocumentToContest","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","addDocumentToContest","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","addDocumentToContest","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}


	/**
	 * <p>
	 * Uploads document asynchronously, if the document is updated successfully,
	 * onSuccess callback function will be called with the updated document, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.uploadDocument = uploadDocument;
	function /* void */ uploadDocument(/* UploadedDocument */ uploadedDocument, /* DocumentHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check data
		if (uploadedDocument == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.uploadedDocument","uploadedDocument should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=uploadDocument&document="+ escape(uploadedDocument.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","uploadDocument","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
		               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","uploadDocument","Invalid response");
		            }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
			               	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","uploadDocument","Invalid response");
			            }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// create a document
	                	var retDocument = new js.topcoder.widgets.bridge.UploadedDocument(jsonResp.json);
	                	// call the success callback passing the contest
	                	onSuccess(retDocument);
	                }
	           }
	     	}
	     });
	}


	/**
	 * <p>
	 * Purchases submission.
	 * onSuccess callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.purchaseSubmission = purchaseSubmission;
	function /* void */ purchaseSubmission(/* Submission Id */ submissionId, /* price */ price, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		if (submissionId == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.submissionId","submissionId should not be null");
		}
		if (price == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.price","price should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=purchaseSubmission&submissionId=" + submissionId + "&price=" + price,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","purchaseSubmission","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","purchaseSubmission","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","purchaseSubmission","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}


	/**
	 * <p>
	 * Selects winner.
	 * onSuccess callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.
	 */
	this.selectWinner = selectWinner;
	function /* void */ selectWinner(/* Submission Id */ submissionId, /* place */ place, /* VoidHandler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		if (submissionId == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.submissionId","submissionId should not be null");
		}
		if (place == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.place","place should not be null");
		}
		// check onSuccess
		if (onSuccess == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onSuccess","onSuccess callback should not be null");
		}
		// check onError
		if (onError == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.onError","onError callback should not be null");
		}
		// Create AJAXProcessor object
		var processor = new AJAXProcessor();
		// Send a request asynchronously
		processor.request({
	    	url:  servletUrlString,
	    	async: true,
	     	method: "POST",
	     	// the json string should be escaped properly here. 
	     	sendingText: "service=studio&method=selectWinner&submissionId=" + submissionId + "&place=" + place,
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","selectWinner","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","selectWinner","Invalid response");
	                }	                
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("studio","selectWinner","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback 
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}
	
} // end

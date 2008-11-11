/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This JavaScript class defines the operations related to the project service.
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
js.topcoder.widgets.bridge.ProjectService = function (/*String*/ servletUrlString) {

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
	 * NOTE: the failure response is like (where the $error-message is the error message, and $json-object is the service response data):
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
	 * Create the project asynchronously, if the project is created successfully, onSuccess callback function will be
	 * called with the created project, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.createProject = createProject;
	function /* void */ createProject(/* Project object */ project, /* Project Handler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check project
		if (project == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.project","project should not be null");
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
	     	sendingText: "service=project&method=createProject&project=" + escape(project.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","createProject","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","createProject","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","createProject","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","createProject","Invalid response");
		                }
	                	// success
	                	// create a project
	                	var retProj = new js.topcoder.widgets.bridge.Project(jsonResp.json);
	                	// call the success callback passing the project
	                	onSuccess(retProj);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Update the project asynchronously, if the project is updated successfully, onSuccess callback function will
	 * be called with the updated project, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.updateProject = updateProject;
	function /* void */ updateProject(/* Project object */ project, /* Project Handler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check project
		if (project == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.project","project should not be null");
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
	     	sendingText: "service=project&method=updateProject&project=" + escape(project.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","updateProject","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","updateProject","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","updateProject","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","updateProject","Invalid response");
		                }
	                	// success
	                	// create a project
	                	var retProj = new js.topcoder.widgets.bridge.Project(jsonResp.json);
	                	// call the success callback passing the project
	                	onSuccess(retProj);
	                }
	           }
	     	}
	     });
	}

	/**
	 * <p>
	 * Delete the project asynchronously, if the project is deleted successfully, onSuccess callback
	 * function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null, or projectID < 0
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.deleteProject = deleteProject;
	function /* void */ deleteProject(/* Project object */ project, /* Void Handler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check project
		if (project == null) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.project","project should not be null");
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
	     	sendingText: "service=project&method=deleteProject&project=" + escape(project.toJSON()),
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","deleteProject","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","deleteProject","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","deleteProject","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
	                	// success
	                	// call the success callback pass a null since there's return is void
	                	onSuccess();
	                }
	           }
	     	}
	     });
	}	

	/**
	 * <p>
	 * Retrieve the project asynchronously, if the project is retrieved successfully, onSuccess callback
	 * function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null, or projectID < 0
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.getProject = getProject;
	function /* void */ getProject(/* long */ projectID, /* Project Handler */ onSuccess, /* ErrorHandler */ onError ) {
		// check first the validity of parameters
		// check project
		if (projectID < 0) {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("parameter.projectID","project ID should not be less than 0");
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
	     	sendingText: "service=project&method=getProject&projectID=" + projectID,   
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProject","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProject","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProject","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProject","Invalid response");
		                }
	                	// success
	                	// create a project
	                	var retProj = new js.topcoder.widgets.bridge.Project(jsonResp.json);
	                	// call the success callback passing the project
	                	onSuccess(retProj);
	                }
	           }
	     	}
	     });
	}	

	/**
	 * <p>
	 * Get all projects asynchronously, if the projects are retrieved successfully, onSuccess
	 * callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.getProjectsForUser = getProjectsForUser;
	function /* void */ getProjectsForUser(/* long */ userID, /* Project Handler */ onSuccess, /* ErrorHandler */ onError ) {
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
	     	sendingText: "service=project&method=getProjectsForUser&userID=" + userID,   
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProjectsForUser","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProjectsForUser","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProjectsForUser","Invalid response");
		                }	                
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getProjectsForUser","Invalid response");
		                }	                
	                	// success
	                	// create new array of projects
	                	var newProjects = new Array();
	                	// get the array of JSON objects
	                	var projects = jsonResp.json;
	                	// convert the array of JSON to array of Projects
	                	for(var x = 0; x < projects.length; x++) {
	                		var retProj = new js.topcoder.widgets.bridge.Project(projects[x]);
	                		// set to new array
	                		newProjects[x] = retProj;
	                	}
	                	// call the success callback passing the project
	                	onSuccess(newProjects);
	                }
	           }
	     	}
	     });
	}
	
	/**
	 * <p>
	 * Get all projects asynchronously, if the projects are retrieved successfully, onSuccess
	 * callback function will be called, otherwise onError will be called.</p>
	 *
	 * @throws IllegalArgumentException if any argument is null
	 * @throws InvalidResponseException if the received response is invalid.</p>
	 */
	this.getAllProjects = getAllProjects;
	function /* void */ getAllProjects(/* Project Handler */ onSuccess, /* ErrorHandler */ onError ) {
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
	     	sendingText: "service=project&method=getAllProjects",   
	     	onStateChange: function() {
	        	// Handle the response
	           	if (processor.getState() == 4 && processor.getStatus() == 200) {
	            	var response = processor.getResponseText();
	                var jsonResp = eval("(" + response + ")");
	                // check response
	                if (jsonResp == null) {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getAllProjects","Invalid response");
	                }
	                if (typeof(jsonResp.success) == "undefined") {
	                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getAllProjects","Invalid response");
	                }
	                // now check if valid or not
	                if (jsonResp.success == false) {
		                if (typeof(jsonResp.error) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getAllProjects","Invalid response");
		                }
	                	// errors
	                	// call error handler with error message
	                	onError(jsonResp.error);
	                } else {
		                if (typeof(jsonResp.json) == "undefined") {
		                	throw new js.topcoder.widgets.bridge.InvalidResponseException("project","getAllProjects","Invalid response");
		                }
	                	// success
	                	// create new array of projects
	                	var newProjects = new Array();
	                	// get the array of JSON objects
	                	var projects = jsonResp.json;
	                	// convert the array of JSON to array of Projects
	                	for(var x = 0; x < projects.length; x++) {
	                		var retProj = new js.topcoder.widgets.bridge.Project(projects[x]);
	                		// set to new array
	                		newProjects[x] = retProj;
	                	}
	                	// call the success callback passing the project
	                	onSuccess(newProjects);
	                }
	           }
	     	}
	     });
	}
	
	
} // end

/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes a AJAX servlet URL. It will use this URL to do ajax call.
 *
 * @param {string} newServletUrlString AJAX servlet URL string
 *
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class PrerequisiteService
 *
 * This is a client side service class which will asynchronously call server side Prerequisite Service.
 * It will use the AJAX Processor js component to communicate with the AjaxServlet.Each method has
 * onSuccess and onError callback functions as its arguments, and as the operation is completed in
 * asynchronous mode, so when the operation is completed successfully, onSuccess will be called, otherwise,
 * onError will be called to notify user an error occured
 * NOTE: the failure response is like:
 * {
 * "success" : false,"error" : "$error-message"
 * }
 * the success response is like:
 * {
 * "success" : true,"json" : $json-object
 * }
 * where the $error-message is the error message, and $json-object is the service response data
 *
 * Thread Safety:
 * This class is mutable and not thread-safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here. Anyway, the
 * external code should be careful if several threads are used to communicate with this class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.PrerequisiteService = function(newServletUrlString){
    /**
     * Service name. It is used to identify the service type.
     *
     * @private
     * @final
     * @type String
     */
    var SERVICE_NAME = "prerequisite";

    /**
     * The variable represents the AJAX servlet URL. It must be a non-null,non-empty string.
     *
     * @private
     * @type string
     */
    var servletUrlString = null;

    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

    /**
     * Abbreviation for IllegalArgumentException class.
     *
     * @type function
     */
    var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;

    /**
     * Abbreviation for InvalidResponseException class.
     *
     * @type function
     */
    var InvalidResponseException = js.topcoder.widgets.bridge.InvalidResponseException;

    /**
     * Abbreviation for PrerequisiteDocument class.
     *
     * @type function
     */
    var PrerequisiteDocument = js.topcoder.widgets.bridge.PrerequisiteDocument;

    /**
     * Gets all prerequsite documents asynchronously, if the documents are retrieved successfully,
     * onSuccess callback function will be called with the retrieved documents, otherwise onError
     * will be called.
     *
     * @param {function} onSuccess success callback function
     * @param {function} onError failure callback function
     * @throws IllegalArgumentException if any argument is undefined or null or not function type
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getAllPrerequisiteDocuments = function(onSuccess, onError){
        Helper.checkDefined(onSuccess, "success callback", "function");
        Helper.checkDefined(onError, "error callback", "function");

        var processor = new AJAXProcessor();
        var methodName = "getAllPrerequisiteDocuments";
        processor.request({
            url: servletUrlString,
            async: true,
            method: "POST",
            sendingText: "service=" + SERVICE_NAME + "&method=" + methodName,
            onStateChange: function(){
                // Handle the response
                if (processor.getState() == 4 && processor.getStatus() == 200) {
                    var jsonResponse = getJSONResponse(processor.getResponseText(), methodName);
                    if (jsonResponse.success == false) {
                        onError(jsonResponse.error);
                    }
                    else {
                        var documents = new Array();
                        var jsonDocuments = jsonResponse.json;
                        for (var i = 0; i < jsonDocuments.length; i++) {
                            documents[i] = new PrerequisiteDocument(jsonDocuments[i]);
                        }
                        onSuccess(documents);
                    }
                }
            }
        });
    }

    /**
     * Gets prerequsite documents for competition and role asynchronously, if the documents are retrieved
     *  successfully, onSuccess callback function will be called with the retrieved documents, otherwise
     *  onError will be called.
     *
     * @param {number} competitionID competition id
     * @param {number} roleID role id
     * @param {function} onSuccess success callback
     * @param {function} onError failure callback
     * @throws IllegalArgumentException if any function argument is undefined or null or not function type
     *  or id is undefined or null or not number or less than 0
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getPrerequisiteDocuments = function(competitionID, roleID, onSuccess, onError){
        Helper.checkGreaterOrEqual(competitionID, "competition id", 0);
        Helper.checkGreaterOrEqual(roleID, "role id", 0);
        Helper.checkDefined(onSuccess, "success callback", "function");
        Helper.checkDefined(onError, "error callback", "function");

        var processor = new AJAXProcessor();
        var methodName = "getPrerequisiteDocuments";
		var sendingText = "service=" + SERVICE_NAME + "&method=" + methodName;
		sendingText += "&competitionID=" + competitionID + "&roleID=" + roleID;
        processor.request({
            url: servletUrlString,
            async: true,
            method: "POST",
            sendingText: sendingText,
            onStateChange: function(){
                // Handle the response
                if (processor.getState() == 4 && processor.getStatus() == 200) {
                    var jsonResponse = getJSONResponse(processor.getResponseText(), methodName);
                    if (jsonResponse.success == false) {
                        onError(jsonResponse.error);
                    }
                    else {
                        var documents = new Array();
                        var jsonDocuments = jsonResponse.json;
                        for (var i = 0; i < jsonDocuments.length; i++) {
                            documents[i] = new PrerequisiteDocument(jsonDocuments[i]);
                        }
                        onSuccess(documents);
                    }
                }
            }
        });
    }

    /**
     *  Gets prerequsite document by id and version asynchronously, if the document is retrieved
     *  successfully, onSuccess callback function will be called with the retrieved document,
     *  otherwise onError will be called.
     *
     * @param {number} documentID document id
     * @param {number} version the version of the document
     * @param {function} onSuccess success callback
     * @param {function} onError error callback
     * @throws IllegalArgumentException if any function argument is undefined or null or not function type
     *  or id is undefined or null or not number or less than 0
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.getPrerequisiteDocument = function(documentID, version, onSuccess, onError){
        Helper.checkGreaterOrEqual(documentID, "document id", 0);
        Helper.checkGreaterOrEqual(version, "version", 0);
        Helper.checkDefined(onSuccess, "success callback", "function");
        Helper.checkDefined(onError, "error callback", "function");

        var processor = new AJAXProcessor();
        var methodName = "getPrerequisiteDocument";
		var sendingText = "service=" + SERVICE_NAME + "&method=" + methodName;
		sendingText += "&documentID=" + documentID + "&version=" + version;
        processor.request({
            url: servletUrlString,
            async: true,
            method: "POST",
            sendingText: sendingText,
            onStateChange: function(){
                // Handle the response
                if (processor.getState() == 4 && processor.getStatus() == 200) {
                    var jsonResponse = getJSONResponse(processor.getResponseText(), methodName);
                    if (jsonResponse.success == false) {
                        onError(jsonResponse.error);
                    }
                    else {
                        onSuccess(new PrerequisiteDocument(jsonResponse.json));
                    }
                }
            }
        });
    }


    /**
     *  Records member answer asynchronously, if the call is returned succcesffuly, onSuccess callback
     *  function will be called,  otherwise onError will be called.
     *
     * @param {number} competitionID competition id
     * @param {string} timestamp time to answer it
     * @param {boolean} agrees indicates if the user has agreed  document
     * @param {PrerequisiteDocument} prerequisiteDocument document concerned
     * @param {number} roleID role id
     * @param {function} onSuccess success callback
     * @param {function} onError error call back
     * @throws IllegalArgumentException if any function argument is undefined or null or not function type
     *  or id is undefined or null or not number or less than 0 or timestamp is undefined or null or not string
     *  type or agrees is undefined or null or not boolean type or prerequisiteDocument is undefined or null or
     *  not a valid object
     * @throws InvalidResponseException if the received response is invalid.
     */
    this.recordMemberAnswer = function(competitionID, timestamp, agrees, prerequisiteDocument, roleID, onSuccess, onError){
        Helper.checkGreaterOrEqual(competitionID, "competition id", 0);
        Helper.checkString(timestamp, "timestamp");
        Helper.checkDefined(agrees, "agrees","boolean");
		Helper.checkDefined(prerequisiteDocument, "prerequisite document","object");
        Helper.checkGreaterOrEqual(roleID, "role id", 0);
        Helper.checkDefined(onSuccess, "success callback", "function");
        Helper.checkDefined(onError, "error callback", "function");

        var processor = new AJAXProcessor();
        var methodName = "recordMemberAnswer";
		var sendingText = "service=" + SERVICE_NAME + "&method=" + methodName;
		sendingText += "&competitionID=" + competitionID + "&timestamp=" + timestamp;
		sendingText += "&agrees=" + agrees + "&prerequisiteDocument=" + escape(prerequisiteDocument.toJSON());
		sendingText += "&roleID="+roleID;
        processor.request({
            url: servletUrlString,
            async: true,
            method: "POST",
            sendingText: sendingText,
            onStateChange: function(){
                // Handle the response
                if (processor.getState() == 4 && processor.getStatus() == 200) {
					//no json return
                    var jsonResponse = getJSONResponse(processor.getResponseText(), methodName,true);
                    if (jsonResponse.success == false) {
                        onError(jsonResponse.error);
                    }
                    else {
                        onSuccess();
                    }
                }
            }
        });
    }

    /**
     * Returns JSON response to see if it is a valid response. InvalidResponseException will be thrown if it is
     * identified as invalid response.
     *
     * @param {string} response JSON response string
     * @param {string} methodName method name
     * @param {boolean} noJSONReturned true if there is no JSON needs to be returned, false or undefined/null if
     *    there is a JSON object returned
     * @return {JSONObject} response JSONObject
     * @throws InvalidResponseException if the response is not a valid JSON response
     */
    function getJSONResponse(response, methodName,noJSONReturned){
		if(noJSONReturned == null){
			noJSONReturned = false;
		}

        var jsonResponse = eval("(" + response + ")");
        var errorMsg = "invalid response";
        if (jsonResponse == null) {
            throw new InvalidResponseException(SERVICE_NAME, methodName, errorMsg);
        }
        if (typeof jsonResponse.success == "undefined") {
            throw new InvalidResponseException(SERVICE_NAME, methodName, errorMsg);
        }
        if (jsonResponse.success == false) {
            if (typeof jsonResponse.error == "undefined") {
                throw new InvalidResponseException(SERVICE_NAME, methodName, errorMsg);
            }
        }
        else {
            if (!noJSONReturned && typeof jsonResponse.json == "undefined") {
                throw new InvalidResponseException(SERVICE_NAME, methodName, errorMsg);
            }
        }
        // looks good, let's return the response as JSON object
        return jsonResponse;
    }

    //constructor code
    Helper.checkString(newServletUrlString, "servlet url string");
    servletUrlString = newServletUrlString;
}

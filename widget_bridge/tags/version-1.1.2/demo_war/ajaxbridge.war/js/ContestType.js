/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes JSON object and assign value to each internal fields.
 *
 * @param {JSONObject} json JSON object with will be converted to ContestType
 *
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class ContestType
 *
 * This class is a client side mapping for ContestTypeData entity in service side. It also encapsulates
 * JSON object coversion.
 * Thread Safety:
 * This class is mutable and not thread-safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here. Anyway, the
 * external code should be careful if several threads are used to communicate with this class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.ContestType = function(json){
    /**
     * This variable represents contest type id. It can not be undefiend or null or less than -1 and its initial value
     *  is -1 which means it is not set yet.
     *
     * @private
     * @type number
     */
     var contestTypeID;

    /**
     * This variable represents contest type description. It can not be undefined or null.
     *
     * @private
     * @type string
     */
     var description;

    /**
     * This variable represents a boolean value to determine if it requires preview file. It can not
     * be undefined or null.
     *
     * @private
     * @type boolean
     */
     var requirePreviewFile = false;

    /**
     * This variable represents a boolean value to determine if it requires preview image. It can not
     * be undefined or null.
     *
     * @private
     * @type boolean
     */
    var requirePreviewImage;

    /**
     * This variable represents an Array of ContestPayload JSON string. See Contest.js and it follows the current convention.
     * It can not be undefined or null.
     *
     * @private
     * @type Array
     */
     var config;

    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

    /**
     * Abbreviation for IllegalArgumentException class.
     */
    var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;

    /**
     * A simple setter for contestTypeID internal field.
     *
     * @param {number} newContestTypeID the value of contest type id.
     * @throws IllegalArgumentException if the argument is null or undefined value or less than 0.
     */
    this.setContestTypeID = function(newContestTypeID){
        Helper.checkGreaterOrEqual(newContestTypeID, "contest type id", 0);
        contestTypeID = newContestTypeID;
    }

    /**
     * A simple getter for contestTypeID internal field.
     *
     * @return {number} contestTypeID internal field.
     */
    this.getContestTypeID = function(){
        return contestTypeID;
    }

    /**
     * A simple setter for description internal field.
     *
     * @param {string} newDescription the value of new description
     * @throws IllegalArgumentException if the argument is null or undefined value or not string type
     */
    this.setDescription = function(newDescription){
        Helper.checkDefined(newDescription, "description", "string");
        description = newDescription;
    }

    /**
     * A simple getter for description internal field.
     *
     * @return {string} description internal field.
     */
    this.getDescription = function(){
        return description;
    }

    /**
     * A simple setter for requirePreviewFile internal field.
     *
     * @param {string} newRequirePreviewFile the value of new requirePreviewFile
     * @throws IllegalArgumentException if the argument is null or undefined value or not boolean type
     */
    this.setRequirePreviewFile = function(newRequirePreviewFile){
        Helper.checkDefined(newRequirePreviewFile, "requirePreviewFile", "boolean");
        requirePreviewFile = newRequirePreviewFile;
    }

    /**
     * A simple getter for requirePreviewFile internal field.
     *
     * @return {string} requirePreviewFile internal field.
     */
    this.getRequirePreviewFile = function(){
        return requirePreviewFile;
    }

    /**
     * A simple setter for requirePreviewImage internal field.
     *
     * @param {string} newRequirePreviewImage the value of new requirePreviewImage
     * @throws IllegalArgumentException if the argument is null or undefined value or not boolean type
     */
    this.setRequirePreviewImage = function(newRequirePreviewImage){
        Helper.checkDefined(newRequirePreviewImage, "requirePreviewImage", "boolean");
        requirePreviewImage = newRequirePreviewImage;
    }

    /**
     * A simple getter for requirePreviewImage internal field.
     *
     * @return {string} requirePreviewImage internal field.
     */
    this.getRequirePreviewImage = function(){
        return requirePreviewImage;
    }

    /**
     * A simple setter for config internal field.
     *
     * @param {Array} newConfig an array of ContestPayload JSON strings
     * @throws IllegalArgumentException if the argument is null or undefined or not an Array type
     */
    this.setConfig = function(newConfig) {
    	Helper.checkDefined(newConfig, "config", "object");
    	if(!(newConfig instanceof Array)){
    		throw new IllegalArgumentException("ContestType#setConfig","config should be Array type");
    	}
    	config = newConfig;
    }

    /**
     * A simple getter for config internal field.
     *
     * @return {Array} config internal field
     */
    this.getConfig = function() {
    	return config;
    }

    /**
     * Convert the current instance into JSON string.
     *
     * @return a JSON string representing current object
     */
    this.toJSON = function(){
        var result = "{";
        result += "\"contestTypeID\":" + contestTypeID;
        result += ",\"description\":\"" + description+"\"";
        result += ",\"requirePreviewFile\":" + requirePreviewFile;
        result += ",\"requirePreviewImage\":" + requirePreviewImage;
        result += ",\"config\": [" + config.join(",")+"]";
		result += "}";
		return result;
    }

    //constructor code
    if (json != null) {
        this.setContestTypeID(json.contestTypeID);
        this.setDescription(json.description);
        this.setRequirePreviewFile(json.requirePreviewFile);
        this.setRequirePreviewImage(json.requirePreviewImage);

        // set config
    	Helper.checkDefined(json.config, "json.config", "object");
    	if(!(json.config instanceof Array)){
    		throw new IllegalArgumentException("json.config","json.config should be Array type");
    	}
    	var jsonStringArray = new Array();
    	for( var i=0; i< json.config.length; i++) {
    		// we follow other classes, but do we just use ContestPayload or its JSON String?
    		jsonStringArray[i] = new js.topcoder.widgets.bridge.ContestPayload(json.config[i]).toJSON();
    	}
    	this.setConfig(jsonStringArray);
    }

}
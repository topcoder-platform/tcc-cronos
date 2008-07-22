/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes JSON object and assign value to each internal fields.
 *
 * @param {JSONObject} json JSON object with will be converted to PrerequisiteDocument
 *
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class PrerequisiteDocument
 *
 * This class is a client side mapping for PrerequisiteDocument entity in service side. It also encapsulates
 * JSON object coversion.
 * Thread Safety:
 * This class is mutable and not thread-safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here. Anyway, the
 * external code should be careful if several threads are used to communicate with this class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.PrerequisiteDocument = function(json){
    /**
     * This variable represents documentID. It can not be undefiend or null or less than -1 and its initial value
     *  is -1 which means it is not set yet.
     *
     *
     * @private
     * @type number
     */
    var documentID = -1;


    /**
     * This variable represents document version number. It can not be undefiend or null or less  than -1 and its
     * initial value is -1 which emans it is not set yet.
     *
     * @private
     * @type number
     */
    var version = -1;


    /**
     * This variable represents version date string. It can not be undefined or null. It should be assigned
     * date string as "2008-05-01 09:00".
     *
     * @private
     * @type string
     */
    var versionDate = "";

    /**
     * This variable represents document name. It can not be undefined or null.
     *
     * @private
     * @type string
     */
    var name = "";

    /**
     * This variable represents document content. It can not be undefined or null.
     *
     * @private
     * @type string
     */
    var content = "";

    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

    /**
     * Abbreviation for IllegalArgumentException class.
     */
    var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;

    /**
     * A simple setter for documentID internal field.
     *
     * @param {number} newDocumentID the value of document id.
     * @throws IllegalArgumentException if the argument is null or undefined value or less than 0.
     */
    this.setDocumentID = function(newDocumentID){
        Helper.checkGreaterOrEqual(newDocumentID, "document id", 0);
        documentID = newDocumentID;
    }

    /**
     * A simple getter for documentID internal field.
     *
     * @return {number} documentID internal field.
     */
    this.getDocumentID = function(){
        return documentID;
    }

    /**
     * A simple setter for version internal field.
     *
     * @param {number} newVersion the value of new version.
     * @throws IllegalArgumentException if the argument is null or undefined value or less than 0.
     */
    this.setVersion = function(newVersion){
        Helper.checkGreaterOrEqual(newVersion, "version", 0);
        version = newVersion;
    }

    /**
     * A simple getter for version internal field.
     *
     * @return {number} version internal field.
     */
    this.getVersion = function(){
        return version;
    }

    /**
     * A simple setter for versionDate internal field.
     *
     * @param {string} newVersionDate the value of new version date string
     * @throws IllegalArgumentException if the argument is null or undefined value or not string type
     */
    this.setVersionDate = function(newVersionDate){
        Helper.checkDefined(newVersionDate, "verionDate", "string");
        versionDate = newVersionDate;
    }

    /**
     * A simple getter for versionDate internal field.
     *
     * @return {string} versionDate internal field.
     */
    this.getVersionDate = function(){
        return versionDate;
    }

    /**
     * A simple setter for name internal field.
     *
     * @param {string} newName the value of new name
     * @throws IllegalArgumentException if the argument is null or undefined value or not string type
     */
    this.setName = function(newName){
        Helper.checkDefined(newName, "name", "string");
        name = newName;
    }

    /**
     * A simple getter for name internal field.
     *
     * @return {string} name internal field.
     */
    this.getName = function(){
        return name;
    }


    /**
     * A simple setter for content internal field.
     *
     * @param {string} newContent the value of new content
     * @throws IllegalArgumentException if the argument is null or undefined value or not string type
     */
    this.setContent = function(newContent){
        Helper.checkDefined(newContent, "content", "string");
        content = newContent;
    }

    /**
     * A simple getter for content internal field.
     *
     * @return {string} content internal field.
     */
    this.getContent = function(){
        return content;
    }

    /**
     * Convert the current instance into JSON string.
     *
     * @return a JSON string representing current object
     */
    this.toJSON = function(){
        var result = "{";
        result += "\"documentID\":" + documentID;
        result += ",\"version\":" + version;
        result += ",\"versionDate\":\"" + Helper.escapeJSONValue(versionDate)+"\"";
        result += ",\"name\":\"" + Helper.escapeJSONValue(name)+"\"";
        result += ",\"content\":\"" + Helper.escapeJSONValue(content)+"\"";
		result += "}";
		return result;
    }

    //constructor code
    if (json != null) {
        this.setDocumentID(json.documentID);
        this.setVersion(json.version);
        this.setVersionDate(json.versionDate);
        this.setName(json.name);
        this.setContent(json.content);
    }
}

/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes JSON object and assign value to each internal fields.
 *
 * @param {JSONObject} json JSON object with will be converted to Medium
 *
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class Medium
 *
 * This class is a client side mapping for MediumData entity in service side. It also encapsulates
 * JSON object coversion.
 * Thread Safety:
 * This class is mutable and not thread-safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here. Anyway, the
 * external code should be careful if several threads are used to communicate with this class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.Medium = function(json){
    /**
     * This variable represents contest type id. It can not be undefiend or null or less than -1 and its initial value
     *  is -1 which means it is not set yet.
     *
     * @private
     * @type number
     */
     var mediumId;

    /**
     * This variable represents contest type description. It can not be undefined or null.
     *
     * @private
     * @type string
     */
     var description;

    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

    /**
     * Abbreviation for IllegalArgumentException class.
     */
    var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;

    /**
     * A simple setter for mediumId internal field.
     *
     * @param {number} newContestTypeID the value of contest type id.
     * @throws IllegalArgumentException if the argument is null or undefined value or less than 0.
     */
    this.setMediumId = function(newMediumId){
        Helper.checkGreaterOrEqual(newMediumId, "medium id", 0);
        mediumId = newMediumId;
    }

    /**
     * A simple getter for mediumId internal field.
     *
     * @return {number} mediumId internal field.
     */
    this.getMediumId = function(){
        return mediumId;
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
     * Convert the current instance into JSON string.
     *
     * @return a JSON string representing current object
     */
    this.toJSON = function(){
        var result = "{";
        result += "\"mediumId\":" + mediumId;
        result += ",\"description\":\"" + Helper.escapeJSONValue(description)+"\"";
		result += "}";
		return result;
    }

    //constructor code
    if (json != null) {
        this.setMediumId(json.mediumId);
        this.setDescription(json.description);
    }

}
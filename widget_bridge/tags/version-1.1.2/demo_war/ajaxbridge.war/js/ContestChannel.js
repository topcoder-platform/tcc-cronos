/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes JSON object and assign value to each internal fields.
 *
 * @param {JSONObject} json JSON object with will be converted to ContestChannel
 *
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class ContestType
 *
 * This class is a client side mapping for ContestChannel entity in service side. It also encapsulates
 * JSON object coversion.
 * Thread Safety:
 * This class is mutable and not thread-safe. But JavaScript execution model is mostly single-threaded.
 * And there is no thread-synchronization features in JavaScript, so it is not an issue here. Anyway, the
 * external code should be careful if several threads are used to communicate with this class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.ContestChannel = function(json){
    /**
     * This variable represents contest channel id. It can not be undefiend or null or less than -1 and its initial value
     *  is -1 which means it is not set yet.
     *
     * @private
     * @type number
     */
     var contestChannelID;

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
     * A simple setter for contestChannelID internal field.
     *
     * @param {number} newContestChannelID the value of contest channel id.
     * @throws IllegalArgumentException if the argument is null or undefined value or less than 0.
     */
    this.setContestChannelID = function(newContestChannelID){
        Helper.checkGreaterOrEqual(newContestChannelID, "contest channel id", 0);
        contestChannelID = newContestChannelID;
    }

    /**
     * A simple getter for contestChannelId internal field.
     *
     * @return {number} contestChannelId internal field.
     */
    this.getContestChannelID = function(){
        return contestChannelID;
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
        result += "\"contestChannelID\":" + contestChannelID;
        result += ",\"description\":\"" + description+"\"";
		result += "}";
		return result;
    }

    //constructor code
    if (json != null) {
        this.setContestChannelId(json.contestChannelId);
        this.setDescription(json.description);
    }
}
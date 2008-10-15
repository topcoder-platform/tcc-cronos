/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the Contest Status data.
 *
 * Thread safety:  Mutable and not thread-safe.</p>
 *
 * @author Standlove, pinoydream
 * @version 1.0
 */

/**
 * <p>Construct the js object with the json object.
 * 
 * Set json's properties to the corresponding properties of this js object.</p>
 *
 * <p>If this constructor is called without a parameter, it serves as an empty default constructor.</p>
 */ 
js.topcoder.widgets.bridge.ContestStatus = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the status id</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be -1, can be greater or equal to 0</p>
	 */
    var statusID /* long */ = -1;

	/**
	 * <p>Represents the name</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var name /* String */ = null;

	/**
	 * <p>Represents the description</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var description /* String */ = null ;

	/**
	 * <p>Represents the allowable Next Statuses</p>
	 * <p>Initial Value: an empty array</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can't be null ,can be empty, can only contain values greater or equal than 0</p>
	 */
    var allowableNextStatuses /* long[] */ = new Array();

	/**
	 * <p>Represents the display icon</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
	var displayIcon /* String */ = null;

    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;

	/**
	 * <p>Construct the js object with the json object.
	 * Set json's properties to the corresponding properties of this js object.
	 *
	 * Note: we can't throw IllegalArgumentException here since we will
	 * support an empty Constructor for this class.
	 *
	 * Note: Setter Methods can't actually be used here since it doesn't
	 * retain the values of the parameters after this object has been created.
	 * The Getter methods won't give the expected result and will return 'undefined' or 'null'.
	 * This is an issue with the use of 'this'. 
	 */ 
	if (json != null) {
		// set the status id
		if (typeof(json.statusID) != "undefined" && typeof(json.statusID) == "number") {		
			that.statusID = json.statusID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.statusID","json.statusID does not exists");
		}		
		// set the name
		if (typeof(json.name) != "undefined" && typeof(json.name) == "string") {		
			that.name = json.name;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.name","json.name does not exists");
		}		
		// set the description
		if (typeof(json.description) != "undefined" && typeof(json.description) == "string") {		
			that.description = json.description;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.description","json.description does not exists");
		}		
		// set the allowable next statuses
		if (typeof(json.allowableNextStatuses) != "undefined" && typeof(json.allowableNextStatuses) == "object") {		
			that.allowableNextStatuses = json.allowableNextStatuses;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.allowableNextStatuses","json.allowableNextStatuses does not exists");
		}		
		// set the display icon
		if (typeof(json.displayIcon) != "undefined" && typeof(json.displayIcon) == "string") {		
			that.displayIcon = json.displayIcon;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.displayIcon","json.displayIcon does not exists");
		}		
	}

    /**
     * <p>Returns the allowableNextStatuses.</p>
     */
    this.getAllowableNextStatuses = function /* long[] */ () {
        return that.allowableNextStatuses;
    }

    /**
     * <p>Sets the allowableNextStatuses.</p>
     */
    this.setAllowableNextStatuses = function /* void */ (/* long[] */ allowableNextStatuses) {
        that.allowableNextStatuses = allowableNextStatuses;
    }

    /**
     * <p>Returns the description.</p>
     */
    this.getDescription = function /* String */ () {
        return that.description;
    }

    /**
     * <p>Sets the description.</p>
     */
    this.setDescription = function /* void */ (/* String */ description) {
        that.description = description;
    }

    /**
     * <p>Returns the displayIcon.</p>
     */
    this.getDisplayIcon = function /* String */ () {
        return that.displayIcon;
    }

    /**
     * <p>Sets the displayIcon.</p>
     */
    this.setDisplayIcon = function /* void */ (/* String */ displayIcon) {
        that.displayIcon = displayIcon;
    }

    /**
     * <p>Returns the name.</p>
     */
    this.getName = function /* String */ () {
        return that.name;
    }

    /**
     * <p>Sets the name.</p>
     */
    this.setName = function /* void */ (/* String */ name) {
        that.name = name;
    }

    /**
     * <p>Returns the statusID.</p>
     */
    this.getStatusID = function /* long */ () {
        return that.statusID;
    }

    /**
     * <p>Sets the statusID.</p>
     */
    this.setStatusID = function /* void */ (/* long */ statusID) {
        that.statusID = statusID;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"statusID" : $statusID,"name" : "$name","description" : "$description",
	 * "allowableNextStatuses" : [ long-value-of-$allowableNextStatuses delimited by comma],
	 * "displayIcon" : "$displayIcon"}
	 */
	this.toJSON = function /* String */ () {
		var allowStatuses = "[";
		var tempStatuses = that.getAllowableNextStatuses();
		allowStatuses += tempStatuses.toString();
		allowStatuses += "]";
		
		return "{\"statusID\" : "+that.getStatusID()+",\"name\" : \""+Helper.escapeJSONValue(that.getName())+
			"\",\"description\" : \""+Helper.escapeJSONValue(that.getDescription())+"\",\"displayIcon\" : \""
                +Helper.escapeJSONValue(that.getDisplayIcon())+"\""+
			", allowableNextStatuses : "+allowStatuses+"}";
	}

} // end

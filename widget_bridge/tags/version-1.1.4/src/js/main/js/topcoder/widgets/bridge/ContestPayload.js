/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the Contest Payload data.
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
js.topcoder.widgets.bridge.ContestPayload = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

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
	 * <p>Represents the value</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var value /* String */ = null;

	/**
	 * <p>Represents the description</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var description /* String */ = null;

	/**
	 * <p>Represents the required</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var required /* boolean */ = false;

	/**
	 * <p>Represents the contestTypeId</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be -1, can be greater or equal to 0</p>
	 */
    var contestTypeID /* long */ = -1;

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
		// set the name
		if (typeof(json.name) != "undefined" && typeof(json.name) == "string") {		
			that.name = json.name;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.name","json.name does not exists");
		}		
		// set the value
		if (typeof(json.value) != "undefined" && typeof(json.value) == "string") {		
			that.value = json.value;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.value","json.value does not exists");
		}		
		// set the description
		if (typeof(json.description) != "undefined" && typeof(json.description) == "string") {		
			that.description = json.description;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.description","json.description does not exists");
		}		
		// set the required
		if (typeof(json.required) != "undefined" && typeof(json.required) == "boolean") {		
			that.required = json.required;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.required","json.required does not exists");
		}		
		// set the contest type id
		if (typeof(json.contestTypeID) != "undefined" && typeof(json.contestTypeID) == "number") {		
			that.contestTypeID = json.contestTypeID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestTypeID","json.contestTypeID does not exists");
		}		
	}

    /**
     * <p>Returns the contestTypeID.</p>
     */
    this.getContestTypeID = function /* long */ () {
        return that.contestTypeID;
    }

    /**
     * <p>Sets the contestTypeID.</p>
     */
    this.setContestTypeID = function /* void */ (/* long */ contestTypeID) {
        that.contestTypeID = contestTypeID;
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
     * <p>Returns the required.</p>
     */
    this.isRequired = function /* boolean */ () {
        return that.required;
    }

    /**
     * <p>Sets the required.</p>
     */
    this.setRequired = function /* void */ (/* boolean */ required) {
        that.required = required;
    }

    /**
     * <p>Returns the value.</p>
     */
    this.getValue = function /* String */ () {
        return that.value;
    }

    /**
     * <p>Sets the value.</p>
     */
    this.setValue = function /* void */ (/* String */ value) {
        that.value = value;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"name" : "$name","value" : "$value","description" : "$description","required" : $required,"contestTypeID" : $contestTypeID}
	 */
	this.toJSON = function /* String */ () {
		return "{\"name\" : \""+Helper.escapeJSONValue(that.getName())+"\",\"value\" : \""
                +Helper.escapeJSONValue(that.getValue())+
			"\",\"description\" : \""+Helper.escapeJSONValue(that.getDescription())+"\",\"required\" : "+that.isRequired()+", \"contestTypeID\" : "+
			that.getContestTypeID()+"}";
	}

} // end

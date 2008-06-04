/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the project data.
 *
 * <p>Thread safety:  Mutable and not thread-safe.</p>
 *
 * @author Standlove, pinoydream
 * @version 1.0
 */

/**
 * <p>Construct the js object with the json object.
 * Set json's properties to the corresponding properties of this js object.</p>
 *
 * <p>If this constructor is called without a parameter, it serves as an empty default constructor.</p>
 */ 
js.topcoder.widgets.bridge.Project = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the project's ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
	var projectID /* long */ = -1;
	
	/**
	 * <p>Represents the project's name</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be null, can be empty</p>
	 */
	var name /* String */ = null;
	
	/**
	 * <p>Represents the project's description</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be null, can be empty</p>
	 */
	var description /* String */ = null;

	/**
	 * <p>Construct the js object with the json object.
	 * Set json's properties to the corresponding properties of this js object.</p>
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
		// set the project ID
		if (typeof(json.projectID) != "undefined" && typeof(json.projectID) == "number") {		
			that.projectID = json.projectID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.projectID","json.projectID does not exists");
		}			
		// set name
		if (typeof(json.name) != "undefined" && typeof(json.name) == "string") {		
			that.name = json.name;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.name","json.name does not exists");
		}			
		// set description
		if (typeof(json.description) != "undefined" && typeof(json.description) == "string") {		
			that.description = json.description;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.description","json.description does not exists");
		}		
	}

	/**
	 * <p>Return the projectID</p>
	 * @return the project ID
	 */
	this.getProjectID = function /* String */ () {
		return that.projectID;
	}

	/**
	 * <p>Set the projectID</p>
	 * @param projectID	the projectID to set
	 * @throws	IllegalArgumentException if id is less than -1
	 */
	this.setProjectID = function /* void */ (/* long */ projectID) {
		that.projectID = projectID;
	}

	/**
	 * <p>Return the name</p>
	 */
	this.getName = function /* String */ () {
		return that.name;
	}

	/**
	 * <p>Set the name</p>
	 * @param name	the name to set, can be null or empty
	 */
	this.setName = function /* void */ (/* String */ name) {
		that.name = name;
	}

	/**
	 * <p>Return the description</p>
	 */
	this.getDescription = function /* String */ () {
		return that.description;
	}

	/**
	 * <p>Set the description</p>
	 * @param description	the description to set, can be null or empty
	 */
	this.setDescription = function /* void */ (/* String */ description) {
		that.description = description;
	}

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value.
	 * 
	 * {"projectID" : $projectID,"name" : "$name","description" : "$description"}
	 */
	this.toJSON = function /* String */ () {
		return "{\"projectID\" : "+that.getProjectID()+",\"name\" : \""+that.getName()+"\",\"description\" : \""+that.getDescription()+"\"}";
	}

} // end

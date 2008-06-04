/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the Contest Category data.
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
js.topcoder.widgets.bridge.ContestCategory = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the contest Category ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be -1, can be greater or equal to 0</p>
	 */
    var contestCategoryID /* long */ = -1;

	/**
	 * <p>Represents the contest name</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var contestName /* String */ = null;

	/**
	 * <p>Represents the competition description.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var contestDescription /* String */ = null;

	/**
	 * <p>Represents the contest parent category: examples are Studio, Software.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var contestCategory /* String */ = null;

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
		// set the contest category id
		if (typeof(json.contestCategoryID) != "undefined" && typeof(json.contestCategoryID) == "number") {		
			that.contestCategoryID = json.contestCategoryID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestCategoryID","json.contestCategoryID does not exists");
		}		
		// set the contest name
		if (typeof(json.contestName) != "undefined" && typeof(json.contestName) == "string") {		
			that.contestName = json.contestName;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestName","json.contestName does not exists");
		}		
		// set the contest description
		if (typeof(json.contestDescription) != "undefined" && typeof(json.contestDescription) == "string") {		
			that.contestDescription = json.contestDescription;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestDescription","json.contestDescription does not exists");
		}		
		// set the contest category
		if (typeof(json.contestCategory) != "undefined" && typeof(json.contestCategory) == "string") {		
			that.contestCategory = json.contestCategory;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestCategory","json.contestCategory does not exists");
		}		
	}

	/**
	 * <p>Returns the contestCategory</p>
	 */
	this.getContestCategory = function /* String */ () {
        return that.contestCategory;
    }

	/**
	 * <p>Sets the contestCategory</p>
	 */
	this.setContestCategory = function /* void */ (/* String */ contestCategory) {
        that.contestCategory = contestCategory;
    }

	/**
	 * <p>Returns the contestCategoryID</p>
	 */
	this.getContestCategoryID = function /* long */ () {
        return that.contestCategoryID;
    }

	/**
	 * <p>Sets the contestCategoryID</p>
	 */
	this.setContestCategoryID = function /* void */ (/* long */ contestCategoryID) {
        that.contestCategoryID = contestCategoryID;
    }

	/**
	 * <p>Returns the contestDescription</p>
	 */
	this.getContestDescription = function /* String */ () {
        return that.contestDescription;
    }

	/**
	 * <p>Sets the contestDescription</p>
	 */
	this.setContestDescription = function /* void */ (/* String */ contestDescription) {
        that.contestDescription = contestDescription;
    }

	/**
	 * <p>Returns the contestName</p>
	 */
	this.getContestName = function /* String */ () {
        return that.contestName;
    }

	/**
	 * <p>Sets the contestName</p>
	 */
	this.setContestName = function /* void */ (/* String */ contestName) {
        that.contestName = contestName;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"contestCategoryID" : $contestCategoryID,"contestName" : "$contestName",
	 * "contestDescription" : "$contestDescription","contestCategory" : "$contestCategory"}
	 */
	this.toJSON = function /* String */ () {
		return "{\"contestCategoryID\" : "+that.getContestCategoryID()+",\"contestName\" : \""+that.getContestName()+
			"\",\"contestDescription\" : \""+that.getContestDescription()+"\",\"contestCategory\" : \""+that.getContestCategory()+"\"}";
	}

} // end

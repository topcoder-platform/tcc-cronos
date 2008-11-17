/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

/**
 * <p>This Java Script object represents the Prize data.
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
js.topcoder.widgets.bridge.Prize = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the place of the Prize</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: greater or equal than -1</p>
	 */
    var place /* int */ = -1;

	/**
	 * <p>Represents the amount of the prize</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var amount /* double */ = -1.0;

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
		// set the place
		if (typeof(json.place) != "undefined" && typeof(json.place) == "number") {		
			that.place = json.place;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.place","json.place does not exists");
		}		
		// set the amount
		if (typeof(json.amount) != "undefined" && typeof(json.amount) == "number") {		
			that.amount = json.amount;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.amount","json.amount does not exists");
		}		
	}

    /**
     * <p>Returns the amount.</p>
     */
    this.getAmount = function /* double */ () {
        return that.amount;
    }

    /**
     * <p>Sets the amount.</p>
     */
    this.setAmount = function /* void */ (/* double */ amount) {
        that.amount = amount;
    }

    /**
     * <p>Returns the place.</p>
     */
    this.getPlace = function /* int */ () {
        return that.place;
    }

    /**
     * <p>Sets the place.</p>
     */
    this.setPlace = function /* void */ (/* int */ place) {
        that.place = place;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"place" : $place,"amount" : $amount}
	 */
	this.toJSON = function /* String */ () {
		return "{\"place\" : "+that.getPlace()+",\"amount\" : "+that.getAmount()+"}";
	}

} // end

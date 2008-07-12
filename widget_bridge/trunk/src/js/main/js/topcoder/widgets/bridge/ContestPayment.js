/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

/**
 * <p>This Java Script object represents the ContestPayment data.
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
js.topcoder.widgets.bridge.ContestPayment = function (/* JSON Object */ json) {
    /**
     * Abbreviation for Helper class.
     */
    var Helper = js.topcoder.widgets.bridge.Helper;
    
    /**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the contestId</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: greater or equal than -1</p>
	 */
    var contestId /* long */ = -1;

	/**
	 * <p>Represents the paymentStatusId</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: greater or equal than -1</p>
	 */
    var paymentStatusId /* long */ = -1;

	/**
	 * <p>Represents the paypalOrderId</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 */
    var paypalOrderId /* string */ = null;

	/**
	 * <p>Represents the prize</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var price /* double */ = -1.0;

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
		// set the contestId
		if (typeof(json.contestId) != "undefined" && typeof(json.contestId) == "number") {		
			that.contestId = json.contestId;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestId","json.contestId does not exists");
		}		
		// set the paymentStatusId
		if (typeof(json.paymentStatusId) != "undefined" && typeof(json.paymentStatusId) == "number") {		
			that.paymentStatusId = json.paymentStatusId;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.paymentStatusId","json.paymentStatusId does not exists");
		}		
		// set the paypalOrderId
		if (typeof(json.paypalOrderId) != "undefined" && typeof(json.paypalOrderId) == "string") {		
			that.paypalOrderId = json.paypalOrderId;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.paypalOrderId","json.paypalOrderId does not exists");
		}		
		// set the price
		if (typeof(json.price) != "undefined" && typeof(json.price) == "number") {		
			that.price = json.price;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.price","json.price does not exists");
		}		
	}

    /**
     * <p>Returns the paymentStatusId.</p>
     */
    this.getPaymentStatusId = function /* long */ () {
        return that.paymentStatusId;
    }

    /**
     * <p>Sets the paymentStatusId.</p>
     */
    this.setPaymentStatusId = function /* void */ (/* long */ paymentStatusId) {
        Helper.checkGreaterOrEqual(paymentStatusId, "paymentStatusId", 0);
        that.paymentStatusId = paymentStatusId;
    }
    
    /**
     * <p>Returns the contestId.</p>
     */
    this.getContestId = function /* long */ () {
        return that.contestId;
    }

    /**
     * <p>Sets the contestId.</p>
     */
    this.setContestId = function /* void */ (/* long */ contestId) {
    	Helper.checkGreaterOrEqual(contestId, "contestId", 0);
    	that.contestId = contestId;
    }

    /**
     * <p>Returns the price.</p>
     */
    this.getPrize = function /* double */ () {
        return that.price;
    }

    /**
     * <p>Sets the price.</p>
     */
    this.setPrize = function /* void */ (/* double */ price) {
    	Helper.checkGreaterOrEqual(price, "price", 0);
        that.price = price;
    }

    /**
     * <p>Returns the paypalOrderId.</p>
     */
    this.getPaypalOrderId = function /* string */ () {
        return that.paypalOrderId;
    }

    /**
     * <p>Sets the paypalOrderId.</p>
     */
    this.setPaypalOrderId = function /* void */ (/* string */ paypalOrderId) {
        that.paypalOrderId = paypalOrderId;
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
		return "{\"contestId\" : "+that.getContestId()+",\"paypalOrderId\" : \"" + this.getPaypalOrderId() +
		        "\",\"paymentStatusId\" : " + that.getPaymentStatusId() + ",\"price\" : "+that.getPrize()+"}";
	}
} // end

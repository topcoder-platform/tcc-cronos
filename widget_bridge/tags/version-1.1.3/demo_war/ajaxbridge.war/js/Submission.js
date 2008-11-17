/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the Submission data.
 *
 * <p>Thread safety:  Mutable and not thread-safe.</p>
 *
 * @author Standlove, pinoydream
 * @version 1.0
 *
 */

/**
 * <p>Construct the js object with the json object.
 * Set json's properties to the corresponding properties of this js object.</p>
 *
 * <p>If this constructor is called without a parameter, it serves as an empty default constructor.</p>
 */ 
js.topcoder.widgets.bridge.Submission = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the submission ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
	var submissionID /* long */ = -1;

	/**
	 * <p>Represents the submitter's ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var submitterID /* long */ = -1;

	/**
	 * <p>Represents the submission date and time of the contest.</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var submissionTimeStamp /* Date */ = null;

	/**
	 * <p>Represents the submissionContent</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be null, can be empty</p>
	 */
    var submissionContent /* URI/String */ = null;

	/**
	 * <p>Represents the contest ID</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var contestID /* long */ = -1;

	/**
	 * <p>Represents the passedScreening</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var passedScreening /* boolean */ = false;

	/**
	 * <p>Represents the passedReview</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var passedReview /* boolean */ = false;

	/**
	 * <p>Represents the placement</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var placement /* int */ = -1;

	/**
	 * <p>Represents the paidFor</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var paidFor /* boolean */ = false;

	/**
	 * <p>Represents the price</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: must be greater or equal than 0 or -1 (not set)</p>
	 */
    var price /* double */ = -1;

	/**
	 * <p>Represents the markedForPurchase</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var markedForPurchase /* boolean */ = false;

	/**
	 * <p>Represents the removed</p>
	 * <p>Initial Value: false, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values</p>
	 */
    var removed /* boolean */ = false;


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
		// sets the submissionID
		if (typeof(json.submissionID) != "undefined" && typeof(json.submissionID) == "number") {		
    		that.submissionID = json.submissionID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.submissionID","json.submissionID does not exists");
		}    		
		// sets the submitterID
		if (typeof(json.submitterID) != "undefined" && typeof(json.submitterID) == "number") {		
    		that.submitterID = json.submitterID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.submitterID","json.submitterID does not exists");
		}    		
		// sets the submissionTimeStamp
		if (typeof(json.submissionTimeStamp) != "undefined" && typeof(json.submissionTimeStamp) == "string") {		
    		that.submissionTimeStamp = json.submissionTimeStamp;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.submissionTimeStamp","json.submissionTimeStamp does not exists");
		}    		
		// sets the submissionContent
		if (typeof(json.submissionContent) != "undefined" && typeof(json.submissionContent) == "string") {		
    		that.submissionContent = json.submissionContent;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.submissionContent","json.submissionContent does not exists");
		}    		
		// sets the contestId
		if (typeof(json.contestID) != "undefined" && typeof(json.contestID) == "number") {		
    		that.contestID = json.contestID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestID","json.contestID does not exists");
		}    		
		// sets the passedScreening
		if (typeof(json.passedScreening) != "undefined" && typeof(json.passedScreening) == "boolean") {		
    		that.passedScreening = json.passedScreening;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.passedScreening","json.passedScreening does not exists");
		}    		
    	// sets the passedReview
		if (typeof(json.passedReview) != "undefined" && typeof(json.passedReview) == "boolean") {    	
    		that.passedReview = json.passedReview;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.passedReview","json.passedReview does not exists");
		}    		
		// sets the placement
		if (typeof(json.placement) != "undefined" && typeof(json.placement) == "number") {		
    		that.placement = json.placement;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.placement","json.placement does not exists");
		}    		
		// sets the paidFor
		if (typeof(json.paidFor) != "undefined" && typeof(json.paidFor) == "boolean") {		
    		that.paidFor = json.paidFor;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.paidFor","json.paidFor does not exists");
		}    		
		// sets the price
		if (typeof(json.price) != "undefined" && typeof(json.price) == "number") {		
    		that.price = json.price;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.price","json.price does not exists");
		}    		
		// sets the markedForPurchase
		if (typeof(json.markedForPurchase) != "undefined" && typeof(json.markedForPurchase) == "boolean") {		
    		that.markedForPurchase = json.markedForPurchase;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.markedForPurchase","json.markedForPurchase does not exists");
		}    		
		// sets the removed
		if (typeof(json.removed) != "undefined" && typeof(json.removed) == "boolean") {		
    		that.removed = json.removed;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.removed","json.removed does not exists");
		}    		
	}


    /**
     * <p>Returns the contestID.</p>
     */
    this.getContestID = function /* long */ () {
        return that.contestID;
    }

    /**
     * <p>Sets the contestID.</p>
     */
    this.setContestID = function /* void */ (/* long */ contestID) {
        that.contestID = contestID;
    }

    /**
     * <p>Returns the markedForPurchase.</p>
     */
    this.isMarkedForPurchase = function /* boolean */ () {
        return that.markedForPurchase;
    }

    /**
     * <p>Sets the markedForPurchase.</p>
     */
    this.setMarkedForPurchase = function /* void */ (/* boolean */ markedForPurchase) {
        that.markedForPurchase = markedForPurchase;
    }

    /**
     * <p>Returns the paidFor.</p>
     */
    this.isPaidFor = function /* boolean */ () {
        return that.paidFor;
    }

    /**
     * <p>Sets the paidFor.</p>
     */
    this.setPaidFor = function /* void */ (/* boolean */ paidFor) {
        that.paidFor = paidFor;
    }

    /**
     * <p>Returns the passedScreening.</p>
     */
    this.isPassedScreening = function /* boolean */ () {
        return that.passedScreening;
    }

    /**
     * <p>Sets the passedScreening.</p>
     */
    this.setPassedScreening = function /* void */ (/* boolean */ passedScreening) {
        that.passedScreening = passedScreening;
    }

    /**
     * <p>Returns the passedReview.</p>
     */
    this.isPassedReview = function /* boolean */ () {
        return that.passedReview;
    }

    /**
     * <p>Sets the passedReview.</p>
     */
    this.setPassedReview = function /* void */ (/* boolean */ passedReview) {
        that.passedReview = passedReview;
    }

    /**
     * <p>Returns the placement.</p>
     */
    this.getPlacement = function /* int */ () {
        return that.placement;
    }

    /**
     * <p>Sets the placement.</p>
     */
    this.setPlacement = function /* void */ (/* int */ placement) {
        that.placement = placement;
    }

    /**
     * <p>Returns the price.</p>
     */
    this.getPrice = function /* double */ () {
        return that.price;
    }

    /**
     * <p>Sets the price.</p>
     */
    this.setPrice = function /* void */ (/* double */ price) {
        that.price = price;
    }

    /**
     * <p>Returns the removed.</p>
     */
    this.isRemoved = function /* boolean */ () {
        return that.removed;
    }

    /**
     * <p>Sets the removed.</p>
     */
    this.setRemoved = function /* void */ (/* boolean */ removed) {
        that.removed = removed;
    }

    /**
     * <p>Returns the submissionContent.</p>
     */
    this.getSubmissionContent = function /* URI/String */ () {
        return that.submissionContent;
    }

    /**
     * <p>Sets the submissionContent.</p>
     */
    this.setSubmissionContent = function /* void */ (/* URI/String */ submissionContent) {
        that.submissionContent = submissionContent;
    }

    /**
     * <p>Returns the submissionID.</p>
     */
    this.getSubmissionID = function /* long */ () {
        return that.submissionID;
    }

    /**
     * <p>Sets the submissionID.</p>
     */
    this.setSubmissionID = function /* void */ (/* long */ submissionID) {
        that.submissionID = submissionID;
    }

    /**
     * <p>Returns the submissionTimeStamp.</p>
     */
    this.getSubmissionTimeStamp = function /* Date */ () {
        return that.submissionTimeStamp;
    }

    /**
     * <p>Sets the submissionTimeStamp.</p>
     */
    this.setSubmissionTimeStamp = function /* void */ (/* Date */ submissionTimeStamp) {
        that.submissionTimeStamp = submissionTimeStamp;
    }

    /**
     * <p>Returns the submitterID.</p>
     */
    this.getSubmitterID = function /* long */ () {
        return that.submitterID;
    }

    /**
     * <p>Sets the submitterID.</p>
     */
    this.setSubmitterID = function /* void */ (/* long */ submitterID) {
        that.submitterID = submitterID;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value.
	 * 
	 * {"submissionID" : $submissionID,"submitterID" : $submitterID,"submissionTimeStamp" : "$submissionTimeStamp" ,
	 * //  this Date value should be converted to string. "submissionContent" : "$submissionContent",
	 * "contestID" : $contestID,"passedScreening" : $passedScreening,"passedReview" : $passedReview,
	 * "placement" : $placement,"paidFor" : $paidFor,"price" : $price,"markedForPurchase" : $markedForPurchase,
	 * "removed" : $removed}
	 */
	this.toJSON = function /* String */ () {
		return "{"+
		    	" \"submissionID\" : "+that.getSubmissionID()+
		    	", \"submitterID\" : "+that.getSubmitterID()+
		    	", \"submissionTimeStamp\" : \""+that.getSubmissionTimeStamp()+"\" "+
		    	", \"submissionContent\" : \""+that.getSubmissionContent()+"\" "+
		    	", \"contestID\" : "+that.getContestID()+
		    	", \"passedScreening\" : "+that.isPassedScreening()+
		    	", \"passedReview\" : "+that.isPassedReview()+
		    	", \"placement\" : "+that.getPlacement()+
		    	", \"paidFor\" : "+that.isPaidFor()+
		    	", \"price\" : "+that.getPrice()+
		    	", \"markedForPurchase\" : "+that.isMarkedForPurchase()+
		    	", \"removed\" : "+that.isRemoved()+"}";
	}

} // end

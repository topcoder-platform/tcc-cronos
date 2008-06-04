/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */


/**
 * <p>This Java Script object represents the Uploaded Document data.
 *
 * Thread safety:  Mutable and not thread-safe.</p>
 *
 * Note: 
 *   the fileName means the local file name that user wants to upload. It will be
  *  used to generate a input-file html control when uploading the file.  And it
  *  won't be generated into the json string.
 * 
 * @author Standlove, pinoydream
 * @version 1.0
 *
 */

/**
 * <p>Construct the js object with the json object.
 * 
 * Set json's properties to the corresponding properties of this js object.</p>
 *
 * <p>If this constructor is called without a parameter, it serves as an empty default constructor.</p>
 */ 
js.topcoder.widgets.bridge.UploadedDocument = function (/* JSON Object */ json) {
	/**
	 * This is to make the object available to private methods.
	 */
	var that = this;

	/**
	 * <p>Represents the documentId</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be -1, can be greater or equal to 0</p>
	 */
    var documentID /* long */ = -1;

	/**
	 * <p>Represents the contestId</p>
	 * <p>Initial Value: -1, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: can be -1, can be greater or equal to 0</p>
	 */
    var contestID /* long */ = -1;

	/**
	 * <p>Represents the fileName</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var fileName /* String */ = null;

	/**
	 * <p>Represents the path</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var path /* String */ = null;

	/**
	 * <p>Represents the description</p>
	 * <p>Initial Value: null, means that is not set</p>
	 * <p>Accessed In: getter method</p>
	 * <p>Modified In: setter method</p>
	 * <p>Utilized In: none</p>
	 * <p>Valid Values: all values, can be null, can be empty</p>
	 */
    var description /* String */  = null;

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
		// set the document id
		if (typeof(json.documentID) != "undefined" && typeof(json.documentID) == "number") {		
			that.documentID = json.documentID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.documentID","json.documentID does not exist");
		}		
		// set the contest id
		if (typeof(json.contestID) != "undefined" && typeof(json.contestID) == "number") {		
			that.contestID = json.contestID;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.contestID","json.contestID does not exist");
		}			
		// set the description
		if (typeof(json.description) != "undefined" && typeof(json.description) == "string") {		
			that.description = json.description;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.description","json.description does not exist");
		}			
		// set the file name
		if (typeof(json.fileName) != "undefined" && typeof(json.fileName) == "string") {		
			that.fileName = json.fileName;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.fileName","json.fileName does not exist");
		}			
		// set the path
		if (typeof(json.path) != "undefined" && typeof(json.path) == "string") {		
			that.path = json.path;
		} else {
			throw new js.topcoder.widgets.bridge.IllegalArgumentException("json.path","json.path does not exist");
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
     * <p>Returns the documentID.</p>
     */
    this.getDocumentID = function /* long */ () {
        return that.documentID;
    }

    /**
     * <p>Sets the documentID.</p>
     */
    this.setDocumentID = function /* void */ (/* long */ documentID) {
        that.documentID = documentID;
    }

    /**
     * <p>Returns the fileName.</p>
     */
    this.getFileName = function /* String */ () {
        return that.fileName;
    }

    /**
     * <p>Sets the fileName.</p>
     */
    this.setFileName = function /* void */ (/* String */ fileName) {
        that.fileName = fileName;
    }

    /**
     * <p>Returns the path.</p>
     */
    this.getPath = function /* String */ () {
        return that.path;
    }

    /**
     * <p>Sets the path.</p>
     */
    this.setPath = function /* void */ (/* String */ path) {
        that.path = path;
    }

	/**
	 * <p>Convert this JavaScript object to a JSON string and return.
	 *
	 * It will generate json string in the following format: (The $xxx should be replaced by corresponding variables.)</p>
	 *
	 * NOTE: the string-value needs to be enclosed by double-quotes according to JSON format.  Properties with null
	 * value should also be added to the generated json string with null value. 
	 *
	 * {"fileName" : $fileName,"documentID" : $documentID,"contestID" : $contestID,"description" : "$description"}
	 */
	this.toJSON = function /* String */ () {
		return "{\"fileName\" : \""+that.getFileName()+"\",\"path\" :\""+that.getPath()+"\",\"documentID\" : "+that.getDocumentID()+
			",\"contestID\" : "+that.getContestID()+", \"description\" : \""+that.getDescription()+"\"}";
	}

} // end

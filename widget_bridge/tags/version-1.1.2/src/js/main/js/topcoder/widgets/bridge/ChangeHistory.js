/**
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * Widget Bridge 1.0
 */

/**
 * The constructor takes JSON object and assign value to each internal fields.
 * 
 * @param {JSONObject}
 *            json JSON object with will be converted to ChangeHistory
 * 
 * @consturctor
 * @namespace js.topcoder.widgets.bridge
 * @class ChangeHistory
 * 
 * This class is a client side mapping for ChangeHistory entity in service side.
 * It also encapsulates JSON object coversion. Thread Safety: This class is
 * mutable and not thread-safe. But JavaScript execution model is mostly
 * single-threaded. And there is no thread-synchronization features in
 * JavaScript, so it is not an issue here. Anyway, the external code should be
 * careful if several threads are used to communicate with this class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
js.topcoder.widgets.bridge.ChangeHistory = function(json) {

	/**
	 * This variable represents ContestId. It can not be undefiend or null.
	 * 
	 * @private
	 * @type long
	 */
	var contestId;

	/**
	 * This variable represents TransactionId. It can not be undefiend or null.
	 * 
	 * @private
	 * @type long
	 */
	var transactionId;

	/**
	 * This variable represents UserName. It can not be undefiend or null.
	 * 
	 * @private
	 * @type string
	 */
	var userName;

	/**
	 * This variable represents timestamp. It can not be undefiend or null.
	 * 
	 * @private
	 * @type Date
	 */
	var timestamp;

	/**
	 * This variable represents FieldName. It can not be undefiend or null.
	 * 
	 * @private
	 * @type string
	 */
	var fieldName;

	/**
	 * This variable represents OldData. It can not be undefiend or null.
	 * 
	 * @private
	 * @type string
	 */
	var oldData;

	/**
	 * This variable represents IsUserAdmin. It can not be undefiend or null.
	 * 
	 * @private
	 * @type boolean
	 */
	var isUserAdmin;

	/**
	 * This variable represents NewData. It can not be undefiend or null.
	 * 
	 * @private
	 * @type string
	 */
	var newData;

	/**
	 * Abbreviation for Helper class.
	 */
	var Helper = js.topcoder.widgets.bridge.Helper;

	/**
	 * Abbreviation for IllegalArgumentException class.
	 */
	var IllegalArgumentException = js.topcoder.widgets.bridge.IllegalArgumentException;

	/**
	 * <p>
	 * Returns the ContestId.
	 * </p>
	 */
	this.getContestId = function /* long */() {
		return contestId;
	}

	/**
	 * <p>
	 * Sets the ContestId.
	 * </p>
	 */
	this.setContestId = function /* void */(/* long */contestId) {
		this.contestId = contestId;
	}


	/**
	 * <p>
	 * Returns the TransactionId.
	 * </p>
	 */
	this.getTransactionId = function /* long */() {
		return transactionId;
	}

	/**
	 * <p>
	 * Sets the TransactionId.
	 * </p>
	 */
	this.setTransactionId = function /* void */(/* long */transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * <p>
	 * Returns the timestamp.
	 * </p>
	 */
	this.getTimestamp = function /* Date */() {
		return timestamp;
	}

	/**
	 * <p>
	 * Sets the timestamp.
	 * </p>
	 */
	this.setTimestamp = function /* void */(/* Date */timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * <p>
	 * Returns the UserName.
	 * </p>
	 */
	this.getUserName = function /* string */() {
		return userName;
	}

	/**
	 * <p>
	 * Sets the UserName.
	 * </p>
	 */
	this.setUserName = function /* void */(/* string */userName) {
		this.userName = userName;
	}

	/**
	 * <p>
	 * Returns the FieldName.
	 * </p>
	 */
	this.getFieldName = function /* string */() {
		return fieldName;
	}

	/**
	 * <p>
	 * Sets the FieldName.
	 * </p>
	 */
	this.setFieldName = function /* void */(/* string */fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * <p>
	 * Returns the OldData.
	 * </p>
	 */
	this.getOldData = function /* string */() {
		return oldData;
	}

	/**
	 * <p>
	 * Sets the OldData.
	 * </p>
	 */
	this.setOldData = function /* void */(/* string */oldData) {
		this.oldData = oldData;
	}

	/**
	 * <p>
	 * Returns the NewData.
	 * </p>
	 */
	this.getNewData = function /* string */() {
		return newData;
	}

	/**
	 * <p>
	 * Sets the NewData.
	 * </p>
	 */
	this.setNewData = function /* void */(/* string */newData) {
		this.newData = newData;
	}

	/**
	 * <p>
	 * Returns the IsUserAdmin.
	 * </p>
	 */
	this.getIsUserAdmin = function /* boolean */() {
		return isUserAdmin;
	}

	/**
	 * <p>
	 * Sets the IsUserAdmin.
	 * </p>
	 */
	this.setIsUserAdmin = function /* void */(/* boolean */isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	/**
	 * Convert the current instance into JSON string.
	 * 
	 * @return a JSON string representing current object
	 */
	this.toJSON = function() {
		var result = "{";

		result += "\"contestId\" : " + this.contestId + ", ";
		result += "\"transactionId\" : " + this.transactionId + ", ";
		result += "\"timestamp\": \"" + this.timestamp + "\", ";
		result += "\"userName\": \"" + this.userName + "\"" + ", ";
		result += "\"fieldName\": \"" + this.fieldName + "\"" + ", ";
		result += "\"oldData\": \"" + this.oldData + "\"" + ", ";
		result += "\"newData\": \"" + this.newData + "\"" + ", ";
		result += "\"isUserAdmin\": " + this.isUserAdmin;

		result += "}";
		return result;
	}

	
	// constructor code
	if (json != null) {
		this.setContestId(json.contestId);
		this.setTransactionId(json.transactionId);
		this.setTimestamp(json.timestamp);
		this.setUserName(json.userName);
		this.setFieldName(json.fieldName);
		this.setOldData(json.oldData);
		this.setNewData(json.newData);
		this.setIsUserAdmin(json.isUserAdmin);
	}
}
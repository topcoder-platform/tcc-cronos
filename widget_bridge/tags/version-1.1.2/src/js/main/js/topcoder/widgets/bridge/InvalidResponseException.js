/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

/**
 * <p>This exception is thrown by the java script service classes if the received response is invalid.
 * (e.g. the received JSON string is not in correct format.).</p>
 *
 * @constructor
 * @author Standlove, pinoydream
 * @version 1.0
 */
js.topcoder.widgets.bridge.InvalidResponseException = function (/* String */serviceName,/* String */methodName,/* String */message) {

	/**
	 * <p>Represents the service name.
	 * Initialized in constructor, and never changed afterwards.
	 * User should set non-null, non-empty value to it.  But it's not validated in this class.</p>
	 */
	this.serviceName = null;

	/**
	 * <p>Represents the method name.
	 * Initialized in constructor, and never changed afterwards.
	 * User should set non-null, non-empty value to it.  But it's not validated in this class.</p>
	 */
	this.methodName = null;
	
	// call superclass constructor
	Error.call(this, message);
	
	// set the 'instance variables' after calling the superclass constructor
	
	// set the serviceName
	this.serviceName = serviceName;
	
	// set the methodName
	this.methodName = methodName;

	/**
	 * <p>Returns the service name.</p>
	 */	
	this.getServiceName = function /* String */ () {
		return this.serviceName;
	}

	/**
	 * <p>Returns the method name.</p>
	 */	
	this.getMethodName = function /* String */ () {
		return this.methodName;
	}

} // end

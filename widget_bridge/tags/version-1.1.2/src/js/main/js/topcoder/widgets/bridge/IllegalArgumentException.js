/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

/**
 * <p>This exception is thrown if the argument is null or invalid. 
 *
 * Thread-safety: Immutable and thread-safe.</p>
 *
 * @constructor
 * @author Standlove, pinoydream
 * @version 1.0
 */
js.topcoder.widgets.bridge.IllegalArgumentException = function (/* String */argumentName,/* String */message) {
	/**
	 * <p>Represents the illegal argument name. 
	 * Initialized in constructor, and never changed afterwards.
	 * User should set non-null, non-empty value to it.  But it's not validated in this class.</p>
	 */
	this.argumentName = null;

	// call superclass constructor
	Error.call(this, message);
	
	// set the 'instance variables' after calling the superclass constructor
	
	// set the argumentName
	this.argumentName = argumentName;
	
	/**
	 * <p>Returns the argument name.</p>
	 */	
	this.getArgumentName = function /* String */ () {
		return this.argumentName;
	}

} // end

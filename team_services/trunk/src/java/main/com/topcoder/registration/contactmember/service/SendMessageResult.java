package com.topcoder.registration.contactmember.service;

import java.io.Serializable;

public interface SendMessageResult extends Serializable {
	/**
	 * Gets the failedMessages field value. The array will contain zero to many non-null/empty Strings. It will not be null. The array will contain the handles of all recipients to whom it was not able to send a message.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm78ef]
	 * @return The failedMessages field value
	 */
	    public String[] getFailedMessages();
	/**
	 * Gets the successfulMessages field value. The array will contain zero to many non-null/empty Strings. It will not be null. The array will contain the handles of all recipients to whom it was able to send a message.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm78e8]
	 * @return The successfulMessages field value
	 */
	    public String[] getSuccessfulMessages();

}

package com.topcoder.registration.contactmember.service;

public interface ContactMemberService {

	/**
	 * Sends the message to the recipients in that message.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm7a00]
	 * @return SendMessageResult with the breakdown which recipients were successfully sent to, and which were not
	 * @param message Message with the message and its recipients
	 * @throws IllegalArgumentException If message is null
	 * @throws InvalidMessageDataException If message contains invalid data, which includes missing required fields, but no checks on the correctness of the data.
	 * @throws ContactMemberServiceException If there is a general system error while performing this operation
	 */
	    public SendMessageResult sendMessage(Message message);

}

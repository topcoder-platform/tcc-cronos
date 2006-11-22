package com.topcoder.message.messenger;

public class MockMessengerPlugin extends MessengerPlugin {

	public MessageAPI createMessage() {
		return new MockMessageAPI();
	}

	public void sendMessage(MessageAPI message) throws MessageException,
			Exception {

	}

	protected Class getMessageType() {
		return null;
	}

}

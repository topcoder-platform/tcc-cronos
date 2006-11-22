package com.topcoder.message.messenger;

public class MockMessageAPI extends MessageAPI {

	public void setParameterValue(String name, Object value) throws IllegalArgumentException {
		System.out.println("MockMessageAPI#setParameterValue(\""+name+"\",\""+value+"\")");
	}

}

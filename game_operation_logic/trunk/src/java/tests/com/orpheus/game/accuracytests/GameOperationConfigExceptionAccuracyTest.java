/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.GameOperationConfigException;

import junit.framework.TestCase;

/**
 * Accuracy test case for GameOperationConfigException.
 * 
 * @author Zulander
 * @version 1.0
 */
public class GameOperationConfigExceptionAccuracyTest extends TestCase {

	/** Represents the default exception in testing accuracy of exception class */
	static final String EXCEPTION_MESSAGE = "Testing exception message";

	/** Represents the default cause in testing accuracy of exception class */
	static final Exception EXCEPTION_CAUSE = new Exception();

	/**
	 * Test for
	 * {@link GameOperationConfigException#GameOperationConfigException(String)}.
	 * It should be constructed with given message.
	 */
	public void testGameOperationConfigException_Constructor1() {
		// create the instance.
		GameOperationConfigException exception = new GameOperationConfigException(
				EXCEPTION_MESSAGE);

		// check the result.
		assertNotNull("The instance should not be null.", exception);
		assertEquals("The message isn't correct.", EXCEPTION_MESSAGE, exception
				.getMessage());
	}

	/**
	 * Test for
	 * {@link GameOperationConfigException#GameOperationConfigException(String, Throwable)}.
	 * It should be constructed with given message and cause.
	 */
	public void testGameOperationConfigException_Constructor2() {
		// create the instance.
		GameOperationConfigException exception = new GameOperationConfigException(
				EXCEPTION_MESSAGE, EXCEPTION_CAUSE);

		// check the result.
		assertNotNull("The instance should not be null.", exception);
		assertEquals("The message isn't correct.", EXCEPTION_MESSAGE
				+ ", caused by null", exception.getMessage());
		assertEquals("The inner exception isn't correct.", EXCEPTION_CAUSE,
				exception.getCause());
	}

}

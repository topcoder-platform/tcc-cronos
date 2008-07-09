/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import junit.framework.TestCase;

import java.util.Date;

import com.topcoder.messaging.Response;

/**
 * This is failure test for the class {@link Response}.
 * 
 * @author superZZ
 * @version 2.0
 */
public class ResponseTests extends TestCase {
	/**
	 * Tests constructor of response.
	 * 
	 * Null name is sent. IAE is expected.
	 */
	public void testConstructor1_Null_Name() {
		try {
			new Response(null, new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Null date is sent. IAE is expected.
	 */
	public void testConstructor1_Null_Date() {
		try {
			new Response("name", null, "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Null message is sent. IAE is expected.
	 */
	public void testConstructor1_Null_Message() {
		try {
			new Response("name", new Date(), null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Null name is sent. IAE is expected.
	 */
	public void testConstructor2_Null_Name() {
		try {
			new Response(1, null, new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Null date is sent. IAE is expected.
	 */
	public void testConstructor2_Null_Date() {
		try {
			new Response(1, "name", null, "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Null message is sent. IAE is expected.
	 */
	public void testConstructor2_Null_Message() {
		try {
			new Response(1, "name", new Date(), null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor of response.
	 * 
	 * Negative id is sent. IAE is expected.
	 */
	public void testConstructor2_Negative_Id() {
		try {
			new Response(-1, "name", new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}
}
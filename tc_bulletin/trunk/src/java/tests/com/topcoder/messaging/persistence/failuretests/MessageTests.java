/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.messaging.Message;
import com.topcoder.messaging.MessageAttribute;
import com.topcoder.messaging.Response;

/**
 * This is failure test for Message class.
 * 
 * @author superZZ
 * @version 2.0
 */
public class MessageTests extends TestCase {
	/**
	 * Represents Message instance used for test.
	 */
	private Message instance;

	/**
	 * Represents message attribute set for test.
	 */
	private Map<String, MessageAttribute> messageAttributes = new HashMap<String, MessageAttribute>();

	/**
	 * Initializes Message instance.
	 */
	protected void setUp() {
		instance = new Message();
	}

	/**
	 * Tests constructor. Null name is given. IAE is expected.
	 */
	public void testConstructor1_NullName() {
		try {
			new Message(null, new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty name is given. IAE is expected.
	 */
	public void testConstructor1_EmptyName() {
		try {
			new Message("", new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null name is given. IAE is expected.
	 */
	public void testConstructor2_NullName() {
		try {
			new Message(1, null, new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty name is given. IAE is expected.
	 */
	public void testConstructor2_EmptyName() {
		try {
			new Message(1, "", new Date(), "message");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null name is given. IAE is expected.
	 */
	public void testConstructor3_EmptyAttributes() {
		try {
			new Message("name", new Date(), "message", messageAttributes,
					new Response());
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Attribute set contains null key. IAE is expected.
	 */
	public void testConstructor3_NullKey() {
		messageAttributes.put(null, new MessageAttribute());
		try {
			new Message("name", new Date(), "message", messageAttributes,
					new Response());
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Attribute set contains empty key. IAE is expected.
	 */
	public void testConstructor4_EmptyKey() {
		messageAttributes.put("", new MessageAttribute());
		try {
			new Message("name", new Date(), "message", messageAttributes,
					new Response());
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Attribute set contains empty key. IAE is expected.
	 */
	public void testConstructor5_EmptyKey() {
		messageAttributes.put("", new MessageAttribute());
		try {
			new Message(1, "name", new Date(), "message", messageAttributes,
					new Response());
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Attribute set contains null value. IAE is expected.
	 */
	public void testConstructor4_NullValue() {
		messageAttributes.put("key", null);
		try {
			new Message("name", new Date(), "message", messageAttributes,
					new Response());
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setAttributes method. Null attributes set is given.
	 */
	public void testSetMessages_NullAttributes() {
		try {
			instance.setAttributes(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setAttributes method. Empty attributes set is given.
	 */
	public void testSetMessages_EmptyAttributes() {
		try {
			instance.setAttributes(messageAttributes);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setAttributes method. Attributes set with null key is given.
	 */
	public void testSetMessages_NullKey() {
		try {
			messageAttributes.put(null, new MessageAttribute());
			instance.setAttributes(messageAttributes);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setAttributes method. Attributes set with empty key is given.
	 */
	public void testSetMessages_EmptyKey() {
		try {
			messageAttributes.put("", new MessageAttribute());
			instance.setAttributes(messageAttributes);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setAttributes method. Attributes set with null value is given.
	 */
	public void testSetMessages_NullValue() {
		try {
			messageAttributes.put("key", null);
			instance.setAttributes(messageAttributes);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setResponse method. Null response is given.
	 */
	public void testSetResponse_NullResponse() {
		try {
			instance.setResponse(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}
}

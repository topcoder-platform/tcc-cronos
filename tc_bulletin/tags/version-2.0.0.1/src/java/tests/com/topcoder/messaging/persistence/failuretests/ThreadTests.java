/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging.persistence.failuretests;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

import com.topcoder.messaging.Thread;
import com.topcoder.messaging.Message;

/**
 * This is failure test for Thread class.
 * 
 * @author superZZ
 * @version 2.0
 */
public class ThreadTests extends TestCase {
	/**
	 * Represents Thread instance used for test.
	 */
	private Thread instance;

	/**
	 * Represents message set for test.
	 */
	private Set<Message> messages = new HashSet<Message>();

	/**
	 * Initializes thread instance.
	 */
	protected void setUp() {
		instance = new Thread();
	}

	/**
	 * Tests constructor. Null userKey is given. IAE is expected.
	 */
	public void testConstructor1_Null_UserKey() {
		try {
			new Thread(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty userKey is given. IAE is expected.
	 */
	public void testConstructor1_Empty_UserKey() {
		try {
			new Thread("");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null userKey is given. IAE is expected.
	 */
	public void testConstructor2_Null_UserKey() {
		try {
			new Thread(1, null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty userKey is given. IAE is expected.
	 */
	public void testConstructor2_Empty_UserKey() {
		try {
			new Thread(1, "");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Negative id is given. IAE is expected.
	 */
	public void testConstructor2_NegativeId() {
		try {
			new Thread(-1, "userKey");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null userKey is given. IAE is expected.
	 */
	public void testConstructor3_Null_UserKey() {
		try {
			new Thread(null, messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty userKey is given. IAE is expected.
	 */
	public void testConstructor3_Empty_UserKey() {
		try {
			new Thread("", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null message set is given.
	 */
	public void testConstructor3_NullMessages() {
		try {
			new Thread("key", null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null message is contained.
	 */
	public void testConstructor3_NullMessage() {
		try {
			messages.add(null);
			new Thread("key", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Message set is empty.
	 */
	public void testConstructor3_EmptyMessages() {
		try {
			new Thread("key", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null userKey is given. IAE is expected.
	 */
	public void testConstructor4_Null_UserKey() {
		try {
			new Thread(1, null, messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Empty userKey is given. IAE is expected.
	 */
	public void testConstructor4_Empty_UserKey() {
		try {
			new Thread(1, "", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null message set is given.
	 */
	public void testConstructor4_NullMessages() {
		try {
			new Thread(1, "key", null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Null message is contained.
	 */
	public void testConstructor4_NullMessage() {
		try {
			messages.add(null);
			new Thread(1, "key", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Message set is empty.
	 */
	public void testConstructor4_EmptyMessages() {
		try {
			new Thread(1, "key", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests constructor. Negative id is given.
	 */
	public void testConstructor4_NegativeId() {
		try {
			messages.add(new Message());
			new Thread(-1, "key", messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setMessages method. Null message set is given.
	 */
	public void testSetMessages_NullMessages() {
		try {
			instance.setMessages(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setMessages method. Null message is contained.
	 */
	public void testSetMessages_NullMessage() {
		try {
			messages.add(null);
			instance.setMessages(messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setMessages method. Message set is empty.
	 */
	public void testSetMessages_EmptyMessages() {
		try {
			instance.setMessages(messages);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests getMessage method. Negative id is given.
	 */
	public void testGetMessage_NegativeId() {
		try {
			instance.getMessage(-1);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests addMessage method. Null message is given.
	 */
	public void testAddMessage_NullMessage() {
		try {
			instance.addMessage(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests removeMessage method. Negative id is given.
	 */
	public void testRemoveMessage_NegativeId() {
		try {
			instance.removeMessage(-1);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests removeMessage method. Null message is given.
	 */
	public void testRemoveMessage_NullMessage() {
		try {
			instance.removeMessage(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests containsMessage method. Null message is given.
	 */
	public void testContainsMessage_NullMessage() {
		try {
			instance.containsMessage(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setUserKey method. Null userKey is given. IAE is expected.
	 */
	public void testSetUserKey_NullUserKey() {
		try {
			instance.setUserKey(null);
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Tests setUserKey method. Empty userKey is given. IAE is expected.
	 */
	public void testSetUserKey_EmptyUserKey() {
		try {
			instance.setUserKey("");
			fail("IllegalArgumentException is expected.");
		} catch (IllegalArgumentException e) {
		}
	}
}

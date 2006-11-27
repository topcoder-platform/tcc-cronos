/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.JndiLookupDesignation;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for <code>JndiLookupDesignation</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JndiLookupDesignationTests extends TestCase {
	/**
	 * Accuracy test for the getName() method of the class.
	 */
	public void testGetNameAccuracy() {
	    assertEquals("getName failed.", JndiLookupDesignation.LOCAL.getName(), "Local");
	    assertEquals("getName failed.", JndiLookupDesignation.REMOTE.getName(), "Remote");
	}

	/**
	 * Accuracy test for the toString() method of the class.
	 */
	public void testToStringAccuracy() {
	    assertEquals("toString failed.", JndiLookupDesignation.LOCAL.toString(), "Local");
	    assertEquals("toString failed.", JndiLookupDesignation.REMOTE.toString(), "Remote");
	}
}

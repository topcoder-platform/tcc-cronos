/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>AdminMessage</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AdminMessageTests extends TestCase {
    /**
     * Tests that the constructor correctly adds all the required parameters.
     */
    public void test_ctor() {
        Set params = new TreeSet();
        for (Enumeration e = new AdminMessage().getRequiredParameters(); e.hasMoreElements();) {
            params.add(e.nextElement());
        }

        Set expectedParams = new TreeSet();
        expectedParams.add(AdminMessage.GUID);
        expectedParams.add(AdminMessage.CATEGORY);
        expectedParams.add(AdminMessage.CONTENT_TYPE);
        expectedParams.add(AdminMessage.CONTENT);
        expectedParams.add(AdminMessage.TIMESTAMP);

        assertEquals("there should be five required parameters", expectedParams, params);
    }
}

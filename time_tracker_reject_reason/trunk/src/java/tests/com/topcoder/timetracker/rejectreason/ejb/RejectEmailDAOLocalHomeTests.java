/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import junit.framework.TestCase;


/**
 * Unit tests for <code>RejectEmailDAOLocalHome</code>.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailDAOLocalHomeTests extends TestCase {
    /**
     * Tests public field EJB_REF_HOME.
     */
    public void testPublicField() {
        assertEquals("The EJB_REF_HOME is not initialized correctly.", "RejectEmailDAOLocalHome",
            RejectEmailDAOLocalHome.EJB_REF_HOME);
    }
}
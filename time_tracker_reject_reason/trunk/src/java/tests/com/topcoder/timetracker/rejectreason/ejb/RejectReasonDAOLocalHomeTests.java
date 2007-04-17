/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import junit.framework.TestCase;


/**
 * Unit tests for <code>RejectReasonDAOLocalHome</code>.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonDAOLocalHomeTests extends TestCase {
    /**
     * Tests public field EJB_REF_HOME.
     */
    public void testPublicField() {
        assertEquals("The EJB_REF_HOME is not initialized correctly.", "RejectReasonDAOLocalHome",
            RejectReasonDAOLocalHome.EJB_REF_HOME);
    }
}

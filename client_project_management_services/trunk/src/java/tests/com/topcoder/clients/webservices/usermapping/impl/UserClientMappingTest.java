/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping.impl;

import com.topcoder.clients.model.AuditableEntity;
import junit.framework.TestCase;

/**
 * Unit test for {@link UserClientMapping}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class UserClientMappingTest extends TestCase {

    /**
     * Represents UserClientMapping instance.
     */
    private UserClientMapping user;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        user = new UserClientMapping();
    }

    /**
     * Teardown environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void tearDown() throws Exception {
        user = null;
        super.tearDown();
    }

    /**
     * Test for {@link UserClientMapping#UserClientMapping()}.
     */
    public void testUserClientMapping() {
        assertNotNull("Fail create new instance.", user);
        assertTrue("Fail create new instance.", user instanceof AuditableEntity);
    }

    /**
     * Test for {@link UserClientMapping#setClientId(long)}.
     */
    public void testSetClientId() {
        assertEquals("Initial value is invalid.", 0, user.getClientId());
        long id = 1;
        user.setClientId(id);
        assertEquals("Fail setup property.", id, user.getClientId());
    }

    /**
     * Test for {@link UserClientMapping#setUserId(long)}.
     */
    public void testSetUserId() {
        assertEquals("Inivial value is invalid.", 0, user.getUserId());
        long id = 1;
        user.setUserId(id);
        assertEquals("Fail setup property.", id, user.getUserId());
    }
}

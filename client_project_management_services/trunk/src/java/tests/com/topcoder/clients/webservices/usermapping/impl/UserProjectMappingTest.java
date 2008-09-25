/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping.impl;

import junit.framework.TestCase;

/**
 * Unit test for {@link UserProjectMapping}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class UserProjectMappingTest extends TestCase {

    /**
     * Represents UserProjectMapping instance.
     */
    private UserProjectMapping user;

    /**
     * Setup environment for testing.
     *
     * @throws Exception into JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        user = new UserProjectMapping();
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
     * Test for {@link UserProjectMapping#UserProjectMapping()}.
     */
    public void testUserProjectMapping() {
        assertNotNull("Fail create new instance.", user);
    }

    /**
     * Test for {@link UserProjectMapping#setProjectId(long)}.
     */
    public void testSetProjectId() {
        assertEquals("Initial value is invalid.", 0, user.getProjectId());
        long id = 1;
        user.setProjectId(id);
        assertEquals("Fail setup property.", id, user.getProjectId());
    }

    /**
     * Test for {@link UserProjectMapping#setUserId(long)}.
     */
    public void testSetUserId() {
        assertEquals("Initial value isinvalid.", 0, user.getUserId());
        long id = 1;
        user.setUserId(id);
        assertEquals("Fail setup property.", id, user.getUserId());
    }
}
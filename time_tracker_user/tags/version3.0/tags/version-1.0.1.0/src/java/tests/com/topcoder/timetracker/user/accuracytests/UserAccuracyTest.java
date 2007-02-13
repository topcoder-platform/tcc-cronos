/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.User;

/**
 * Accuracy test for class <code>user</code>.
 * @author fuyun
 * @version 1.0
 */
public class UserAccuracyTest extends TestCase {

    /**
     * Represent the name used to create <code>User</code> insatance.
     */
    private final String name = "name";

    /**
     * Represent the store name used to create <code>User</code> insatance.
     */
    private final String storeName = "store name";

    /**
     * Represent the id used to create <code>User</code> insatance.
     */
    private final int id = 1;

    /**
     * Test constructor User(long id, String name, String storeName).
     */
    public void testUser() {
        User user = new User(id, name, storeName, null);
        // verify whether the fields are properly set
        assertNotNull("fail to create User instance.", user);
        assertEquals("the id field is not properly set.", id, user.getId());
        assertEquals("the name field is not properly set.", name, user.getName());
        assertEquals("the storeName field is not properly set.", storeName, user
                .getUserStoreName());
    }

    /**
     * Test method getUserStoreName().
     */
    public void testGetUserStoreName() {
        assertEquals("the storeName field is not properly set.", storeName, (new User(id, name,
                storeName, null)).getUserStoreName());
    }

}

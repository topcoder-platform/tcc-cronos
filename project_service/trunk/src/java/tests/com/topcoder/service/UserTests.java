/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import junit.framework.TestCase;

import com.topcoder.service.user.Address;
import com.topcoder.service.user.User;

/**
 * <p>
 * <code>User</code> tests.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class UserTests extends TestCase {

    /**
     * This tests the UserInfo constructor.
     */
    public void testCtor() {
        assertNotNull("Failed to instantiate?", new User());
    }

    /**
     * This tests the accessor methods.
     */
    public void testSettersGetters() {
        User user = new User();

        user.setPassword("p1");
        assertEquals("setter/getter failed", "p1", user.getPassword());

        user.setPhone("55");
        assertEquals("setter/getter failed", "55", user.getPhone());

        Address address = new Address();
        user.setAddress(address);
        assertEquals("setter/getter failed", address, user.getAddress());

        user.setEmailAddress("foo1");
        assertEquals("setter/getter failed", "foo1", user.getEmailAddress());

        user.setEmailAddress("foo1");
        assertEquals("setter/getter failed", "foo1", user.getEmailAddress());

        user.setFirstName("foo2");
        assertEquals("setter/getter failed", "foo2", user.getFirstName());

        user.setLastName("foo3");
        assertEquals("setter/getter failed", "foo3", user.getLastName());

        user.setHandle("foo4");
        assertEquals("setter/getter failed", "foo4", user.getHandle());

        user.setUserId(2);
        assertEquals("setter/getter failed", 2, user.getUserId());

        long[] groupIds = new long[] {2};
        user.setGroupIds(groupIds);
        assertEquals("setter/getter failed", groupIds, user.getGroupIds());
    }
}

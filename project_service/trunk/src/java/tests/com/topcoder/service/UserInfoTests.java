/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import com.topcoder.service.user.UserInfo;

import junit.framework.TestCase;

/**
 * <p>
 * <code>UserInfo</code> tests.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class UserInfoTests extends TestCase {

    /**
     * This tests the UserInfo constructor.
     */
    public void testCtor() {
        assertNotNull("Failed to instantiate?", new UserInfo());
    }

    /**
     * This tests the accessor methods.
     */
    public void testSettersGetters() {
        UserInfo userInfo = new UserInfo();

        userInfo.setEmailAddress("foo");
        assertEquals("setter/getter failed", "foo", userInfo.getEmailAddress());

        userInfo.setFirstName("foo");
        assertEquals("setter/getter failed", "foo", userInfo.getFirstName());

        userInfo.setLastName("foo");
        assertEquals("setter/getter failed", "foo", userInfo.getLastName());

        userInfo.setHandle("foo");
        assertEquals("setter/getter failed", "foo", userInfo.getHandle());

        userInfo.setUserId(2);
        assertEquals("setter/getter failed", 2, userInfo.getUserId());

        long[] groupIds = new long[] {2};
        userInfo.setGroupIds(groupIds);
        assertEquals("setter/getter failed", groupIds, userInfo.getGroupIds());
    }
}

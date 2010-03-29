/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import java.util.Date;

import junit.framework.Test;

import com.topcoder.service.user.Address;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserServiceRemote;

/**
 * <p>
 * Demo of the User Service.
 * </p>
 *
 * @author ernestobf
 * @version 1.1
 */
public class UserServiceDemo extends BaseUnitTestCase {

    /**
     * group id 1 used in tests.
     */
    private static final long TEST_GROUP_ID1 = 888801;

    /**
     * terms of use id used in tests.
     */
    private static final long TEST_TERMS_ID = 666601;

    /**
     * <p>
     * Aggregates all Unit tests in this class.
     * </p>
     *
     * @return Test suite aggregating all Unit tests in this class
     */
    public static Test suite() {
        return suite(UserServiceDemo.class);
    }

    /**
     * Demos the use of the new methods in the user service.
     *
     * @throws Exception pass any unhandled exception to JUunit
     */
    public void testDemo() throws Exception {
        UserServiceRemote userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();

        // First, create a user entity
        Address address = new Address();
        address.setAddress1("x1");
        address.setAddress2("x2");
        address.setAddress3("x3");
        address.setCity("huh");
        address.setCountryCode("us");
        address.setZip("07871");
        address.setProvince("x");
        address.setStateCode("ny");
        User user = new User();
        user.setAddress(address);
        user.setEmailAddress("my@mail.com");
        user.setFirstName("First");
        user.setLastName("Second");
        user.setPassword("xxx");
        user.setPhone("5512342");
        user.setHandle("foo1");
        user.setGroupIds(new long[0]);

        // register the user, it returns the generated id
        long userId = userService.registerUser(user);

        // add groups using the addUserToGroups method
        userService.addUserToGroups("foo1", new long[] {TEST_GROUP_ID1});

        // remove groups using the addUserToGroups method
        userService.removeUserFromGroups("foo1", new long[] {TEST_GROUP_ID1});

        Date nowDate = new Date();

        // add a term
        userService.addUserTerm("foo1", TEST_TERMS_ID, nowDate);

        // remove the term
        userService.removeUserTerm("foo1", TEST_TERMS_ID);
        
        // and that's it
    }
}

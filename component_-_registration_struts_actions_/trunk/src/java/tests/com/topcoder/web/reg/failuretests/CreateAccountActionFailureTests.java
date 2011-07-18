/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.registration.CreateAccountAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


/**
 * Failure Tests for {@link CreateAccountAction}.
 *
 * @author gjw99
 * @version 1.0
 */
public class CreateAccountActionFailureTests {
    /** {@link CreateAccountAction} instance for unit test. */
    private CreateAccountAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CreateAccountAction();
    }

    /**
     * Clears the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Failure test for {@link CreateAccountAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure1() throws Exception {
        instance.setUserDAO(new MockUserDAO(true));

        Set<Email> emailAddresses = new HashSet<Email>();
        emailAddresses.add(new Email());

        User user = new User();
        user.setEmailAddresses(emailAddresses);
        instance.setUser(user);
        instance.execute();
    }

    /**
     * Failure test for {@link CreateAccountAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure2() throws Exception {
        instance.setUserDAO(new MockUserDAO(false));

        Set<Email> emailAddresses = new HashSet<Email>();
        User user = new User();
        user.setEmailAddresses(emailAddresses);
        instance.setUser(user);
        instance.execute();
    }

    /**
     * Failure test for {@link CreateAccountAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure3() throws Exception {
        instance.setUserDAO(new MockUserDAO(false));

        User user = new User();
        instance.setUser(user);
        instance.execute();
    }
}

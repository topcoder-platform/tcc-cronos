/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Failure Tests for {@link ResendAccountActivationEmailAction}.
 *
 * @author gjw99
 * @version 1.0
 */
public class ResendAccountActivationEmailActionFailureTests {
    /** {@link ResendAccountActivationEmailAction} instance for unit test. */
    private ResendAccountActivationEmailAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ResendAccountActivationEmailAction();
    }

    /**
     * Clears the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Failure test for {@link ResendAccountActivationEmailAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure1() throws Exception {
        instance.setUserDAO(new MockUserDAO(true));

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key1", new MockWebAuthentication(true));
        instance.setSession(session);
        instance.setAuthenticationSessionKey("key1");
        instance.execute();
    }

    /**
     * Failure test for {@link ResendAccountActivationEmailAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure2() throws Exception {
        instance.setUserDAO(new MockUserDAO(false));

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key1", new MockWebAuthentication(false));
        instance.setSession(session);
        instance.setAuthenticationSessionKey("key1");
        instance.execute();
    }
}

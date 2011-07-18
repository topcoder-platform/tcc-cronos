/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.common.TCWebException;
import com.topcoder.web.reg.actions.registration.ActivateAccountAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Failure Tests for {@link ActivateAccountAction}.
 *
 * @author gjw99
 * @version 1.0
 */
public class ActivateAccountActionFailureTests {
    /** {@link ActivateAccountAction} instance for unit test. */
    private ActivateAccountAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ActivateAccountAction();
    }

    /**
     * Clears the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Failure test for {@link ActivateAccountAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure1() throws Exception {
        instance.setUserDAO(new MockUserDAO(true));
        instance.execute();
    }

    /**
     * Failure test for {@link ActivateAccountAction#execute()}.
     *
     * @throws Exception to junit
     */
    @Test(expected = TCWebException.class)
    public void test_execute_failure2() throws Exception {
        instance.setUserDAO(new MockUserDAO(false));
        instance.execute();
    }
}

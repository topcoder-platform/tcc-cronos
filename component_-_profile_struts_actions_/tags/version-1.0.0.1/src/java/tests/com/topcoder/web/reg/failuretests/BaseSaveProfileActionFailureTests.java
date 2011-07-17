/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.actions.profile.BaseSaveProfileAction;
import com.topcoder.web.reg.actions.profile.SaveAccountInfoAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseSaveProfileAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseSaveProfileActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseSaveProfileActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseSaveProfileAction#execute() for failure.
     * Expects ProfileActionException.
     * </p>
     */
    public void testExecute_ProfileActionException() {
        BaseSaveProfileAction instance = new SaveAccountInfoAction();
        instance.setAuthenticationSessionKey("authenticationSessionKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationSessionKey", null);
        instance.setSession(session);
        try {
            instance.execute();
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }
}
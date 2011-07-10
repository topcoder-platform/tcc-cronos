/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ViewAccountInfoAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ViewAccountInfoActionFailureTests extends TestCase {
    /**
     * <p>
     * The ViewAccountInfoAction instance for testing.
     * </p>
     */
    private ViewAccountInfoAction instance;

    /**
     * <p>
     * The PrincipalMgrRemote instance for testing.
     * </p>
     */
    private PrincipalMgrRemote principalMgr;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ViewAccountInfoAction();
        principalMgr = mock(PrincipalMgrRemote.class);
        instance.setPrincipalMgr(principalMgr);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ViewAccountInfoActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) for failure.
     * Expects ProfileActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessOutputData_ProfileActionException() throws Exception {
        when(principalMgr.getPassword(1)).thenThrow(new GeneralSecurityException("error"));
        User user = new User();
        user.setId(1L);
        try {
            instance.processOutputData(user);
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#checkInitialization() for failure.
     * It tests the case that when principalMgr is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testcheckInitialization_NullPrincipalMgr() {
        instance.setPrincipalMgr(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

}
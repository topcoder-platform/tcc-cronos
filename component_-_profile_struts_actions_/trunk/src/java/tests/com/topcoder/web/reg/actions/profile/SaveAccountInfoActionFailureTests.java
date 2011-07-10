/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.UserPrincipal;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SaveAccountInfoAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SaveAccountInfoActionFailureTests extends TestCase {
    /**
     * <p>
     * The SaveAccountInfoAction instance for testing.
     * </p>
     */
    private SaveAccountInfoAction instance;

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
        instance = new SaveAccountInfoAction();
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
        return new TestSuite(SaveAccountInfoActionFailureTests.class);
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#processInputData(User) for failure.
     * Expects ProfileActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessInputData_ProfileActionException() throws Exception {
        instance.setPassword("password");
        when(principalMgr.editPassword(any(UserPrincipal.class), anyString(), any(TCSubject.class), anyString())).thenThrow(
            new GeneralSecurityException("error"));
        User user = new User();
        user.setId(1L);
        try {
            instance.processInputData(user);
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SaveAccountInfoAction#checkInitialization() for failure.
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
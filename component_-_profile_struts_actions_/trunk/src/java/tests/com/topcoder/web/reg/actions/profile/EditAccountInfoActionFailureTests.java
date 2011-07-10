/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for EditAccountInfoAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class EditAccountInfoActionFailureTests extends TestCase {
    /**
     * <p>
     * The EditAccountInfoAction instance for testing.
     * </p>
     */
    private EditAccountInfoAction instance;

    /**
     * <p>
     * The CoderTypeDAO instance for testing.
     * </p>
     */
    private CoderTypeDAO coderTypeDAO;

    /**
     * <p>
     * The User instance for testing.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new EditAccountInfoAction();
        coderTypeDAO = mock(CoderTypeDAO.class);
        user = new User();
        user.setHandle("handle");
        user.setId(1L);
        instance.setCoderTypeDAO(coderTypeDAO);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EditAccountInfoActionFailureTests.class);
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#processOutputData(User) for failure.
     * Expects ProfileActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testProcessOutputData_ProfileActionException() throws Exception {
        UserDAO userDAO = mock(UserDAO.class);
        instance.setUserDAO(userDAO);
        when(userDAO.canChangeHandle("handle")).thenThrow(new RuntimeException("error"));
        PrincipalMgrRemote principalMgr = mock(PrincipalMgrRemote.class);
        instance.setPrincipalMgr(principalMgr);
        when(principalMgr.getPassword(1)).thenReturn("value");
        try {
            instance.processOutputData(user);
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#performAdditionalTasks(User) for failure.
     * Expects ProfileActionException.
     * </p>
     */
    public void testPerformAdditionalTasks_ProfileActionException() {
        when(coderTypeDAO.getCoderTypes()).thenThrow(new RuntimeException("error"));
        try {
            instance.performAdditionalTasks(user);
            fail("ProfileActionException expected.");
        } catch (ProfileActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#checkInitialization() for failure.
     * It tests the case that when coderTypeDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testCheckInitialization_NullCoderTypeDAO() {
        instance.setCoderTypeDAO(null);
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

}
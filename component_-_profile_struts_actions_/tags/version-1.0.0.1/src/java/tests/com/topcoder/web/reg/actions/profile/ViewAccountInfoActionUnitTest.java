/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.NoSuchUserException;
import com.topcoder.security.admin.PrincipalMgrRemote;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for ViewAccountInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewAccountInfoActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ViewAccountInfoAction instance for testing.
     * </p>
     */
    private ViewAccountInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (ViewAccountInfoAction) getBean("viewAccountInfoAction");
        // init principal manager
        when(action.getPrincipalMgr().getPassword(anyLong())).thenReturn("password");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        action = null;
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction constructor.
     * </p>
     * <p>
     * ViewAccountInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ViewAccountInfoAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and passed user.
     * </p>
     * <p>
     * Displayed and referral user should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        User referralUser = MockFactory.createUser((long) id, "first name", "last name", "referral");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenReturn(referralUser);
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertEquals("Referral user should be changed.", "referral", action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and passed user, but exception
     * occurred in underlying DAO.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Exception() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenThrow(new RuntimeException("just for testing."));
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and passed user with not found
     * referral. occurred in underlying DAO.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_UserNotFound() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenReturn(null);
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and not found user for password.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Password_UserNotFound() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        User referralUser = MockFactory.createUser((long) id, "first name", "last name", "referral");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenReturn(referralUser);
        when(action.getPrincipalMgr().getPassword(user.getId())).thenThrow(
                new NoSuchUserException("just for testing."));
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and remote exception for password.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Password_RemoteException() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        User referralUser = MockFactory.createUser((long) id, "first name", "last name", "referral");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenReturn(referralUser);
        when(action.getPrincipalMgr().getPassword(user.getId())).thenThrow(new RemoteException("just for testing."));
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and security exception for
     * password.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Password_SecurityException() throws Exception {
        int id = 2;
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        User referralUser = MockFactory.createUser((long) id, "first name", "last name", "referral");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        Referral referral = new Referral();
        referral.setId(id);
        coderReferral.setReferral(referral);
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        when(action.getUserDAO().find((long) id)).thenReturn(referralUser);
        when(action.getPrincipalMgr().getPassword(user.getId())).thenThrow(
                new GeneralSecurityException("just for testing."));
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and null user's coder.
     * </p>
     * <p>
     * Displayed user should be changed and referral user should be null. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Null_Coder() throws Exception {
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertNull("Referral user should be null.", action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and null user's coder referral.
     * </p>
     * <p>
     * Displayed user should be changed and referral user should be null. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Null_CoderReferral() throws Exception {
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        user.setCoder(new Coder());
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertNull("Referral user should be null.", action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#processOutputData(User) method with valid fields and null user's coder referral.
     * </p>
     * <p>
     * Displayed user should be changed and referral user should be null. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Null_CoderReferralReferral() throws Exception {
        MockFactory.createUserInSession(action);
        action.setServletRequest(MockFactory.createServletRequest());
        User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        Coder coder = new Coder();
        CoderReferral coderReferral = new CoderReferral();
        coder.setCoderReferral(coderReferral);
        user.setCoder(coder);
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
        assertNull("Referral user should be null.", action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#getReferralUserHandle() method.
     * </p>
     * <p>
     * referralUserHandle should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetReferralUserHandle() {
        String referralUserHandle = "test";
        action.setReferralUserHandle(referralUserHandle);
        assertSame("getReferralUserHandle() doesn't work properly.", referralUserHandle,
                action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#setReferralUserHandle(String) method.
     * </p>
     * <p>
     * referralUserHandle should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetReferralUserHandle() {
        String referralUserHandle = "test";
        action.setReferralUserHandle(referralUserHandle);
        assertSame("setReferralUserHandle() doesn't work properly.", referralUserHandle,
                action.getReferralUserHandle());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#getPassword() method.
     * </p>
     * <p>
     * password should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPassword() {
        String password = "test";
        action.setPassword(password);
        assertSame("getPassword() doesn't work properly.", password, action.getPassword());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#setPassword(String) method.
     * </p>
     * <p>
     * password should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPassword() {
        String password = "test";
        action.setPassword(password);
        assertSame("setPassword() doesn't work properly.", password, action.getPassword());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#getPrincipalMgr() method.
     * </p>
     * <p>
     * principalMgr should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPrincipalMgr() {
        PrincipalMgrRemote principalMgr = MockFactory.getPrincipalMgr();
        action.setPrincipalMgr(principalMgr);
        assertSame("getPrincipalMgr() doesn't work properly.", principalMgr, action.getPrincipalMgr());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#setPrincipalMgr(PrincipalMgrRemote) method.
     * </p>
     * <p>
     * principalMgr should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPrincipalMgr() {
        PrincipalMgrRemote principalMgr = MockFactory.getPrincipalMgr();
        action.setPrincipalMgr(principalMgr);
        assertSame("setPrincipalMgr() doesn't work properly.", principalMgr, action.getPrincipalMgr());
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#checkInitialization() method with valid fields.
     * </p>
     * <p>
     * check initialization should pass successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        action.checkInitialization();
    }

    /**
     * <p>
     * Tests ViewAccountInfoAction#checkInitialization() method with null principalMgr.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_PrincipalMgr() throws Exception {
        action.setPrincipalMgr(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }
}

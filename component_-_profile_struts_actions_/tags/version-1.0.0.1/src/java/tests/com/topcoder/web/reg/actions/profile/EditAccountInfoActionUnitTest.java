/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.model.Coder;
import com.topcoder.web.common.model.CoderReferral;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Referral;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for EditAccountInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EditAccountInfoActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents EditAccountInfoAction instance for testing.
     * </p>
     */
    private EditAccountInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (EditAccountInfoAction) getBean("editAccountInfoAction");
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
     * Tests EditAccountInfoAction constructor.
     * </p>
     * <p>
     * EditAccountInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("EditAccountInfoAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#performAdditionalTasks() method with valid fields and passed user.
     * </p>
     * <p>
     * Values should be changed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.getUser();
        action.performAdditionalTasks(user);
        assertEquals("Values should be changed successfully.", 2, action.getCoderTypes().size());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#performAdditionalTasks() method with valid fields and passed user, but underlying
     * exception occurred.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPerformAdditionalTasks_Exception() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        when(action.getCoderTypeDAO().getCoderTypes()).thenThrow(new RuntimeException("just for testing."));
        try {
            action.performAdditionalTasks(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#processOutputData() method with valid fields and passed user, but underlying
     * exception occurred.
     * </p>
     * <p>
     * ProfileActionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData_Exception() throws Exception {
        when(action.getCoderTypeDAO().getCoderTypes()).thenThrow(new RuntimeException("just for testing."));
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
        when(action.getUserDAO().canChangeHandle(user.getHandle())).thenThrow(
                new RuntimeException("just for testing."));
        try {
            action.processOutputData(user);
            fail("ProfileActionException exception is expected.");
        } catch (ProfileActionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#processOutputData() method with valid fields and passed user.
     * </p>
     * <p>
     * User canChange flag should be true. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData() throws Exception {
        when(action.getCoderTypeDAO().getCoderTypes()).thenThrow(new RuntimeException("just for testing."));
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
        when(action.getUserDAO().canChangeHandle(user.getHandle())).thenReturn(true);
        action.processOutputData(user);
        assertTrue("User canChange flag should be true.", action.getCanChangeHandle());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#checkInitialization() method with valid fields.
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
     * Tests EditAccountInfoAction#checkInitialization() method with null coderTypeDAO.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_CoderTypeDAO() throws Exception {
        action.setCoderTypeDAO(null);
        try {
            action.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#getCoderTypeDAO() method.
     * </p>
     * <p>
     * coderTypeDAO should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCoderTypeDAO() {
        CoderTypeDAO coderTypeDAO = MockFactory.getCoderTypeDAO();
        action.setCoderTypeDAO(coderTypeDAO);
        assertSame("getCoderTypeDAO() doesn't work properly.", coderTypeDAO, action.getCoderTypeDAO());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#setCoderTypeDAO(CoderTypeDAO) method.
     * </p>
     * <p>
     * coderTypeDAO should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypeDAO() {
        CoderTypeDAO coderTypeDAO = MockFactory.getCoderTypeDAO();
        action.setCoderTypeDAO(coderTypeDAO);
        assertSame("setCoderTypeDAO() doesn't work properly.", coderTypeDAO, action.getCoderTypeDAO());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#getCoderTypes() method.
     * </p>
     * <p>
     * coderTypes should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCoderTypes() {
        List<CoderType> coderTypes = new ArrayList<CoderType>();
        action.setCoderTypes(coderTypes);
        assertSame("getCoderTypes() doesn't work properly.", coderTypes, action.getCoderTypes());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#setCoderTypes(List) method.
     * </p>
     * <p>
     * coderTypes should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCoderTypes() {
        List<CoderType> coderTypes = new ArrayList<CoderType>();
        action.setCoderTypes(coderTypes);
        assertSame("setCoderTypes() doesn't work properly.", coderTypes, action.getCoderTypes());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#getCanChangeHandle() method.
     * </p>
     * <p>
     * canChangeHandle should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCanChangeHandle() {
        boolean canChangeHandle = true;
        action.setCanChangeHandle(canChangeHandle);
        assertEquals("isCanChangeHandle() doesn't work properly.", canChangeHandle, action.getCanChangeHandle());
    }

    /**
     * <p>
     * Tests EditAccountInfoAction#setCanChangeHandle(boolean) method.
     * </p>
     * <p>
     * canChangeHandle should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCanChangeHandle() {
        boolean canChangeHandle = true;
        action.setCanChangeHandle(canChangeHandle);
        assertEquals("setCanChangeHandle() doesn't work properly.", canChangeHandle, action.getCanChangeHandle());
    }
}

/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.reg.BaseUnitTest;

/**
 * <p>
 * This class contains Unit tests for CompleteProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompleteProfileActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents CompleteProfileAction instance for testing.
     * </p>
     */
    private CompleteProfileAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (CompleteProfileAction) getBean("completeProfileAction");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests CompleteProfileAction constructor.
     * </p>
     * <p>
     * CompleteProfileAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("CompleteProfileAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests CompleteProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_AccountTab() throws Exception {
        CurrentTab currentTab = CurrentTab.ACCOUNT_TAB;
        action.setCurrentTab(currentTab);
        assertEquals("Action should be executed successfully.", "Save Account", action.execute());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_ContactTab() throws Exception {
        CurrentTab currentTab = CurrentTab.CONTACT_TAB;
        action.setCurrentTab(currentTab);
        assertEquals("Action should be executed successfully.", "Save Contact", action.execute());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#execute() method with valid fields.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_DemographicTab() throws Exception {
        CurrentTab currentTab = CurrentTab.DEMOGRAPHIC_TAB;
        action.setCurrentTab(currentTab);
        assertEquals("Action should be executed successfully.", "Save Demographic", action.execute());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#getCurrentTab() method.
     * </p>
     * <p>
     * currentTab should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetCurrentTab() {
        CurrentTab currentTab = CurrentTab.DEMOGRAPHIC_TAB;
        action.setCurrentTab(currentTab);
        assertSame("getCurrentTab() doesn't work properly.", currentTab, action.getCurrentTab());
    }

    /**
     * <p>
     * Tests CompleteProfileAction#setCurrentTab(CurrentTab) method.
     * </p>
     * <p>
     * currentTab should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetCurrentTab() {
        CurrentTab currentTab = CurrentTab.ACCOUNT_TAB;
        action.setCurrentTab(currentTab);
        assertSame("setCurrentTab() doesn't work properly.", currentTab, action.getCurrentTab());
    }
}

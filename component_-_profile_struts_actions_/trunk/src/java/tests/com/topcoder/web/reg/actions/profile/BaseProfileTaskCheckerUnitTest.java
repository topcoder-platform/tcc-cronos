/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for BaseProfileTaskChecker.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseProfileTaskCheckerUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BaseProfileTaskChecker instance for testing.
     * </p>
     */
    private BaseProfileTaskChecker checker;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        checker = (BaseProfileTaskChecker) getBean("baseProfileTaskChecker");
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
        checker = null;
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker constructor.
     * </p>
     * <p>
     * BaseProfileTaskChecker instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BaseProfileTaskChecker instance should be created successfully.", checker);
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker#checkInitialization() method with valid fields.
     * </p>
     * <p>
     * check initialization should pass successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        checker.checkInitialization();
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker#checkInitialization() method with null taskName.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_TaskName() throws Exception {
        checker.setTaskName(null);
        try {
            checker.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker#checkInitialization() method with empty taskName.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Empty_TaskName() throws Exception {
        checker.setTaskName("");
        try {
            checker.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker#getTaskName() method.
     * </p>
     * <p>
     * taskName should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetTaskName() {
        String taskName = "test";
        checker.setTaskName(taskName);
        assertSame("getTaskName() doesn't work properly.", taskName, checker.getTaskName());
    }

    /**
     * <p>
     * Tests BaseProfileTaskChecker#setTaskName(String) method.
     * </p>
     * <p>
     * taskName should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetTaskName() {
        String taskName = "test";
        checker.setTaskName(taskName);
        assertSame("setTaskName() doesn't work properly.", taskName, checker.getTaskName());
    }
}

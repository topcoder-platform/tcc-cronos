/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for ViewNameAndContactAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewNameAndContactActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ViewNameAndContactAction instance for testing.
     * </p>
     */
    private ViewNameAndContactAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = (ViewNameAndContactAction) getBean("viewNameAndContactAction");
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
     * Tests ViewNameAndContactAction constructor.
     * </p>
     * <p>
     * ViewNameAndContactAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ViewNameAndContactAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ViewNameAndContactAction#processOutputData(User) method with valid fields and passed user.
     * </p>
     * <p>
     * Displayed user should be changed. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testProcessOutputData() throws Exception {
        com.topcoder.web.common.model.User user = MockFactory.createUser(1L, "first name", "last name", "handle");
        action.processOutputData(user);
        assertEquals("Displayed user should be changed.", user, action.getDisplayedUser());
    }
}

/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for EditDemographicInfoAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EditDemographicInfoActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents EditDemographicInfoAction instance for testing.
     * </p>
     */
    private EditDemographicInfoAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        action = (EditDemographicInfoAction) getBean("editDemographicInfoAction");
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
     * Tests EditDemographicInfoAction constructor.
     * </p>
     * <p>
     * EditDemographicInfoAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("EditDemographicInfoAction instance should be created successfully.", action);
    }
}

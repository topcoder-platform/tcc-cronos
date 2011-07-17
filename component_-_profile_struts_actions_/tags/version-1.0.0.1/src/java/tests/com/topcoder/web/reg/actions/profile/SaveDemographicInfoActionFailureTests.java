/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.reg.ProfileActionConfigurationException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SaveDemographicInfoAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SaveDemographicInfoActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SaveDemographicInfoActionFailureTests.class);
    }

    /**
     * <p>
     * Tests SaveDemographicInfoAction#checkInitialization() for failure.
     * It tests the case that when demographicQuestionDAO is null and expects ProfileActionConfigurationException.
     * </p>
     */
    public void testcheckInitialization_NullDemographicQuestionDAO() {
        SaveDemographicInfoAction instance = new SaveDemographicInfoAction();
        try {
            instance.checkInitialization();
            fail("ProfileActionConfigurationException expected.");
        } catch (ProfileActionConfigurationException e) {
            //good
        }
    }

}
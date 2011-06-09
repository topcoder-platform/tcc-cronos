/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.action.ContestServicesActionException;
import com.topcoder.web.tc.action.PastContestsManagerAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for PastContestsManagerAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class PastContestsManagerActionFailureTests extends TestCase {
    /**
     * <P>
     * The PastContestsManagerAction instance for testing.
     * </p>
     */
    private PastContestsManagerAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new PastContestsManagerAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(PastContestsManagerActionFailureTests.class);
    }

    /**
     * <p>
     * Tests PastContestsManagerAction#checkConfiguration() for failure.
     * It tests the case that when pastContestsManager is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullPastContestsManager() {
        try {
            instance.setPastContestsManager(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests PastContestsManagerAction#execute() for failure.
     * Expects ContestServicesActionException.
     * </p>
     */
    public void testExecute_ContestServicesActionException() {
        String jsonStr = "{\"sortingOrder\":\"invalid\"}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        instance.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);
        EasyMock.replay(servletRequest);
        try {
            instance.execute();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }
}
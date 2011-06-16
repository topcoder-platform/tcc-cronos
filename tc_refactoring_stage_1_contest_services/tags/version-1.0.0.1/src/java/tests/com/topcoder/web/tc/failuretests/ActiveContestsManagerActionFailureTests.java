/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.action.ActiveContestsManagerAction;
import com.topcoder.web.tc.action.ContestServicesActionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ActiveContestsManagerAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ActiveContestsManagerActionFailureTests extends TestCase {
    /**
     * <P>
     * The ActiveContestsManagerAction instance for testing.
     * </p>
     */
    private ActiveContestsManagerAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ActiveContestsManagerAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ActiveContestsManagerActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ActiveContestsManagerAction#checkConfiguration() for failure.
     * It tests the case that when activeContestsManager is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testcheckConfiguration_NullActiveContestsManager() {
        try {
            instance.setActiveContestsManager(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ActiveContestsManagerAction#execute() for failure.
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
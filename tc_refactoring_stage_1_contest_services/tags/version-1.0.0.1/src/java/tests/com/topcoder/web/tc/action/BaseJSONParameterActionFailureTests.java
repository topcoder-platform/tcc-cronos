/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.action.ActiveContestsManagerAction;
import com.topcoder.web.tc.action.BaseJSONParameterAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseJSONParameterAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseJSONParameterActionFailureTests extends TestCase {

    /**
     * <P>
     * The BaseJSONParameterAction instance for testing.
     * </p>
     */
    private BaseJSONParameterAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    @Override
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
        return new TestSuite(BaseJSONParameterActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#getParameterAsJSONObject() for failure.
     * It tests the case that when JSON is invalid and expects ContestServicesActionException.
     * </p>
     */
    public void testGetParameterAsJSONObject_InvalidJSON() {
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        instance.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn("invalid");

        EasyMock.replay(servletRequest);

        try {
            instance.getParameterAsJSONObject();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#getParameterAsJSONObject() for failure.
     * It tests the case that when JSON is not JSONObject and expects ContestServicesActionException.
     * </p>
     */
    public void testGetParameterAsJSONObject_NotJSONObject() {
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        instance.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn("[\"Aunt\",\"Some Movie Guy\"]");

        EasyMock.replay(servletRequest);

        try {
            instance.getParameterAsJSONObject();
            fail("ContestServicesActionException expected.");
        } catch (ContestServicesActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#checkConfiguration() for failure.
     * It tests the case that when parameterKey is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullParameterKey() {
        try {
            instance.setParameterKey(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#checkConfiguration() for failure.
     * It tests the case that when parameterKey is empty and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyParameterKey() {
        try {
            instance.setParameterKey(" ");
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#checkConfiguration() for failure.
     * It tests the case that when dateFormatString is null and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NulldateFormatString() {
        try {
            instance.setDateFormatString(null);
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseJSONParameterAction#checkConfiguration() for failure.
     * It tests the case that when dateFormatString is empty and expects ContestServicesConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptydateFormatString() {
        try {
            instance.setDateFormatString(" ");
            instance.checkConfiguration();
            fail("ContestServicesConfigurationException expected.");
        } catch (ContestServicesConfigurationException e) {
            //good
        }
    }

}
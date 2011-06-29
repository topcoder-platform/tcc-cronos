/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests.action;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.StandardJSONEncoder;
import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.action.ContestServicesActionException;
import com.topcoder.web.tc.action.SearchContestsManagerAction;
import com.topcoder.web.tc.action.UpcomingContestsManagerAction;

/**
 * <p>
 * Failure test for UpcomingContestsManagerAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/failure.xml" })
public class UpcomingContestsManagerActionFailureTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of UpcomingContestsManagerAction used in test.
     */
    private UpcomingContestsManagerAction action;

    /**
     * Represents the instance of UpcomingContestsManager used in test.
     */
    private UpcomingContestsManager manager;

    /**
     * The http servlet request;
     */
    private MockHttpServletRequest request;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() throws Exception {
        manager = (UpcomingContestsManager) applicationContext.getBean("upcomingContestsManager");
        action = new UpcomingContestsManagerAction();
        action.setUpcomingContestsManager(manager);

        request = new MockHttpServletRequest();
        action.setServletRequest(request);
    }

    /**
     * Failure test execute(). When the request is invalid.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_RequestIsInvalid1() throws Exception {
        action.execute();
    }

    /**
     * Failure test execute(). When the request is invalid.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_RequestIsInvalid2() throws Exception {
        StandardJSONEncoder encoder = new StandardJSONEncoder();
        JSONObject json = new JSONObject();
        json.setString("sortingOrder", "Invalid");
        request.setParameter("parameter", encoder.encode(json));
        action.execute();
    }

    /**
     * Failure test execute(). When the request is invalid.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_RequestIsInvalid3() throws Exception {
        StandardJSONEncoder encoder = new StandardJSONEncoder();
        JSONObject json = new JSONObject();
        json.setInt("pageNumber", -2);
        request.setParameter("parameter", encoder.encode(json));
        action.execute();
    }

    /**
     * Failure test execute(). When the request is invalid.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_RequestIsInvalid4() throws Exception {
        StandardJSONEncoder encoder = new StandardJSONEncoder();
        JSONObject json = new JSONObject();
        json.setInt("pageSize", -1);
        request.setParameter("parameter", encoder.encode(json));
        action.execute();
    }

    /**
     * Failure test checkConfiguration(). When searchContestsManager is not set.
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfiguration_searchContestsManagerIsNull() {
        new SearchContestsManagerAction().checkConfiguration();
    }
}
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
import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.action.ContestServicesActionException;
import com.topcoder.web.tc.action.SearchContestsManagerAction;

/**
 * <p>
 * Failure test for SearchContestsManagerAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/failure.xml" })
public class SearchContestsManagerActionFailureTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of SearchContestsManagerAction used in test.
     */
    private SearchContestsManagerAction action;

    /**
     * Represents the instance of SearchContestsManager used in test.
     */
    private SearchContestsManager manager;

    /**
     * The http servlet request;
     */
    private MockHttpServletRequest request;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() throws Exception {
        manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
        action = new SearchContestsManagerAction();
        action.setSearchContestsManager(manager);

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
     * Failure test execute(). When the request is invalid.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testExecute_RequestIsInvalid5() throws Exception {
        StandardJSONEncoder encoder = new StandardJSONEncoder();
        JSONObject json = new JSONObject();
        JSONObject jsonFilter = new JSONObject();
        JSONObject jsonContestFinalization = new JSONObject();
        jsonFilter.setNestedObject("contestFinalization", jsonContestFinalization);
        jsonContestFinalization.setString("firstDate", "2011");
        json.setNestedObject("filter", jsonFilter);
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
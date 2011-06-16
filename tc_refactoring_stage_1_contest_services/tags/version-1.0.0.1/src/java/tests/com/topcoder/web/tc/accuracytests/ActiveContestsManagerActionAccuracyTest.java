/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.action.ActiveContestsManagerAction;
import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.impl.ActiveContestsManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for ActiveContestsManagerAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ActiveContestsManagerActionAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents ActiveContestsManagerAction instance for testing.
     * </p>
     */
    private ActiveContestsManagerAction action;

    /**
     * <p>
     * Represents ActiveContestsManagerImpl instance for testing.
     * </p>
     */
    private ActiveContestsManagerImpl impl;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ActiveContestsManagerActionAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        action = new ActiveContestsManagerAction();
        impl = createActiveContestsManagerImpl();
        action.setActiveContestsManager(impl);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        super.tearDown();
        impl = null;
        action = null;
    }

    /**
     * <p>
     * Tests ActiveContestsManagerAction constructor.
     * </p>
     * <p>
     * ActiveContestsManagerAction instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("ActiveContestsManagerAction instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link ActiveContestsManagerAction#execute()} with no filtering, but paging and sorting.
     * </p>
     * <p>
     * Project1 should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testExecute() throws Exception {
        String jsonStr =
                "{\"columnName\":\"firstPrizeInfo.value\","
                        + "\"sortingOrder\":\"ASCENDING\", \"pageNumber\":\"1\", \"pageSize\":\"1\"}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);
        EasyMock.replay(servletRequest);
        action.execute();
        List < ActiveContestDTO > actual = action.getActiveContests();
        assertEquals("Project should be retrieved successfully.", 1, actual.size());
        assertEquals("Project should be retrieved successfully.", "Project1", actual.get(0).getContestName());
        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Tests {@link ActiveContestsManagerAction#execute()} with all types of filtering.
     * </p>
     * <p>
     * Project2 should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testExecute2() throws Exception {
        String jsonStr =
                "{\"columnName\":\"firstPrizeInfo.value\","
                        + "\"sortingOrder\":\"ASCENDING\", \"pageNumber\":\"1\", \"pageSize\":\"2\","
                        + " \"filter\":{\"contestName\":\"roject2\","
                        + "\"submissionEndDate\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2021/04/01\"},"
                        + "\"prizeUpperBound\":800,\"contestFinalizationDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                        + "\"catalogs\":[\"Competitions\"]," + "\"subTypes\":[\"Component Design\"],"
                        + "\"types\":[\"Design\"],\"prizeLowerBound\":\"-1\","
                        + "\"registrationStartDate\":{\"intervalType\":\"BETWEEN_DATES\","
                        + "\"firstDate\":\"2009/01/01\",\"secondDate\":\"2100/05/01\"}}}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);
        EasyMock.replay(servletRequest);
        action.execute();
        List < ActiveContestDTO > actual = action.getActiveContests();
        assertEquals("Project should be retrieved successfully.", 1, actual.size());
        assertEquals("Project should be retrieved successfully.", "Project2", actual.get(0).getContestName());
        EasyMock.verify(servletRequest);
    }
}

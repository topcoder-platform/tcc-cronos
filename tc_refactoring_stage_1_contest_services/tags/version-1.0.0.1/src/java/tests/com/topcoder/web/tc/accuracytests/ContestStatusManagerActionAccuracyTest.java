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

import com.topcoder.web.tc.action.ContestStatusManagerAction;
import com.topcoder.web.tc.dto.ContestStatusDTO;
import com.topcoder.web.tc.impl.ContestStatusManagerImpl;

/**
 * <p>
 * This class contains all Accuracy tests for ContestStatusManagerAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ContestStatusManagerActionAccuracyTest extends BaseAccuracyTest {

    /**
     * <p>
     * Represents ContestStatusManagerAction instance for testing.
     * </p>
     */
    private ContestStatusManagerAction action;

    /**
     * <p>
     * Represents ContestStatusManagerImpl instance for testing.
     * </p>
     */
    private ContestStatusManagerImpl impl;

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy tests for class under test.
     * </p>
     * @return TestSuite that aggregates all Accuracy tests for class under test
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ContestStatusManagerActionAccuracyTest.class);
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        super.setUp();
        action = new ContestStatusManagerAction();
        impl = createContestStatusManagerImpl();
        action.setContestStatusManager(impl);
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
     * Tests ContestStatusManagerAction constructor.
     * </p>
     * <p>
     * ContestStatusManagerAction instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("ContestStatusManagerAction instance should be created successfully.", impl);
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerAction#execute()} with paging.
     * </p>
     * <p>
     * Project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testExecute1() throws Exception {
        String jsonStr =
                "{\"columnName\":\"firstPrizeInfo.value\","
                        + "\"sortingOrder\":\"ASCENDING\", \"pageNumber\":1, \"pageSize\":\"1\"}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);
        EasyMock.replay(servletRequest);
        action.execute();
        List < ContestStatusDTO > actual = action.getContestStatuses();
        assertEquals("Project should be retrieved successfully.", 1, actual.size());
        assertEquals("Project1 should be retrieved successfully.", "Submission", actual.get(0).getCurrentPhase());
        EasyMock.verify(servletRequest);
    }

    /**
     * <p>
     * Tests {@link ContestStatusManagerAction#execute()} with all types of filtering.
     * </p>
     * <p>
     * Project should be retrieved successfully.
     * </p>
     * @throws Exception if any error occurs
     */
    @Test
    public void testExecute2() throws Exception {
        String jsonStr =
                "{\"columnName\":\"projectGroupCategory.name\","
                        + "\"sortingOrder\":\"ASCENDING\", \"pageNumber\":1, \"pageSize\":\"2\","
                        + " \"filter\":{\"contestName\":\"roject2\","
                        + "\"submissionEndDate\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2021/04/01\"},"
                        + "\"prizeUpperBound\":\"800\",\"contestFinalizationDate\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                        + "\"catalogs\":[\"Competitions\"]," + "\"subTypes\":[\"Component Design\"],"
                        + "\"types\":[\"Design\"],\"prizeLowerBound\":\"-1\","
                        + "\"registrationStartDate\":{\"intervalType\":\"BETWEEN_DATES\","
                        + "\"firstDate\":\"2000/04/01\",\"secondDate\":\"2100/05/01\"},"
                        + "\"winnerHandle\":\"Handle11\"}}";
        HttpServletRequest servletRequest = EasyMock.createMock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        EasyMock.expect(servletRequest.getParameter("parameter")).andReturn(jsonStr);
        EasyMock.replay(servletRequest);
        action.execute();
        List < ContestStatusDTO > actual = action.getContestStatuses();
        assertEquals("Project should be retrieved successfully.", 1, actual.size());
        assertEquals("Project2 should be retrieved successfully.", "Registration", actual.get(0).getCurrentPhase());
        EasyMock.verify(servletRequest);
    }
}

/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.action;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.action.SearchContestsManagerAction;
import com.topcoder.web.tc.dto.ContestDTO;
import com.topcoder.web.tc.implement.SearchContestsManagerImpl;

/**
 * <p>
 * Accuracy test for SearchContestsManagerAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SearchContestsManagerActionAccuracyTest extends StrutsSpringTestCase {
    /**
     * Represents the instance of SearchContestsManagerAction used in test.
     */
    private SearchContestsManagerAction action;

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        action = (SearchContestsManagerAction) applicationContext.getBean("searchContestsManagerAction");
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Accuracy test for SearchContestsManagerAction(). The instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("The instance should be created.", new SearchContestsManagerAction());
    }

    /**
     * <p>
     * Tests the setSearchContestsManager(SearchContestsManager) method. It uses valid manager. No exception should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetSearchContestsManager() throws Exception {
        SearchContestsManager manager = new SearchContestsManagerImpl();
        action.setSearchContestsManager(manager);

        Field managerField = action.getClass().getDeclaredField("searchContestsManager");
        managerField.setAccessible(true);

        assertEquals("The setSearchContestsManager is incorrect.", manager, managerField.get(action));
    }

    /**
     * Accuracy test for getContests(). Default to null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetContests() throws Exception {
        assertNull("The getContests is incorrect.", action.getContests());
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string with pagination and verifies the contests property is set
     * correctly. No exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute_WithPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Conceptualization\",\"Component Design\"],"
            + "\"subtype\":[\"System Assembly\",\"Conceptualization\"],"
            + "\"catalog\":[\"Design\",\"UI Development\"],\"contestName\":\"test\","
            + "\"registrationStart\":{\"intervalType\":\"AFTER\",\"firstDate\":\"2011/01/14\"},"
            + "\"submissionEnd\":{\"intervalType\":\"AFTER_CURRENT_DATE\"},"
            + "\"contestFinalization\":{\"intervalType\":\"BETWEEN_DATES\",\"firstDate\":\"2011/08/01\","
            + "\"secondDate\":\"2011/08/31\"},\"winnerHandle\":\"winner\"},\"pageNumber\":1,\"pageSize\":1}";

        request.setParameter("parameter", jsonString);
        action.setServletRequest(request);
        action.execute();

        List<ContestDTO> result = action.getContests();
        assertEquals("The searchContests is incorrect.", 1, result.size());

        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }
}
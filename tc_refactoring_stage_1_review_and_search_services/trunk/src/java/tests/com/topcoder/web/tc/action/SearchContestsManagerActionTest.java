/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.dto.ContestDTO;
import com.topcoder.web.tc.implement.SearchContestsManagerImpl;

/**
 * <p>
 * Tests the SearchContestsManagerAction class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class SearchContestsManagerActionTest extends StrutsSpringTestCase {

    /**
     * Represents the action to test
     */
    private SearchContestsManagerAction action;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
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
     * Tears down testing environment. Set variable to null.
     * </p>
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
     * Tests the default constructor. It verifies nothing.
     * </p>
     */
    public void testCtor() {
        new SearchContestsManagerAction();
    }

    /**
     * <p>
     * Tests the checkConfiguration() method. It invokes this method before the manager is set.
     * ContestServicesConfigurationException is expected to be thrown.
     * </p>
     */
    public void testCheckConfiguration_NullManager() {
        try {
            action = new SearchContestsManagerAction();
            action.checkConfiguration();
            fail("ContestServicesConfigurationException should be thrown");
        } catch (ContestServicesConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the checkConfiguration() method. It invokes this method after the manager is set. No exception should be
     * thrown.
     * </p>
     */
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }

    /**
     * <p>
     * Tests the setSearchContestsManager(SearchContestsManager) method. It uses valid manager. No exception should be
     * thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testSetSearchContestsManager() throws Exception {
        SearchContestsManager manager = new SearchContestsManagerImpl();
        action.setSearchContestsManager(manager);
        Field managerField = action.getClass().getDeclaredField("searchContestsManager");
        managerField.setAccessible(true);
        assertEquals("searchContestsManager should be set", manager, managerField.get(action));
    }

    /**
     * <p>
     * Tests the execute() method. It uses invalid JSON string. ServletException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute_Exception() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Component Design\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\"}}}";
        request.setParameter("parameter", jsonString);
        try {
            executeAction("/searchContestsManagerAction");
            fail("ServletException should be thrown");
        } catch (ServletException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string with pagination and verifies the contests property is set
     * correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_WithPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Component Design\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"AFTER_CURRENT_DATE\"},"
                + "\"contestFinalization\":{\"intervalType\":\"BETWEEN_DATES\",\"firstDate\":\"2011/08/01\","
                + "\"secondDate\":\"2011/08/31\"},\"winnerHandle\":\"winner\"},\"pageNumber\":2,\"pageSize\":1}";
        request.setParameter("parameter", jsonString);
        executeAction("/searchContestsManagerAction");
        List<ContestDTO> contests = (List<ContestDTO>) findValueAfterExecute("contests");
        assertEquals("1 contest should be returned", 1, contests.size());
        ContestDTO contest = contests.get(0);
        assertEquals("contest name should be returned", "test project 1", contest.getContestName());
        assertEquals("type should be returned", "Component Design", contest.getType());
        assertEquals("subtype should be returned", "Component Design", contest.getSubtype());
        assertEquals("catalog should be returned", "Design", contest.getCatalog());
        assertEquals("number of registrants should be returned", 0, contest.getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 0, contest.getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 0, contest.getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 1", contest.getWinnerProfileLink());
        assertEquals("winner score should be returned", 100.0, contest.getWinnerScore());
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string without pagination and verifies the contests property is
     * set correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_NoPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Component Design\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Design\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"AFTER_CURRENT_DATE\"},"
                + "\"contestFinalization\":{\"intervalType\":\"BETWEEN_DATES\",\"firstDate\":\"2011/08/01\","
                + "\"secondDate\":\"2011/08/31\"},\"winnerHandle\":\"winner\"},\"columnName\":\"winnerProfileLink\","
                + "\"sortingOrder\":\"DESCENDING\"}";
        request.setParameter("parameter", jsonString);
        executeAction("/searchContestsManagerAction");
        List<ContestDTO> contests = (List<ContestDTO>) findValueAfterExecute("contests");
        assertEquals("2 contest should be returned", 2, contests.size());
        ContestDTO contest = contests.get(0);
        assertEquals("contest name should be returned", "test project 2", contest.getContestName());
        assertEquals("type should be returned", "Component Design", contest.getType());
        assertEquals("subtype should be returned", "Component Design", contest.getSubtype());
        assertEquals("catalog should be returned", "Design", contest.getCatalog());
        assertEquals("number of registrants should be returned", 2, contest.getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 2, contest.getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 2, contest.getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 2", contest.getWinnerProfileLink());
        assertEquals("winner score should be returned", 95.0, contest.getWinnerScore());
    }
}

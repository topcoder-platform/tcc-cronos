/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.action;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.ContestServicesConfigurationException;
import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.implement.UpcomingContestsManagerImpl;

/**
 * <p>
 * Tests the UpcomingContestsManagerAction class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class UpcomingContestsManagerActionTest extends StrutsSpringTestCase {

    /**
     * Represents the action to test
     */
    private UpcomingContestsManagerAction action;

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
        action = (UpcomingContestsManagerAction) applicationContext.getBean("upcomingContestsManagerAction");
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
            action = new UpcomingContestsManagerAction();
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
     * Tests the setUpcomingContestsManager(UpcomingContestsManager) method. It uses valid manager. No exception should
     * be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    public void testSetUpcomingContestsManager() throws Exception {
        UpcomingContestsManager manager = new UpcomingContestsManagerImpl();
        action.setUpcomingContestsManager(manager);
        Field managerField = action.getClass().getDeclaredField("upcomingContestsManager");
        managerField.setAccessible(true);
        assertEquals("upcomingContestsManager should be set", manager, managerField.get(action));
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
                + "\"catalog\":[\"Development\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\"}}}";
        request.setParameter("parameter", jsonString);
        try {
            executeAction("/upcomingContestsManagerAction");
            fail("ServletException should be thrown");
        } catch (ServletException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string with pagination and verifies the upcomingContests property
     * is set correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_WithPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Component Design\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Development\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"AFTER_CURRENT_DATE\"},"
                + "\"prizeStart\":600,\"prizeEnd\":\"800\"},\"pageNumber\":1,\"pageSize\":1}";
        request.setParameter("parameter", jsonString);
        executeAction("/upcomingContestsManagerAction");
        List<UpcomingContestDTO> upcomingContests = (List<UpcomingContestDTO>) findValueAfterExecute("upcomingContests");
        assertEquals("1 upcoming contest should be returned", 1, upcomingContests.size());
        UpcomingContestDTO upcomingContest = upcomingContests.get(0);
        assertEquals("contest name should be returned", "test project 4 1.1", upcomingContest.getContestName());
        assertEquals("type should be returned", "Assembly", upcomingContest.getType());
        assertEquals("subtype should be returned", "System Assembly", upcomingContest.getSubtype());
        assertEquals("register date should be returned", "2011-08-23 12:00:00.0", upcomingContest.getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-24 18:00:00.0", upcomingContest.getSubmitDate().toString());
        assertEquals("duration should be returned", 1, upcomingContest.getDuration());
        assertEquals("technologies should be returned", "J2EE", upcomingContest.getTechnologies());
        assertEquals("status should be returned", "Draft", upcomingContest.getStatus());
        assertEquals("first prize should be returned", 800.0, upcomingContest.getFirstPrize());
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string without pagination and verifies the upcomingContests
     * property is set correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testExecute_NoPagination() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\",\"Component Design\"],"
                + "\"subtype\":[\"Component Design\",\"System Assembly\",\"Conceptualization\"],"
                + "\"catalog\":[\"Development\",\"UI Development\"],\"contestName\":\"test\","
                + "\"registrationStart\":{\"intervalType\":\"AFTER\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"AFTER_CURRENT_DATE\"},"
                + "\"prizeStart\":600,\"prizeEnd\":\"800\"}}";
        request.setParameter("parameter", jsonString);
        executeAction("/upcomingContestsManagerAction");
        List<UpcomingContestDTO> upcomingContests = (List<UpcomingContestDTO>) findValueAfterExecute("upcomingContests");
        assertEquals("1 upcoming contest should be returned", 1, upcomingContests.size());
        UpcomingContestDTO upcomingContest = upcomingContests.get(0);
        assertEquals("contest name should be returned", "test project 4 1.1", upcomingContest.getContestName());
        assertEquals("type should be returned", "Assembly", upcomingContest.getType());
        assertEquals("subtype should be returned", "System Assembly", upcomingContest.getSubtype());
        assertEquals("register date should be returned", "2011-08-23 12:00:00.0", upcomingContest.getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-24 18:00:00.0", upcomingContest.getSubmitDate().toString());
        assertEquals("duration should be returned", 1, upcomingContest.getDuration());
        assertEquals("technologies should be returned", "J2EE", upcomingContest.getTechnologies());
        assertEquals("status should be returned", "Draft", upcomingContest.getStatus());
        assertEquals("first prize should be returned", 800.0, upcomingContest.getFirstPrize());
    }
}

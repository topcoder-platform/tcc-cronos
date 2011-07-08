/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.action;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.action.UpcomingContestsManagerAction;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.implement.UpcomingContestsManagerImpl;

/**
 * <p>
 * Accuracy test for UpcomingContestsManagerAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpcomingContestsManagerActionAccuracyTest extends StrutsSpringTestCase {
    /**
     * Represents the instance of UpcomingContestsManagerAction used in test.
     */
    private UpcomingContestsManagerAction action;

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
        action = (UpcomingContestsManagerAction) applicationContext.getBean("upcomingContestsManagerAction");
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
     * Accuracy test for UpcomingContestsManagerAction(). The instance should be created.
     */
    public void testCtor() {
        assertNotNull("The instance should be created.", new UpcomingContestsManagerAction());
    }

    /**
     * Accuracy test for setUpcomingContestsManager(UpcomingContestsManager upcomingContestsManager). The manager should
     * be set.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetUpcomingContestsManager() throws Exception {
        UpcomingContestsManager manager = new UpcomingContestsManagerImpl();
        action.setUpcomingContestsManager(manager);

        Field managerField = action.getClass().getDeclaredField("upcomingContestsManager");
        managerField.setAccessible(true);

        assertEquals("The setUpcomingContestsManager is incorrect.", manager, managerField.get(action));
    }

    /**
     * Accuracy test for getUpcomingContests(). Default to null.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetUpcomingContests() throws Exception {
        assertNull("The setUpcomingContestsManager is incorrect.", action.getUpcomingContests());
    }

    /**
     * <p>
     * Tests the execute() method. It uses valid JSON string with pagination and verifies the upcomingContests property
     * is set correctly. No exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testExecute() throws Exception {
        String jsonString = "{\"filter\":{\"type\":[\"Assembly\"],"
            + "\"subtype\":[\"System Assembly\",\"Conceptualization\"],"
            + "\"catalog\":[\"Development\",\"UI Development\"],\"contestName\":\"project\","
            + "\"prizeStart\":400,\"prizeEnd\":\"800\"},\"pageNumber\":1,\"pageSize\":10}";

        request.setParameter("parameter", jsonString);
        action.setServletRequest(request);
        action.execute();

        List<UpcomingContestDTO> result = action.getUpcomingContests();

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 800.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-23 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Draft", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-24 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "System Assembly", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Assembly", dto1.getType());
    }
}
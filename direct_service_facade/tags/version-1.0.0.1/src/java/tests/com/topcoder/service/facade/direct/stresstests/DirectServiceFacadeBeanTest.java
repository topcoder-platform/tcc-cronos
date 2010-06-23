/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.stresstests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;

import com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBean;
import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for the <code>DirectServiceFacadeBean</code> class.
 * This class is not thread safe, so there is no threading test. Also it is only a facade, so use mocking should be
 * fine, the real burden is for underlying services.
 * </p>
 *
 * @author assistant
 * @version 1.0
 */
public class DirectServiceFacadeBeanTest extends TestCase {

    /**
     * <p>
     * Represents the <code>DirectServiceFacadeBean</code> instance for test.
     * </p>
     */
    private DirectServiceFacadeBean instance;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {

        instance = new DirectServiceFacadeBean();

        inject(instance, "loggerName", "direct_service_facade_log");
        inject(instance, "projectManagerClassName", MockProjectManager.class.getName());
        inject(instance, "projectLinkManagerClassName", MockProjectLinkManager.class.getName());
        inject(instance, "phaseManagerClassName", MockPhaseManager.class.getName());
        inject(instance, "receiptEmailTitleTemplateText", "Receipt for contest \"%contestName%\"");
        inject(instance, "receiptEmailBodyTemplateText", "test");
        inject(instance, "budgetUpdateEmailTitleTemplateText", "Budget Updated");
        inject(instance, "budgetUpdateEmailBodyTemplateText", "test");
        inject(instance, "emailSender", "test@mydomain.com");

        inject(instance, "contestServiceFacade", new MockContestServiceFacade());
        inject(instance, "userService", new MockUserService());
        inject(instance, "projectDAO", new MockProjectDAO());

        invoke(instance, "initialize");
    }

    /**
     * Invokes the method.
     * @param instance the instance
     * @param name the method name
     * @throws Exception to JUnit
     */
    private static void invoke(Object instance, String name) throws Exception {
        Method method = null;
        try {
            method = instance.getClass().getDeclaredMethod(name);
            method.setAccessible(true);
            method.invoke(instance);
        } finally {
            method.setAccessible(false);
        }
    }
    /**
     * <p>
     * Sets the value of a private field in the given instance.
     * </p>
     *
     * @param instance the instance which the private field belongs to
     * @param name the name of the private field to be set
     * @param value the value to set
     * @throws Exception to JUnit
     */
    private static void inject(Object instance, String name, Object value) throws Exception {
        Field field = null;
        try {
            field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>
     * Test for the <code>getContestPrize</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetContestPrize() throws Exception {
        ContestPrize contestPrize = null;
        for (int i = 0; i < 100; i++) {
            contestPrize = instance.getContestPrize(new TCSubject(1), 1, true);
        }

        // check the contest id
        assertEquals("The contest id is wrong.", 1, contestPrize.getContestId());
        // check the prize
        double[] prizes = contestPrize.getContestPrizes();
        assertEquals("The prize size is wrong.", 1000, prizes.length);
        for (int i = 0; i < 1000; i++) {
            assertEquals("The prize is wrong.", 123, prizes[i], Double.MIN_VALUE);
        }
    }

    /**
     * <p>
     * Test for the <code>getProjectGamePlan</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectGamePlan() throws Exception {
        for (int i = 0; i < 100; i++) {
            List<ContestPlan> plans = instance.getProjectGamePlan(new TCSubject(1), 1);
            assertEquals("The gameplans size is wrong.", 1000, plans.size());
        }
    }
}

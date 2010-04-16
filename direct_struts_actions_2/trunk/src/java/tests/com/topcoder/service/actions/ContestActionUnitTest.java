/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for <code>ContestAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private GetDocumentsContestAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new GetDocumentsContestAction();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testInheritance() throws Exception {
        TestHelper
            .assertSuperclass(instance.getClass().getSuperclass().getSuperclass(), BaseDirectStrutsAction.class);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getContestServiceFacade. Verifies the returned value is correct.
     */
    @Test
    public void test_getContestServiceFacade_Accuracy() {
        assertEquals("incorrect default value", null, instance.getContestServiceFacade());
        MockContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        assertSame("incorrect value after setting", facade, instance.getContestServiceFacade());
    }

    /**
     * Accuracy Test for setContestServiceFacade. Verifies the assigned value is correct.
     */
    @Test
    public void test_setContestServiceFacade_Accuracy() {
        MockContestServiceFacade facade = new MockContestServiceFacade();
        instance.setContestServiceFacade(facade);
        assertSame("incorrect value after setting", facade, instance.getContestServiceFacade());
    }

}

/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.struts2.interceptor.SessionAware;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.service.actions.AbstractAction;

/**
 * <p>
 * Unit tests for <code>SessionAwareAction</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SessionAwareActionUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private MockSpecificationReviewAction instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new MockSpecificationReviewAction();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Tests the class inheritance to make sure it is correct.
     */
    @Test
    public void testInheritance() {
        TestHelper.assertSuperclass(SessionAwareAction.class, AbstractAction.class);
        assertTrue("inheritance is wrong", instance instanceof SessionAware);
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getSession. Verifies the returned value is correct.
     */
    @Test
    public void test_getSession_Accuracy() {
        Map<String, Object> test = new HashMap<String, Object>();
        instance.setSession(test);
        assertSame("getter is wrong", test, instance.getSession());
    }

    /**
     * Accuracy test for setSession. Verifies the assigned value is correct.
     */
    @Test
    public void test_setSession_Accuracy() {
        Map<String, Object> test = new HashMap<String, Object>();
        instance.setSession(test);
        TestHelper.assertFieldSame(SessionAwareAction.class, test, instance, "session");
    }

}

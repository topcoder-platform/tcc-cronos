/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.log.LogManager;

/**
 * <p> Unit test case of {@link ShowCardDescriptionAction}. </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class ShowCardDescriptionActionTest extends TestCase {
    /**
     * <p>
     * The ShowCardDescriptionAction instance to test.
     * </p>
     * */
    private ShowCardDescriptionAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new ShowCardDescriptionAction();
        instance.setLog(LogManager.getLog());
        instance.setAuthenticationSessionKey("authenticationkey");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p> Creates a test suite for the tests in this test case. </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ShowCardDescriptionActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor ShowCardDescriptionAction.
     * It verifies instance is correctly created.
     * </p>
     * @throws Exception to Junit
     */
    public void test_ShowCardDescriptionAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method execute.
     *
     * The result should be correct.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute() throws Exception {
        String res = instance.execute();
        assertEquals("Incorrect result", "success", res);
    }
    
}

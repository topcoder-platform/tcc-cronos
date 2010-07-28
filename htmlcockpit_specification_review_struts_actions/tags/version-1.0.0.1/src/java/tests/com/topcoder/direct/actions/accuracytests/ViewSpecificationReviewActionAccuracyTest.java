/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.accuracytests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.direct.actions.ViewSpecificationReviewAction;
import com.topcoder.security.TCSubject;


/**
 * Accuracy tests for class <code>ViewSpecificationReviewAction</code>.
 *
 * @author onsky
 * @version 1.0
 */
public class ViewSpecificationReviewActionAccuracyTest extends TestCase {
	
	private ViewSpecificationReviewAction instance;
    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
    	instance = new ViewSpecificationReviewAction();
    	instance.setSpecificationReviewService(new MockSpecificationReviewService());
    	instance.setSpecReviewCommentService(new MockSpecReviewCommentService());
        super.setUp();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests the accuracy of constructor ViewSpecificationReviewAction(String).
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testViewSpecificationReviewAction_Accuracy() throws Exception {
        ViewSpecificationReviewAction action = new ViewSpecificationReviewAction();
        assertNotNull("the action is not initialized", action);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>execute()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_execute_Accuracy1() throws Exception {
        String result = instance.execute();
        assertEquals("the result must be correct", Action.ERROR, result);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>execute()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_execute_Accuracy2() throws Exception {
    	instance = new ViewSpecificationReviewAction();
        String result = instance.execute();
        assertEquals("the result must be correct", Action.ERROR, result);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>execute()</code>.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_execute_Accuracy3() throws Exception {
    	instance.setContestId(1000);
    	instance.setStudio(true);
    	Map<String, Object> session = new HashMap<String, Object>();
    	TCSubject sub = new TCSubject(20);
    	session.put("tcSubject", sub);
    	instance.setSession(session);
        String result = instance.execute();
        assertEquals("the result must be correct", Action.SUCCESS, result);
    }
}

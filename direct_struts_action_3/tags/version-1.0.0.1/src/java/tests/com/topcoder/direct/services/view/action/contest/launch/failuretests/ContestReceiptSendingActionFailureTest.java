/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch.failuretests;

import java.util.HashMap;

import junit.framework.TestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.topcoder.direct.services.view.action.contest.launch.ContestReceiptSendingAction;

/**
 * <p>
 * Failure tests for ContestReceiptSendingAction.
 * </p>
 * 
 * @author Beijing2008
 * @version 1.0
 */
public class ContestReceiptSendingActionFailureTest extends TestCase {

    /** Represents ContestReceiptSendingAction instance for test. */
    private ContestReceiptSendingAction instance;

    /**
     * <p>
     * Setup the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new ContestReceiptSendingAction();
        instance.prepare();
        ActionContext actionContext = new ActionContext(new HashMap<String, Object>());
        actionContext.setActionInvocation(new MockActionInvocation());
        ActionContext.setContext(actionContext);
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }
    /**
     * Tests for execute() with illegal state.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_execute() throws Exception {
        try {
            instance.execute();
            fail("Expects IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }
    /**
     * Tests for setAdditionalEmailAddresses() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setAdditionalEmailAddresses3() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        //empty
        instance.setAdditionalEmailAddresses(";xxx;");
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setAdditionalEmailAddresses() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setAdditionalEmailAddresses4() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setAdditionalEmailAddresses("@b.com");
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setAdditionalEmailAddresses() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setAdditionalEmailAddresses5() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setAdditionalEmailAddresses("a@");
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    /**
     * Tests for setAdditionalEmailAddresses() with illegal input.
     * 
     * 
     * @throws Exception
     *             to junit
     */
    public void test_setAdditionalEmailAddresses6() throws Exception {
        instance.setDirectServiceFacade(new FailureMockDirectServiceFacade());
        instance.setAdditionalEmailAddresses("a#b.com");
        instance.execute();
        assertEquals(instance.getFieldErrors().size(), 1);
    }
    
    
}

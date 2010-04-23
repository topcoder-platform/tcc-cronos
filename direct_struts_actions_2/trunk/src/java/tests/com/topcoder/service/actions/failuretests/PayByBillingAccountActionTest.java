/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.annotations.Before;
import com.topcoder.service.actions.PayByBillingAccountAction;

/**
 * Failure tests for <code>PayByBillingAccountAction</code>.
 * @author moon.river
 * @version 1.0
 */
public class PayByBillingAccountActionTest extends StrutsSpringTestCase {

    /**
     * Instance to test.
     */
    private PayByBillingAccountAction instance;

    /**
     * Proxy.
     */
    private ActionProxy proxy;

    /**
     * Sets up the environment.
     * @throws java.lang.Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new PayByBillingAccountAction();
        instance.prepare();

        // prepare the action proxy
        proxy = getActionProxy("/FailurePayByBillingAccountAction");
        Helper.setUpSession(proxy);

        FailurePayByBillingAccountAction.clientId = 1;
        FailurePayByBillingAccountAction.projectId = 1;
        FailurePayByBillingAccountAction.poNumber = "abcd";
    }

    /**
     * Cleans up the environment.
     * @throws java.lang.Exception to JUnit
     */
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPoNumber_Null() throws Exception {
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        FailurePayByBillingAccountAction.poNumber = null;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testPoNumber_Empty() throws Exception {
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        FailurePayByBillingAccountAction.poNumber = "";

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }


    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryYear_Negative() throws Exception {
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        FailurePayByBillingAccountAction.clientId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 2, action.getFieldErrors().size());
    }

    /**
     * Test method for {@link com.topcoder.service.actions.FileUploadAttachContestFileAction#setContestId(long)}.
     * @throws Exception to JUnit
     */
    public void testExpiryMonth_Negative() throws Exception {
        PayByBillingAccountAction action = (PayByBillingAccountAction) proxy.getAction();

        FailurePayByBillingAccountAction.projectId = -1;

        proxy.execute();

        assertTrue("There should be errors.", action.hasFieldErrors());
        assertEquals("There should be one error field message.", 3, action.getFieldErrors().size());
    }

}

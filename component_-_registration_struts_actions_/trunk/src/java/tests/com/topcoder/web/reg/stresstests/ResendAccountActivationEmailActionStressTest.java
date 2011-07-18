/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.reg.actions.registration.ResendAccountActivationEmailAction;

/**
 * <p>
 * This class contains Stress tests for ResendAccountActivationEmailAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ResendAccountActivationEmailActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents ResendAccountActivationEmailAction instance for testing.
     * </p>
     */
    private ResendAccountActivationEmailAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("resendAccountActivationEmailAction");
        MockFactory.createUserInSession(action);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests ResendAccountActivationEmailAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
        }
        verify(action.getAuditDAO(), times(getRunCount())).audit(any(AuditRecord.class));
    }
}

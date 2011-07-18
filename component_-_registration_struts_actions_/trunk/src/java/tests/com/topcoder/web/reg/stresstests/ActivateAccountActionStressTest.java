/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.reg.actions.registration.ActivateAccountAction;

/**
 * <p>
 * This class contains Stress tests for ActivateAccountAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ActivateAccountActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents ActivateAccountAction instance for testing.
     * </p>
     */
    private ActivateAccountAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("activateAccountAction");
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
     * Tests ActivateAccountAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        String activationCode = "activationCode";
        action.setActivationCode(activationCode);
        when(action.getUserDAO().find(anyLong())).thenReturn(MockFactory.getUser());
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
        }
        verify(action.getAuditDAO(), times(getRunCount())).audit(any(AuditRecord.class));
    }
}

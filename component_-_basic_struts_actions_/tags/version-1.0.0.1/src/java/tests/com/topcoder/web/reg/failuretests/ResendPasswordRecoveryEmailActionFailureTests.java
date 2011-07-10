/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import org.easymock.EasyMock;

import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.reg.actions.basic.BasicActionException;
import com.topcoder.web.reg.actions.basic.ResendPasswordRecoveryEmailAction;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ResendPasswordRecoveryEmailAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ResendPasswordRecoveryEmailActionFailureTests extends TestCase {
    /**
     * <p>
     * The ResendPasswordRecoveryEmailAction instance for testing.
     * </p>
     */
    private ResendPasswordRecoveryEmailAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ResendPasswordRecoveryEmailAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ResendPasswordRecoveryEmailActionFailureTests.class);
    }

    /**
     * <p>
     * Tests ResendPasswordRecoveryEmailAction#execute() for failure.
     * Expects BasicActionException.
     * </p>
     */
    public void testExecute_BasicActionException() {
        AuditDAO auditDAO = EasyMock.createNiceMock(AuditDAO.class);
        instance.setAuditDAO(auditDAO);
        try {
            instance.execute();
            fail("BasicActionException expected.");
        } catch (BasicActionException e) {
            //good
        }
    }
}
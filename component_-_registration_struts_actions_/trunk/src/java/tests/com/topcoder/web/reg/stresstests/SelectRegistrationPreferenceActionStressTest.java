/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.reg.actions.registration.SelectRegistrationPreferenceAction;

/**
 * <p>
 * This class contains Stress tests for SelectRegistrationPreferenceAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class SelectRegistrationPreferenceActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents SelectRegistrationPreferenceAction instance for testing.
     * </p>
     */
    private SelectRegistrationPreferenceAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("selectRegistrationPreferenceAction");
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
     * Tests SelectRegistrationPreferenceAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        Set<RegistrationType> userRegistrationTypes = new HashSet<RegistrationType>();
        userRegistrationTypes.add(mock(RegistrationType.class));
        userRegistrationTypes.add(mock(RegistrationType.class));
        userRegistrationTypes.add(mock(RegistrationType.class));
        action.setUserRegistrationTypes(userRegistrationTypes);
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
        }
        verify(action.getAuditDAO(), times(getRunCount())).audit(any(AuditRecord.class));
    }
}

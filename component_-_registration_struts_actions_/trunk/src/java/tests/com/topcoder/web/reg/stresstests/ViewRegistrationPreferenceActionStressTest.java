/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.stresstests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.RegistrationType;
import com.topcoder.web.reg.actions.registration.ViewRegistrationPreferenceAction;

/**
 * <p>
 * This class contains Stress tests for ViewRegistrationPreferenceAction.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class ViewRegistrationPreferenceActionStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents ViewRegistrationPreferenceAction instance for testing.
     * </p>
     */
    private ViewRegistrationPreferenceAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("viewRegistrationPreferenceAction");
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
     * Tests ViewRegistrationPreferenceAction#execute() method with valid fields set.
     * </p>
     * <p>
     * Action should be executed successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute() throws Exception {
        List<RegistrationType> allRegistrationTypes = new ArrayList<RegistrationType>();
        allRegistrationTypes.add(mock(RegistrationType.class));
        allRegistrationTypes.add(mock(RegistrationType.class));
        allRegistrationTypes.add(mock(RegistrationType.class));
        when(action.getRegistrationTypeDAO().getRegistrationTypes()).thenReturn(allRegistrationTypes);
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Action should be executed successfully", ActionSupport.SUCCESS, action.execute());
            assertEquals("User registration types should be set successfully.", MockFactory.getUser()
                    .getRegistrationTypes().size(), action.getUserRegistrationTypes().size());
            assertEquals("Registration types should be set successfully.", allRegistrationTypes.size(), action
                    .getAllRegistrationTypes().size());
        }
        verify(action.getAuditDAO(), times(getRunCount())).audit(any(AuditRecord.class));
    }
}

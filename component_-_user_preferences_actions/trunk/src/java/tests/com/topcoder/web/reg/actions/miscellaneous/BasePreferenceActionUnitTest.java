/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains Unit tests for BasePreferenceAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasePreferenceActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BasePreferencesAction action for testing.
     * </p>
     */
    private BasePreferencesAction basePreferencesAction;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/base");
        basePreferencesAction = (BasePreferencesAction) proxy.getAction();
        proxy.execute();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        basePreferencesAction = null;
    }

    /**
     * <p>
     * Tests BasePreferencesAction#sendEmail() method with valid arguments and all characters that should be escaped.
     * </p>
     * <p>
     * Email should be sent successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSendEmail() throws Exception {
        Long id = 1L;
        UserDAO userDAO = basePreferencesAction.getUserDao();
        User user = createUser(id, "First", "Last", "tc_handle", true);
        when(userDAO.find(id)).thenReturn(user);
        basePreferencesAction.setEmailText("it has all characters that should be escaped: < > & ' \"");
        basePreferencesAction.sendEmail();
        // check received email manually
    }

    /**
     * <p>
     * Tests BasePreferencesAction#getLoggedInUser() method with valid arguments.
     * </p>
     * <p>
     * User should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetLoggedInUser() throws Exception {
        Long id = 1L;
        UserDAO userDAO = basePreferencesAction.getUserDao();
        User user = createUser(id, "First", "Last", "tc_handle", true);
        when(userDAO.find(id)).thenReturn(user);
        User actual = basePreferencesAction.getLoggedInUser();
        assertEquals("User should be retrieved successfully.", user, actual);
    }

    /**
     * <p>
     * Tests BasePreferencesAction#audit() method with valid arguments.
     * </p>
     * <p>
     * Audit should be done successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testAudit() throws Exception {
        Long id = 1L;
        UserDAO userDAO = basePreferencesAction.getUserDao();
        User user = createUser(id, "First", "Last", "tc_handle", true);
        when(userDAO.find(id)).thenReturn(user);
        AuditDAO auditDao = basePreferencesAction.getAuditDao();
        basePreferencesAction.audit("oldValud", "newValue", "operationType");
        verify(auditDao, times(1)).audit(any(AuditRecord.class));
    }
}

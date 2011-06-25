/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;

/**
 * <p>
 * This class contains Unit tests for ConfigurePrivacyPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurePrivacyPreferencesActionUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ConfigurePrivacyPreferencesAction action for testing.
     * </p>
     */
    private ConfigurePrivacyPreferencesAction action;

    /**
     * <p>
     * Represents ActionProxy instance for testing.
     * </p>
     */
    private ActionProxy proxy;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        proxy = getActionProxy("/privacy");
        action = (ConfigurePrivacyPreferencesAction) proxy.getAction();
        setBasePreferencesActionDAOs(action);
        action.setPreferenceGroupDao(getPreferenceGroupDao());
        // put valid class for basic authentication session key
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), createAuthentication());
        User user = createUser(1L, "First", "Last", "tc_handle", true);
        setUserPreferences(user, action);
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
        proxy = null;
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction constructor.
     * </p>
     * <p>
     * ConfigurePrivacyPreferencesAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ConfigurePrivacyPreferencesAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() method with valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare() throws Exception {
        action.prepare();
        List < UserPreference > actual = action.getPreferences();
        assertEquals("Preferences should be set successfully.", 2, actual.size());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() method with submit action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit() throws Exception {
        action.prepare();
        String newValue = "newValue";
        action.setAction(SUBMIT_ACTION);
        // change user preference value
        List < UserPreference > preferences = action.getPreferences();
        UserPreference userPreference1 = new UserPreference();
        userPreference1.setId(preferences.get(0).getId());
        userPreference1.setValue(newValue);
        preferences.set(0, userPreference1);
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one record should be audited
        verify(action.getAuditDao(), times(1)).audit(any(AuditRecord.class));
        // one user should be updated
        verify(action.getUserDao(), times(1)).saveOrUpdate(any(User.class));
        assertEquals("Preference should be updated.", newValue, action.getPreferences().get(0).getValue());
        assertNotNull("User should be backed up.",
                ActionContext.getContext().getSession().get(action.getBackupSessionKey()));
        assertEquals("Second time execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // no records should be audited
        verify(action.getAuditDao(), times(1)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() method with discard action and valid attributes.
     * </p>
     * <p>
     * Execute should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard() throws Exception {
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one user should be retrieved from backup session key
        verify(action.getUserDao(), times(1)).saveOrUpdate(user);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() method with discard action, valid attributes and email true
     * flag.
     * </p>
     * <p>
     * Execute should be ended successfully and email should be sent. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_EmailSend() throws Exception {
        // put backup session
        User user = new User();
        user.setId(2L);
        putKeyValueToSession(action.getBackupSessionKey(), user);
        action.setAction(DISCARD_ACTION);
        action.setEmailSendFlag(true);
        // change user preference value
        assertEquals("Execute should be ended successfully.", ActionSupport.SUCCESS, action.execute());
        // one user should be retrieved from backup session key
        verify(action.getUserDao(), times(1)).saveOrUpdate(user);
        // no records should be audited
        verify(action.getAuditDao(), times(0)).audit(any(AuditRecord.class));
        // check email sent manually
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#validate() method valid input argument passed.
     * </p>
     * <p>
     * Validate should be ended successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate() throws Exception {
        action.prepare();
        // provide validation
        action.validate();
    }
}

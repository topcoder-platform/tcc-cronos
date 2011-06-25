/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.dao.PreferenceGroupDAO;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;

/**
 * <p>
 * This class contains Unit tests for ConfigurePrivacyPreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurePrivacyPreferencesActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ConfigurePrivacyPreferencesAction action for testing.
     * </p>
     */
    private ConfigurePrivacyPreferencesAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = getBean("configurePrivacyPreferencesAction");
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
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#getGroup() method.
     * </p>
     * <p>
     * group should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetGroup() {
        Integer group = 1;
        action.setGroup(group);
        assertSame("getGroup() doesn't work properly.", group, action.getGroup());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#setGroup(Integer) method.
     * </p>
     * <p>
     * group should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetGroup() {
        Integer group = 2;
        action.setGroup(group);
        assertSame("setGroup() doesn't work properly.", group, action.getGroup());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#getPreferenceGroupDao() method.
     * </p>
     * <p>
     * preferenceGroupDao should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPreferenceGroupDao() {
        PreferenceGroupDAO preferenceGroupDao = getPreferenceGroupDao();
        action.setPreferenceGroupDao(preferenceGroupDao);
        assertSame("getPreferenceGroupDao() doesn't work properly.", preferenceGroupDao,
                action.getPreferenceGroupDao());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#setPreferenceGroupDao(PreferenceGroupDAO) method.
     * </p>
     * <p>
     * preferenceGroupDao should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPreferenceGroupDao() {
        PreferenceGroupDAO preferenceGroupDao = getPreferenceGroupDao();
        action.setPreferenceGroupDao(preferenceGroupDao);
        assertSame("setPreferenceGroupDao() doesn't work properly.", preferenceGroupDao,
                action.getPreferenceGroupDao());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#getPreferences() method.
     * </p>
     * <p>
     * preferences should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetPreferences() {
        List < UserPreference > preferences = new ArrayList < UserPreference >();
        action.setPreferences(preferences);
        assertSame("getPreferences() doesn't work properly.", preferences, action.getPreferences());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#setPreferences(List) method.
     * </p>
     * <p>
     * preferences should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetPreferences() {
        List < UserPreference > preferences = new ArrayList < UserPreference >();
        action.setPreferences(preferences);
        assertSame("setPreferences() doesn't work properly.", preferences, action.getPreferences());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#prepare() method with null group attribute.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_Group() throws Exception {
        action.setGroup(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#prepare() method with negative group attribute.
     * </p>
     * <p>
     * UserPreferencesActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Negative_Group() throws Exception {
        action.setGroup(-1);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#prepare() method with null preferenceGroupDao attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_PreferenceGroupDao() throws Exception {
        action.setPreferenceGroupDao(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#validate() method with null preference value.
     * </p>
     * <p>
     * Fields error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Null_PreferenceValue() throws Exception {
        action.prepare();
        List < UserPreference > preferences = action.getPreferences();
        preferences.get(0).setValue(null);
        preferences.get(1).setValue(null);
        action.validate();
        assertEquals("Fields error should be added.", 2, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#validate() method with null preference value.
     * </p>
     * <p>
     * Field error should be added.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testValidate_Empty_PreferenceValue() throws Exception {
        action.prepare();
        List < UserPreference > preferences = action.getPreferences();
        preferences.get(0).setValue("");
        action.validate();
        assertEquals("Fields error should be added.", 1, action.getFieldErrors().size());
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() with logged in user that has invalid state.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_UserISE() throws Exception {
        action.prepare();
        // make user invalid state
        User user = action.getLoggedInUser();
        user.setUserProfile(null);
        when(action.getUserDao().find(anyLong())).thenReturn(user);
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() with no user in session.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_NoUser() throws Exception {
        putKeyValueToSession(action.getBasicAuthenticationSessionKey(), null);
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() with email send fail.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Submit_EmailFail() throws Exception {
        action.prepare();
        action.setEmailSendFlag(true);
        action.setEmailBodyTemplateFileName("Unknown");
        action.setAction(SUBMIT_ACTION);
        try {
            action.execute();
            fail("UserPreferencesActionExecutionException exception is expected.");
        } catch (UserPreferencesActionExecutionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests ConfigurePrivacyPreferencesAction#execute() with no backed up user in session.
     * </p>
     * <p>
     * PreferencesActionDiscardException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testExecute_Discard_NoUser() throws Exception {
        action.setAction(DISCARD_ACTION);
        try {
            action.execute();
            fail("PreferencesActionDiscardException exception is expected.");
        } catch (PreferencesActionDiscardException e) {
            // expected
        }
    }
}

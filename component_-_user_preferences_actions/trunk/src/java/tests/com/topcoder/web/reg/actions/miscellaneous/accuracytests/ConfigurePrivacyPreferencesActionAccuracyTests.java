/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestSuite;
import junit.framework.Test;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.dao.PreferenceGroupDAO;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.reg.actions.miscellaneous.ConfigurePrivacyPreferencesAction;

/**
 * <p>
 * Accuracy Unit test cases for ConfigurePrivacyPreferencesAction.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ConfigurePrivacyPreferencesActionAccuracyTests extends BaseAccuracyTest {
    /**
     * <p>ConfigurePrivacyPreferencesAction instance for testing.</p>
     */
    private ConfigurePrivacyPreferencesAction instance;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        super.setUp();
        ActionProxy proxy = getActionProxy("/privacy");
        instance = (ConfigurePrivacyPreferencesAction) proxy.getAction();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ConfigurePrivacyPreferencesActionAccuracyTests.class);
    }

    /**
     * <p>Tests ctor ConfigurePrivacyPreferencesAction#ConfigurePrivacyPreferencesAction() for accuracy.</p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ConfigurePrivacyPreferencesAction instance.", instance);
    }

    /**
     * <p>Tests ConfigurePrivacyPreferencesAction#setPreferenceGroupDao(PreferenceGroupDAO) for accuracy.</p>
     */
    public void testSetPreferenceGroupDao() {
        PreferenceGroupDAO preferenceGroupDao = getPreferenceGroupDao();
        instance.setPreferenceGroupDao(preferenceGroupDao);
        assertSame("Failed to setPreferenceGroupDao correctly.", preferenceGroupDao, instance.getPreferenceGroupDao());
    }

    /**
     * <p>Tests ConfigurePrivacyPreferencesAction#setPreferences(List) for accuracy.</p>
     */
    public void testSetPreferences() {
        List<UserPreference> preferences = new ArrayList<UserPreference>();
        instance.setPreferences(preferences);
        assertEquals("Failed to setPreferences correctly.", 0, instance.getPreferences().size());
    }

    /**
     * <p>Tests ConfigurePrivacyPreferencesAction#execute() for accuracy.</p>
     * @throws Exception to JUnit
     */
    public void testExecute() throws Exception {
        setBasePreferencesActionDAOs(instance);
        instance.setPreferenceGroupDao(getPreferenceGroupDao());
        putKeyValueToSession(instance.getBasicAuthenticationSessionKey(), createAuthentication());
        User loggedInUser = createUser(1L, "First", "Second", "handle");
        when(instance.getUserDao().find(1L)).thenReturn(loggedInUser);
        List<UserPreference> preferences = new ArrayList<UserPreference>();
        instance.setPreferences(preferences);
        instance.setAction(SUBMIT_ACTION);

        assertEquals("Failed to execute correctly.", ActionSupport.SUCCESS, instance.execute());
        verify(instance.getAuditDao(), times(0)).audit(any(AuditRecord.class));
        verify(instance.getUserDao(), times(1)).saveOrUpdate(any(User.class));
    }

    /**
     * <p>Tests ConfigurePrivacyPreferencesAction#setGroup(Integer) for accuracy.</p>
     */
    public void testSetGroup() {
        instance.setGroup(new Integer(1));
        assertEquals("Failed to setGroup correctly.", 1, instance.getGroup().intValue());
    }

}
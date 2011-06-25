/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.stresstests;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.web.common.model.PreferenceValue;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserPreference;
import com.topcoder.web.common.model.UserPreference.Identifier;
import com.topcoder.web.reg.actions.miscellaneous.ConfigurePrivacyPreferencesAction;

/**
 * <p>
 * Stress test cases for ConfigurePrivacyPreferencesAction.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class ConfigurePrivacyPreferencesActionStressTests extends BaseTest {
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
        for (int i = 0; i < 1000; i++) {
            UserPreference up = new UserPreference();
            up.setId(new Identifier());
            up.getId().setUser(loggedInUser);
            up.setNew(true);
            up.setPreferenceValue(new PreferenceValue());
            up.getPreferenceValue().setId(i);
            up.setValue("test");
        }
        instance.setPreferences(preferences);
        instance.setAction(SUBMIT_ACTION);

        long start = System.currentTimeMillis();
        assertEquals("Wrong.", ActionSupport.SUCCESS, instance.execute());
        long end = System.currentTimeMillis();
        System.err.println("Run execute took " + (end - start) + "ms");
    }

}
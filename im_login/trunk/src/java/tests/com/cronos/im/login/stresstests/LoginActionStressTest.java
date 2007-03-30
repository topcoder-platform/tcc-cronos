/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import java.io.File;
import java.util.Iterator;

import javax.naming.NamingException;

import junit.framework.TestCase;

import com.topcoder.chat.user.profile.ChatUserProfileManager;
import com.topcoder.security.authorization.AuthorizationManager;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * Test case for LoginAction.
 *
 * @author radium
 * @version 1.0
 */
public class LoginActionStressTest extends TestCase {
    /**
     * The excute time.
     */
    private int time = 100;

    /**
     * Mock LoginAction for testing.
     *
     * @author radium
     * @version 1.0
     */
    private class MockLoginAction extends LoginAction {
        /**
         * Get the log.
         */
        public Log getLog() {
            return super.getLog();
        }
        /**
         * Get the userProfileKey.
         */
        public String getUserProfileKey() {
            return super.getUserProfileKey();
        }
        /**
         * Get the loginSuceedForwardName.
         */
        public String getLoginSucceedForwardName() {
            return super.getLoginSucceedForwardName();
        }
        /**
         * Get the loginFailForwardName.
         */
        public String getLoginFailForwardName() {
            return super.getLoginFailForwardName();
        }
        /**
         * Do the log.
         */
        public void doLog(String user, String action, String entityId, String message, Level level) {
            super.doLog(user, action, entityId, message, level);
        }
        /**
         * Get cateGoryKey.
         */
        public String getCategoryKey() {
            return super.getCategoryKey();
        }
        /**
         * Get roleKey.
         */
        public String getRoleKey() {
            return super.getRoleKey();
        }
        /**
         * Get authProvideURL.
         */
        public String getAuthProviderURL() {
            return super.getAuthProviderURL();
        }
        /**
         * Get authInitialContextFactory.
         */
        public String getAuthInitialContextFactory() {
            return super.getAuthInitialContextFactory();
        }
        /**
         * Get LoginRemote.
         */
        public LoginRemote getLoginRemote() throws NamingException {
            return super.getLoginRemote();
        }
        /**
         * Get charUserProfileManager.
         */
        public ChatUserProfileManager getChatUserProfileManager() throws InvalidClassSpecificationException,
            SpecificationConfigurationException, IllegalReferenceException {
            return super.getChatUserProfileManager();
        }
        /**
         * Get authorizationManager.
         */
        public AuthorizationManager getAuthorizationManager()
            throws com.topcoder.security.authorization.ConfigurationException {
            return super.getAuthorizationManager();
        }
    }

    /**
     * The ConfigManager for testing.
     */
    private ConfigManager cm;

    /**
     * MockLoginAction for testing.
     */
    private MockLoginAction mla;

    /**
     * Set up the environment for testing.
     */
    protected void setUp() throws Exception {
        cm = ConfigManager.getInstance();
        File file = new File("test_files" + File.separator + "stress_config.xml");
        cm.add(file.getCanonicalPath());
        mla = new MockLoginAction();
    }

    /**
     * TearDown the environment for testing.
     *
     * @throws Exception
     */
    protected void tearDown() throws Exception {
        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * Test method for getLog().
     */
    public void testGetLog() {
        for (int i = 0; i < time; i++) {
            assertNotNull("Should not be null.", mla.getLog());
        }
    }

    /**
     * Test method for getUserProfileKey().
     */
    public void testGetUserProfileKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "userProfileConfig", mla.getUserProfileKey());
        }
    }

    /**
     * Test method for getLoginSucceedForwardName().
     */
    public void testGetLoginSucceedForwardName() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "loginSucceedConfig", mla.getLoginSucceedForwardName());
        }
    }

    /**
     * Test method for getLoginFailForwardName().
     */
    public void testGetLoginFailForwardName() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "loginFailConfig", mla.getLoginFailForwardName());
        }
    }

    /**
     * Test method for doLog.
     */
    public void testDoLog() {
        for (int i = 0; i < time; i++) {
            mla.doLog("user", "action", "entityId", "message", Level.INFO);
        }
    }

    /**
     * Test method for getCategoryKey().
     */
    public void testGetCategoryKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "categoryConfig", mla.getCategoryKey());
        }
    }

    /**
     * Test method for getRoleKey().
     */
    public void testGetRoleKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "roleConfig", mla.getRoleKey());
        }
    }

    /**
     * Test method for getAuthProviderURL().
     */
    public void testGetAuthProviderURL() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "http://locahost:1099", mla.getAuthProviderURL());
        }
    }

    /**
     * Test method for getAuthInitialContextFactory().
     */
    public void testGetAuthInitialContextFactory() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal", "org.jnp.interfaces.NamingContextFactory", mla
                .getAuthInitialContextFactory());
        }
    }
}

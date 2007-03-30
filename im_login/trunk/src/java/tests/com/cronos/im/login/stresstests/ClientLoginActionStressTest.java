/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.stresstests;

import java.io.File;
import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.im.login.ClientLoginAction;
import com.topcoder.util.config.ConfigManager;

/**
 * Stress test case for ClientLoginAction.
 *
 * @author radium
 * @version 1.0
 */
public class ClientLoginActionStressTest extends TestCase {
    /**
     * The excute time.
     */
    private int time = 100;

    /**
     * Mock class for testing.
     *
     * @author radium
     * @version 1.0
     */
    private class MockClientLoginAction extends ClientLoginAction {
        public boolean checkChattable(String str) {
            return false;
        }

        /**
         * Get the unchattableforwardName.
         */
        public String getUnchattableForwardName() {
            return super.getUnchattableForwardName();
        }

        /**
         * Get the connectionName.
         */
        public String getConnectionName() {
            return super.getConnectionName();
        }

        /**
         * Get the familyName.
         */
        public String getFamilyNameKey() {
            return super.getFamilyNameKey();
        }

        /**
         * Get the lastName.
         */
        public String getLastNameKey() {
            return super.getLastNameKey();
        }

        /**
         * Get the title.
         */
        public String getTitleKey() {
            return super.getTitleKey();
        }

        /**
         * Get the email.
         */
        public String getEmailKey() {
            return super.getEmailKey();
        }

        /**
         * Get the company.
         */
        public String getCompanyKey() {
            return super.getCompanyKey();
        }
    }

    /**
     * Sample ConfigManager for testing.
     */
    private ConfigManager cm;

    /**
     * Sample MockClientLoginAction for testing.
     */
    private MockClientLoginAction mcl;

    /**
     * Set up the environment for testing.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void setUp() throws Exception {
        cm = ConfigManager.getInstance();
        File file = new File("test_files" + File.separator + "stress_config.xml");
        cm.add(file.getCanonicalPath());
        mcl = new MockClientLoginAction();
    }

    /**
     * Tear down the environment for testing.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * Test method for getUnchattableForwardName().
     */
    public void testGetUnchattableForwardName() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "unchattableConfig", mcl.getUnchattableForwardName());
        }
    }



    /**
     * Test method for getLastNameKey().
     */
    public void testGetLastNameKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "lastNameConfig", mcl.getLastNameKey());
        }
    }

    /**
     * Test method for getTitleKey().
     */
    public void testGetTitleKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "titleConfig", mcl.getTitleKey());
        }
    }

    /**
     * Test method for getEmailKey().
     */
    public void testGetEmailKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "emailConfig", mcl.getEmailKey());
        }
    }

    /**
     * Test method for getCompanyKey().
     */
    public void testGetCompanyKey() {
        for (int i = 0; i < time; i++) {
            assertEquals("Should be equal.", "companyConfig", mcl.getCompanyKey());
        }
    }

}

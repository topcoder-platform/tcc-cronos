/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.DbUserAuthenticator;
import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.security.authenticationfactory.InvalidPrincipalException;
import com.topcoder.security.authenticationfactory.MissingPrincipalKeyException;
import com.topcoder.security.authenticationfactory.Principal;

/**
 * <p>
 * Failure unit test cases for the DbUserAuthenticator class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class DbUserAuthenticatorFailureTests extends TestCase {
    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String CONFIG_XML = "failuretests" + File.separatorChar + "auth.xml";

    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String DB_CONFIG_XML = "failuretests" + File.separatorChar + "dbconfig.xml";

    /**
     * <p>
     * DbUserAuthenticator instance used for testing
     * </p>
     */
    private DbUserAuthenticator auth = null;

    /**
     * <p>
     * Creates DbCompanyDAO instance.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);
        FailureTestsHelper.addConfig(DB_CONFIG_XML);
        auth = new DbUserAuthenticator("auth");
    }

    /**
     * <p>
     * Removes the configuration if it exists.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestsHelper.cleanConfig();
    }

    /**
     * <p>
     * Tests DbUserAuthenticator(String) for failure. Passes empty string, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1() throws Exception {
        try {
            new DbUserAuthenticator(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserAuthenticator(String) for failure. Passes incorrect config file, ConfigurationException is
     * expected.
     * </p>
     */
    public void testConstructor2() {
        try {
            new DbUserAuthenticator("auth.missed_db");
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserAuthenticator(String) for failure. Passes incorrect config file, ConfigurationException is
     * expected.
     * </p>
     */
    public void testConstructor3() {
        try {
            new DbUserAuthenticator("auth.missed_encryption");
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserAuthenticator(String) for failure. Passes incorrect config file, ConfigurationException is
     * expected.
     * </p>
     */
    public void testConstructor4() {
        try {
            new DbUserAuthenticator("auth.missed_classname");
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // expected
        }
    }
    
    /**
     * <p>
     * Tests doAuthenticate(Principal) for failure. Passes principial without a key,
     * MissingPrincipalKeyException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDoAuthenticate1() throws Exception {
        Principal p = new Principal(new Long(1));
        try {
            auth.authenticate(p);
            fail("MissingPrincipalKeyException should be thrown");
        } catch (MissingPrincipalKeyException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests doAuthenticate(Principal) for failure. Passes principial without a key,
     * MissingPrincipalKeyException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDoAuthenticate2() throws Exception {
        Principal p = new Principal(new Long(1));
        p.addMapping(DbUserAuthenticator.PASSWORD_KEY, "asas");
        try {
            auth.authenticate(p);
            fail("MissingPrincipalKeyException should be thrown");
        } catch (MissingPrincipalKeyException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests doAuthenticate(Principal) for failure. Passes incorrect principial, InvalidPrincipalException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDoAuthenticate3() throws Exception {
        Principal p = new Principal(new Long(1));
        p.addMapping(DbUserAuthenticator.USERNAME_KEY, Boolean.TRUE);
        p.addMapping(DbUserAuthenticator.PASSWORD_KEY, "asas");
        try {
            auth.authenticate(p);
            fail("InvalidPrincipalException should be thrown");
        } catch (InvalidPrincipalException e) {
            // expected
        }
    }
}

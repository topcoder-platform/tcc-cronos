/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.io.File;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.security.authenticationfactory.MissingPrincipalKeyException;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for UserAuthenticator.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserAuthenticatorTests extends TestCase {
    /**
     * <p>
     * Represents the default namespace for the UserAuthenticator.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.user.UserAuthenticator";

    /**
     * <p>
     * The UserAuthenticator instance for testing.
     * </p>
     */
    private UserAuthenticator user;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE_3_2_1);
        TestHelper.loadXMLConfig("test_files" + File.separator + "authenticator_invalid_config.xml");
        TestHelper.setUpDataBase();
        user = new UserAuthenticator(NAMESPACE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
        user = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserAuthenticatorTests.class);
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UserAuthenticator instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new UserAuthenticator instance.", user);
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_NullNamespace() throws ConfigurationException {
        try {
            new UserAuthenticator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws ConfigurationException
     *             to JUnit
     */
    public void testCtor_EmptyNamespace() throws ConfigurationException {
        try {
            new UserAuthenticator(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UnknownNamespace() {
        try {
            new UserAuthenticator("unknownNamespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the objectFactoryNamespace property is missing and expects for
     * ConfigurationException.
     * </p>
     */
    public void testCtor_ObjectFactoryNamespaceMissing() {
        try {
            new UserAuthenticator("objectFactoryNamespace_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the objectFactoryNamespace property is empty and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_ObjectFactoryNamespaceEmpty() {
        try {
            new UserAuthenticator("objectFactoryNamespace_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the objectFactoryNamespace property is invalid and expects for
     * ConfigurationException.
     * </p>
     */
    public void testCtor_ObjectFactoryNamespaceInvalid() {
        try {
            new UserAuthenticator("objectFactoryNamespace_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the userDao.classname property is missing and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UserDaoClassnameMissing() {
        try {
            new UserAuthenticator("userDao_classname_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the userDao.classname property is empty and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UserDaoClassnameEmpty() {
        try {
            new UserAuthenticator("userDao_classname_empty");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the userDao.classname property is invalid and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_UserDaoClassnameInvalid() {
        try {
            new UserAuthenticator("userDao_classname_invalid");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the userDao.classname property is not UserDAO type and expects for
     * ConfigurationException.
     * </p>
     */
    public void testCtor_UserDaoClassnameWrongType() {
        try {
            new UserAuthenticator("userDao_classname_wrong_type");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor UserAuthenticator#UserAuthenticator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is missing and expects ConfigurationException.
     * </p>
     */
    public void testCtor_MissingNamespace() {
        try {
            new UserAuthenticator("unknown_namespace");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserAuthenticator#doAuthenticate(Principal) is correct.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", "admin");
        principal.addMapping("password", "tc_super");

        Response response = user.doAuthenticate(principal);
        assertTrue("Failed to do authenticate correctly.", response.isSuccessful());
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserAuthenticator#doAuthenticate(Principal) is correct.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_WrongPassword() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", "admin");
        principal.addMapping("password", "wrong_password");

        Response response = user.doAuthenticate(principal);
        assertFalse("Failed to do authenticate correctly.", response.isSuccessful());
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for accuracy.
     * </p>
     *
     * <p>
     * It verifies UserAuthenticator#doAuthenticate(Principal) is correct.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_UnknownUser() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", "who_is_it");
        principal.addMapping("password", "password");

        Response response = user.doAuthenticate(principal);
        assertFalse("Failed to do authenticate correctly.", response.isSuccessful());
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when principal is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_UserNameKeyMissing() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("password", "tc_super");

        try {
            user.doAuthenticate(principal);
            fail("MissingPrincipalKeyException expected.");
        } catch (MissingPrincipalKeyException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when principal is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_UserNameNonString() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", new Object());
        principal.addMapping("password", "tc_super");

        try {
            user.doAuthenticate(principal);
            fail("AuthenticateException expected.");
        } catch (AuthenticateException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when principal is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_PasswordKeyMissing() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", "admin");

        try {
            user.doAuthenticate(principal);
            fail("MissingPrincipalKeyException expected.");
        } catch (MissingPrincipalKeyException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests UserAuthenticator#doAuthenticate(Principal) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when principal is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws AuthenticateException
     *             to JUnit
     */
    public void testDoAuthenticate_PasswordNonString() throws AuthenticateException {
        Principal principal = new Principal("tc");
        principal.addMapping("username", "admin");
        principal.addMapping("password", new Object());

        try {
            user.doAuthenticate(principal);
            fail("AuthenticateException expected.");
        } catch (AuthenticateException e) {
            // good
        }
    }
}
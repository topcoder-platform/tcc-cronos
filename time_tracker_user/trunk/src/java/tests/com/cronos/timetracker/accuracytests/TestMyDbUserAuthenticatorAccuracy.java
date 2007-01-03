/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.common.EncryptionRepository;
import com.cronos.timetracker.user.DbUserAuthenticator;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Accuracy test for class <code>DbUserAuthenticator</code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestMyDbUserAuthenticatorAccuracy extends TestCase {

    /**
     * Represents the DbUserAuthenticator instance for test.
     */
    private MyDbUserAuthenticator test = null;

    /**
     * Set up the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        Util.clearNamespace();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add("accuracytests/config.xml");
        cm.add("accuracytests/DBConnectionFactory.xml");

        String namespace = "com.cronos.timetracker.user.DbUserAuthenticator";

        Util.insertRecordsIntoCompanyTable(1);

        EncryptionRepository.getInstance().registerAlgorithm("simple", new SimpleAlgorithm());

        test = new MyDbUserAuthenticator(namespace);
    }

    /**
     * Tear down the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        Util.clearTables();

        Util.clearNamespace();
    }

    /**
     * Test constructor.
     *
     */
    public void testDbUserAuthenticator() {
        assertNotNull("Should be created.", test);
    }

    /**
     * Test for Response doAuthenticate(Principal).
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoAuthenticatePrincipal_1() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "psw");

        Response response = test.doAuthenticate(principal);

        // no password would be retrieved from the database. so false returned.
        assertFalse("False is expected.", response.isSuccessful());
    }

    /**
     * Test for Response doAuthenticate(Principal).
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoAuthenticatePrincipal_2() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "psw");

        Util.insertIntoTable_User_Account(1, 1, "user", "psw");

        Response response = test.doAuthenticate(principal);

        // match the password.
        assertTrue("True is expected.", response.isSuccessful());
    }

    /**
     * Test for Response doAuthenticate(Principal).
     *
     * @throws Exception
     *             to junit.
     */
    public void testDoAuthenticatePrincipal_3() throws Exception {
        Principal principal = new Principal("1");
        principal.addMapping(DbUserAuthenticator.USERNAME_KEY, "user");
        principal.addMapping(DbUserAuthenticator.PASSWORD_KEY, "ppp");

        Util.insertIntoTable_User_Account(1, 1, "user", "psw");

        Response response = test.doAuthenticate(principal);

        // the password is not equal to ppp;
        assertFalse("False is expected.", response.isSuccessful());
    }
}

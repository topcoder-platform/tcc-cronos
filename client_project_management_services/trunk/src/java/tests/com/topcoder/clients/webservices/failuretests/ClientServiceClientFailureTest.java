/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.webservices.ClientService;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClient;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClientCreationException;

import junit.framework.TestCase;

import java.net.URL;

import javax.xml.namespace.QName;


/**
 * This is a test case for <code>ClientServiceClient</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ClientServiceClientFailureTest extends TestCase {
    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments1()
        throws Throwable {
        try {
            new ClientServiceClient((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments2()
        throws Throwable {
        try {
            new ClientServiceClient(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects ClientServiceClientCreationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments3()
        throws Throwable {
        try {
            new ClientServiceClient("invalid");
            fail("ClientServiceClientCreationException should be thrown.");
        } catch (ClientServiceClientCreationException ex) {
            // success
        }
    }

    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments4()
        throws Throwable {
        try {
            new ClientServiceClient((URL) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments5()
        throws Throwable {
        try {
            new ClientServiceClient((URL) null, new QName(ClientService.class.getName(), "ClientService"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ClientServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testClientServiceClient_InvalidArguments6()
        throws Throwable {
        try {
            new ClientServiceClient(new URL("http://www.topcoder.com"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}

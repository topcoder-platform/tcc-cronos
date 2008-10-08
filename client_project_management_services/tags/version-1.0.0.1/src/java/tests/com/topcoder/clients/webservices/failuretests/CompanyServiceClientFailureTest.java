/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.webservices.CompanyService;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClient;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClientCreationException;

import junit.framework.TestCase;

import java.net.URL;

import javax.xml.namespace.QName;


/**
 * This is a test case for <code>CompanyServiceClient</code>.
 *
 * @author PE
 * @version 1.0
 */
public class CompanyServiceClientFailureTest extends TestCase {
    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments1()
        throws Throwable {
        try {
            new CompanyServiceClient((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments2()
        throws Throwable {
        try {
            new CompanyServiceClient(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects CompanyServiceClientCreationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments3()
        throws Throwable {
        try {
            new CompanyServiceClient("invalid");
            fail("CompanyServiceClientCreationException should be thrown.");
        } catch (CompanyServiceClientCreationException ex) {
            // success
        }
    }

    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments4()
        throws Throwable {
        try {
            new CompanyServiceClient((URL) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments5()
        throws Throwable {
        try {
            new CompanyServiceClient((URL) null, new QName(CompanyService.class.getName(), "CompanyService"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>CompanyServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testCompanyServiceClient_InvalidArguments6()
        throws Throwable {
        try {
            new CompanyServiceClient(new URL("http://www.topcoder.com"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}

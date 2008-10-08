/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import com.topcoder.clients.webservices.ProjectService;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClient;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClientCreationException;

import junit.framework.TestCase;

import java.net.URL;

import javax.xml.namespace.QName;


/**
 * This is a test case for <code>ProjectServiceClient</code>.
 *
 * @author PE
 * @version 1.0
 */
public class ProjectServiceClientFailureTest extends TestCase {
    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments1()
        throws Throwable {
        try {
            new ProjectServiceClient((String) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments2()
        throws Throwable {
        try {
            new ProjectServiceClient(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects ProjectServiceClientCreationException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments3()
        throws Throwable {
        try {
            new ProjectServiceClient("invalid");
            fail("ProjectServiceClientCreationException should be thrown.");
        } catch (ProjectServiceClientCreationException ex) {
            // success
        }
    }

    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments4()
        throws Throwable {
        try {
            new ProjectServiceClient((URL) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments5()
        throws Throwable {
        try {
            new ProjectServiceClient((URL) null, new QName(ProjectService.class.getName(), "ProjectService"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Test for <code>ProjectServiceClient</code> constructor.
     * 
     * <p>
     * Tests it against the invalid arguments, expects IllegalArgumentException.
     * </p>
     *
     * @throws Throwable to JUnit
     */
    public void testProjectServiceClient_InvalidArguments6()
        throws Throwable {
        try {
            new ProjectServiceClient(new URL("http://www.topcoder.com"), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }
}

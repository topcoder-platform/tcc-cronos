/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.webserviceclients;

import junit.framework.TestCase;

/**
 * Unit test for {@link ClientUtils}.
 *
 * @author  CaDenza
 * @version 1.0
 */
public class ClientUtilsTest extends TestCase {

    /**
     * Test for {@link ClientUtils#createURL(String, Class)}.
     */
    public void testCreateURL() {
        String urlLoc = "http://topcoder.com";
        assertNotNull(ClientUtils.createURL(urlLoc, RuntimeException.class));
    }

    /**
     * Test for {@link ClientUtils#createURL(String, Class)}.
     *
     * Caused by: Invalid given url location.
     * Expected : {@link ServiceClientCreationException}.
     */
    public void testCreateURL_WithNotDefinedClazz() {
        String urlLoc = "error http:////topcoder.com";

        try {
            ClientUtils.createURL(urlLoc, ServiceClientCreationException.class);
            fail("ServiceClientCreationException is expected to be thrown.");
        } catch (ServiceClientCreationException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientUtils#createURL(String, Class)}.
     *
     * Caused by: GIven url is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testCreateURL_WithNullURLLocation() {
        try {
            ClientUtils.createURL(null, ClientServiceClientCreationException.class);
            fail("IllegalArgumentException is expected to be thrown.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientUtils#createURL(String, Class)}.
     *
     * Caused by: GIven url is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testCreateURL_WithEmptyURLLocation() {
        try {
            ClientUtils.createURL("\t \n", ClientServiceClientCreationException.class);
            fail("IllegalArgumentException is expected to be thrown.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test for {@link ClientUtils#checkNull(String, Object)}.
     */
    public void testCheckNull() {
        Object obj = "passing argument";
        assertEquals("Fail check null.", obj, ClientUtils.checkNull("argument-name", obj));
    }

    /**
     * Test for {@link ClientUtils#checkNull(String, Object)}.
     *
     * Caused by: Null passing object.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testCheckNull_WithNullObject() {
        try {
            ClientUtils.checkNull("argument-name", null);
            fail("IllegalArgumentException is expected to be thrown.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }
}

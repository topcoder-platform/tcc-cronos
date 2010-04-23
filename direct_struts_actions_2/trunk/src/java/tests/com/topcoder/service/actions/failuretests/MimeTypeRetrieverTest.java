/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.actions.MimeTypeRetriever;

/**
 * Failure tests for <code>MimeTypeRetriever</code>.
 * @author moon.river
 * @version 1.0
 */
public class MimeTypeRetrieverTest extends TestCase {

    /**
     * Instance to test.
     */
    private MimeTypeRetriever instance;

    /**
     * Sets up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new MimeTypeRetriever();
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeFromFileName_Null() {
        try {
            instance.getMimeTypeFromFileName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeFromFileName_Emtpy() {
        try {
            instance.getMimeTypeFromFileName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeIdFromFileName_Null() {
        try {
            instance.getMimeTypeIdFromFileName(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeIdFromFileName_Emtpy() {
        try {
            instance.getMimeTypeIdFromFileName(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeIdFromMimeType_Null() {
        try {
            instance.getMimeTypeIdFromMimeType(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeIdFromMimeType_Emtpy() {
        try {
            instance.getMimeTypeIdFromMimeType(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test method for {@link com.topcoder.service.actions.MimeTypeRetriever
     * #getMimeTypeFromFileName(java.lang.String)}.
     */
    public void testGetMimeTypeFromMimeType_Negative() {
        try {
            instance.getMimeTypeFromMimeTypeId(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}

/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.accuracytests;

import junit.framework.TestCase;

import com.topcoder.service.actions.MimeTypeRetriever;


/**
 * Accuracy tests for MimeTypeRetriever.
 *
 * @author onsky
 * @version 1.0
 */
public class MimeTypeRetrieverTests extends TestCase {
    /**
     * <p>Represents MimeTypeRetriever instance for testing.</p>
     */
    private MimeTypeRetriever instance;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new MimeTypeRetriever();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for getMimeTypeFromFileName.
     */
    public void test_getMimeTypeFromFileName1() {
        assertEquals("Incorrect value after set a new one", "application/zip",
            instance.getMimeTypeFromFileName("test.zip"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName.
     */
    public void test_getMimeTypeFromFileName2() {
        assertEquals("Incorrect value after set a new one", "text/plain",
            instance.getMimeTypeFromFileName("test.txt"));
    }

    /**
     * Accuracy test for getMimeTypeFromFileName.
     */
    public void test_getMimeTypeFromFileName3() {
        assertEquals("Incorrect value after set a new one", "application/octet-stream",
            instance.getMimeTypeFromFileName("test.unknown"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName.
     */
    public void test_getMimeTypeIdFromFileName1() {
        assertEquals("Incorrect value after set a new one", 12,
            instance.getMimeTypeIdFromFileName("test.zip"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName.
     */
    public void test_getMimeTypeIdFromFileName2() {
        assertEquals("Incorrect value after set a new one", 3,
            instance.getMimeTypeIdFromFileName("test.txt"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromFileName.
     */
    public void test_getMimeTypeIdFromFileName3() {
        assertEquals("Incorrect value after set a new one", -1,
            instance.getMimeTypeIdFromFileName("test.unknown"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType.
     */
    public void test_getMimeTypeIdFromMimeType1() {
        assertEquals("Incorrect value after set a new one", 12,
            instance.getMimeTypeIdFromMimeType("application/zip"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType.
     */
    public void test_getMimeTypeIdFromMimeType2() {
        assertEquals("Incorrect value after set a new one", 3,
            instance.getMimeTypeIdFromMimeType("text/plain"));
    }

    /**
     * Accuracy test for getMimeTypeIdFromMimeType.
     */
    public void test_getMimeTypeIdFromMimeType3() {
        assertEquals("Incorrect value after set a new one", -1,
            instance.getMimeTypeIdFromMimeType("application/octet-stream"));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId.
     */
    public void test_getMimeTypeFromMimeTypeId1() {
        assertEquals("Incorrect value after set a new one", "application/zip",
            instance.getMimeTypeFromMimeTypeId(12));
    }

    /**
     * Accuracy test for getMimeTypeFromMimeTypeId.
     */
    public void test_getMimeTypeFromMimeTypeId2() {
        assertEquals("Incorrect value after set a new one", "text/plain",
            instance.getMimeTypeFromMimeTypeId(3));
    }
}

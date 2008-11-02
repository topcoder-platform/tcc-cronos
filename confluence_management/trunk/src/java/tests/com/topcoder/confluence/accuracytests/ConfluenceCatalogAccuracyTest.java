/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.accuracytests;

import com.topcoder.confluence.entities.ConfluenceCatalog;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for the <code>ConfluenceCatalog</code> enum.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceCatalogAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the method <code>getStringName()</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetStringNameAccuracy() throws Exception {
        assertEquals("Should be '.NET'.", ".NET", ConfluenceCatalog.DOT_NET.getStringName());
        assertEquals("Should be 'Java'.", "Java", ConfluenceCatalog.JAVA.getStringName());
    }
}

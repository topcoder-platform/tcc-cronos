/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.entities;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceCatalog</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceCatalogTests extends TestCase {

    /**
     * <p>
     * Accuracy test case for {@link ConfluenceCatalog#getStringName()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getStringName_Accuracy() throws Exception {
        assertEquals("Should be '.NET'.", ".NET", ConfluenceCatalog.DOT_NET.getStringName());
        assertEquals("Should be 'Java'.", "Java", ConfluenceCatalog.JAVA.getStringName());
    }
}

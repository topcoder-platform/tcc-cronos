/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.Local;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagementServiceLocal</code> class for annotations.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceLocalAnnotationTests extends TestCase {

    /**
     * <p>
     * Verify that <code>ConfluenceManagementServiceLocal</code> contains Local annotation.
     * </p>
     */
    public void testClassAnnotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceLocal.class, Local.class);
    }
}

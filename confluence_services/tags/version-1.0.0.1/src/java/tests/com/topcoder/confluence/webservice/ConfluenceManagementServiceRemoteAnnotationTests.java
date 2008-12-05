/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import javax.ejb.Remote;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>ConfluenceManagementServiceRemote</code> class for annotations.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagementServiceRemoteAnnotationTests extends TestCase {

    /**
     * <p>
     * Verify that <code>ConfluenceManagementServiceRemote</code> contains Remote annotation.
     * </p>
     */
    public void testClassAnnotation() {
        TestHelper.assertClassAnnotation(ConfluenceManagementServiceRemote.class, Remote.class);
    }
}
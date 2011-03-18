/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.accuracytests;

import junit.framework.TestCase;

import com.topcoder.web.memberphoto.servlet.MemberInformation;

/**
 * Accuracy test for method MemberInformation.
 * @author extra
 * @version 1.0
 */
public class MemberInformationAccuracyTest extends TestCase {

    /**
     * Represents MemberInformation instance.
     */
    private MemberInformation information;

    /**
     * Sets up test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        information = new MemberInformation();
        super.setUp();
    }

    /**
     * Accuracy test for ctor method MemberInformation().
     */
    public void testCtor1() {
        assertNotNull("information should be created.", information);
    }

    /**
     * Accuracy test for method getName and setName.
     */
    public void testGetSetName() {
        information.setName("name");
        assertEquals("getName", "name", information.getName());
    }

    /**
     * Accuracy test for method getHandle and setHandle.
     */
    public void testGetSetHandle() {
        information.setHandle("handle");
        assertEquals("getHandle", "handle", information.getHandle());
    }

    /**
     * Accuracy test for method getEmailAddress and setEmailAddress.
     */
    public void testGetSetEmailAddress() {
        information.setEmailAddress("email@topcoder.com");
        assertEquals("getEmailAddress", "email@topcoder.com", information.getEmailAddress());
    }
}

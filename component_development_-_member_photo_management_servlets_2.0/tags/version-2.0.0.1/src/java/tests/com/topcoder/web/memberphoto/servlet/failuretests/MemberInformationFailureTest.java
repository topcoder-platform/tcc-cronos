/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet.failuretests;

import com.topcoder.web.memberphoto.servlet.MemberInformation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for MemberInformation class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberInformationFailureTest extends TestCase {
    /**
     * Represents the instance of MemberInformation used in unit test.
     */
    private MemberInformation instance;

    /**
     * Returns the test suite of this class.
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberInformationFailureTest.class);
    }

    /**
     * Set up for each test.
     */
    protected void setUp() {
        instance = new MemberInformation();
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When name is null.
     */
    public void testCtor_NameIsNull() {
        try {
            new MemberInformation(null, "b", "c");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When name is empty.
     */
    public void testCtor_NameIsEmpty() {
        try {
            new MemberInformation(" ", "b", "c");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When handle is null.
     */
    public void testCtor_HandleIsNull() {
        try {
            new MemberInformation("a", null, "c");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When handle is empty.
     */
    public void testCtor_HandleIsEmpty() {
        try {
            new MemberInformation("a", " ", "c");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When emailAddress is null.
     */
    public void testCtor_EmailAddressIsNull() {
        try {
            new MemberInformation("a", "b", null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#MemberInformation(String, String, String)}. When emailAddress is empty.
     */
    public void testCtor_EmailAddressIsEmpty() {
        try {
            new MemberInformation("a", "b", " ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setName(String)}. When name is null.
     */
    public void testSetName_NameIsNull() {
        try {
            instance.setName(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setName(String)}. When name is empty.
     */
    public void testSetName_NameIsEmpty() {
        try {
            instance.setName(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setHandle(String)}. When handle is null.
     */
    public void testSetHandle_HandleIsNull() {
        try {
            instance.setHandle(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setHandle(String)}. When handle is empty.
     */
    public void testSetHandle_HandleIsEmpty() {
        try {
            instance.setHandle(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setEmailAddress(String)}. When emailAddress is null.
     */
    public void testSetEmailAddress_EmailAddressIsNull() {
        try {
            instance.setEmailAddress(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Failure test for {@link MemberInformation#setEmailAddress(String)}. When emailAddress is empty.
     */
    public void testSetEmailAddress_EmailAddressIsEmpty() {
        try {
            instance.setEmailAddress(" ");
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
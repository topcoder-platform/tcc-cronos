/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>MemberInformation</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberInformationTest extends TestCase {
    /**
     * <p>
     * Name constant used for testing.
     * </p>
     */
    private static final String NAME = "name";

    /**
     * <p>
     * Handle constant used for testing.
     * </p>
     */
    private static final String HANDLE = "handle";

    /**
     * <p>
     * EmailAddress constant used for testing.
     * </p>
     */
    private static final String EMAIL_ADDRESS = "service@topcoder.com";

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberInformationTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>MemberInformation()</code>.
     * </p>
     * <p>
     * Verify that the instance can be created successfully.
     * </p>
     */
    public void testEmptyCtorAccuracy() {
        MemberInformation instance = new MemberInformation();
        assertNotNull("Unable to create MemberInformation instance.", instance);
        assertTrue("Should be instance of MemberInformation.",
                instance instanceof MemberInformation);
        assertNull("'name' should be null.", instance.getName());
        assertNull("'handle' should be null.", instance.getHandle());
        assertNull("'emailAddress' should be null.", instance.getEmailAddress());
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * Verify that the instance can be created successfully.
     * </p>
     */
    public void testCtorAccuracy() {
        MemberInformation instance =
                new MemberInformation(NAME, HANDLE, EMAIL_ADDRESS);
        assertNotNull("Unable to create MemberInformation instance.", instance);
        assertTrue("Should be instance of MemberInformation.",
                instance instanceof MemberInformation);
        assertEquals("'name' should be the same.", NAME, instance.getName());
        assertEquals("'handle' should be the same.", HANDLE,
                instance.getHandle());
        assertEquals("'emailAddress' should be the same.", EMAIL_ADDRESS,
                instance.getEmailAddress());
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * name is null, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure1() {
        try {
            new MemberInformation(null, HANDLE, EMAIL_ADDRESS);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * name is empty, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure2() {
        try {
            new MemberInformation(" ", HANDLE, EMAIL_ADDRESS);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * handle is null, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure3() {
        try {
            new MemberInformation(NAME, null, EMAIL_ADDRESS);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * handle is empty, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure4() {
        try {
            new MemberInformation(NAME, "  ", EMAIL_ADDRESS);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * emailAddress is null, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure5() {
        try {
            new MemberInformation(NAME, HANDLE, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>MemberInformation(name,handle,email)</code>.
     * </p>
     * <p>
     * emailAddress is empty, IAE should be thrown.
     * </p>
     */
    public void testCtorFailure6() {
        try {
            new MemberInformation(NAME, HANDLE, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setName(name)</code> and
     * <code>getName()</code>.
     * </p>
     * <p>
     * Verify that can set and get name successfully.
     * </p>
     */
    public void testSetAndGetNameAccuracy() {
        MemberInformation instance = new MemberInformation();
        assertNull("'name' should be null.", instance.getName());
        instance.setName(NAME);
        assertEquals("'name' should be the same.", NAME, instance.getName());
    }

    /**
     * <p>
     * Failure test for method <code>setName(name)</code> and
     * <code>getName()</code>.
     * </p>
     * <p>
     * name is null, IAE should be thrown.
     * </p>
     */
    public void testSetNameFailure1() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setName(name)</code> and
     * <code>getName()</code>.
     * </p>
     * <p>
     * name is empty, IAE should be thrown.
     * </p>
     */
    public void testSetNameFailure2() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setName("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setHandle(handle)</code> and
     * <code>getHandle()</code>.
     * </p>
     * <p>
     * Verify that can set and get handle successfully.
     * </p>
     */
    public void testSetAndGetHandleAccuracy() {
        MemberInformation instance = new MemberInformation();
        assertNull("'handle' should be null.", instance.getHandle());
        instance.setHandle(HANDLE);
        assertEquals("'handle' should be the same.", HANDLE,
                instance.getHandle());
    }

    /**
     * <p>
     * Failure test for method <code>setHandle(handle)</code> and
     * <code>getHandle()</code>.
     * </p>
     * <p>
     * handle is null, IAE should be thrown.
     * </p>
     */
    public void testSetHandleFailure1() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setHandle(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setHandle(handle)</code> and
     * <code>getHandle()</code>.
     * </p>
     * <p>
     * handle is empty, IAE should be thrown.
     * </p>
     */
    public void testSetHandleFailure2() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setHandle("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>setEmailAddress(emailAddress)</code> and
     * <code>getEmailAddress()</code>.
     * </p>
     * <p>
     * Verify that can set and get emailAddress successfully.
     * </p>
     */
    public void testSetAndGetEmailAddressAccuracy() {
        MemberInformation instance = new MemberInformation();
        assertNull("'emailAddress' should be null.", instance.getEmailAddress());
        instance.setEmailAddress(EMAIL_ADDRESS);
        assertEquals("'emailAddress' should be the same.", EMAIL_ADDRESS,
                instance.getEmailAddress());
    }

    /**
     * <p>
     * Failure test for method <code>setEmailAddress(emailAddress)</code> and
     * <code>getEmailAddress()</code>.
     * </p>
     * <p>
     * emailAddress is null, IAE should be thrown.
     * </p>
     */
    public void testSetEmailAddressFailure1() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setEmailAddress(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method <code>setEmailAddress(emailAddress)</code> and
     * <code>getEmailAddress()</code>.
     * </p>
     * <p>
     * emailAddress is empty, IAE should be thrown.
     * </p>
     */
    public void testSetEmailAddressFailure2() {
        try {
            MemberInformation instance = new MemberInformation();
            instance.setEmailAddress("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}

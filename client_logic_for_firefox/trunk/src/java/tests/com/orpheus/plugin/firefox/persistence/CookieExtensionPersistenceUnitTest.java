/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.persistence;

import com.orpheus.plugin.firefox.MockJSObject;
import com.orpheus.plugin.firefox.UnitTestHelper;

import junit.framework.TestCase;

import netscape.javascript.JSObject;

import java.util.Calendar;


/**
 * <p>
 * Tests functionality and error cases of {@link CookieExtensionPersistence} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CookieExtensionPersistenceUnitTest extends TestCase {
    /** Represents the JSObject for testing. */
    private JSObject jsObject = null;

    /**
     * Represents the timestamp of the latest feed read from the Orpheus server for testing. Default value will be
     * null.
     */
    private Calendar timestamp = null;

    /** Represents the ID of the working game for testing. Default value will be -1. */
    private long workingGameID = -1;

    /** Represents the ID string of the current target for testing. Default value will be null. */
    private String currentTargetID = null;

    /** Represents the persisted sequence number for testing. Default value will be -1. */
    private int sequenceNumber = -1;

    /** Represents the <code>CookieExtensionPersistence</code> instance used for testing. */
    private CookieExtensionPersistence persistence = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        jsObject = new MockJSObject();
        persistence = new CookieExtensionPersistence();
        persistence.setClientWindow(jsObject);
    }

    /**
     * <p>
     * Tests the accuracy of constructor {@link CookieExtensionPersistence#CookieExtensionPersistence()}.
     * </p>
     */
    public void testCookieExtensionPersistence_Accuracy() {
        assertEquals("The clientWindow value should be set properly.", jsObject,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "clientWindow"));

        assertNull("The timestamp value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));

        assertEquals("The workingGameID value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());

        assertNull("The currentTargetID value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setFeedTimestamp(Calendar)} when the given timestamp is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetFeedTimestamp_NullCalendar() throws Exception {
        try {
            persistence.setFeedTimestamp(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setFeedTimestamp(Calendar)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetFeedTimestamp_Accuracy() throws Exception {
        timestamp = Calendar.getInstance();
        persistence.setFeedTimestamp(timestamp);
        assertEquals("The timestamp value should be set properly.", timestamp,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setFeedTimestamp(Calendar)} to ensure the cookie
     * value updated properly.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetFeedTimestamp_CookieAccuracy() throws Exception {
        timestamp = Calendar.getInstance();
        persistence.setFeedTimestamp(timestamp);
        persistence.setClientWindow(jsObject);

        assertEquals("The timestamp value should be set properly.", timestamp,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));
        assertEquals("The workingGameID value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());

        assertNull("The currentTargetID value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#getFeedTimestamp()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetFeedTimestamp_Accuracy() throws Exception {
        timestamp = Calendar.getInstance();
        UnitTestHelper.setPrivateField(persistence.getClass(), persistence, "timestamp", timestamp);
        assertEquals("The workingGameID value should be got properly.", timestamp, persistence.getFeedTimestamp());
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setWorkingGameID(long)} when the given id is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetWorkingGameID_NegativeId() throws Exception {
        try {
            persistence.setWorkingGameID(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setWorkingGameID(long)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetWorkingGameID_Accuracy() throws Exception {
        workingGameID = 10;
        persistence.setWorkingGameID(workingGameID);
        assertEquals("The workingGameID value should be set properly.", workingGameID + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setWorkingGameID(long)} to ensure the cookie
     * value updated properly.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetWorkingGameID_CookieAccuracy() throws Exception {
        workingGameID = 10;
        persistence.setWorkingGameID(workingGameID);
        persistence.setClientWindow(jsObject);

        assertEquals("The workingGameID value should be set properly.", workingGameID + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());

        assertNull("The timestamp value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));

        assertNull("The currentTargetID value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#getWorkingGameID()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetWorkingGameID_Accuracy() throws Exception {
        assertEquals("The workingGameID value should be got properly.", workingGameID, persistence.getWorkingGameID());
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setCurrentTargetID(String, int)} when the given
     * currentTargetID is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_NullId() throws Exception {
        try {
            persistence.setCurrentTargetID(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setCurrentTargetID(String, int)} when the given
     * currentTargetID is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_EmptyId() throws Exception {
        try {
            persistence.setCurrentTargetID(" ", 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setCurrentTargetID(String, int)} when the given
     * sequenceNumber is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_NegativeSequenceNumber() throws Exception {
        try {
            persistence.setCurrentTargetID("currentTargetID", -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setCurrentTargetID(String, int)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_Accuracy() throws Exception {
        currentTargetID = "currentTargetID";
        sequenceNumber = 0;
        persistence.setCurrentTargetID(currentTargetID, sequenceNumber);

        assertEquals("The currentTargetID value should be set properly.", currentTargetID,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be set properly.", sequenceNumber + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setCurrentTargetID(String, int)} to ensure the
     * cookie value updated properly.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_CookieAccuracy() throws Exception {
        currentTargetID = "currentTargetID";
        sequenceNumber = 1;
        persistence.setCurrentTargetID(currentTargetID, sequenceNumber);
        persistence.setClientWindow(jsObject);

        assertEquals("The currentTargetID value should be set properly.", currentTargetID,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be set properly.", sequenceNumber + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());

        assertNull("The timestamp value should be default value.",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));

        assertEquals("The workingGameID value should be default value.", "-1",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#getCurrentTargetID()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetCurrentTargetID_Accuracy() throws Exception {
        currentTargetID = "currentTargetID";
        UnitTestHelper.setPrivateField(persistence.getClass(), persistence, "currentTargetID", currentTargetID);
        assertEquals("The currentTargetID value should be got properly.", currentTargetID,
            persistence.getCurrentTargetID());
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#getSequenceNumber()}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetSequenceNumber_Accuracy() throws Exception {
        sequenceNumber = 1;
        UnitTestHelper.setPrivateField(persistence.getClass(), persistence, "sequenceNumber",
            new Integer(sequenceNumber));
        assertEquals("The sequenceNumber value should be got properly.",
                sequenceNumber, persistence.getSequenceNumber());
    }

    /**
     * <p>
     * Tests the method {@link CookieExtensionPersistence#setClientWindow(JSObject)} when the given clientWindow is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetClientWindow_NullClientWindow() throws Exception {
        try {
            persistence.setClientWindow(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method {@link CookieExtensionPersistence#setClientWindow(JSObject)}.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetClientWindow_Accuracy() throws Exception {
        currentTargetID = "currentTargetID";
        sequenceNumber = 10;
        workingGameID = 100;
        timestamp = Calendar.getInstance();
        persistence.setCurrentTargetID(currentTargetID, sequenceNumber);
        persistence.setFeedTimestamp(timestamp);
        persistence.setWorkingGameID(workingGameID);

        persistence.setClientWindow(jsObject);
        assertEquals("The clientWindow value should be set properly.", jsObject,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "clientWindow"));

        assertEquals("The currentTargetID value should be set properly.", currentTargetID,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));

        assertEquals("The sequenceNumber value should be set properly.", sequenceNumber + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());

        assertEquals("The workingGameID value should be set properly.", workingGameID + "",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "workingGameID").toString());

        assertEquals("The timestamp value should be set properly.", timestamp,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "timestamp"));
    }
}

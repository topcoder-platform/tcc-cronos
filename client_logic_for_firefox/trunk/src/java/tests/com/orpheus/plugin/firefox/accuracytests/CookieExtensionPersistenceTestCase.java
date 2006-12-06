/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.net.URLEncoder;
import java.util.Calendar;

import netscape.javascript.JSObject;

import com.orpheus.plugin.firefox.ExtensionPersistence;
import com.orpheus.plugin.firefox.persistence.CookieExtensionPersistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the functionality of <code>CookieExtensionPersistence</code>. The test cases in this class must be executed
 * in the test applet, since it requires access to the Javascript objects. The property getters are tested together with
 * other methods.
 * 
 * @author visualage
 * @version 1.0
 */
public class CookieExtensionPersistenceTestCase extends TestCase {
    /**
     * Represents the <code>CookieExtensionPersistence</code> used in tests.
     */
    private ExtensionPersistence persistence = null;

    /**
     * Represents the window object in the Javascript.
     */
    private JSObject window = null;

    /**
     * Aggragates all tests in this class.
     * 
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(CookieExtensionPersistenceTestCase.class);
    }

    /**
     * Sets up the test environment. The test instance is created. The cookie of the HTML page is cleared. The client
     * window is set.
     * 
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        persistence = new CookieExtensionPersistence();
        window = JSObject.getWindow(JUnitTestApplet.getCurrentApplet());
        TestHelper.clearCookie(window);
        persistence.setClientWindow(window);
    }

    /**
     * Cleans up the test environment. The test instance is disposed. The cookie of the HTML page is cleared.
     */
    protected void tearDown() {
        persistence = null;
        TestHelper.clearCookie(window);
        window = null;
    }

    /**
     * Tests the accuracy of <code>CookieExtensionPersistence()</code>. A new instance should be created. All
     * properties should be set as default value (either -1 or <code>null</code>).
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testCookieExtensionPersistenceAccuarcy() throws Exception {
        // Create
        persistence = new CookieExtensionPersistence();

        // Verify
        assertNotNull("A new cookie persistence should be created.", persistence);
        assertNull("The default value of time stamp should be null.", persistence.getFeedTimestamp());
        assertEquals("The default value of working game ID should be -1.", -1, persistence.getWorkingGameID());
        assertNull("The default value of current target ID should be null.", persistence.getCurrentTargetID());
        assertEquals("The default value of sequence number should be -1.", -1, persistence.getSequenceNumber());
    }

    /**
     * Tests the accuracy of <code>setFeedTimestamp(Calendar)</code> when the timestamp is not set. The new value
     * should be set in the cookie as well as the private field. This is verified by getter and the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetFeedTimestampCalendarNewAccuarcy() throws Exception {
        // Set
        Calendar calendar = Calendar.getInstance();

        persistence.setFeedTimestamp(calendar);

        // Verify
        assertEquals("The timestamp should be set in.", calendar, persistence.getFeedTimestamp());
        assertTrue("The timestamp should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("TIMESTAMP=") >= 0);
    }

    /**
     * Tests the accuracy of <code>setFeedTimestamp(Calendar)</code> when the timestamp is set. The new value should
     * be set in the cookie as well as the private field. This is verified by getter and the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetFeedTimestampCalendarReplaceAccuarcy() throws Exception {
        // Set
        Calendar calendar = Calendar.getInstance();

        calendar.set(2000, 1, 1);
        persistence.setFeedTimestamp(calendar);

        String cookie = ((JSObject) window.getMember("document")).getMember("cookie").toString();

        // Set another
        calendar = Calendar.getInstance();
        calendar.set(2006, 10, 10);
        persistence.setFeedTimestamp(calendar);

        // Verify
        assertEquals("The timestamp should be set in.", calendar, persistence.getFeedTimestamp());
        assertFalse("The timestamp in cookie should be changed.", cookie.equals(((JSObject) window
            .getMember("document")).getMember("cookie")));
        assertTrue("The timestamp should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("TIMESTAMP=") >= 0);
    }

    /**
     * Tests the accuracy of <code>setWorkingGameID(long)</code> when the game ID is not set. The new value should be
     * set in the cookie as well as the private field. This is verified by getter and the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetWorkingGameIDLongNewAccuarcy() throws Exception {
        // Set
        persistence.setWorkingGameID(3);

        // Verify
        assertEquals("The game ID should be set in.", 3, persistence.getWorkingGameID());
        assertTrue("The game ID should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("WORKING_GAME_ID=3") >= 0);
    }

    /**
     * Tests the accuracy of <code>setWorkingGameID(long)</code> when the game ID is set. The new value should be set
     * in the cookie as well as the private field. This is verified by getter and the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetWorkingGameIDLongReplaceAccuarcy() throws Exception {
        // Set
        persistence.setWorkingGameID(3);

        // Set another
        persistence.setWorkingGameID(5);

        // Verify
        assertEquals("The game ID should be set in.", 5, persistence.getWorkingGameID());
        assertTrue("The game ID be set to the cookie.", ((JSObject) window.getMember("document")).getMember("cookie")
            .toString().indexOf("WORKING_GAME_ID=5") >= 0);
    }

    /**
     * Tests the accuracy of <code>setCurrentTargetID(String, int)</code> when the target ID and sequence number are
     * not set. The new value should be set in the cookie as well as the private field. This is verified by getter and
     * the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetCurrentTargetIDStringIntNewAccuarcy() throws Exception {
        // Set
        persistence.setCurrentTargetID("TargetID", 2);

        // Verify
        assertEquals("The target ID be set in.", "TargetID", persistence.getCurrentTargetID());
        assertEquals("The sequence number be set in.", 2, persistence.getSequenceNumber());
        assertTrue("The target ID should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("CURRENT_TARGET_ID=TargetID") >= 0);
        assertTrue("The sequence number should be set to the cookie.", ((JSObject) window.getMember("document"))
            .getMember("cookie").toString().indexOf("SEQUENCE_NUMBER=2") >= 0);
    }

    /**
     * Tests the accuracy of <code>setCurrentTargetID(String, int)</code> when the target ID and the sequence number
     * are set. The new value should be set in the cookie as well as the private field. This is verified by getter and
     * the Javascript object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetCurrentTargetIDStringIntReplaceAccuarcy() throws Exception {
        // Set
        persistence.setCurrentTargetID("TargetID", 2);

        // Set another
        persistence.setCurrentTargetID("IDIDID", 3);

        // Verify
        assertEquals("The target ID be set in.", "IDIDID", persistence.getCurrentTargetID());
        assertEquals("The sequence number be set in.", 3, persistence.getSequenceNumber());
        assertTrue("The target ID should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("CURRENT_TARGET_ID=IDIDID") >= 0);
        assertTrue("The sequence number should be set to the cookie.", ((JSObject) window.getMember("document"))
            .getMember("cookie").toString().indexOf("SEQUENCE_NUMBER=3") >= 0);
    }

    /**
     * Tests the accuracy of <code>setCurrentTargetID(String, int)</code> when the target ID needs to be escaped. The
     * new value should be set in the cookie as well as the private field. This is verified by getter and the Javascript
     * object.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetCurrentTargetIDStringIntEscapeAccuracy() throws Exception {
        // Set
        persistence.setCurrentTargetID("==;;\u1234", 2);

        // Verify
        assertEquals("The target ID be set in.", "==;;\u1234", persistence.getCurrentTargetID());
        assertEquals("The sequence number be set in.", 2, persistence.getSequenceNumber());
        assertTrue("The target ID should be set to the cookie.", ((JSObject) window.getMember("document")).getMember(
            "cookie").toString().indexOf("CURRENT_TARGET_ID=" + URLEncoder.encode("==;;\u1234", "UTF-8")) >= 0);
        assertTrue("The sequence number should be set to the cookie.", ((JSObject) window.getMember("document"))
            .getMember("cookie").toString().indexOf("SEQUENCE_NUMBER=2") >= 0);
    }

    /**
     * Tests the accuracy of <code>setClientWindow(JSObject)</code> when all keys in the cookie are missing. The
     * default value should override the existing values. The client window should be set in the private field. This is
     * verified by reflection and property getters.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetClientWindowAllMissingAccuracy() throws Exception {
        // Set something
        persistence.setCurrentTargetID("id", 1);
        persistence.setWorkingGameID(2);
        persistence.setFeedTimestamp(Calendar.getInstance());

        // Clear the cookie
        TestHelper.clearCookie(window);

        // Set the window
        persistence.setClientWindow(window);

        // Verify
        assertEquals("The client window should be set.", window, TestHelper.getPrivateField(
            CookieExtensionPersistence.class, persistence, "clientWindow"));
        assertNull("The default value of time stamp should be null.", persistence.getFeedTimestamp());
        assertEquals("The default value of working game ID should be -1.", -1, persistence.getWorkingGameID());
        assertNull("The default value of current target ID should be null.", persistence.getCurrentTargetID());
        assertEquals("The default value of sequence number should be -1.", -1, persistence.getSequenceNumber());
    }

    /**
     * Tests the accuracy of <code>setClientWindow(JSObject)</code> when all keys in the cookie are specified. The
     * specified value should override the existing values. The client window should be set in the private field. This
     * is verified by reflection and property getters.
     * 
     * @throws Exception if any unexpected exception occurs.
     */
    public void testSetClientWindowAllSpecifiedAccuracy() throws Exception {
        // Set something
        Calendar calendar = Calendar.getInstance();
        persistence.setCurrentTargetID("id", 1);
        persistence.setWorkingGameID(2);
        persistence.setFeedTimestamp(calendar);
        
        // Create a new persistence
        persistence = new CookieExtensionPersistence();

        // Set the window
        persistence.setClientWindow(window);

        // Verify
        assertEquals("The client window should be set.", window, TestHelper.getPrivateField(
            CookieExtensionPersistence.class, persistence, "clientWindow"));
        assertEquals("The value of time stamp should be retrieved.", calendar, persistence.getFeedTimestamp());
        assertEquals("The value of working game ID should be 2.", 2, persistence.getWorkingGameID());
        assertEquals("The value of current target ID should be 'id'.", "id", persistence.getCurrentTargetID());
        assertEquals("The value of sequence number should be 1.", 1, persistence.getSequenceNumber());
    }
}

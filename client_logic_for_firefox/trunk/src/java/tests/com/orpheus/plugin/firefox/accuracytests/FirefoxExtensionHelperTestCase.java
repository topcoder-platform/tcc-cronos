/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import com.orpheus.plugin.firefox.ExtensionPersistence;
import com.orpheus.plugin.firefox.FirefoxExtensionHelper;
import com.orpheus.plugin.firefox.FirefoxExtensionHelperAccuracyMock;
import com.orpheus.plugin.firefox.OrpheusServer;
import com.orpheus.plugin.firefox.UIEventListener;
import com.orpheus.plugin.firefox.UIEventType;
import com.orpheus.plugin.firefox.persistence.CookieExtensionPersistence;
import com.topcoder.bloom.BloomFilter;
import com.topcoder.util.algorithm.hash.algorithm.SHAAlgorithm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the functionality of <code>FirefoxExtensionHelper</code> class. Since the <code>initialize(String)</code>
 * method requires to call <code>JSObject.getWindow(Applet)</code>, it is not tested by the program. Also, since the
 * RSS generator 2.0 component is incomplete, <code>serverMessageReceived(RSSFeed)</code> is not tested by test cases
 * as well. The initialization of all private fields are done by reflection in the test cases. <code>popupWindow</code>
 * and <code>popupWindowWithContent</code> interacts with a popped up window. They are inspected manually.
 * 
 * @author visualage
 * @version 1.0
 */
public class FirefoxExtensionHelperTestCase extends TestCase {
    /**
     * Defines a mocked implementation of <code>JSObject</code>. It only has the ability to get and set members.
     * 
     * @author visualage
     * @version 1.0
     */
    private static class MockJSObject extends JSObject {
        /**
         * Represents the object members of this mock instance.
         */
        private final Map members;

        /**
         * Creates a new instance of <code>MockJSObject</code> class. The member map is given.
         * 
         * @param map the member map.
         */
        public MockJSObject(Map map) {
            members = map;
        }

        public Object call(String arg0, Object[] arg1) throws JSException {
            throw new JSException("Not supported.");
        }

        public Object eval(String arg0) throws JSException {
            throw new JSException("Not supported.");
        }

        public Object getMember(String arg0) throws JSException {
            return members.get(arg0);
        }

        public Object getSlot(int arg0) throws JSException {
            throw new JSException("Not supported.");
        }

        public void removeMember(String arg0) throws JSException {
            throw new JSException("Not supported.");
        }

        public void setMember(String arg0, Object arg1) throws JSException {
            members.put(arg0, arg1);
        }

        public void setSlot(int arg0, Object arg1) throws JSException {
            throw new JSException("Not supported.");
        }
    }

    /**
     * Represents the Javascript window used to communicate between Javascript and Java.
     */
    private JSObject window;

    /**
     * Represents the <code>FirefoxExtensionHelper</code> instance used in tests.
     */
    private FirefoxExtensionHelperAccuracyMock helper;

    /**
     * Represents the extension persistence instance used in tests.
     */
    private ExtensionPersistence persistence;

    /**
     * Represents the mocked Orpheus server instance used in tests.
     */
    private OrpheusServerMock server;

    /**
     * Represents the simple HTTP server used in tests.
     */
    private SimpleHttpServer httpServer;

    /**
     * Sets up the test environment. The Javascript window is obtained. The test instance is created and initialized.
     * The cookies are cleared.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    protected void setUp() throws Exception {
        httpServer = new SimpleHttpServer(8080);
        window = JSObject.getWindow(JUnitTestApplet.getCurrentApplet());
        TestHelper.clearCookie(window);
        persistence = new CookieExtensionPersistence();
        persistence.setClientWindow(window);
        helper = new FirefoxExtensionHelperAccuracyMock();
        server = new OrpheusServerMock(helper);
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "persistence", persistence);
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "clientWindow", window);
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "server", server);
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "domainParameter", "domain");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "hashMatchURL", "http://localhost:8080/hash");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "hashMatchDomainParameter", "hashdomain");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "hashMatchSequenceNumberParameter", "hashseq");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "targetTextParameter", "target");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "pageChangedURL",
            "http://localhost:8080/pagechange");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "defaultPopupWidth", new Integer(400));
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "eventPages", new HashMap());
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "filter", new BloomFilter(1, 0.1f));
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "pollURL", "http://localhost:8080/server");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "timestampParameter", "timestamp");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "pollTime", new Integer(1));
    }

    /**
     * Cleans up the test environment. All instances are disposed. The cookies are cleared.
     */
    protected void tearDown() {
        TestHelper.clearCookie(window);
        httpServer.reset();
        
        OrpheusServer thread = (OrpheusServer) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper,
            "server");

        if (thread != null) {
            thread.stopThread();
            thread.interrupt();
        }
        
        window = null;
        persistence = null;
        helper = null;
        server = null;
        httpServer = null;
    }

    /**
     * Aggragates all tests in this class.
     * 
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(FirefoxExtensionHelperTestCase.class);
    }

    /**
     * Tests the accuracy of <code>FirefoxExtensionHelper()</code>. A new instance should be created. Note that the
     * designer now requires an empty constructor.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testFirefoxExtensionHelperAccuracy() throws Exception {
        assertNotNull("A new instance should be created.", new FirefoxExtensionHelper());
    }

    /**
     * Tests the accuracy of <code>logInClick()</code>. All registered listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testLogInClickAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.logInClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "logInClick()", listener.getCalledMethod());
        }
    }

    /**
     * Tests the accuracy of <code>successfulLogIn()</code> when there is no page to pop up. All registered listeners
     * should be notified. The server should be started.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSuccessfulLogInNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.successfulLogIn();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "successfulLogIn()", listener.getCalledMethod());
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }

        assertTrue("The server should be started.", ((Thread) TestHelper.getPrivateField(FirefoxExtensionHelper.class,
            helper, "server")).isAlive());
        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>successfulLogIn()</code> when there is a page to pop up. All registered listeners
     * should be notified. The server should be started.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSuccessfulLogInPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SUCCESSFUL_LOGIN, "http://localhost:8080/successfulLogIn");

        // Do
        helper.successfulLogIn();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "successfulLogIn()", listener.getCalledMethod());
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }

        assertTrue("The server should be started.", ((Thread) TestHelper.getPrivateField(FirefoxExtensionHelper.class,
            helper, "server")).isAlive());
        assertEquals("There should be one pop up window.", "popupWindow(\"http://localhost:8080/successfulLogIn\","
            + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showActiveGamesClick()</code> when there is no page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowActiveGamesClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showActiveGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showActiveGamesClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showActiveGamesClick()</code> when there is a page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowActiveGamesClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_ACTIVE_GAMES_CLICK, "http://localhost:8080/showActiveGamesClick");

        // Do
        helper.showActiveGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showActiveGamesClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.",
            "popupWindow(\"http://localhost:8080/showActiveGamesClick\","
                + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showMyGamesClick()</code> when there is no page to pop up. All registered listeners
     * should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowMyGamesClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showMyGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showMyGamesClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showMyGamesClick()</code> when there is a page to pop up. All registered listeners
     * should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowMyGamesClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_MY_GAMES_CLICK, "http://localhost:8080/showMyGamesClick");

        // Do
        helper.showMyGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showMyGamesClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.", "popupWindow(\"http://localhost:8080/showMyGamesClick\","
            + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showUnlockedDomainsClick()</code> when there is no page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowUnlockedDomainsClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showUnlockedDomainsClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showUnlockedDomainsClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showUnlockedDomainsClick()</code> when there is a page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowUnlockedDomainsClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK, "http://localhost:8080/showUnlockedDomainsClick");

        // Do
        helper.showUnlockedDomainsClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showUnlockedDomainsClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.",
            "popupWindow(\"http://localhost:8080/showUnlockedDomainsClick\","
                + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showUpcomingGamesClick()</code> when there is no page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowUpcomingGamesClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showUpcomingGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showUpcomingGamesClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showUpcomingGamesClick()</code> when there is a page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowUpcomingGamesClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_UPCOMING_GAMES_CLICK, "http://localhost:8080/showUpcomingGamesClick");

        // Do
        helper.showUpcomingGamesClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showUpcomingGamesClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.",
            "popupWindow(\"http://localhost:8080/showUpcomingGamesClick\","
                + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showLeadersClick()</code> when there is no page to pop up. All registered listeners
     * should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowLeadersClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showLeadersClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showLeadersClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showLeadersClick()</code> when there is a page to pop up. All registered listeners
     * should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowLeadersClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_LEADERS_CLICK, "http://localhost:8080/showLeadersClick");

        // Do
        helper.showLeadersClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showLeadersClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.", "popupWindow(\"http://localhost:8080/showLeadersClick\","
            + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showLatestClueClick()</code> when there is no page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowLatestClueClickNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.showLatestClueClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showLatestClueClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>showLatestClueClick()</code> when there is a page to pop up. All registered
     * listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testShowLatestClueClickPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SHOW_LATEST_CLUE_CLICK, "http://localhost:8080/showLatestClueClick");

        // Do
        helper.showLatestClueClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "showLatestClueClick()", listener.getCalledMethod());
        }

        assertEquals("There should be one pop up window.", "popupWindow(\"http://localhost:8080/showLatestClueClick\","
            + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>successfulLogOut()</code> when there is no page to pop up. All registered listeners
     * should be notified. The server should be stopped.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSuccessfulLogOutNoPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.successfulLogOut();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "successfulLogOut()", listener.getCalledMethod());
        }

        assertEquals("The server should be stopped.", "stopThread()", server.getCalledMethod());
        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>successfulLogOut()</code> when there is a page to pop up. All registered listeners
     * should be notified. The server should be stopped.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSuccessfulLogOutPopAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.SUCCESSFUL_LOGOUT, "http://localhost:8080/successfulLogOut");

        // Do
        helper.successfulLogOut();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "successfulLogOut()", listener.getCalledMethod());
        }

        assertEquals("The server should be stopped.", "stopThread()", server.getCalledMethod());
        assertEquals("There should be one pop up window.", "popupWindow(\"http://localhost:8080/successfulLogOut\","
            + "false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>logOutClick()</code> when there is no page to retrieve. All registered listeners
     * should be notified. Note the page should not be popped up.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testLogOutClickNoPageAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.logOutClick();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "logOutClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>logOutClick()</code> when there is a page to retrieve. All registered listeners
     * should be notified. Note the page should not be popped up.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testLogOutClickPageAccuracy() throws Exception {
        // Start the server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nPage1\nPage2\nPage3\n");
        httpServer.start();

        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Put a page
        Map map = (Map) TestHelper.getPrivateField(FirefoxExtensionHelper.class, helper, "eventPages");

        map.put(UIEventType.LOGOUT_CLICK, "http://localhost:8080/logOutClick");

        // Do
        helper.logOutClick();

        // Stop http server
        httpServer.stopServer();

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "logOutClick()", listener.getCalledMethod());
        }

        assertNull("There should be no pop up window.", helper.getCalledMethod());
        assertEquals("There should be one request.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The request URL should be the given URL and GET method.", request
            .indexOf("GET /logOutClick HTTP/1.") >= 0);
    }

    /**
     * Tests the accuracy of <code>pollServerNow()</code>. The call should be delegated to the underlying server
     * instance.
     */
    public void testPollServerNowAccuracy() {
        helper.pollServerNow();

        // Verify
        assertEquals("The server's pollServerNow() method should be called.", "pollServerNow()", server
            .getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>addEventListener(UIEventListener)</code>. The event listener should be added to
     * the listener list. This is verified via reflection.
     */
    public void testAddEventListenerUIEventListenerAccuracy() {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Verify
        assertEquals("The listeners should be added.", listeners, TestHelper.getPrivateField(
            FirefoxExtensionHelper.class, helper, "eventListeners"));
    }

    /**
     * Tests the accuracy of <code>removeEventListener(UIEventListener)</code> when the listener exists. The event
     * listener should be removed from the listener list. This is verified via reflection.
     */
    public void testRemoveEventListenerUIEventListenerExistAccuracy() {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        helper.removeEventListener((UIEventListener) listeners.get(3));
        listeners.remove(3);

        // Verify
        assertEquals("The listener should be removed.", listeners, TestHelper.getPrivateField(
            FirefoxExtensionHelper.class, helper, "eventListeners"));
    }

    /**
     * Tests the accuracy of <code>removeEventListener(UIEventListener)</code> when the listener does not exist. No
     * listener should be removed. This is verified via reflection.
     */
    public void testRemoveEventListenerUIEventListenerNotExistAccuracy() {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        helper.removeEventListener(new MockUIEventListener());

        // Verify
        assertEquals("No listener should be removed.", listeners, TestHelper.getPrivateField(
            FirefoxExtensionHelper.class, helper, "eventListeners"));
    }

    /**
     * Tests the accuracy of <code>clearEventListeners()</code>. All listeners should be cleared. This is verified
     * via reflection.
     */
    public void testClearEventListenersAccuracy() {
        // Add 5 listeners
        for (int i = 0; i < 5; ++i) {
            helper.addEventListener(new MockUIEventListener());
        }

        // Clear
        helper.clearEventListeners();

        // Verify
        assertEquals("All listeners should be cleared.", new ArrayList(), TestHelper.getPrivateField(
            FirefoxExtensionHelper.class, helper, "eventListeners"));
    }

    /**
     * Tests the accuracy of <code>setWorkingGameID(long)</code>. The working game ID should be set to the
     * persistence. All listeners should be notified.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSetWorkingGameIDLongAccuracy() throws Exception {
        // Add 5 listeners
        List listeners = new ArrayList();

        for (int i = 0; i < 5; ++i) {
            UIEventListener listener = new MockUIEventListener();

            listeners.add(listener);
            helper.addEventListener(listener);
        }

        // Do
        helper.setWorkingGameID(1234);

        // Verify
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            MockUIEventListener listener = (MockUIEventListener) iter.next();

            assertEquals("The listener should be notified.", "workingGameUpdate(1234)", listener.getCalledMethod());
        }

        assertEquals("The game ID in persistence should be updated.", 1234, persistence.getWorkingGameID());
    }

    /**
     * Tests the accuracy of <code>getWorkingGameID()</code>. The working game ID should be retrieved from the
     * persistence.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testGetWorkingGameIDAccuracy() throws Exception {
        persistence.setWorkingGameID(1234);

        // Verify
        assertEquals("The working game ID should be retrieved from persistence.", 1234, helper.getWorkingGameID());
    }

    /**
     * Tests the accuracy of <code>setCurrentTargetID(String, int)</code>. The target ID and the sequence number
     * should be set to the persistence.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testSetCurrentTargetIDStringIntAccuracy() throws Exception {
        helper.setCurrentTargetID("target", 1234);

        // Verify
        assertEquals("The target ID in persistence should be updated.", "target", persistence.getCurrentTargetID());
        assertEquals("The sequence number in persistence should be updated.", 1234, persistence.getSequenceNumber());
    }

    /**
     * Tests the accuracy of <code>getCurrentTargetID()</code>. The target ID should be retrieved from the
     * persistence.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testGetCurrentTargetIDAccuracy() throws Exception {
        persistence.setCurrentTargetID("target", 1234);

        // Verify
        assertEquals("The target ID should be retrieved from persistence.", "target", helper.getCurrentTargetID());
    }

    /**
     * Tests the accuracy of <code>setPollTime(int)</code>. The method of the underlying server should be called.
     */
    public void testSetPollTimeIntAccuracy() {
        helper.setPollTime(1234);

        // Verify
        assertEquals("The underlying server should be called.", "setPollTime(1234)", server.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>getPollTime()</code>. The method of the underlying server should be called.
     */
    public void testGetPollTimeAccuracy() {
        helper.getPollTime();

        // Verify
        assertEquals("The underlying server should be called.", "getPollTime()", server.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>getFeedTimestamp()</code>. The time stamp should be retrieved from the
     * persistence.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testGetFeedTimestampAccuracy() throws Exception {
        Calendar calendar = Calendar.getInstance();

        persistence.setFeedTimestamp(calendar);

        // Verify
        assertEquals("The timestamp should be retrieved from persistence.", calendar, helper.getFeedTimestamp());
    }

    /**
     * Tests the accuracy of <code>isPopupWindow()</code> when there is no opener. Even if the name of the window is
     * 'OrpheusChild', it should return <code>false</code>.
     */
    public void testIsPopupWindowNoOpenerAccuracy() {
        // Create the member map
        Map map = new HashMap();

        map.put("name", "OrpheusChild");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "clientWindow", new MockJSObject(map));

        // Verify
        assertFalse("The window is not a popup window.", helper.isPopupWindow());
    }

    /**
     * Tests the accuracy of <code>isPopupWindow()</code> when there is opener, but the name is not 'OrpheusChild'. It
     * should return <code>false</code>.
     */
    public void testIsPopupWindowOpenerNoNameAccuracy() {
        // Create the member map
        Map map = new HashMap();

        map.put("opener", new MockJSObject(new HashMap()));
        map.put("name", "1234");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "clientWindow", new MockJSObject(map));

        // Verify
        assertFalse("The window is not a popup window.", helper.isPopupWindow());
    }

    /**
     * Tests the accuracy of <code>isPopupWindow()</code> when there is opener, and the name is 'OrpheusChild'. It
     * should return <code>true</code>.
     */
    public void testIsPopupWindowOpenerNameAccuracy() {
        // Create the member map
        Map map = new HashMap();

        map.put("opener", new MockJSObject(new HashMap()));
        map.put("name", "OrpheusChild");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "clientWindow", new MockJSObject(map));

        // Verify
        assertTrue("The window is a popup window.", helper.isPopupWindow());
    }

    /**
     * Tests the accuracy of <code>pageChanged(String)</code> when the domain is not in the bloom filter. It should
     * not call <code>queryDomain</code> method.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testPageChangedStringFilteredAccuracy() throws Exception {
        // Do
        helper.pageChanged("http://www.topcoder.com");

        // Verify
        assertNull("The queryDomain method should not be called.", server.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>pageChanged(String)</code> when the domain is in the bloom filter, but the query
     * result is 0. It should call <code>queryDomain</code> method, but should not pop up a window.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testPageChangedStringZeroCountAccuracy() throws Exception {
        // Set the query domain result.
        server.setQueryDomainResult(0);

        // Set a bloom filter
        BloomFilter filter = new BloomFilter(10, 0.1f);

        filter.add("www.topcoder.com");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "filter", filter);

        // Do
        helper.pageChanged("http://www.topcoder.com/test?anything=123&more=abc#ref");

        // Verify
        assertEquals("The queryDomain method should be called.", "queryDomain(\"domain\",\"www.topcoder.com\")", server
            .getCalledMethod());
        assertNull("No pop up window.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>pageChanged(String)</code> when the domain is in the bloom filter, and the query
     * result is not 0. It should call <code>queryDomain</code> method, and pop up a window.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testPageChangedStringNonZeroCountAccuracy() throws Exception {
        // Set the query domain result.
        server.setQueryDomainResult(1.0);

        // Set a bloom filter
        BloomFilter filter = new BloomFilter(10, 0.1f);

        filter.add("www.topcoder.com");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "filter", filter);

        // Do
        helper.pageChanged("http://www.topcoder.com/test?anything=123&more=abc#ref");

        // Verify
        assertEquals("The queryDomain method should be called.", "queryDomain(\"domain\",\"www.topcoder.com\")", server
            .getCalledMethod());
        assertEquals("A pop up window should be created.",
            "popupWindow(\"http://localhost:8080/pagechange?domain=www.topcoder.com\""
                + ",false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>pageChanged(String)</code> when the domain is in the bloom filter, and the query
     * result is not 0. The domain and its parameter should be escaped. It should call <code>queryDomain</code>
     * method, and pop up a window.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testPageChangedStringNonZeroCountEscapedAccuracy() throws Exception {
        // Set the query domain result.
        server.setQueryDomainResult(1.0);

        // Set a bloom filter
        BloomFilter filter = new BloomFilter(10, 0.1f);

        filter.add("\u1234\u5678");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "filter", filter);
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "domainParameter", "\u90ab\ucdef");

        // Do
        helper.pageChanged("http://\u1234\u5678/test?anything=123&more=abc#ref");

        // Verify
        assertEquals("The queryDomain method should be called.", "queryDomain(\"\u90ab\ucdef\",\"\u1234\u5678\")",
            server.getCalledMethod());
        assertEquals("A pop up window should be created.", "popupWindow(\"http://localhost:8080/pagechange?"
            + URLEncoder.encode("\u90ab\ucdef", "UTF-8") + "=" + URLEncoder.encode("\u1234\u5678", "UTF-8")
            + "\",false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>currentTargetTest(String)</code> when the given element cannot match the hashed
     * target ID. The HTTP server should not be accessed. The pop up window should not be created.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testCurrentTargetTestStringNoMatchAccuracy() throws Exception {
        String original = "<element attr='test' ignore='true'>    sOmE\t  \n  text  <sub>  remove<deep>deeper</deep>strange  "
            + "</sub> \u4e84k?\n\t <another   >=;*^!!!!</another>      </element>";
        String compressed = "some textremovedeeperstrange\u4e84k?=;*^!!!!";

        // Set current domain
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "currentDomain", "www.topcoder.com");

        // Calculate the target ID
        persistence.setCurrentTargetID(
            new SHAAlgorithm().hashToHexString(URLEncoder.encode(compressed + "1", "UTF-8")), 1234);

        // Do
        helper.currentTargetTest(original);

        // Verify
        assertNull("No pop up window should be created.", helper.getCalledMethod());
    }

    /**
     * Tests the accuracy of <code>currentTargetTest(String)</code> when the given element matches the hashed target
     * ID. The HTTP server should be accessed with POST method. The pop up window should be created.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testCurrentTargetTestStringMatchAccuracy() throws Exception {
        String original = "<element attr='test' ignore='true'>    sOmE\t  \n  text  <sub>  remove<deep>deeper</deep>strange  "
            + "</sub> \u4e84k?\n\t <another   >=;*^!!!!</another>      </element>";
        String compressed = "some textremovedeeperstrange\u4e84k?=;*^!!!!";

        // Set current domain
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "currentDomain", "www.topcoder.com");

        // Start HTTP server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nPage1\nPage2\nPage3\n");
        httpServer.start();

        // Calculate the target ID
        persistence
            .setCurrentTargetID(new SHAAlgorithm().hashToHexString(URLEncoder.encode(compressed, "UTF-8")), 1234);

        // Do
        helper.currentTargetTest(original);

        // Stop HTTP server
        httpServer.stopServer();

        // Verify
        assertEquals("The pop up window should be created with the content.", "popupWindowWithContent("
            + "\"Page1\nPage2\nPage3\n\",false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
        assertEquals("The HTTP server should be accessed once.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The POST method should be used to access.", request.indexOf("POST /hash HTTP/1.") >= 0);
        assertTrue("The post data should contain current domain.", request.indexOf("hashdomain=www.topcoder.com") >= 0);
        assertTrue("The post data should contain sequence number.", request.indexOf("hashseq=1234") >= 0);
        assertTrue("The post data should contain target text.", request.indexOf("target="
            + URLEncoder.encode(compressed, "UTF-8")) >= 0);
    }

    /**
     * Tests the accuracy of <code>currentTargetTest(String)</code> when the given element matches the hashed target
     * ID. The HTTP server should be accessed with POST method. The pop up window should be created. All data being
     * posted should be escaped.
     * 
     * @throws Exception if any unexpected error occurs.
     */
    public void testCurrentTargetTestStringMatchEscapedAccuracy() throws Exception {
        String original = "<element attr='test' ignore='true'>    sOmE\t  \n  text  <sub>  remove<deep>deeper</deep>strange  "
            + "</sub> \u4e84k?\n\t <another   >=;*^!!!!</another>      </element>";
        String compressed = "some textremovedeeperstrange\u4e84k?=;*^!!!!";

        // Set current domain
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "currentDomain", "\u1234\u1234");
        TestHelper
            .setPrivateField(FirefoxExtensionHelper.class, helper, "hashMatchDomainParameter", "hashdomain\u2345");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "hashMatchSequenceNumberParameter",
            "hashseq\u3456");
        TestHelper.setPrivateField(FirefoxExtensionHelper.class, helper, "targetTextParameter", "target\u4567");

        // Start HTTP server
        httpServer.addResponse("HTTP/1.1 200 OK\nContent-Type: text/html\nConnection: Close\n\nPage1\nPage2\nPage3\n");
        httpServer.start();

        // Calculate the target ID
        persistence
            .setCurrentTargetID(new SHAAlgorithm().hashToHexString(URLEncoder.encode(compressed, "UTF-8")), 1234);

        // Do
        helper.currentTargetTest(original);

        // Stop HTTP server
        httpServer.stopServer();

        // Verify
        assertEquals("The pop up window should be created with the content.", "popupWindowWithContent("
            + "\"Page1\nPage2\nPage3\n\",false,false,false,false,false,false,false,200,400)", helper.getCalledMethod());
        assertEquals("The HTTP server should be accessed once.", 1, httpServer.getRequests().size());

        String request = httpServer.getRequests().get(0).toString();

        assertTrue("The POST method should be used to access.", request.indexOf("POST /hash HTTP/1.") >= 0);
        assertTrue("The post data should contain current domain.", request.indexOf("hashdomain"
            + URLEncoder.encode("\u2345", "UTF-8") + "=" + URLEncoder.encode("\u1234\u1234", "UTF-8")) >= 0);
        assertTrue("The post data should contain sequence number.", request.indexOf("hashseq"
            + URLEncoder.encode("\u3456", "UTF-8") + "=1234") >= 0);
        assertTrue("The post data should contain target text.", request.indexOf("target"
            + URLEncoder.encode("\u4567", "UTF-8") + "=" + URLEncoder.encode(compressed, "UTF-8")) >= 0);
    }
}

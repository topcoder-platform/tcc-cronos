/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.orpheus.plugin.firefox.persistence.CookieExtensionPersistence;

import com.topcoder.bloom.BloomFilter;

import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.algorithm.hash.algorithm.SHAAlgorithm;
import com.topcoder.util.rssgenerator.RSSFeed;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Content;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Feed;
import com.topcoder.util.rssgenerator.impl.atom10.Atom10Item;

import junit.framework.TestCase;

import netscape.javascript.JSObject;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Tests functionality and error cases of {@link FirefoxExtensionHelper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionHelperUnitTest extends TestCase {
    /** Represents the JSObject for testing. */
    private MockJSObject jsObject = null;

    /** Represents the {@link FirefoxExtensionHelper} instance used for testing. */
    private FirefoxExtensionHelper helper;

    /** Represents the {@link UIEventListener} instance used for testing. */
    private UIEventListener listener = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();

        // create the FirefoxExtensionHelper instance and initialize it
        helper = new FirefoxExtensionHelper();
        jsObject = new MockJSObject();
        UnitTestHelper.setPrivateField(helper.getClass(), helper, "clientWindow", jsObject);
        helper.initialize();

        // add the listener for testing
        listener = UnitTestHelper.BuildListener();
        helper.addEventListener(listener);
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * <p>
     * Tests the method <code>serverMessageReceived(RSSFeed)</code> when the given feed is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_NullFeed() throws Exception {
        try {
            helper.serverMessageReceived(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>serverMessageReceived(RSSFeed)</code> when feed type is text.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_TextFeed() throws Exception {
        // build the feed
        RSSFeed feed = new Atom10Feed();
        Atom10Item item = new Atom10Item();
        Atom10Content content = new Atom10Content();
        content.setType("text");
        content.setElementText("text content");
        item.setContent(content);
        feed.addItem(item);

        // run
        helper.serverMessageReceived(feed);

        // check
        ExtensionPersistence persistence = (ExtensionPersistence) UnitTestHelper.getPrivateField(helper.getClass(),
                helper, "persistence");
        assertEquals("The last feedTimestamp of the persistence should be updated.", new Date(1000),
            persistence.getFeedTimestamp().getTime());

        jsObject = (MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertTrue("The content should be written.", ((MockJSObject) this.jsObject.getMember("document"))
                .getDocument().indexOf("text content") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>serverMessageReceived(RSSFeed)</code> when feed type is html.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_HtmlFeed() throws Exception {
        // build the feed
        RSSFeed feed = new Atom10Feed();
        Atom10Item item = new Atom10Item();
        Atom10Content content = new Atom10Content();
        content.setType("html");
        content.setElementText("html content");
        item.setContent(content);
        feed.addItem(item);

        // run
        helper.serverMessageReceived(feed);

        // check
        ExtensionPersistence persistence = (ExtensionPersistence) UnitTestHelper.getPrivateField(helper.getClass(),
                helper, "persistence");
        assertEquals("The last feedTimestamp of the persistence should be updated.", new Date(1000),
            persistence.getFeedTimestamp().getTime());

        jsObject = (MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertTrue("The content should be written.", ((MockJSObject) this.jsObject.getMember("document"))
            .getDocument().indexOf("html content") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>serverMessageReceived(RSSFeed)</code> when feed type is xhtml.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_XhtmlFeed() throws Exception {
        // build the feed
        RSSFeed feed = new Atom10Feed();
        Atom10Item item = new Atom10Item();
        Atom10Content content = new Atom10Content();
        content.setType("xhtml");
        content.setElementText("xhtml content");
        item.setContent(content);
        feed.addItem(item);

        // run
        helper.serverMessageReceived(feed);

        // check
        ExtensionPersistence persistence = (ExtensionPersistence) UnitTestHelper.getPrivateField(helper.getClass(),
                helper, "persistence");
        assertEquals("The last feedTimestamp of the persistence should be updated.", new Date(1000),
            persistence.getFeedTimestamp().getTime());

        jsObject = (MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertTrue("The content should be written.", ((MockJSObject) this.jsObject.getMember("document"))
                .getDocument().indexOf("xhtml content") >= 0);
    }

    /**
     * <p>
     * Tests the method <code>serverMessageReceived(RSSFeed)</code> when feed type is application/x-tc-bloom-filter and
     * the content is not a serialized bloom filter instance, FirefoxClientException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_NotSerializedFeed() throws Exception { // build the feed

        RSSFeed feed = new Atom10Feed();
        Atom10Item item = new Atom10Item();
        Atom10Content content = new Atom10Content();
        content.setType("application/x-tc-bloom-filter");
        content.setElementText("content");
        item.setContent(content);
        feed.addItem(item);

        try {
            helper.serverMessageReceived(feed);
            fail("FirefoxClientException should be thrown.");
        } catch (FirefoxClientException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>serverMessageReceived(RSSFeed)</code> when feed type is
     * application/x-tc-bloom-filter.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testServerMessageReceived_BloomFilterFeed() throws Exception {
        // build the feed
        RSSFeed feed = new Atom10Feed();
        Atom10Item item = new Atom10Item();
        Atom10Content content = new Atom10Content();
        content.setType("application/x-tc-bloom-filter");

        BloomFilter filter = new BloomFilter(1000, 0.01f);
        content.setElementText(filter.getSerialized());
        item.setContent(content);
        feed.addItem(item);

        // run
        helper.serverMessageReceived(feed);

        // check
        ExtensionPersistence persistence = (ExtensionPersistence) UnitTestHelper.getPrivateField(helper.getClass(),
                helper, "persistence");
        assertEquals("The last feedTimestamp of the persistence should be updated.", new Date(1000),
            persistence.getFeedTimestamp().getTime());

        assertEquals("The filter should be set.", filter,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "filter"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>logInClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLogInClick_Accuracy() throws Exception {
        helper.logInClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.LOGIN_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>successfulLogIn()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSuccessfulLogIn_Accuracy() throws Exception {
        helper.successfulLogIn();

        assertEquals("The listener should be invoked.",
                UnitTestHelper.SUCCESSFUL_LOGIN_FUN, jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "successfulLogin.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showActiveGamesClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowActiveGamesClick_Accuracy() throws Exception {
        helper.showActiveGamesClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_ACTIVE_GAMES_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showActiveGames.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showMyGamesClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowMyGamesClick_Accuracy() throws Exception {
        helper.showMyGamesClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_MY_GAMES_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showMyGames.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showUnlockedDomainsClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowUnlockedDomainsClick_Accuracy() throws Exception {
        helper.showUnlockedDomainsClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_UNLOCKED_DOMAINS_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showUnlockedDomains.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showUpcomingGamesClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowUpcomingGamesClick_Accuracy() throws Exception {
        helper.showUpcomingGamesClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_UPCOMING_GAMES_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showUpcomingGames.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showLeadersClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowLeadersClick_Accuracy() throws Exception {
        helper.showLeadersClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_LEADERS_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showLeaders.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>showLatestClueClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testShowLatestClueClick_Accuracy() throws Exception {
        helper.showLatestClueClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.SHOW_LATEST_CLUE_CLICK_FUN,
            jsObject.getFunctionNames());
        assertEquals("The window should popup.", UnitTestHelper.getDomain() + "showLatestClue.html",
                this.jsObject.getCurrentWindowUrl());
    }

    /**
     * <p>
     * Tests the method <code>pageChanged(String)</code> when the given newPage represents an invalid url,
     * FirefoxClientException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPageChanged_InvalidNewPage1() throws Exception {
        helper.successfulLogIn();
        try {
            helper.pageChanged("www.google.com");
            fail("FirefoxClientException should be thrown.");
        } catch (FirefoxClientException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>pageChanged(String)</code> when the given newPage represents an invalid url,
     * FirefoxClientException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPageChanged_InvalidNewPage2() throws Exception {
        helper.successfulLogIn();
        try {
            helper.pageChanged("http");
            fail("FirefoxClientException should be thrown.");
        } catch (FirefoxClientException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>pageChanged(String)</code> when the filter does not contain this new domain.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPageChanged_FilterNotContainsAccuracy() throws Exception {
        String newPage = "http://www.google.com?b=c";
        helper.successfulLogIn();
        helper.pageChanged(newPage);

        assertEquals("The currentDomain should not be updated.", null,
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "currentDomain"));

        assertEquals("The window should not popup.", UnitTestHelper.getDomain() + "successfulLogin.html",
                this.jsObject.getCurrentWindowUrl());
        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the accuracy of method <code>pageChanged(String)</code> when the filter contains this new domain.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPageChanged_FilterContainsAccuracy() throws Exception {
        String newPage = "http://www.google.com?b=c";
        BloomFilter filter = (BloomFilter) UnitTestHelper.getPrivateField(helper.getClass(), helper, "filter");
        filter.add("www.google.com");
        helper.successfulLogIn();
        helper.pageChanged(newPage);

        assertEquals("The currentDomain should be updated.", "www.google.com",
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "currentDomain"));

        assertEquals("The window should popup.", "pageChanged.html?domainParameter=www.google.com",
                ((MockJSObject)
                    ((MockJSObject)
                        ((MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow"))
                        .getMember("document"))
                    .getMember("location")).
                getCurrentWindowUrl());
        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the method <code>addEventListener(UIEventListener)</code> when the given listener is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testAddEventListener_NullListener() {
        try {
            helper.addEventListener(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addEventListener(UIEventListener)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddEventListener_Accuracy() throws Exception {
        helper.clearEventListeners();
        helper.addEventListener(listener);

        List eventListeners = (List) UnitTestHelper.getPrivateField(helper.getClass(), helper, "eventListeners");

        assertNotNull("The listeners should not be null.", eventListeners);
        assertEquals("The listeners' length should be 1.", 1, eventListeners.size());

        assertEquals("the listener should be added properly", listener, eventListeners.get(0));

        assertEquals("The client window should be set.", jsObject,
            UnitTestHelper.getPrivateField(listener.getClass(), listener, "clientWindow"));
    }

    /**
     * <p>
     * Tests the method <code>removeEventListener(UIEventListener)</code> when the given listener is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testRemoveEventListener_NullListener() {
        try {
            helper.removeEventListener(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>addEventListener(UIEventListener)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveEventListener_Accuracy() throws Exception {
        helper.removeEventListener(listener);

        List eventListeners = (List) UnitTestHelper.getPrivateField(helper.getClass(), helper, "eventListeners");
        assertEquals("The listeners' length should be 0.", 0, eventListeners.size());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>clearEventListeners()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testClearEventListeners_Accuracy() throws Exception {
        helper.clearEventListeners();

        List eventListeners = (List) UnitTestHelper.getPrivateField(helper.getClass(), helper, "eventListeners");
        assertEquals("The listeners' length should be 0.", 0, eventListeners.size());
    }

    /**
     * <p>
     * Tests the method <code>setWorkingGameID(long)</code> when the given id is negative, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetWorkingGameID_NegativeId() throws Exception {
        try {
            helper.setWorkingGameID(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setWorkingGameID(long)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetWorkingGameID_Accuracy() throws Exception {
        helper.setWorkingGameID(100);

        assertEquals("The listener should be invoked.", UnitTestHelper.WORKING_GAME_ID_UPDATE_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWorkingGameID()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetWorkingGameID_Accuracy() throws Exception {
        helper.setWorkingGameID(100);
        assertEquals("The workingGameID should be got properly", 100, helper.getWorkingGameID());
    }

    /**
     * <p>
     * Tests the method <code>setCurrentTargetID(String, int)</code> when the given id is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_NullId() throws Exception {
        try {
            helper.setCurrentTargetID(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setCurrentTargetID(String, int)</code> when the given id is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_EmptyId() throws Exception {
        try {
            helper.setCurrentTargetID(" ", 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>setCurrentTargetID(String, int)</code> when the given sequenceNumber is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_NegativeSequenceNumber() throws Exception {
        try {
            helper.setCurrentTargetID("id", -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setCurrentTargetID(String, int)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetCurrentTargetID_Accuracy() throws Exception {
        helper.setCurrentTargetID("id", 1);

        ExtensionPersistence persistence = (CookieExtensionPersistence)
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "persistence");

        assertEquals("The currentTargetID should set properly", "id",
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "currentTargetID"));
        assertEquals("The sequenceNumber should set properly", "" + 1,
            UnitTestHelper.getPrivateField(persistence.getClass(), persistence, "sequenceNumber").toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCurrentTargetID()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetCurrentTargetID_Accuracy() throws Exception {
        helper.setCurrentTargetID("id", 1);
        assertEquals("The currentTargetID should got properly", "id", helper.getCurrentTargetID());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>pollServerNow()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPollServerNow_Accuracy() throws Exception {
        helper.successfulLogIn();
        helper.pollServerNow();

        OrpheusServer server = (OrpheusServer) UnitTestHelper.getPrivateField(helper.getClass(), helper, "server");

        assertEquals("The pollTime value should be set properly.", new Date(1000),
            ((Calendar) UnitTestHelper.getPrivateField(server.getClass(), server, "timestamp")).getTime());

        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the method <code>setPollTime(int)</code> when the pollTime is not positive, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetPollTime_InvalidPollTime() throws Exception {
        helper.successfulLogIn();

        try {
            helper.setPollTime(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }

        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the accuracy of method <code>setPollTime(int)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSetPollTime_Accuracy() throws Exception {
        helper.successfulLogIn();
        helper.setPollTime(10);

        OrpheusServer server = (OrpheusServer) UnitTestHelper.getPrivateField(helper.getClass(), helper, "server");

        assertEquals("The pollTime should set properly.", 10 + "",
            UnitTestHelper.getPrivateField(server.getClass(), server, "pollTime").toString());
        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getPollTime()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetPollTime_Accuracy() throws Exception {
        helper.successfulLogIn();
        helper.setPollTime(10);
        assertEquals("The pollTime should got properly.", 10, helper.getPollTime());
        helper.successfulLogOut();
    }

    /**
     * <p>
     * Tests the method <code>currentTargetTest(String)</code> when the given element is not valid DOM Element,
     * FirefoxClientException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_InvalidElement() throws Exception {
        try {
            String element = "content </Element>";
            helper.currentTargetTest(element);
            fail("FirefoxClientException should be thrown.");
        } catch (FirefoxClientException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>currentTargetTest(String)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_MatchedAccuracy1() throws Exception {
        String element = "<Element> content </Element>";
        HashAlgorithm algorithm = new SHAAlgorithm();
        String expected = algorithm.hashToHexString(URLEncoder.encode("content",
                ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING));
        helper.setCurrentTargetID(expected, 0);

        // test will return true since the element matches
        helper.currentTargetTest(element);

        // check
        assertTrue("Content should be written.", ((MockJSObject)
            ((MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow"))
            .getMember("document")).getDocument().indexOf("hashMatchContent") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>currentTargetTest(String)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_MatchedAccuracy2() throws Exception {
        String element = "<Element> con     tent </Element>";
        HashAlgorithm algorithm = new SHAAlgorithm();
        String expected = algorithm.hashToHexString(URLEncoder.encode("con tent",
                ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING));
        helper.setCurrentTargetID(expected, 0);

        // test will return true since the element matches
        helper.currentTargetTest(element);

        // check
        assertTrue("Content should be written.", ((MockJSObject)
                ((MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow"))
                .getMember("document")).getDocument().indexOf("hashMatchContent") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>currentTargetTest(String)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_NotMatchedAccuracy() throws Exception {
        String element = "<Element> con     tent </Element>";
        HashAlgorithm algorithm = new SHAAlgorithm();
        String expected = algorithm.hashToHexString("con     tent");
        helper.setCurrentTargetID(expected, 0);

        // test will return false since the element does not match
        helper.currentTargetTest(element);

        // check
        assertNull("Content should not be written.", (MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(),
                    helper, "popupWindow"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>currentTargetTest(String)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_MatchedAccuracy3() throws Exception {
        String element = "<Element> con \n \n tent </Element>";
        HashAlgorithm algorithm = new SHAAlgorithm();
        String expected = algorithm.hashToHexString(URLEncoder.encode("con tent",
                ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING));
        helper.setCurrentTargetID(expected, 0);

        // test will return true since the element matches
        helper.currentTargetTest(element);

        // check
        assertTrue("Content should be written.", ((MockJSObject)
                ((MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow"))
                .getMember("document")).getDocument().indexOf("hashMatchContent") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>currentTargetTest(String)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCurrentTargetTest_MatchedAccuracy4() throws Exception {
        String element = "<Element> \n\n content \n\n </Element>";
        HashAlgorithm algorithm = new SHAAlgorithm();
        String expected = algorithm.hashToHexString(URLEncoder.encode("content",
                ClientLogicForFirefoxHelper.DEFAULT_URL_ENCODING));
        helper.setCurrentTargetID(expected, 0);

        // test will return true since the element matches
        helper.currentTargetTest(element);

        // check
        assertTrue("Content should be written.", ((MockJSObject)
                ((MockJSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow"))
                .getMember("document")).getDocument().indexOf("hashMatchContent") >= 0);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>popupWindow(String, boolean, boolean, boolean, boolean, boolean, boolean,
     * boolean, int, int)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPopupWindow_Accuracy() throws Exception {
        JSObject popupWindow1 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertNull("The popupWindow should be null.", popupWindow1);

        helper.popupWindow("test.html", false, false, false, false, false, false, false, 100, 100);

        JSObject popupWindow2 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertNotNull("The popupWindow should not be null.", popupWindow2);

        helper.popupWindow("test.html", false, false, false, false, false, false, false, 100, 100);

        JSObject popupWindow3 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertEquals("The popupWindow should be same as previous.", popupWindow2, popupWindow3);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>popupWindowWithContent(String, boolean, boolean, boolean, boolean, boolean,
     * boolean, boolean, int, int)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testPopupWindowWithContent_Accuracy() throws Exception {
        JSObject popupWindow1 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertNull("The popupWindow should be null.", popupWindow1);

        helper.popupWindowWithContent("test", false, false, false, false, false, false, false, 100, 100);

        JSObject popupWindow2 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertNotNull("The popupWindow should not be null.", popupWindow2);

        helper.popupWindowWithContent("test", false, false, false, false, false, false, false, 100, 100);

        JSObject popupWindow3 = (JSObject) UnitTestHelper.getPrivateField(helper.getClass(), helper, "popupWindow");
        assertEquals("The popupWindow should be same as previous.", popupWindow2, popupWindow3);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>logOutClick()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLogOutClick_Accuracy() throws Exception {
        helper.logOutClick();

        assertEquals("The listener should be invoked.", UnitTestHelper.LOGOUT_CLICK_FUN, jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>successfulLogOut()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSuccessfulLogOut_Accuracy() throws Exception {
        helper.successfulLogOut();

        assertEquals("The listener should be invoked.", UnitTestHelper.SUCCESSFUL_LOGOUT_FUN,
            jsObject.getFunctionNames());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getFeedTimestamp()</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetFeedTimestamp_Accuracy() throws Exception {
        ExtensionPersistence persistence = (CookieExtensionPersistence)
            UnitTestHelper.getPrivateField(helper.getClass(), helper, "persistence");
        Calendar cal = Calendar.getInstance();
        persistence.setFeedTimestamp(cal);

        assertEquals("The timestamp should got properly", cal, helper.getFeedTimestamp());
    }
}

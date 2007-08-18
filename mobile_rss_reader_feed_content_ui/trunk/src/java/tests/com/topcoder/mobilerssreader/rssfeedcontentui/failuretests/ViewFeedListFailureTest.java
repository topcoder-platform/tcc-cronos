/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.failuretests;

import java.util.Date;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.rssfeedcontentui.ViewFeedList;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates Failure test cases for ViewFeedList.
 * </p>
 *
 * @author iRabbit
 * @version 1.0
 */
public class ViewFeedListFailureTest extends TestCase {

    /**
     * <p>
     * Instance of the <code>RSSFeedContent</code> for test.
     * </p>
     */
    private RSSFeedContent feed = new RSSFeedContent("name", "objectId", "description", new URL(
            "http://www.topcoder.com/"), new Date());

    /**
     * Ctor.
     */
    public ViewFeedListFailureTest() {
        OSGiContainer osgicontainer = OSGiContainer.Instance();
        osgicontainer.setProperty("log4j.priority", "DEBUG");
        osgicontainer.startBundle(new LogActivator());
    }

    /**
     * Ctor.
     * @param arg0 arg0
     */
    public ViewFeedListFailureTest(String arg0) {
        super(arg0);
    }

    /**
     * Ctor.
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public ViewFeedListFailureTest(String arg0, TestMethod arg1) {
        super(arg0, arg1);
    }

    /**
     * Creates a test suite containing all J2MEUnit tests.
     *
     * @return the test suite
     */
    public Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ViewFeedListFailureTest("testConstructor_NullRSSFeedContent", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListFailureTest) test).testConstructor_NullRSSFeedContent();
            }
        }));

        suite.addTest(new ViewFeedListFailureTest("testSetFeed_NullRSSFeedContent", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListFailureTest) test).testSetFeed_NullRSSFeedContent();
            }
        }));

        suite.addTest(new ViewFeedListFailureTest("testGetSelectedFeedEntryId_NoSelectElement", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListFailureTest) test).testGetSelectedFeedEntryId_NoSelectElement();
            }
        }));

        suite.addTest(new ViewFeedListFailureTest("testSetSelectedFeedEntryStatus_NoSelectElement", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListFailureTest) test).testSetSelectedFeedEntryStatus_NoSelectElement();
            }
        }));

        return suite;
    }

    /**
     * <p>
     * Failure test for constructor: ViewFeedList(String, RSSFeedContent). <br>
     * Failure cause: RSSFeedContent is null. <br>
     * Expected: IllegalArgumentException.
     * </p>
     */
    public void testConstructor_NullRSSFeedContent() {
        try {
            new ViewFeedList(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method: getSelectedFeedEntryId(). <br>
     * Failure cause: if no element in the list selected. <br>
     * Expected: IllegalStateException.
     * </p>
     */
    public void testGetSelectedFeedEntryId_NoSelectElement() {
        ViewFeedList viewFeedList = new ViewFeedList(null, feed);
        try {
            viewFeedList.getSelectedFeedEntryId();
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method: setFeed(String, RSSFeedContent). <br>
     * Failure cause: RSSFeedContent is null. <br>
     * Expected: IllegalArgumentException.
     * </p>
     */
    public void testSetFeed_NullRSSFeedContent() {
        ViewFeedList viewFeedList = new ViewFeedList(null, feed);
        try {
            viewFeedList.setFeed(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method: setSelectedFeedEntryStatus(boolean). <br>
     * Failure cause: if no element in the list selected. <br>
     * Expected: IllegalStateException.
     * </p>
     */
    public void testSetSelectedFeedEntryStatus_NoSelectElement() {
        ViewFeedList viewFeedList = new ViewFeedList(null, feed);
        try {
            viewFeedList.setSelectedFeedEntryStatus(true);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }
}

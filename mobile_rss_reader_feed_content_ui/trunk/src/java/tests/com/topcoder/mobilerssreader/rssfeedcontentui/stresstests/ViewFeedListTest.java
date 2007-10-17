/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.mobilerssreader.rssfeedcontentui.stresstests;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

import java.util.Date;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;
import com.topcoder.mobilerssreader.rssfeedcontentui.ViewFeedList;

/**
 * <p>
 * Stress test case for class <code>ViewFeedList</code>.
 * </p>
 * <p>
 * The class is not thread safe, and all the APIs are somewhat simple, so we
 * only care the performance of each method.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class ViewFeedListTest extends TestCase {

    /**
     * The title used to create the <code>ViewFeedList</code> instance.
     */
    private static final String TITLE = "title";

    /**
     * The <code>RSSFeedContent</code> instance used to create
     * <code>ViewFeedList</code> instance.
     */
    private RSSFeedContent feedContent = null;

    /**
     * The <code>RSSFeedContentEntry</code> array used for test.
     */
    private RSSFeedContentEntry[] entries = null;

    /**
     * The <code>ViewFeedList</code> instance used for test.
     */
    private ViewFeedList list = null;

    /**
     * <p>
     * The default constructor.
     * </p>
     */
    public ViewFeedListTest() {
        OSGiContainer osgicontainer = OSGiContainer.Instance();
        osgicontainer.setProperty("log4j.priority", "DEBUG");
        osgicontainer.startBundle(new LogActivator());
    }

    /**
     * <p>
     * The constructor with name and test method.
     * </p>
     * @param testName the name of the case.
     * @param testMethod the method to test.
     */
    public ViewFeedListTest(String testName, TestMethod testMethod) {
        super(testName, testMethod);
    }

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        entries = new RSSFeedContentEntry[50];
        for (int i = 0; i < 50; i++) {
            entries[i] = new RSSFeedContentEntry("entry: " + i, "entry id: "
                    + i, "description", new URL("http://www.topcoder.com/tc"),
                    false, new Date());
        }
        feedContent = new RSSFeedContent("name", "objectId", "description",
                new URL("http://www.topcoder.com/tc"), new Date(), entries);
        list = new ViewFeedList(TITLE, feedContent);
    }

    /**
     * Cleans up the test environment.
     */
    protected void tearDown() {
        list = null;
        feedContent = null;
    }

    /**
     * <p>
     * Tests for method <code>getFeedId()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testGetFeedId() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            assertEquals("Fails to get the feed id.", "objectId", list
                    .getFeedId());
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testGetFeedId takes " + (end - start)
                + " milliseconds.");
    }

    /**
     * <p>
     * Tests for method <code>getSelectedFeedEntryId()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testGetSelectedFeedEntryId() {
        long start = System.currentTimeMillis();
        list.setSelectedIndex(25, true);
        for (int i = 0; i < 10; i++) {
            assertEquals("Fails to get the selected feed entry id.",
                    "entry id: 25", list.getSelectedFeedEntryId());
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testGetSelectedFeedEntryId takes "
                + (end - start) + " milliseconds.");
    }

    /**
     * <p>
     * Tests for method <code>setFeed(String, RSSFeedContent)</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testSetFeed() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            list.setFeed(TITLE, feedContent);
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testSetFeed takes " + (end - start)
                + " milliseconds.");
    }

    /**
     * <p>
     * Tests for method <code>toggleAll()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testToggleAll() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            list.toggleAll();
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testToggleAll takes "
                + (end - start) + " milliseconds.");
    }
    
    /**
     * <p>
     * Tests for method <code>testSetSelectedFeedENtryStatus(boolean)</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testSetSelectedFeedEntryStatus() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            list.setSelectedFeedEntryStatus(true);
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testSetSelectedFeedEntryStatus takes "
                + (end - start) + " milliseconds.");
    }
    
    /**
     * <p>
     * Tests for method <code>updateUI()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testUpdateUI() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            list.updateUI();
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testUpdateUI takes "
                + (end - start) + " milliseconds.");
    }

    /**
     * <p>
     * Return test suite containing this class' tests.
     * </p>
     * @return test suite containing this class' tests.
     */
    public Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ViewFeedListTest("testGetFeedId", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testGetFeedId();
            }
        }));
        suite.addTest(new ViewFeedListTest("testGetSelectedFeedEntryId",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ViewFeedListTest) test).testGetSelectedFeedEntryId();
                    }
                }));
        suite.addTest(new ViewFeedListTest("testSetFeed",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ViewFeedListTest) test).testSetFeed();
                    }
                }));
        suite.addTest(new ViewFeedListTest("testSetSelectedFeedEntryStatus",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ViewFeedListTest) test).testSetSelectedFeedEntryStatus();
                    }
                }));
        suite.addTest(new ViewFeedListTest("testToggleAll",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ViewFeedListTest) test).testToggleAll();
                    }
                }));
        suite.addTest(new ViewFeedListTest("testUpdateUI",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ViewFeedListTest) test).testUpdateUI();
                    }
                }));
        return suite;
    }
}

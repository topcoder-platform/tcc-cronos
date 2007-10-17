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
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;
import com.topcoder.mobilerssreader.rssfeedcontentui.ReplyFeedEntryForm;

/**
 * <p>
 * Stress test case for class <code>ReplyFeedEntryForm</code>.
 * </p>
 * <p>
 * The class is not thread safe, and all the APIs are somewhat simple, so we
 * only care the performance of each method.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class ReplyFeedEntryFormTest extends TestCase {

    /**
     * The title used to create the <code>ReplyFeedEntryForm</code> instance.
     */
    private static final String TITLE = "title";

    /**
     * The <code>RSSFeedContentEntry</code> used for test.
     */
    private RSSFeedContentEntry entry = null;

    /**
     * The <code>ReplyFeedEntryForm</code> instance used for test.
     */
    private ReplyFeedEntryForm form = null;

    /**
     * <p>
     * The default constructor.
     * </p>
     */
    public ReplyFeedEntryFormTest() {
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
    public ReplyFeedEntryFormTest(String testName, TestMethod testMethod) {
        super(testName, testMethod);
    }

    /**
     * Sets up the test environment.
     */
    protected void setUp() {

        entry = new RSSFeedContentEntry("entry", "entry id", "description",
                new URL("http://www.topcoder.com/tc"), false, new Date());

        form = new ReplyFeedEntryForm(TITLE, entry, null, 20);
    }

    /**
     * Cleans up the test environment.
     */
    protected void tearDown() {
        form = null;
        entry = null;
    }

    /**
     * <p>
     * Tests for method <code>getFeedEntryId()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testGetFeedEntryId() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            assertEquals("Fails to get the feed entry id.", "entry id", form.getFeedEntryId());
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testGetFeedEntryId takes " + (end - start)
                + " milliseconds.");
    }

    /**
     * <p>
     * Tests for method <code>setFeedEntry(String, RSSFeedContentEntry)</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testSetFeedEntry() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            form.setFeedEntry(TITLE, entry);
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testSetFeedEntry takes " + (end - start)
                + " milliseconds.");
    }
    
    /**
     * <p>
     * Tests for method <code>clear()</code>.
     * </p>
     * <p>
     * Tests the performance of this method.
     * </p>
     */
    public void testClear() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            form.clear();
        }
        long end = System.currentTimeMillis();
        System.out.println("The test testClear takes " + (end - start)
                + " milliseconds.");
    }

    /**
     * <p>
     * Return test suite containing this class' tests.
     * </p>
     * @return test suite containing this class' tests.
     */
    public Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ReplyFeedEntryFormTest("testGetFeedEntryId",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ReplyFeedEntryFormTest) test).testGetFeedEntryId();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormTest("testSetFeedEntry",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ReplyFeedEntryFormTest) test).testSetFeedEntry();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormTest("testClear",
                new TestMethod() {
                    public void run(TestCase test) throws Throwable {
                        ((ReplyFeedEntryFormTest) test).testClear();
                    }
                }));
        return suite;
    }
}

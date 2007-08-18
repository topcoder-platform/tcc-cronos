/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.failuretests;

import java.util.Date;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;
import com.topcoder.mobilerssreader.rssfeedcontentui.ReadFeedEntryForm;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates Failure test cases for ReadFeedEntryForm.
 * </p>
 *
 * @author iRabbit
 * @version 1.0
 */
public class ReadFeedEntryFormFailureTest extends TestCase {

    /**
     * <p>
     * Instance of <code>RSSFeedContentEntry</code> for test.
     * </p>
     */
    private RSSFeedContentEntry feedEntry = new RSSFeedContentEntry("name", "objectId", "description", new URL(
            "http://www.topcoder.com/"), true, new Date());

    /**
     * Ctor.
     */
    public ReadFeedEntryFormFailureTest() {
        OSGiContainer osgicontainer = OSGiContainer.Instance();
        osgicontainer.setProperty("log4j.priority", "DEBUG");
        osgicontainer.startBundle(new LogActivator());
    }

    /**
     * Ctor.
     * @param arg0 arg0
     */
    public ReadFeedEntryFormFailureTest(String arg0) {
        super(arg0);
    }

    /**
     * Ctor.
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public ReadFeedEntryFormFailureTest(String arg0, TestMethod arg1) {
        super(arg0, arg1);
    }

    /**
     * Creates a test suite containing all J2MEUnit tests.
     *
     * @return the test suite
     */
    public Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ReadFeedEntryFormFailureTest("testConstructor_NullRSSFeedContentEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormFailureTest) test).testConstructor_NullRSSFeedContentEntry();
            }
        }));

        suite.addTest(new ReadFeedEntryFormFailureTest("testSetFeedEntry_NullRSSFeedContentEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormFailureTest) test).testSetFeedEntry_NullRSSFeedContentEntry();
            }
        }));

        return suite;
    }

    /**
     * <p>
     * Failure test for constructor: ReadFeedEntryForm(String, RSSFeedContentEntry, Image). <br>
     * Failure cause: RSSFeedContentEntry is null. <br>
     * Expected: IllegalArgumentException.
     * </p>
     */
    public void testConstructor_NullRSSFeedContentEntry() {
        try {
            new ReadFeedEntryForm(null, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for method: setFeedEntry(String, RSSFeedContentEntry). <br>
     * Failure cause: RSSFeedContentEntry is null. <br>
     * Expected: IllegalArgumentException.
     * </p>
     */
    public void testSetFeedEntry_NullRSSFeedContentEntry() {
        ReadFeedEntryForm readFeedEntryForm = new ReadFeedEntryForm(null, feedEntry, null);
        try {
            readFeedEntryForm.setFeedEntry(null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}

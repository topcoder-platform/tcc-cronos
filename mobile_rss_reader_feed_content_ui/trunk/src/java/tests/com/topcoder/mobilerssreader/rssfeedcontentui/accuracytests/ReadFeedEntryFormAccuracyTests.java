/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests;


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
 * Accuracy tests for class <code>ReadFeedEntryForm</code>.
 * </p>
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReadFeedEntryFormAccuracyTests extends TestCase {
    /** Instance of <code>ReadFeedEntryForm</code> for testing. */
    private ReadFeedEntryForm instance = null;

    /** The feedEntry of the ReadFeedEntryForm for testing. */
    private RSSFeedContentEntry feedEntry;

    /**
     * Simply call the super constructor.
     */
    public ReadFeedEntryFormAccuracyTests() {
        super();
    }

    /**
     * Simply call the super constructor with parameters.
     *
     *
     * @param testName
     *            the test name
     * @param testMethod
     *            the TestMethod to run
     */
    public ReadFeedEntryFormAccuracyTests(String testName, TestMethod testMethod) {
        super(testName, testMethod);
    }

    /**
     * Set up the environment.
     *
     * @throws Exception
     *             exception to j2meunit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        OSGiContainer osgicontainer = OSGiContainer.Instance();
        osgicontainer.setProperty("log4j.priority", "DEBUG");
        osgicontainer.startBundle(new LogActivator());

        feedEntry = new RSSFeedContentEntry("name", "objectId", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        instance = new ReadFeedEntryForm("title", feedEntry, null);
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception
     *             exception to j2meunit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test the Constructor of ReadFeedEntryForm. The title, feedEntryId should be set after construct.
     */
    public void testReadFeedEntryForm() {
        assertNotNull("failed to create instance", instance);
        assertEquals("failed to initiate with title", "title", instance.getTitle());
        assertEquals("failed to initiate with feedEntry", feedEntry.getObjectId(), instance.getFeedEntryId());
    }

    /**
     * Test the method of setFeedEntry. The title, feedEntryId should be set correctly with this method.
     */
    public void testSetFeedEntry() {
        RSSFeedContentEntry another = new RSSFeedContentEntry("another", "anotherobjectId", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        instance.setFeedEntry("another-title", another);
        assertEquals("failed to setFeedEntry with title", "another-title", instance.getTitle());
        assertEquals("failed to setFeedEntry with feedEntry", another.getObjectId(), instance.getFeedEntryId());
    }

    /**
     * Test the method of getFeedEntryId. The feedEntryId should be returned.
     */
    public void testGetFeedEntryId() {
        assertEquals("failed to initiate with feedEntry", feedEntry.getObjectId(), instance.getFeedEntryId());
    }

    /**
     * Test the method of getLogger. The logger should be returned.
     */
    public void testGetLogger() {
        assertNotNull("failed to initiate logger", ReadFeedEntryForm.getLogger());
    }

    /**
     * Aggregate all test cases.
     *
     * @return the test suite of this class
     *
     */
    public Test suite() {
        final TestSuite suite = new TestSuite();

        // add all the test cases
        suite.addTest(new ReadFeedEntryFormAccuracyTests("testGetFeedEntryId",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReadFeedEntryFormAccuracyTests) tc)
                                .testGetFeedEntryId();
                    }
                }));
        suite.addTest(new ReadFeedEntryFormAccuracyTests(
                "testGetLogger", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReadFeedEntryFormAccuracyTests) tc)
                                .testGetLogger();
                    }
                }));
        suite.addTest(new ReadFeedEntryFormAccuracyTests(
                "testReadFeedEntryForm", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReadFeedEntryFormAccuracyTests) tc)
                                .testReadFeedEntryForm();
                    }
                }));
        suite.addTest(new ReadFeedEntryFormAccuracyTests(
                "testSetFeedEntry", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReadFeedEntryFormAccuracyTests) tc)
                                .testSetFeedEntry();
                    }
                }));
        return suite;
    }

}

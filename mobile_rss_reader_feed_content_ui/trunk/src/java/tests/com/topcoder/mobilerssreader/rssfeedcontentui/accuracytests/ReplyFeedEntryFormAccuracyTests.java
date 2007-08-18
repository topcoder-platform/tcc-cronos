/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests;

import java.util.Date;

import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;
import com.topcoder.mobilerssreader.rssfeedcontentui.ReplyFeedEntryForm;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * Accuracy tests for class <code>ReplyFeedEntryForm</code>.
 * </p>
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReplyFeedEntryFormAccuracyTests extends TestCase {
    /** Instance of <code>ReplyFeedEntryForm</code> for testing. */
    private ReplyFeedEntryForm instance = null;

    /** The feedEntry of the ReplyFeedEntryForm for testing. */
    private RSSFeedContentEntry feedEntry;

    /**
     * Simply call the super constructor.
     */
    public ReplyFeedEntryFormAccuracyTests() {
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
    public ReplyFeedEntryFormAccuracyTests(String testName, TestMethod testMethod) {
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

        feedEntry = new RSSFeedContentEntry("name", "id", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        instance = new ReplyFeedEntryForm("title", feedEntry, null, 100);
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
     * Test the Constructor of ReplyFeedEntryForm. Title, feedEntryId, maxSize should be set correctly.
     */
    public void testReplyFeedEntryForm() {
        assertEquals("failed to initiate with title", "title", instance.getTitle());
        assertEquals("failed to initiate with feedEntry", feedEntry.getObjectId(), instance.getFeedEntryId());
        TextField field = getTextField(instance, "Comments:");
        assertEquals("failed to initiate with max size for text field", 100, field.getMaxSize());
    }

    /**
     * Test the method of setFeedEntry. Title, feedEntryId should be set correctly.
     */
    public void testSetFeedEntry() {
        RSSFeedContentEntry another = new RSSFeedContentEntry("another", "another_id", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        instance.setFeedEntry("another-title", another);
        assertEquals("failed to setFeedEntry with title", "another-title", instance.getTitle());
        assertEquals("failed to setFeedEntry with feedEntry", another.getObjectId(), instance.getFeedEntryId());
    }

    /**
     * Test the method of getFeedEntryId. the correct feedEntryId should be returned.
     */
    public void testGetFeedEntryId() {
        assertEquals("incorrect feed entry id", feedEntry.getObjectId(), instance.getFeedEntryId());
    }

    /**
     * Test the method of getPostMessage. The postMessage should be returned correctly.
     */
    public void testGetPostMessage() {
        assertEquals("the message should be clear", "", instance.getPostMessage());
        TextField field = getTextField(instance, "Comments:");
        String message = "message";
        field.setString(message);
        assertEquals("the message is not null", message, instance.getPostMessage());
    }

    /**
     * Test the method of clear. The postMessage should be empty while calling clear at any time.
     */
    public void testClear() {
        TextField field = getTextField(instance, "Comments:");
        String message = "message";
        field.setString(message);
        assertEquals("the message is not null", message, instance.getPostMessage());
        instance.clear();
        assertEquals("the message should be clear", "", instance.getPostMessage());
    }

    /**
     * Test the method of getLogger. The logger instance should be return.
     */
    public void testGetLogger() {
        assertNotNull("failed to initiate logger", ReplyFeedEntryForm.getLogger());
    }

    /**
     * <p>
     * Return TextField with given label from given form.
     * </p>
     *
     * @param form
     *            the given form to check.
     * @param label
     *            the given string value of label.
     * @return true if the form contains TextField with null string value and the same label as the given label, else
     *         false.
     */
    private TextField getTextField(ReplyFeedEntryForm form, String label) {
        for (int i = 0; i < form.size(); i++) {
            Item item = form.get(i);
            if (item instanceof TextField) {
                TextField field = (TextField) item;
                // if TextField with the same label and null string, return true
                if (label.equals(field.getLabel())) {
                    return field;
                }
            }
        }
        return null;
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
        suite.addTest(new ReplyFeedEntryFormAccuracyTests("testClear",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                                .testClear();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormAccuracyTests(
                "testGetFeedEntryId", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                                .testGetFeedEntryId();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormAccuracyTests(
                "testGetLogger", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                                .testGetLogger();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormAccuracyTests(
                "testGetPostMessage", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                                .testGetPostMessage();
                    }
                }));
        suite.addTest(new ReplyFeedEntryFormAccuracyTests(
                "testReplyFeedEntryForm", new TestMethod() {
                    public void run(TestCase tc) throws Exception{
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                        .testReplyFeedEntryForm();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormAccuracyTests(
                "testSetFeedEntry", new TestMethod() {
                    public void run(TestCase tc) throws Exception{
                        ((ReplyFeedEntryFormAccuracyTests) tc)
                        .testSetFeedEntry();
            }
        }));
        return suite;
    }
}
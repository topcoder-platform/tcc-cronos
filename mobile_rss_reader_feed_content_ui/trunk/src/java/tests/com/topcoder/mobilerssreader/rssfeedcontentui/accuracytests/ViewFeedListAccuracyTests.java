/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui.accuracytests;

import java.util.Date;

import javax.microedition.lcdui.Font;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;
import com.topcoder.mobilerssreader.rssfeedcontentui.ReadFeedEntryForm;
import com.topcoder.mobilerssreader.rssfeedcontentui.ViewFeedList;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * Accuracy tests for class <code>ViewFeedList</code>.
 * </p>
 *
 * @author brain_cn
 * @version 1.0
 */
public class ViewFeedListAccuracyTests extends TestCase {
    /** Instance of <code>ViewFeedList</code> for testing. */
    private ViewFeedList instance = null;

    /** The RSSFeedContent of the ViewFeedList for testing. */
    private RSSFeedContent feed;

    /** The entries of the RSSFeedContent for testing. */
    private RSSFeedContentEntry[] entries;

    /**
     * Simply call the super constructor.
     */
    public ViewFeedListAccuracyTests() {
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
    public ViewFeedListAccuracyTests(String testName, TestMethod testMethod) {
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

        entries = new RSSFeedContentEntry[8];
        for (int i = 1; i <= entries.length; i++) {
            entries[i - 1] = new RSSFeedContentEntry("RSSFeedContentEntry" + i,
                    "objectId" + i, "description" + i, new URL(
                            "http://www.topcoder.com"), true, new Date());
        }
        feed = new RSSFeedContent("RSSFeedContent", "objectId", "description",
                new URL("http://www.topcoder.com"), new Date(), entries);

        instance = new ViewFeedList("title", feed);
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
     * Test the method of updateUI. The correct font should be set.
     */
    public void testUpdateUI() {
        Font bold = Font.getFont(Font.STYLE_BOLD);
        Font defaultFont = Font.getDefaultFont();

        // update ui with current model
        instance.updateUI();
        FilterListModel model = instance.getFilterListModel();
        for (int i = 0; i < model.getSize(); i++) {
            FilterListEntry entry = model.get(i);

            // 3 is read/unread column
            Font expected = "true".equals(entry.getColumn(3)) ? defaultFont : bold;
            assertEquals("incorrect font", expected, instance.getFont(i));
        }

        // Toggle the read/unread status
        instance.toggleAll();
        instance.updateUI();
        model = instance.getFilterListModel();

        for (int i = 0; i < model.getSize(); i++) {
            FilterListEntry entry = model.get(i);

            // 3 is read/unread column
            Font expected = "true".equals(entry.getColumn(3)) ? defaultFont : bold;
            assertEquals("incorrect font", expected, instance.getFont(i));
        }
    }

    /**
     * Test the constructor of viewFeedList.
     */
    public void testViewFeedList() {
        assertEquals("incorrect feedId", feed.getObjectId(), instance.getFeedId());
        assertEquals("incorrect title", "title", instance.getTitle());

        // Verify the model
        FilterListModel model = instance.getFilterListModel();

        assertEquals("size of model.", entries.length, model.getSize());

        // all the entry should be added to the model.
        for (int i = 0; i < entries.length; i++) {
            FilterListEntry entry = model.get(i);

            assertEquals("incorrect column size", 4, entry.getColumnSize());
            assertEquals("incorrect name", entries[i].getName(), entry.getColumn(0));
            assertEquals("incorrect update date", entries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("incorrect object id", entries[i].getObjectId(), entry.getColumn(2));
            assertTrue("incorrect mark as read/unread", "true".equals(entry.getColumn(3)) == entries[i].isRead());
        }
    }

    /**
     * Test the method of setFeed. Verify the title, feedId, mode should be set correctly.
     */
    public void testSetFeed() {
        RSSFeedContentEntry[] anotherEntries = new RSSFeedContentEntry[10];
        for (int i = 1; i <= anotherEntries.length; i++) {
            anotherEntries[i - 1] = new RSSFeedContentEntry("RSSFeedContentEntry" + i,
                    "objectId" + i, "description" + i, new URL(
                            "http://www.topcoder.com"), true, new Date());
        }

        RSSFeedContent another = new RSSFeedContent("RSSFeedContent", "objectId", "description",
                new URL("http://www.topcoder.com"), new Date(), anotherEntries);

        instance.setFeed("another title", another);

        assertEquals("incorrect feedId", another.getObjectId(), instance.getFeedId());
        assertEquals("incorrect title", "another title", instance.getTitle());

        // Verify the model
        FilterListModel model = instance.getFilterListModel();

        assertEquals("size of model.", anotherEntries.length, model.getSize());

        // all the entry should be added to the model.
        for (int i = 0; i < anotherEntries.length; i++) {
            FilterListEntry entry = model.get(i);

            assertEquals("incorrect column size", 4, entry.getColumnSize());
            assertEquals("incorrect name", anotherEntries[i].getName(), entry.getColumn(0));
            assertEquals("incorrect update date", anotherEntries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("incorrect object id", anotherEntries[i].getObjectId(), entry.getColumn(2));
            assertTrue("incorrect mark as read/unread", "true".equals(entry.getColumn(3)) == anotherEntries[i].isRead());
        }
    }

    /**
     * Test the method of getSelectedFeedEntryId.
     */
    public void testGetSelectedFeedEntryId() {
        for (int i = 0; i < instance.size(); i++) {
            instance.setSelectedIndex(i, true);
            assertEquals("the feed entry id is ", entries[i].getObjectId(), instance.getSelectedFeedEntryId());
        }
    }

    /**
     * Test the method of setSelectedFeedEntryStatus. with read status set.
     */
    public void testSetSelectedFeedEntryStatus_with_read() {
        for (int i = 0; i < instance.size(); i++) {
            instance.setSelectedIndex(i, true);
            instance.setSelectedFeedEntryStatus(true);
            assertEquals("failed to set status", "true", instance.getFilterListModel().get(i).getColumn(3));
        }
    }

    /**
     * Test the method of setSelectedFeedEntryStatus. With unread set.
     */
    public void testSetSelectedFeedEntryStatus_with_unread() {
        for (int i = 0; i < instance.size(); i++) {
            instance.setSelectedIndex(i, true);
            instance.setSelectedFeedEntryStatus(false);
            assertEquals("failed to set status", "false", instance.getFilterListModel().get(i).getColumn(3));
        }
    }

    /**
     * Test the method of getFeedId.
     */
    public void testGetFeedId() {
        assertEquals("incorrect feedId", feed.getObjectId(), instance.getFeedId());
    }

    /**
     * Test the method of toggleAll.
     */
    public void testToggleAll() {
        instance.toggleAll();

        FilterListModel model = instance.getFilterListModel();

        assertEquals("size of model.", entries.length, model.getSize());

        // all the entry should be added to the model.
        for (int i = 0; i < entries.length; i++) {
            FilterListEntry entry = model.get(i);
            assertTrue("incorrect mark as read/unread", "true".equals(entry.getColumn(3)) != entries[i].isRead());
        }
    }

    /**
     * Test the method of getLogger.
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
        suite.addTest(new ViewFeedListAccuracyTests("testGetFeedId",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testGetFeedId();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests("testGetLogger",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testGetLogger();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests(
                "testGetSelectedFeedEntryId", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc)
                                .testGetSelectedFeedEntryId();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests("testSetFeed",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testSetFeed();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests(
                "testSetSelectedFeedEntryStatus_with_read", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc)
                                .testSetSelectedFeedEntryStatus_with_read();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests(
                "testSetSelectedFeedEntryStatus_with_unread", new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc)
                                .testSetSelectedFeedEntryStatus_with_unread();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests("testToggleAll",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testToggleAll();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests("testUpdateUI",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testUpdateUI();
                    }
                }));
        suite.addTest(new ViewFeedListAccuracyTests("testViewFeedList",
                new TestMethod() {
                    public void run(TestCase tc) throws Exception {
                        ((ViewFeedListAccuracyTests) tc).testViewFeedList();
                    }
                }));

        return suite;
    }
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import java.util.Date;

import javax.microedition.lcdui.Font;

import org.apache.log4j.LogActivator;
import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.filterlistscreen.FilterList;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * Unit tests for class <code>ViewFeedList</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewFeedListTest extends TestCase {
    /**
     * <p>
     * Title of the feed content for test.
     * </p>
     */
    private String title;
    /**
     * <p>
     * New title of the feed content for test.
     * </p>
     */
    private String newTitle;
    /**
     * <p>
     * Instance of the <code>RSSFeedContent</code> for test.
     * </p>
     */
    private RSSFeedContent feed;
    /**
     * <p>
     * New instance of the <code>RSSFeedContent</code> for test.
     * </p>
     */
    private RSSFeedContent newFeed;

    /**
     * <p>
     * Instance of the <code>RSSFeedContent</code> with empty entries for test.
     * </p>
     */
    private RSSFeedContent emptyFeed;
    /**
     * <p>
     * Instance of the <code>ViewFeedList</code> for test.
     * </p>
     */
    private ViewFeedList feedList;

    /**
     * <p>
     * Instance of the <code>RSSFeedContentEntry</code> array for test.
     * </p>
     */
    private RSSFeedContentEntry[] entries;
    /**
     * <p>
     * New instance of the <code>RSSFeedContentEntry</code> array for test.
     * </p>
     */
    private RSSFeedContentEntry[] newEntries;


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
     *
     * @param testName
     *            the name of the case.
     * @param testMethod
     *            the method to test.
     */
    public ViewFeedListTest(String testName, TestMethod testMethod) {
        super(testName, testMethod);
    }

    /**
     * <p>
     * Set up the environment.
     * </p>
     *
     * @throws Exception
     *             exception to j2meunit.
     */
    protected void setUp() throws Exception {
        title = "title";
        entries = new RSSFeedContentEntry[10];
        boolean read = true;
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new RSSFeedContentEntry("name:" + i, "objectId:" + i, "description:" + i, new URL(
                    "http://www.topcoder.com/"), read, new Date());
            read = !read;
        }
        feed = new RSSFeedContent("name", "objectId", "description", new URL("http://www.topcoder.com/"), new Date(),
                entries);
        newTitle = "new title";
        newEntries = new RSSFeedContentEntry[8];
        for (int i = 0; i < newEntries.length; i++) {
            newEntries[i] = new RSSFeedContentEntry("new name:" + i, "new objectId:" + i, "new description:" + i,
                    new URL("http://www.topcoder.com/"), read, new Date());
            read = !read;
        }

        newFeed = new RSSFeedContent("new name", "new objectId", "new description", new URL(
                "http://forums.topcoder.com/"), new Date(), newEntries);
        emptyFeed = new RSSFeedContent("new name", "new objectId", "new description", new URL(
                "http://forums.topcoder.com/"), new Date());
        super.setUp();
    }

    /**
     * <p>
     * Tear down the environment.
     * </p>
     *
     * @throws Exception
     *             exception to j2meunit.
     */
    protected void tearDown() throws Exception {
        title = null;
        newTitle = null;
        feed = null;
        newFeed = null;
        emptyFeed = null;
        feedList = null;
        super.tearDown();
    }


    /**
     * <p>
     * Test constructor method <code>ViewFeedList(String, RSSFeedContent)</code> for basic. instance should be
     * created, should be instance of FilterList.
     * </p>
     */
    public void testConstructor_basic() {
        feedList = new ViewFeedList(title, feed);
        assertNotNull("instance of ViewFeedList.", feedList);
        assertTrue("should be instance of FilterList.", feedList instanceof FilterList);
    }

    /**
     * <p>
     * Test constructor method <code>ViewFeedList(String, RSSFeedContent)</code> for failure. with null
     * feed,IllegalArgumentException expected.
     * </p>
     */
    public void testConstructor_failure_nullfeed() {
        try {
            feedList = new ViewFeedList(title, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor method <code>ViewFeedList(String, RSSFeedContent)</code> for accuracy. with null title,
     * instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_nulltitle() {
        feedList = new ViewFeedList(null, feed);
        // feedId should be correct
        assertEquals("feedId", feed.getObjectId(), feedList.getFeedId());
        // title should be null
        assertNull("title", feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", entries.length, model.getSize());
        // all the entry should be added to the model.
        for (int i = 0; i < entries.length; i++) {
            FilterListEntry entry = model.get(i);
            // should be four columns
            assertEquals("column size", 4, entry.getColumnSize());
            // the columns should be set correctly
            assertEquals("column 0", entries[i].getName(), entry.getColumn(0));
            assertEquals("column 1", entries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("column 2", entries[i].getObjectId(), entry.getColumn(2));
            assertEquals("column 3", new Boolean(entries[i].isRead()).toString(), entry.getColumn(3));
        }
    }

    /**
     * <p>
     * Test constructor method <code>ViewFeedList(String, RSSFeedContent)</code> for accuracy. with not null title,
     * not empty feed, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_notemptyfeed() {
        feedList = new ViewFeedList(title, feed);
        // feedId should be correct
        assertEquals("feedId", feed.getObjectId(), feedList.getFeedId());
        // title should be correct
        assertEquals("title", title, feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", entries.length, model.getSize());
        // all the entry should be added to the model.
        for (int i = 0; i < entries.length; i++) {
            FilterListEntry entry = model.get(i);
            // should be four columns
            assertEquals("column size", 4, entry.getColumnSize());
            // the columns should be set correctly
            assertEquals("column 0", entries[i].getName(), entry.getColumn(0));
            assertEquals("column 1", entries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("column 2", entries[i].getObjectId(), entry.getColumn(2));
            assertEquals("column 3", new Boolean(entries[i].isRead()).toString(), entry.getColumn(3));
        }
    }

    /**
     * <p>
     * Test constructor method <code>ViewFeedList(String, RSSFeedContent)</code> for accuracy. with not null title,
     * empty feed, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_emptyfeed() {
        feedList = new ViewFeedList(title, emptyFeed);

        // feedId should be correct
        assertEquals("feedId", emptyFeed.getObjectId(), feedList.getFeedId());
        // title should be correct
        assertEquals("title", title, feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", 0, model.getSize());
    }

    /**
     * <p>
     * Test method <code>setFeed(String, RSSFeedContent)</code> for failure. with null feed,IllegalArgumentException
     * expected.
     * </p>
     */
    public void testSetFeed_failure_nullfeed() {
        feedList = new ViewFeedList(title, feed);
        try {
            feedList.setFeed(newTitle, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test method <code>setFeed(String, RSSFeedContent)</code> for accuracy. with null title, instance should be
     * created, properties should be set correctly.
     * </p>
     */
    public void testSetFeed_accuracy_nulltitle() {
        feedList = new ViewFeedList(title, feed);
        // set with null title
        feedList.setFeed(null, newFeed);
        // feedId should be correct
        assertEquals("feedId", newFeed.getObjectId(), feedList.getFeedId());
        // title should be null
        assertNull("title", feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", newEntries.length, model.getSize());
        // all the entry should be added to the model.
        for (int i = 0; i < newEntries.length; i++) {
            FilterListEntry entry = model.get(i);
            // should be four columns
            assertEquals("column size", 4, entry.getColumnSize());
            // the columns should be set correctly
            assertEquals("column 0", newEntries[i].getName(), entry.getColumn(0));
            assertEquals("column 1", newEntries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("column 2", newEntries[i].getObjectId(), entry.getColumn(2));
            assertEquals("column 3", new Boolean(newEntries[i].isRead()).toString(), entry.getColumn(3));
        }
    }

    /**
     * <p>
     * Test method <code>setFeed(String, RSSFeedContent)</code> for accuracy. with not null title, not empty feed,
     * instance should be created, properties should be set correctly.
     * </p>
     */
    public void testSetFeed_accuracy_notemptyfeed() {
        feedList = new ViewFeedList(title, feed);
        // set with not empty feed
        feedList.setFeed(newTitle, newFeed);
        // feedId should be correct
        assertEquals("feedId", newFeed.getObjectId(), feedList.getFeedId());
        // title should be correct
        assertEquals("title", newTitle, feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", newEntries.length, model.getSize());
        // all the entry should be added to the model.
        for (int i = 0; i < newEntries.length; i++) {
            FilterListEntry entry = model.get(i);
            // should be four columns
            assertEquals("column size", 4, entry.getColumnSize());
            // the columns should be set correctly
            assertEquals("column 0", newEntries[i].getName(), entry.getColumn(0));
            assertEquals("column 1", newEntries[i].getUpdatedDate().toString(), entry.getColumn(1));
            assertEquals("column 2", newEntries[i].getObjectId(), entry.getColumn(2));
            assertEquals("column 3", new Boolean(newEntries[i].isRead()).toString(), entry.getColumn(3));
        }
    }

    /**
     * <p>
     * Test method <code>setFeed(String, RSSFeedContent)</code> for accuracy. with not null title, empty feed,
     * properties should be set correctly.
     * </p>
     */
    public void testSetFeed_accuracy_emptyfeed() {
        feedList = new ViewFeedList(title, feed);
        // set with empty feed.
        feedList.setFeed(newTitle, emptyFeed);
        // feedId should be correct
        assertEquals("feedId", emptyFeed.getObjectId(), feedList.getFeedId());
        // title should be correct
        assertEquals("title", newTitle, feedList.getTitle());
        // model should be correct
        FilterListModel model = feedList.getFilterListModel();
        assertEquals("size of model.", 0, model.getSize());
    }

    /**
     * <p>
     * Test method <code>updateUI()</code> for accuracy. with not empty feed, string and font of the item of list
     * should be set correctly.
     * </p>
     */
    public void testUpdateUI_accuracy_notemptyfeed() {
        feedList = new ViewFeedList(title, feed);
        feedList.updateUI();
        // check the size of list
        assertEquals("size of list.", entries.length, feedList.size());

        FilterListModel model = feedList.getFilterListModel();
        for (int i = 0; i < feedList.size(); i++) {
            FilterListEntry entry = model.get(i);
            // the string should be correct
            String name = entry.getColumn(0) + "-" + entry.getColumn(1);
            assertTrue("string", feedList.getString(i).endsWith(name));
            // the font should be correct
            boolean read = "true".equals(entry.getColumn(3));
            assertEquals("font", read ? Font.getDefaultFont() : Font.getFont(Font.STYLE_BOLD), feedList.getFont(i));
        }
    }

    /**
     * <p>
     * Test method <code>updateUI()</code> for accuracy. with empty feed, size of list should be 0.
     * </p>
     */
    public void testUpdateUI_accuracy_emptyfeed() {
        feedList = new ViewFeedList(title, emptyFeed);
        feedList.updateUI();

        // size of the list should be 0
        assertEquals("size of list.", 0, feedList.size());
    }

    /**
     * <p>
     * Test method <code>getSelectedFeedEntryId()</code> for accuracy. objectId of the last item with read value
     * 'false' should be return.
     * </p>
     */
    public void testGetSelectedFeedEntryId_accuracy() {
        feedList = new ViewFeedList(title, feed);
        // the last item with flag value of read 'false' should be the selected entry
        FilterListModel model = feedList.getFilterListModel();
        for (int i = model.getSize() - 1; i >= 0; i--) {
            if ("false".equals(model.get(i).getColumn(3))) {
                assertEquals("selectedFeedEntryId", entries[i].getObjectId(), feedList.getSelectedFeedEntryId());
                return;
            }
        }
    }

    /**
     * <p>
     * Test method <code>getSelectedFeedEntryId()</code> for failure. with no element in the list selected.
     * ,IllegalStateException expected.
     * </p>
     */
    public void testGetSelectedFeedEntryId_IAE() {
        feed = new RSSFeedContent("RSSFeedContent", "objectId", "description",
            new URL("http://www.topcoder.com"), new Date(), new RSSFeedContentEntry[0]);
        feedList = new ViewFeedList("title", feed);
        try {
            feedList.getSelectedFeedEntryId();
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test method <code>setSelectedFeedEntryStatus(boolean)</code> for accuracy. with the read values true, set to
     * default font.
     * </p>
     */
    public void testSetSelectedFeedEntryStatus_accuracy_true() {
        feedList = new ViewFeedList(title, feed);

        // the last item with flag value of read 'false' should be the selected entry
        FilterListModel model = feedList.getFilterListModel();
        int i = model.getSize() - 1;
        for (; i >= 0; i--) {
            if ("false".equals(model.get(i).getColumn(3))) {
                assertEquals("selectedFeedEntryId", entries[i].getObjectId(), feedList.getSelectedFeedEntryId());
                break;
            }
        }

        FilterListModel oldModel = feedList.getFilterListModel();
        // before set
        assertEquals("model size before toggle", entries.length, oldModel.getSize());
        // set the read to true
        feedList.setSelectedFeedEntryStatus(true);
        FilterListModel newModel = feedList.getFilterListModel();
        // after set
        assertEquals("model size after toggle", entries.length, newModel.getSize());
        // name, updateDate, objectId should be equals
        for (int j = 0; j < oldModel.getSize(); j++) {
            assertEquals("name", oldModel.get(j).getColumn(0), newModel.get(j).getColumn(0));
            assertEquals("updateDate", oldModel.get(j).getColumn(1), newModel.get(j).getColumn(1));
            assertEquals("objectId", oldModel.get(j).getColumn(2), newModel.get(j).getColumn(2));
            // the read should be set to false, in index i.
            if (j == i) {
                assertEquals("read", "true", newModel.get(j).getColumn(3));
            } else {
                assertEquals("read", oldModel.get(j).getColumn(3), newModel.get(j).getColumn(3));
            }
        }
    }

    /**
     * <p>
     * Test method <code>setSelectedFeedEntryStatus(boolean)</code> for accuracy. with the read values false, set to
     * bold font.
     * </p>
     */
    public void testSetSelectedFeedEntryStatus_accuracy_false() {
        feedList = new ViewFeedList(title, feed);

        // the last item with flag value of read 'false' should be the selected entry
        FilterListModel model = feedList.getFilterListModel();
        int i = model.getSize() - 1;
        for (; i >= 0; i--) {
            if ("false".equals(model.get(i).getColumn(3))) {
                assertEquals("selectedFeedEntryId", entries[i].getObjectId(), feedList.getSelectedFeedEntryId());
                break;
            }
        }
        FilterListModel oldModel = feedList.getFilterListModel();
        // before set
        assertEquals("model size before toggle", entries.length, oldModel.getSize());
        // set the read to true
        feedList.setSelectedFeedEntryStatus(false);
        FilterListModel newModel = feedList.getFilterListModel();
        // after set
        assertEquals("model size after toggle", entries.length, newModel.getSize());
        // name, updateDate, objectId should be equals
        for (int j = 0; j < oldModel.getSize(); j++) {
            assertEquals("name", oldModel.get(j).getColumn(0), newModel.get(j).getColumn(0));
            assertEquals("updateDate", oldModel.get(j).getColumn(1), newModel.get(j).getColumn(1));
            assertEquals("objectId", oldModel.get(j).getColumn(2), newModel.get(j).getColumn(2));
            // the read should be set to false, in index i.
            if (j == i) {
                assertEquals("read", "false", newModel.get(j).getColumn(3));
            } else {
                assertEquals("read", oldModel.get(j).getColumn(3), newModel.get(j).getColumn(3));
            }
        }
    }

    /**
     * <p>
     * Test method <code>getFeedId()</code> for accuracy. field feedId should be return.
     * </p>
     */
    public void testGetFeedId() {
        feedList = new ViewFeedList(title, feed);
        assertEquals("feedId", feed.getObjectId(), feedList.getFeedId());
    }

    /**
     * <p>
     * test method <code>sortByName(boolean)</code> for accuracy. with isAscending values true, the result should be
     * correct.
     * </p>
     */
    public void testSortByName_accuracy_true() {
        feedList = new ViewFeedList(title, feed);
        // before sort
        FilterListModel oldModel = feedList.getFilterListModel();
        assertEquals("model size before sort", entries.length, oldModel.getSize());
        feedList.sortByName(true);
        // after sort
        FilterListModel newModel = feedList.getFilterListModel();
        assertEquals("model size after sort", entries.length, newModel.getSize());
    }

    /**
     * <p>
     * Test method <code>sortByName(boolean)</code> for accuracy. with isAscending values false, the result should be
     * correct.
     * </p>
     */
    public void testSortByName_accuracy_false() {
        feedList = new ViewFeedList(title, feed);
        FilterListModel model = feedList.getFilterListModel();
        // before sort
        assertEquals("model size before sort", entries.length, model.getSize());
        feedList.sortByName(false);
        // after sort
        assertEquals("model size after sort", entries.length, model.getSize());
    }

    /**
     * <p>
     * Test method <code>sortByDate(boolean)</code> for accuracy. with isAscending values true, the result should be
     * correct.
     * </p>
     */
    public void testSortByDate_accuracy_true() {
        feedList = new ViewFeedList(title, feed);
        FilterListModel model = feedList.getFilterListModel();
        // before sort
        assertEquals("model size before sort", entries.length, model.getSize());
        feedList.sortByDate(true);
        // after sort
        assertEquals("model size after sort", entries.length, model.getSize());
    }

    /**
     * <p>
     * Test method <code>sortByDate(boolean)</code> for accuracy. with isAscending values false, the result should be
     * correct.
     * </p>
     */
    public void testSortByDate_accuracy_false() {
        feedList = new ViewFeedList(title, feed);
        FilterListModel model = feedList.getFilterListModel();
        // before sort
        assertEquals("model size before sort", entries.length, model.getSize());
        feedList.sortByDate(false);
        // after sort
        assertEquals("model size after sort", entries.length, model.getSize());
    }

    /**
     * <p>
     * Test method <code>toggleAll()</code> for accuracy. flag read of all the entries in the model should be changed.
     * </p>
     */
    public void testToggleAll_accuracy() {
        feedList = new ViewFeedList(title, feed);
        FilterListModel oldModel = feedList.getFilterListModel();
        // before toggle
        assertEquals("model size before toggle", entries.length, oldModel.getSize());
        feedList.toggleAll();
        FilterListModel newModel = feedList.getFilterListModel();
        // after toggle
        assertEquals("model size after toggle", entries.length, newModel.getSize());
        // name, updateDate, objectId should be equals, read not
        for (int i = 0; i < oldModel.getSize(); i++) {
            assertEquals("name", oldModel.get(i).getColumn(0), newModel.get(i).getColumn(0));
            assertEquals("updateDate", oldModel.get(i).getColumn(1), newModel.get(i).getColumn(1));
            assertEquals("objectId", oldModel.get(i).getColumn(2), newModel.get(i).getColumn(2));
            assertEquals("read", "true".equals(oldModel.get(i).getColumn(3)) ? "false" : "true", newModel.get(i)
                    .getColumn(3));
        }
    }

    /**
     * <p>
     * Test method <code>getLogger()</code> for accuracy. class field logger should be return.
     * </p>
     */
    public void testGetLogger() {
        assertNotNull("logger", ViewFeedList.getLogger());
    }

    /**
     * <p>
     * Return test suite containing this class' tests.
     * </p>
     *
     * @return test suite containing this class' tests.
     */
    public Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ViewFeedListTest("testConstructor_basic", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testConstructor_basic();
            }
        }));
        suite.addTest(new ViewFeedListTest("testConstructor_accuracy_emptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testConstructor_accuracy_emptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testConstructor_accuracy_notemptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testConstructor_accuracy_notemptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testConstructor_accuracy_nulltitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testConstructor_accuracy_nulltitle();
            }
        }));
        suite.addTest(new ViewFeedListTest("testConstructor_failure_nullfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testConstructor_failure_nullfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testGetFeedId", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testGetFeedId();
            }
        }));
        suite.addTest(new ViewFeedListTest("testGetLogger", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testGetLogger();
            }
        }));
        suite.addTest(new ViewFeedListTest("testGetSelectedFeedEntryId_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testGetSelectedFeedEntryId_accuracy();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetFeed_accuracy_emptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetFeed_accuracy_emptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetFeed_accuracy_notemptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetFeed_accuracy_notemptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetFeed_accuracy_nulltitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetFeed_accuracy_nulltitle();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetFeed_failure_nullfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetFeed_failure_nullfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSortByDate_accuracy_false", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSortByDate_accuracy_false();
            }
        }));
        suite.addTest(new ViewFeedListTest("testsortByDate_accuracy_true", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSortByDate_accuracy_true();
            }
        }));
        suite.addTest(new ViewFeedListTest("testsortByName_accuracy_false", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSortByName_accuracy_false();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSortByName_accuracy_true", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSortByName_accuracy_true();
            }
        }));
        suite.addTest(new ViewFeedListTest("testtoggleAll_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testToggleAll_accuracy();
            }
        }));
        suite.addTest(new ViewFeedListTest("testupdateUI_accuracy_emptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testUpdateUI_accuracy_emptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testupdateUI_accuracy_notemptyfeed", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testUpdateUI_accuracy_notemptyfeed();
            }
        }));
        suite.addTest(new ViewFeedListTest("testGetSelectedFeedEntryId_IAE", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testGetSelectedFeedEntryId_IAE();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetSelectedFeedEntryStatus_accuracy_false", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetSelectedFeedEntryStatus_accuracy_false();
            }
        }));
        suite.addTest(new ViewFeedListTest("testSetSelectedFeedEntryStatus_accuracy_true", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ViewFeedListTest) test).testSetSelectedFeedEntryStatus_accuracy_true();
            }
        }));
        return suite;
    }
}

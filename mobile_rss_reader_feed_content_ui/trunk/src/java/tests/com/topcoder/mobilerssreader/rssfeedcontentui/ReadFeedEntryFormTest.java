/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import java.util.Date;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

import org.apache.log4j.LogActivator;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

/**
 * <p>
 * Unit tests for class <code>ReadFeedEntryForm</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReadFeedEntryFormTest extends TestCase {
    /**
     * <p>
     * Instance of <code>ReadFeedEntryForm</code> for test.
     * </p>
     */
    private ReadFeedEntryForm readForm;
    /**
     * <p>
     * Title of the form for test.
     * </p>
     */
    private String title;
    /**
     * <p>
     * Instance of <code>RSSFeedContentEntry</code> for test.
     * </p>
     */
    private RSSFeedContentEntry feedEntry;

    /**
     * <p>
     * Instance of <code>RSSFeedContentEntry</code> for test.
     * </p>
     */
    private RSSFeedContentEntry newFeedEntry;
    /**
     * <p>
     * The header image of the form for test.
     * </p>
     */
    private Image headerImage;

    /**
     * <p>
     * The default constructor.
     * </p>
     */
    public ReadFeedEntryFormTest() {
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
    public ReadFeedEntryFormTest(String testName, TestMethod testMethod) {
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
        feedEntry = new RSSFeedContentEntry("name", "objectId", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        newFeedEntry = new RSSFeedContentEntry("new name", "new objectId", "new description", new URL(
                "http://forums.topcoder.com/"), true, new Date());
        headerImage = null;

        headerImage = TestHepler.getImage("/topcoder.gif");

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
        feedEntry = null;
        newFeedEntry = null;
        headerImage = null;
        readForm = null;
    }

    /**
     * <p>
     * Test constructor method <code>ReadFeedEntryForm(String, RSSFeedContentEntry, Image)</code> for basic. with
     * validated parameters instance should be created, not <code>null</code> and extends <code>Form</code>.
     * </p>
     */
    public void testConstructor_basic() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);
        assertNotNull("instance of ReadFeedEntryForm", readForm);
        assertTrue("should be instance of Form.", readForm instanceof Form);
    }

    /**
     * <p>
     * Test constructor method <code>ReadFeedEntryForm(String, RSSFeedContentEntry, Image)</code> for failure. with
     * <code>null</code> feedEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testConstructor_failure_nullfeedEntry() {
        try {
            readForm = new ReadFeedEntryForm(title, null, headerImage);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor method <code>ReadFeedEntryForm(String, RSSFeedContentEntry, Image)</code> for accuracy. with
     * not null parameters, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);
        // title should be correct
        assertEquals("title", title, readForm.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), readForm.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(readForm, headerImage));
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getDescription()));
    }

    /**
     * <p>
     * Test constructor method <code>ReadFeedEntryForm(String, RSSFeedContentEntry, Image)</code> for accuracy. with
     * null image, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_nullImage() {
        readForm = new ReadFeedEntryForm(title, feedEntry, null);
        // title should be correct
        assertEquals("title", title, readForm.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), readForm.getFeedEntryId());
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getDescription()));

    }

    /**
     * <p>
     * Test constructor method <code>ReadFeedEntryForm(String, RSSFeedContentEntry, Image)</code> for accuracy. with
     * null title, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_nullTitle() {
        readForm = new ReadFeedEntryForm(null, feedEntry, headerImage);
        // title should be null
        assertNull("title", readForm.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), readForm.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(readForm, headerImage));
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(readForm, feedEntry
                .getDescription()));

    }

    /**
     * <p>
     * Test constructor method <code>setFeedEntry(String, RSSFeedContentEntry)</code> for failure. with
     * <code>null</code> feedEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetFeedEntry_failure_nullfeedEntry() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);
        try {
            readForm.setFeedEntry(title, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test method <code>setFeedEntry(String, RSSFeedContentEntry)</code> for accuracy. with not null parameters,
     * properties should be set correctly.
     * </p>
     */
    public void testSetFeedEntry_accuracy() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);
        // set a new feedEntry
        readForm.setFeedEntry("new title", newFeedEntry);
        // new title should be correct
        assertEquals("title", "new title", readForm.getTitle());
        // new feedEntryId should be correct
        assertEquals("feedEntryId", newFeedEntry.getObjectId(), readForm.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(readForm, headerImage));
        // new feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(readForm, newFeedEntry
                .getName()
                + "-" + newFeedEntry.getUpdatedDate() + "\n"));
        // new feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(readForm, newFeedEntry
                .getDescription()));
    }

    /**
     * <p>
     * Test method <code>setFeedEntry(String, RSSFeedContentEntry)</code> for accuracy. with null title, properties
     * should be set correctly.
     * </p>
     */
    public void testSetFeedEntry_accuracy_nullTitle() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);

        // set null title and a new feedEntry
        readForm.setFeedEntry(null, newFeedEntry);
        // title should be null
        assertNull("title", readForm.getTitle());
        // new feedEntryId should be correct
        assertEquals("feedEntryId", newFeedEntry.getObjectId(), readForm.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(readForm, headerImage));
        // new feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(readForm, newFeedEntry
                .getName()
                + "-" + newFeedEntry.getUpdatedDate() + "\n"));
        // new feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(readForm, newFeedEntry
                .getDescription()));
    }

    /**
     * <p>
     * Test method <code>getFeedEntryId()</code> for accuracy. objectId of feedEntry should be return.
     * </p>
     */
    public void testGetFeedEntryId() {
        readForm = new ReadFeedEntryForm(title, feedEntry, headerImage);
        assertEquals("feedEntryId", feedEntry.getObjectId(), readForm.getFeedEntryId());
    }

    /**
     * <p>
     * Test method <code>getLogger()</code> for accuracy. class field logger should be return.
     * </p>
     */
    public void testGetLogger() {
        assertNotNull("logger", ReadFeedEntryForm.getLogger());
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
        suite.addTest(new ReadFeedEntryFormTest("testConstructor_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testConstructor_accuracy();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testConstructor_accuracy_nullImage", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testConstructor_accuracy_nullImage();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testConstructor_accuracy_nullTitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testConstructor_accuracy_nullTitle();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testConstructor_basic", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testConstructor_basic();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testConstructor_failure_nullfeedEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testConstructor_failure_nullfeedEntry();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testGetFeedEntryId", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testGetFeedEntryId();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testGetLogger", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testGetLogger();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testSetFeedEntry_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testSetFeedEntry_accuracy();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testSetFeedEntry_accuracy_nullTitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testSetFeedEntry_accuracy_nullTitle();
            }
        }));
        suite.addTest(new ReadFeedEntryFormTest("testSetFeedEntry_failure_nullfeedEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReadFeedEntryFormTest) test).testSetFeedEntry_failure_nullfeedEntry();
            }
        }));

        return suite;
    }
}

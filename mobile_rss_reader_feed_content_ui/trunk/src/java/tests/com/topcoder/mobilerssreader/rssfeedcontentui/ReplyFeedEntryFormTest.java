/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import java.util.Date;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

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
 * Unit tests for class <code>ReplyFeedEntryForm</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReplyFeedEntryFormTest extends TestCase {
    /**
     * <p>
     * Instance of <code>ReplyFeedEntryForm</code> for test.
     * </p>
     */
    private ReplyFeedEntryForm test;
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
     * The max size of the textField for test.
     * </p>
     */
    private int maxSize;

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
     *
     * @param testName
     *            the name of the case.
     * @param testMethod
     *            the method to test.
     */
    public ReplyFeedEntryFormTest(String testName, TestMethod testMethod) {
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
        maxSize = 100;
        title = "title";
        feedEntry = new RSSFeedContentEntry("name", "objectId", "description", new URL("http://www.topcoder.com/"),
                true, new Date());
        newFeedEntry = new RSSFeedContentEntry("new name", "new objectId", "new description", new URL(
                "http://forums.topcoder.com/"), true, new Date());
        headerImage = TestHepler.getImage("/topcoder.gif");
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
        feedEntry = null;
        newFeedEntry = null;
        headerImage = null;
        test = null;
        super.tearDown();
    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for basic.
     * with validated parameters instance should be created, not <code>null</code> and extends <code>Form</code>.
     * </p>
     */
    public void testConstructor_basic() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        assertNotNull("instance of ReplyFeedEntryForm", test);
        assertTrue("should be instance of Form.", test instanceof Form);
    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for failure.
     * with <code>null</code> feedEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testConstructor_failure_nullfeedEntry() {
        try {
            test = new ReplyFeedEntryForm(title, null, headerImage, maxSize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for failure.
     * with maxSize 0 and less , IllegalArgumentException should be thrown.
     * </p>
     */
    public void testConstructor_failure_invalidmaxSize() {
        try {
            test = new ReplyFeedEntryForm(title, feedEntry, headerImage, 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            test = new ReplyFeedEntryForm(title, feedEntry, headerImage, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for accuracy.
     * with not null parameters, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        // title should be correct
        assertEquals("title", title, test.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), test.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(test, headerImage));
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(test, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // textField should be appended
        assertTrue("textField should be appended.", TestHepler.checkTextFieldExist(test, "Comments:"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler
                .checkStringExist(test, feedEntry.getDescription()));
    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for accuracy.
     * with null image, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_nullImage() {
        test = new ReplyFeedEntryForm(title, feedEntry, null, maxSize);
        // title should be correct
        assertEquals("title", title, test.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), test.getFeedEntryId());
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(test, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // textField should be appended
        assertTrue("textField should be appended.", TestHepler.checkTextFieldExist(test, "Comments:"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler
                .checkStringExist(test, feedEntry.getDescription()));

    }

    /**
     * <p>
     * Test constructor method <code>ReplyFeedEntryForm(String, RSSFeedContentEntry, Image, int)</code> for accuracy.
     * with null title, instance should be created, properties should be set correctly.
     * </p>
     */
    public void testConstructor_accuracy_nullTitle() {
        test = new ReplyFeedEntryForm(null, feedEntry, headerImage, maxSize);
        // title should be null
        assertNull("title", test.getTitle());
        // feedEntryId should be correct
        assertEquals("feedEntryId", feedEntry.getObjectId(), test.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(test, headerImage));
        // feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(test, feedEntry
                .getName()
                + "-" + feedEntry.getUpdatedDate() + "\n"));
        // textField should be appended
        assertTrue("textField should be appended.", TestHepler.checkTextFieldExist(test, "Comments:"));
        // feed description should be appended
        assertTrue("feed description should be appended.", TestHepler
                .checkStringExist(test, feedEntry.getDescription()));

    }

    /**
     * <p>
     * Test constructor method <code>setFeedEntry(String, RSSFeedContentEntry)</code> for failure. with
     * <code>null</code> feedEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testSetFeedEntry_failure_nullfeedEntry() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        try {
            test.setFeedEntry(title, null);
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
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        // set a new feedEntry
        test.setFeedEntry("new title", newFeedEntry);
        // new title should be correct
        assertEquals("title", "new title", test.getTitle());
        // new feedEntryId should be correct
        assertEquals("feedEntryId", newFeedEntry.getObjectId(), test.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(test, headerImage));
        // new feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(test, newFeedEntry
                .getName()
                + "-" + newFeedEntry.getUpdatedDate() + "\n"));
        // textField should be appended
        assertTrue("textField should be appended.", TestHepler.checkTextFieldExist(test, "Comments:"));
        // new feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(test, newFeedEntry
                .getDescription()));
    }

    /**
     * <p>
     * Test method <code>setFeedEntry(String, RSSFeedContentEntry)</code> for accuracy. with null title, properties
     * should be set correctly.
     * </p>
     */
    public void testSetFeedEntry_accuracy_nullTitle() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);

        // set null title and a new feedEntry
        test.setFeedEntry(null, newFeedEntry);
        // title should be null
        assertNull("title", test.getTitle());
        // new feedEntryId should be correct
        assertEquals("feedEntryId", newFeedEntry.getObjectId(), test.getFeedEntryId());
        // headerImage should have be appended to the form
        assertTrue("headerImage should be appended.", TestHepler.checkImageExist(test, headerImage));
        // new feed name and updateDate should be appended
        assertTrue("feed name and updateDate should be appended.", TestHepler.checkStringExist(test, newFeedEntry
                .getName()
                + "-" + newFeedEntry.getUpdatedDate() + "\n"));
        // textField should be appended
        assertTrue("textField should be appended.", TestHepler.checkTextFieldExist(test, "Comments:"));
        // new feed description should be appended
        assertTrue("feed description should be appended.", TestHepler.checkStringExist(test, newFeedEntry
                .getDescription()));
    }

    /**
     * <p>
     * Test method <code>getFeedEntry()</code> for accuracy. objectId of feedEntry should be return.
     * </p>
     */
    public void testGetFeedEntryId() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        assertEquals("feedEntryId", feedEntry.getObjectId(), test.getFeedEntryId());
    }

    /**
     * <p>
     * Test method <code>getLogger()</code> for accuracy. class field logger should be return.
     * </p>
     */
    public void testGetLogger() {
        assertNotNull("logger", ReplyFeedEntryForm.getLogger());
    }

    /**
     * Test the method of clear. The postMessage should be empty while calling clear at any time.
     */
    public void testClear() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        TextField field = getTextField(test, "Comments:");
        String message = "message";
        field.setString(message);
        assertEquals("the message is not null", message, test.getPostMessage());
        test.clear();
        assertEquals("the message should be clear", "", test.getPostMessage());
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
     * <p>
     * Test method <code>getPostMessage()</code> for accuracy. the string of the textField should be return correctly.
     * </p>
     */
    public void testGetPostMessage() {
        test = new ReplyFeedEntryForm(title, feedEntry, headerImage, maxSize);
        assertEquals("string of textField", "", test.getPostMessage());
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
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_accuracy();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_accuracy_nullImage", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_accuracy_nullImage();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_accuracy_nullTitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_accuracy_nullTitle();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_basic", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_basic();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_failure_nullfeedEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_failure_nullfeedEntry();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testConstructor_failure_invalidmaxSize", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testConstructor_failure_invalidmaxSize();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testGetFeedEntryId", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testGetFeedEntryId();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testGetLogger", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testGetLogger();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testSetFeedEntry_accuracy", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testSetFeedEntry_accuracy();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testSetFeedEntry_accuracy_nullTitle", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testSetFeedEntry_accuracy_nullTitle();
            }
        }));
        suite.addTest(new ReplyFeedEntryFormTest("testSetFeedEntry_failure_nullfeedEntry", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testSetFeedEntry_failure_nullfeedEntry();
            }
        }));

        suite.addTest(new ReplyFeedEntryFormTest("testClear", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testClear();
            }
        }));

        suite.addTest(new ReplyFeedEntryFormTest("testGetPostMessage", new TestMethod() {
            public void run(TestCase test) throws Throwable {
                ((ReplyFeedEntryFormTest) test).testGetPostMessage();
            }
        }));
        return suite;
    }
}

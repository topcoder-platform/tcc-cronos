/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.failuretests;

import com.orpheus.plugin.firefox.*;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Iterator;
import java.lang.reflect.Field;



import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * Tests failure cases of FirefoxExtensionHelper class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionHelperTest extends TestCase {
    /** Represents the FirefoxExtensionHelper instance used for testing. */
    private FirefoxExtensionHelper testObject;

    /**
     * This variable is used to load the UIEventType in the typesafe enum map
     */
    private UIEventType testEvent = UIEventType.LOGIN_CLICK;

    /**
     * Sets up the test environment. The test instance is created.
     */
    protected void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failureConfig.xml");

        try {
            testObject = new FirefoxExtensionHelper();
            setPrivateField(testObject.getClass(), testObject, "clientWindow", new JsMockObject());
            testObject.initialize("com.orpheus.plugin.firefox.FirefoxExtensionHelper");

            // add the listener for testing
            UIEventListener listener = TestHelper.BuildListener();
            testObject.addEventListener(listener);
        }
        catch(Exception e)
        {
        }
        finally
        {
            clearConfigManager();
        }
    }

    private void clearConfigManager() throws Exception  {
        ConfigManager manager = ConfigManager.getInstance();
        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * clears the Config Manager namespace
     */
    protected void tearDown() throws Exception  {
        clearConfigManager();
    }

    /**
     * Tests the method pageChanged(String) when the given newPage represents an invalid url,
     * FirefoxClientException is expected.
     */
    public void testPageChanged_InvalidNewPage() throws Exception {
        try {
            testObject.pageChanged("www.topcoder.com");
            fail("FirefoxClientException should be thrown.");
        } catch (Exception e) {

        }
    }

    /**
     * Tests addEventListener(UIEventListener) when the listener is null
     */
    public void testAddEventListener_NullListener() {
        try {
            testObject.addEventListener(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests removeEventListener(UIEventListener) when the listener is null
     */
    public void testRemoveEventListener_NullListener() {
        try {
            testObject.removeEventListener(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Tests setWorkingGameID(long) when the id is negative
     */
    public void testSetWorkingGameID_NegativeId() throws Exception {
        try {
            testObject.setWorkingGameID(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests setCurrentTargetID(String, int) when the id is null
     */
    public void testSetCurrentTargetID_NullId() throws Exception {
        try {
            testObject.setCurrentTargetID(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests setCurrentTargetID(String, int) when the id is empty
     */
    public void testSetCurrentTargetID_EmptyId() throws Exception {
        try {
            testObject.setCurrentTargetID(" ", 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests setCurrentTargetID(String, int) when the sequenceNumber is negative
     */
    public void testSetCurrentTargetID_NegativeSequenceNumber() throws Exception {
        try {
            testObject.setCurrentTargetID("id", -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests setPollTime(int) when the pollTime is not positive
     */
    public void testSetPollTime_InvalidPollTime() throws Exception {
        try {
            testObject.successfulLogIn();
            testObject.setPollTime(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests currentTargetTest(String) when the element is not a valid DOM Element
     */
    public void testCurrentTargetTest_InvalidElement() throws Exception {
        try {
            String element = "<invalid/>";
            testObject.currentTargetTest(element);
            fail("FirefoxClientException should be thrown.");
        } catch (Exception e) {

        }
    }

    /**
     * <p>
     * Sets the value of a private field in the given class. The field has the given name. The value is retrieved from
     * the given instance.
     * </p>
     *
     * @param type the class which the private field belongs to.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be setted.
     * @param value the value be given to the field.
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }
}

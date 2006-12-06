/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.orpheus.plugin.firefox.failuretests;

import junit.framework.TestCase;
import com.orpheus.plugin.firefox.*;
import com.orpheus.plugin.firefox.eventlisteners.JavascriptUIEventListener;


/**
 * Tests the failure cases for the JavascriptUIEventListener class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JavascriptUIEventListenerTest extends TestCase {
    /**
     * test instance
     */
    private JavascriptUIEventListener testObject;

    protected void setUp()  {
        testObject = new JavascriptUIEventListener();
    }

    /**
     * tests pageChanged(String) with a null parameter
     */
    public void testpageChanged_NullPage()  {
        try {
        testObject.pageChanged(null);
            fail("IllegalArgumentException should be thrown");
        }
        catch(IllegalArgumentException iae) {

        }
    }

    /**
     * tests workingGameUpdate(long) with a negative parameter
     */
    public void workingGameUpdate_NegativeId()  {
        try {
        testObject.workingGameUpdate(-1);
            fail("IllegalArgumentException should be thrown");
        }
        catch(IllegalArgumentException iae) {

        }
    }

    /**
     * tests addFunctionName(UIEventType, String) with a null parameter
     */
    public void addFunctionName_NullFunction()  {
        try {
        testObject.addFunctionName(UIEventType.LOGIN_CLICK, null);
            fail("IllegalArgumentException should be thrown");
        }
        catch(IllegalArgumentException iae) {

        }
    }
    /**
     * tests removeFunctionName(UIEventType, String) with a null parameter
     */
    public void removeFunctionName_NullFunction()  {
        try {
        testObject.removeFunctionName(UIEventType.LOGIN_CLICK, null);
            fail("IllegalArgumentException should be thrown");
        }
        catch(IllegalArgumentException iae) {

        }
    }
}

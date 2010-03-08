/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for <code>ValidationErrorRecord</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationErrorRecordUnitTest extends TestCase {

    /**
     * The instance used in testing.
     */
    private ValidationErrorRecord instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ValidationErrorRecord();
    }

    /**
     * Tears down the environment.
     *
     * @throws Exception to junit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for constructor. Verifies the new instance is created.
     */
    @Test
    public void testConstructor() {
        assertNotNull("unable to create instance", instance);
    }

    /**
     * Accuracy test for getPropertyName. Verifies the returned value is correct.
     */
    @Test
    public void test_getPropertyName_Accuracy() {
        assertNull("incorrect default value", instance.getPropertyName());
        String propertyName = "value";
        instance.setPropertyName(propertyName);
        assertEquals("incorrect value after setting", propertyName, instance.getPropertyName());
    }

    /**
     * Accuracy test for setPropertyName. Verifies the assigned value is correct.
     */
    @Test
    public void test_setPropertyName_Accuracy1() {
        String propertyName = "value";
        instance.setPropertyName(propertyName);
        assertEquals("incorrect value after setting", propertyName, instance.getPropertyName());
    }

    /**
     * Accuracy test for setPropertyName. Verifies that null can be assigned.
     */
    @Test
    public void test_setPropertyName_Accuracy2() {
        instance.setPropertyName(null);
        assertNull("incorrect value after setting", instance.getPropertyName());
    }

    /**
     * Accuracy test for getMessages. Verifies the returned value is correct.
     */
    @Test
    public void test_getMessages_Accuracy() {
        assertNull("incorrect default value", instance.getMessages());
        String[] messages = new String[] {"message1", "message2"};
        instance.setMessages(messages);
        assertSame("incorrect value after setting", messages, instance.getMessages());
        assertEquals("incorrect value for first message", messages[0], instance.getMessages()[0]);
        assertEquals("incorrect value for second message", messages[1], instance.getMessages()[1]);
    }

    /**
     * Accuracy test for setMessages. Verifies the assigned value is correct.
     */
    @Test
    public void test_setMessages_Accuracy1() {
        String[] messages = new String[] {"hot", "cold"};
        instance.setMessages(messages);
        assertSame("incorrect value after setting", messages, instance.getMessages());
        assertEquals("incorrect value for first message", messages[0], instance.getMessages()[0]);
        assertEquals("incorrect value for second message", messages[1], instance.getMessages()[1]);
    }

    /**
     * Accuracy test for setMessages. Verifies that null can be assigned.
     */
    @Test
    public void test_setMessages_Accuracy2() {
        instance.setMessages(null);
        assertNull("incorrect value after setting", instance.getMessages());
    }

}

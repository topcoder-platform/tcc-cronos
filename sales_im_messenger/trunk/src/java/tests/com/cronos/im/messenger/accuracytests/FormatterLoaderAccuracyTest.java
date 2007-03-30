/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.accuracytests;

import com.cronos.im.messenger.ChatMessageFormatter;
import com.cronos.im.messenger.FormatterLoader;

import junit.framework.TestCase;


/**
 * Tests the functionality for class <code>FormatterLoader</code>.
 *
 * @author lyt
 * @version 1.0
 */
public class FormatterLoaderAccuracyTest extends TestCase {
    /** An instance of <code>FormatterLoader</code> class for testing. */
    private FormatterLoader loader;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        AccuracyHelper.loadConfig("formatter_loader.xml");

        loader = FormatterLoader.getInstance();
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Test method for 'FormatterLoader.getInstance()'.
     */
    public void testGetInstance() {
        assertNotNull("Test method for 'FormatterLoader.getInstance()' failed.", FormatterLoader.getInstance());
    }

    /**
     * Test method for 'FormatterLoader.getRolePropertyName()'.
     *
     * @throws Exception to JUnit
     */
    public void testGetRolePropertyName() throws Exception {
        assertEquals("Test method for 'FormatterLoader.getRolePropertyName()' failed.", "manager",
            loader.getRolePropertyName());
    }

    /**
     * Test method for 'FormatterLoader.getUserPropertyName()'
     *
     * @throws Exception to JUnit
     */
    public void testGetUserPropertyName() throws Exception {
        assertEquals("Test method for 'FormatterLoader.getUserPropertyName()' failed.", "user_name",
            loader.getUserPropertyName());
    }

    /**
     * Test method for 'FormatterLoader.getChatMessageFormatter(String)'.
     *
     * @throws Exception to JUnit
     */
    public void testGetChatMessageFormatter() throws Exception {
        ChatMessageFormatter formatter = loader.getChatMessageFormatter("manager");
        assertNotNull("Test method for 'FormatterLoader.getChatMessageFormatter(String)'", formatter);
    }
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger.failuretests;

import junit.framework.TestCase;

import com.cronos.im.messenger.FormatterConfigurationException;
import com.cronos.im.messenger.FormatterLoader;

/**
 * Tests the {@link FormatterLoader} for failure.
 *
 * @author mittu
 * @version 1.0
 */
public class FormatterLoaderFailure extends TestCase {

    /**
     * Represents the FormatterLoader.
     */
    private FormatterLoader formatterLoader;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        formatterLoader = FormatterLoader.getInstance();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void tearDown() throws Exception {
        formatterLoader = null;
    }

    /**
     * <p>
     * Tests the getRolePropertyName method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Invokes the method without loading config files.
     * </p>
     *
     */
    public void testRolePropertyName() {
        try {
            formatterLoader.getRolePropertyName();
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the getUserPropertyName method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Invokes the method without loading config files.
     * </p>
     *
     */
    public void testUserPropertyName() {
        try {
            formatterLoader.getUserPropertyName();
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with role name as empty.
     * </p>
     *
     */
    public void testChatMessageFormatter1() {
        try {
            formatterLoader.getChatMessageFormatter("   ");
            fail("Should throw IllegalArgumentException");
        } catch (FormatterConfigurationException e) {
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with role name as null.
     * </p>
     *
     */
    public void testChatMessageFormatter() {
        try {
            formatterLoader.getChatMessageFormatter(null);
            fail("Should throw IllegalArgumentException");
        } catch (FormatterConfigurationException e) {
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with not loading the config file.
     * </p>
     *
     */
    public void testChatMessageFormatter2() {
        try {
            formatterLoader.getChatMessageFormatter("not_loaded_role");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with role name not loaded in config file.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testChatMessageFormatter3() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config_no_rolename.xml");
            formatterLoader.getChatMessageFormatter("not_loaded_role");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with user name formatter not loaded in config file.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testChatMessageFormatter4() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config_no_user_name_formatter.xml");
            formatterLoader.getChatMessageFormatter("manager");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with timestamp formatter not loaded in config file.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testChatMessageFormatter5() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config_no_timestamp_formatter.xml");
            formatterLoader.getChatMessageFormatter("manager");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with static chat text formatter not loaded in config file.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testChatMessageFormatter6() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config_no_static_chat text.xml");
            formatterLoader.getChatMessageFormatter("manager");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

    /**
     * <p>
     * Tests the getChatMessageFormatter method of <code>FormatterLoader</code>.
     * </p>
     *
     * <p>
     * Tests with dynamic chat text formatter not loaded in config file.
     * </p>
     *
     * @throws Exception
     *             to junit.
     *
     */
    public void testChatMessageFormatter7() throws Exception {
        try {
            FailureTestHelper.loadConfigFile("formatter_loader_config_no_dynamic_chat_text.xml");
            formatterLoader.getChatMessageFormatter("manager");
            fail("Should throw FormatterConfigurationException");
        } catch (FormatterConfigurationException e) {
            // expect
        } finally {
            FailureTestHelper.releaseConfigFiles();
        }
    }

}

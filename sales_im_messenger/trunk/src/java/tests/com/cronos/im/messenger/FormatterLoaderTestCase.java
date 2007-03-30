/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.topcoder.util.config.ConfigManager;
import junit.framework.TestCase;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Test the methods of <c>FormatterLoader</c> class for accuracy and failure cases.
 *
 * @author marius_neo
 * @version 1.0
 */
public class FormatterLoaderTestCase extends TestCase {
    /**
     * Represents a valid configuration file for loading namespaces requested by <c>FormatterLoader</c> class.
     */
    private final String formatterLoaderValidConfig = "test_files" + File.separator + "formatter_loader_config.xml";

    /**
     * Represents an invalid configuration namespace for <c>FormatterLoader</c> class
     * because it doesn't contain any required property.
     */
    private final String formatterLoaderInvalidConfigEmpty =
        "test_files" + File.separator + "formatter_loader_config_empty.xml";

    /**
     * Represents an invalid configuration namespace for <c>FormatterLoader</c> class
     * because it contains empty values for the properties.
     */
    private final String formatterLoaderInvalidConfigEmptyValues =
        "test_files" + File.separator + "formatter_loader_config_empty_values.xml";

    /**
     * Represents an invalid configuration file for loading namespaces
     * requested by <c>FormatterLoader</c> class because it contains invalid
     * configurations for chat message formatters.The value of
     * <c>chat_text_format.chat_text_dynamic_format</c> from the namespace used in
     * creating a ChatMessageFormatter doesn't exist and it is required.
     */
    private final String formatterLoaderConfigInvalidChatMessageFormatter =
        "test_files" + File.separator + "formatter_loader_config_invalid_chat_message_formatter.xml";

    /**
     * Represents the configuration namespace used by <c>FormatterLoader</c> to load role and user
     * property values.
     */
    private final String formatterLoaderNamespace = FormatterLoader.class.getName();

    /**
     * Represents the <c>FormatterLoader</c> instance used in tests.
     * It is initialized in <c>setUp()</c> method.
     */
    private FormatterLoader formatterLoader;

    /**
     * Setup test environment by initializing <c>formatterLoader</c> field.
     *
     * @throws Exception Any exception that is may appear in this method is thrown to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadXMLConfig(formatterLoaderValidConfig);

        formatterLoader = FormatterLoader.getInstance();
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception Any exception that may appear is thrown to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        // Clear the instace static field of FormatterLoader class by reflection.
        Field instanceField = FormatterLoader.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    /**
     * Tests <c>getInstance()</c> method for accuracy.
     */
    public void testGetInstance() {
        assertNotNull("formatterLoader was not created", this.formatterLoader);

        // See if the instance returned by the method is alwais the same.
        FormatterLoader formatterLoader = FormatterLoader.getInstance();
        assertSame("The FormatterLoader instances should be the same", this.formatterLoader, formatterLoader);
    }

    /**
     * Tests <c>getRolePropertyName()</c> method for accuracy.
     *
     * @throws Exception Any exception that may appear is thrown to JUnit.
     */
    public void testGetRolePropertyName() throws Exception {
        String roleName = formatterLoader.getRolePropertyName();
        //Retrieve the same property by using configManager and compare the results
        String roleName1 = ConfigManager.getInstance().getString(formatterLoaderNamespace, "role_name_key");
        assertEquals("The role names should be equal", roleName1, roleName);
    }

    /**
     * Tests the accuracy of <c>getRolePropertyName()</c> when the value of <c>role_name_key</c> property
     * is missing.
     *
     * @throws FormatterConfigurationException
     *          Is expected to be thrown.
     */
    public void testGetRolePropertyNameFailure() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.removeNamespace(formatterLoaderNamespace);
        TestHelper.loadXMLConfig(formatterLoaderInvalidConfigEmpty);

        try {
            formatterLoader.getRolePropertyName();

            fail("Should have thrown FormatterConfigurationException because of the missing property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }

        cm.removeNamespace(formatterLoaderNamespace);
        TestHelper.loadXMLConfig(formatterLoaderInvalidConfigEmptyValues);

        try {
            formatterLoader.getRolePropertyName();

            fail("Should have thrown FormatterConfigurationException because of the empty string property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }
    }

    /**
     * Tests <c>getUserPropertyName()</c> method for accuracy.
     *
     * @throws Exception Any exception that may appear is thrown to JUnit.
     */
    public void testGetUserPropertyName() throws Exception {
        String userName = formatterLoader.getUserPropertyName();
        //Retrieve the same property by using configManager and compare the results
        String userName1 = ConfigManager.getInstance().getString(formatterLoaderNamespace, "user_name_key");
        assertEquals("The user names should be equal", userName1, userName);
    }

    /**
     * Tests the accuracy of <c>getUserPropertyName()</c> when the value of <c>role_name_key</c> property
     * is missing.
     *
     * @throws FormatterConfigurationException
     *          Is expected to be thrown.
     */
    public void testGetUserPropertyNameFailure() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.removeNamespace(formatterLoaderNamespace);
        TestHelper.loadXMLConfig(formatterLoaderInvalidConfigEmpty);

        try {
            formatterLoader.getUserPropertyName();

            fail("Should have thrown FormatterConfigurationException because of the missing property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }

        cm.removeNamespace(formatterLoaderNamespace);
        TestHelper.loadXMLConfig(formatterLoaderInvalidConfigEmptyValues);
        try {
            formatterLoader.getUserPropertyName();

            fail("Should have thrown FormatterConfigurationException because of the empty string property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy for the method <c>getChatMessageFormatter(String)</c>.
     *
     * @throws Exception No exception should be thrown.
     */
    public void testGetChatMessageFormatter() throws Exception {
        String roleName = formatterLoader.getRolePropertyName();
        ChatMessageFormatter cmFormatter = formatterLoader.getChatMessageFormatter(roleName);
        assertNotNull("cmFormatter must not be null", cmFormatter);

        // See if the same instance is returned for the same roleName
        ChatMessageFormatter cmFormatter1 = formatterLoader.getChatMessageFormatter(roleName);
        assertSame("The ChatMessageFormatter instances should be the same", cmFormatter, cmFormatter1);
    }

    /**
     * Tests the failure for the method <c>getChatMessageFormatter(String)</c> when
     * an invalid namespace is used to create the ChatMessageFormatter instance.
     *
     * @throws FormatterConfigurationException
     *          Is expected to be thrown.
     */
    public void testGetChatMessageFormatterFailure() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(formatterLoaderConfigInvalidChatMessageFormatter);

        String roleName = formatterLoader.getRolePropertyName();
        try {
            formatterLoader.getChatMessageFormatter(roleName);

            fail("FormatterConfigurationException is expected to be thrown because of invalid configuration");
        } catch (FormatterConfigurationException e) {
            // Success
        }
    }

    /**
     * Tests the accuracy of the alghoritm used to build the <c>ChatMessageFormatter</c>
     * instance from configuration.
     * Note that the configuration file used in this tests should not be changed
     * in order to keep this test succeeding.
     *
     * @throws Exception Should not be thrown.
     */
    public void testGetMessageFormatterLoaderAccuracy() throws Exception {
        String userName = "user";
        String timestamp = "15/03/2007 13:15";
        String chatText = "Visit http://www.google.com/finance everyday and you will be very rich.";

        String expectedResult = "[<font color=\"#2248\" size=\"5\" face=\"Arial\">user</font>]"
            + ":[<font size=\"10\" face=\"Arial\">15/03/2007 13:15</font>]"
            + ":[<font size=\"12\" face=\"Helvetica\">Visit"
            + " <a href=\"http://www.google.com/finance\">http://www.google.com/finance</a>"
            + " everyday and you will be very rich.</font>]";
        String roleName = formatterLoader.getRolePropertyName();
        ChatMessageFormatter cmFormatter = formatterLoader.getChatMessageFormatter(roleName);
        assertEquals("The results of formatting should be equal"
            , expectedResult, cmFormatter.format(userName, timestamp, chatText));
    }
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import com.cronos.im.messenger.formatterimpl.ChatMessageFormatterImpl;
import com.topcoder.document.highlight.ContentHighlighter;
import com.topcoder.document.highlight.Matcher;
import com.topcoder.document.highlight.XmlTagFormatter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * This class is used to load <c>ChatMessageFormatter</c> concrete instances from a configuration file
 * and associate them with role names for the easy access.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe since it is properly synchronized.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class FormatterLoader {
    /**
     * The namespace for the loader to load the user name property name and role property name.
     * The value is the fully qualified class name of FormatterLoader.
     */
    private final String FORMATTER_LOADER_NAMESPACE = FormatterLoader.class.getName();

    /**
     * The formatter associates the role name with <c>ChatMessageFormatter</c>.
     * The keys of the map are role names, and the values <c>ChatMessageformatter</c> instances.
     * Each formatter is loaded from the namespace: FORMATTER_LOADER_NAMESPACE + "." + roleName
     */
    private final Map formatters = new HashMap();

    /**
     * The singleton instance of this accessed by <c>getInstance()</c> method.
     */
    private static FormatterLoader instance;

    /**
     * Represents the ConfigManager component's singleton instance.
     */
    private ConfigManager configManager;

    /**
     * Private constructor which has as purpose to prevent creation of new instances of this class
     * from outside.
     */
    private FormatterLoader() {
        // There are no exceptions thrown by this method call.
        configManager = ConfigManager.getInstance();
    }

    /**
     * If instance is null, initialize it and and return it.
     * This method should be synchronized to be thread safe.
     *
     * @return The singleton instance of this class.
     */
    public static synchronized FormatterLoader getInstance() {
        if (instance == null) {
            instance = new FormatterLoader();
        }
        return instance;
    }

    /**
     * Get the role property name.
     *
     * @return The role property name.
     * @throws FormatterConfigurationException
     *          Wraps all the exceptions that might appear during the
     *          retrieval of the role property name.
     */
    public String getRolePropertyName()
        throws FormatterConfigurationException {
        String roleNameProperty = "role_name_key";
        return getPropertyValue(roleNameProperty);
    }

    /**
     * Get the user name property name.
     *
     * @return the user property name
     * @throws FormatterConfigurationException
     *          Wraps all the exceptions that might appear during the
     *          retrieval of the user property name.
     */
    public String getUserPropertyName()
        throws FormatterConfigurationException {
        final String userNameProperty = "user_name_key";
        return getPropertyValue(userNameProperty);
    }

    /**
     * Get the value for a property specified by name from the namespace
     * <c>FORMATTER_LOADER_NAMESPACE</c>.
     *
     * @param propertyName The property name.
     * @return The property value.
     * @throws FormatterConfigurationException
     *          Wraps all the exceptions that might appear during the
     *          retrieval of the user property name.
     */
    private String getPropertyValue(String propertyName)
        throws FormatterConfigurationException {
        try {
            String propertyValue = configManager.getString(FORMATTER_LOADER_NAMESPACE, propertyName);
            Helper.validatePropertyValue(propertyName, propertyValue, FORMATTER_LOADER_NAMESPACE);
            return propertyValue;
        } catch (UnknownNamespaceException ex) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append(FORMATTER_LOADER_NAMESPACE).append(" is not loaded in configuration manager");
            throw new FormatterConfigurationException(sbMessage.toString(), ex);
        }
    }

    /**
     * Get the ChatMessageFormatter associated with the role name.
     * This method is synchronized because there are read/update operations made on
     * <c>formatters</c> map which impose the usage of method level synchronization.
     *
     * @param roleName The role name.
     * @return The ChatMessageFormatter associated with the role name.
     * @throws IllegalArgumentException If argument is null, or empty string.
     * @throws FormatterConfigurationException
     *                                  Wraps all the exceptions that might appear during the
     *                                  retrieval of the chat message formatter.
     */
    public synchronized ChatMessageFormatter getChatMessageFormatter(String roleName)
        throws FormatterConfigurationException {
        Helper.validateNotNullOrEmpty(roleName, "roleName");

        if (formatters.containsKey(roleName)) {
            return (ChatMessageFormatter) formatters.get(roleName);
        }

        String namespace = FORMATTER_LOADER_NAMESPACE + "." + roleName;
        // Load the user name formatting configuration values from the constructed namespace
        try {
            // Load all the font attributes from user_name_format property.
            // and create XmlTagFormatter with "font" tag
            // (and add the loaded attributes as static attributes).
            XmlTagFormatter userNameFormatter = createStaticXmlTagFormatter(namespace, "user_name_format");

            // Load all the font attributes from timestamp_format property.
            // and create XmlTagFormatter with "font" tag
            // (and add the loaded attributes as static attributes).
            XmlTagFormatter timestampFormatter = createStaticXmlTagFormatter(namespace, "timestamp_format");

            // Load the chat text dynamic attributes and pattern from chat_text_dynamic_format property.
            // Create XmlTagFormatter with "a" tag, pattern and add the dynamic attributes.
            String chatTextDynamicPropName = "chat_text_format.chat_text_dynamic_format";
            Property chatTextDynamicProperty
                = configManager.getPropertyObject(namespace, chatTextDynamicPropName);
            // The regex pattern for the XmlTagFormatter constructor which is validated for
            // not being null or empty/blank string.
            String pattern = chatTextDynamicProperty.getValue();
            Helper.validatePropertyValue(chatTextDynamicPropName, pattern, namespace);

            XmlTagFormatter chatTextDynamicFormatter = new XmlTagFormatter("a", Pattern.compile(pattern));
            for (Enumeration en = chatTextDynamicProperty.propertyNames(); en.hasMoreElements();) {
                String attributeName = (String) en.nextElement();
                if (attributeName.trim().length() == 0) {
                    throw new FormatterConfigurationException(namespace
                        + " namespace contains an empty string sub-property name for property '"
                        + chatTextDynamicPropName + "'");
                }
                String attributeValue = chatTextDynamicProperty.getValue(attributeName);
                Helper.validatePropertyValue(attributeName, attributeValue, namespace);
                chatTextDynamicFormatter.addDynamicAttribute(attributeName, attributeValue);
            }
            // Create Matcher, create ContentHighligthter, add the chatTextDynamicFormatter to
            // the matcher and then add the Matcher to ContentHiglighter.
            Matcher matcher = new Matcher();
            matcher.addFormatter(chatTextDynamicFormatter);
            ContentHighlighter chl = new ContentHighlighter();
            chl.addMatcher(matcher);

            // Load all the font attributes from chat_text_static_format property.
            // and create XmlTagFormatter with "font" tag
            // (and add the loaded attributes as static attributes).
            XmlTagFormatter chatTextFormatter
                = createStaticXmlTagFormatter(namespace, "chat_text_format.chat_text_static_format");

            ChatMessageFormatter messageFormatter = new ChatMessageFormatterImpl(userNameFormatter
                , timestampFormatter, chatTextFormatter, chl, pattern);
            formatters.put(roleName, messageFormatter);
            return messageFormatter;
        } catch (FormatterConfigurationException e) {
            throw e;
        }
        catch (UnknownNamespaceException e) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append(FORMATTER_LOADER_NAMESPACE)
                .append(" is not loaded in configuration manager");
            throw new FormatterConfigurationException(sbMessage.toString(), e);
        } catch (Exception e) {
            StringBuffer sbMessage = new StringBuffer();
            sbMessage.append("Exception occurred when building a ChatMessageFormatter instance for role '")
                .append(roleName).append("': ").append(e.getMessage());
            throw new FormatterConfigurationException(sbMessage.toString(), e);
        }
    }

    /**
     * Method used to avoid code redundancy when creating static xml tag formatters with "font" tag.
     * For all sub-properties which exist in Configuration Manager property, denoted by specified
     * <c>namespace</c> and <c>propertyName</c> the name and the value is used for adding a new
     * attribute to the static formatter.
     *
     * @param namespace    The namespace in which is found the property which holds the static attributes
     *                     for the static formatter.
     * @param propertyName The name for the property which holds the static attributes
     *                     for the static formatter.
     * @return A newly created <c>XmlTagFormatter</c> instance.
     * @throws UnknownNamespaceException This exception is thrown by Configuration Manager when
     *                                   the specified <c>namespace</c> is not loaded.
     * @throws FormatterConfigurationException
     *                                   If atributs' names/values read from configuration are null/empty.
     */
    private XmlTagFormatter createStaticXmlTagFormatter(String namespace, String propertyName)
        throws UnknownNamespaceException, FormatterConfigurationException {
        XmlTagFormatter formatter = new XmlTagFormatter("font");
        Property formatProperty
            = configManager.getPropertyObject(namespace, propertyName);
        for (Enumeration en = formatProperty.propertyNames(); en.hasMoreElements();) {
            String attributeName = (String) en.nextElement();
            if (attributeName.trim().length() == 0) {
                StringBuffer sbMessage = new StringBuffer();
                sbMessage.append(namespace)
                    .append(" namespace contains an empty string sub-property name for property '")
                    .append(propertyName).append("'");
                throw new FormatterConfigurationException(sbMessage.toString());
            }

            String attributeValue = formatProperty.getValue(attributeName);
            Helper.validatePropertyValue(attributeName, attributeValue, namespace);
            formatter.addAttribute(attributeName, attributeValue);
        }
        return formatter;
    }

}

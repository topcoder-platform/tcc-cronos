/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * This class is used to load <c>ChatMessageFormatter</c> concrete instances
 * from a configuration file and associate them with role names for the easy
 * access.
 * </p>
 * <p>
 * <b>Thread safety</b>: This class is thread safe since it is properly
 * synchronized.
 * </p>
 *
 * @author woodjhon, marius_neo
 * @version 1.0
 */
public class FormatterLoader {
	/**
	 * The namespace for the loader to load the user name property name and role
	 * property name. The value is the fully qualified class name of
	 * FormatterLoader.
	 */
	private final String FORMATTER_LOADER_NAMESPACE = FormatterLoader.class
			.getName();

	/**
	 * The formatter associates the role name with <c>ChatMessageFormatter</c>.
	 * The keys of the map are role names, and the values
	 * <c>ChatMessageformatter</c> instances. Each formatter is loaded from the
	 * namespace: FORMATTER_LOADER_NAMESPACE + "." + roleName
	 */
	private final Map formatters = new HashMap();

	/**
	 * The singleton instance of this accessed by <c>getInstance()</c> method.
	 */
	private static FormatterLoader instance;

	/**
	 * Private constructor which has as purpose to prevent creation of new
	 * instances of this class from outside.
	 */
	private FormatterLoader() {
	}

	/**
	 * If instance is null, initialize it and and return it. This method should
	 * be synchronized to be thread safe.
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
	 *             Wraps all the exceptions that might appear during the
	 *             retrieval of the role property name.
	 */
	public String getRolePropertyName() throws FormatterConfigurationException {
		String roleNameProperty = "role_name_key";
		return getPropertyValue(roleNameProperty);
	}

	/**
	 * Get the user name property name.
	 *
	 * @return the user property name
	 * @throws FormatterConfigurationException
	 *             Wraps all the exceptions that might appear during the
	 *             retrieval of the user property name.
	 */
	public String getUserPropertyName() throws FormatterConfigurationException {
		final String userNameProperty = "user_name_key";
		return getPropertyValue(userNameProperty);
	}

	/**
	 * Get the value for a property specified by name from the namespace
	 * <c>FORMATTER_LOADER_NAMESPACE</c>.
	 *
	 * @param propertyName
	 *            The property name.
	 * @return The property value.
	 * @throws FormatterConfigurationException
	 *             Wraps all the exceptions that might appear during the
	 *             retrieval of the user property name.
	 */
	private String getPropertyValue(String propertyName) {
		return null;
	}

	/**
	 * Get the ChatMessageFormatter associated with the role name. This method
	 * is synchronized because there are read/update operations made on
	 * <c>formatters</c> map which impose the usage of method level
	 * synchronization.
	 *
	 * @param roleName
	 *            The role name.
	 * @return The ChatMessageFormatter associated with the role name.
	 * @throws IllegalArgumentException
	 *             If argument is null, or empty string.
	 * @throws FormatterConfigurationException
	 *             Wraps all the exceptions that might appear during the
	 *             retrieval of the chat message formatter.
	 */
	public synchronized ChatMessageFormatter getChatMessageFormatter(
			String roleName) {
		return null;
	}

}

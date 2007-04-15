/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

/**
 * <p>
 * IMAjaxSupportUtility holds the global configurable values(like session key, object key, etc), shared by all
 * the other class in this component.
 * </p>
 *
 * <p>
 * This class is thread safe since it only reads the values from the config manager.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxSupportUtility {

    /**
     * The namesapce from which to load the configuration values. The values are defined in secion 3.2 of the
     * component specification.
     */
    public static final String NAMESPACE = "com.cronos.im.ajax.IMAjaxSupportUtility";

    /**
     * Empty constructor.
     */
    private IMAjaxSupportUtility() {
    }

    /**
     * Get the client role name.
     *
     *
     * @return the client role name
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getClientRoleName() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "client_role_name");
    }

    /**
     * get the manager role name.
     *
     *
     * @return the manager role name
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getManagerRoleName() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "manager_role_name");
    }

    /**
     * Get the role property name.
     *
     *
     * @return the role property name used to get the role name from chat user profile
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getRolePropertyKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "role_property_name");
    }

    /**
     * Get the session key used to get the user profile from the http session attribute.
     *
     *
     * @return the session key used to get the user profile from the http session attribute.
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getUserProfileSessionKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "user_profile_session_key");
    }

    /**
     * Get the messenger key. The key is used by object factory to create Messenger instance. The key is also
     * used by the request handlers to get the instance from ServletContext attribute.
     *
     *
     * @return the messenger key
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getIMMessengerKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "messenger_key");
    }

    /**
     * Get the chat session manager key. The key is used by object factory to create ChatSessionManager
     * instance. The key is also used by the request handlers to get the instance from ServletContext
     * attribute.
     *
     * @return the chat session manager key
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getChatSessionManagerKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "chat_session_manager_key");
    }

    /**
     * Get the chat status tracker key. The key is used by object factory to create ChatStatusTracker
     * instance. The key is also used by the request handlers to get the instance from ServletContext
     * attribute.
     *
     * @return the chat status tracker key
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getChatStatusTrackerKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "chat_status_tracker_key");
    }

    /**
     * Get the service key. The key is used by object factory to create ServiceEngine instance. The key is
     * also used by the request handlers to get the instance from ServletContext attribute.
     *
     * @return the service key
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getServiceEngineKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "service_engine_key");
    }

    /**
     * Get the cookie name, used to get the time zone from the cookie value.
     *
     * @return the cookie name, used to get the time zone from the cookie value.
     * @throws IMAjaxConfigurationException
     *             if the configuration value is invalid.
     */
    public static String getTimezoneCookieName() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "time_zone_cookie_name");
    }

    /**
     * Get the category key used to get the category from http session attribute.
     *
     * @return the category key used to get the category from http session attribute
     * @throws IMAjaxConfigurationException
     *             if the value is not configured, or empty string.
     */
    public static String getCategorySessionKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "category_session_key");
    }

    /**
     * The key used to get the xml request message from the http request.
     *
     * @return The key used to get the xml request message from the http request
     * @throws IMAjaxConfigurationException
     *             if the value is not configured, or empty string.
     */
    public static String getXMLRequestParamKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "xml_request");
    }

    /**
     * The key used to get the user id from ServiceEelement.
     *
     * @return The key used to get the user id from ServiceEelement.
     * @throws IMAjaxConfigurationException
     *             if the value is not configured, or empty string.
     */
    public static String getUserIdServiceElementKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "user_id_property_key");
    }

    /**
     * The key used to get the session id from ServiceEelement.
     *
     * @return The key used to get the session id from ServiceEelement.
     * @throws IMAjaxConfigurationException
     *             if the value is not configured, or empty string.
     */
    public static String getSessionIdServiceElementKey() throws IMAjaxConfigurationException {
        return IMHelper.getRequiredConfigString(NAMESPACE, "session_id_property_key");
    }
}

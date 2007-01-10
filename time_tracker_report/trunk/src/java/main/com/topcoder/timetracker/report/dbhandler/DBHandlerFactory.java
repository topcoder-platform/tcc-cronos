/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is a factory and contains all the {@link DBHandler} implementations registered with the application. This
 * class allows the fetching of a {@link DBHandler} registered by name using the method {@link #getDBHandler(String)}.
 * <p/>
 * This class is thread-safe as the instance members are not modifiable.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class DBHandlerFactory {

    /**
     * This is the name of the {@link ConfigManager} namespace used for lookup of DBHandler implementations.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.report.DBHandlers";
    /**
     * This is the name of the {@link ConfigManager} property used for lookup of DBHandler implementations.
     */
    private static final String DB_HANDLERS_PROPERTY_NAME = "DBHandlers";

    /**
     * This map contains all the registered DBHandler implementation. The key for the Map is a string representing the
     * name with which the instance is registered.  The value in the map will be an implementation of {@link
     * DBHandler}.
     * <p/>
     * This field cannot be <tt>null</tt> and is initialized during the instance creation. This Map cannot hold
     * <tt>null</tt> elements, but can be empty.
     */
    private final Map dbHandlers = new HashMap();

    /**
     * Creates a new DBHandlerFactory. The factory loads the configuration from {@link ConfigManager} and registers the
     * instances configured for lookup via {@link #getDBHandler(String)}.
     *
     * @throws ReportConfigurationException if there are problems during the reading from the configuration or loading
     *                                      the specified implementation of DBHandler through reflection.
     */
    public DBHandlerFactory() throws ReportConfigurationException {
        loadHandlersFromConfiguration();
    }

    /**
     * Returns the DBHandler instance registered with the specified name. If no {@link DBHandler} implementation is
     * found registered with the specified registration name, then a {@link DBHandlerNotFoundException} will be thrown.
     *
     * @param dbhandlerName the name for which a registered DBHandler shall be retrieved
     *
     * @return the {@link DBHandler} instance registered for the given name
     *
     * @throws NullPointerException       if dbhandlerName is <tt>null</tt>
     * @throws IllegalArgumentException   if dbhandlerName is an empty (trim'd) String
     * @throws DBHandlerNotFoundException If no {@link DBHandler} implementation is found registered with the passed
     *                                    name
     */
    public DBHandler getDBHandler(final String dbhandlerName)
        throws DBHandlerNotFoundException {
        if (dbhandlerName == null) {
            throw new NullPointerException("The parameter named [dbhandlerName] was null.");
        }
        if (dbhandlerName.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [dbhandlerName] was an empty String.");
        }

        final DBHandler ret = (DBHandler) dbHandlers.get(dbhandlerName);
        if (ret != null) {
            return ret;
        } else {
            throw new DBHandlerNotFoundException("The DBHandler with the name [" + dbhandlerName + "] was not found.");
        }
    }

    /**
     * Initializes the {@link #dbHandlers} with the information looked up from {@link ConfigManager} namespace {@link
     * #NAMESPACE}.
     *
     * @throws ReportConfigurationException if there are exceptions during the loading from the configuration or
     *                                      instantiation of the classes configured to be used for the dbHandlers
     */
    private void loadHandlersFromConfiguration() throws ReportConfigurationException {
        final ConfigManager manager = ConfigManager.getInstance();
        final String[] stringArray;
        try {
            stringArray = manager.getStringArray(NAMESPACE, DB_HANDLERS_PROPERTY_NAME);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("Unable to load the DBHandlers, as the namespace [" + NAMESPACE
                + "] was not found in ConfigManager configuration.", e);

        }
        if (stringArray == null || stringArray.length == 0) {
            throw new ReportConfigurationException("Unable to load the DBHandlers, as the ConfigManager property ["
                + DB_HANDLERS_PROPERTY_NAME + "]  in namespace [" + NAMESPACE
                + "] was not defined or did not contain any values.");
        }

        for (int i = 0; i < stringArray.length; i++) {
            final String handlerName = stringArray[i];
            dbHandlers.put(handlerName, createDbHandler(handlerName));
        }
    }

    /**
     * This method tries to retrieve the implementation class for the given DBHandler name by looking up the
     * implementation class name using ConfigManager. First, the property with the given dbhandlerName is looked up in
     * {@link ConfigManager} in the namespace {@link #NAMESPACE} - The value found is the class name. Using the class
     * name found the class is loaded using {@link Class#forName(String)}.
     *
     * @param name the DBHandler name to find the implementation class for.
     *
     * @return the implementation class looked up for the given DBHandler name.
     *
     * @throws ReportConfigurationException in case the class name for the DBHandler name specified cannot be found in
     *                                      ConfigManager, a ConfigManager operation throws an exception or the
     *                                      implementation class cannot be loaded.
     */
    private static Class getDBHandlerImplClass(final String name) throws ReportConfigurationException {
        final String implClassName;
        try {
            implClassName = ConfigManager.getInstance().getString(NAMESPACE, name);
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigurationException("unable to find configuration property for DBHandler named [" + name
                + "] in namespace [" + NAMESPACE + "].", e);
        }

        //lookup impl class name
        if (implClassName == null) {
            throw new ReportConfigurationException(
                "The implementation class name for DBHandler named [" + name + "] was null.");
        }
        if (implClassName.trim().length() == 0) {
            throw new ReportConfigurationException(
                "The implementation class name for DBHandler named [" + name + "] was an empty String.");
        }

        //load impl class
        final Class implClass;
        try {
            implClass = Class.forName(implClassName);
        } catch (Throwable t) {
            throw new ReportConfigurationException(
                "Unable to load implementation class [" + implClassName + "] for DBHandler named [" + name + "].", t);
        }
        return implClass;
    }

    /**
     * This method creates a new {@link DBHandler} instance by looking up the implementation class name for the given
     * DBHandler name and the finding a no-arg Constructor, which is then invoked.
     *
     * @param name the DBHandler name to lookup the implementation class name for
     *
     * @return the DBHandler instance created
     *
     * @throws ReportConfigurationException in case the DBHandler cannot be looked up due to misconfiguration, the class
     *                                      configured cannot be loaded, no public constructor with the signature
     *                                      specified does exist in the class or the constructor invocation throws an
     *                                      exception
     */
    private static DBHandler createDbHandler(final String name) throws ReportConfigurationException {
        //lookup and load DBHandler implementation class
        final Class implClass = getDBHandlerImplClass(name);
        final Constructor constructor;
        try {
            constructor = implClass.getConstructor(null);
        } catch (NoSuchMethodException e) {
            throw new ReportConfigurationException("DBHandler implementation class [" + implClass .getName()
                + "] did not have a constructor with no parameters.", e);
        }

        //create DBHandler instance
        final DBHandler handler;
        try {
            handler = (DBHandler) constructor.newInstance(null);
        } catch (ClassCastException e) {
            throw new ReportConfigurationException("the DBHandler implementation class [" + implClass.getName()
                + "] configured for dbhandler with name [" + name + "] does not implement DBHandler.", e);
        } catch (Throwable t) {
            throw new ReportConfigurationException("the DBHandler implementation class [" + implClass .getName()
                + "] configured for dbhandler with name [" + name
                + "] could not be instantiated, constructor invocation failed.", t);
        }
        return handler;
    }
}

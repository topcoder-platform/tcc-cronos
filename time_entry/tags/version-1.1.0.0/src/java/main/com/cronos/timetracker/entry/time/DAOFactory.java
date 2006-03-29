/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import java.lang.reflect.InvocationTargetException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;


/**
 * <p>
 * Defines one method that instantiates the <code>DAO</code> that a <code>DataObject</code> implementation will
 * use to persist itself.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DAOFactory {

    /**
     * <p>
     * The connectionName property to get the connection from DBConnectionFactory.
     * </p>
     */
    private static final String CONNECTIONNAME_PROPERTY = "connectionName";

    /**
     * <p>
     * The class property for DAOFactory to create the <code>DAO</code> instance via reflection.
     * </p>
     */
    private static final String CLASS_PROPERTY = "class";

    /**
     * <p>
     * Private constructor to prevent this class be instantiated.
     * </p>
     */
    private DAOFactory() {
        // empty
    }

    /**
     * <p>
     * Obtains the <code>DAO</code> that is to be used for the given dataobject class.
     * </p>
     *
     * @param dataObject The class of the passed object.
     * @param namespace the namespace to load the configuration
     *
     * @return The instance of the <code>DAO</code> object matched to the passed DataObject
     *
     * @throws DAOFactoryException if the passed object is not an instance of <code>DataObject</code>, or the composed
     *         <code>DAO</code> cannot be instantiated.
     * @throws NullPointerException if any argument is <code>null</code>
     * @throws IllegalArgumentException if namespace is empty
     */
    public static DAO getDAO(Class dataObject, String namespace) throws DAOFactoryException {
        TimeEntryHelper.checkNull(dataObject, "dataObject");
        TimeEntryHelper.checkString(namespace, "namespace");

        if (!(DataObject.class.isAssignableFrom(dataObject))) {
            throw new DAOFactoryException("dataObject is not an instance of DataObject");
        }

        // get the ConfigManager instance
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            // obtain the property
            Property property = configManager.getPropertyObject(namespace, dataObject.getName());

            // obtain the DAO class name
            String daoClassName = property.getProperty(CLASS_PROPERTY).getValue();

            // obtain the connection name
            String connName = property.getProperty(CONNECTIONNAME_PROPERTY).getValue();

            // obtain the DAO class
            Class[] parameterClassNames = new Class[] {String.class, String.class};
            Object[] parameters = new Object[] {connName, namespace};

            return (DAO) Class.forName(daoClassName).getConstructor(parameterClassNames).newInstance(parameters);
        } catch (UnknownNamespaceException une) {
            // wrap the UnknownNamespaceException with DAOFactoryException
            throw new DAOFactoryException("the namespace does not exist", une);
        } catch (SecurityException e) {
            // wrap the SecurityException with DAOFactoryException
            throw new DAOFactoryException("Security error occurs.", e);
        } catch (InstantiationException e) {
            // wrap the InstantiationException with DAOFactoryException
            throw new DAOFactoryException("The class cannot be instantiated.", e);
        } catch (IllegalAccessException e) {
            // wrap the IllegalAccessException with DAOFactoryException
            throw new DAOFactoryException("The class constructor cannot be accessed.", e);
        } catch (NoSuchMethodException e) {
            // wrap the NoSuchMethodException with DAOFactoryException
            throw new DAOFactoryException("Proper constructor of the class cannot be found.", e);
        } catch (ClassNotFoundException e) {
            // wrap the ClassNotFoundException with DAOFactoryException
            throw new DAOFactoryException("The class cannot be found.", e);
        } catch (InvocationTargetException e) {
            // wrap the InvocationTargetException with DAOFactoryException
            throw new DAOFactoryException("The class constructor throws exception.", e);
        } catch (NullPointerException e) {
            // wrap the NullPointerException with DAOFactoryException
            throw new DAOFactoryException("Some properties missed in the config files. The cause is " + e.toString(),
                    e);
        } catch (IllegalArgumentException e) {
            // wrap the IllegalArgumentException with DAOFactoryException
            throw new DAOFactoryException("Some properties have empty values. The cause is " + e.toString(), e);
        } catch (Exception e) {
            // wrap any other exception with DAOFactoryException
            throw new DAOFactoryException("any other exception occurs during the getDAO() method.", e);
        }
    }
}

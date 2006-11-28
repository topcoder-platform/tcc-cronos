/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;

import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;

/**
 * <p>Helper class containing some methods to simplify access to the config manager and object factory.
 *
 * <p><strong>Thread Safety</strong>: This class has no state and is therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class ObjectFactoryHelpers {
    /**
     * Private constructor to prevent instantiation.
     */
    private ObjectFactoryHelpers() {
    }

    /**
     * Instantiates an object using the object factory.
     *
     * @param namespace namespace used to look up the config specification namespace and object key
     * @param specNamespaceProperty the property containing the config specification namespace
     * @param keyProperty the property containing the key
     * @param klass the class of the object to be instantiated
     * @return a new instance of the configured object
     * @throws InstantiationException if the namespace or one of the properties does not exist or the object cannot
     *   be instantiated
     */
    public static Object instantiateObject(String namespace, String specNamespaceProperty, String keyProperty,
                                           Class klass)
        throws InstantiationException {
        String specNamespace = getProperty(namespace, specNamespaceProperty);
        String key = getProperty(namespace, keyProperty);

        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace)).createObject(klass, key);
        } catch (SpecificationConfigurationException ex) {
            throw new InstantiationException("error instantiating '" + key + "' from '" + specNamespace + "': "
                                             + ex.getMessage(), ex);
        } catch (IllegalReferenceException ex) {
            throw new InstantiationException("error instantiating '" + key + "' from '" + specNamespace + "': "
                                             + ex.getMessage(), ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new InstantiationException("error instantiating '" + key + "' from '" + specNamespace + "': "
                                             + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the specified property of the specified namespace.
     *
     * @param namespace the namespace
     * @param prop the property
     * @return the value of the specified property of the specified namespace
     * @throws InstantiationException if <code>namespace</code> does not exist or does not contain the requested
     *   property
     */
    public static String getProperty(String namespace, String prop) throws InstantiationException {
        ConfigManager manager = ConfigManager.getInstance();

        try {
            String property = manager.getString(namespace, prop);
            if (property == null) {
                throw new InstantiationException("namespace '" + namespace + "' is missing required property '"
                                                 + prop + "'");
            }

            if (property.trim().length() == 0) {
                throw new InstantiationException("property '" + prop + "' of namespace '" + namespace
                                                 + "' should not be an empty string");
            }

            return property;
        } catch (UnknownNamespaceException ex) {
            throw new InstantiationException("unknown namespace '" + namespace + "'");
        }
    }

    /**
     * Retrieves the specified property from the specified namespace and does a JNDI lookup to retrieve the
     * <code>MessageRemote</code> interface.
     *
     * @param namespace the configuration manager namespace
     * @return the <code>MessageRemote</code> referenced by the EJB JNDI reference
     * @throws InstantiationException if the specified namespace or property does not exist
     * @throws InstantiationException if the object cannot be resolved
     */
    static MessageRemote resolveRemoteEjbReference(String namespace) throws InstantiationException {
        String ejbRef = ObjectFactoryHelpers.getProperty(namespace, "jndiEjbReference");
        try {
            InitialContext ic = new InitialContext();
            Object lookup = ic.lookup(ejbRef);
            MessageRemoteHome home = (MessageRemoteHome) PortableRemoteObject.narrow(lookup, MessageRemoteHome.class);
            return home.create();
        } catch (RemoteException ex) {
            throw new InstantiationException("error communicating with MessageRemoteHome: " + ex.getMessage(), ex);
        } catch (CreateException ex) {
            throw new InstantiationException("remote message EJB could not be created: " + ex.getMessage(), ex);
        } catch (NamingException ex) {
            throw new InstantiationException("error resolving EJB home reference '" + ejbRef + "': "
                                             + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the specified property from the specified namespace and does a JNDI lookup to retrieve the
     * <code>MessageLocal</code> interface.
     *
     * @param namespace the configuration manager namespace
     * @return the <code>MessageLocal</code> referenced by the EJB JNDI reference
     * @throws InstantiationException if the specified namespace or property does not exist
     * @throws InstantiationException if the object cannot be resolved
     */
    static MessageLocal resolveLocalEjbReference(String namespace) throws InstantiationException {
        String ejbRef = ObjectFactoryHelpers.getProperty(namespace, "jndiEjbReference");
        try {
            return ((MessageLocalHome) new InitialContext().lookup(ejbRef)).create();
        } catch (CreateException ex) {
            throw new InstantiationException("local message EJB could not be created: " + ex.getMessage(), ex);
        } catch (NamingException ex) {
            throw new InstantiationException("error resolving EJB home reference '" + ejbRef + "': "
                                             + ex.getMessage(), ex);
        }
    }
}

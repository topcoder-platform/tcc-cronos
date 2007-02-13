/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * This UserStoreManager implementation provides basic support for pluggable user stores. A
 * predefined set of user stores can be specified in the configuration file. The constructor reads
 * all user store names and classes, instantiates them as UserStore instances and adds them to an
 * internal set of user stores.  Note that the name of a UserStore instance is not "internal" to the
 * UserStore object, but maintained "externally" by this class.
 * </p>
 *
 * <p>
 * This class also provides methods to:
 * <ul>
 * <li>add a user store, identifying them by a unique name</li>
 * <li>remove a user store, bsaed on name</li>
 * <li>retrieve all known user store names</li>
 * <li>retrieve a UserStore instance by its given unique name</li>
 * </ul>
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UserStoreManagerImpl implements UserStoreManager {

    /**
     * The configuration property name for the predefined set of user store names that should be
     * created by this manager.
     */
    private static final String NAMES = "UserStoreNames";


    /**
     * The configuration property name for the predefined set of user store class names for this
     * manager. The actual format of the property name is "ClassName.userstorename", where
     * "userstorename" is one of the names defined by the UserStoreNames property.
     */
    private static final String CLASS_NAME = "ClassName";


    /**
     * The configuration property name for the predefined set of user store connection strings for
     * this manager. The actual format of the property name is "ConnectionString.userstorename",
     * where "userstorename" is one of the names defined by the UserStoreNames property. Each user
     * store can have at most one connection string, which is sent to the UserStore via the
     * setConnectionString method.
     */
    private static final String CONNECTION_STRING = "ConnectionString";


    /**
     * These are all the userStores we know about. The keys are the unique name of the user stores,
     * and the values are UserStore instances. The constructor populates this object (from the
     * configuration namespace), and is accessed by all remaining methods of this class.
     */
    private final Map userStores = new Hashtable();


    /**
     * <p>
     * Creates the UserStoreManagerImpl instance with the given configuration namespace. The
     * constructor registers a predefined set of user stores represented in the configuration file
     * in the following way:
     * </p>
     * <ul>
     * <li>Reads all registered user store names from the NAMES property</li>
     * <li>For each registered name, reads the name of the class to instantiate from
     * CLASSNAME+&quot;.&quot;+name</li>
     * <li>For each registered name, reads the connection string for that particular class from
     * CONNECTION_STRING+&quot;.&quot;+name</li>
     * <li>For each registered name, creates a UserStore instance from the specified class name,
     * passes the connection string to the instance using <code>setConnectionString()</code> and
     * adds it the manager using the <code>add</code> method</li>
     * </ul>
     *
     * @param namespace configuration namespace to read configuration parameter from
     * @throws NullPointerException if namespace is null
     * @throws IllegalArgumentException if namespace is the empty String
     * @throws ConfigurationException if any error occured during initialization, such as, could not
     *             find or instantiate the UserStore class, or if the configuration file could not
     *             be read.
     */
    public UserStoreManagerImpl(String namespace) throws ConfigurationException {

        if (namespace == null) {
            throw new NullPointerException("namespace cannot be null.");
        }

        if (namespace.length() == 0) {
            throw new IllegalArgumentException("namespace cannot be empty.");
        }

        ConfigManager cm = ConfigManager.getInstance();
        if (!cm.existsNamespace(namespace)) {
            throw new ConfigurationException("namespace " + namespace + " not found.");
        }

        try {
            cm.refresh(namespace);

            String names[] = cm.getStringArray(namespace, NAMES);
            if (names != null) {
                // instantiate each store
                for (int i = 0; i < names.length; ++i) {
                    String name = names[i];
                    if (name == null || name.trim().length() == 0) {
                        throw new ConfigurationException("name is empty in " + NAMES + " property.");
                    }

                    String classname = cm.getString(namespace, CLASS_NAME + "." + name);
                    if (classname == null || classname.trim().length() == 0) {
                        throw new ConfigurationException("Class name for userStore " + name
                                                         + " not found.");
                    }

                    // this is actually an optional property
                    String connectionString = cm.getString(namespace,
                                                           CONNECTION_STRING + "." + name);

                    if (connectionString == null || connectionString.trim().length() == 0) {
                        throw new ConfigurationException(CONNECTION_STRING + " property is required.");
                    }

                    // instantiate and capture the store
                    UserStore store = instantiateStore(classname);
                    store.setConnectionString(connectionString);
                    this.add(name, store);
                }
            }
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("namespace " + namespace + " could not be refreshed.");
        }
    }


    /**
     * Instantiate an instance of the given class name, assuming it is a UserStore.
     *
     * @param classname the UserStore class name to instantiate
     * @return a new instance of the UserStore. The default (no-arg) constructor is called.
     * @throws ConfigurationException if the requested class could not be found, or instantiated.
     */
    private UserStore instantiateStore(String classname) throws ConfigurationException {
        try {

            // find the class, instantiate one, and give it the connection string.
            Class klass = Class.forName(classname);

            Object storeObject = klass.newInstance();
            // make sure it's the right type
            if (!(storeObject instanceof UserStore)) {
                throw new ConfigurationException(classname + " is not a UserStore.");
            }

            // it's all OK now
            return (UserStore) storeObject;

        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Could not find UserStore class " + classname + ".", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Could not instantiate UserStore class " + classname,
                                             e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Could not access UserStore class " + classname + ".", e);
        }
    }


    /**
     * <p>
     * Returns true if given user store name is present within the manager, false otherwise.
     * </p>
     *
     * @return true if given user store name is present within the manager, false otherwise
     * @param name user store name to query
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     */
    public boolean contains(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }

        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // if the map has a non-null value, that indicates we know about it.
        return userStores.get(name) != null;
    }


    /**
     * <p>
     * Adds given user store with given name to the manager.
     * </p>
     *
     * @return true if userStore is added, false if userStore is already present within the manager
     * @param name unique name of user store to add
     * @param userStore UserStore instance
     * @throws NullPointerException if name or userStore is null
     * @throws IllegalArgumentException if name is the empty String
     */
    public boolean add(String name, UserStore userStore) {

        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }
        if (userStore == null) {
            throw new NullPointerException("userStore cannot be null.");
        }

        // if it's already there, return false
        if (contains(name)) {
            return false;
        }
        // capture it.
        userStores.put(name, userStore);
        return true;
    }


    /**
     * <p>
     * Removes user store with given name from the manager.
     * </p>
     *
     * @return true if userStore is removed, false if userStore is not present within the manager
     * @param name name of user store to add
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     */
    public boolean remove(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }

        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // remove it, and if it was there, return true.
        return userStores.remove(name) != null;
    }


    /**
     * Returns all user stores from the manager (Collection of String instances).
     *
     * @return all user stores from the manager
     */
    public Collection getUserStoreNames() {
        return Collections.unmodifiableSet(userStores.keySet());
    }


    /**
     * <p>
     * Returns UserStore instance for given user store name.
     * </p>
     *
     * @param name unique name of user store to retrieve
     * @return UserStore instance for given user store name
     * @throws NullPointerException if name is null
     * @throws IllegalArgumentException if name is the empty String
     * @throws UnknownUserStoreException if no user store exists for given name
     */
    public UserStore getUserStore(String name) throws UnknownUserStoreException {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }

        if (name.length() == 0) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        // retrieve from the map
        UserStore store = (UserStore) userStores.get(name);
        if (store == null) {
            throw new UnknownUserStoreException("user store " + name + " not found.");
        }

        return store;
    }
}
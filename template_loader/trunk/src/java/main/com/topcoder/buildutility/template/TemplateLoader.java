/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 *
 * TCS Template Loader 1.0
 */
package com.topcoder.buildutility.template;

import com.topcoder.util.cache.Cache;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.cache.LRUCacheEvictionStrategy;

import java.lang.reflect.Constructor;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;

import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>A main class of the component which must be used for locating and loading the top-level template hierarchies. The
 * clients must use this class to obtain the template hierarchies loaded from the persistent data store. The <code>
 * TemplateLoader</code> class provide an ability to specify whether the template hierarchies loaded from the
 * persistent data store on demand must be cached or not.</p>
 *
 * <p>The instances of this class may be initialized with parameters passed at run-time or using the configuration
 * properties provided by the specified configuration namespace.</p>
 *
 * <p>Thread safety: this class is thread safe. The private state of <code>TemplateLoader</code> is never changed after
 * instantiation and the only method affecting the state of the internal cache is synchronized.</p>
 *
 *
 * @author isv
 * @author oldbig
 *
 * @version 1.0
 */
public class TemplateLoader {


    /**
     * <p>A <code>String</code> providing the name of optional configuration property providing the parameters to be
     * used to configure the cache to be used by a <code>TemplateLoader</code> instance. If such a property is provided
     * by the configuration namespace then the <code>TemplateLoader</code> instance will cache the template hierarchies
     * loaded on demand. If such a property is missing then the <code>TemplateLoader</code> will not use any caching.
     * </p>
     */
    public static final String CACHING_PROPERTY = "caching";

    /**
     * <p>A <code>String</code> providing the name of optional configuration property which must be nested within
     * "caching" property. This property (if present) must provide a positive timeout value to be used for configuring
     * the cache used by the <code>TemplateLoader</code> instance.</p>
     */
    public static final String CACHING_TIMEOUT_PROPERTY = "timeout";

    /**
     * <p>A <code>String</code> providing the name of optional configuration property which must be nested within
     * "caching" property. This property (if present) must provide a positive value specifying the maximum size of the
     * cache to be used for configuring the cache used by the <code>TemplateLoader</code> instance.</p>
     */
    public static final String CACHING_SIZE_PROPERTY = "size";

    /**
     * <p>A <code>String</code> providing the name of required configuration property providing the parameters necessary
     * to configure the <code>TemplateHierarchyPersistence</code> to be used by the <code>TemplateLoader</code> instance
     * to load the requested template hierarchies from the persistent data store.</p>
     */
    public static final String PERSISTENCE_PROPERTY = "persistence";

    /**
     * <p>A <code>String</code> providing the name of required configuration property which must be nested within the
     * "persistence" property. This property must provide the fully-qualified name of class implementing the <code>
     * TemplateHierarchyPersistence</code> interface which must be used by the <code>TemplateLoader</code>.</p>
     */
    public static final String PERSISTENCE_CLASS_PROPERTY = "class";

    /**
     * <p>A <code>String</code> providing the name of optional configuration property which must be nested within the
     * "persistence" property. This property (if present) must provide the configuration parameters necessary to
     * configure the instance of class implementing the <code>TemplateHierarchyPersistence</code>.</p>
     */
    public static final String PERSISTENCE_CONFIG_PROPERTY = "config";

    /**
     * <p>A <code>String</code> providing the name of required configuration property providing the URI referencing the
     * root of a file server which the template URIs are resolved relatively to.</p>
     */
    public static final String FILE_SERVER_PROPERTY = "file_server";

    /**
     * <p>A <code>Cache</code> which is used for caching the template hierarchies loaded from the persistent data store
     * on demand. This instance field is initialized within the constructors and is never changed during the lifetime of
     * <code>TemplateLoader</code> instance. This instance field must notbe <code>null</code>. Note that even if the
     * <code>TemplateLoader</code> instance is configured not to use any sort of caching this instance field is
     * initialized with instance of <code>NullCache</code>.</p>
     */
    private Cache cache = null;

    /**
     * <p>A <code>TemplateHierarchyPersistence</code> which is used by this <code>TemplateLoader</code> to load the
     * requested template hierarchies on demand. This instance field is initialized within the constructors and is never
     * changed during the lifetime of <code>TemplateLoader</code> instance. This instance field must not be <code>null
     * </code>.</p>
     */
    private TemplateHierarchyPersistence persistence = null;

    /**
     * <p>A <code>String</code> providing the URI of a file server root which the template URIs are resolved relatively
     * to. This instance field is initialized within the constructors and is never changed during the lifetime of
     * <code>TemplateLoader</code> instance. This instance field must not be <code>null
     * </code>.</p>
     */
    private String fileServerUri = null;

    /**
     * <p>Private constructor used to create <code>TemplateLoader</code> instance with the given
     * persistence, fileServerUri acn cache.</p>
     *
     * @throws NullPointerException if persistence or fileServerUri is <code>null</code>.
     * @throws IllegalArgumentException if specified <code>fileServerUri</code> is an empty <code>String</code> being
     * trimmed.
     *
     * @param persistence a <code>TemplateHierarchyPersistence</code> to be used by this <code>TemplateLoader</code> to
     * load the requested template hierarchies on demand.
     * @param fileServerUri a <code>String</code> providing the URI of a file server root.
     * @param cache a <code>Cache</code> which is used for caching the template hierarchies loaded from the persistent
     * data store on demand.
     */
    private TemplateLoader(TemplateHierarchyPersistence persistence, String fileServerUri, Cache cache) {
        // validate arguments
        TemplateLoaderHelper.checkNull(persistence, "persistence");
        TemplateLoaderHelper.checkString(fileServerUri, "fileServerUri");

        // initialize the inner fields
        this.persistence = persistence;
        this.fileServerUri = fileServerUri;
        this.cache = cache;
    }

    /**
     * <p>Constructs new <code>TemplateLoader</code> which will use the specified <code>TemplateHierarchyPersistence
     * </code> to load the requested template hierarchies. The created instance will not cache any template hierarchies
     * which it will be asked to load from persistent data store.</p>
     *
     * @throws NullPointerException if any of specified arguments is <code>null</code>.
     * @throws IllegalArgumentException if specified <code>fileServerUri</code> is an empty <code>String</code> being
     * trimmed.
     *
     * @param persistence a <code>TemplateHierarchyPersistence</code> to be used by this <code>TemplateLoader</code> to
     * load the requested template hierarchies on demand.
     * @param fileServerUri a <code>String</code> providing the URI of a file server root.
     */
    public TemplateLoader(TemplateHierarchyPersistence persistence, String fileServerUri) {
        this(persistence, fileServerUri, new NullCache());
    }

    /**
     * <p>Constructs new <code>TemplateLoader</code> which will use the specified <code>TemplateHierarchyPersistence
     * </code> to load the requested template hierarchies. The created instance will not template hierarchies which it
     * will be asked to load from persistent data store. The cache will use the supplied timeout and maximum size values
     * to customize its behavior.</p>
     *
     * @throws NullPointerException if specified <code>persistence</code> or fileServerUri is <code>null</code>.
     * @throws IllegalArgumentException if any of specified <code>timeout</code> or <code>cacheSize</code> is not
     * positive or specified <code>fileServerUri</code> is an empty <code>String</code> being trimmed.
     *
     * @param persistence a <code>TemplateHierarchyPersistence</code> to be used by this <code>TemplateLoader</code> to
     * load the requested template hierarchies on demand.
     * @param fileServerUri a <code>String</code> providing the URI of a file server root.
     * @param timeout a <code>long</code> providing the timeout to be used to configure the cache.
     * @param cacheSize an <code>int</code> providing the maximum size to be used to configure the cache.
     */
    public TemplateLoader(TemplateHierarchyPersistence persistence, String fileServerUri, long timeout, int cacheSize) {
        this(persistence, fileServerUri, new SimpleCache(cacheSize, timeout, new LRUCacheEvictionStrategy()));
    }

    /**
     * <p>Constructs new <code>TemplateLoader</code> which is initialized based on configuration properties provided by
     * the specified configuration namespace. The specified namespace must be loaded into the <code>Configuration
     * Manager</code> prior to calling this constructor.</p>
     *
     *
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified argument is an empty <code>String</code> being trimmed.
     * @throws ConfigurationException if any unexpected error occurs.
     *
     * @param namespace a <code>String</code> providing the configuration namespace which must be used by the new
     * <code>TemplateLoader</code> instance
     */
    public TemplateLoader(String namespace) throws ConfigurationException {
        this(loadPersistence(namespace), loadFileServerUri(namespace), loadCache(namespace));
    }

    /**
     * Loads the file server property from the given configuration namespace.
     *
     * @param namespace the namespace containing the property.
     * @throws ConfigurationException if any error occurred while loading the property
     * @return the string value of file server property
     */
    private static String loadFileServerUri(String namespace) throws ConfigurationException {

        String value = null;
        try {
            // loads the file server property from the given configuration namespace
            value = ConfigManager.getInstance().getString(namespace, FILE_SERVER_PROPERTY);
        } catch (UnknownNamespaceException e) {
            // if the namespace does not exist, throw an exception
            throw new ConfigurationException(namespace + " does not exist", e);
        }

        // if the FILE_SERVER_PROPERTY does not exist, throw an exception
        if (value == null) {
            throw new ConfigurationException(FILE_SERVER_PROPERTY + " property does not exist");
        }

        // if the FILE_SERVER_PROPERTY is empty string, throw an exception
        if (value.trim().length() == 0) {
            throw new ConfigurationException(FILE_SERVER_PROPERTY + " property is empty string");
        }

        return value;
    }

    /**
     * Loads the cache property from the given configuration namespace. If this property does not exist,
     * a NullCache instance will be returned.
     *
     * @param namespace the namespace containing the property.
     * @throws ConfigurationException if any error occurred while loading the property
     * @return the loaded Cache instance.
     */
    private static Cache loadCache(String namespace) throws ConfigurationException {
        Property caching = null;
        try {
            // loads the cache property from the given configuration namespace
            caching = ConfigManager.getInstance().getPropertyObject(namespace, CACHING_PROPERTY);
        } catch (UnknownNamespaceException une) {
            // if the namespace does not exist, throw an exception
            throw new ConfigurationException(namespace + " does not exist", une);
        }

        // if  the cache property does not exist, a NullCache instance will be returned.
        if (caching == null) {
            return new NullCache();
        }

        // retrieve the timeout property
        Long timeout = getLongProperty(caching, CACHING_TIMEOUT_PROPERTY);
        if (timeout == null) {
            // if the timeout property does not exist, use the default value - SimpleCache.NO_TIMEOUT;
            timeout = new Long(SimpleCache.NO_TIMEOUT);
        }

        // retrieve the cache size property
        Integer size = getIntProperty(caching, CACHING_SIZE_PROPERTY);
        if (size == null) {
            // if the cache size property does not exist, use the default value - SimpleCache.NO_MAX_SIZE;
            size = new Integer(SimpleCache.NO_MAX_SIZE);
        }

        // create the SimpleCache instance and return it
        return new SimpleCache(size.intValue(), timeout.longValue(), new LRUCacheEvictionStrategy());
    }


    /**
     * Loads the persistence property from the given configuration namespace and creates the
     * TemplateHierarchyPersistence instance.
     *
     * @param namespace the namespace containing the property.
     * @throws ConfigurationException if any error occurred while loading the property
     * @return the loaded TemplateHierarchyPersistence instance.
     */
    private static TemplateHierarchyPersistence loadPersistence(String namespace) throws ConfigurationException {
        TemplateLoaderHelper.checkString(namespace, "namespace");

        Property persistence = null;
        try {
            // loads the cache property from the given configuration namespace
            persistence = ConfigManager.getInstance().getPropertyObject(namespace, PERSISTENCE_PROPERTY);
        } catch (UnknownNamespaceException une) {
            // if the namespace does not exist, throw an exception
            throw new ConfigurationException("the given namespace does not exist", une);
        }

        // if the persistence property does not exist, throw an exception
        if (persistence == null) {
            throw new ConfigurationException(PERSISTENCE_PROPERTY + " property does not exist");
        }

        // retrieve persistence class name
        String persistenceClass = persistence.getValue(PERSISTENCE_CLASS_PROPERTY);

        // if the class name does not exist, throw an exception
        if (persistenceClass == null) {
            throw new ConfigurationException(PERSISTENCE_CLASS_PROPERTY + " property does not exist");
        }


        // retrieve the config property
        Property config = persistence.getProperty(PERSISTENCE_CONFIG_PROPERTY);
        try {
            // create the TemplateHierarchyPersistence instance via reflection
            if (config == null) {
                return (TemplateHierarchyPersistence) Class.forName(persistenceClass).newInstance();
            } else {

                Constructor constructor = Class.forName(persistenceClass).getConstructor(new Class[] {Property.class});
                return (TemplateHierarchyPersistence) constructor.newInstance(new Object[] {config});
            }
        } catch (Exception e) {
            // catch any exception and wrap it with ConfigurationException
            throw new ConfigurationException(
                "error occurred while creating the TemplateHierarchyPersistence instance", e);
        }
    }

    /**
     * Gets the specified long value from given Property.
     *
     * @param parent the Property containing the value
     * @param key the name of the property
     * @return the specified integer value.
     * @throws ConfigurationException if the integer value is invalid.
     */
    private static Long getLongProperty(Property parent, String key) throws ConfigurationException {

        // retrieve the specified value, and return null, if it does not exist
        String value = parent.getValue(key);
        if (value == null) {
            return null;
        }

        // parse the retrieved value, and throw an exception if it's invalid
        Long result = null;
        try {
            result = Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            throw new ConfigurationException(key + " property is invalid long.", nfe);
        }

        // if the long value is non-positive, throw an exception
        if (result.longValue() <= 0) {
            throw new ConfigurationException("the value should be positive");
        }
        return result;
    }

    /**
     * Gets the specified integer value from give Property.
     *
     * @param parent the Property containing the value
     * @param key the name of the property
     * @return the specified integer value.
     * @throws ConfigurationException if the integer value is invalid.
     */
    private static Integer getIntProperty(Property parent, String key) throws ConfigurationException {

        // retrieve the specified value, and return null, if it does not exist
        String value = parent.getValue(key);
        if (value == null) {
            return null;
        }

        // parse the retrieved value, and throw an exception if it's invalid
        Integer result = null;
        try {
            result = Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            throw new ConfigurationException(key + " property is invalid integer.", nfe);
        }

        // if the long value is non-positive, throw an exception
        if (result.intValue() <= 0) {
            throw new ConfigurationException("the value should be positive");
        }
        return result;
    }



    /**
     * <p>Gets the top-level template hierarchy corresponding to specified name. Note that this TemplateLoader
     * may return a cached <code>TemplateHierachy</code> if this <code>TemplateLoader</code> was instructed to cache
     * any template hierarchies loaded from persistent data store and the requested template hierarchy was already
     * loaded.</p>
     *
     * @throws UnknownTemplateHierarchyException if the specified top-level template hierarchy is not recognized.
     * @throws NullPointerException if specified argument is <code>null</code>.
     * @throws IllegalArgumentException if specified argument is an empty <code>String</code> being trimmed.
     * @throws PersistenceException if any unexpected error occurs.
     *
     * @return a <code>TemplateHierarchy</code> corresponding to specified name.
     * @param name a <code>String</code> providing the name of requested template hierarchy. Note that the provided
     * name must correspond to a top-level hierarchy which is not nested within any parent template hierarchy.
     */
    public synchronized TemplateHierarchy loadTemplateHierarchy(String name) throws PersistenceException {


        TemplateLoaderHelper.checkString(name, "name");

        // retrieve the cached instance
        TemplateHierarchy result = (TemplateHierarchy) cache.get(name);

        // if the required instance is not in the cache,
        // retrieve it from the persistence, and put it back to the cache
        if (result == null) {
            result = persistence.getTemplateHierarchy(name);
            result.setFileServerUri(fileServerUri);
            cache.put(name, result);
        }

        return result;
    }

}

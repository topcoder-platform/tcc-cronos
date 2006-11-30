/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import org.apache.cactus.ServletTestCase;

import com.orpheus.user.persistence.ConfigHelper;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * The base class of the SQLServerPendingConfirmationDAOTest and
 * SQLServerUserProfileDAOTest test cases. It takes care of all the common
 * configuration handling, such as testing for invalid configuration, etc.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public abstract class DAOTestBase extends ServletTestCase {

    /**
     * <p>
     * A test DAO configuration file containing valid configuration. This
     * file contains configuration for instantiating a DAO instance with and
     * without a namespace given to its constructor.
     * </p>
     */
    protected static final String VALID_CONFIG_FILE = "test_conf/valid_dao_config.xml";

    /**
     * <p>
     * A test DAO configuration file containing invalid configuration.
     * </p>
     */
    protected static final String INVALID_CONFIG_FILE = "test_conf/invalid_dao_config.xml";

    /**
     * <p>
     * The prefix for the test DAO configuration namespaces.
     * </p>
     */
    protected static final String NAMESPACE_PREFIX = "com.orpheus.user.persistence.impl.DAO";

    /**
     * <p>
     * The configuration namespace containing valid DAO configuration. The
     * "name" property is present.
     * </p>
     */
    protected static final String VALID_NAMESPACE_WITH_CONN_NAME = NAMESPACE_PREFIX + ".validWithConnName";

    /**
     * <p>
     * The configuration namespace containing valid DAO configuration. The
     * "name" property is missing.
     * </p>
     */
    protected static final String VALID_NAMESPACE_WITHOUT_CONN_NAME = NAMESPACE_PREFIX + ".validWithoutConnName";

    /**
     * <p>
     * The namespace from which to read valid Object Factory configuration for
     * the DAO class.
     * </p>
     */
    private static final String VALID_OBJFACTORY_NAMESPACE = NAMESPACE_PREFIX + ".objFactory";;

    /**
     * <p>
     * An invalid DAO configuration namespace where the "specNamespace" property
     * is missing.
     * </p>
     */
    private static final String MISSING_SPECNAMESPACE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".missingSpecNamespace";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "specNamespace" property
     * value is an empty string.
     * </p>
     */
    private static final String EMPTY_SPECNAMESPACE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".emptySpecNamespace";;

    /**
     * <p>
     * An invalid DAO configuration namespace where the "specNamespace" property
     * refers to an unknown Object Factory configuration namespace.
     * </p>
     */
    private static final String INVALID_SPECNAMESPAE_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".invalidSpecNamespace";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "factoryKey" property is
     * missing.
     * </p>
     */
    private static final String MISSING_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".missingFactoryKey";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "factoryKey" property
     * value is an empty string.
     * </p>
     */
    private static final String EMPTY_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".emptyFactoryKey";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "factoryKey" property
     * refers to an unknown Object Factory key.
     * </p>
     */
    private static final String INVALID_FACTORYKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".invalidFactoryKey";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "cacheKey" property is
     * missing.
     * </p>
     */
    private static final String MISSING_CACHEKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".missingCacheKey";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "cacheKey" property
     * value is an empty string.
     * </p>
     */
    private static final String EMPTY_CACHEKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".emptyCacheKey";

    /**
     * <p>
     * An invalid DAO configuration namespace where the "cacheKey" property
     * refers to an unknown Object Factory key.
     * </p>
     */
    private static final String INVALID_CACHEKEY_PROPERTY_NAMESPACE = NAMESPACE_PREFIX + ".invalidCacheKey";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the
     * DBConnectionFactory object specification is incorrect/incomplete.
     * </p>
     */
    private static final String INVALID_FACTORY_SPECIFICATION_NAMESPACE
        = VALID_OBJFACTORY_NAMESPACE + ".invalidFactorySpec";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the factory
     * object to create is not of type DBConnetionFactory.
     * </p>
     */
    private static final String WRONG_FACTORY_CLASS_NAMESPACE = VALID_OBJFACTORY_NAMESPACE + ".wrongFactoryClass";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the Cache object
     * specification is incorrect/incomplete.
     * </p>
     */
    private static final String INVALID_CACHE_SPECIFICATION_NAMESPACE
        = VALID_OBJFACTORY_NAMESPACE + ".invalidCacheSpec";

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the cache object
     * to create is not of type Cache.
     * </p>
     */
    private static final String WRONG_CACHE_CLASS_NAMESPACE = VALID_OBJFACTORY_NAMESPACE + ".wrongCacheClass";

    /**
     * <p>
     * Loads the test configuration namespaces into the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, VALID_NAMESPACE_WITH_CONN_NAME);
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, VALID_NAMESPACE_WITHOUT_CONN_NAME);
        ConfigHelper.loadConfig(VALID_CONFIG_FILE, VALID_OBJFACTORY_NAMESPACE);
        ConfigHelper.loadCacheConfig();
        ConfigHelper.loadDBConnectionFactoryConfig();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(VALID_NAMESPACE_WITH_CONN_NAME);
        ConfigHelper.unloadConfig(VALID_NAMESPACE_WITHOUT_CONN_NAME);
        ConfigHelper.unloadConfig(VALID_OBJFACTORY_NAMESPACE);
        ConfigHelper.unloadCacheConfig();
        ConfigHelper.unloadDBConnectionFactoryConfig();
    }

    /**
     * <p>
     * Tests that the DAO class constructor throws an
     * ObjectInstantiationException when the "specNamespace" configuration
     * property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(MISSING_SPECNAMESPACE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor throws an
     * ObjectInstantiationException when the "specNamespace" configuration
     * property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptySpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(EMPTY_SPECNAMESPACE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "specNamespace" configuration
     * property refers to an unknown Object Factory configuration namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_SPECNAMESPAE_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "factoryKey" configuration property
     * is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(MISSING_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "factoryKey" configuration property
     * value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(EMPTY_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "factoryKey" configuration property
     * does not refer to a factory key specified in the Object Factory
     * configuration.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidFactoryKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_FACTORYKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the Object Factory configuration
     * contains an invalid DBConnectionFactory object specification.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidFactoryObjectSpecification() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_FACTORY_SPECIFICATION_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the object to create with the
     * "factoryKey" configuration property refers to an object which is not of
     * type DBConnectionFactory.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithWrongFactoryClassInObjFactoryConfig() throws Exception {
        performCtorTestWithInvalidConfig(WRONG_FACTORY_CLASS_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "cacheKey" configuration property
     * is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(MISSING_CACHEKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "cacheKey" configuration property
     * value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(EMPTY_CACHEKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the "cacheKey" configuration property
     * does not refer to a cache key specified in the Object Factory
     * configuration.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_CACHEKEY_PROPERTY_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the Object Factory configuration
     * contains an invalid Cache object specification.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidCacheObjectSpecification() throws Exception {
        performCtorTestWithInvalidConfig(INVALID_CACHE_SPECIFICATION_NAMESPACE);
    }

    /**
     * <p>
     * Tests that the DAO class constructor constructor throws an
     * ObjectInstantiationException when the object to create with the
     * "cacheKey" configuration property refers to an object which is not of
     * type Cache.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithWrongCacheClassInObjFactoryConfig() throws Exception {
        performCtorTestWithInvalidConfig(WRONG_CACHE_CLASS_NAMESPACE);
    }

    /**
     * <p>
     * Performs the DAO class constructor test with invalid configuration. An
     * ObjectInstantiationException is expected to be thrown.
     * </p>
     *
     * @param namespace the namespace containing the invalid configuration
     * @throws ConfigManagerException if loading the invalid configuration
     *         namespace fails
     */
    protected abstract void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException;

}

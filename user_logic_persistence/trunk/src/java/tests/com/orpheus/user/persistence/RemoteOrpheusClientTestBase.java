/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.TestCase;

import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * The base class of the OrpheusPendingConfirmationStorageTest,
 * OrpheusUserProfilePersistenceTest and RemoteOrpheusEJBClientTestBase test
 * cases. It takes care of all the common configuration handling, such as
 * testing for invalid configuration, etc.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public abstract class RemoteOrpheusClientTestBase extends TestCase {

    /**
     * <p>
     * A test client configuration file containing valid configuration.
     * </p>
     */
    private final String validConfigFile;

    /**
     * <p>
     * The namespace from which to read valid client configuration. This
     * configuration includes the optional "name" property.
     * </p>
     */
    private final String validClientNamespace;

    /**
     * <p>
     * The namespace from which to read valid Object Factory configuration for
     * the client class.
     * </p>
     */
    private final String validObjFactoryNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "specNamespace"
     * property is missing.
     * </p>
     */
    private final String missingSpecNamespacePropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "specNamespace"
     * property value is an empty string.
     * </p>
     */
    private final String emptySpecNamespacePropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "specNamespace"
     * property refers to an unknown Object Factory configuration namespace.
     * </p>
     */
    private final String invalidSpecNamespacePropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "translatorKey"
     * property is missing.
     * </p>
     */
    private final String missingTranslatorKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "tanslatorKey"
     * property value is an empty string.
     * </p>
     */
    private final String emptyTranslatorKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "translatorKey"
     * property refers to an unknown Object Factory key.
     * </p>
     */
    private final String invalidTranslatorKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "cacheKey" property
     * is missing.
     * </p>
     */
    private final String missingCacheKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "cacheKey" property
     * value is an empty string.
     * </p>
     */
    private final String emptyCacheKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "cacheKey" property
     * refers to an unknown Object Factory key.
     * </p>
     */
    private final String invalidCacheKeyPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "log" property is
     * missing.
     * </p>
     */
    private final String missingLogPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "log" property value
     * is an empty string.
     * </p>
     */
    private final String emptyLogPropertyNamespace;

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the
     * ObjectTranslator object specification is incorrect/incomplete.
     * </p>
     */
    private final String invalidTranslatorSpecificationNamespace;

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the factory
     * object to create is not of type ObjectTranslator.
     * </p>
     */
    private final String wrongTranslatorClassNamespace;

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the Cache object
     * specification is incorrect/incomplete.
     * </p>
     */
    private final String invalidCacheSpecificationNamespace;

    /**
     * <p>
     * An invalid Object Factory configuration namespace where the cache object
     * to create is not of type Cache.
     * </p>
     */
    private final String wrongCacheClassNamespace;

    /**
     * <p>
     * Creates a new OrpheusClientTestBase with the given namespace prefix,
     * valid client namespace and valid client configuration file. The namespace
     * prefix is used to construct the names of all the test namespaces.
     * </p>
     *
     * @param namespacePrefix the client namespace prefix
     * @param validClientNamespace the configuration namespace containing valid
     *        client configuration
     * @param validConfigFile the valid client configuration file
     */
    protected RemoteOrpheusClientTestBase(String namespacePrefix, String validClientNamespace, String validConfigFile) {
        this.validClientNamespace = validClientNamespace;
        this.validConfigFile = validConfigFile;

        // Create all the test namespaces from the namespace prefix.
        validObjFactoryNamespace = namespacePrefix + ".objFactory";
        missingSpecNamespacePropertyNamespace = namespacePrefix + ".missingSpecNamespace";
        emptySpecNamespacePropertyNamespace = namespacePrefix + ".emptySpecNamespace";
        invalidSpecNamespacePropertyNamespace = namespacePrefix + ".invalidSpecNamespace";
        missingTranslatorKeyPropertyNamespace = namespacePrefix + ".missingTranslatorKey";
        emptyTranslatorKeyPropertyNamespace = namespacePrefix + ".emptyTranslatorKey";
        invalidTranslatorKeyPropertyNamespace = namespacePrefix + ".invalidTranslatorKey";
        missingCacheKeyPropertyNamespace = namespacePrefix + ".missingCacheKey";
        emptyCacheKeyPropertyNamespace = namespacePrefix + ".emptyCacheKey";
        invalidCacheKeyPropertyNamespace = namespacePrefix + ".invalidCacheKey";
        missingLogPropertyNamespace = namespacePrefix + ".missingLog";
        emptyLogPropertyNamespace = namespacePrefix + ".emptyLog";
        invalidTranslatorSpecificationNamespace = validObjFactoryNamespace + ".invalidTranslatorSpec";
        wrongTranslatorClassNamespace = validObjFactoryNamespace + ".wrongTranslatorClass";
        invalidCacheSpecificationNamespace = validObjFactoryNamespace + ".invalidCacheSpec";
        wrongCacheClassNamespace = validObjFactoryNamespace + ".wrongCacheClass";
    }

    /**
     * <p>
     * Loads the test configuration namespaces into the Configuration Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadConfig(validConfigFile, validObjFactoryNamespace);
        ConfigHelper.loadConfig(validConfigFile, validClientNamespace);
        ConfigHelper.loadLoggerConfig();
        ConfigHelper.loadCacheConfig();
        ConfigHelper.loadDBConnectionFactoryConfig();
        ConfigHelper.loadProfileTypesConfig();
    }

    /**
     * <p>
     * Unloads the test configuration namespaces from the Configuration Manager.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.unloadConfig(validObjFactoryNamespace);
        ConfigHelper.unloadConfig(validClientNamespace);
        ConfigHelper.unloadLoggerConfig();
        ConfigHelper.unloadCacheConfig();
        ConfigHelper.unloadDBConnectionFactoryConfig();
        ConfigHelper.unloadProfileTypesConfig();
    }

    /**
     * <p>
     * Tests that the client class constructor throws some Exception when the
     * "specNamespace" configuration property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(missingSpecNamespacePropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor throws some Exception when the
     * "specNamespace" configuration property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptySpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(emptySpecNamespacePropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "specNamespace" configuration property refers to an unknown
     * Object Factory configuration namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidSpecNamespaceConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(invalidSpecNamespacePropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "translatorKey" configuration property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingTranslatorKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(missingTranslatorKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "translatorKey" configuration property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyTranslatorKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(emptyTranslatorKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "translatorKey" configuration property does not refer to a
     * translator key specified in the Object Factory configuration.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidTranslatorKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(invalidTranslatorKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the Object Factory configuration contains an invalid
     * ObjectTranslator object specification.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidTranslatorObjectSpecification() throws Exception {
        performCtorTestWithInvalidConfig(invalidTranslatorSpecificationNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the object to create with the "translatorKey" configuration property
     * refers to an object which is not of type ObjectTranslator.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithWrongTranslatorClassInObjFactoryConfig() throws Exception {
        performCtorTestWithInvalidConfig(wrongTranslatorClassNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "log" configuration property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingLogConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(missingLogPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "log" configuration property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyLogConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(emptyLogPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "cacheKey" configuration property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(missingCacheKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "cacheKey" configuration property value is an empty string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(emptyCacheKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "cacheKey" configuration property does not refer to a cache key
     * specified in the Object Factory configuration.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidCacheKeyConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(invalidCacheKeyPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the Object Factory configuration contains an invalid Cache object
     * specification.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidCacheObjectSpecification() throws Exception {
        performCtorTestWithInvalidConfig(invalidCacheSpecificationNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the object to create with the "cacheKey" configuration property
     * refers to an object which is not of type Cache.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithWrongCacheClassInObjFactoryConfig() throws Exception {
        performCtorTestWithInvalidConfig(wrongCacheClassNamespace);
    }

    /**
     * <p>
     * Performs the class constructor test with invalid configuration. The given
     * invalid configuration namespace is loaded into the Configuration Manager.
     * Then, the constructor is called with the given namespace. Some Exception
     * is expected to be thrown.
     * </p>
     * <p>
     * The given configuration namespace is unloaded from the Configuration
     * Manager after the test has been performed. This will occur whether the
     * test passed or failed.
     * </p>
     *
     * @param namespace the invalid configuration namespace to load into the
     *        Configuration Manager prior to running the test
     * @throws ConfigManagerException if loading or unloading configuration
     *         namespace fails
     */
    protected abstract void performCtorTestWithInvalidConfig(String namespace) throws ConfigManagerException;

}

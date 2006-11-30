/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

/**
 * <p>
 * The base class of the RemoteOrpheusPendingConfirmationStorageTest and
 * RemoteOrpheusUserProfilePersistenceTest test cases. It takes care of all the
 * common configuration handling, such as testing for invalid configuration,
 * etc.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public abstract class RemoteOrpheusEJBClientTestBase extends RemoteOrpheusClientTestBase {

    /**
     * <p>
     * An invalid client configuration namespace where the "jndiEjbReference"
     * property is missing.
     * </p>
     */
    private final String missingJndiRefPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "jndiEjbReference"
     * property value is an empty string.
     * </p>
     */
    private final String emptyJndiRefPropertyNamespace;

    /**
     * <p>
     * An invalid client configuration namespace where the "jndiEjbReference"
     * property refers to an unknown Object Factory key.
     * </p>
     */
    private final String invalidJndiRefPropertyNamespace;

    /**
     * <p>
     * Creates a new OrpheusEJBClientTestBase with the given namespace prefix,
     * valid client namespac and valid client configuration file. The namespace
     * prefix is used to construct the names of all the test namespaces.
     * </p>
     *
     * @param namespacePrefix the client namespace prefix
     * @param validClientNamespace the configuration namespace containing valid
     *        client configuration
     * @param validConfigFile the valid client configuration file
     */
    protected RemoteOrpheusEJBClientTestBase(String namespacePrefix, String validClientNamespace,
            String validConfigFile) {
        super(namespacePrefix, validClientNamespace, validConfigFile);
        missingJndiRefPropertyNamespace = namespacePrefix + ".missingJndiRef";
        emptyJndiRefPropertyNamespace = namespacePrefix + ".emptyJndiRef";
        invalidJndiRefPropertyNamespace = namespacePrefix + ".invalidJndiRef";
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "jndiEjbReference" configuration property is missing.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithMissingJndiRefConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(missingJndiRefPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "jndiEjbReference" configuration property value is an empty
     * string.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithEmptyJndiRefConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(emptyJndiRefPropertyNamespace);
    }

    /**
     * <p>
     * Tests that the client class constructor constructor throws some Exception
     * when the "jndiEjbReference" configuration property does not refer to an
     * existing EJB.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtorWithInvalidJndiRefConfigProperty() throws Exception {
        performCtorTestWithInvalidConfig(invalidJndiRefPropertyNamespace);
    }

}

/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class is the default implementation of ApplicationsManager interface. It holds and instance of
 * ReviewApplicationPersistence and delegates all job to ReviewApplicationPersistence instance. It provides CRUD
 * operations of ReviewApplication entity.
 * </p>
 *
 * <p>
 * The default configuration namespace for this class is:
 * &quot;com.topcoder.management.project.ApplicationsManagerImpl&quot;. It can accept a custom namespace as well.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it's immutable and thread safe.
 * </p>
 *
 * <p>
 * <b>Sample usage</b>:
 * </p>
 *
 * <pre>
 * // create an instance of ReviewerStatisticsManagerImpl
 * ApplicationsManagerImpl manager = new ApplicationsManagerImpl(ApplicationsManagerImpl.class.getName());
 * ReviewApplication reviewA = new ReviewApplication();
 *
 * // set properties of ReviewApplication
 * reviewA.setApplicationDate(new Date());
 * reviewA.setReviewerId(19);
 * reviewA.setProjectId(1);
 * reviewA.setAcceptPrimary(true);
 *
 * // create ReviewApplication
 * reviewA = manager.create(reviewA);
 * // reviewA has new ID provided by review_application_id_seq + 1
 *
 * // create another
 * ReviewApplication reviewB = new ReviewApplication();
 * reviewB.setApplicationDate(reviewA.getApplicationDate());
 * reviewB.setReviewerId(20);
 * reviewB.setAcceptPrimary(reviewA.isAcceptPrimary());
 * reviewB.setProjectId(reviewA.getProjectId());
 * reviewB = manager.create(reviewB);
 * // reviewB has new ID equal to reviewA ID plus 1
 *
 * // update ReviewApplication
 * reviewB.setAcceptPrimary(false);
 * reviewB = manager.update(reviewB);
 *
 * // retrieve ReviewApplication by id
 * ReviewApplication rc = manager.retrieve(reviewB.getId());
 * // rc should be the same as reviewB
 *
 * // delete ReviewApplication
 * manager.delete(reviewB.getId());
 * // reviewB is deleted
 *
 * // re-create reviewB
 * reviewB = manager.create(reviewB);
 * // reviewB has new ID equal to reviewA ID plus 2
 *
 * // get primary applications
 * ReviewApplication[] primaryApps = manager.getPrimaryApplications(1);
 * // an array containing reviewA should be returned
 *
 * // get secondary applications
 * ReviewApplication[] secondaryApps = manager.getSecondaryApplications(1);
 * // an array containing reviewB should be returned
 *
 * // get all applications
 * ReviewApplication[] allApps = manager.getAllApplications(1);
 * // an array containing reviewA and reviewB should be returned
 * </pre>
 *
 * @author moonli, pvmagacho
 * @version 1.1
 * @since 1.1
 */
public class ApplicationsManagerImpl implements ApplicationsManager {
    /**
     * Represents the default configuration namespace.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.management.project.ApplicationsManagerImpl";

    /**
     * Represents the persistence class property name.
     */
    private static final String PERSISTENCE_CLASS = "PersistenceClassName";

    /**
     * Represents the persistence namespace property name.
     */
    private static final String PERSISTENCE_NAMESPACE = "PersistenceNamespace";

    /**
     * Represents an instance of ReviewApplicationPersistence that will be used to access underlying persistence for
     * ReviewApplication data.
     *
     * It's initialized in ctor, won't change afterwards. Cannot be null. It is used in all method of this class.
     */
    private final ReviewApplicationPersistence persistence;

    /**
     * Creates an instance of this class.
     *
     * <pre>
     * Sample usage for configuration file:
     *
     * &lt;Config name="com.topcoder.management.project.ApplicationsManagerImpl"&gt;
     *   &lt;Property name="PersistenceClassName"&gt;
     *     &lt;Value&gt;com.topcoder.management.project.persistence.InformixReviewApplicationPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="PersistenceNamespace"&gt;
     *     &lt;Value&gt;com.topcoder.management.project.persistence.InformixReviewApplicationPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @throws ReviewApplicationConfigurationException if any error occurred during configuration
     */
    public ApplicationsManagerImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Creates an instance of this class.
     *
     * <pre>
     * Sample usage for configuration file:
     *
     * &lt;Config name="com.topcoder.management.project.ApplicationsManagerImpl"&gt;
     *   &lt;Property name="PersistenceClassName"&gt;
     *     &lt;Value&gt;com.topcoder.management.project.persistence.InformixReviewApplicationPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="PersistenceNamespace"&gt;
     *     &lt;Value&gt;com.topcoder.management.project.persistence.InformixReviewApplicationPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if namespace is null or empty
     * @throws ReviewApplicationConfigurationException if any error occurred during configuration
     */
    public ApplicationsManagerImpl(String namespace) {
        Helper.checkStringNotNullOrEmpty(namespace, "namespace");

        // get config manager instance.
        ConfigManager cm = ConfigManager.getInstance();
        try {
            // get PersistenceClass property.
            String persistenceClassName = cm.getString(namespace, PERSISTENCE_CLASS);
            // assert perisitenceClass not null or empty.
            Helper.checkStringNotNullOrEmpty(persistenceClassName, "persistenceClassName");

            // get PersistenceNamespace property.
            String persistenceNamespace = cm.getString(namespace, PERSISTENCE_NAMESPACE);

            // if PersistenceNamespace property is not exist, use
            // persistenceClass instead.
            if (persistenceNamespace == null) {
                persistenceNamespace = persistenceClassName;
            }

            // create persistence and validator
            persistence = (ReviewApplicationPersistence) createObject(persistenceClassName, persistenceNamespace);
        } catch (IllegalArgumentException iae) {
            throw new ReviewApplicationConfigurationException("some property is missed: " + iae.getMessage(), iae);
        } catch (UnknownNamespaceException une) {
            throw new ReviewApplicationConfigurationException(namespace + " namespace is unknown.", une);
        } catch (ClassCastException cce) {
            throw new ReviewApplicationConfigurationException("error occurs: " + cce.getMessage(), cce);
        }
    }

    /**
     * This private method is used to create object via reflection.
     *
     * @param className className to use
     * @param namespace namespace to use
     * @throws ReviewApplicationConfigurationException if any error occurs
     * @return the object created.
     */
    private static Object createObject(String className, String namespace) {
        try {
            // get constructor
            Constructor<?> constructor = Class.forName(className).getConstructor(new Class[] {String.class});
            // create object
            return constructor.newInstance(new Object[] {namespace});
        } catch (ClassNotFoundException cnfe) {
            throw new ReviewApplicationConfigurationException(
                "error occurs when trying to create object via reflection.", cnfe);
        } catch (NoSuchMethodException nsme) {
            throw new ReviewApplicationConfigurationException(
                "error occurs when trying to create object via reflection.", nsme);
        } catch (InstantiationException ie) {
            throw new ReviewApplicationConfigurationException(
                "error occurs when trying to create object via reflection.", ie);
        } catch (IllegalAccessException iae) {
            throw new ReviewApplicationConfigurationException(
                "error occurs when trying to create object via reflection.", iae);
        } catch (InvocationTargetException ite) {
            throw new ReviewApplicationConfigurationException(
                "error occurs when trying to create object via reflection.", ite);
        }
    }

    /**
     * Create a new review application in persistence.
     *
     * @param reviewApplication the new review application to create, cannot be null. reviewId and projectId should be
     *            positive return
     *
     * @return the created review application
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication create(ReviewApplication reviewApplication) throws ApplicationsManagerException {
        return persistence.create(reviewApplication);
    }

    /**
     * Updates existing review application.
     *
     * @param reviewApplication the review application to update, cannot be null. reviewId and projectId should be
     *            positive return
     *
     * @return the updated review application
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication update(ReviewApplication reviewApplication) throws ApplicationsManagerException {
        return persistence.update(reviewApplication);
    }

    /**
     * Retrieve the review application by id.
     *
     * @param id id of the review application to retrieve, should be positive
     * @return the retrieved review application, or null if not found
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication retrieve(long id) throws ApplicationsManagerException {
        return persistence.retrieve(id);
    }

    /**
     * Deletes the review application by ID.
     *
     * @param id id of the review application to delete, should be positive
     * @return true if review application is deleted, otherwise false (id doesn't exist in persistence)
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public boolean delete(long id) throws ApplicationsManagerException {
        return persistence.delete(id);
    }

    /**
     * Gets primary review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication[] getPrimaryApplications(long projectId) throws ApplicationsManagerException {
        return persistence.getPrimaryApplications(projectId);
    }

    /**
     * Gets secondary review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication[] getSecondaryApplications(long projectId) throws ApplicationsManagerException {
        return persistence.getSecondaryApplications(projectId);
    }

    /**
     * Gets all review applications for specified project.
     *
     * @param projectId id of the project, should be positive
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ApplicationsManagerException if any other error occurred during operation
     */
    public ReviewApplication[] getAllApplications(long projectId) throws ApplicationsManagerException {
        return persistence.getAllApplications(projectId);
    }
}

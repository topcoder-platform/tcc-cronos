/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.services.impl;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.utilities.cp.entities.DirectProjectCPConfig;
import com.topcoder.utilities.cp.entities.MemberContributionPoints;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundException;
import com.topcoder.utilities.cp.services.ContributionServiceException;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationException;

/**
 * <p>
 * This class is a base for all services defined in this component. It holds a Log4j logger instance to be used by
 * subclasses for logging errors and debug information, together with the default page size parameter. Additionally it
 * provides protected methods for accessing the details of the currently logged in user from persistence.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable since it provides public setters for its properties, and
 * thus not thread safe. Its subclasses can be used in thread safe manner assuming that configuration parameters are
 * never changed after being initialized by Spring.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
abstract class BaseService {
    /**
     * The logger to be used by subclasses for logging errors and debug information. Can be initialized in the
     * setLoggerName() method. If is null, logging is not performed. Has a protected getter.
     */
    private Log log;

    /**
     * <p>
     * An instance of the SessionFactory, which will be used as a link to the Hibernate API. Is initialized with
     * Spring setter dependency injection. Cannot be null after initialization, assuming that property is initialized
     * via Spring setter-based dependency injection and is never changed after that (note that check is performed in
     * checkInit() method instead of the setter). Has a protected setter and a protected getter to be used by
     * subclasses.
     * </p>
     */
    private SessionFactory sessionFactory;

    /**
     * Creates an instance of BaseService.
     */
    protected BaseService() {
        // Empty
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws ContributionServiceInitializationException
     *             if the class was not initialized properly (sessionFactory is <code>null</code>).
     */
    @PostConstruct
    protected void checkInit() {
        ValidationUtility.checkNotNull(sessionFactory, "sessionFactory",
            ContributionServiceInitializationException.class);
    }

    /**
     * Sets the name of the logger to be used by this class.
     *
     * @param loggerName
     *            the name of the logger to be used by this class (null if logging is not required)
     *
     * @throws ContributionServiceInitializationException
     *             if loggerName is empty.
     */
    public void setLoggerName(String loggerName) {
        ValidationUtility.checkNotEmptyAfterTrimming(loggerName, "loggerName",
            ContributionServiceInitializationException.class);

        log = (loggerName == null) ? null : LogManager.getLog(loggerName);
    }

    /**
     * Retrieves the logger to be used by subclasses for logging errors and debug information.
     *
     * @return the logger to be used by subclasses for logging errors and debug information
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Sets the instance of the SessionFactory, which will be used as a link to the Hibernate API.
     *
     * @param sessionFactory
     *            the instance of the SessionFactory, which will be used as a link to the Hibernate API.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the instance of the SessionFactory, which will be used as a link to the Hibernate API.
     *
     * @return the instance of the SessionFactory, which will be used as a link to the Hibernate API.
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Creates the entity.
     *
     * @param signature
     *            the signature.
     * @param entity
     *            the entity.
     * @param entityName
     *            the entity name.
     * @param id
     *            the entity id.
     * @param idName
     *            the entity id name.
     *
     * @return the ID of the created entity.
     *
     * @throws IllegalArgumentException
     *             if entity is null or id is invalid.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    protected long createEntity(String signature, Object entity, String entityName, long id, String idName)
        throws ContributionServiceException {
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {entityName},
            new Object[] {entity});

        try {
            ParameterCheckUtility.checkNotNull(entity, entityName);

            if (entity instanceof MemberContributionPoints) {
                ParameterCheckUtility.checkNotPositive(id, idName);

                checkId(signature, "checkMCPContestId", ((MemberContributionPoints) entity).getContestId(),
                    "memberContributionPoints#contestId");
            } else {
                ParameterCheckUtility.checkPositive(id, idName);

                checkId(signature,
                    (entity instanceof DirectProjectCPConfig) ? "checkDPDirectProjectId" : "checkPCContestId",
                    id, idName);

                if (getEntity(entity.getClass(), id, false) != null) {
                    // Log exception
                    throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                        "The entity with the ID '" + id + "' already exists."));
                }
            }

            long result = (Long) sessionFactory.getCurrentSession().save(entity);

            LoggingWrapperUtility.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to create the entity.", e));
        }
    }

    /**
     * Updates the entity.
     *
     * @param signature
     *            the signature.
     * @param entity
     *            the entity.
     * @param entityName
     *            the entity name.
     * @param id
     *            the entity id.
     * @param idName
     *            the entity id name.
     *
     * @throws IllegalArgumentException
     *             if entity is null or id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    protected void updateEntity(String signature, Object entity, String entityName, long id, String idName)
        throws ContributionServiceException {
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {entityName},
            new Object[] {entity});

        try {
            ParameterCheckUtility.checkNotNull(entity, entityName);
            ParameterCheckUtility.checkPositive(id, idName);

            getEntity(entity.getClass(), id, true);

            sessionFactory.getCurrentSession().merge(entity);

            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ContributionServiceEntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to update the entity.", e));
        }
    }

    /**
     * Deletes the entity.
     *
     * @param <T>
     *            the entity type.
     * @param signature
     *            the signature.
     * @param clazz
     *            the entity class.
     * @param id
     *            the entity id.
     * @param idName
     *            the entity id name.
     *
     * @throws IllegalArgumentException
     *             if entity is null or id is not positive.
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    protected <T> void deleteEntity(String signature, Class<T> clazz, long id, String idName)
        throws ContributionServiceException {
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {idName},
            new Object[] {id});

        try {
            ParameterCheckUtility.checkPositive(id, idName);

            T entity = getEntity(clazz, id, true);

            sessionFactory.getCurrentSession().delete(entity);

            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ContributionServiceEntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to delete the entity.", e));
        }
    }

    /**
     * Gets the entity by id.
     *
     * @param <T>
     *            the entity type.
     * @param signature
     *            the signature.
     * @param clazz
     *            the entity class.
     * @param id
     *            the entity id.
     * @param idName
     *            the entity id name.
     *
     * @return the entity or <code>null</code> if the entity doesn't exist.
     *
     * @throws IllegalArgumentException
     *             if id is not positive.
     * @throws ContributionServiceException
     *             if any error occurs.
     */
    protected <T> T getEntity(String signature, Class<T> clazz, long id, String idName)
        throws ContributionServiceException {
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {idName},
            new Object[] {id});

        try {
            ParameterCheckUtility.checkPositive(id, idName);

            T entity = getEntity(clazz, id, false);

            LoggingWrapperUtility.logExit(log, signature, new Object[] {entity});
            return entity;
        } catch (HibernateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(
                "Failed to get the entity.", e));
        }
    }

    /**
     * Gets the entity by id.
     *
     * @param <T>
     *            the entity type.
     * @param clazz
     *            the entity class.
     * @param id
     *            the entity id.
     * @param required
     *            <code>true</code> if the entity should exist; <code>false</code> otherwise.
     *
     * @return the entity or <code>null</code> if the entity doesn't exist (when required is <code>false</code>).
     *
     * @throws ContributionServiceEntityNotFoundException
     *             if the entity cannot be found (when required is <code>true</code>).
     * @throws HibernateException
     *             if any error occurs.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getEntity(Class<T> clazz, long id, boolean required)
        throws ContributionServiceEntityNotFoundException {
        T entity = (T) sessionFactory.getCurrentSession().get(clazz, id);
        if ((entity == null) && required) {
            throw new ContributionServiceEntityNotFoundException("The entity with ID '" + id + "' cannot be found.",
                clazz.getName(), id);
        }

        return entity;
    }

    /**
     * Checks if the id exists in the database.
     *
     * @param signature
     *            the signature.
     * @param namedQuery
     *            the named query.
     * @param id
     *            the id.
     * @param idName
     *            the id name.
     *
     * @throws ContributionServiceException
     *             if the id doesn't exist in the database.
     */
    private void checkId(String signature, String namedQuery, long id, String idName)
        throws ContributionServiceException {
        long num = Long.valueOf(sessionFactory.getCurrentSession().getNamedQuery(namedQuery).setLong("id", id)
            .uniqueResult().toString());
        if (num != 1) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ContributionServiceException(idName + " ("
                + id + ") doesn't exist in the database."));
        }
    }
}

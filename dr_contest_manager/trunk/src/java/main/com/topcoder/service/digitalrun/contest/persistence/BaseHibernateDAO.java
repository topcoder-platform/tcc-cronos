/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.persistence;

import java.text.MessageFormat;
import java.util.List;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.contest.EntityExistsException;
import com.topcoder.service.digitalrun.contest.EntityNotFoundException;
import com.topcoder.service.digitalrun.contest.Helper;
import com.topcoder.service.digitalrun.contest.PersistenceException;
import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Log;


/**
 * <p>
 * This is a JPA/Hibernate based DAO.
 * </p>
 *
 * <p>
 * It provides convenience methods to set and get the necessary context information such as:
 * <ol>
 *     <li>the logger to use when logging</li>
 *     <li>the <code>SessionContext</code> for any JNDI based data that the DAO needs</li>
 *     <li>unitName specifically used to fetch the <code>EntityManager</code> for the JPA requests</li>
 * </ol>
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This is conditionally thread safe in the sense that we expect that the setter-injection will only
 *     be used after creation to initialize the DAO.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseHibernateDAO {

    /**
     * <p>
     * Represents the <code>SessionContext</code> injected by the manager bean when created.
     * </p>
     *
     * <p>
     * It is initialized in the injection setter, and never changed afterwards.
     * </p>
     *
     * <p>
     * It's non-null after injected when this DAO is instantiated.
     * </p>
     *
     * <p>
     * It can be used to lookup JNDI resources. Here we will use it to get the <code>EntityManager</code>.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the persistence unit name to lookup the <code>EntityManager</code> from
     * <code>SessionContext</code>.
     * </p>
     *
     * <p>
     * This is initialized in the injection setter, and never changed afterwards.
     * </p>
     *
     * <p>
     * It must be non-null, non-empty string.
     * </p>
     */
    private String unitName;

    /**
     * <p>
     * This is a logger which will be used to log actions and exception for this DAO.
     * </p>
     *
     * <p>
     * This is initialized through an injection setter, and never changed afterwards.
     * </p>
     *
     * <p>
     * Cannot be null.
     * </p>
     *
     * <p>
     * This is used in all public methods and logs the actions/exceptions.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    protected BaseHibernateDAO() {
        // empty
    }

    /**
     * <p>
     * Set the session context.
     * </p>
     *
     * @param sessionContext A non-null session context.
     *
     * @throws IllegalArgumentException if the input is null.
     */
    public void setSessionContext(SessionContext sessionContext) {
        ExceptionUtils.checkNull(sessionContext, null, null, "SessionConext should not be null.");
        this.sessionContext = sessionContext;
    }

    /**
     * <p>
     * Set the logger.
     * </p>
     *
     * @param logger A non-null logger to be used.
     *
     * @throws IllegalArgumentException if the input is null.
     */
    public void setLogger(Log logger) {
        ExceptionUtils.checkNull(logger, null, null, "Log should not be null.");
        this.logger = logger;
    }

    /**
     * <p>
     * Set the persistence unit name.
     * </p>
     *
     * @param unitName A non-null, non-empty persistence unit name to lookup the <code>EntityManager</code> from
     *        <code>SessionContext</code>.
     *
     * @throws IllegalArgumentException if the input is null or an empty string.
     */
    public void setUnitName(String unitName) {
        ExceptionUtils.checkNullOrEmpty(unitName, null, null, "Persistence unit name should not be null or empty.");
        this.unitName = unitName;
    }

    /**
     * <p>
     * Get the session context.
     * </p>
     *
     * @return session context.
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * Get the logger.
     * </p>
     *
     * @return logger.
     */
    protected Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Get the unit name.
     * </p>
     *
     * @return unit name.
     */
    protected String getUnitName() {
        return unitName;
    }

    /**
     * <p>
     * This is a utility method to obtain the <code>EntityManager</code> from the JNDI ENC via the session context.
     * Logging will also be performed.
     * </p>
     *
     * @return The <code>EntityManager</code> obtained.
     *
     * @throws PersistenceException If we can't obtain the manager instance.
     */
    protected EntityManager getEntityManager() throws PersistenceException {
        try {
            return Helper.getEntityManager(sessionContext, unitName);
        } catch (PersistenceException e) {
            throw Helper.logException(getLogger(), e);
        }
    }

    /**
     * <p>
     * This method throws <code>IllegalArgumentException</code> if given object is null.
     * Logging will also be performed.
     * </p>
     *
     * @param object To be checked.
     * @param usage Represents the usage of the object.
     *
     * @throws IllegalArgumentException if given object is null.
     */
    protected void checkNullWithLogging(Object object, String usage) {
        try {
            ExceptionUtils.checkNull(object, null, null, usage + " should not be null.");
        } catch (IllegalArgumentException e) {
            throw Helper.logException(getLogger(), e);
        }
    }

    /**
     * <p>
     * This method throws <code>EntityExistsException</code> if entity already exists in persistence.
     * Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     Since this is protected method, to simplify the code and avoid unnecessary overkill, this method does
     *     not perform IAE check for null input. It assumes that the implementations should always pass non-null
     *     arguments.
     * </p>
     *
     * @param <T> Generic type.
     * @param em <code>EntityManager</code>.
     * @param entity Entity.
     *
     * @throws NullPointerException if any argument is null.
     * @throws EntityExistsException if entity already exists in persistence.
     * @throws PersistenceException If a generic persistence error occurs.
     */
    protected < T extends BaseEntity > void errorIfEntityExists(EntityManager em, T entity)
        throws EntityExistsException, PersistenceException {

        Class < ? extends BaseEntity > clazz = entity.getClass();

        long id = entity.getId();

        if (findEntity(em, clazz, id) != null) {
            EntityExistsException ex =
                new EntityExistsException(MessageFormat.format(
                        "{0} entity with id {1} already exists.", clazz.getSimpleName(), id));
            throw Helper.logException(getLogger(), ex);
        }
    }

    /**
     * <p>
     * This method throws <code>EntityNotFoundException</code> if entity does not exist in persistence.
     * Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     Since this is protected method, to simplify the code and avoid unnecessary overkill, this method does
     *     not perform IAE check for null input. It assumes that the implementations should always pass non-null
     *     arguments.
     * </p>
     *
     * @param <T> Generic type.
     * @param em <code>EntityManager</code>.
     * @param entity Entity.
     *
     * @return The existing entity.
     *
     * @throws NullPointerException if any argument is null.
     * @throws EntityNotFoundException if entity does not exist in persistence.
     * @throws PersistenceException If a generic persistence error occurs.
     */
    @SuppressWarnings("unchecked")
    protected < T extends BaseEntity > T errorIfEntityNotExists(EntityManager em, T entity)
        throws EntityNotFoundException, PersistenceException {

        Class < ? extends BaseEntity > clazz = entity.getClass();
        long id = entity.getId();

        return (T) errorIfEntityNotExists(em, clazz, id);
    }

    /**
     * <p>
     * This method throws <code>EntityNotFoundException</code> if entity does not exist in persistence.
     * Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     Since this is protected method, to simplify the code and avoid unnecessary overkill, this method does
     *     not perform IAE check for null input. It assumes that the implementations should always pass non-null
     *     arguments.
     * </p>
     *
     * @param <T> Generic type.
     * @param em <code>EntityManager</code>.
     * @param clazz Class of entity.
     * @param id Id of entity.
     *
     * @return The existing entity.
     *
     * @throws NullPointerException if any argument is null.
     * @throws EntityNotFoundException if entity does not exist in persistence.
     * @throws PersistenceException If a generic persistence error occurs.
     */
    protected < T extends BaseEntity > T errorIfEntityNotExists(EntityManager em, Class < T > clazz, long id)
        throws EntityNotFoundException, PersistenceException {

        T current = findEntity(em, clazz, id);

        if (current == null) {
            EntityNotFoundException ex =
                new EntityNotFoundException(MessageFormat.format(
                    "{0} entity with id {1} does not exists.", clazz.getSimpleName(), id));
            throw Helper.logException(getLogger(), ex);
        }

        return current;
    }

    /**
     * <p>
     * Find entity. Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     Since this is protected method, to simplify the code and avoid unnecessary overkill, this method does
     *     not perform IAE check for null input. It assumes that the implementations should always pass non-null
     *     arguments.
     * </p>
     *
     * @param <T> Generic type.
     * @param em <code>EntityManager</code>.
     * @param clazz Class of entity.
     * @param id Id of entity.
     *
     * @return Entity found. May be null if does not exist.
     *
     * @throws PersistenceException If a generic persistence error occurs.
     */
    protected < T extends BaseEntity > T findEntity(EntityManager em, Class < T > clazz, long id)
        throws PersistenceException {

        try {
            return em.find(clazz, id);
        } catch (javax.persistence.PersistenceException e) {
            Helper.logException(getLogger(), e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Query entities. Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * @param em <code>EntityManager</code>.
     * @param query The JPA query string.
     *
     * @return The list of entities.
     *
     * @throws PersistenceException If a generic persistence error occurs.
     */
    protected List queryEntities(EntityManager em, String query) throws PersistenceException {
        try {
            return em.createQuery(query).getResultList();
        } catch (javax.persistence.PersistenceException e) {
            Helper.logException(getLogger(), e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Create/update/delete entity. Logging will also be performed if persistence exception occurs.
     * </p>
     *
     * <p>
     *     <strong>Note:</strong>
     *     Since this is protected method, to simplify the code and avoid unnecessary overkill, this method does
     *     not perform IAE check for null input. It assumes that the implementations should always pass non-null
     *     arguments.
     * </p>
     *
     * @param <T> Generic type.
     * @param em The <code>EntityManager</code> used to manage entity.
     * @param entity The entity to be created/updated/deleted.
     * @param action The enum indicates the desired action.
     *
     * @throws PersistenceException If a generic persistence error occurs.
     */
    protected < T extends BaseEntity > void manageEntity(EntityManager em, T entity, Action action)
        throws PersistenceException {

        try {

            if (action == Action.CREATE) {
                // Persist entity
                em.persist(entity);
            } else if (action == Action.DELETE) {
                // Remove entity
                em.remove(entity);
            } else {
                // Merge entity
                em.merge(entity);
            }

            // Flush EntityManager
            em.flush();
        } catch (javax.persistence.PersistenceException e) {
            Helper.logException(getLogger(), e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    /**
     * <p>
     * The enum represents the action to create/update/delete entity. It is only used within this class and
     * is not exposed to external.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    protected static enum Action {

        /**
         * <p>
         * Enum represents the action to create entity.
         * </p>
         */
        CREATE,

        /**
         * <p>
         * Enum represents the action to update entity.
         * </p>
         */
        UPDATE,

        /**
         * <p>
         * Enum represents the action to delete entity.
         * </p>
         */
        DELETE
    }
}

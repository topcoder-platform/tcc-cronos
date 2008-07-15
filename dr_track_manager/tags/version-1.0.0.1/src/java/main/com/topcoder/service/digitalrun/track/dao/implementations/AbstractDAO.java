/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.dao.implementations;

import com.topcoder.service.digitalrun.entity.BaseEntity;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceException;
import com.topcoder.service.digitalrun.track.EntityNotFoundException;
import com.topcoder.service.digitalrun.track.Helper;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.SessionContext;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;


/**
 * <p>
 * This abstract class holds the common field of all the five concrete DAO implementations. It has a SessionContext and
 * an unit name used to obtain an EntityManager instance and a Log instance used to perform logging if logging is
 * desired; if logging is not desired this field will remain null.It provides a method that obtains an EntityManager
 * instance. It provides some utility methods to persist, find, findAll, update, remove the entity with EntityManager.
 * </p>
 *
 * <p>
 * It is not thread safe because it is mutable. Anyway, setters are meant to be called only once so there will be no
 * thread safety issues.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
public abstract class AbstractDAO {
    /**
     * <p>
     * The unit name used to obtain an EntityManager instance. It has a setter; cannot be set to null or empty string.
     * </p>
     */
    private String unitName;

    /**
     * <p>
     * Logger used to perform logging. If not set logging is disabled. It has a setter; cannot be set to null.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * The session context used to obtain an EntityManager instance. It has a setter; cannot be set to null.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    protected AbstractDAO() {
    }

    /**
     * <p>
     * Creates an EntityManager instance based on the persistence unit name.
     * </p>
     *
     * @return an EntityManager instance
     *
     * @throws DigitalRunTrackManagerPersistenceException if the retrieval of the EntityManager failed
     */
    protected EntityManager getEntityManager() throws DigitalRunTrackManagerPersistenceException {
        Helper.logEntranceInfo("getEntityManager()", logger);

        try {
            Object obj = sessionContext.lookup(this.unitName);

            if (!(obj instanceof EntityManager)) {
                String msg = "The EntityManager for the persistence unit [" + unitName + "] can not be found.";
                Helper.logError(msg, logger);
                throw new DigitalRunTrackManagerPersistenceException(msg);
            }

            return (EntityManager) obj;
        } catch (IllegalArgumentException iae) {
            Helper.logException(iae, logger);
            throw new DigitalRunTrackManagerPersistenceException("Error occurs during find EntityManager.", iae);
        } finally {
            Helper.logExitInfo("getEntityManager()", logger);
        }
    }

    /**
     * <p>
     * Retrieves the BaseEntity object with the given primary key.
     * </p>
     *
     * @param <T> a supertype of the types of the entity arguments. The compiler will infer the narrowest possible type
     *        based on the arguments, but a wider type may be explicitly specified
     * @param clz the class type of the entity to retrieve
     * @param primaryKey the primary key of the entity
     *
     * @return The entity object, not null
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no required entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    protected < T extends BaseEntity > T find(Class < T > clz, Long primaryKey)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.checkNegative(primaryKey, clz.getSimpleName() + ".id", getLogger());

        String notFoundErrorMsg =
            "There is no " + clz.getSimpleName() + " entity with the given id [" + primaryKey + " ] in persistence.";

        try {
            EntityManager em = getEntityManager();
            T object = em.find(clz, primaryKey);

            if (object == null) {
                Helper.logError(notFoundErrorMsg, getLogger());
                throw new EntityNotFoundException(notFoundErrorMsg);
            }

            return object;
        } catch (IllegalArgumentException iae) {
            Helper.logException(iae, getLogger());
            throw new EntityNotFoundException(notFoundErrorMsg, iae);
        } catch (PersistenceException pe) {
            Helper.logException(pe, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to retrieve the entity.", pe);
        }
    }

    /**
     * <p>
     * Retrieves all the entity objects for the given class type.
     * </p>
     *
     * @param <T> a supertype of the types of the entity arguments. The compiler will infer the narrowest possible type
     *        based on the arguments, but a wider type may be explicitly specified
     * @param clz the class type of the entity to retrieve
     *
     * @return The List of entities, not null, maybe empty
     *
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    @SuppressWarnings("unchecked")
    protected < T extends BaseEntity > List < T > findAll(Class < T > clz)
        throws DigitalRunTrackManagerPersistenceException {
        EntityManager em = getEntityManager();

        try {
            List < T > result = em.createQuery("FROM " + clz.getSimpleName()).getResultList();

            return (result == null) ? new ArrayList<T>() : result;
        } catch (Exception pe) {
            Helper.logException(pe, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to retrieve the entity list.", pe);
        }
    }

    /**
     * <p>
     * Creates the BaseEntity object into Persistence.
     * </p>
     *
     * @param <T> a supertype of the types of the entity arguments. The compiler will infer the narrowest possible type
     *        based on the arguments, but a wider type may be explicitly specified
     * @param entity the BaseEntity to persist
     * @param name the name of the entity
     *
     * @return The persisted entity object, not null
     *
     * @throws IllegalArgumentException if argument is null or if its id is positive
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    protected < T extends BaseEntity > T persist(T entity, String name)
        throws DigitalRunTrackManagerPersistenceException {
        Helper.checkEntityPositive(entity, name, getLogger());

        try {
            EntityManager em = getEntityManager();
            em.persist(entity);
            //Synchronize the persistence context to the underlying database.
            em.flush();

            return entity;
        } catch (EntityExistsException eee) {
            //thrown by persist, if already exists
            Helper.logException(eee, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("The given entity already exists.", eee);
        } catch (PersistenceException pe) {
            Helper.logException(pe, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to persist the entity.", pe);
        }
    }

    /**
     * <p>
     * Removes the BaseEntity object with the given primary key.
     * </p>
     *
     * @param <T> a supertype of the types of the entity arguments. The compiler will infer the narrowest possible type
     *        based on the arguments, but a wider type may be explicitly specified
     * @param clz the class type of the entity to remove
     * @param primaryKey the primary key of the entity
     *
     * @throws IllegalArgumentException if argument is negative
     * @throws EntityNotFoundException if there is no given entity  with the given id in persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    protected < T extends BaseEntity > void remove(Class < T > clz, Long primaryKey)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.checkNegative(primaryKey, clz.getSimpleName() + ".id", getLogger());

        String notFoundErrorMsg =
            "There is no " + clz.getSimpleName() + " entity with the given id [" + primaryKey + " ] in persistence.";

        try {
            EntityManager em = getEntityManager();
            T object = em.find(clz, primaryKey);

            if (object == null) {
                Helper.logError(notFoundErrorMsg, getLogger());
                throw new EntityNotFoundException(notFoundErrorMsg);
            }

            em.remove(object);
            //Synchronize the persistence context to the underlying database.
            em.flush();
        } catch (IllegalArgumentException iae) {
            //IAE thrown by EntityManager#find method
            Helper.logException(iae, getLogger());
            throw new EntityNotFoundException(notFoundErrorMsg, iae);
        } catch (PersistenceException pe) {
            Helper.logException(pe, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to remove the entity.", pe);
        }
    }

    /**
     * <p>
     * Updates the BaseEntity object.
     * </p>
     *
     * @param <T> a supertype of the types of the entity arguments. The compiler will infer the narrowest possible type
     *        based on the arguments, but a wider type may be explicitly specified
     * @param entity the entity object to update
     * @param name the name of the entity
     *
     * @throws IllegalArgumentException if argument is null
     * @throws EntityNotFoundException if the entity does not exist in the persistence
     * @throws DigitalRunTrackManagerPersistenceException if any errors occur when accessing the persistent storage
     */
    protected < T extends BaseEntity > void update(T entity, String name)
        throws EntityNotFoundException, DigitalRunTrackManagerPersistenceException {
        Helper.checkNull(entity, name, getLogger());

        try {
            EntityManager em = getEntityManager();

            if (em.find(entity.getClass(), entity.getId()) == null) {
                throw new EntityNotFoundException("The entity to update is not exist.");
            }

            em.merge(entity);
            //Synchronize the persistence context to the underlying database.
            em.flush();
        } catch (IllegalArgumentException iae) {
            //thrown by merge, if instance is not an entity or is a removed entity
            Helper.logException(iae, getLogger());
            throw new EntityNotFoundException("The given entity to update does not exist.", iae);
        } catch (PersistenceException pe) {
            Helper.logException(pe, getLogger());
            throw new DigitalRunTrackManagerPersistenceException("Fail to update the entity.", pe);
        }
    }

    /**
     * <p>
     * Returns the current log instance.
     * </p>
     *
     * @return the logger field
     */
    protected Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Returns the current persistence unit name.
     * </p>
     *
     * @return the unit name
     */
    protected String getUnitName() {
        return unitName;
    }

    /**
     * <p>
     * Returns the current SssionContext.
     * </p>
     *
     * @return the session context
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * <p>
     * Sets the logger instance.
     * </p>
     *
     * @param logger the logger
     *
     * @throws IllegalArgumentException if argument is null
     */
    public void setLogger(Log logger) {
        ExceptionUtils.checkNull(logger, null, null, "Log is null");
        this.logger = logger;
    }

    /**
     * <p>
     * Sets the SessionContext.
     * </p>
     *
     * @param sessionContext the session context
     *
     * @throws IllegalArgumentException if argument is null
     */
    public void setSessionContext(SessionContext sessionContext) {
        ExceptionUtils.checkNull(sessionContext, null, null, "SessionContext is null");
        this.sessionContext = sessionContext;
    }

    /**
     * <p>
     * Sets the persistence unit name.
     * </p>
     *
     * @param unitName the unit name
     *
     * @throws IllegalArgumentException if unitName is null or empty string
     */
    public void setUnitName(String unitName) {
        ExceptionUtils.checkNullOrEmpty(unitName, null, null, "UnitName is null or empty");
        this.unitName = unitName;
    }
}

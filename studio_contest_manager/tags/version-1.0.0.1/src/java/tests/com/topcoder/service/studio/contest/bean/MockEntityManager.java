/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;


/**
 * <p>
 * Mock implementation of EntityManager, it's used for failure test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockEntityManager implements EntityManager {
    /**
     * <p>EntityManager proxy.</p>
     */
    private static EntityManager manager;

    /**
     * <p>The public share entity manager factory.</p>
     */
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "contest_submission");

    /**
     * <p>Exception flag for persistence exception.</p>
     */
    private boolean expFlag = false;

    /**
     * <p>Exception flag for transaction exception.</p>
     */
    private boolean transactionExp = false;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public MockEntityManager() {
        if (manager == null) {
            manager = emf.createEntityManager();
        } else {
            if (!manager.isOpen()) {
                manager = emf.createEntityManager();
            }
        }
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 entity
     */
    public void persist(Object arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        manager.persist(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 entity
     * @param <T> class
     * @return new entity
     */
    public <T> T merge(T arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.merge(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 entity
     */
    public void remove(Object arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        manager.remove(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 entity
     * @param arg1 primary key
     * @param <T> class
     * @return found entity
     */
    public <T> T find(Class<T> arg0, Object arg1) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.find(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 entity
     * @param arg1 primary key
     * @param <T> class
     * @return found entity
     */
    public <T> T getReference(Class<T> arg0, Object arg1) {
        return manager.getReference(arg0, arg1);
    }

    /**
     * <p>Mock implementation.</p>
     */
    public void flush() {
        manager.flush();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 flush mode type
     */
    public void setFlushMode(FlushModeType arg0) {
        manager.setFlushMode(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @return flush mode type
     */
    public FlushModeType getFlushMode() {
        return manager.getFlushMode();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public void lock(Object arg0, LockModeType arg1) {
        manager.lock(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     */
    public void refresh(Object arg0) {
        manager.refresh(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void clear() {
        manager.clear();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @return result
     */
    public boolean contains(Object arg0) {
        return manager.contains(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @return result
     */
    public Query createQuery(String arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createQuery(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @return result
     */
    public Query createNamedQuery(String arg0) {
        return manager.createNamedQuery(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @return result
     */
    public Query createNativeQuery(String arg0) {
        return manager.createNativeQuery(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @param arg1 arg1
     * @return result
     */
    public Query createNativeQuery(String arg0, Class arg1) {
        return manager.createNativeQuery(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param arg0 arg0
     * @param arg1 arg1
     * @return result
     */
    public Query createNativeQuery(String arg0, String arg1) {
        return manager.createNativeQuery(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void joinTransaction() {
        manager.joinTransaction();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @return result
     */
    public Object getDelegate() {
        return manager.getDelegate();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void close() {
        manager.close();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @return result
     */
    public boolean isOpen() {
        return manager.isOpen();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @return result
     */
    public EntityTransaction getTransaction() {
        return manager.getTransaction();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param flag the exception flag
     */
    public void enablePersistenceException(boolean flag) {
        this.expFlag = flag;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     * @param flag the exception flag
     */
    public void enableTransactionException(boolean flag) {
        this.transactionExp = flag;
    }
}

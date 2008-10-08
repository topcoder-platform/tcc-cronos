/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests.mock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;


/**
 * <p>
 * Mock implementation of the EntityManager interface.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class MockEntityManager implements EntityManager {
    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#clear()
     */
    public void clear() {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#close()
     */
    public void close() {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#contains(java.lang.Object)
     */
    public boolean contains(Object arg0) {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
     */
    public Query createNamedQuery(String arg0) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
     */
    public Query createNativeQuery(String arg0, Class arg1) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
     */
    public Query createNativeQuery(String arg0, String arg1) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
     */
    public Query createNativeQuery(String arg0) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#createQuery(java.lang.String)
     */
    public Query createQuery(String arg0) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
     */
    public <T> T find(Class<T> arg0, Object arg1) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#flush()
     */
    public void flush() {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#getDelegate()
     */
    public Object getDelegate() {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#getFlushMode()
     */
    public FlushModeType getFlushMode() {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
     */
    public <T> T getReference(Class<T> arg0, Object arg1) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#getTransaction()
     */
    public EntityTransaction getTransaction() {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#isOpen()
     */
    public boolean isOpen() {
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#joinTransaction()
     */
    public void joinTransaction() {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
     */
    public void lock(Object arg0, LockModeType arg1) {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#merge(java.lang.Object)
     */
    public <T> T merge(T arg0) {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#persist(java.lang.Object)
     */
    public void persist(Object arg0) {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#refresh(java.lang.Object)
     */
    public void refresh(Object arg0) {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#remove(java.lang.Object)
     */
    public void remove(Object arg0) {
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
     */
    public void setFlushMode(FlushModeType arg0) {
    }
}

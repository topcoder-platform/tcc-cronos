/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.failuretests;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 * Mock class implements EntityManager for failure test.
 *
 * @author extra
 * @version 1.0
 */
public class MockEntityManager implements EntityManager {

    /**
     * Do nothing.
     */
    public void clear() {
        // DO NOTHING

    }

    /**
     * Do nothing.
     */
    public void close() {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return false
     */
    public boolean contains(Object arg0) {
        // DO NOTHING
        return false;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return null
     */
    public Query createNamedQuery(String arg0) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return null
     */
    public Query createNativeQuery(String arg0) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param arg1
     *            no use
     * @return null
     */
    public Query createNativeQuery(String arg0, Class arg1) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param arg1
     *            no use
     * @return null
     */
    public Query createNativeQuery(String arg0, String arg1) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @return null
     */
    public Query createQuery(String arg0) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param arg1
     *            no use
     * @param <T> type
     * @return null
     */
    public <T> T find(Class<T> arg0, Object arg1) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     */
    public void flush() {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public Object getDelegate() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public FlushModeType getFlushMode() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param arg1
     *            no use
     * @param <T> type
     * @return null
     */
    public <T> T getReference(Class<T> arg0, Object arg1) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return null
     */
    public EntityTransaction getTransaction() {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @return false
     */
    public boolean isOpen() {
        // DO NOTHING
        return false;
    }

    /**
     * Do nothing.
     */
    public void joinTransaction() {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param arg1
     *            no use
     */
    public void lock(Object arg0, LockModeType arg1) {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     * @param <T> type
     * @return null
     */
    public <T> T merge(T arg0) {
        // DO NOTHING
        return null;
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     */
    public void persist(Object arg0) {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     */
    public void refresh(Object arg0) {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     */
    public void remove(Object arg0) {
        // DO NOTHING

    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            no use
     */
    public void setFlushMode(FlushModeType arg0) {
        // DO NOTHING

    }

}

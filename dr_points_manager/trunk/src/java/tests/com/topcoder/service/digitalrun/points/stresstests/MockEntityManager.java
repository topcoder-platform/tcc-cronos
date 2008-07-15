/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 * <p>
 * The mock EntityManager class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockEntityManager implements EntityManager {
    
    private boolean exceptionFlag = false;
    
    private static EntityManager manager;
    
    public MockEntityManager(EntityManager manager){
        this.manager = manager;
    }

    public void clear() {
        this.manager.clear();

    }

    public void close() {
        this.manager.close();

    }

    public boolean contains(Object arg0) {
        return this.manager.contains(arg0);
    }

    public Query createNamedQuery(String arg0) {
        return this.manager.createNamedQuery(arg0);
    }

    public Query createNativeQuery(String arg0) {
        return this.manager.createNativeQuery(arg0);
    }

    public Query createNativeQuery(String arg0, Class arg1) {
        return this.manager.createNativeQuery(arg0, arg1);
    }

    public Query createNativeQuery(String arg0, String arg1) {
        return this.manager.createNativeQuery(arg0, arg1);
    }

    public Query createQuery(String arg0) {
        return this.manager.createQuery(arg0);
    }

    public <T> T find(Class<T> arg0, Object arg1) {
        if(arg1 instanceof Long){
            if(((Long)arg1).longValue() == 1){
                try {
                    return arg0.newInstance();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return this.manager.find(arg0, arg1);
    }

    public void flush() {
        this.manager.flush();

    }

    public Object getDelegate() {
        return this.manager.getDelegate();
    }

    public FlushModeType getFlushMode() {
        return this.manager.getFlushMode();
    }

    public <T> T getReference(Class<T> arg0, Object arg1) {
        return this.manager.getReference(arg0, arg1);
    }

    public EntityTransaction getTransaction() {
        return this.manager.getTransaction();
    }

    public boolean isOpen() {
        return this.manager.isOpen();
    }

    public void joinTransaction() {
        this.manager.joinTransaction();

    }

    public void lock(Object arg0, LockModeType arg1) {
        this.manager.lock(arg0, arg1);

    }

    public <T> T merge(T arg0) {
        if(this.exceptionFlag){
            throw new IllegalArgumentException("For Testing");
        }
        for (int i = 0; i < 10000; i++) {
            // mock persisting
        }
        return arg0;
    }

    public void persist(Object arg0) {
       if(this.exceptionFlag){
           throw new EntityExistsException("For Testing");
       }
       for (int i = 0; i < 10000; i++) {
           // mock persisting
       }
    }

    public void refresh(Object arg0) {
        this.manager.refresh(arg0);

    }

    public void remove(Object arg0) {
        this.manager.remove(arg0);

    }

    public void setFlushMode(FlushModeType arg0) {
        this.manager.setFlushMode(arg0);
    }
    
    public void SetExceptionFlag(boolean flag){
        this.exceptionFlag = flag;
    }

}

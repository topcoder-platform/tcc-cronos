/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import java.security.Identity;
import java.security.Principal;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * Mock for the {@link SessionContext}.
 *
 * @author KLW
 * @version 1.0
  */
public class MockSessionContext implements SessionContext {
   private static EntityManager manager;
    public <T> T getBusinessObject(Class<T> arg0) throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public EJBObject getEJBObject() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public Class getInvokedBusinessInterface() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public MessageContext getMessageContext() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public Identity getCallerIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

    public Principal getCallerPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    public EJBHome getEJBHome() {
        // TODO Auto-generated method stub
        return null;
    }

    public EJBLocalHome getEJBLocalHome() {
        // TODO Auto-generated method stub
        return null;
    }

    public Properties getEnvironment() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean getRollbackOnly() throws IllegalStateException {
        // TODO Auto-generated method stub
        return false;
    }

    public TimerService getTimerService() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public UserTransaction getUserTransaction() throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isCallerInRole(Identity arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isCallerInRole(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object lookup(String arg0) {
        if(manager!=null){
            return manager;
        }
        try{
        Ejb3Configuration cfg = new Ejb3Configuration();
        
        EntityManagerFactory emf = cfg.configure("hibernate.cfg.xml").buildEntityManagerFactory();
        manager = emf.createEntityManager();
        }catch(Exception e){
            //ignore
        }
        
        return manager;
    }

    public void setRollbackOnly() throws IllegalStateException {
        // TODO Auto-generated method stub
        
    }
}

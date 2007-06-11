/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RatePersistence;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>This is an implementation EJB of the LocalRate interface. This is nothing more than a delegating wrapper
 * around the RatePersistence instance but with an interception of exceptions from the dao with subsequent transaction
 * control (rollback only) when an issue has been detected/signalled.</p>
 *  <p>In other words this acts like a container based transactional control plugin. This is a stateless session
 * bean with a container manager transaction control. This ejb will join any started transactions.</p>
 *  <p>Thread-Safety</p>
 *  <p>Thread safety is is not an issue since each bean will be guaranteed by the container to be accessed by a
 * single thread at any given time.</p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 3.2
 */
public class RateEjb implements SessionBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 2125904035291617864L;

    /**
     * Represents the property name to retrieve the object factory namespace from sessionContext. If it's
     * absent, the default namespace "com.topcoder.util.objectfactory.ObjectFactory" will be used.
     */
    private static final String PROP_OF_NAMESPACE = "of_namespace";

    /**
     * Represents the property name to retrieve the key from sessionContext for ObjectFactory to create
     * RatePersistencefrom. This property should exist in the context.
     */
    private static final String PROP_PERSISTENCE_KEY = "of_rate_persistence_key";

    /**
     * <p>This is the DAO that implements the necessary persistence actions for the rate data. It is
     * initialized in the ejbCreate method and is immutable for the lifetime of this bean.</p>
     *  <p>Cannot be null.</p>
     */
    private RatePersistence ratePersistenceDao;

    /**
     * <p>This is a session context that is used to obtain the run time session context. We will use this to
     * access configuration value for the EJB (i.e. we will use it to get the Object factory Key)</p>
     *  <p>This is initialized through the setSessionContext() method and is immutable after that.</p>
     *  <p>Cannot be null.</p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * This is a no-op constructor.
     * </p>
     */
    public RateEjb() {
        // does nothing
    }

    /**
     * <p>Delegate method that adds an array of rates to persistence. If any exception is caught,
     * sessionContext.setRollbackOnly() will be called to roll back the transaction.</p>
     *
     * @param rates The array of Rates to add - this cannot be null, or contain null values and should not be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws IllegalArgumentException if the array of rates is null, or contains null values or it's empty
     * @throws RatePersistenceException if there are problems persisting the rates.
     */
    public void addRates(Rate[] rates, boolean audit) throws RatePersistenceException {
        try {
            this.ratePersistenceDao.addRates(rates, audit);
        } catch (RatePersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>Delegate method that deletes all the entries from the input array of rates in persistence. If any
     * exception is caught, sessionContext.setRollbackOnly() will be called to roll back the transaction.</p>
     *
     * @param rates The array of Rates to removed - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, or contains null values or it's empty
     */
    public void deleteRates(Rate[] rates, boolean audit)
        throws RatePersistenceException {
        try {
            this.ratePersistenceDao.deleteRates(rates, audit);
        } catch (RatePersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>This method does nothing.</p>
     */
    public void ejbActivate() {
        //does nothing
    }

    /**
     * <p>This is an initialization delegation that will be called by the container expecting the bean to
     * initialize itself. This will read env entities from java:comp/env the ratePersistenceDao.</p>
     *
     * @throws CreateException if sessionContext is not set or there was an issue with creation (we re-throw)
     */
    public void ejbCreate() throws CreateException {
        if (this.sessionContext == null) {
            throw new CreateException("sessionContext is not set");
        }

        Context context;

        try {
            context = (Context) new InitialContext().lookup("java:comp/env");
        } catch (NamingException e) {
            throw new CreateException("failed to get context:java:comp/env, msg:" + e.getMessage());
        }

        String objFactoryNs;

        try {
            objFactoryNs = (String) context.lookup(PROP_OF_NAMESPACE);
        } catch (NamingException e1) {
            objFactoryNs = ObjectFactory.class.getName(); //uses default if it does not exist
        }

        String persistenceKey;

        try {
            persistenceKey = (String) context.lookup(PROP_PERSISTENCE_KEY);
        } catch (NamingException e1) {
            throw new CreateException(PROP_PERSISTENCE_KEY + " is not configured");
        }

        ObjectFactory factory;

        try {
            factory = new ObjectFactory(new ConfigManagerSpecificationFactory(objFactoryNs));
        } catch (SpecificationConfigurationException e) {
            throw new CreateException("failed to create ObjectFactory, msg:" + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new CreateException("failed to create ObjectFactory, msg:" + e.getMessage());
        }

        //creates RatePersistence via ObjectFactory
        try {
            this.ratePersistenceDao = (RatePersistence) factory.createObject(persistenceKey);
        } catch (InvalidClassSpecificationException e) {
            throw new CreateException("failed to create RatePersistence with key:" + persistenceKey + " msg:"
                + e.getMessage());
        } catch (ClassCastException e) {
            throw new CreateException("the created instance is not type of RatePersistence, key:" + persistenceKey
                + " msg:" + e.getMessage());
        }
    }

    /**
     * <p>This method does nothing.</p>
     */
    public void ejbPassivate() {
        //does nothing
    }

    /**
     * <p>This method does nothing.</p>
     */
    public void ejbRemove() {
        //does nothing
    }

    /**
     * <p>Delegate method that fetches the matching Rate from persistence.</p>
     *
     * @param rateId The ID of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id pair, or null if no match is found.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     */
    public Rate retrieveRate(long rateId, long companyId)
        throws RatePersistenceException {
        return this.ratePersistenceDao.retrieveRate(rateId, companyId);
    }

    /**
     * <p>Delegate method that fetches the matching Rate from persistence.</p>
     *
     * @param description The description of the rate to find.
     * @param companyId The ID of the company the rate is related to.
     *
     * @return The Rate matching the given Id/description pair, or null if no match is found.
     *
     * @throws IllegalArgumentException If the description is null.
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate retrieveRate(String description, long companyId)
        throws RatePersistenceException {
        return this.ratePersistenceDao.retrieveRate(description, companyId);
    }

    /**
     * <p>Delegate method that fetches the matching Rates from persistence. This should simply delegate the
     * call to ratePersistenceDao, re throwing exceptions.</p>
     *
     * @param companyId The Id of the company using the rates.
     *
     * @return An array of Rates used by the given company, not null but may be empty
     *
     * @throws RatePersistenceException If there are problems retrieving the rates from persistence
     */
    public Rate[] retrieveRates(long companyId) throws RatePersistenceException {
        return this.ratePersistenceDao.retrieveRates(companyId);
    }

    /**
     * <p>This is called by the container (before ejbCreate) to allow the bean to fetch the session context
     * which holds configuration data from the deployment descriptor.</p>
     *
     * @param context session context for this bean
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     * <p>Delegate method that updates the entries from the input array of rates in persistence.If any
     * exception is caught, sessionContext.setRollbackOnly() will be called to roll back the transaction.</p>
     *
     * @param rates The array of Rates to update - this cannot be null, or contain null values, but may be empty
     * @param audit Boolean flag indication whether auditing should occur.
     *
     * @throws RatePersistenceException if there are problems persisting the rates.
     * @throws IllegalArgumentException if the array of rates is null, or contains null values or it's empty
     */
    public void updateRates(Rate[] rates, boolean audit)
        throws RatePersistenceException {
        try {
            this.ratePersistenceDao.updateRates(rates, audit);
        } catch (RatePersistenceException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }
}
